package com.dasbikash.csclientbe.filters


import com.dasbikash.csclientbe.services.BasicAuthService
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.servlet.http.HttpServletRequest

@Configuration
@Aspect
open class AspectService @Autowired constructor(
        private val basicAuthService: BasicAuthService
) {

    open var logger = LoggerFactory.getLogger(this.javaClass)

    @Before("CommonJoinPoints.basicAuthProtectedEndPoints() && args(..,request)")
    @Throws(Throwable::class)
    fun basicAuthProtectedFilter(joinPoint: JoinPoint, request: HttpServletRequest){
        basicAuthService.authenticateUser(request)
    }
}