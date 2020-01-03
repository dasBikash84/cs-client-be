package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import com.dasbikash.csclientbe.model.db.User
import com.dasbikash.csclientbe.repos.UserRepository
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class UserService(
        open var basicAuthService: BasicAuthService?=null,
        open var userRepository: UserRepository?=null
) {
    fun getEndUserDetails(request: HttpServletRequest): User {
        basicAuthService!!.getAuthRequest(request)!!.apply {
            userRepository!!.findById(id).orElseThrow { CsClientAuthenticationException() }.apply {
                if (isEndUser){
                    return this
                }else{
                    throw CsClientAuthenticationException()
                }
            }
        }
    }

    fun getCmDetails(request: HttpServletRequest): User {
        basicAuthService!!.getAuthRequest(request)!!.apply {
            userRepository!!.findById(id).orElseThrow { CsClientAuthenticationException() }.apply {
                if (isCustomerManager){
                    return this
                }else{
                    throw CsClientAuthenticationException()
                }
            }
        }
    }
}