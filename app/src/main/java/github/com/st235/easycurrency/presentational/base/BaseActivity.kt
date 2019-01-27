package github.com.st235.easycurrency.presentational.base

import android.os.Bundle
import android.view.WindowManager
import androidx.annotation.IdRes
import androidx.annotation.LayoutRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar


abstract class BaseActivity: AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON)

        onBeforeInit()

        super.onCreate(savedInstanceState)
        setContentView(getLayout())

        onInitViews()
        onViewsInitialized(savedInstanceState)
    }

    @LayoutRes
    protected abstract fun getLayout(): Int

    /***
     * Calls before all checks
     * Note: use it for important checks (splash screen, for example)
     */
    protected open fun onBeforeInit() {}

    /***
     * Calls before any another methods call.
     * Needs to bind views variables and layout representation
     */
    protected open fun onInitViews() {}

    /**
     * Calls when views initialized to setup
     * @param savedInstanceState state
     */
    protected open fun onViewsInitialized(savedInstanceState: Bundle?) {}

    protected fun setToolbar(@IdRes toolbarId: Int, isHomeEnabled: Boolean) {
        val toolbar = findViewById<Toolbar>(toolbarId)
        setSupportActionBar(toolbar)
        supportActionBar!!.setDisplayHomeAsUpEnabled(isHomeEnabled)
    }
}