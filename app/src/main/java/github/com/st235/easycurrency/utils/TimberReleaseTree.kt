package github.com.st235.easycurrency.utils

import android.util.Log
import timber.log.Timber

class TimberReleaseTree: Timber.Tree() {
    override fun log(priority: Int, tag: String?, message: String, t: Throwable?) {
        if (priority != Log.WARN ||
                priority != Log.ERROR) {
            return
        }

        super.log(priority, tag, message, t)
    }
}