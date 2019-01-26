package github.com.st235.easycurrency.presentational

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.com.st235.easycurrency.R
import github.com.st235.easycurrency.data.GetRatesTask
import github.com.st235.easycurrency.data.db.RatesDatabase
import github.com.st235.easycurrency.data.db.RatesEntity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject

class MainActivity : AppCompatActivity() {
    private val currencyRepository: GetRatesTask by inject()
    private val ratesDatabase: RatesDatabase by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(context = Dispatchers.IO) {
//            val response = currencyRepository.get("EUR")
//
//            val list = mutableListOf<RatesEntity>()
//            for (entity in response.rates) {
//                list.add(RatesEntity(entity.key, entity.value))
//            }
//
//            ratesDatabase.ratesDataDao().insertAll(list)
        }
    }
}
