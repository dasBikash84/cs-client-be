package com.dasbikash.csclientbe.model.db

import org.aspectj.lang.JoinPoint
import java.util.*
import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.GenerationType
import javax.persistence.Id
import javax.servlet.http.HttpServletRequest

@Entity
class RestActivityLog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id:Int?=null,
        val requestURL:String?=null,
        val requestMethod:String?=null,
        val remoteHost:String?=null,
        val methodSignature:String?=null,
        val timeTakenMs:Int?=null,
        val userId:String?=null,
        val exceptionClassName:String?=null,
        val acceptHeader:String?=null,
        val userAgentHeader:String?=null
){
    companion object{
        fun getInstance(joinPoint: JoinPoint, request: HttpServletRequest, timeTakenMs: Int,
                        exceptionClassFullName: String?=null, userId: String?=null,
                        acceptHeader: String?=null,userAgentHeader: String?=null)
                : RestActivityLog {
            return RestActivityLog(requestURL = request.requestURL.toString(), methodSignature = joinPoint.signature.toString(),
                    requestMethod = request.method, remoteHost = request.remoteHost, timeTakenMs = timeTakenMs,
                    exceptionClassName = exceptionClassFullName, userId = userId,
                    acceptHeader = acceptHeader, userAgentHeader = userAgentHeader)
        }
    }

    override fun toString(): String {
        return "RestActivityLog(requestURL='$requestURL', requestMethod='$requestMethod', remoteHost='$remoteHost', methodSignature='$methodSignature', timeTakenMs=$timeTakenMs, userId=$userId, exceptionClassName=$exceptionClassName)"
    }

}