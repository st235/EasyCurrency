package github.com.st235.easycurrency.presentational.main

import github.com.st235.easycurrency.domain.Currency
import github.com.st235.easycurrency.domain.CurrencyListHolder
import github.com.st235.easycurrency.presentational.base.BasePresenter
import github.com.st235.easycurrency.utils.CurrencyUtils
import github.com.st235.easycurrency.utils.Observer

class MainPresenter(
    private val currenciesHolder: CurrencyListHolder): BasePresenter<MainView>() {

    private val currenciesChangeObserver: Observer<Pair<Boolean, List<Currency>>> = { currencies: Pair<Boolean, List<Currency>> ->
        if (currencies.first) {
            view?.updateBaseCurrency(currencies.second)
        } else {
            view?.updateCurrenciesData(currencies.second)
        }
    }

    override fun onAttachView(v: MainView) {
        super.onAttachView(v)
        currenciesHolder.addObserver(currenciesChangeObserver)
    }

    fun onTypeValue(newValue: Double, currency: Currency) {
        currenciesHolder.recalculateCurrencies(
                CurrencyUtils.getBaseValue(newValue, currency)
        )
    }

    fun onClickCurrency(newBaseCurrency: Currency) {
        currenciesHolder.changeBaseCurrency(newBaseCurrency)
    }

    override fun onDetachView(v: MainView?) {
        currenciesHolder.addObserver(currenciesChangeObserver)
        super.onDetachView(v)
    }
}