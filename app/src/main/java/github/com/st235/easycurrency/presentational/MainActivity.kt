package github.com.st235.easycurrency.presentational

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import github.com.st235.easycurrency.R
import github.com.st235.easycurrency.data.CurrencyRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import org.koin.android.ext.android.inject
import timber.log.Timber

class MainActivity : AppCompatActivity() {
    private val currencyRepository: CurrencyRepository by inject()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        GlobalScope.launch(context = Dispatchers.Main) {
            val response = currencyRepository.getCurrenciesRate("EUR")
        }
    }
}
