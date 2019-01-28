package github.com.st235.easycurrency.data

import androidx.annotation.WorkerThread
import github.com.st235.easycurrency.data.db.CurrencyRateDatabase
import github.com.st235.easycurrency.data.db.CurrencyRateEntity
import github.com.st235.easycurrency.data.net.CurrencyRateResponse
import github.com.st235.easycurrency.data.net.RatesMap
import github.com.st235.easycurrency.data.prefs.CurrencyRatePrefs
import timber.log.Timber

class CurrencyRateStorageHelper(private val database: CurrencyRateDatabase,
                                private val prefs: CurrencyRatePrefs) {
    companion object {
        private const val TAG = "[OperatingHelper]"
    }

    @WorkerThread
    fun read(): CurrencyRateResponse? {
        val rates = readDb()
        if (rates.isEmpty()) {
            return null
        }

        val base = prefs.baseCurrency
        val date = prefs.updateDate

        return CurrencyRateResponse(base, date.toString(), rates)
    }

    @WorkerThread
    private fun readDb(): RatesMap {
        val rates = mutableMapOf<String, Double>()
        val dbRates = database.ratesDataDao().getAll()

        for (dbRate in dbRates) {
            rates[dbRate.currency] = dbRate.rate
        }

        return rates
    }

    @WorkerThread
    fun write(ratesResponse: CurrencyRateResponse) {
        writeToDb(ratesResponse)
        writePrefs(ratesResponse)
    }

    @WorkerThread
    private fun writeToDb(ratesResponse: CurrencyRateResponse) {
        Timber.tag(TAG).d("Perform to update database")

        val dbRatesEntities = mutableListOf<CurrencyRateEntity>()
        for (currencyRatePair in ratesResponse.rates) {
            dbRatesEntities.add(
                CurrencyRateEntity(
                    currency = currencyRatePair.key,
                    rate = currencyRatePair.value
                )
            )
        }
        database.ratesDataDao().insertAll(dbRatesEntities)
    }

    @WorkerThread
    private fun writePrefs(ratesResponse: CurrencyRateResponse) {
        Timber.tag(TAG).d("Perform to update prefs")
        prefs.updateDate = 0L
    }
}
