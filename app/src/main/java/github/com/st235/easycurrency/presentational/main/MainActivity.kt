package github.com.st235.easycurrency.presentational.main

import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.SimpleItemAnimator
import github.com.st235.easycurrency.R
import github.com.st235.easycurrency.domain.Currency
import github.com.st235.easycurrency.presentational.base.BaseActivity
import github.com.st235.easycurrency.utils.OnItemClickListener
import github.com.st235.easycurrency.utils.OnItemValueChangedListener
import org.koin.android.ext.android.inject

class MainActivity : BaseActivity(), MainView {
    private val presenter: MainPresenter by inject()
    private val adapter: CurrenciesAdapter by inject()

    private val onItemValueChangedListener = object: OnItemValueChangedListener<Double, Currency> {
        override fun onItemValueChanged(value: Double, item: Currency, position: Int) {
            presenter.onTypeValue(value, item)
        }
    }

    private val onItemClickListener = object: OnItemClickListener<Currency> {
        override fun onItemClick(item: Currency, position: Int) {
            presenter.onClickCurrency(item)
        }
    }

    private lateinit var currenciesRV: RecyclerView

    override fun getLayout() = R.layout.activity_main

    override fun onInitViews() {
        currenciesRV = findViewById(R.id.currenciesList)

        adapter.valueChangedListener = onItemValueChangedListener
        adapter.itemClickListener = onItemClickListener

        (currenciesRV.itemAnimator as SimpleItemAnimator).supportsChangeAnimations = false
        currenciesRV.layoutManager = LinearLayoutManager(this)
        currenciesRV.adapter = adapter
    }

    override fun onViewsInitialized(savedInstanceState: Bundle?) {
        presenter.attachView(this)
    }

    override fun updateBaseCurrency(currencies: List<Currency>) {
        adapter.onNewOrder(currencies)
    }

    override fun updateCurrenciesData(currencies: List<Currency>) {
        adapter.onNewData(currencies)
    }

    override fun onDestroy() {
        presenter.detachView()
        super.onDestroy()
    }
}
