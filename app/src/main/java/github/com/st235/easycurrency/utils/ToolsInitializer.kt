package github.com.st235.easycurrency.utils

import android.content.Context
import com.facebook.stetho.Stetho
import timber.log.Timber

sealed class ToolsInitializer {
    abstract fun init(androidContext: Context)

    companion object {
        fun init(androidContext: Context,
                 isDebug: Boolean) {
            val toolsInitializer: ToolsInitializer =
                    if (isDebug) DebugInitializer else ReleaseInitializer
            toolsInitializer.init(androidContext)
        }
    }
}

object DebugInitializer : ToolsInitializer() {
    override fun init(androidContext: Context) {
        Timber.plant(Timber.DebugTree())
        Stetho.initializeWithDefaults(androidContext)
    }
}

object ReleaseInitializer : ToolsInitializer() {
    override fun init(androidContext: Context) {
        Timber.plant(TimberReleaseTree())
    }
}
