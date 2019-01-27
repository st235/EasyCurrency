package github.com.st235.easycurrency.utils

import android.text.Editable
import android.text.TextWatcher

abstract class CurrencyTextWatcher: TextWatcher {

    override fun afterTextChanged(s: Editable?) {
        val text = s.toString()
        onValueChanged(if (text.isEmpty()) 0.0 else text.toDouble())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    abstract fun onValueChanged(newValue: Double)
}