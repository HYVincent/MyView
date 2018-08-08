package com.jycs.myview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

/**
 * Created by xjw on 2017/5/15.
 */

public class XView extends View implements View.OnClickListener {

    private Paint mPaint;
    private Path mPath;
    private float mCenterY;
    private float mWidth;
    private float mHeight;
    private float mWLenght = 1200;
    private int mCount;
    private int offSet;
    private int velocity;
    private ValueAnimator mValueAnimator;

    public XView(Context context) {
        super(context);
        init();
    }

    public XView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setColor(Color.GRAY);
        mPaint.setStyle(Paint.Style.FILL_AND_STROKE);
        mPaint.setStrokeWidth(2);
        mPath = new Path();
        setOnClickListener(this);

        initAnim();
    }

    private void initAnim() {
        mValueAnimator = new ValueAnimator().ofInt(0, (int) mWLenght);
        mValueAnimator.setRepeatCount(ValueAnimator.INFINITE);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                offSet = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mCenterY = h / 2;
        mWidth = w;
        mHeight = h;
        mCount = (int) Math.round(mWidth / mWLenght + 1.5);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        mPath.reset();
        mPath.moveTo(-mWLenght + offSet, mCenterY);
        for (int i = 0; i < mCount; i++) {
            mPath.quadTo(
                    -mWLenght / 4 * 3 + i * mWLenght + offSet, mCenterY + 60,
                    -mWLenght / 2 + i * mWLenght + offSet, mCenterY
            );
            mPath.quadTo(
                    -mWLenght / 4 + i * mWLenght + offSet, mCenterY - 60,
                    0 + i * mWLenght + offSet, mCenterY
            );
        }
        mPath.lineTo(mWidth, mHeight);
        mPath.lineTo(0, mHeight);
        mPath.close();
        canvas.drawPath(mPath, mPaint);
    }

    @Override
    public void onClick(View v) {
        velocity++;
        mValueAnimator.setDuration(1000 / velocity);
        mValueAnimator.start();
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
    }
}
