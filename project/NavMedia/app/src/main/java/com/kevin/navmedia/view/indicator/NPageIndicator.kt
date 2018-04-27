package com.kevin.navmedia.view.indicator

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.support.v4.content.ContextCompat
import android.support.v4.view.ViewPager
import android.util.AttributeSet
import android.view.View
import com.kevin.navmedia.R

/**
 * Created by quf93 on 2018-04-14.
 */
class NPageIndicator @JvmOverloads constructor(
        context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0
        ): View(context, attrs, defStyleAttr), PageIndicator {

    private val mBackgroundPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mFillPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private var mViewPager: ViewPager? = null
    private var mScrollState: Int = -1

    private var mCount: Int = 0

    private var mIndicatorRadius: Float = 0f
    private var mCurrentIndicatorRadius: Float = 0f
    private var mDistance: Float = 0f
    private var mCurrentPage: Int = 0

    init {
        val res = context.resources

        val activeColor = ContextCompat.getColor(context, R.color.Active)
        val inactiveColor = ContextCompat.getColor(context, R.color.Inactive)

        mDistance = res.getDimension(R.dimen.double_space)

        mIndicatorRadius = res.getDimension(R.dimen.indicator_radius)
        mCurrentIndicatorRadius = res.getDimension(R.dimen.current_indicator_radius)

        mBackgroundPaint.style = Paint.Style.FILL
        mBackgroundPaint.color = inactiveColor

        mFillPaint.style = Paint.Style.FILL
        mFillPaint.color = activeColor
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(measureWidth(widthMeasureSpec), measureHeight(heightMeasureSpec))
    }

    private fun measureWidth(widthMeasureSpec: Int): Int {
        var result: Int = 0

        val specMode = View.MeasureSpec.getMode(widthMeasureSpec)
        val specSize = View.MeasureSpec.getSize(widthMeasureSpec)

        if(specMode == MeasureSpec.EXACTLY || mViewPager == null) result = specSize
        else {
            val count = mViewPager?.adapter?.count!!
            result = ( paddingLeft + paddingRight +
                    (count * 2 * mIndicatorRadius) + ((count - 1) * mDistance) ).toInt()

            if(specMode == MeasureSpec.AT_MOST) result = Math.min(specSize, result)
        }
        return result
    }

    private fun measureHeight(heightMeasureSpec: Int): Int {
        var result: Int = 0

        val specMode = View.MeasureSpec.getMode(heightMeasureSpec)
        val specSize = View.MeasureSpec.getSize(heightMeasureSpec)

        if(specMode == MeasureSpec.EXACTLY || mViewPager == null) result = specSize
        else {
            result = ( paddingTop + paddingBottom +
                    (2 * mIndicatorRadius) + 1 ).toInt()

            if(specMode == MeasureSpec.AT_MOST) result = Math.min(specSize, result)
        }
        return result
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        if(mViewPager == null) return

        val widthOffset = paddingLeft + mIndicatorRadius
        val heightOffset = paddingTop + mIndicatorRadius

        val count = mViewPager?.adapter?.count!!

        for (i in 0 until count) {
            canvas?.drawCircle(
                    widthOffset + i * mDistance,
                    heightOffset + mIndicatorRadius,
                    mIndicatorRadius,
                    mBackgroundPaint)
        }
        mCount = count

        canvas?.drawCircle(
                widthOffset + mCurrentPage * mDistance,
                heightOffset + mIndicatorRadius,
                mCurrentIndicatorRadius,
                mFillPaint
        )
    }

    override fun setViewPager(viewPager: ViewPager) {
        if(mViewPager == viewPager) return
        // if(mViewPager != null) mViewPager?.clearOnPageChangeListeners()
        if (viewPager.adapter == null) throw IllegalStateException("ViewPager does not have adapter instance.")

        mViewPager = viewPager
        mViewPager?.addOnPageChangeListener(this)
        invalidate()
    }

    override fun setCurrentPage(currentPage: Int) {
        if(mViewPager == null) throw IllegalStateException("ViewPager has not been bound.")

        mViewPager?.currentItem = currentPage
        mCurrentPage = currentPage
        invalidate()
    }

    override fun onPageScrollStateChanged(state: Int) {
        mScrollState = state
    }


    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
        mCurrentPage = position
        invalidate()
    }

    override fun onPageSelected(position: Int) {
        /**
         * Indicates that the pager is in an idle, settled state. The current page
         * is fully in view and no animation is in progress.
         */
        if(mScrollState == ViewPager.SCROLL_STATE_IDLE) mCurrentPage = position
        invalidate()
    }

}