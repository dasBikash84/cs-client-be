package com.dasbikash.csclientbe.model.db

import java.util.*
import javax.persistence.*

@Entity
class ErrorLog(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        var id: Long? = null,
        var userIpAddress: String = "",
        var userId: String = "",
        @Column(insertable = false,updatable = false)
        var created: Date?=null
) {
        @Column(columnDefinition = "text")
        var stackTrace: String = ""
        var message: String = ""
        var exceptionClass: String = ""
        override fun toString(): String {
                return "ErrorLog(id=$id, userIpAddress='$userIpAddress', userId='$userId', message='$message', exceptionClass='$exceptionClass')"
        }

        fun addException(exception: Throwable):ErrorLog{
                exception.let {
//                        it.printStackTrace()
                        this.exceptionClass = it.javaClass.canonicalName
                        this.message = it.getFullMessage()
                        this.stackTrace = it.getFullStackTrace()
                        println(this)
                }
                return this
        }
}

fun Throwable.getFullStackTrace():String{
        val traceBuilder = StringBuilder("")
        var exp:Throwable? = this
        do {
                exp?.stackTrace?.asSequence()?.forEach {
                        traceBuilder.append("${it} \n")
                }
                exp = exp?.cause
        }while (exp !=null)
        return traceBuilder.toString()
}

fun Throwable.getFullMessage():String{
        val messageBuilder = StringBuilder("")
        var exp:Throwable? = this
        do {
                exp?.message?.let {
                        messageBuilder.append("${it} | ")
                }
                exp = exp?.cause
        }while (exp !=null)
        if (messageBuilder.isNotBlank()) {
                return messageBuilder.substring(0, messageBuilder.length-3)
        }
        return messageBuilder.toString()
}