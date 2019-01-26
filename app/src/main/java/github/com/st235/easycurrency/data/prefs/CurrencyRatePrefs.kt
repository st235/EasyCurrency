package github.com.st235.easycurrency.data.prefs

import android.content.Context

private const val APP_PREFS = "currency_rate.prefs"
private const val BASE_CURRENCY_VALUE = "base_currency"
private const val UPDATE_DATA_VALUE = "update_data"

class CurrencyRatePrefs(context: Context) {

    private val sharedPrefs = context.getSharedPreferences(APP_PREFS, Context.MODE_PRIVATE)

    var baseCurrency: String
    get() = sharedPrefs.getString(BASE_CURRENCY_VALUE, "EUR")!!
    set(value) {
        with(sharedPrefs.edit()) {
            putString(BASE_CURRENCY_VALUE, value)
            apply()
        }
    }

    var updateDate: Long
    get() = sharedPrefs.getLong(UPDATE_DATA_VALUE, 0L)
    set(value) {
        with(sharedPrefs.edit()) {
            putLong(BASE_CURRENCY_VALUE, value)
            apply()
        }
    }
}