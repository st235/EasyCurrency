package github.com.st235.easycurrency.data.net

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitFactory {
    inline fun<reified T> createApiWrapper(baseUrl: String): T {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

        return retrofit.create(T::class.java)
    }
}
