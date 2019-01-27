package github.com.st235.easycurrency.utils

import java.util.*

object CurrencyUtils {
    fun getTitle(code: String) = Currency.getInstance(code).displayName

    fun getEmoji(code: String): String {
        val flagOffset = 0x1F1E6
        val asciiOffset = 0x41

        val firstChar = Character.codePointAt(code, 0) - asciiOffset + flagOffset
        val secondChar = Character.codePointAt(code, 1) - asciiOffset + flagOffset

        return String(Character.toChars(firstChar)) + String(Character.toChars(secondChar))
    }

    fun getBaseValue(newValue: Double, currency: github.com.st235.easycurrency.domain.Currency) =
            newValue / currency.rate
}