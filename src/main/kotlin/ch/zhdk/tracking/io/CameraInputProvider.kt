package ch.zhdk.tracking.io

import org.bytedeco.javacv.Frame
import org.bytedeco.javacv.OpenCVFrameGrabber

class CameraInputProvider(deviceNumber : Int = 0,
                          val width : Int = 640,
                          val height : Int = 480) : InputProvider() {

    private val grabber = OpenCVFrameGrabber(deviceNumber)
    private var timestamp = 0L

    // todo: implement device selection through list

    override fun open() {
        grabber.imageWidth = width
        grabber.imageHeight = height
        grabber.start()
        super.open()
    }

    override fun read(): Frame {
        val frame = grabber.grab()
        frame.timestamp = timestamp++
        return frame.clone()
    }

    override fun close() {
        grabber.stop()
        super.close()
    }
}