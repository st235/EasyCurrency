package github.com.st235.easycurrency.presentational.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import github.com.st235.easycurrency.R
import github.com.st235.easycurrency.data.CurrencyRatesRepository
import github.com.st235.easycurrency.domain.Currency
import github.com.st235.easycurrency.presentational.base.BaseActivity
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(), MainView {
    private val presenter: MainPresenter by inject()
    private val adapter: CurrenciesAdapter by inject()

    private lateinit var currenciesRV: RecyclerView

    override fun getLayout() = R.layout.activity_main

    override fun onInitViews() {
        currenciesRV = findViewById(R.id.currenciesList)
        currenciesRV.layoutManager = LinearLayoutManager(this)
        currenciesRV.adapter = adapter
        currenciesRV.itemAnimator = null

    }

    override fun onViewsInitialized(savedInstanceState: Bundle?) {
        presenter.attachView(this)
    }

    override fun updateCurrenciesData(currencies: List<Currency>) {
        adapter.onNewData(currencies)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
