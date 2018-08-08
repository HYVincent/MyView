package com.jycs.myview.widget

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/7/31 13:31
 */
class MyView(context: Context?, attrs: AttributeSet?) : ViewGroup(context, attrs) {

    private lateinit var mPaint : Paint
    private var mDownX : Float = 0f
    private var mDownY : Float = 0f
    private var mMoveX : Float = 0f
    private var mMoveY : Float = 0f
    private var mOffset : Float = 0f

    init {
        init()
    }

    private fun init() {
        mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
        mPaint.color = Color.BLACK
        mPaint.style = Paint.Style.FILL
        mPaint.textSize = 100f
    }

    override fun onFinishInflate() {
        super.onFinishInflate()


    }

    override fun onInterceptTouchEvent(ev: MotionEvent?): Boolean {
        return true
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec)

        for (i in 0 until 10){

        }

        for (i in 0 until childCount){

        }
    }

    override fun onLayout(changed: Boolean, l: Int, t: Int, r: Int, b: Int) {
        if (childCount > 2) {
            throw IllegalStateException("只能有两个孩子")
        }
        getChildAt(0).layout(l, t, r, b)

        var width = getChildAt(1).layoutParams.width
        getChildAt(1).layout(r, t, r + width, b)

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {

        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                mDownX = event.rawX
                mDownY = event.rawY
                mMoveX = event.rawX
                mMoveY = event.rawY

            }
            MotionEvent.ACTION_MOVE -> {
                var dx = event.rawX - mMoveX
                var dy = event.rawY - mMoveY

                mOffset += dx
                scrollBy((-dx).toInt(), 0)

                mMoveX = event.rawX
                mMoveY = event.rawY

            }
            MotionEvent.ACTION_UP -> {

            }
        }


        return true
    }



}