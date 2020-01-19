package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import com.dasbikash.csclientbe.exceptions.CsNotAvailableException
import com.dasbikash.csclientbe.model.db.User
import com.dasbikash.csclientbe.model.request.CsTokenReqResponse
import com.dasbikash.csclientbe.model.request.CsUserRegisterRequest
import com.dasbikash.csclientbe.model.response.CsSuccessResponse
import com.dasbikash.csclientbe.repos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestClientException
import org.springframework.web.client.RestTemplate


@Service
class AdminTaskService @Autowired constructor(
        private val userRepository: UserRepository,
        private val basicAuthService: BasicAuthService,
        @Value("\${cs.client_id}")
        private val CS_ADMIN_USER_ID:String,
        @Value("\${cs.password}")
        private val CS_ADMIN_USER_PASSWORD:String,
        @Value("\${cs.base_path}")
        private val CHAT_SERVICE_BASE_PATH:String
) {


    private var csJwtAccessToken:String? = null

    private fun getBasicAuthHeader():String{
        return basicAuthService.getBasicAuthHeader(AuthenticationRequest(CS_ADMIN_USER_ID, CS_ADMIN_USER_PASSWORD))
    }

    private fun obtainAdminJwtTokenFromCs():String{
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add(HttpHeaders.AUTHORIZATION, getBasicAuthHeader())
        val entity = HttpEntity<Any>(headers)
        val restTemplate = RestTemplate()
        try {
            return restTemplate.exchange(CHAT_SERVICE_BASE_PATH+ LOG_IN_PATH,HttpMethod.GET,entity,CsTokenReqResponse::class.java).body!!.token!!
        }catch (ex:Throwable){
            throw CsNotAvailableException(ex)
        }
    }

    private fun getAdminJwtTokenForCs():String{
        if (csJwtAccessToken == null){
            csJwtAccessToken = obtainAdminJwtTokenFromCs()
        }
        return JWT_TOKEN_PREAMBLE+csJwtAccessToken!!
    }

    private fun getJwtHeader():HttpHeaders{
        val headers = HttpHeaders()
        headers.contentType = MediaType.APPLICATION_JSON
        headers.add(HttpHeaders.AUTHORIZATION, getAdminJwtTokenForCs())
        return headers
    }

    fun registerUser(csUserRegisterRequest: CsUserRegisterRequest):CsSuccessResponse?{
        val user = userRepository.findById(csUserRegisterRequest.id).orElseThrow { IllegalArgumentException() }
        var csSuccessResponse:CsSuccessResponse?=null
        runWithJwtRefresher {
            val entity = HttpEntity<Any>(csUserRegisterRequest, it)
            val restTemplate = RestTemplate()
            if (user.isCustomerManager) {
                csSuccessResponse =
                        restTemplate.exchange(ADMIN_BASE_PATH + REGISTER_CM_PATH, HttpMethod.PUT, entity, CsSuccessResponse::class.java).body
            } else {
                csSuccessResponse  =
                        restTemplate.exchange(ADMIN_BASE_PATH + REGISTER_USER_PATH, HttpMethod.PUT, entity, CsSuccessResponse::class.java).body
            }
        }
        return csSuccessResponse
    }

    private fun <T:Any?> runWithJwtRefresher(task:(HttpHeaders)->T):T{
        try {
            return task(getJwtHeader())
        }catch (ex: RestClientException){
            csJwtAccessToken = null
            return task(getJwtHeader())
        }
    }

    fun generateCustomerManagerAccessToken(user: User):CsTokenReqResponse{
        return generateUserToken(user,GENERATE_CM_ACCESS_TOKEN_PATH){user.isCustomerManager}
    }

    fun generateCustomerManagerSessionToken(user: User):CsTokenReqResponse{
        return generateUserToken(user,GENERATE_CM_SESSION_TOKEN_PATH){user.isCustomerManager}
    }

    fun generateEndUserAccessToken(user: User):CsTokenReqResponse{
        return generateUserToken(user,GENERATE_USER_ACCESS_TOKEN_PATH){user.isEndUser}
    }

    fun generateEndUserSessionToken(user: User):CsTokenReqResponse {
        return generateUserToken(user, GENERATE_USER_SESSION_TOKEN_PATH){user.isEndUser}
    }

    private fun generateUserToken(user: User,path:String,validateUserType:(User)->Boolean):CsTokenReqResponse{
        if (validateUserType(user)){
            return runWithJwtRefresher {
                val entity = HttpEntity<Any>(it)
                val restTemplate = RestTemplate()
                restTemplate.exchange(ADMIN_BASE_PATH + path, HttpMethod.GET, entity,
                        CsTokenReqResponse::class.java, user.userId!!).body!!
            }
        }else{
            throw CsClientAuthenticationException()
        }
    }
    private val ADMIN_BASE_PATH = CHAT_SERVICE_BASE_PATH+"client-admin/"
    private val LOG_IN_PATH = "auth/login"
    private val REGISTER_CM_PATH = "register-cm"
    private val REGISTER_USER_PATH = "register-user"
    private val GENERATE_CM_ACCESS_TOKEN_PATH = "cm-access-token/{cmId}"
    private val GENERATE_USER_ACCESS_TOKEN_PATH = "user-access-token/{userId}"
    private val GENERATE_CM_SESSION_TOKEN_PATH = "cm-session-token/{cmId}"
    private val GENERATE_USER_SESSION_TOKEN_PATH = "user-session-token/{userId}"
    private val GET_CM_PATH = "get-cm/{cmId}"
    private val GET_USER_PATH = "get-user/{userId}"
    private val ENABLE_CM_PATH = "enable-cm/{cmId}"
    private val ENABLE_USER_PATH = "enable-user/{userId}"
    private val DISABLE_CM_PATH = "disable-cm/{cmId}"
    private val DISABLE_USER_PATH = "disable-user/{userId}"
    private val SIGN_OUT_PATH = "sign-out"

    private val JWT_TOKEN_PREAMBLE = "Bearer "
}