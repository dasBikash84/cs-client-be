package com.dasbikash.csclientbe.utils

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class BgTaskUtils {
    companion object {
        fun launchContinuousBgTask(task: () -> Unit, taskName:String ,initExDelay: Long = DateUtils.MINUTE_IN_MS) {
            var exceptionCount = 0
            GlobalScope.launch(Dispatchers.IO) {
                do {
                    try {
                        println("############################\n\nStarting \"$taskName\" continuous back-ground task\n\n############################")
                        task()
                        exceptionCount = 0
                    } catch (ex: Throwable) {
                        ex.printStackTrace()
                        exceptionCount++
                        delay(exceptionCount * exceptionCount * initExDelay)
                    }
                } while (true)
            }
        }
    }
}