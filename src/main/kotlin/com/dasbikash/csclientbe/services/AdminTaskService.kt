package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import com.dasbikash.csclientbe.exceptions.CsNotAvailableException
import com.dasbikash.csclientbe.model.db.User
import com.dasbikash.csclientbe.model.request.CsTokenReqResponse
import com.dasbikash.csclientbe.model.request.CsUserRegisterRequest
import com.dasbikash.csclientbe.model.response.CsSuccessResponse
import com.dasbikash.csclientbe.repos.UserRepository
import com.dasbikash.csclientbe.utils.AuthenticationRequest
import com.dasbikash.csclientbe.utils.BasicAuthUtils
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.HttpEntity
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpMethod
import org.springframework.http.MediaType
import org.springframework.stereotype.Service
import org.springframework.web.client.RestTemplate


@Service
open class AdminTaskService @Autowired constructor(
        private val userRepository: UserRepository
) {
    @Value("\${cs-admin.path}")
    private var CHAT_SERVICE_BASE_PATH:String = ""
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

    private val CS_ADMIN_USER_ID = "client001"
    private val CS_ADMIN_USER_PASSWORD = "1234"

    private val JWT_TOKEN_PREAMBLE = "Bearer "

    private var csJwtAccessToken:String? = null

    private fun getBasicAuthHeader():String{
        return BasicAuthUtils.getBasicAuthHeader(AuthenticationRequest(CS_ADMIN_USER_ID, CS_ADMIN_USER_PASSWORD))
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
        val entity = HttpEntity<Any>(csUserRegisterRequest, getJwtHeader())
        val restTemplate = RestTemplate()
        if (user.isCustomerManager) {
            return restTemplate.exchange(ADMIN_BASE_PATH + REGISTER_CM_PATH, HttpMethod.PUT, entity, CsSuccessResponse::class.java).body
        }else{
            return restTemplate.exchange(ADMIN_BASE_PATH + REGISTER_USER_PATH, HttpMethod.PUT, entity, CsSuccessResponse::class.java).body
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
            val entity = HttpEntity<Any>(getJwtHeader())
            val restTemplate = RestTemplate()
            return restTemplate.exchange(ADMIN_BASE_PATH + path,
                                            HttpMethod.GET, entity,
                                            CsTokenReqResponse::class.java,user.userId!!).body!!
        }else{
            throw CsClientAuthenticationException()
        }
    }
}