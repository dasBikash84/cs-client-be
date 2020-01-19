package com.dasbikash.csclientbe.filters

import com.dasbikash.csclientbe.model.response.ErrorResponse
import com.dasbikash.csclientbe.services.ErrorLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.AuthenticationEntryPoint
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
open class RestAuthenticationEntryPoint @Autowired constructor(
        private val errorLogService: ErrorLogService
) : AuthenticationEntryPoint {
    @Throws(IOException::class, ServletException::class)
    override fun commence(httpServletRequest: HttpServletRequest,
                          httpServletResponse: HttpServletResponse,
                          ex: AuthenticationException) {
        ex.printStackTrace()
        errorLogService.logError(httpServletRequest,ex)
        httpServletResponse.sendError(HttpStatus.FORBIDDEN.value(), ErrorResponse.getAuthErrorResponse().toString())
    }
}