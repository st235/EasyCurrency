package github.com.st235.easycurrency.utils

import androidx.annotation.AnyThread

typealias Observer<T> = (response: T) -> Unit

abstract class ObservableModel<T> {

    private val currencyObservers: MutableList<Observer<T>> = mutableListOf()

    @AnyThread
    fun addObserver(observer: Observer<T>) {
        currencyObservers.add(observer)
    }

    @AnyThread
    fun removeObserver(observer: Observer<T>) {
        currencyObservers.remove(observer)
    }

    @AnyThread
    protected fun notifyObservers(result: T) {
        for (observer in currencyObservers) {
            observer(result)
        }
    }
}
