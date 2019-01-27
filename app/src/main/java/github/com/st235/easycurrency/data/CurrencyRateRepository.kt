package github.com.st235.easycurrency.data

import androidx.annotation.MainThread
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import github.com.st235.easycurrency.data.inmemory.CurrencyRateInMemoryModel
import github.com.st235.easycurrency.data.net.CurrencyRateApiWrapper
import github.com.st235.easycurrency.data.net.CurrencyRateResponse
import github.com.st235.easycurrency.data.prefs.CurrencyRatePrefs
import github.com.st235.easycurrency.utils.ObservableModel
import github.com.st235.easycurrency.utils.UpdateTimer
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

const val BASE_TO_BASE_CONVERT_RATIO = 1.0

class CurrencyRatesRepository(private val inMemoryModel: CurrencyRateInMemoryModel,
                              private val apiWrapper: CurrencyRateApiWrapper,
                              private val prefs: CurrencyRatePrefs,
                              private val updateTimer: UpdateTimer):
    ObservableModel<CurrencyRateResponse>(), LifecycleObserver {
    companion object {
        private const val TAG = "[RatesRepository]"
    }

    init {
        startUpdating()
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun startUpdating() {
        Timber.tag(TAG).v("Start updating")
        updateTimer.updateCallback = this::update
    }

    @MainThread
    fun update() {
        Timber.tag(TAG).v("Update task called")

        GlobalScope.launch {
            val response = apiWrapper.getRates(prefs.baseCurrency).await()
            response.rates.put(response.base, BASE_TO_BASE_CONVERT_RATIO)
            inMemoryModel.update(response)
            notifyObservers(response)
        }
    }

    fun changeBaseCurrency(baseCurrency: String) {
        Timber.tag(TAG).v("Change base currency to $baseCurrency")

    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun stopUpdating() {
        Timber.tag(TAG).v("Stop updating")
        updateTimer.removeCallback()
        inMemoryModel.flush()
    }
}
