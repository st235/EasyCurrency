package github.com.st235.easycurrency.utils.extensions

import android.content.res.Resources
import android.util.TypedValue
import androidx.annotation.Px

@Px
fun Int.toPx(): Int {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(),
            Resources.getSystem().getDisplayMetrics()
    ).toInt()
}

fun Float.toPx(): Float {
    return TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this,
            Resources.getSystem().getDisplayMetrics()
    )
}
