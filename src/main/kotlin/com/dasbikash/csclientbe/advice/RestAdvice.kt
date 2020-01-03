package com.dasbikash.csclientbe.advice

import com.dasbikash.csclientbe.exceptions.CsClientAuthenticationException
import com.dasbikash.csclientbe.exceptions.SignupException
import com.dasbikash.csclientbe.model.response.ErrorResponse
import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.ExceptionHandler
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestControllerAdvice


@RestControllerAdvice
open class RestAdvice {

    private val INVALID_SIGN_UP_MESSAGE = "Invalid sign up parameters!!"
    private val INVALID_PARAM_MESSAGE = "Invalid parameters!!"
    private val DUPLICATE_EMAIL_MESSAGE = "Duplicate email address!!"
    private val BAD_AUTH_MESSAGE = "Invalid user name or password!!"
    private val NO_DATA_MESSAGE = "Data not found!!"

    @ExceptionHandler(SignupException::class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    fun signupExceptionHandler(ex: SignupException): ErrorResponse {
        return ErrorResponse.getSignUpErrorResponse(ex.message ?: INVALID_SIGN_UP_MESSAGE)
    }

    @ExceptionHandler(CsClientAuthenticationException::class)
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    fun authenticationExceptionHandler(ex: CsClientAuthenticationException): ErrorResponse {
        return ErrorResponse.getAuthErrorResponse(ex.message ?: BAD_AUTH_MESSAGE)
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException)
            : ErrorResponse {
        return ErrorResponse.getInvalidParamErrorResponse(ex.message ?: INVALID_PARAM_MESSAGE)
    }
}