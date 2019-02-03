package github.com.st235.easycurrency.background

import androidx.annotation.MainThread
import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

private const val JOB_TAG = "currency_update.background"
private const val JOB_REPEAT_INTERVAL_IN_MINUTES = 60L

/**
 * Creates background tasks,
 * and cancel planned ones of it needed
 */
object BackgroundFactory {
    @MainThread
    fun enqueueJob() {
        WorkManager.getInstance().cancelAllWorkByTag(JOB_TAG)

        val constraints: Constraints = Constraints.Builder()
            .setRequiredNetworkType(NetworkType.CONNECTED)
            .build()

        val recurringWork = PeriodicWorkRequest
            .Builder(BackgroundUpdateWorker::class.java, JOB_REPEAT_INTERVAL_IN_MINUTES, TimeUnit.MINUTES)
            .setConstraints(constraints)
            .addTag(JOB_TAG)
            .build()

        WorkManager.getInstance().enqueue(recurringWork)
    }
}
