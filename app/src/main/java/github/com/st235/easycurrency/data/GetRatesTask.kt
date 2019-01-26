package github.com.st235.easycurrency.data

import github.com.st235.easycurrency.data.entities.ConvertRatesResponse
import github.com.st235.easycurrency.data.net.CurrencyApi
import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class GetRatesTask(private val currencyApi: CurrencyApi) {
    fun get(baseCurrency: String): Deferred<ConvertRatesResponse> {
        val getRequest = CompletableDeferred<ConvertRatesResponse>()

        val call = currencyApi.getCurrenciesConvertRate(baseCurrency)
        call.enqueue(object : Callback<ConvertRatesResponse> {
            override fun onResponse(call: Call<ConvertRatesResponse>, response: Response<ConvertRatesResponse>) {
                val convertRates = response.body()
                Timber.d("Fetching url (${call.request().url()}) completed with ${response.code()} response code and $convertRates body")

                if (convertRates == null) {
                    getRequest.completeExceptionally(IllegalStateException("There is no result"))
                    return
                }

                getRequest.complete(convertRates)
            }

            override fun onFailure(call: Call<ConvertRatesResponse>, t: Throwable) {
                Timber.e(t, "There was an exception while fetching ${call.request().url()}")
                getRequest.completeExceptionally(t)
            }
        })

        getRequest.invokeOnCompletion {
            if (getRequest.isCancelled) {
                call.cancel()
            }
        }

        return getRequest
    }
}