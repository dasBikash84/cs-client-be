package com.dasbikash.csclientbe.controllers

import com.dasbikash.csclientbe.config.ContextPathUtils
import com.dasbikash.csclientbe.model.db.User
import com.dasbikash.csclientbe.model.request.CsTokenReqResponse
import com.dasbikash.csclientbe.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = [ContextPathUtils.USER_CONTROLLER_BASE_PATH])
class UserController(
        open var userService: UserService?=null
) {
    @GetMapping("generate-access-token")
    fun getAccessToken(@Autowired request: HttpServletRequest):ResponseEntity<CsTokenReqResponse>{
        return ResponseEntity.ok(userService!!.generateAccessToken(request))
    }
    @GetMapping("generate-session-token")
    fun getSessionToken(@Autowired request: HttpServletRequest):ResponseEntity<CsTokenReqResponse>{
        return ResponseEntity.ok(userService!!.generateSessionToken(request))
    }

    @GetMapping("user-details/{id}")
    fun getUserDetails(@PathVariable("id") id:String,@Autowired request: HttpServletRequest)
        :ResponseEntity<User>{
        return ResponseEntity.ok(userService!!.getUserDetails(id))
    }
}