package com.dasbikash.csclientbe.controllers

import com.dasbikash.csclientbe.config.ContextPathUtils
import com.dasbikash.csclientbe.model.db.User
import com.dasbikash.csclientbe.model.response.SuccessResponse
import com.dasbikash.csclientbe.services.AuthService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PutMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = [ContextPathUtils.AUTH_CONTROLLER_BASE_PATH])
class AuthController(
        private var authService: AuthService?=null
) {
    @PutMapping(CM_SIGN_UP_PATH)
    fun cmSignUp(@RequestBody user: User,@Autowired request:HttpServletRequest):
            ResponseEntity<SuccessResponse>{
        return ResponseEntity.ok(authService!!.userSignUp(user,isEndUser = false))
    }
    @PutMapping(USER_SIGN_UP_PATH)
    fun userSignUp(@RequestBody user: User,@Autowired request:HttpServletRequest):
            ResponseEntity<SuccessResponse>{
        return ResponseEntity.ok(authService!!.userSignUp(user,isEndUser = true))
    }

    companion object{
        private const val CM_SIGN_UP_PATH = "cm-sign-up"
        private const val USER_SIGN_UP_PATH = "user-sign-up"
    }
}