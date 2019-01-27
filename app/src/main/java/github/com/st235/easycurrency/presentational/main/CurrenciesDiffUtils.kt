package github.com.st235.easycurrency.presentational.main

import androidx.recyclerview.widget.DiffUtil
import github.com.st235.easycurrency.domain.Currency

class CurrenciesDiffUtils(
    private val oldOne: List<Currency>,
    private val newOne: List<Currency>): DiffUtil.Callback() {

    override fun getOldListSize() = oldOne.size

    override fun getNewListSize() = newOne.size

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) = false

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int): Boolean {
        val oldCurrency = oldOne[oldItemPosition]
        val newCurrency = newOne[newItemPosition]
        return oldCurrency.value == newCurrency.value
    }
}