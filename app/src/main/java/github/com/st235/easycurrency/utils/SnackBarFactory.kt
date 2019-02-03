package github.com.st235.easycurrency.utils

import android.content.Context
import android.view.View
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import com.google.android.material.snackbar.BaseTransientBottomBar
import com.google.android.material.snackbar.Snackbar
import github.com.st235.easycurrency.R

class SnackBarFactory(context: Context) {
    @ColorInt
    private val actionColor: Int = ContextCompat.getColor(context, R.color.colorSnackbarAction)

    private val resources = context.resources

    fun createRatesExpiresSnackBar(rootView: View, hoursDelta: Int,
                                   dismissCallback: BaseTransientBottomBar.BaseCallback<Snackbar>,
                                   actionCallback: (v: View) -> Unit): Snackbar {
        val hoursText = resources.getQuantityString(R.plurals.all_rates_are_outdated_hours, hoursDelta, hoursDelta)
        val text = resources.getString(R.string.all_rates_are_outdated, hoursText)

        val snackBar = Snackbar.make(rootView, text, Snackbar.LENGTH_INDEFINITE)
        snackBar.setActionTextColor(actionColor)
        snackBar.setAction(R.string.all_rates_are_outdated_dismiss, actionCallback)

        snackBar.addCallback(dismissCallback)
        return snackBar
    }
}