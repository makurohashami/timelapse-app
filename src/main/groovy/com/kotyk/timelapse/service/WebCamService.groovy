package com.kotyk.timelapse.service

import com.kotyk.timelapse.configuration.GlobalSettings
import com.kotyk.timelapse.util.FileUtil
import groovy.util.logging.Slf4j
import org.bytedeco.javacv.FrameGrabber
import org.bytedeco.javacv.OpenCVFrameConverter
import org.bytedeco.javacv.OpenCVFrameGrabber
import org.bytedeco.opencv.opencv_core.IplImage

import java.util.concurrent.Executors
import java.util.concurrent.ScheduledExecutorService
import java.util.concurrent.TimeUnit

import static com.kotyk.timelapse.util.DateUtil.toFormattedTime
import static java.time.LocalDateTime.now
import static org.bytedeco.opencv.helper.opencv_imgcodecs.cvSaveImage

@Slf4j
class WebCamService {

    static void takePhotosAsync(String path) {
        log.info("Start taking photos")
        path = FileUtil.toRightPath(path)
        ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor()
        try (
                FrameGrabber grabber = new OpenCVFrameGrabber(GlobalSettings.webcamId)
                OpenCVFrameConverter.ToIplImage converter = new OpenCVFrameConverter.ToIplImage()
        ) {
            grabber.start()
            Runnable captureTask = () -> executorService.submit {
                try {
                    IplImage photo = converter.convert(grabber.grab())
                    String photoPath = "${path}${toFormattedTime(now())}$GlobalSettings.photosExtension"
                    cvSaveImage(photoPath, photo)
                    log.info("Saved : $photoPath")
                } catch (FrameGrabber.Exception e) {
                    throw new RuntimeException(e)
                }
            }
            executorService.scheduleAtFixedRate(captureTask, 0, GlobalSettings.period, TimeUnit.MILLISECONDS)
            executorService.schedule((Runnable) executorService::shutdown, GlobalSettings.duration, TimeUnit.MILLISECONDS)
            boolean terminated = executorService.awaitTermination(GlobalSettings.duration + 100, TimeUnit.MILLISECONDS)
            log.debug("Taking photos task successfully terminated: $terminated")
            log.info("Taking photos successfully finished")
        } catch (FrameGrabber.Exception | InterruptedException e) {
            throw new RuntimeException(e)
        } finally {
            executorService.shutdown()
        }
    }

}
