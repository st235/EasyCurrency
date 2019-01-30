package github.com.st235.easycurrency.utils

typealias Observer<T> = (response: T) -> Unit

abstract class ObservableModel<T> {

    private val observersList: MutableList<Observer<T>> = mutableListOf()

    fun addObserver(observer: Observer<T>) {
        observersList.add(observer)
    }

    fun removeObserver(observer: Observer<T>) {
        observersList.remove(observer)
    }

    protected fun notifyObservers(result: T) {
        for (observer in observersList) {
            observer(result)
        }
    }
}
