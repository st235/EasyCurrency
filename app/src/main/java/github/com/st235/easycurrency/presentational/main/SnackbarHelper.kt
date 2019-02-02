package github.com.st235.easycurrency.presentational.main

import android.app.Activity
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import github.com.st235.easycurrency.R

class SnackbarHelper(private val activity: Activity) {
    private val rootView: View = activity.findViewById(android.R.id.content)
    @ColorInt private val actionColor: Int = ContextCompat.getColor(activity, R.color.colorSnackbarAction)
    private val snackbarCallback = object: BaseTransientBottomBar.BaseCallback<Snackbar>() {
        override fun onDismissed(transientBottomBar: Snackbar?, event: Int) {
            snackbar = null
        }
    }

    private var snackbar: Snackbar? = null

    private var isDismissedByUser = false
    private var lastUpdatedTime: Int = -1

    fun showSnackbar(hoursDelta: Int) {
        if (isDismissedByUser) {
            return
        }

        if (snackbar != null && snackbar!!.isShownOrQueued) {
            if (hoursDelta != lastUpdatedTime) {
                dismissDialog(false)
                lastUpdatedTime = hoursDelta
            } else {
                return
            }
        }

        if (snackbar != null && !snackbar!!.isShownOrQueued) {
            snackbar!!.removeCallback(snackbarCallback)
        }

        val hoursText = activity.resources.getQuantityString(R.plurals.all_rates_are_outdated_hours,
            hoursDelta, hoursDelta)
        val text = activity.getString(R.string.all_rates_are_outdated, hoursText)
        snackbar = Snackbar.make(rootView, text, Snackbar.LENGTH_INDEFINITE)
        snackbar!!.setActionTextColor(actionColor)
        snackbar!!.setAction(R.string.all_rates_are_outdated_dismiss) {
            dismissDialog(true)
        }

        snackbar!!.addCallback(snackbarCallback)

        snackbar!!.show()
    }

    private fun dismissDialog(isDismissedByUser: Boolean) {
        this.isDismissedByUser = isDismissedByUser
        snackbar?.removeCallback(snackbarCallback)
        snackbar?.dismiss()
        snackbar = null
    }
}
