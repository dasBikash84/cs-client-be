package com.dasbikash.csclientbe.controllers

import com.dasbikash.csclientbe.config.ContextPathUtils
import com.dasbikash.csclientbe.model.db.User
import com.dasbikash.csclientbe.services.UserService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = [ContextPathUtils.USER_CONTROLLER_BASE_PATH])
class UserController(
        open var userService: UserService?=null
) {
    @GetMapping
    fun getDetails(@Autowired request: HttpServletRequest):
            ResponseEntity<User> {
        return ResponseEntity.ok(userService!!.getDetails(request))
    }
}