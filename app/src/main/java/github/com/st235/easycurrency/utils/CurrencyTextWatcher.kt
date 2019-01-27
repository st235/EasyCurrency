package github.com.st235.easycurrency.utils

import android.text.Editable
import android.text.TextWatcher
import java.text.NumberFormat
import java.util.*

abstract class CurrencyTextWatcher: TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        val format = NumberFormat.getInstance(Locale.getDefault())
        var result = 0.0
        val text = s.toString()
        if (text.isNotEmpty()) {
            val number = format.parse(s.toString())
            result = number.toDouble()
        }


        onValueChanged(result)
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    abstract fun onValueChanged(newValue: Double)
}