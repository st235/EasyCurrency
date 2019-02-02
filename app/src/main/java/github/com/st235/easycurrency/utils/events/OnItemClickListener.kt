package github.com.st235.easycurrency.utils.events

interface OnItemClickListener<in R> {
    fun onItemClick(item: R, position: Int)
}
