package github.com.st235.easycurrency

import android.app.Application
import timber.log.Timber

class EasyCurrencyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
    }
}