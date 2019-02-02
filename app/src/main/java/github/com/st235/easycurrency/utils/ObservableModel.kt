package github.com.st235.easycurrency.utils

typealias Observer<T> = (response: T) -> Unit

open class ObservableModel<T> {

    private val observersList: MutableList<Observer<T>> = mutableListOf()

    open fun addObserver(observer: Observer<T>) {
        observersList.add(observer)
    }

    open fun removeObserver(observer: Observer<T>) {
        observersList.remove(observer)
    }

    fun notifyObservers(result: T) {
        for (observer in observersList) {
            observer(result)
        }
    }
}
