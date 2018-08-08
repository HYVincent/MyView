package com.jycs.myview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Shader;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.AccelerateInterpolator;
import android.widget.TextView;

/**
 * Created by chong on 2017/9/3.
 */

public class MyTextView extends TextView {

    private Paint mPaint;

    private ValueAnimator mValueAnimator;

    private float mVal;

    private int[] colors = {Color.RED,Color.YELLOW};


    public MyTextView(Context context) {
        this(context, null);

    }

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
//        mPaint.setColor(Color.RED);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShader(new LinearGradient(0,0,1080,0,colors,null, Shader.TileMode.CLAMP));
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SCREEN));


        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(5000);
        mValueAnimator.setInterpolator(new AccelerateInterpolator());
        mValueAnimator.addUpdateListener(mAnimatorUpdateListener);
        mValueAnimator.start();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);

    }

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator valueAnimator) {
            mVal = ((Float) valueAnimator.getAnimatedValue());
            postInvalidate();
        }
    };



    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawARGB(0 ,0, 0, 0);
        super.onDraw(canvas);
        canvas.drawRect(0,0,getWidth() * mVal,getHeight(),mPaint);
    }
}
