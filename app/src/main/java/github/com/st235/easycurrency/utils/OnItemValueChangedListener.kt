package github.com.st235.easycurrency.utils

interface OnItemValueChangedListener<in V, in R> {
    fun onItemValueChanged(value: V, item: R, position: Int)
}