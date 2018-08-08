package com.jycs.myview.widget;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.support.annotation.StringDef;
import android.support.annotation.UiThread;
import android.text.TextPaint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;


import com.jycs.myview.ListUtils;
import com.jycs.myview.TimeUtils;
import com.jycs.myview.WindowsInfo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/5/10 14:58
 */

public class DeviceChartView extends BaseEdgeEffect {

    public static final String UNIT_TEMP = "℃";
    public static final String UNIT_CURRENT = "A";
    public static final String UNIT_VOLTAGE = "V";
    public static final String UNIT_POWER = "W";
    private int mStartPos;
    private int mCount;
    private float mPercentage;

    @StringDef({UNIT_TEMP, UNIT_CURRENT, UNIT_VOLTAGE, UNIT_POWER})
    public @interface UnitType{}

    private static final int[] DEFAULT_UNIT_Y_STRING = {150, 140, 130, 120, 110, 100, 90, 80, 70, 60, 50, 40, 30, 20, 10};
    private int[] UNIT_Y_STRING = {150, 140, 130, 120, 110, 100, 90, 80, 70, 60, 50, 40, 30, 20, 10};

    private static final int DEFAULT_COUNT_X = 6;

    private int mCountY = 15;
    private float mCountX = 6;
    private int mMaxVal = UNIT_Y_STRING[0];

    private int mChartWidht;
    private int mChartHeight;
    private int mBitmapWidht;
    private int mBitmapHeight;
    private final int mChartTopPadding = (int) (2 * WindowsInfo.sAutoScaleX);
    private final int mChartLeftPadding = (int) (20 * WindowsInfo.sAutoScaleX);

    private final float mMinGapX = 55 * WindowsInfo.sAutoScaleX;
    private final float mTextLeftX = 60 * WindowsInfo.sAutoScaleX;
    private final float mTextSize = 20 * WindowsInfo.sAutoScaleY;
    private final int mTextColor = Color.parseColor("#c2c2c2");
    private final int mLineColor = Color.parseColor("#F0F0F0");
    private final int mContentColor = Color.parseColor("#2293FE");
    private final int mMaxValueColor = Color.parseColor("#FEB449");

    private final float mChartLeftMargin = 60 * WindowsInfo.sAutoScaleX;
    private final float mChartTopMargin = 55 * WindowsInfo.sAutoScaleY;
    private final float mTextArea = 30 * WindowsInfo.sAutoScaleY;
    private float mGAP_X;
    private float mGAP_Y;

    private final float mCirclePlus = 11f * WindowsInfo.sAutoScaleX;
    private final float mCircleMini = 5f * WindowsInfo.sAutoScaleX;

    private float mTagRight = 55 * WindowsInfo.sAutoScaleX;
    private float mTagWidth = 50 * WindowsInfo.sAutoScaleX;
    private final float mTagHeight = 30 * WindowsInfo.sAutoScaleY;
    private final float mTagVertexWidth = 6 * WindowsInfo.sAutoScaleX;
    private final float mTagFillet = 3 * WindowsInfo.sAutoScaleX;

    private final float mIndexWidht = 200 * WindowsInfo.sAutoScaleX;
    private final float mIndexHeight = 85 * WindowsInfo.sAutoScaleX;

    private float mNormFillet;

    private TextPaint mTextPaint, mNormPaint, mTagPaint;
    private Canvas mChartCanvas;
    private float mMaxValue;
    private Bitmap mBitmap;
    private Path mPath;
    private Point[] mPoints;
    private RectF mRectf;
    private Paint mSolidPaint, mContentPaint, mLinePaint;

    private List<String> mList = new ArrayList<>();

    public enum StateOrder {
        day, week, month
    }

    private StateOrder mTimeType = StateOrder.day;
    private long mStartTime;
    private long mTimeGap; //秒
    private Paint mCirclePaint;
    private int mPosition = -1;
    private boolean mIsNeedInvalid;
    private String mStandard;
    private String mUnit = UNIT_TEMP;
    private String mUnitName = "温度";

    public DeviceChartView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mTextPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTextPaint.setAntiAlias(true);
        mTextPaint.setTextSize(mTextSize);
        mTextPaint.setColor(mTextColor);

        mLinePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mLinePaint.setAntiAlias(true);
        mLinePaint.setColor(mLineColor);
        mLinePaint.setStyle(Paint.Style.STROKE);
        mLinePaint.setStrokeWidth(1);

        mContentPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mContentPaint.setAntiAlias(true);
        mContentPaint.setColor(mContentColor);
        mContentPaint.setStyle(Paint.Style.STROKE);
        mContentPaint.setStrokeWidth(2);

        mSolidPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mSolidPaint.setAntiAlias(true);
        mSolidPaint.setColor(0x192293FE);

        mCirclePaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mCirclePaint.setAntiAlias(true);
        mCirclePaint.setStrokeWidth(2);

        mTagPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mTagPaint.setAntiAlias(true);
        mTagPaint.setTextSize(mTextSize);
        mTagPaint.setTextAlign(Paint.Align.CENTER);
        mTagPaint.setStrokeWidth(1);

        mNormPaint = new TextPaint(Paint.ANTI_ALIAS_FLAG);
        mNormPaint.setAntiAlias(true);
        mNormPaint.setTextSize(mTextSize);

        mPath = new Path();
        mRectf = new RectF();

        setBackgroundColor(Color.WHITE);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mChartWidht = MeasureSpec.getSize(widthMeasureSpec);
        mChartHeight = MeasureSpec.getSize(heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBitmapWidht = (int) (w * 0.85);
        mBitmapHeight = (int) (h * 0.8);
        mBitmap = Bitmap.createBitmap(mBitmapWidht, mBitmapHeight, Bitmap.Config.ARGB_8888);
        mChartCanvas = new Canvas(mBitmap);

        //横屏显示14, 竖屏显示6个
        mCountX = w > h ? 14 : 6;
        mGAP_X = (mBitmapWidht - mCirclePlus * 2) / mCountX;
        mGAP_Y = (mBitmapHeight - mTextArea) / mCountY;

        mNormFillet = w * 0.85f;

        //如果setData在量测之前已经调用, 那么等量测完之后再重新设置一次数据
        if (mIsNeedInvalid){
            mIsNeedInvalid = false;
            setData(Arrays.asList((mList.toArray(new String[]{}))));
        }
    }

    public void setXCount(int count){
        mCountX = count < DEFAULT_COUNT_X + 1 ? DEFAULT_COUNT_X + 1 : count;
        mGAP_X = (mBitmapWidht - mCirclePlus * 2) / mCountX;
    }

    @Override
    protected void onDrawContent(Canvas canvas) {

        drawUint(canvas);

        drawBitmap(canvas);
    }


    private void drawUint(Canvas canvas) {
        mTextPaint.setTextAlign(Paint.Align.RIGHT);
        for (int i = 0; i < UNIT_Y_STRING.length; i++) {
            float x = mTextLeftX;
            float y = mChartTopMargin - (mTextPaint.ascent() + mTextPaint.baselineShift) / 2 + i * mGAP_Y;
            canvas.drawText(String.valueOf(UNIT_Y_STRING[i]), x, y, mTextPaint);
        }
    }

    private void drawBitmap(Canvas canvas) {
        mChartCanvas.drawColor(Color.TRANSPARENT, PorterDuff.Mode.CLEAR);
        //保存
        mChartCanvas.save();
        //平移
        mChartCanvas.translate(mOffsetX, 0);
        //绘制
        drawLineX();
        drawLineY();
        drawSolid();
        drawContent();
        drawCircle();
        drawText();
        //重置
        mChartCanvas.restore();

        canvas.drawBitmap(mBitmap, mChartLeftMargin, mChartTopMargin, null);

        drawNormLine(canvas);
        drawNorm(canvas);
        drawMaxValue(canvas);
        drawIndex(canvas);
    }


    private void drawLineX() {
        if (mGAP_X < mMinGapX) return;
        for (int i = 0; i < mCountX; i++) {
            float startX = mChartLeftPadding + mGAP_X * i;
            float endX = startX;
            float startY = mChartTopPadding;
            float endY = mCountY * mGAP_Y + mChartTopPadding;
            mChartCanvas.drawLine(startX, startY, endX, endY, mLinePaint);
        }
    }

