package com.dasbikash.csclientbe.filters


import com.dasbikash.csclientbe.model.db.RestActivityLog
import com.dasbikash.csclientbe.repos.RestActivityLogRepository
import com.dasbikash.csclientbe.services.BasicAuthService
import com.dasbikash.csclientbe.utils.consoleLog
import org.aspectj.lang.JoinPoint
import org.aspectj.lang.ProceedingJoinPoint
import org.aspectj.lang.annotation.Around
import org.aspectj.lang.annotation.Aspect
import org.aspectj.lang.annotation.Before
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Configuration
import javax.servlet.http.HttpServletRequest

@Configuration
@Aspect
open class AspectService @Autowired constructor(
        private val restActivityLogRepository: RestActivityLogRepository,
        private val basicAuthService: BasicAuthService
) {

    open var logger = LoggerFactory.getLogger(this.javaClass)

    @Before("CommonJoinPoints.basicAuthProtectedEndPoints() && args(..,request)")
    @Throws(Throwable::class)
    fun basicAuthProtectedFilter(joinPoint: JoinPoint, request: HttpServletRequest){
        basicAuthService.authenticateUser(request)
    }

    @Around("CommonJoinPoints.allControllers() && args(..,request)")
    @Throws(Throwable::class)
    fun requestDetailsLogger(proceedingJoinPoint: ProceedingJoinPoint, request: HttpServletRequest):Any {

        val startTime = System.currentTimeMillis()
        var result:Any?=null
        var exception:Exception?=null
        var userId: String?=null
        var acceptHeader:String?=null
        var userAgentHeader:String?=null


        basicAuthService.getAuthRequest(request)?.let {
                userId = it.id
        }

        try {
            result = proceedingJoinPoint.proceed()
        }catch (ex:Exception){
            exception=ex
        }

        request.headerNames.asSequence().find { it.equals("accept") }?.let { acceptHeader= request.getHeader(it)}
        request.headerNames.asSequence().find { it.equals("user-agent") }?.let { userAgentHeader= request.getHeader(it) }
        val restActivityLog = RestActivityLog.getInstance(
                proceedingJoinPoint,request,(System.currentTimeMillis() - startTime).toInt(),
                exception?.let { it::class.java.canonicalName} ,userId,
                acceptHeader, userAgentHeader)

        restActivityLogRepository.save(restActivityLog)
        consoleLog(restActivityLog.toString())

        exception?.let {
            throw it
        }
        return result!!
    }
}