package com.dasbikash.csclientbe.controllers

import com.dasbikash.csclientbe.config.ContextPathUtils
import com.dasbikash.csclientbe.exceptions.CsNotAvailableException
import com.dasbikash.csclientbe.model.request.CsTokenReqResponse
import com.dasbikash.csclientbe.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = [ContextPathUtils.USER_CONTROLLER_BASE_PATH])
open class UserController @Autowired constructor(
        private val userService: UserService
) {
    @GetMapping("generate-access-token")
    open fun getAccessToken(@Autowired request: HttpServletRequest):ResponseEntity<CsTokenReqResponse>{
        return ResponseEntity.ok(userService.generateAccessToken(request))
    }
    @GetMapping("generate-session-token")
    open fun getSessionToken(@Autowired request: HttpServletRequest):ResponseEntity<CsTokenReqResponse>{
        try {
            return ResponseEntity.ok(userService.generateSessionToken(request))
        }catch (ex:Throwable){
            ex.printStackTrace()
            throw CsNotAvailableException()
        }
    }
}