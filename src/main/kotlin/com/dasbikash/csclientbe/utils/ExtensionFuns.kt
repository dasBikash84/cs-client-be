package com.dasbikash.csclientbe.utils


fun Any.consoleLog(message:Any?){
    println("${DateUtils.getLongDateStringForNow()} | from ${this.javaClass.simpleName}:  $message")
}