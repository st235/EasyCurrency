package github.com.st235.easycurrency.utils

import java.text.DecimalFormatSymbols
import java.util.*

object CurrencyUtils {
    private const val ASCII_OFFSET = 0x41
    private const val UNICODE_FLAG_OFFSET = 0x1F1E6
    private const val CURRENCY_PLACEHOLDER_MASK = "0%s00"
    private const val CURRENCY_OUTPUT_MASK = "%.2f"

    private val decimalFormatSymbols = DecimalFormatSymbols.getInstance()

    private var currencyInputPlaceholder: String? = null
    private var currencyDecimalAllowedSymbols: String? = null

    fun getCurrencyTitleBy(code: String) = Currency.getInstance(code).displayName

    fun getCurrencySignBy(code: String) = Currency.getInstance(code).symbol

    fun getCurrencyFlagEmojiBy(code: String): String {
        val firstChar = Character.codePointAt(code, 0) - ASCII_OFFSET + UNICODE_FLAG_OFFSET
        val secondChar = Character.codePointAt(code, 1) - ASCII_OFFSET + UNICODE_FLAG_OFFSET
        return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
    }

    fun calculateBaseValue(newValue: Double,
                           currency: github.com.st235.easycurrency.domain.Currency) =
            newValue / currency.rate

    fun getCurrencyAllowedSymbols(): String {
        if (currencyDecimalAllowedSymbols == null) {
            val separator = decimalFormatSymbols.decimalSeparator
            currencyDecimalAllowedSymbols = "0123456789$separator"
        }

        return currencyDecimalAllowedSymbols!!
    }

    fun getCurrencySeparator() = decimalFormatSymbols.decimalSeparator

    fun getCurrencyInputPlaceholder(): String {
        if (currencyInputPlaceholder == null) {
            val separator = decimalFormatSymbols.decimalSeparator
            currencyInputPlaceholder = CURRENCY_PLACEHOLDER_MASK.format(separator)
        }

        return currencyInputPlaceholder!!
    }

    fun getCurrencyOutputText(value: Double) = CURRENCY_OUTPUT_MASK.format(value)
}