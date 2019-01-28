package github.com.st235.easycurrency.data.inmemory

import androidx.annotation.WorkerThread
import github.com.st235.easycurrency.data.CurrencyRateStorageHelper
import github.com.st235.easycurrency.data.net.CurrencyRateResponse
import timber.log.Timber

class CurrencyRateInMemoryModel(private val storageHelper: CurrencyRateStorageHelper) {
    companion object {
        private const val TAG = "[InMemoryModel]"
    }

    private var inMemoryCurrencyRate: CurrencyRateResponse? = null

    fun get() = inMemoryCurrencyRate

    fun update(currencyRate: CurrencyRateResponse) {
        inMemoryCurrencyRate = currencyRate
    }

    @WorkerThread
    fun flush() {
        Timber.tag(TAG).v("flush model to disk")
        val modelToFlush = inMemoryCurrencyRate ?: return
        storageHelper.write(modelToFlush)
    }
}
