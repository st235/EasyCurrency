package github.com.st235.easycurrency.data.inmemory

import androidx.annotation.WorkerThread
import github.com.st235.easycurrency.data.CurrencyRateStorageHelper
import github.com.st235.easycurrency.data.net.CurrencyRateResponse
import github.com.st235.easycurrency.utils.ThreadUtils
import timber.log.Timber

class CurrencyRateInMemoryModel(private val storageHelper: CurrencyRateStorageHelper) {
    companion object {
        private const val TAG = "[InMemoryModel]"
    }

    private var inMemoryCurrencyRate: CurrencyRateResponse? = null

    fun get() = inMemoryCurrencyRate

    @WorkerThread
    fun getOrRead(): CurrencyRateResponse? {
        ThreadUtils.assertOnBackgroundThread()

        if (inMemoryCurrencyRate == null) {
            inMemoryCurrencyRate = storageHelper.read()
        }
        return inMemoryCurrencyRate
    }

    fun update(currencyRate: CurrencyRateResponse) {
        inMemoryCurrencyRate = currencyRate
    }

    @WorkerThread
    fun flush() {
        ThreadUtils.assertOnBackgroundThread()

        Timber.tag(TAG).v("flush model to disk")
        val modelToFlush = inMemoryCurrencyRate ?: return
        storageHelper.write(modelToFlush)
    }
}
