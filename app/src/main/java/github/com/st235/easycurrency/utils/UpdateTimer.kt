package github.com.st235.easycurrency.utils

import java.util.*
import java.util.concurrent.TimeUnit

private const val TIME_VALUE_IN_SECONDS = 1L

typealias UpdateCallback = () -> Unit

/**
 * Timer to create a periodic task
 */
class UpdateTimer {
    private val timer = Timer()
    private val timerTask = object: TimerTask() {
        override fun run() {
            updateCallback?.invoke()
        }
    }

    /**
     * Callback which would be fires every time period
     */
    var updateCallback: UpdateCallback? = null

    init {
        timer.schedule(timerTask, 0, TimeUnit.SECONDS.toMillis(TIME_VALUE_IN_SECONDS))
    }

    /**
     * Removes callback to release reference
     * and prevent leaks
     */
    fun removeCallback() {
        updateCallback = null
    }
}
