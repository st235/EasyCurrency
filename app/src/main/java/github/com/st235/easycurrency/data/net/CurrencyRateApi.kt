package github.com.st235.easycurrency.data.net

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface CurrencyRateApi {
    @GET("latest")
    fun getCurrenciesConvertRate(@Query("base") baseCurrency: String): Call<CurrencyRateResponse>
}
