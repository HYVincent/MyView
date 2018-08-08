package com.jycs.myview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.ColorFilter;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PixelFormat;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.os.Handler;
import android.support.annotation.IntRange;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

/**
 * Created by chong on 2017/8/17.
 */

public class LabellingLinearLayout extends LinearLayout {

    private DisplayMetrics dm;

    private final int OFF = 1;

    private final int ON = 2;

    private Context mContext;

    private final int ROW = 12;

    private final int COLUMN = 3;

    private float mChildViewSize = 26.0f;

    private float mChildPadding = 0;

    private int mTextViewBackgroundOFF = Color.parseColor("#FFFFFF");

    private int mTextViewBackgroundON = Color.parseColor("#EEEEEE");

    private float TITLE_TEXTVIEW_HIDE = 20;

    private int mWide;

    private int mHide;

    private String mText;

    private int mTextColor = Color.parseColor("#FFFFFF");

    private int mTextSize = 12;

    private int mTitleBackgroundColor = Color.parseColor("#FF0000");

    private int mLabellingTextSize = 9;

    private int mLabellingTextColor = 0xff888888;

    private GradientDrawable mGradientDrawable;

    private int mMin;

    private int mMax = 12;

    private int mCount = -1;

    private OnTVClickListener mOnTVClickListener;
    private GridLayout mGridLayout;

    public LabellingLinearLayout(Context context) {
        this(context,null);
    }

