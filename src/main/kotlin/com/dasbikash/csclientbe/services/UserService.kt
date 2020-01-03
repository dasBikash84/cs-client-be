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
    fun getDetails(request: HttpServletRequest): User {
        basicAuthService!!.getAuthRequest(request)!!.apply {
            return userRepository!!.findById(id).orElseThrow { CsClientAuthenticationException() }
        }
    }
}