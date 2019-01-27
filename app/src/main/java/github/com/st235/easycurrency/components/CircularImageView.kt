package github.com.st235.easycurrency.components

import android.content.Context
import android.graphics.*
import android.graphics.Paint.ANTI_ALIAS_FLAG
import android.graphics.drawable.BitmapDrawable
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.View
import androidx.annotation.*
import github.com.st235.easycurrency.R
import timber.log.Timber


class CircularImageView: View {
    private val DEFAULT_PLACEHOLDER = ""

    private val paint = Paint(ANTI_ALIAS_FLAG)
    private val center = PointF()

    private var extraText = DEFAULT_PLACEHOLDER

    @DrawableRes
    private var drawableId = -1

    private var targetImage: Bitmap? = null

    @ColorInt
    private var textColor = Color.BLACK

    @Px
    private var textSize = 0

    @FloatRange(from = 0.0)
    private var radius: Float = 0.toFloat()


    constructor(context: Context) : this(context, null)
    constructor(context: Context, attrs: AttributeSet?) : this(context, attrs, 0)
    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    /**
     * Initialize current [CircularImageView] with attributes from xml
     */
    private fun init(context: Context, @Nullable attrs: AttributeSet?) {
        val ta = context.obtainStyledAttributes(attrs, R.styleable.CircularImageView)

        val drawableId = ta.getResourceId(R.styleable.CircularImageView_cl_foreground, -1)
        if (drawableId != -1) {
            loadDrawable(drawableId)
        }

        textColor = ta.getColor(R.styleable.CircularImageView_cl_text_color, Color.BLACK)
        textSize = ta.getDimensionPixelSize(R.styleable.CircularImageView_cl_text_size, 0)

        val t = ta.getString(R.styleable.CircularImageView_cl_text)
        extraText = if (t == null) extraText else t

        ta.recycle()
    }

    /**
     * Set extra text
     * @param extraText is a text which will be displayed at center of image view if exists
     */
    fun setExtraText(extraText: String) {
        this.extraText = extraText
        invalidate()
    }

    /**
     * Set current image drawable
     * @param drawableId is identifier of drawable which will be displayed at image view
     */
    fun setDrawable(drawable: Drawable) {
        targetImage = drawableToBitmap(drawable)
        targetImage = cropBitmap(targetImage)
        invalidate()
    }

    /**
     * Set current image drawable
     * @param drawableId is identifier of drawable which will be displayed at image view
     */
    fun setDrawable(bitmap: Bitmap) {
        targetImage = bitmap
        targetImage = cropBitmap(targetImage)
        invalidate()
    }

    /**
     * {@inheritDoc}
     */
    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val fontMetrics = paint.getFontMetrics()

        val textWidth = paint.measureText(extraText)
        val textHeight = -fontMetrics.top + fontMetrics.bottom

        val desiredWidth = Math.round(textWidth + paddingLeft + paddingRight)
        val desiredHeight = Math.round(textHeight * 2f + paddingTop + paddingBottom)

        val measuredWidth = reconcileSize(desiredWidth, widthMeasureSpec)
        val measuredHeight = reconcileSize(desiredHeight, heightMeasureSpec)

