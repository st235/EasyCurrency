package github.com.st235.easycurrency.presentational.main

import github.com.st235.easycurrency.domain.Currency

interface MainView {
    fun updateBaseCurrency(currencies: List<Currency>)
    fun updateCurrenciesData(currencies: List<Currency>)
}
