package com.kotyk.timelapse.service

import com.kotyk.timelapse.enumeration.Framerate
import com.kotyk.timelapse.util.FileUtil
import groovy.util.logging.Slf4j
import org.apache.commons.io.FileUtils
import org.bytedeco.javacv.FFmpegFrameRecorder
import org.bytedeco.javacv.Java2DFrameConverter

import javax.imageio.ImageIO

@Slf4j
class VideoService {

    private final static String photosExtension = ".jpg"
    private final static String filename = "timelapse.mp4"
    private final static String codec = "h.264"
    private final static int bitrate = 10000
    private final static Framerate frameRate = Framerate.TWENTY_FOUR
    private final static int imageWidth = 1280
    private final static int imageHeight = 720

    static void makeVideoFromPhotos(String pathToPhotos, String pathToSave) {
        log.info("Start making video from photos for path: $pathToPhotos")
        pathToSave = FileUtil.toRightPath(pathToSave)
        pathToPhotos = FileUtil.toRightPath(pathToPhotos)
        File[] images = new File(pathToPhotos).listFiles({ File file -> file.name.toLowerCase().endsWith(photosExtension) } as FileFilter)
        if (!images || images.length == 0) {
            throw new RuntimeException("No $photosExtension files found in the folder: '$pathToPhotos'")
        }
        log.info("Photos count: ${images.length}")
        Arrays.sort(images, Comparator.comparingLong(file -> FileUtils.getFile(file as String).lastModified()))
        String pathToVideo = "${pathToSave}${filename}"
        try (
                FFmpegFrameRecorder recorder = new FFmpegFrameRecorder(pathToVideo, imageWidth, imageHeight)
                Java2DFrameConverter converter = new Java2DFrameConverter()
        ) {
            recorder.setVideoCodecName(codec)
            recorder.setVideoBitrate(bitrate)
            recorder.setFrameRate(frameRate.value)
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
