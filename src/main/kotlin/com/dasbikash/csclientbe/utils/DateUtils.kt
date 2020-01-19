package com.dasbikash.csclientbe.utils

import java.text.SimpleDateFormat
import java.util.*

object DateUtils {
    const val DAY_IN_MS: Long = 24 * 60 * 60 * 1000L
    const val HOUR_IN_MS: Long = 60 * 60 * 1000L
    const val MINUTE_IN_MS: Long = 60 * 1000L
    const val SECOND_IN_MS: Long = 1000L
    private const val FULL_DATE_STRING_FORMAT = "dd MMM yyyy HH:mm:ss"
    private const val SHORT_DATE_STRING_FORMAT = "dd MMM yyyy"
    private const val TIME_STRING_FORMAT = "hh:mm:ss a"

    fun getLongDateStringForNow() = getLongDateString(Date())

    fun getLongDateString(date: Date):String =
            getDateString(
                    FULL_DATE_STRING_FORMAT,
                    date
            )

    private fun getDateString(format:String,date: Date) =
            SimpleDateFormat(format, Locale.ENGLISH).format(date)

    fun getShortDateString(date: Date):String =
            getDateString(
                    SHORT_DATE_STRING_FORMAT,
                    date
            )

    fun getTimeString(date: Date):String =
            getDateString(
                    TIME_STRING_FORMAT,
                    date
            )
}