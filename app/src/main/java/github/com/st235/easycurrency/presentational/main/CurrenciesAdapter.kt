package github.com.st235.easycurrency.presentational.main

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.TextView
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import github.com.st235.easycurrency.R
import github.com.st235.easycurrency.components.CircularImageView
import github.com.st235.easycurrency.domain.Currency
import github.com.st235.easycurrency.utils.CurrencyUtils

class CurrenciesAdapter()
    : RecyclerView.Adapter<CurrenciesAdapter.CurrenciesViewHolder>() {

    private var currencies: List<Currency> = emptyList()

    class CurrenciesViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        private val currencyValue: EditText = itemView.findViewById(R.id.currencyInputValue)
        private val currencyCode: TextView = itemView.findViewById(R.id.currencyIsoCode)
        private val currencyTitle: TextView = itemView.findViewById(R.id.currencyTitle)
        private val currencyAvatar: CircularImageView = itemView.findViewById(R.id.currencyAvatar)

        fun bind(currency: Currency) {
            currencyValue.setText(currency.value.toString())
            currencyCode.text = currency.id
            currencyTitle.text = currency.title
            currencyAvatar.setExtraText(CurrencyUtils.getEmoji(currency.id))
        }
    }

    fun onNewData(newCurrencies: List<Currency>) {
//        val diffResult = DiffUtil.calculateDiff(CurrenciesDiffUtils(currencies, newCurrencies))
//        diffResult.dispatchUpdatesTo(this)
        this.currencies = newCurrencies
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CurrenciesViewHolder {
        val context = parent.context
        val view = LayoutInflater.from(context).inflate(R.layout.content_currency_item, parent, false)
        return CurrenciesViewHolder(view)
    }

    override fun onBindViewHolder(holder: CurrenciesViewHolder, position: Int) {
        holder.bind(currencies[position])
    }

    override fun getItemCount() = currencies.size
}
