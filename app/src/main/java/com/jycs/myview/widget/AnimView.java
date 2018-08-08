package com.jycs.myview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.LinearInterpolator;

import com.jycs.myview.R;


/**
 * Created by chong on 2017/10/9.
 */

public class AnimView extends View{


    private Bitmap mBitmap1;
    private Bitmap mBitmap2;
    private Rect mSrcRect;
    private Paint mPaint;
    private Bitmap mBitmap;
    private int mBitmapWidth;
    private int mBitmapHeight;

    private final int BITMAP_START_Y = 200;
    private final int BITMAP_END_Y = 800;

    private int mCenterX;
    private int mCenterY;

    private final int LIGHT_WIDE = 20;

    private int mLightStartX = 0;

    private final int BITMAP_SHOW_WIDE = 500;

    private final int BITMAP_SHOW_HEIGHT = 300;

    private int mWide;

    private float mVal;
    private Rect mDstRect;
    private ValueAnimator mValueAnimator;

    private float mDownX,mDownY;

    public AnimView(Context context) {
        this(context, null);
    }

    public AnimView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mBitmap1 = BitmapFactory.decodeResource(getResources(), R.drawable.a);
        mBitmap2 = BitmapFactory.decodeResource(getResources(), R.drawable.b);

        mBitmapWidth = mBitmap1.getWidth();
        mBitmapHeight = mBitmap1.getHeight();

        mPaint = new Paint();

        initAnim();
    }

    private void initAnim() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(2000);
        mValueAnimator.setRepeatMode(ValueAnimator.RESTART);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.addUpdateListener(mUpdateListener);
    }

    private ValueAnimator.AnimatorUpdateListener mUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mVal = animation.getAnimatedFraction();
            postInvalidate();
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        mWide = MeasureSpec.getSize(widthMeasureSpec);
        int height = MeasureSpec.getSize(heightMeasureSpec);

        mCenterX = mWide / 2;
        mCenterY = height / 2;
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        mSrcRect = new Rect((int)(mWide * mVal), 0, (int)(mWide * mVal) + LIGHT_WIDE, mBitmapHeight);
        mDstRect = new Rect((int)(mWide * mVal), BITMAP_START_Y, (int)(mWide * mVal) + LIGHT_WIDE, BITMAP_END_Y);
        Rect src = new Rect(0,0,mBitmapWidth,mBitmapHeight);
        Rect dst = new Rect(0, BITMAP_START_Y,mWide,BITMAP_END_Y);

        canvas.drawBitmap(mBitmap1, src, dst, null);


        if (mVal <= 0) return;

        Bitmap circleBitmap = getCircleBitmap();
        mPaint.reset();

        float startX = mDownX - mVal * mWide / 2;
        float startY = mDownY - mVal * (BITMAP_END_Y - BITMAP_START_Y) / 2;


        float endX =  + circleBitmap.getWidth() + startX;
        float endY =  + circleBitmap.getHeight() + startY;

        startX = endX > mWide ? startX - (endX - mWide) : startX;
        startY = endY > BITMAP_END_Y ? startY - (endY - BITMAP_END_Y) : startY;

        startX = startX < 0 ? 0 : startX;
        startY = startY < BITMAP_START_Y ? BITMAP_START_Y : startY;

        canvas.drawBitmap(circleBitmap,startX,startY,mPaint);
    }

    private Bitmap getCircleBitmap() {

        mPaint.setColor(Color.RED);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.FILL);

        Bitmap bitmap = Bitmap.createBitmap((int)(mVal * mWide), (int)(mVal * (BITMAP_END_Y - BITMAP_START_Y)), Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(bitmap);
        canvas.drawARGB(0,0,0,0);

        canvas.drawCircle(bitmap.getWidth() / 2, bitmap.getHeight() / 2, bitmap.getHeight() / 2,mPaint);
        mPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        Rect src = new Rect(0,0,mBitmap2.getWidth(),mBitmap2.getHeight());
        Rect dst = new Rect(0,0,bitmap.getWidth(),bitmap.getHeight());
        canvas.drawBitmap(mBitmap2,src,dst,mPaint);
        return bitmap;

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        if (event.getAction() == MotionEvent.ACTION_UP) {
            if (event.getY() > BITMAP_START_Y && event.getY() < BITMAP_END_Y){
                mDownX = event.getX();
                mDownY = event.getY();
                mValueAnimator.start();
            }
        }

        return true;
    }
}
