package com.jycs.myview.widget;

import android.app.Service;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.Typeface;
import android.icu.text.UFormat;
import android.support.annotation.Nullable;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/8/3 10:56
 */

public class ChartView extends View {

    private float mAtuoScaleX, mAtuoScaleY;
    private static final int MAX_COUNT_X = 7;
    private static final long MAX_DAY_MILLIS = 3600 * 24 * 1000;

    //适配所需的缩放比例

    private float mTextSize;
    private float mCharHeight;
    private float mLeftMargin;
    private float mRightMargin;
    private float mBaseLineBottomMargin;
    private float mContentTopMargin;
    private float mGapX;
    //深睡颜色
    private int mDeepSleepColor = Color.parseColor("#5BA584");
    //浅睡颜色
    private int mLightSleepColor = Color.parseColor("#7CC7A6");


    private TextPaint mTextPaint;
    private Paint mLinePaint;

    private List<ChartBean> mList = new ArrayList<>();
    private Paint mContentPaint;
    private RectF mRectF;


    public ChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        Display display = ((WindowManager) getContext().getSystemService(Service.WINDOW_SERVICE)).getDefaultDisplay();
        DisplayMetrics metrics = new DisplayMetrics();
        display.getMetrics(metrics);

        mAtuoScaleX = metrics.widthPixels * 1.0f / 750;
        mAtuoScaleY = metrics.widthPixels * 1.0f / 1334;

        initValue();

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setColor(0xffEFEFEF);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setAntiAlias(true);

        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(0xFFDFDFDF);
        mTextPaint.setTypeface(Typeface.create(Typeface.SANS_SERIF, Typeface.BOLD));
        mTextPaint.setTextAlign(Paint.Align.CENTER);

        mContentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContentPaint.setAntiAlias(true);

        mRectF = new RectF();
    }

    private void initValue() {
        mTextSize = 24 * mAtuoScaleX;
        mCharHeight = 530 * mAtuoScaleY;
        mLeftMargin = 50 * mAtuoScaleX;
        mRightMargin = 50 * mAtuoScaleX;
        mBaseLineBottomMargin = 50 * mAtuoScaleX;
        mContentTopMargin = 70 * mAtuoScaleY;
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(widthMeasureSpec, (int) mCharHeight);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mGapX = (w - mLeftMargin - mRightMargin) / (MAX_COUNT_X - 1);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawLine(canvas);

        drawText(canvas);

        drawContent(canvas);
    }


    private void drawLine(Canvas canvas) {
        mLinePaint.setStrokeWidth(6 * mAtuoScaleY);

        float sx = mLeftMargin;
        float sy = getHeight() - mBaseLineBottomMargin;
        float ex = getWidth() - mRightMargin;
        float ey = getHeight() - mBaseLineBottomMargin;

        canvas.drawLine(sx, sy, ex, ey, mLinePaint);

        for (int i = 0; i < MAX_COUNT_X; i++) {
            sx = mLeftMargin + i * mGapX;
            sy = getHeight() - mBaseLineBottomMargin + 10 * mAtuoScaleY;
            ex = mLeftMargin + i * mGapX;
            ey = getHeight() - mBaseLineBottomMargin + 25 * mAtuoScaleY;
            canvas.drawLine(sx, sy, ex, ey, mLinePaint);
        }


        mLinePaint.setStrokeWidth(1);
        for (int i = 0; i < MAX_COUNT_X; i++) {
            sx = mLeftMargin + i * mGapX;
            sy = 0;
            ex = mLeftMargin + i * mGapX;
            ey = getHeight() - mBaseLineBottomMargin;
            canvas.drawLine(sx, sy, ex, ey, mLinePaint);
        }
    }

    private void drawText(Canvas canvas) {
        for (int i = 0; i < MAX_COUNT_X; i++) {
            float x = mLeftMargin + i * mGapX;
            float y = getHeight() - mBaseLineBottomMargin + 30 * mAtuoScaleY + mTextSize;
            String text = (i * 4 < 10 ? "0" + i * 4 : String.valueOf(i * 4)) + ":00";
            canvas.drawText(text, x, y, mTextPaint);
        }
    }

    private void drawContent(Canvas canvas) {
        for (int i = 0; i < mList.size(); i++) {
            mContentPaint.setColor(mList.get(i).getSleepStatus() == 1 ? mDeepSleepColor : mLightSleepColor);
            mRectF.left = findX(mList.get(i).getStartTime());
            mRectF.top = mContentTopMargin;
            mRectF.right = findX(mList.get(i).getEndTime());
            mRectF.bottom = getHeight() - mBaseLineBottomMargin;
            canvas.drawRect(mRectF, mContentPaint);
        }
    }

    private float findX(String startTime) {
        return (float) (getTime(startTime) * 1.0 / MAX_DAY_MILLIS * (getWidth() - mLeftMargin - mRightMargin) + mLeftMargin);
    }

    private long getTime(String startTime){
        String hour = startTime.split(" ")[1].split(":")[0];
        String minute = startTime.split(" ")[1].split(":")[1];
        String second = startTime.split(" ")[1].split(":")[2];

        Calendar calendar = Calendar.getInstance();
        calendar.clear();
        calendar.set(Calendar.HOUR_OF_DAY, Integer.parseInt(hour));
        calendar.set(Calendar.MINUTE, Integer.parseInt(minute));
        calendar.set(Calendar.SECOND, Integer.parseInt(second));

        return calendar.getTimeInMillis();
    }


    public void setData(List<ChartBean> data){
        mList.clear();
        if (data != null) mList.addAll(data);
        invalidate();
    }

    /**
     * 获取睡眠小时
     * @return
     */
    public int getSleepHour(){
        long millis = 0;
        for (ChartBean chartBean : mList) {
            millis += getTime(chartBean.getEndTime()) - getTime(chartBean.getStartTime());
        }
        return (int) (millis / 1000 / 3600);
    }

    /**
     * 获取睡眠分钟
     * @return
     */
    public int getSleepMin(){
        long millis = 0;
        for (ChartBean chartBean : mList) {
            millis += getTime(chartBean.getEndTime()) - getTime(chartBean.getStartTime());
        }
        return (int) (millis / 1000 / 60 % 60);
    }

    /**
     * 获取深睡小时
     * @return
     */
    public int getDeepSleepHour(){
        long millis = 0;
        for (ChartBean chartBean : mList) {
            if (chartBean.getSleepStatus() != 1) continue;
            millis += getTime(chartBean.getEndTime()) - getTime(chartBean.getStartTime());
        }
        return (int) (millis / 1000 / 3600);
    }

    /**
     * 获取深睡分钟
     * @return
     */
    public int getDeepSleepMin(){
        long millis = 0;
        for (ChartBean chartBean : mList) {
            if (chartBean.getSleepStatus() != 1) continue;
            millis += getTime(chartBean.getEndTime()) - getTime(chartBean.getStartTime());
        }
        return (int) (millis / 1000 / 60 % 60);
    }
}
