package github.com.st235.easycurrency.presentational.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import github.com.st235.easycurrency.R
import github.com.st235.easycurrency.components.CurrencyEditText
import github.com.st235.easycurrency.domain.Currency
import github.com.st235.easycurrency.utils.CurrencyTextWatcher
import github.com.st235.easycurrency.utils.CurrencyUtils
import github.com.st235.easycurrency.utils.OnItemClickListener
import github.com.st235.easycurrency.utils.OnItemValueChangedListener

class CurrenciesAdapter
    : RecyclerView.Adapter<CurrenciesAdapter.CurrenciesViewHolder>() {
    private companion object {
        private const val NO_POSITION = -1

        private const val BASE = 0
        private const val ORDINARY = 1
    }

    private var currentlyFocusedItem = -1

    private var currencies: List<Currency> = emptyList()

    var itemClickListener: OnItemClickListener<Currency>? = null
    var valueChangedListener: OnItemValueChangedListener<Double, Currency>? = null

    inner class CurrenciesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val currencyItemRoot: View = itemView.findViewById(R.id.currencyItemRoot)
        private val currencyValue: CurrencyEditText = itemView.findViewById(R.id.currencyInputValue)
        private val currencyCode: TextView = itemView.findViewById(R.id.currencyIsoCode)
        private val currencyTitle: TextView = itemView.findViewById(R.id.currencyTitle)
        private val currencyAvatar: TextView = itemView.findViewById(R.id.currencyAvatar)

        private val onFocusChangeListener = View.OnFocusChangeListener { v, hasFocus ->
            val adapterPosition = adapterPosition
            if (hasFocus) {
                currentlyFocusedItem = adapterPosition
            }
        }

        private val onTextWatcher: CurrencyTextWatcher = object: CurrencyTextWatcher() {
            override fun onValueChanged(newValue: Double) {
                val position = adapterPosition
                valueChangedListener?.onItemValueChanged(newValue,
                        currencies[position], position)
            }
        }

        init {
            currencyItemRoot.setOnClickListener {
                val position = adapterPosition
                itemClickListener?.onItemClick(currencies[position], position)
            }

            currencyValue.onFocusChangeListener = onFocusChangeListener
        }

        fun bind(currency: Currency) {
            currencyValue.removeTextWatcher(onTextWatcher)

            currencyValue.changeInputText(CurrencyUtils.getCurrencyOutputText(currency.value))
            currencyValue.setSign(CurrencyUtils.getCurrencySignBy(currency.id))
            currencyCode.text = currency.id
            currencyTitle.text = currency.title
            currencyAvatar.text = CurrencyUtils.getCurrencyFlagEmojiBy(currency.id)

            currencyValue.addTextWatcher(onTextWatcher)
        }

        fun bindWithPayload(currency: Currency) {
            currencyValue.removeTextWatcher(onTextWatcher)
            currencyValue.changeInputText(CurrencyUtils.getCurrencyOutputText(currency.value))
            currencyValue.addTextWatcher(onTextWatcher)
        }
    }

    fun onNewData(newCurrencies: List<Currency>) {
        this.currencies = newCurrencies
        notifyAllBut(currentlyFocusedItem)
    }

    fun onNewOrder(newCurrencies: List<Currency>) {
        this.currencies = newCurrencies
        notifyDataSetChanged()
    }

    private fun notifyAllBut(one: Int) {
        if (one == NO_POSITION) {
            notifyItemRangeChanged(0, itemCount, true)
            return
        }

        notifyItemRangeChanged(0, one, true)
        notifyItemRangeChanged(one + 1, itemCount, true)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder {
        val context = parent.context
        val layoutId = if (viewType == BASE) R.layout.item_base_currency else R.layout.item_ordinary_currency
        val view = LayoutInflater.from(context).inflate(layoutId, parent, false)
        return CurrenciesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int, payloads: MutableList<Any>) {
        if (payloads.isEmpty()) {
            onBindViewHolder(holder, position)
            return
        }

        holder.bindWithPayload(currencies[position])
    }

    override fun getItemViewType(position: Int) = if (currencies[position].isBase) BASE else ORDINARY

    override fun getItemCount() = currencies.size
}
