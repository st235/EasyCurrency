package github.com.st235.easycurrency.components

import android.content.Context
import android.text.TextWatcher
import android.text.method.DigitsKeyListener
import android.util.AttributeSet
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.TextView
import github.com.st235.easycurrency.R
import github.com.st235.easycurrency.utils.CurrencyUtils

class CurrencyEditText @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    : LinearLayout(context, attrs, defStyleAttr) {

    private val inputEt: EditText
    private val signTv: TextView

    init {
        orientation = LinearLayout.HORIZONTAL
        gravity = Gravity.CENTER_VERTICAL
        isClickable = true
        isFocusable = true
        isFocusableInTouchMode = true
        setBackgroundResource(R.drawable.current_edittext_background)

        val v = LayoutInflater.from(getContext()).inflate(R.layout.content_currency_edittext, this)
        inputEt = v.findViewById(R.id.input)
        signTv = v.findViewById(R.id.sign)

        inputEt.hint = CurrencyUtils.getCurrencyInputPlaceholder()
        inputEt.keyListener = DigitsKeyListener.getInstance(CurrencyUtils.getCurrencyAllowedSymbols())

        inputEt.onFocusChangeListener = View.OnFocusChangeListener { iv, f ->
            onFocusChangeListener.onFocusChange(iv, f)
        }
    }

    fun addTextWatcher(textWatcher: TextWatcher) {
        inputEt.addTextChangedListener(textWatcher)
    }

    fun removeTextWatcher(textWatcher: TextWatcher) {
        inputEt.removeTextChangedListener(textWatcher)
    }

    fun changeInputText(charSequence: CharSequence) {
        inputEt.setText(charSequence)
    }

    fun setSign(sign: CharSequence) {
        signTv.text = sign
    }
}
