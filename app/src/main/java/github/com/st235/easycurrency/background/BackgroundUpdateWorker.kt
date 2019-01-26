package github.com.st235.easycurrency.background

import android.content.Context
import androidx.annotation.WorkerThread
import androidx.work.Worker
import androidx.work.WorkerParameters
import github.com.st235.easycurrency.data.net.CurrencyRateApiWrapper
import github.com.st235.easycurrency.data.CurrencyRatesFacade
import kotlinx.coroutines.runBlocking
import org.koin.standalone.KoinComponent
import org.koin.standalone.inject
import timber.log.Timber

class BackgroundUpdateWorker(context: Context,
                             params: WorkerParameters):
    Worker(context, params), KoinComponent {

    private val currencyRateApiWrapper: CurrencyRateApiWrapper by inject()
    private val currencyRatesDatabaseFacade: CurrencyRatesFacade by inject()

    @WorkerThread
    override fun doWork(): Result {
        var result = Result.failure()
        Timber.d("background task: start periodic task")

        runBlocking {
            try {
                val rates = currencyRateApiWrapper.getRates("EUR").await()
                currencyRatesDatabaseFacade.updateDatabase(rates)
                result = Result.success()
                Timber.d("background task: update finished")
            } catch (exception: Throwable) {
                Timber.w(exception, "background task: failed with an exception")
            }
        }

        return result
    }
}
