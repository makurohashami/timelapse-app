package com.kotyk.timelapse.util


import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

class DateUtil {

    final static String APP_TIME_FORMAT = "yyMMdd-HHmmss-SSS"

    static String toFormattedTime(LocalDateTime time) {
        time.format(DateTimeFormatter.ofPattern(APP_TIME_FORMAT))
    }

}
