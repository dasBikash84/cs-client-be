package com.dasbikash.csclientbe.model.response

class ErrorResponse private constructor(
        val errorCode:String,
        val errorMessage:String?=null
){

    companion object{
        private const val AUTH_ERROR_CODE = "CS_CL_ER001"
        private const val SIGN_UP_ERROR_CODE = "CS_CL_ER002"
        private const val INVALID_PARAM_ERROR_CODE = "CS_CL_ER002"

        fun getAuthErrorResponse(message:String?=null) =
                ErrorResponse(AUTH_ERROR_CODE,message)

        fun getSignUpErrorResponse(message:String?=null) =
                ErrorResponse(SIGN_UP_ERROR_CODE,message)

        fun getInvalidParamErrorResponse(message:String?=null) =
                ErrorResponse(INVALID_PARAM_ERROR_CODE,message)
    }

    override fun toString(): String {
        return "ErrorResponse(errorCode='$errorCode', errorMessage=$errorMessage)"
    }
}