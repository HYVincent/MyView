package com.jycs.myview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.animation.LinearInterpolator;
import android.widget.TextView;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/8/2 11:39
 */

public class MarqueeView extends TextView{

    private float mTextWidth;
    private float mFraction;

    public MarqueeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        ValueAnimator animator = ValueAnimator.ofFloat(0, 1);
        animator.setInterpolator(new LinearInterpolator());
        animator.setDuration(5000);
        animator.setRepeatCount(ValueAnimator.INFINITE);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.addUpdateListener(mAnimatorUpdateListener);
        animator.start();
    }

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {


        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mFraction = animation.getAnimatedFraction();
            postInvalidate();
        }
    };

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        mTextWidth = getPaint().measureText(getText().toString());
    }

    @Override
    public void setTextSize(int unit, float size) {
        super.setTextSize(unit, size);
        mTextWidth = getPaint().measureText(getText().toString());
    }

    @Override
    protected void onDraw(Canvas canvas) {

//        int offset = (int) ((mTextWidth - getWidth()) * mFraction);
//        canvas.save();
//        canvas.translate(-offset, 0);
        super.onDraw(canvas);
//        scrollTo(offset, 0);

//        canvas.restore();
    }
}
