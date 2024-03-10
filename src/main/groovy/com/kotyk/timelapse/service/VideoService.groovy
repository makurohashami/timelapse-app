package com.kotyk.timelapse.service

import com.kotyk.timelapse.configuration.GlobalSettings
import com.kotyk.timelapse.util.FileUtil
import groovy.util.logging.Slf4j
import org.apache.commons.io.FileUtils
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Java2DFrameConverter

import javax.imageio.ImageIO

@Slf4j
class VideoService {

    static void makeVideoFromPhotos(String pathToPhotos, String pathToSave) {
        log.info("Start making video from photos for path: $pathToPhotos")
        pathToSave = FileUtil.toRightPath(pathToSave)
        pathToPhotos = FileUtil.toRightPath(pathToPhotos)
        File[] images = new File(pathToPhotos).listFiles({ File file -> file.name.toLowerCase().endsWith(GlobalSettings.photosExtension) } as FileFilter)
        if (!images || images.length == 0) {
            throw new RuntimeException("No $GlobalSettings.photosExtension files found in the folder: '$pathToPhotos'")
        }
        log.info("Photos count: ${images.length}")
        Arrays.sort(images, Comparator.comparingLong(file -> FileUtils.getFile(file as String).lastModified()))
        String pathToVideo = "${pathToSave}${GlobalSettings.filename}"
        try (
                FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(pathToVideo, GlobalSettings.imageWidth, GlobalSettings.imageHeight)
                Java2DFrameConverter converter = new Java2DFrameConverter()
        ) {
            recorder.setVideoCodecName(GlobalSettings.codec)
            recorder.setVideoBitrate(GlobalSettings.bitrate)
            recorder.setFrameRate(GlobalSettings.frameRate.value)
            recorder.start()
            for (File image : images) {
                recorder.record(converter.convert(ImageIO.read(image)))
            }
            recorder.stop()
            log.info("Making video from photos successfully ended. See timelapse in: $pathToVideo")
        } catch (IOException e) {
            throw new RuntimeException(e.getMessage())
        }
    }

}
