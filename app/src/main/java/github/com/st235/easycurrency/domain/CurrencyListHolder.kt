package github.com.st235.easycurrency.domain

import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import github.com.st235.easycurrency.data.CurrencyRateRepository
import github.com.st235.easycurrency.data.net.CurrencyRateResponse
import github.com.st235.easycurrency.utils.CurrencyUtils
import github.com.st235.easycurrency.utils.ObservableModel
import github.com.st235.easycurrency.utils.debug.ThreadUtils
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import timber.log.Timber

typealias CurrenciesList = List<Currency>

class CurrencyListHolder(private val currencyRateRepository: CurrencyRateRepository):
    ObservableModel<CurrenciesList>() {
    companion object {
        private const val TAG = "[CurrencyListHolder]"
    }

    private lateinit var baseCurrency: Currency
    private val currencies: MutableList<Currency> = mutableListOf()

    init {
        currencyRateRepository.addObserver(this::onUpdate)
    }

    @MainThread
    fun changeBaseCurrency(newCurrency: Currency) {
        GlobalScope.launch {
            changeBaseCurrencyInternal(newCurrency)
        }
    }

    @Synchronized
    private fun changeBaseCurrencyInternal(newCurrency: Currency) {
        baseCurrency = newCurrency
        currencyRateRepository.changeBaseCurrency(newCurrency.id)

        currencies[0].isBase = false
        currencies.find { it.id == newCurrency.id }?.isBase = true
        sortCurrenciesList()
    }

    @MainThread
    fun recalculateCurrencies(newBaseValue: Double) {
        GlobalScope.launch {
            baseCurrency.value = newBaseValue
            updateValues()
            withContext(context = Dispatchers.Main) {
                notifyObservers(currencies.map { i -> i })
            }
        }
    }

    @WorkerThread
    private fun onUpdate(currencyRateResponse: CurrencyRateResponse) {
        ThreadUtils.assertOnBackgroundThread()
        if (currencies.isEmpty()) {
            createList(currencyRateResponse)
        } else {
            updateList(currencyRateResponse)
        }

        updateValues()

        GlobalScope.launch(context = Dispatchers.Main) {
            notifyObservers(currencies.map { i -> i })
        }
    }

    @WorkerThread
    private fun createList(response: CurrencyRateResponse) {
        for (entry in response.rates) {
            val isBase = entry.key == response.base
            val currencyForEntry = Currency(entry.key, CurrencyUtils.getCurrencyTitleBy(entry.key), isBase)

            if (isBase) {
                baseCurrency = currencyForEntry
            }

            currencyForEntry.rate = entry.value
            currencies.add(currencyForEntry)
        }

        sortCurrenciesList()
    }

    @WorkerThread
    private fun updateList(response: CurrencyRateResponse) {
        if (response.base != baseCurrency.id) {
            Timber.tag(TAG).w("Update was dropped cause base currency is different")
            return
        }

        for (i in 0 until currencies.size) {
            val currency = currencies[i]
            currencies[i] = Currency.copyWithNewRate(currency, response.rates[currency.id]!!)
        }
    }

    @WorkerThread
    private fun updateValues() {
        for (i in 0 until currencies.size) {
            val currency = currencies[i]
            currencies[i] = Currency.copyWithNewValue(currency, baseCurrency.value * currency.rate)
        }
    }

    @Synchronized
    private fun sortCurrenciesList() {
        currencies.sortWith(compareBy({ !it.isBase }, { it.title }))
    }
}
