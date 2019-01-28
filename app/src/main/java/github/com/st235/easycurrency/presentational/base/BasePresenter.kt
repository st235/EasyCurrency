package github.com.st235.easycurrency.presentational.base

open class BasePresenter<View> {
    protected var view: View? = null
    private set

    fun attachView(v: View) {
        view = v
        onAttachView(v)
    }

    protected open fun onAttachView(v: View) {}

    fun detachView() {
        onDetachView(view)
        view = null
    }

    protected open fun onDetachView(v: View?) {}
}
