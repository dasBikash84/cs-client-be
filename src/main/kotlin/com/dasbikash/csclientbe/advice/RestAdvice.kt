package com.dasbikash.csclientbe.advice

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import com.dasbikash.csclientbe.exceptions.CsNotAvailableException
import com.dasbikash.csclientbe.exceptions.SignupException
import com.dasbikash.csclientbe.model.response.ErrorResponse
import com.dasbikash.csclientbe.services.ErrorLogService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice
import java.lang.IllegalArgumentException
import javax.servlet.http.HttpServletRequest


@RestControllerAdvice
open class RestAdvice  @Autowired constructor(
        private val errorLogService: ErrorLogService
) {

    private val INVALID_SIGN_UP_MESSAGE = "Invalid sign up parameters!!"
    private val INVALID_PARAM_MESSAGE = "Invalid parameters!!"
    private val DUPLICATE_EMAIL_MESSAGE = "Duplicate email address!!"
    private val BAD_AUTH_MESSAGE = "Invalid user name or password!!"
    private val NO_DATA_MESSAGE = "Data not found!!"
    private val CS_UNAVAILABLE_MESSAGE = "Chat service not available!!"

    @ExceptionHandler(SignupException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun signupExceptionHandler(httpServletRequest: HttpServletRequest, ex: SignupException): ErrorResponse {
        logError(httpServletRequest, ex)
        return ErrorResponse.getSignUpErrorResponse(ex.message ?: INVALID_SIGN_UP_MESSAGE)
    }

    @ExceptionHandler(CsClientAuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun authenticationExceptionHandler(httpServletRequest: HttpServletRequest, ex: CsClientAuthenticationException): ErrorResponse {
        logError(httpServletRequest, ex)
        return ErrorResponse.getAuthErrorResponse(ex.message ?: BAD_AUTH_MESSAGE)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(httpServletRequest: HttpServletRequest, ex: DataIntegrityViolationException)
            : ErrorResponse {
        logError(httpServletRequest, ex)
        return ErrorResponse.getInvalidParamErrorResponse(ex.message ?: INVALID_PARAM_MESSAGE)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(CsNotAvailableException::class)
    fun handleCsNotAvailableException(httpServletRequest: HttpServletRequest, ex: CsNotAvailableException)
            : ErrorResponse {
        logError(httpServletRequest, ex)
        return ErrorResponse.getCsNotAvailableErrorResponse(ex.message ?: CS_UNAVAILABLE_MESSAGE)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(httpServletRequest: HttpServletRequest, ex: IllegalArgumentException)
            : ErrorResponse {
        logError(httpServletRequest, ex)
        return ErrorResponse.getIllegalArgumentExceptionErrorResponse(ex.message ?: INVALID_PARAM_MESSAGE)
    }

    private fun logError(httpServletRequest: HttpServletRequest, ex:Throwable){
        ex.printStackTrace()
        errorLogService.logError(httpServletRequest,ex)
    }
}