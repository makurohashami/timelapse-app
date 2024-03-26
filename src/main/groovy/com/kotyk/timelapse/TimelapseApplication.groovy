package com.kotyk.timelapse

import com.kotyk.timelapse.configuration.GlobalSettings
import com.kotyk.timelapse.service.VideoService
import com.kotyk.timelapse.service.WebCamService
import com.kotyk.timelapse.util.FileUtil

class TimelapseApplication {

    static void main(String[] args) {
        FileUtil.createFolders(GlobalSettings.pathToPhotos)
        WebCamService.takePhotosAsync(GlobalSettings.pathToPhotos)
        VideoService.makeVideoFromPhotos(GlobalSettings.pathToPhotos, GlobalSettings.pathToTimelapse)
    }

}