    private void drawLineY() {
        for (int i = 0; i < mCountY; i++) {
            float startX = mChartLeftPadding;
            float endX = (mList.size() <= mCountX ? mCountX + 1 : mList.size()) * mGAP_X;
            float startY = mChartTopPadding + i * mGAP_Y;
            float endY = mChartTopPadding + i * mGAP_Y;
            mChartCanvas.drawLine(startX, startY, endX, endY, mLinePaint);
        }
    }

    /**
     * 填充色
     */
    private void drawSolid() {

        mPath.reset();

        float startX = mChartLeftPadding;
        float startY = mChartTopPadding + mCountY * mGAP_Y;
        mPath.moveTo(startX, startY);

        int startPos = (int) Math.abs(mOffsetX / mGAP_X);
        int count = startPos + mCountX + 2 < mList.size() ? (int) (startPos + mCountX + 2) : mList.size();
        for (int i = startPos; i < count; i++) {
            findMaxVal((int) Float.parseFloat(mList.get(i)));
            float value = Float.parseFloat(mList.get(i));
            float x = mChartLeftPadding + i * mGAP_X;
            float y = mCountY * mGAP_Y - value / mMaxVal * (mCountY * mGAP_Y);


            float endX = mChartLeftPadding + i * mGAP_X;
            float endY = mCountY * mGAP_Y - value / mMaxVal * (mCountY * mGAP_Y);
            float p1x = (endX - startX) / 2 + startX;
            float p1y = startY;
            float p2x = (endX - startX) / 2 + startX;
            float p2y = endY;

            startX = endX;
            startY = endY;
            mPath.cubicTo(p1x, p1y, p2x, p2y, endX, endY);

            if (i == count - 1){
                mPath.lineTo(x, mBitmapHeight - mTextArea);
                mPath.close();
            }
        }
        mChartCanvas.drawPath(mPath, mSolidPaint);
    }


    /**
     * 画折线
     */
    private void drawContent() {

        mPath.reset();
        float startX = mChartLeftPadding;
        float startY = mChartTopPadding + mCountY * mGAP_Y;
        mPath.moveTo(startX, startY);

        int startPos = (int) Math.abs(mOffsetX / mGAP_X);
        int count = startPos + mCountX + 2 < mList.size() ? (int) (startPos + mCountX + 2) : mList.size();

        for (int i = startPos; i < count; i++) {
            float value = Float.parseFloat(mList.get(i));
            float endX = mChartLeftPadding + i * mGAP_X;
            float endY = mCountY * mGAP_Y - value / mMaxVal * (mCountY * mGAP_Y);
            float p1x = (endX - startX) / 2 + startX;
            float p1y = startY;
            float p2x = (endX - startX) / 2 + startX;
            float p2y = endY;

            startX = endX;
            startY = endY;
            mPath.cubicTo(p1x, p1y, p2x, p2y, endX, endY);
        }
        mChartCanvas.drawPath(mPath, mContentPaint);
    }


    private void drawText() {

        mTextPaint.setTextAlign(Paint.Align.CENTER);

        if (mList.size() == 0) {
            for (int i = 0; i < DEFAULT_COUNT_X + 1; i++) {
                float x = (mBitmapWidht - mCirclePlus * 4) / (DEFAULT_COUNT_X + 1) * i + mChartLeftPadding * 2;
                float y = mBitmapHeight;
                String text = "";

                if (mTimeType == StateOrder.day){
                    text = (i * 4 / 10 == 0 ? "0" + i * 4 : i * 4) + ":00";
                }
                mChartCanvas.drawText(text, x, y, mTextPaint);
            }
            return;
        }


        if (mGAP_X < mMinGapX) {
            for (int i = 0; i < DEFAULT_COUNT_X + 2; i++) {
                float x = (mList.size() * mGAP_X - mCirclePlus * 4) / (DEFAULT_COUNT_X  + 1) * i + mChartLeftPadding * 2;
                float y = mBitmapHeight;
                String timeFormat = mTimeType == StateOrder.day ? "HH:mm" : "MM-dd";
                String text = TimeUtils.getDate(timeFormat, mStartTime + mList.size() * mTimeGap / (DEFAULT_COUNT_X + 1) * i);
                mChartCanvas.drawText(text, x, y, mTextPaint);
            }
            return;
        }

        int startPos = (int) Math.abs(mOffsetX / mGAP_X);
        int count = startPos + mCountX + 2 < mList.size() ? (int) (startPos + mCountX + 2) : mList.size();
        for (int i = startPos; i < count; i++) {
            float x = (mGAP_X * i) + mChartLeftPadding;
            float y = mBitmapHeight;
            String timeFormat = mTimeType == StateOrder.day ? "HH:mm" : "MM-dd";
            String text = TimeUtils.getDate(timeFormat, mStartTime + (i * mTimeGap));
            mChartCanvas.drawText(text, x, y, mTextPaint);
        }
    }

