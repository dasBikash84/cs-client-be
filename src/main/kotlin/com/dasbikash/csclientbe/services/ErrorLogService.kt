package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.model.db.ErrorLog
import com.dasbikash.csclientbe.repos.ErrorLogRepo
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import javax.servlet.http.HttpServletRequest

@Service
open class ErrorLogService @Autowired constructor(
        private val errorLogRepo: ErrorLogRepo,
        private val basicAuthService: BasicAuthService
) {
    fun logError(request: HttpServletRequest,exception: Throwable): ErrorLog {
        val errorLog = ErrorLog(userIpAddress = request.remoteHost).addException(exception)
        basicAuthService.getAuthRequest(request)?.let {
                errorLog.userId = it.id
        }
        return errorLogRepo.save(errorLog)
    }
}
