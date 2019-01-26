package github.com.st235.easycurrency.data

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import github.com.st235.easycurrency.data.db.CurrencyRateDatabase
import github.com.st235.easycurrency.data.db.CurrencyRateEntity
import github.com.st235.easycurrency.data.net.CurrencyRateApiWrapper
import github.com.st235.easycurrency.data.net.CurrencyRateResponse
import github.com.st235.easycurrency.data.prefs.CurrencyRatePrefs
import github.com.st235.easycurrency.utils.ObservableModel
import github.com.st235.easycurrency.utils.UpdateTimer
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import timber.log.Timber

const val BASE_TO_BASE_CONVERT_RATIO = 1.0

class CurrencyRatesFacade(private val currencyRateDatabase: CurrencyRateDatabase,
                          private val currencyRateApiWrapper: CurrencyRateApiWrapper,
                          private val currencyRatePrefs: CurrencyRatePrefs,
                          updateTimer: UpdateTimer): ObservableModel<CurrencyRateResponse>() {
    companion object {
        val TAG = "[CurrencyRatesFacade]"
    }

    init {
        updateTimer.updateCallback = {
            update()
        }
    }

    @MainThread
    fun update() {
        Timber.tag(TAG).v("Update task called")

        GlobalScope.launch {
            val rates = currencyRateApiWrapper.getRates(currencyRatePrefs.baseCurrency).await()
            updateDatabase(rates)
            updatePrefs(rates)
            notifyObservers(rates)
        }
    }

    @WorkerThread
    fun updateDatabase(ratesResponse: CurrencyRateResponse) {
        Timber.tag(TAG).d("Perform to update database")

        val dbRatesEntities = mutableListOf<CurrencyRateEntity>()
        dbRatesEntities.add(
            CurrencyRateEntity(
                currency = ratesResponse.base,
                rate = BASE_TO_BASE_CONVERT_RATIO
            )
        )
        for (currencyRatePair in ratesResponse.rates) {
            dbRatesEntities.add(
                CurrencyRateEntity(
                    currency = currencyRatePair.key,
                    rate = currencyRatePair.value
                )
            )
        }
        currencyRateDatabase.ratesDataDao().insertAll(dbRatesEntities)
    }

    @WorkerThread
    private fun updatePrefs(ratesResponse: CurrencyRateResponse) {
        Timber.tag(TAG).d("Perform to update prefs")

        currencyRatePrefs.baseCurrency = ratesResponse.base
        currencyRatePrefs.updateDate = 0L
    }

    @WorkerThread
    private fun getStoredValue() {
//        val dbEntity =
    }
}
