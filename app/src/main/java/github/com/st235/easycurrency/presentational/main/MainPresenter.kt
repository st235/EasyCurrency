package github.com.st235.easycurrency.presentational.main

import github.com.st235.easycurrency.domain.CurrenciesListPair
import github.com.st235.easycurrency.domain.Currency
import github.com.st235.easycurrency.domain.CurrencyListHolder
import github.com.st235.easycurrency.presentational.base.BasePresenter
import github.com.st235.easycurrency.utils.CurrencyUtils
import github.com.st235.easycurrency.utils.Observer

class MainPresenter(
    private val currenciesHolder: CurrencyListHolder): BasePresenter<MainView>() {

    private val currenciesChangeObserverPair: Observer<CurrenciesListPair>
            = { currenciesPair: CurrenciesListPair ->
        if (currenciesPair.first) {
            view?.updateBaseCurrency(currenciesPair.second)
        } else {
            view?.updateCurrenciesData(currenciesPair.second)
        }
    }

    override fun onAttachView(v: MainView) {
        super.onAttachView(v)
        currenciesHolder.addObserver(currenciesChangeObserverPair)
    }

    fun onTypeValue(newValue: Double, currency: Currency) {
        currenciesHolder.recalculateCurrencies(
                CurrencyUtils.calculateBaseValue(newValue, currency)
        )
    }

    fun onClickCurrency(newBaseCurrency: Currency) {
        currenciesHolder.changeBaseCurrency(newBaseCurrency)
    }

    override fun onDetachView(v: MainView?) {
        currenciesHolder.addObserver(currenciesChangeObserverPair)
        super.onDetachView(v)
    }
}