    public LabellingLinearLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mContext = getContext();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        setMeasuredDimension(dpiToPx(mChildViewSize * COLUMN),dpiToPx(mChildViewSize * ROW + TITLE_TEXTVIEW_HIDE));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWide = w;
        mHide = h;
        setMeasuredDimension(dpiToPx(mChildViewSize * COLUMN),dpiToPx(mChildViewSize * ROW + TITLE_TEXTVIEW_HIDE));
    }

    public LabellingLinearLayout setTitleText(String text) {
        mText = text;
        return this;
    }

    public LabellingLinearLayout setLabelling(int startNum){
        mCount = startNum;
        mMin= startNum;
        return this;
    }

    public LabellingLinearLayout setOnTVClickListener(OnTVClickListener onClickListener){
        mOnTVClickListener = onClickListener;
        return this;
    }

    public LabellingLinearLayout setLabellingTextColor(int color){
        mLabellingTextColor = color;
        return this;
    }

    public LabellingLinearLayout setTitleBackgroundColor(String color){
        return setTitleBackgroundColor(Color.parseColor(color));
    }

    public LabellingLinearLayout setTitleBackgroundColor(int color){
        mTitleBackgroundColor = color;
        return this;
    }

    public LabellingLinearLayout setLabellingTextColor(String color){
        return setLabellingTextColor(Color.parseColor(color));
    }

    public LabellingLinearLayout setLabellingTextSize(int size){
        mLabellingTextSize = size;
        return this;
    }

    public LabellingLinearLayout setTitleTextColor(String color){
        return setTitleTextColor(Color.parseColor(color));
    }

    public LabellingLinearLayout setTitleTextColor(int color){
        mTextColor = color;
        return this;
    }

    public LabellingLinearLayout setTitleTextSize(int size){
        mTextSize = size;
        return this;
    }

    public LabellingLinearLayout create(){
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                initView();
            }
        },200);
        return this;
    }

    private void initView(){
        removeAllViews();

        mGradientDrawable = new GradientDrawable();
        mGradientDrawable.setShape(GradientDrawable.RECTANGLE);
        mGradientDrawable.setStroke(2,mTitleBackgroundColor);

        setOrientation(LinearLayout.VERTICAL);
        setBackgroundDrawable(mGradientDrawable);

        LayoutParams layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,dpiToPx(TITLE_TEXTVIEW_HIDE));
        TextView titleTextView = new TextView(mContext);
        titleTextView.setText(mText);
        titleTextView.setGravity(Gravity.CENTER);
        titleTextView.setTextSize(mTextSize);
        titleTextView.setTextColor(mTextColor);
        titleTextView.setBackgroundColor(mTitleBackgroundColor);
        addView(titleTextView,layoutParams);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParams.setMargins(1,0,1,1);
        RelativeLayout relativeLayout = new RelativeLayout(mContext);
        addView(relativeLayout,layoutParams);

        layoutParams = new LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, dpiToPx(mChildViewSize * ROW));
        layoutParams.setMargins(1,0,1,1);
        mGridLayout = new GridLayout(mContext);
        mGridLayout.setOrientation(GridLayout.HORIZONTAL);
        mGridLayout.setRowCount(ROW);
        mGridLayout.setColumnCount(COLUMN);

        View view = new View(mContext);
        view.setBackgroundDrawable(new DottedLineDrawable());
        RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        params.setMargins(1,0,1,1);
        relativeLayout.addView(mGridLayout,layoutParams);
        relativeLayout.addView(view,params);

        for (int i = 0; i < COLUMN * ROW; i++) {
            GridLayout.Spec rowSpec = GridLayout.spec(i % ROW, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i / ROW, 1f);
            GridLayout.LayoutParams layoutParams1 = new GridLayout.LayoutParams(rowSpec, columnSpec);
            layoutParams1.height = 0;
            layoutParams1.width = 0;
            TextView textView = new TextView(mContext);
            textView.setText(mCount == -1 ? "" : mCount >= (mMax + mMin) ? "" : String.valueOf(mCount++));
            textView.setGravity(Gravity.CENTER);
            textView.setTextColor(mLabellingTextColor);
            textView.setTextSize(mLabellingTextSize);
            textView.setTag(OFF);
            textView.setOnClickListener(mOnTextViewClickListener);
            mGridLayout.addView(textView,layoutParams1);
        }
    }

    private OnClickListener mOnTextViewClickListener = new OnClickListener() {
        @Override
        public void onClick(View view) {
            if (mOnTVClickListener != null)mOnTVClickListener.onClick(LabellingLinearLayout.this, view, ((int) getTag()), mGridLayout.indexOfChild(view));
            if (((int) view.getTag()) == OFF) {
                view.setBackgroundColor(mTextViewBackgroundON);
                view.setTag(ON);
            }else {
                view.setBackgroundColor(mTextViewBackgroundOFF);
                view.setTag(OFF);
            }
        }
    };


    interface OnTVClickListener{
        void onClick(View parent, View childView, int parentPosition, int childPosition);
    }


    class DottedLineDrawable extends Drawable{

        private Paint mPaint;

        private Path mPath;

        private String mLineColor = "#BBBBBB";


        public DottedLineDrawable() {
            mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
            mPaint.setPathEffect(new DashPathEffect(new float[]{dpiToPx(3.0f), dpiToPx(2f)}, 0));
            mPaint.setStyle(Paint.Style.STROKE);
            mPaint.setColor(Color.parseColor(mLineColor));
            mPath = new Path();
        }

        @Override
        public void draw(@NonNull Canvas canvas) {

            for (int i = 0; i < ROW - 1; i++) {
                mPath.moveTo(dpiToPx(mChildPadding),dpiToPx(mChildViewSize * (i + 1)));
                mPath.lineTo(getWidth() - dpiToPx(mChildPadding),dpiToPx(mChildViewSize * (i + 1)));
                canvas.drawPath(mPath,mPaint);
            }
            for (int i = 0; i < COLUMN - 1; i++) {
                mPath.moveTo(dpiToPx(mChildViewSize * (i + 1)),dpiToPx(mChildPadding));
                mPath.lineTo(dpiToPx(mChildViewSize * (i + 1)),getHeight() - dpiToPx(mChildPadding));
                canvas.drawPath(mPath,mPaint);
            }

        }

        @Override
        public void setAlpha(@IntRange(from = 0, to = 255) int i) {

        }

        @Override
        public void setColorFilter(@Nullable ColorFilter colorFilter) {

        }

        @Override
        public int getOpacity() {
            return PixelFormat.OPAQUE;
        }
    }

    public int dpiToPx(float dpValue) {
        final float scale = mContext.getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