    private void drawCircle(){

        if (mGAP_X < mMinGapX) return;

        for (int mStartPos = 0; mStartPos < mCount; mStartPos++) {
            mPoints[mStartPos].x = 0;
            mPoints[mStartPos].y = 0;
        }

        mStartPos = (int) Math.abs(mOffsetX / mGAP_X);
        mCount = mStartPos + mCountX + 2 < mList.size() ? (int) (mStartPos + mCountX + 2) : mList.size();
        for (int i = mStartPos; i < mCount; i++) {
            float value = Float.parseFloat(mList.get(i));
            float x = mChartLeftPadding + i * mGAP_X;
            float y = mCountY * mGAP_Y - value / mMaxVal * (mCountY * mGAP_Y);

            mPoints[i].x = (int)(x + mOffsetX);
            mPoints[i].y = (int) y;

            mCirclePaint.setStyle(Paint.Style.FILL);
            mCirclePaint.setColor(Color.WHITE);
            mChartCanvas.drawCircle(x, y, mCirclePlus, mCirclePaint);

            mCirclePaint.setColor(mMaxValue == value ? mMaxValueColor : mContentColor);
            mChartCanvas.drawCircle(x, y, mCircleMini, mCirclePaint);

            mCirclePaint.setStyle(Paint.Style.STROKE);
            mChartCanvas.drawCircle(x, y, mCirclePlus, mCirclePaint);
        }
    }

    private void drawNormLine(Canvas canvas) {
        float value = Float.parseFloat(mStandard == null ? "0" : mStandard);
        float y = mCountY * mGAP_Y - value / mMaxVal * (mCountY * mGAP_Y) + mChartTopMargin;

        mRectf.left = (int) (mTagRight - mTagWidth);
        mRectf.right = (int) mTagRight;
        mRectf.top = (int) (y - mTagHeight / 2);
        mRectf.bottom = (int) (y + mTagHeight / 2);

        mPath.reset();
        mPath.moveTo(mRectf.right + mTagVertexWidth, mRectf.centerY());
        mPath.lineTo(mRectf.right, mRectf.centerY() - mTagVertexWidth / 2);
        mPath.lineTo(mRectf.right, mRectf.centerY() + mTagVertexWidth / 2);
        mPath.close();

        mTagPaint.setColor(mContentColor);
        mTagPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(mRectf, mTagFillet, mTagFillet, mTagPaint);
        canvas.drawPath(mPath, mTagPaint);

        String text = String.valueOf((int)value) + mUnit;
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);

        mTagPaint.setColor(Color.WHITE);
        canvas.drawText(text, mRectf.centerX(), mRectf.centerY() + rect.height() / 2, mTagPaint);

