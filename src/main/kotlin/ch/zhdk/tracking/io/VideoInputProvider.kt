package ch.zhdk.tracking.io

import org.bytedeco.javacv.FFmpegFrameGrabber
import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.FrameGrabber
import java.nio.file.Path
import kotlin.math.roundToLong

class VideoInputProvider(videoFilePath: Path, val frameRate: Double = Double.NaN) : InputProvider {
    private var videoGrabber: FrameGrabber = FFmpegFrameGrabber(videoFilePath.toAbsolutePath().toString())

    override fun open() {
        videoGrabber.start()

        println(videoGrabber)
        val firstFrame = videoGrabber.grabFrame()
        println("video framerate: ${videoGrabber.frameRate}")
    }

    override fun read(): Frame {
        // check if constrain fps
        if(!frameRate.isNaN()) {
            Thread.sleep((1000.0 / frameRate).roundToLong())
        }

        val frame = videoGrabber.grabFrame()

        if (frame == null) {
            println("restarting video")
            videoGrabber.stop()
            videoGrabber.start()
            return read()
        }

        // return cloned frame
        // (not messing up with start stop of grabber)
        frame.timestamp = System.currentTimeMillis()
        return frame.clone()
    }

    override fun close() {
        videoGrabber.stop()
    }

}