package github.com.st235.easycurrency.data.db

import androidx.annotation.WorkerThread
import github.com.st235.easycurrency.data.entities.ConvertRatesResponse
import timber.log.Timber

const val BASE_TO_BASE_CONVERT_RATIO = 1.0

class RatesDatabaseFacade(private val ratesDatabase: RatesDatabase) {

    @WorkerThread
    fun updateDatabase(ratesResponse: ConvertRatesResponse) {
        Timber.d("Perform to update database")

        val dbRatesEntities = mutableListOf<RatesEntity>()
        dbRatesEntities.add(RatesEntity(ratesResponse.base, BASE_TO_BASE_CONVERT_RATIO))
        for (currencyRatePair in ratesResponse.rates) {
            dbRatesEntities.add(RatesEntity(currencyRatePair.key, currencyRatePair.value))
        }
        ratesDatabase.ratesDataDao().insertAll(dbRatesEntities)
    }
}