        setMeasuredDimension(measuredWidth, measuredHeight)
    }

    /**
     * Reconcile a desired size for the view contents with a [android.view.View.MeasureSpec]
     * constraint passed by the parent.
     *
     * This is a simplified version of [View.resolveSize]
     *
     * @param contentSize Size of the view's contents.
     * @param measureSpec A [android.view.View.MeasureSpec] passed by the parent.
     * @return A size that best fits `contentSize` while respecting the parent's constraints.
     */
    private fun reconcileSize(contentSize: Int, measureSpec: Int): Int {
        val mode = MeasureSpec.getMode(measureSpec)
        val specSize = MeasureSpec.getSize(measureSpec)
        when (mode) {
            MeasureSpec.EXACTLY -> return specSize
            MeasureSpec.AT_MOST -> {
                return if (contentSize < specSize) {
                    contentSize
                } else specSize
            }
            MeasureSpec.UNSPECIFIED -> return contentSize
            else -> return contentSize
        }
    }

    /**
     * {@inheritDoc}
     */
    override fun onDraw(canvas: Canvas) {
        paint.setStyle(Paint.Style.FILL)

        radius = Math.min(getMeasuredWidth() / 2.0f - getPaddingLeft() - getPaddingRight(),
                getMeasuredHeight() / 2.0f - getPaddingTop() - getPaddingBottom())
        center.set(getMeasuredWidth() / 2.0f, getMeasuredHeight() / 2.0f)

        if (targetImage == null && drawableId != -1) {
            loadDrawable(drawableId)
        }

        updateShader()
        canvas.drawCircle(center.x, center.y, radius, paint)

        paint.shader = null
        paint.color = textColor
        paint.textSize = textSize.toFloat()

        val textWidth = paint.measureText(extraText)
        val textY = (center.y - (paint.descent() + paint.ascent()) / 2)
        canvas.drawText(extraText, center.x - textWidth / 2, textY, paint)
    }

    /**
     * Loads targetImage to be shown into memory
     * @param drawableId which will be loaded as target
     */
    private fun loadDrawable(@DrawableRes drawableId: Int) {
        this.drawableId = drawableId
        val drawable = getResources().getDrawable(drawableId)
        targetImage = drawableToBitmap(drawable)
        targetImage = cropBitmap(targetImage)
    }

    /**
     * Updates paint shader with custom targetImage target
     */
    private fun updateShader() {
        if (targetImage == null) {
            return
        }

        val shader = BitmapShader(targetImage!!, Shader.TileMode.CLAMP, Shader.TileMode.CLAMP)

        val matrix = Matrix()
        matrix.setScale(getMeasuredWidth() as Float / targetImage!!.width.toFloat(),
                getMeasuredHeight() as Float / targetImage!!.height.toFloat())
        shader.setLocalMatrix(matrix)
        paint.setShader(shader)
    }

    /**
     * Creates center cropped targetImage from origin
     * @param bitmap which need to be cropped
     * @return targetImage instance
     */
    @Nullable
    @CheckResult
    private fun cropBitmap(@Nullable bitmap: Bitmap?): Bitmap? {
        if (bitmap == null) {
            return null
        }

        return if (bitmap.width >= bitmap.height) {
            Bitmap.createBitmap(
                    bitmap,
                    bitmap.width / 2 - bitmap.height / 2,
                    0,
                    bitmap.height, bitmap.height)
        } else Bitmap.createBitmap(
                bitmap,
                0,
                bitmap.height / 2 - bitmap.width / 2,
                bitmap.width, bitmap.width)

    }

    /**
     * Converts drawable to targetImage.
     * If the drawable has no intrinsic
     * width or height the laid out sizes will be set up as current viewport.
     * @param drawable which need to be shown
     * @return targetImage instance of drawable
     */
    @Nullable
    @CheckResult
    private fun drawableToBitmap(@Nullable drawable: Drawable?): Bitmap? {
        if (drawable == null) {
            return null
        }

        if (drawable is BitmapDrawable) {
            return drawable.bitmap
        }

        var intrinsicWidth = drawable.intrinsicWidth
        var intrinsicHeight = drawable.intrinsicHeight

        if (intrinsicWidth == -1 || intrinsicHeight == -1) {
            intrinsicWidth = getMeasuredWidth()
            intrinsicHeight = getMeasuredHeight()
        }

        if (intrinsicWidth <= 0 || intrinsicHeight <= 0) {
            return null
        }

        try {
            val bitmap = Bitmap.createBitmap(intrinsicWidth, intrinsicHeight, Bitmap.Config.ARGB_8888)
            val canvas = Canvas(bitmap)
            drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight())
            drawable.draw(canvas)
            return bitmap
        } catch (e: OutOfMemoryError) {
            Timber.e(e, "OutOfMemory while creating targetImage!")
            return null
        }

    }
}