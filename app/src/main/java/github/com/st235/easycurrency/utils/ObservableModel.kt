package github.com.st235.easycurrency.utils

import androidx.annotation.AnyThread

typealias Observer<T> = (response: T) -> Unit

abstract class ObservableModel<T> {

    private val observersList: MutableList<Observer<T>> = mutableListOf()

    @AnyThread
    fun addObserver(observer: Observer<T>) {
        observersList.add(observer)
    }

    @AnyThread
    fun removeObserver(observer: Observer<T>) {
        observersList.remove(observer)
    }

    @AnyThread
    protected fun notifyObservers(result: T) {
        for (observer in observersList) {
            observer(result)
        }
    }
}
