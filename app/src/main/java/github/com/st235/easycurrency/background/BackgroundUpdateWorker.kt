package github.com.st235.easycurrency.background

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.work.Worker
import androidx.work.WorkerParameters
import github.com.st235.easycurrency.data.CurrencyRateStorageHelper
import github.com.st235.easycurrency.data.net.CurrencyRateApiWrapper
import kotlinx.coroutines.runBlocking
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

class BackgroundUpdateWorker(context: Context,
                             params: WorkerParameters):
    Worker(context, params), KoinComponent {
    companion object {
        private const val TAG = "[BackgroundWorker]"
    }

    private val apiWrapper: CurrencyRateApiWrapper by inject()
    private val storageHelper: CurrencyRateStorageHelper by inject()

    @WorkerThread
    override fun doWork(): Result {
        var result = Result.failure()
        Timber.d("background task: start periodic task")

        if (apiWrapper.isInUsage()) {
            Timber.tag(TAG).v("rate api is in usage")
            return Result.success()
        }

        runBlocking {
            try {
                val rates = apiWrapper.getRates("EUR").await()
                storageHelper.write(rates)
                result = Result.success()
                Timber.d("background task: update finished")
            } catch (exception: Throwable) {
                Timber.w(exception, "background task: failed with an exception")
            }
        }

        return result
    }
}
