package github.com.st235.easycurrency.data.net

import kotlinx.coroutines.CompletableDeferred
import kotlinx.coroutines.Deferred
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import timber.log.Timber

class CurrencyRateApiWrapper(private val currencyRateApi: CurrencyRateApi) {
    companion object {
        private const val TAG = "[RateApiWrapper]"
    }

    private var currentCall: Call<CurrencyRateResponse>? = null

    fun isInUsage() = currentCall != null && currentCall!!.isExecuted

    fun getRates(baseCurrency: String): Deferred<CurrencyRateResponse> {
        val ratesDeferredRequest = CompletableDeferred<CurrencyRateResponse>()

        currentCall = currencyRateApi.getCurrenciesConvertRate(baseCurrency)
        currentCall?.enqueue(object : Callback<CurrencyRateResponse> {
            override fun onResponse(call: Call<CurrencyRateResponse>, response: Response<CurrencyRateResponse>) {            resetCurrentCall()
                resetCurrentCall()

                val convertRates = response.body()
                Timber.tag(TAG).d("Fetching url (${call.request().url()}) completed with" +
                        " ${response.code()} response code and $convertRates body")

                if (convertRates == null) {
                    ratesDeferredRequest.completeExceptionally(IllegalStateException("There is no result"))
                    return
                }

                ratesDeferredRequest.complete(convertRates)
            }

            override fun onFailure(call: Call<CurrencyRateResponse>, t: Throwable) {
                resetCurrentCall()
                Timber.tag(TAG).e(t, "There was an exception while fetching ${call.request().url()}")
                ratesDeferredRequest.completeExceptionally(t)
            }
        })

        ratesDeferredRequest.invokeOnCompletion {
            resetCurrentCall()
            if (ratesDeferredRequest.isCancelled) {
                currentCall?.cancel()
            }
        }

        return ratesDeferredRequest
    }

    private fun resetCurrentCall() {
        currentCall = null
    }
}
