package com.dasbikash.csclientbe.services

import com.dasbikash.csclientbe.exceptions.SignupException
import com.dasbikash.csclientbe.model.db.CustomerManager
import com.dasbikash.csclientbe.model.db.User
import com.dasbikash.csclientbe.model.response.SuccessResponse
import com.dasbikash.csclientbe.repos.CustomerManagerRepository
import com.dasbikash.csclientbe.repos.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import javax.validation.Valid

@Service
class AuthService(
        private var customerManagerRepository: CustomerManagerRepository?=null,
        private var userRepository: UserRepository?=null,
        private var passwordEncoder: PasswordEncoder?=null,
        private var userDetailsService: CsClientUserDetailsService?=null
) {
    fun cmSignUp(@Valid customerManager: CustomerManager): SuccessResponse {
        checkIfUserIdTaken(customerManager.userId)
        customerManager.password = passwordEncoder!!.encode(customerManager.password)
        customerManagerRepository!!.save(customerManager)
        return SuccessResponse(CM_SIGN_SUCCESS_MESSAGE)
    }

    fun userSignUp(@Valid user: User): SuccessResponse {
        checkIfUserIdTaken(user.userId)
        user.password = passwordEncoder!!.encode(user.password)
        userRepository!!.save(user)
        return SuccessResponse(USER_SIGN_SUCCESS_MESSAGE)
    }

    private fun checkIfUserIdTaken(userId:String?){
        userDetailsService!!.loadUserByUsername(userId)?.let {
            throw SignupException(DUPLICATE_USER_ID_MESSAGE)
        }
    }

    companion object{
        private const val CM_SIGN_SUCCESS_MESSAGE = "Customer manger sign up success."
        private const val USER_SIGN_SUCCESS_MESSAGE = "User sign up success."
        private const val DUPLICATE_USER_ID_MESSAGE = "User id already taken!!"
    }
}