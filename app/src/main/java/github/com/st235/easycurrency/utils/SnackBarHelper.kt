package github.com.st235.easycurrency.utils

import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import github.com.st235.easycurrency.R

class SnackBarHelper(activity: Activity,
                     private val snackBarFactory: SnackBarFactory) {
    private val rootView: View = activity.findViewById(android.R.id.content)
    private val snackbarCallback = object: BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            snackbar?.removeCallback(this)
            snackbar = null
        }
    }

    private var snackbar: Snackbar? = null

    private var isDismissedByUser = false
    private var lastUpdatedTime: Int = -1

    fun show(hoursDelta: Int,
             isExpired: Boolean) {
        if (isDismissedByUser) {
            return
        }

        if (!isVisible() && !isExpired) {
            return
        }

        if (isVisible() && !isExpired) {
            dismissDialog(false)
            return
        }

        if (isVisible()) {
            if (hoursDelta != lastUpdatedTime) {
                dismissDialog(false)
                lastUpdatedTime = hoursDelta
            } else {
                return
            }
        }

        lastUpdatedTime = hoursDelta
        snackbar = snackBarFactory.createRatesExpiresSnackBar(rootView, hoursDelta, snackbarCallback) {
            dismissDialog(true)
        }

        snackbar!!.show()
    }

    private fun isVisible(): Boolean {
        if (snackbar == null) {
            return false
        }

        return snackbar!!.isShownOrQueued
    }

    private fun dismissDialog(isDismissedByUser: Boolean) {
        this.isDismissedByUser = isDismissedByUser
        snackbar?.removeCallback(snackbarCallback)
        snackbar?.dismiss()
        snackbar = null
    }
}
