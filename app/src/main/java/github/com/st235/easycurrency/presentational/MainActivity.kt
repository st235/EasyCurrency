package github.com.st235.easycurrency.presentational

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.com.st235.easycurrency.R
import github.com.st235.easycurrency.data.CurrencyRepository
import github.com.st235.easycurrency.data.db.RatesDatabase
import github.com.st235.easycurrency.data.entities.ConvertRatesDbEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val currencyRepository: CurrencyRepository by inject()
    private val ratesDatabase: RatesDatabase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(context = Dispatchers.IO) {
            val response = currencyRepository.getCurrenciesRate("EUR")

            val list = mutableListOf<ConvertRatesDbEntity>()
            for (entity in response.rates) {
                list.add(ConvertRatesDbEntity(entity.key, entity.value))
            }

            ratesDatabase.ratesDataDao().insertAll(list)
        }
    }
}
