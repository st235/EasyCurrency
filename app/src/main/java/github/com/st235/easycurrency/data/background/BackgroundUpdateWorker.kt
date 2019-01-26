package github.com.st235.easycurrency.data.background

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.work.Worker
import androidx.work.WorkerParameters
import github.com.st235.easycurrency.data.GetRatesTask
import github.com.st235.easycurrency.data.db.RatesDatabaseFacade
import kotlinx.coroutines.runBlocking
import timber.log.Timber
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject

class BackgroundUpdateWorker(context: Context,
                             params: WorkerParameters):
    Worker(context, params), KoinComponent {

    private val getRatesTask: GetRatesTask by inject()
    private val ratesDatabaseFacade: RatesDatabaseFacade by inject()

    @WorkerThread
    override fun doWork(): Result {
        var result = Result.failure()
        Timber.d("background task: start periodic task")

        runBlocking {
            try {
                val rates = getRatesTask.get("EUR").await()
                ratesDatabaseFacade.updateDatabase(rates)
                result = Result.success()
                Timber.d("background task: update finished")
            } catch (e: Throwable) {
                Timber.e("background task: failed with an exception")
            }
        }

        return result
    }
}
