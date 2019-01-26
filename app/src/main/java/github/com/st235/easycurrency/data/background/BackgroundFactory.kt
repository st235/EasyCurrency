package github.com.st235.easycurrency.data.background

import androidx.work.Constraints
import androidx.work.NetworkType
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import java.util.concurrent.TimeUnit

private const val JOB_TAG = "currency_update.background"

class BackgroundFactory {
    companion object {
        fun enqueueWork() {
            WorkManager.getInstance().cancelAllWorkByTag(JOB_TAG)

            val constraints: Constraints = Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()

            val recurringWork =
                PeriodicWorkRequest.Builder(BackgroundUpdateWorker::class.java, 15, TimeUnit.MINUTES)
                    .setConstraints(constraints).addTag(JOB_TAG).build()
            WorkManager.getInstance().enqueue(recurringWork)
        }
    }
}