package github.com.st235.easycurrency

import android.app.Application
import github.com.st235.easycurrency.di.appModules
import org.koin.android.ext.android.startKoin
import timber.log.Timber

class EasyCurrencyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())
        startKoin(this, appModules)
    }
}