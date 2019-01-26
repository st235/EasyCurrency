package github.com.st235.easycurrency.presentational

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.com.st235.easycurrency.R
import github.com.st235.easycurrency.data.CurrencyRatesRepository
import github.com.st235.easycurrency.data.net.CurrencyRateApiWrapper
import github.com.st235.easycurrency.data.db.CurrencyRateDatabase
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val currencyRatesRepository: CurrencyRatesRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(context = Dispatchers.IO) {
            currencyRatesRepository.update()
//            val response = currencyRepository.getRates("EUR")
//
//            val list = mutableListOf<CurrencyRateEntity>()
//            for (entity in response.rates) {
//                list.add(CurrencyRateEntity(entity.key, entity.value))
//            }
//
//            currencyRateDatabase.ratesDataDao().insertAll(list)
        }
    }
}
