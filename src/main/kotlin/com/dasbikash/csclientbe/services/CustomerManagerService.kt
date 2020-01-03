package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import com.dasbikash.csclientbe.model.db.CustomerManager
import com.dasbikash.csclientbe.repos.CustomerManagerRepository
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
class CustomerManagerService(
        open var basicAuthService: BasicAuthService?=null,
        open var customerManagerRepository: CustomerManagerRepository?=null
) {
    fun getDetails(request: HttpServletRequest): CustomerManager {
        basicAuthService!!.getAuthRequest(request)!!.apply {
            return customerManagerRepository!!.findById(id).orElseThrow { CsClientAuthenticationException() }
        }
    }
}