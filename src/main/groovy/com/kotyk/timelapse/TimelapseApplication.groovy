package com.kotyk.timelapse

import com.kotyk.timelapse.service.VideoService
import com.kotyk.timelapse.service.WebCamService
import com.kotyk.timelapse.util.FileUtil
import groovy.util.logging.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

import static com.kotyk.timelapse.util.DateUtil.toFormattedTime
import static java.time.LocalDateTime.now

@Slf4j
@SpringBootApplication
class TimelapseApplication implements CommandLineRunner {

    private static final String pathToSave = System.getenv("HOME")
    private static final String pathToTimelapse = "$pathToSave/timelapse-${toFormattedTime(now())}/"
    private static final String pathToPhotos = pathToTimelapse + "photos/"

    static void main(String[] args) {
        SpringApplication.run(TimelapseApplication, args)
    }

    @Override
    void run(String... args) throws Exception {
        FileUtil.createFolders(pathToPhotos)
        WebCamService.takePhotosAsync(pathToPhotos)
        VideoService.makeVideoFromPhotos(pathToPhotos, pathToTimelapse)
    }
}
