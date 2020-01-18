package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import com.dasbikash.csclientbe.model.db.User
import com.dasbikash.csclientbe.model.request.CsTokenReqResponse
import com.dasbikash.csclientbe.repos.UserRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import javax.servlet.http.HttpServletRequest

@Service
open class UserService @Autowired constructor(
        private val userRepository: UserRepository,
        private val adminTaskService: AdminTaskService,
        private val basicAuthService: BasicAuthService

) {
    fun generateAccessToken(request: HttpServletRequest): CsTokenReqResponse {
        basicAuthService.getAuthRequest(request)!!.apply {
            userRepository!!.findById(id).orElseThrow { CsClientAuthenticationException() }.apply {
                if (isCustomerManager){
                    return adminTaskService!!.generateCustomerManagerAccessToken(this)
                }else{
                    return adminTaskService!!.generateEndUserAccessToken(this)
                }
            }
        }
    }
    fun generateSessionToken(request: HttpServletRequest): CsTokenReqResponse {
        basicAuthService.getAuthRequest(request)!!.apply {
            userRepository!!.findById(id).orElseThrow { CsClientAuthenticationException() }.apply {
                if (isCustomerManager){
                    return adminTaskService!!.generateCustomerManagerSessionToken(this)
                }else{
                    return adminTaskService!!.generateEndUserSessionToken(this)
                }
            }
        }
    }

    fun getUserDetails(id: String): User {
        return userRepository.findById(id).orElseThrow { IllegalArgumentException() }
    }
}