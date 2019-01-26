package github.com.st235.easycurrency.data.net

import com.google.gson.annotations.SerializedName

typealias RatesMap = Map<String, Double>

data class CurrencyRateResponse(@SerializedName("base") val base: String,
                                @SerializedName("date") val date: String,
                                @SerializedName("rates") val rates: RatesMap)