        mTagPaint.setStyle(Paint.Style.STROKE);
        mTagPaint.setColor(mContentColor);
        canvas.drawLine(mRectf.right + mTagVertexWidth * 2, mRectf.centerY(), mChartLeftMargin + mBitmapWidht, mRectf.centerY(), mTagPaint);
    }

    private void drawNorm(Canvas canvas) {
        mNormPaint.setTextAlign(Paint.Align.RIGHT);
        float x = mBitmapWidht + mChartLeftMargin + mChartLeftPadding;
        float y = mTagHeight / 2 - (mNormPaint.ascent() + mNormPaint.baselineShift) / 2;
        mNormPaint.setColor(Color.BLACK);
        canvas.drawText("标准值", x, y, mNormPaint);


        mRectf.left = (int) (mNormFillet - mTagWidth);
        mRectf.right = (int) mNormFillet;
        mRectf.top = 0;
        mRectf.bottom = mTagHeight;

        mPath.reset();
        mPath.moveTo(mRectf.right + mTagVertexWidth, mRectf.centerY());
        mPath.lineTo(mRectf.right, mRectf.centerY() - mTagVertexWidth / 2);
        mPath.lineTo(mRectf.right, mRectf.centerY() + mTagVertexWidth / 2);
        mPath.close();

        mTagPaint.setColor(mContentColor);
        mTagPaint.setStyle(Paint.Style.FILL);
        canvas.drawRoundRect(mRectf, mTagFillet, mTagFillet, mTagPaint);
        canvas.drawPath(mPath, mTagPaint);

        mTagPaint.setColor(Color.WHITE);

        String text = (mStandard == null ? "" : mStandard) + mUnit;
        Rect rect = new Rect();
        mTextPaint.getTextBounds(text, 0, text.length(), rect);
        canvas.drawText(text, mRectf.centerX(), mRectf.centerY() + rect.height() / 2, mTagPaint);
    }

    private void drawMaxValue(Canvas canvas) {
        mNormPaint.setTextAlign(Paint.Align.LEFT);
        float x = mBitmapWidht * 0.75f;
        float y = mTagHeight / 2 - (mNormPaint.ascent() + mNormPaint.baselineShift) / 2;
        mNormPaint.setColor(Color.BLACK);
        canvas.drawText("最大值", x, y, mNormPaint);


        x = mBitmapWidht * 0.75f - mNormPaint.measureText("最大值") / 2;
        y = mTagHeight / 2;
        mCirclePaint.setStyle(Paint.Style.FILL);
        mCirclePaint.setColor(Color.WHITE);
        canvas.drawCircle(x, y, mCirclePlus, mCirclePaint);

        mCirclePaint.setColor(mMaxValueColor);
        canvas.drawCircle(x, y, mCircleMini, mCirclePaint);

        mCirclePaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(x, y, mCirclePlus, mCirclePaint);
    }


    private void drawIndex(Canvas canvas){
        if (mPosition == -1 || mGAP_X < mMinGapX) return;
        float x = mPoints[mPosition].x + mChartLeftMargin + mChartTopPadding;
        float y = mPoints[mPosition].y + mChartTopMargin - mCirclePlus;

        mRectf.left = x - mIndexWidht / 2;
        mRectf.right = x + mIndexWidht / 2;
        mRectf.bottom = y - mTagVertexWidth * 2;
        mRectf.top = mRectf.bottom - mIndexHeight;

        mPath.moveTo(mRectf.centerX(), mRectf.bottom + mTagVertexWidth);
        mPath.lineTo(mRectf.centerX() + mTagVertexWidth, mRectf.bottom);
        mPath.lineTo(mRectf.centerX() - mTagVertexWidth, mRectf.bottom);
        mPath.close();

        mTagPaint.setColor(mContentColor);
        mTagPaint.setStyle(Paint.Style.FILL);
        canvas.drawPath(mPath, mTagPaint);
        canvas.drawRoundRect(mRectf, mIndexHeight / 10, mIndexHeight / 10, mTagPaint);

        mTagPaint.setStyle(Paint.Style.FILL);
        mTagPaint.setColor(Color.WHITE);

        String text = TimeUtils.getDate(TimeUtils.DATE_FORMAT_2, mStartTime + mPosition * mTimeGap);
        canvas.drawText(text, x, mRectf.centerY() - (mRectf.centerY() - mRectf.top) / 4, mTagPaint);

        canvas.drawText(mUnitName + " : " + mList.get(mPosition) + mUnit, x, mRectf.bottom - (mRectf.bottom - mRectf.centerY()) / 3, mTagPaint);
    }


    @Override
    protected void onDrawEdgeEffectLeft(Canvas canvas) {
        super.onDrawEdgeEffectLeft(canvas);
        canvas.translate(0, mChartLeftMargin);
    }

    @Override
    public void onClick(MotionEvent event) {
        float x = event.getX() - mChartLeftPadding - mChartLeftMargin ;
        float y = event.getY() - mChartTopMargin;

        for (int i = 0; i < mPoints.length; i++) {
            if (Math.pow(x - mPoints[i].x, 2) + Math.pow(y - mPoints[i].y, 2) <= Math.pow(CLICK_RADIUS, 2)){
                mPosition = i;
                return;
            }
        }
    }

    @Override
    public void onDrag(MotionEvent event, float dx, float dy) {
        super.onDrag(event, dx, dy);
        mPosition = -1;
    }

    @Override
    public void pointerMove(MotionEvent event) {
        super.pointerMove(event);
    }

    @Override
    public void pointerDown(MotionEvent event) {
        super.pointerDown(event);
    }

    @Override
    public void pointerUp(MotionEvent event) {
        super.pointerUp(event);
    }

    @Override
    public void up(MotionEvent event) {
        mPosition = -1;
        super.up(event);
    }

    @Override
    public void onScale(ScaleDetector detector) {
        if (mOffsetX == 0 && super.mMaxVal != 0){
            mPercentage = -((detector.centerX - mChartLeftMargin) / (super.mMaxVal + mBitmapWidht));
        }else {
            mPercentage = super.mMaxVal == 0 ? 0 : mOffsetX / super.mMaxVal;
        }
        mCountX = mCountX + mCountX * -detector.scaleX * 2;
        mCountX = mCountX > mList.size() ? mList.size() : mCountX;
        mCountX = mCountX < DEFAULT_COUNT_X + 1 ? DEFAULT_COUNT_X + 1 : mCountX;
        mGAP_X = (mBitmapWidht - mCirclePlus * 2) / mCountX;
        float maxOffsetX = mList.size() * mGAP_X < mCountX * mGAP_X ? 0 : Math.abs(mList.size() - mCountX) * mGAP_X;
        setMaxVal((int) maxOffsetX);

        mOffsetX = mPercentage == 0 ? 0 : super.mMaxVal * mPercentage;
    }

    @UiThread
    public void setData(@Nullable List<String> list){
        mList.clear();
        mMaxValue = 0;
        if (list != null){
            for (int i = 0; i < list.size(); i++) {
                mList.add(list.get(i));
                mMaxValue = mMaxValue < Float.parseFloat(list.get(i)) ? Float.parseFloat(list.get(i)) : mMaxValue;
            }
        }
        setXCount(mList.size());
        float maxOffsetX = mList.size() * mGAP_X < mCountX * mGAP_X ? 0 : Math.abs(mList.size() - mCountX) * mGAP_X;
        setMaxVal((int) maxOffsetX);
        UNIT_Y_STRING = DEFAULT_UNIT_Y_STRING.clone();
        mMaxVal = UNIT_Y_STRING[0];
        mOffsetX = 0;
        mIsNeedInvalid = true;
        mPosition = -1;
        mPoints = new Point[mList.size()];
        for (int i = 0; i < mPoints.length; i++) {
            mPoints[i] = new Point();
        }
        invalidate();
    }

    public void setParam(StateOrder timeType, long startTime, int timeGap, String standard) {
        mStartTime = startTime;
        mTimeType = timeType;
        mTimeGap = timeGap;
        mStandard = standard;
        mTagRight = (standard != null && standard.length() >= 4 ? 70 : 55) * WindowsInfo.sAutoScaleX;
        mTagWidth = (standard != null && standard.length() >= 4 ? 65 : 50) * WindowsInfo.sAutoScaleX;
        findMaxVal(mStandard == null ? 0 : Integer.parseInt(mStandard));
        invalidate();
    }


    public void setUnit(@UnitType String unit){
        mUnit = unit;
        switch (mUnit) {
            case UNIT_TEMP:
                mUnitName = "温度";
                break;
            case UNIT_POWER:
                mUnitName = "功率";
                break;
            case UNIT_CURRENT:
                mUnitName = "电流";
                break;
            case UNIT_VOLTAGE:
                mUnitName = "电压";
                break;
        }

        invalidate();
    }


    private void findMaxVal(int num) {
        if (num > mMaxVal){
            mMaxVal += DEFAULT_UNIT_Y_STRING[0];
            if (num > mMaxVal) {
                findMaxVal(num);
                return;
            }
            for (int i = 0; i < UNIT_Y_STRING.length; i++) {
                UNIT_Y_STRING[i] = mMaxVal / UNIT_Y_STRING.length * (UNIT_Y_STRING.length - i);
                invalidate();
            }
        }
    }
}
