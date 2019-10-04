package ch.zhdk.tracking.pipeline

import ch.zhdk.tracking.io.InputProvider
import ch.zhdk.tracking.javacv.toMat
import org.bytedeco.opencv.opencv_core.Mat
import kotlin.concurrent.thread

abstract class Pipeline(val inputProvider : InputProvider) {

    private lateinit var pipelineThread : Thread
    @Volatile private var shutdownRequested = false

    var isRunning = false
        private set

    var lastFrame : Mat = Mat()
        private set

    fun start() {
        if(isRunning)
            return

        shutdownRequested = false

        // open input provider
        inputProvider.open()

        // start processing thread
        pipelineThread = thread(start = true) {
            while(!shutdownRequested) {
                // read frame
                val input = inputProvider.read()

                // process
                lastFrame = process(input.toMat())

                println("Lastframe: ${lastFrame.rows()}")
            }
        }
    }

    abstract fun process(frame : Mat) : Mat

    fun stop() {
        if(!isRunning)
            return

        shutdownRequested = true
        pipelineThread.join()

        inputProvider.close()
    }
}