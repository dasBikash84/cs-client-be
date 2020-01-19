package com.dasbikash.csclientbe.filters

import com.dasbikash.csclientbe.model.response.ErrorResponse
import com.dasbikash.csclientbe.services.ErrorLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.security.access.AccessDeniedException
import org.springframework.security.web.access.AccessDeniedHandler
import org.springframework.stereotype.Component
import java.io.IOException
import javax.servlet.ServletException
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

@Component
open class RestAccessDeniedHandler @Autowired constructor(
        private val errorLogService: ErrorLogService
) : AccessDeniedHandler {
    @Throws(IOException::class, ServletException::class)
    override fun handle(httpServletRequest: HttpServletRequest,
                        httpServletResponse: HttpServletResponse,
                        ex: AccessDeniedException) {
        ex.printStackTrace()
        errorLogService.logError(httpServletRequest,ex)
        httpServletResponse.sendError(HttpStatus.BAD_REQUEST.value(), ErrorResponse.getAuthErrorResponse().toString())
    }
}