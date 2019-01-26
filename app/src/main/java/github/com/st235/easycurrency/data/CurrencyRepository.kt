package github.com.st235.easycurrency.data

import github.com.st235.easycurrency.data.entities.ConvertRatesResponse
import github.com.st235.easycurrency.data.net.CurrencyApi
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber
import kotlin.coroutines.resume
import kotlin.coroutines.resumeWithException
import kotlin.coroutines.suspendCoroutine

class CurrencyRepository(private val currencyApi: CurrencyApi) {
    suspend fun getCurrenciesRate(baseCurrency: String) = suspendCoroutine<ConvertRatesResponse> {
        currencyApi.getCurrenciesConvertRate(baseCurrency).enqueue(object: Callback<ConvertRatesResponse> {
            override fun onResponse(call: Call<ConvertRatesResponse>, response: Response<ConvertRatesResponse>) {
                val convertRates = response.body()
                Timber.d("Fetching url (${call.request().url()}) completed with ${response.code()} response code and $convertRates body")

                if (convertRates == null) {
                    it.resumeWithException(IllegalStateException("There is no result"))
                    return
                }

                it.resume(convertRates)
            }

            override fun onFailure(call: Call<ConvertRatesResponse>, t: Throwable) {
                Timber.e(t, "There was an exception while fetching ${call.request().url()}")
            }
        })
    }
}