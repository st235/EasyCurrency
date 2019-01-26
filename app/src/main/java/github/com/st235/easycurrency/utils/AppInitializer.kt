package github.com.st235.easycurrency.utils

import android.content.Context
import com.facebook.stetho.Stetho
import timber.log.Timber

interface AppInitializer {
    fun init(androidContext: Context)

    companion object {
        fun init(androidContext: Context,
                 isDebug: Boolean) {
            val appInitializer: AppInitializer =
                    if (isDebug) DebugInitializer() else ReleaseInitializer()
            appInitializer.init(androidContext)
        }
    }
}

class DebugInitializer: AppInitializer {
    override fun init(androidContext: Context) {
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(androidContext)
    }
}

class ReleaseInitializer: AppInitializer {
    override fun init(androidContext: Context) {
        Timber.plant(TimberReleaseTree())
    }
}
