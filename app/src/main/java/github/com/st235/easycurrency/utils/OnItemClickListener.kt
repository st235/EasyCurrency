package github.com.st235.easycurrency.utils

interface OnItemClickListener<in R> {
    fun onItemClick(item: R, position: Int)
}