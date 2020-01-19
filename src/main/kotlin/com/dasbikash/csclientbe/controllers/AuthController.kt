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
class AuthController @Autowired constructor(
        private val authService: AuthService
) {
    @PutMapping(USER_SIGN_UP_PATH)
    fun userSignUp(@RequestBody user: User,@Autowired request:HttpServletRequest):
            ResponseEntity<SuccessResponse>{
        return ResponseEntity.ok(authService.userSignUp(user))
    }

    companion object{
        private const val USER_SIGN_UP_PATH = "user-sign-up"
    }
}