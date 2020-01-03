package com.dasbikash.csclientbe.controllers

import com.dasbikash.csclientbe.config.ContextPathUtils
import com.dasbikash.csclientbe.model.db.CustomerManager
import com.dasbikash.csclientbe.services.CustomerManagerService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.servlet.http.HttpServletRequest

@RestController
@RequestMapping(path = [ContextPathUtils.CM_CONTROLLER_BASE_PATH])
class CmController(
        open var customerManagerService: CustomerManagerService?=null
) {
    @GetMapping
    fun getDetails(@Autowired request: HttpServletRequest):
            ResponseEntity<CustomerManager> {
        return ResponseEntity.ok(customerManagerService!!.getDetails(request))
    }
}