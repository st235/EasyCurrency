package github.com.st235.easycurrency.utils

import java.util.*
import java.util.concurrent.TimeUnit

private const val TIME_VALUE_IN_SECONDS = 1L

typealias UpdateCallback = () -> Unit

class UpdateTimer {

    var timer = Timer()
    var updateCallback: UpdateCallback? = null

    var timerTask = object: TimerTask() {
        override fun run() {
            updateCallback?.invoke()
        }
    }

    init {
        timer.schedule(timerTask, 0, TimeUnit.SECONDS.toMillis(TIME_VALUE_IN_SECONDS))
    }

    fun removeCallback() {
        updateCallback = null
    }
}
