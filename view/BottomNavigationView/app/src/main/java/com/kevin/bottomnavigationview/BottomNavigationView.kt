package com.kevin.bottomnavigationview

import android.content.Context
import android.content.res.Resources
import android.graphics.Typeface
import android.os.Build
import android.support.annotation.ColorInt
import android.support.constraint.ConstraintLayout
import android.support.v4.view.ViewCompat
import android.util.AttributeSet
import android.util.Log
import android.util.TypedValue
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.LinearLayout
import kotlinx.android.synthetic.main.bottom_navigation_item.view.*
import java.util.*

/**
 * Created by quf93 on 2018-04-12.
 */
class BottomNavigationView : FrameLayout {

    private val TAG = "JUWONLEE"

    private var con: Context? = null
    private var res: Resources? = null

    private var layoutHeight: Float = 0.0f

    private var container: LinearLayout? = null

    private var items = ArrayList<BottomNavigationItem>()
    private var views = ArrayList<View>()

    private var currentItem: Int = -1

    var textTypeface: Typeface? = null

    var textInactiveSize: Float = 0.0f
    var textActiveSize: Float = 0.0f

    /* TODO
    @ColorInt
    var textActiveColor: Int = 0
    @ColorInt
    var textInactiveColor: Int = 0
    */

    @ColorInt
    var activeColor: Int = 0
    @ColorInt
    var inactiveColor: Int = 0

    var onTabSelectedListener: OnTabSelectedListener? = null

    init {

    }

    constructor(context: Context) : super(context) {
        init(context, null)
    }

    constructor(context: Context, attrs: AttributeSet?) : super(context, attrs) {
        init(context, attrs)
    }

    constructor(context: Context, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        init(context, attrs)
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        val width = measuredWidth
        val height = measuredHeight
        Log.d(TAG, "onMeasure : $width, $height")
    }

    private fun init(context: Context, attrs: AttributeSet?) {
        con = context
        res = context.resources

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            activeColor = res!!.getColor(R.color.colorActive, null)
            inactiveColor = res!!.getColor(R.color.colorInactive, null)
        }

        layoutHeight = res!!.getDimension(R.dimen.bottom_navigation_height)

        ViewCompat.setElevation(this, res!!.getDimension(R.dimen.bottom_navigation_elevation))

        /*
        val params = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, layoutHeight.toInt())
        layoutParams = params
        */
    }

    fun setCurrentItem(position: Int) {
        if(position >= items.size || position < 0) {
            Log.w(TAG, "out of bounds")
            return
        }

        updateItem(position)
    }

    fun addItem(item: BottomNavigationItem) {
        this.items.add(item)

        drawItems()
    }

    fun addItems(items: List<BottomNavigationItem>) {
        this.items.addAll(items)

        drawItems()
    }

    /**
     * draw items on the bottom navigation layout
     */
    private fun drawItems() {
        if(items.size < 3) Log.w(TAG, "should have at least 3 items")
        else if(items.size > 5) Log.w(TAG, "should not have more than 5 items")

        Log.d(TAG, "Draw Items " + items.size)

        // 0.
        clearLayout()
        setContainer()

        // 1.
        addItemViews()

        requestLayout()
    }

    private fun clearLayout() {
        removeAllViews()
        views.clear()
    }

    /**
     * set item container on the bottom navigation layout
     */
    private fun setContainer() {
        container = LinearLayout(con)
        container!!.orientation = LinearLayout.HORIZONTAL
        container!!.gravity = Gravity.CENTER

        val layoutParams = FrameLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, layoutHeight.toInt())
        addView(container, layoutParams)
    }

    /**
     * add item views on the container
     */
    private fun addItemViews() {
        val inflater = con!!.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

        val height = res!!.getDimension(R.dimen.bottom_navigation_height)

        val width = width // layout width
        val minWidth = res!!.getDimension(R.dimen.bottom_navigation_item_min_width)
        val maxWidth = res!!.getDimension(R.dimen.bottom_navigation_item_max_width)

        Log.d(TAG, "width : " + width)

        if(parent !is ConstraintLayout)
            if(width == 0 || items.size == 0) return


        var itemWidth = (width / items.size).toFloat()

        if(itemWidth < minWidth) itemWidth = minWidth
        else if(itemWidth > maxWidth) itemWidth = maxWidth

        var activeTextSize = res!!.getDimension(R.dimen.bottom_navigation_active_text_size)
        var inactiveTextSize = res!!.getDimension(R.dimen.bottom_navigation_inactive_text_size)

        if(textActiveSize > 0) activeTextSize = textActiveSize
        if(textInactiveSize > 0) inactiveTextSize = textInactiveSize

        Log.d(TAG, "ats : $activeTextSize, iats : $inactiveTextSize")
        //TODO text color

        for(item in items) {
            val index = items.indexOf(item)
            val view = inflater.inflate(R.layout.bottom_navigation_item, this, false)

            view.icon.setImageResource(item.drawableRes)
            // view.icon.setColorFilter(item.colorRes)

            view.title.text = item.title
            textTypeface?.let { view.title.typeface = textTypeface }

            view.setOnClickListener{updateItem(index)}

            if(index == currentItem) {
                view.isSelected = true

                view.icon.setColorFilter(activeColor)

                view.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, activeTextSize)
                view.title.setTextColor(activeColor)
            } else {
                view.isSelected = false

                view.icon.setColorFilter(inactiveColor)

                view.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, inactiveTextSize)
                view.title.setTextColor(inactiveColor)
            }

            val params = LayoutParams(itemWidth.toInt(), height.toInt())
            container!!.addView(view, params)
            views.add(view)
        }
    }

    private fun updateItem(position: Int) {
        if(currentItem == position) {
            onTabSelectedListener?.onTabSelected(position, true)
            return
        } else onTabSelectedListener?.onTabSelected(position, false)


        val activeTopMargin = res!!.getDimension(R.dimen.bottom_navigation_active_item_top_margin)
        val inactiveTopMargin = res!!.getDimension(R.dimen.bottom_navigation_inactive_item_top_margin)

        val activeTextSize = res!!.getDimension(R.dimen.bottom_navigation_active_text_size)
        val inactiveTextSize = res!!.getDimension(R.dimen.bottom_navigation_inactive_text_size)

        val activeView = views[position]
        val inactiveView = if(currentItem!=-1) views[currentItem] else null

        activeView.icon.setPadding(activeView.icon.paddingLeft, activeTopMargin.toInt(),
                activeView.icon.paddingRight, activeView.icon.paddingBottom)

        activeView.isSelected = true

        activeView.icon.setColorFilter(activeColor)

        activeView.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, activeTextSize)
        activeView.title.setTextColor(activeColor)

        inactiveView?.let {
            it.icon.setPadding(inactiveView.icon.paddingLeft, inactiveTopMargin.toInt(),
            it.icon.paddingRight, inactiveView.icon.paddingBottom)

            it.isSelected = false

            it.icon.setColorFilter(inactiveColor)

            it.title.setTextSize(TypedValue.COMPLEX_UNIT_PX, inactiveTextSize)
            it.title.setTextColor(inactiveColor)
        }

        currentItem = position
    }

    interface OnTabSelectedListener {
        fun onTabSelected(position: Int, wasSelected: Boolean) : Boolean
    }
}