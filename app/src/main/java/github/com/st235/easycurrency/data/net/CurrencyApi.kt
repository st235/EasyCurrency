package github.com.st235.easycurrency.data.net

import github.com.st235.easycurrency.data.entities.ConvertRatesResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyApi {
    @GET("latest")
    fun getCurrenciesConvertRate(@Query("base") baseCurrency: String): Call<ConvertRatesResponse>
}