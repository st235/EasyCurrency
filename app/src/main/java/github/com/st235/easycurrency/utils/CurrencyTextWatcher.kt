package github.com.st235.easycurrency.utils

import android.text.Editable
import android.text.TextWatcher
import timber.log.Timber
import java.lang.StringBuilder
import java.text.NumberFormat
import java.text.ParseException
import java.util.*

abstract class CurrencyTextWatcher: TextWatcher {
    companion object {
        private const val TAG = "[CurrencyTextWatcher]"
    }

    private val format = NumberFormat.getInstance(Locale.getDefault())

    override fun afterTextChanged(s: Editable?) {
        replaceTwiceSeparator(CurrencyUtils.getCurrencySeparator(), s)

        var result = 0.0
        val text = s.toString()

        if (text.isNotEmpty()) {
            try {
                val number = format.parse(s.toString())
                result = number.toDouble()
            } catch (e: ParseException) {
                Timber.tag(TAG).e(e, "There was exception while parsing. Possible invalid string like '.'")
            }
        }

        onValueChanged(result)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    abstract fun onValueChanged(newValue: Double)

    private fun replaceTwiceSeparator(separator: Char, text: Editable?) {
        if (text == null) {
            return
        }

        var i = 0
        var isOnce = false

        while (i < text.length) {
            val character = text[i]

            if (character != separator) {
                i++
                continue
            }

            if (!isOnce) {
                isOnce = true
                i++
                continue
            }

            text.delete(i, i + 1)
        }
    }
}