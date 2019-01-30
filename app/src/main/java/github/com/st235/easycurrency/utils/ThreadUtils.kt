package github.com.st235.easycurrency.utils

import android.os.Looper
import androidx.annotation.AnyThread
import androidx.annotation.MainThread
import androidx.annotation.WorkerThread
import github.com.st235.easycurrency.BuildConfig
import timber.log.Timber

object ThreadUtils {
    @MainThread
    fun assertOnMainThread() {
        assertThat(Looper.getMainLooper().thread == Thread.currentThread(),
                "Does not on main thread! Thread: ${Thread.currentThread().name}")
        Timber.v("Thread name: ${Thread.currentThread().name}")
    }

    @WorkerThread
    fun assertOnBackgroundThread() {
        assertThat(Looper.getMainLooper().thread != Thread.currentThread(),
                "Does not on background thread! Thread: ${Thread.currentThread().name}")
        Timber.v("Thread name: ${Thread.currentThread().name}")
    }

    @AnyThread
    private fun assertThat(condition: Boolean,
                           message: String) {
        if (condition || !BuildConfig.DEBUG) {
            return
        }

        throw IllegalStateException(message)
    }
}