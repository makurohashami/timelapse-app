package com.kotyk.timelapse

import com.kotyk.timelapse.configuration.GlobalSettings
import com.kotyk.timelapse.service.VideoService
import com.kotyk.timelapse.service.WebCamService
import com.kotyk.timelapse.util.FileUtil
import groovy.util.logging.Slf4j
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

@Slf4j
@SpringBootApplication
class TimelapseApplication implements CommandLineRunner {

    static void main(String[] args) {
        SpringApplication.run(TimelapseApplication, args)
    }

    @Override
    void run(String... args) throws Exception {
        FileUtil.createFolders(GlobalSettings.pathToPhotos)
        WebCamService.takePhotosAsync(GlobalSettings.pathToPhotos)
        VideoService.makeVideoFromPhotos(GlobalSettings.pathToPhotos, GlobalSettings.pathToTimelapse)
    }
}
