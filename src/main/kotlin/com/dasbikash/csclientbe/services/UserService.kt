package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import com.dasbikash.csclientbe.model.request.CsTokenReqResponse
import com.dasbikash.csclientbe.repos.UserRepository
import com.dasbikash.csclientbe.utils.BasicAuthUtils
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class UserService(
        private var userRepository: UserRepository?=null,
        private var adminTaskService: AdminTaskService?=null

) {
    fun generateAccessToken(request: HttpServletRequest): CsTokenReqResponse {
        BasicAuthUtils.getAuthRequest(request)!!.apply {
            userRepository!!.findById(id).orElseThrow { CsClientAuthenticationException() }.apply {
                if (isCustomerManager){
                    return adminTaskService!!.generateCustomerManagerAccessToken(this)
                }else{
                    return adminTaskService!!.generateEndUserAccessToken(this)
                }
            }
        }
    }
}