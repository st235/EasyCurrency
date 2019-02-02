package github.com.st235.easycurrency.utils

import github.com.st235.easycurrency.BuildConfig
import timber.log.Timber
import java.util.concurrent.TimeUnit

const val MILLIS_TO_UNIX_TIMESTAMP = 1000L

object TimeUtils {
    fun getTimestamp() = System.currentTimeMillis() / MILLIS_TO_UNIX_TIMESTAMP

    fun getHours(timestamp: Long): Int {
        val timestampDelta = Math.abs(timestamp - getTimestamp())
        return TimeUnit.MILLISECONDS.toHours(timestampDelta * MILLIS_TO_UNIX_TIMESTAMP).toInt()
    }

    fun isTimestampExpired(timestamp: Long, hoursToExpire: Long = BuildConfig.RATES_EXPIRES_HOURS): Boolean {
        val timestampDelta = Math.abs(timestamp - getTimestamp())
        Timber.v("Timestamp diff in minutes " +
                "is ${TimeUnit.MILLISECONDS.toMinutes(timestampDelta * MILLIS_TO_UNIX_TIMESTAMP)}")
        return TimeUnit.MILLISECONDS.toHours(timestampDelta * MILLIS_TO_UNIX_TIMESTAMP) >= hoursToExpire
    }
}