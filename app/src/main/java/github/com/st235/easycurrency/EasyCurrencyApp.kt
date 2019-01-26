package github.com.st235.easycurrency

import android.app.Application
import github.com.st235.easycurrency.di.appModules
import github.com.st235.easycurrency.utils.AppInitializer
import org.koin.android.ext.android.startKoin

class EasyCurrencyApp: Application() {
    override fun onCreate() {
        super.onCreate()

        AppInitializer.init(this, BuildConfig.DEBUG)
        startKoin(this, appModules)
    }
}