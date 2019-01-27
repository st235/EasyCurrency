package github.com.st235.easycurrency.presentational.main

import androidx.recyclerview.widget.DiffUtil
import github.com.st235.easycurrency.domain.Currency

class CurrencyDiffUtilCallback(
    private val oldCurrenciesList: List<Currency>,
    private val newCurrenciesList: List<Currency>
): DiffUtil.Callback() {

    override fun areItemsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldCurrenciesList[oldItemPosition].id == newCurrenciesList[newItemPosition].id

    override fun areContentsTheSame(oldItemPosition: Int, newItemPosition: Int) =
        oldCurrenciesList[oldItemPosition] == newCurrenciesList[newItemPosition]

    override fun getOldListSize() = oldCurrenciesList.size

    override fun getNewListSize() = newCurrenciesList.size

}
