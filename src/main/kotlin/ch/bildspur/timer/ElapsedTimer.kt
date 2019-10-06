package ch.bildspur.timer

class ElapsedTimer(var duration : Long) {
    private var timestamp = millis()

    fun elapsed() : Boolean {
        val m = millis()
        val result = m - timestamp >= duration
        timestamp = millis()
        return result
    }

    fun reset() {
        timestamp = millis()
    }

    private fun millis() : Long
    {
        return System.currentTimeMillis()
    }
}