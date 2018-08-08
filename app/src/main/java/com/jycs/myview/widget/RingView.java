package com.jycs.myview.widget;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.LinearInterpolator;



/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/1/3 16:15
 */

public class RingView extends View {

    private RingViewConfig mConfig;
    private Paint mPaint;
    private int mWidth, mHeight;
    private int mCenterX, mCenterY;
    private float mFloat;
    private int mWidthPixels;

    private float mProgress;
    private ValueAnimator mValueAnimator;


    public RingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init(){

        //获取屏幕宽度
        WindowManager wm = (WindowManager)getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics outMetrics = new DisplayMetrics();
        wm.getDefaultDisplay().getMetrics(outMetrics);
        mWidthPixels = outMetrics.widthPixels;

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        setLayerType(LAYER_TYPE_SOFTWARE, null);


        RingViewConfig.Builder builder = new RingViewConfig.Builder();
        mConfig = builder.build();

        initAnim();
    }

    private void initAnim() {
        mValueAnimator = ValueAnimator.ofFloat(0, 1);
        mValueAnimator.setDuration(3500);
        mValueAnimator.setInterpolator(new LinearInterpolator());
        mValueAnimator.setStartDelay(2000);
        mValueAnimator.addUpdateListener(mAnimatorUpdateListener);
    }

    private ValueAnimator.AnimatorUpdateListener mAnimatorUpdateListener = new ValueAnimator.AnimatorUpdateListener() {
        @Override
        public void onAnimationUpdate(ValueAnimator animation) {
            mFloat = animation.getAnimatedFraction();
            postInvalidate();
        }
    };

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

        setMeasuredDimension(autoSize(460 + mConfig.getCircleSize() * 2), autoSize(188 + mConfig.getCircleSize() * 2));

        mWidth = autoSize(460 + mConfig.getCircleSize() * 2);
        mHeight = autoSize(188 + mConfig.getCircleSize() * 2);

        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawRing(canvas);

        drawContentText(canvas);

        drawCircle(canvas);

        drawCircleText(canvas);

    }


    /**
     * 绘制圆环
     * @param canvas
     */
    private void drawRing(Canvas canvas) {
        float circleSize = autoSize(mConfig.getCircleSize());
        mPaint.setStrokeWidth(autoSize(mConfig.getRingWidth()));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(mConfig.getRingColor());
        RectF rectF = new RectF(circleSize, circleSize , mWidth - circleSize, mWidth - circleSize);
        canvas.drawArc(rectF, 180 + mConfig.getRingStartAngle(), mConfig.getRingSweepAngle(), false, mPaint);

        mPaint.setColor(mConfig.getSolidColor());
        canvas.drawArc(rectF, 180 + mConfig.getRingStartAngle(), mConfig.getRingSweepAngle() * mProgress / 100 * mFloat, false, mPaint);
    }


    /**
     * 绘制中间文字
     * @param canvas
     */
    private void drawContentText(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextSize(autoSize(mConfig.getUnitTextSize()));
        mPaint.setColor(mConfig.getContentTextColor());
        mPaint.setTextAlign(Paint.Align.LEFT);

        String mContentText = String.valueOf(mProgress);

        Rect unitRect = new Rect();
        mPaint.getTextBounds(" %", 0, 2, unitRect);
        int unitWidth = unitRect.width();

        mPaint.setTextSize(autoSize(mConfig.getContentTextSize()));
        Rect contentRect = new Rect();
        mPaint.getTextBounds(mContentText, 0, mContentText.length(), contentRect);
        int contentWidth = contentRect.width();
        int contentHeight = contentRect.height();


        canvas.drawText(mContentText, mCenterX - (unitWidth + contentWidth) / 2, autoSize(mConfig.getContentTextMarginTop() + contentHeight + mConfig.getCircleSize()), mPaint);

        mPaint.setTextSize(autoSize(mConfig.getUnitTextSize()));
        canvas.drawText(" %", mCenterX - (unitWidth + contentWidth) / 2 + contentWidth, autoSize(mConfig.getContentTextMarginTop() + contentHeight + mConfig.getCircleSize()), mPaint);
    }


    /**
     * 绘制圆
     * @param canvas
     */
    private void drawCircle(Canvas canvas) {

        float circleSize = autoSize(mConfig.getCircleSize());

        mPaint.setColor(mConfig.getSolidColor());
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setShadowLayer(autoSize(mConfig.getCircleShadeSize()), 0, autoSize(mConfig.getCircleShadeOffsetY()), mConfig.getCircleShadeColor());

        canvas.save();
        canvas.rotate(mConfig.getRingStartAngle() + mConfig.getRingSweepAngle() * mProgress / 100 * mFloat , mCenterX, mCenterX);
        canvas.rotate(-(mConfig.getRingStartAngle() + mConfig.getRingSweepAngle() * mProgress / 100 * mFloat), circleSize, mCenterX);
        canvas.drawCircle(circleSize, mCenterX, circleSize, mPaint);
        canvas.restore();
        mPaint.clearShadowLayer();
    }


    /**
     * 绘制圆内文字
     * @param canvas
     */
    private void drawCircleText(Canvas canvas) {
        mPaint.setColor(mConfig.getCircleTextColor());
        mPaint.setTextSize(autoSize(mConfig.getCircleTextSize()));
        mPaint.setTextAlign(Paint.Align.CENTER);

        float circleSize = autoSize(mConfig.getCircleSize());
        String progress = (int)(mProgress * mFloat) + "%";

        Rect rect = new Rect();
        mPaint.getTextBounds(progress, 0, progress.length(), rect);

        canvas.save();
        canvas.rotate(mConfig.getRingStartAngle() + mConfig.getRingSweepAngle() * mProgress / 100 * mFloat, mCenterX, mCenterX);
        canvas.rotate(-(mConfig.getRingStartAngle() + mConfig.getRingSweepAngle() * mProgress / 100 * mFloat), circleSize, mCenterX);
        canvas.drawText(progress, circleSize, mCenterX + rect.height() / 2, mPaint);
        canvas.restore();

    }


    /**
     * 设置进度, 范围0~100
     * @param progress
     */
    public void setProgress(float progress){
        mProgress = progress > 100 ? 100 : progress < 0 ? 0 : progress;
        mValueAnimator.start();
    }

    public void setOptions(RingViewConfig options){
        mConfig = options;
        invalidate();
    }


    /**
     * 屏幕适配
     * @param v
     * @return
     */
    private int autoSize(float v){
        return (int) (v * mWidthPixels / 750);
    }
}
