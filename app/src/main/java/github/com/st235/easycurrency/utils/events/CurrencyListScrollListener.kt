package github.com.st235.easycurrency.utils.events

import androidx.recyclerview.widget.RecyclerView

/**
 * Interface for all the objects that can be scrolled
 */
interface ScrollableAdapter {
    var isScrolling: Boolean
}

/**
 * The entity that monitors the state of the scroll
 */
class CurrencyListScrollListener(private val adapter: ScrollableAdapter,
                                 private val threshold: Int = 0):
        RecyclerView.OnScrollListener() {
    override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
        val mdy = Math.abs(dy)
        adapter.isScrolling = mdy > threshold
    }

    override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
        if (newState == RecyclerView.SCREEN_STATE_OFF ||
                newState == RecyclerView.SCROLL_STATE_IDLE) {
            adapter.isScrolling = false
        }
    }
}
