package github.com.st235.easycurrency.domain

import androidx.annotation.WorkerThread
import github.com.st235.easycurrency.data.CurrencyRatesRepository
import github.com.st235.easycurrency.data.net.CurrencyRateResponse
import github.com.st235.easycurrency.utils.CurrencyUtils
import github.com.st235.easycurrency.utils.ObservableModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class CurrencyListHolder(private val currencyRatesRepository: CurrencyRatesRepository):
    ObservableModel<Pair<Boolean, List<Currency>>>() {

    private var baseValue: Double = 1.0
    private var isBaseCurrencyChanged: Boolean = false

    private val currencies: MutableList<Currency> = mutableListOf()

    init {
        currencyRatesRepository.addObserver(this::onUpdate)
    }

    fun changeBaseCurrency(newCurrency: Currency) {
        baseValue = newCurrency.value
        currencyRatesRepository.changeBaseCurrency(newCurrency.id)

        currencies[0].isBase = false
        newCurrency.isBase = true

        currencies.sortWith(compareBy ({ !it.isBase }, { it.title }))
        isBaseCurrencyChanged = true
    }

    fun recalculateCurrencies(newBaseValue: Double) {
        GlobalScope.launch {
            baseValue = newBaseValue
            updateValues()
            withContext(context = Dispatchers.Main) {
                notifyObservers(isBaseCurrencyChanged to currencies)
                isBaseCurrencyChanged = false
            }
        }
    }

    @WorkerThread
    private fun onUpdate(currencyRateResponse: CurrencyRateResponse) {
        if (currencies.isEmpty()) {
            createList(currencyRateResponse)
        } else {
            updateList(currencyRateResponse)
        }

        updateValues()

        GlobalScope.launch(context = Dispatchers.Main) {
            notifyObservers(isBaseCurrencyChanged to currencies)
            isBaseCurrencyChanged = false
        }
    }

    private fun createList(response: CurrencyRateResponse) {
        for (entry in response.rates) {
            val currencyForEntry = Currency(entry.key, CurrencyUtils.getTitle(entry.key),
                    entry.key == response.base)
            currencyForEntry.rate = entry.value
            currencies.add(currencyForEntry)
        }

        currencies.sortWith(compareBy ({ !it.isBase }, { it.title }))
    }

    private fun updateList(response: CurrencyRateResponse) {
        for (currency in currencies) {
            currency.rate = response.rates[currency.id]!!
        }
    }

    private fun updateValues() {
        for (currency in currencies) {
            currency.value = baseValue * currency.rate
        }
    }
}