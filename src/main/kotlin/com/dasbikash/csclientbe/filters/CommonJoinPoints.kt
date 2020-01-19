package com.dasbikash.csclientbe.filters

import org.aspectj.lang.annotation.Pointcut

class CommonJoinPoints {

    @Pointcut("@annotation(com.dasbikash.csclientbe.filters.annotations.BasicAuthProtected)")
    fun basicAuthProtectedEndPoints() {}

    @Pointcut("execution(* com.dasbikash.csclientbe.controllers..*(..))")
    fun allControllers() {}
}
