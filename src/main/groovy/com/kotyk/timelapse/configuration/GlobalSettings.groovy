package com.kotyk.timelapse.configuration

import com.kotyk.timelapse.enumeration.Framerate

import java.util.concurrent.TimeUnit

import static com.kotyk.timelapse.util.DateUtil.toFormattedTime
import static java.time.LocalDateTime.now

class GlobalSettings {

    // Paths settings
    final static String pathToSave = System.getenv("HOME")
    final static String pathToTimelapse = "$pathToSave/timelapse-${toFormattedTime(now())}/"
    final static String pathToPhotos = "${pathToTimelapse}photos/"

    // Timelapse settings
    final static int webcamId = 0
    final static long period = TimeUnit.SECONDS.toMillis(5)
    final static long duration = TimeUnit.HOURS.toMillis(2)

    // Video settings
    final static String photosExtension = ".jpg"
    final static String filename = "timelapse.mp4"
    final static String codec = "h.264"
    final static int bitrate = 10000
    final static Framerate frameRate = Framerate.TWENTY_FOUR
    final static int imageWidth = 1280
    final static int imageHeight = 720

}
