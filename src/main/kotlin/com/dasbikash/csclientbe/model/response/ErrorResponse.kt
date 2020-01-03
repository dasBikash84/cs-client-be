package com.dasbikash.csclientbe.model.response

class ErrorResponse private constructor(
        val errorCode:String,
        val errorMessage:String?=null
){

    companion object{
        private const val AUTH_ERROR_CODE = "CS_CL_ER001"
        private const val SIGN_UP_ERROR_CODE = "CS_CL_ER002"
        private const val INVALID_PARAM_ERROR_CODE = "CS_CL_ER002"
        private const val CS_UNAVAILABLE_ERROR_CODE = "CS_CL_ER003"
        private const val ILLEGAL_ARGUMENT_ERROR_CODE = "CS_CL_ER004"

        fun getAuthErrorResponse(message:String?=null) =
                ErrorResponse(AUTH_ERROR_CODE,message)

        fun getSignUpErrorResponse(message:String?=null) =
                ErrorResponse(SIGN_UP_ERROR_CODE,message)

        fun getInvalidParamErrorResponse(message:String?=null) =
                ErrorResponse(INVALID_PARAM_ERROR_CODE,message)

        fun getCsNotAvailableErrorResponse(message:String?=null) =
                ErrorResponse(CS_UNAVAILABLE_ERROR_CODE,message)

        fun getIllegalArgumentExceptionErrorResponse(message:String?=null) =
                ErrorResponse(ILLEGAL_ARGUMENT_ERROR_CODE,message)
    }

    override fun toString(): String {
        return "ErrorResponse(errorCode='$errorCode', errorMessage=$errorMessage)"
    }
}