package com.jycs.myview.widget;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.view.View;
import android.widget.GridLayout;
import android.widget.ScrollView;

import java.util.List;

/**
 * Created by chong on 2017/8/18.
 */

public class MyBoView extends ScrollView {

    private Context mContext;

    private int mWide;

    private int mHide;

    private final int COLUMN = 4;

    private List<MyBoBean> mData;

    private OnMyBoClickListener mOnMyBoClickListener;
    private GridLayout mGridLayout;

    public MyBoView(Context context) {
        this(context,null);
    }

    public MyBoView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mContext = getContext();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWide = w;
        mHide = h;
    }

    public void setAdapder(List<MyBoBean> adapder){
        mData = adapder;
        initView();
    }

    public void setOnMyBoClickListener(OnMyBoClickListener onMyBoClickListener){
        mOnMyBoClickListener = onMyBoClickListener;
    }

    private void initView() {

        if (mData == null || mData.size() == 0)return;

        removeAllViews();

        mGridLayout = new GridLayout(mContext);
        mGridLayout.setOrientation(GridLayout.HORIZONTAL);
        mGridLayout.setColumnCount(COLUMN);

        for (int i = 0; i < mData.size(); i++) {
            GridLayout.Spec rowSpec = GridLayout.spec(i / COLUMN, 1f);
            GridLayout.Spec columnSpec = GridLayout.spec(i % COLUMN, 1f);
            GridLayout.LayoutParams layoutParams = new GridLayout.LayoutParams(rowSpec,columnSpec);
            layoutParams.setMargins(10,10,10,10);

            LabellingLinearLayout labellingLinearLayout = new LabellingLinearLayout(mContext)
                    .setTitleText(mData.get(i).getTitleText())
                    .setTitleTextSize(mData.get(i).getTitleTextSize())
                    .setTitleTextColor(mData.get(i).getTitleTextColor())
                    .setTitleBackgroundColor(mData.get(i).getTitleBackgroundColor())
                    .setLabelling(mData.get(i).getLabelling())
                    .setLabellingTextColor(mData.get(i).getLabellingTextColor())
                    .setLabellingTextSize(mData.get(i).getLabellingTextSize())
                    .setLabelling(mData.get(i).getLabelling())
                    .setOnTVClickListener(mOnTVClickListener)
                    .create();
            labellingLinearLayout.setTag(i);
            mGridLayout.addView(labellingLinearLayout,layoutParams);
        }
        addView(mGridLayout);
    }

    private LabellingLinearLayout.OnTVClickListener mOnTVClickListener = new LabellingLinearLayout.OnTVClickListener() {
        @Override
        public void onClick(View parent,View childView, int parentPosition,int childPosition) {
            if (mOnMyBoClickListener != null)mOnMyBoClickListener.onClick(parent,childView,parentPosition,childPosition);
        }
    };

    public interface OnMyBoClickListener{
        void onClick(View parent, View childView, int parentPosition, int childPosition);
    }


    public static class MyBoBean{

        private int mTextViewBackgroundOFF = Color.parseColor("#FFFFFF");

        private int mTextViewBackgroundON = Color.parseColor("#EEEEEE");

        private String mTitleText = "";

        private int mTitleTextSize = 12;

        private int mTitleTextColor = Color.parseColor("#FFFFFF");

        private int mTitleBackgroundColor = Color.parseColor("#FF0000");

        private int mLabellingTextSize = 9;

        private int mLabellingTextColor = 0xff888888;

        private int mLabelling = -1;

        public int getTitleTextColor() {
            return mTitleTextColor;
        }

        public void setTitleTextColor(int titleTextColor) {
            mTitleTextColor = titleTextColor;
        }

        public int getTextViewBackgroundOFF() {
            return mTextViewBackgroundOFF;
        }

        public void setTextViewBackgroundOFF(int textViewBackgroundOFF) {
            mTextViewBackgroundOFF = textViewBackgroundOFF;
        }

        public int getTextViewBackgroundON() {
            return mTextViewBackgroundON;
        }

        public void setTextViewBackgroundON(int textViewBackgroundON) {
            mTextViewBackgroundON = textViewBackgroundON;
        }

        public String getTitleText() {
            return mTitleText;
        }

        public void setTitleText(String titleText) {
            mTitleText = titleText;
        }

        public int getTitleTextSize() {
            return mTitleTextSize;
        }

        public void setTitleTextSize(int titleTextSize) {
            mTitleTextSize = titleTextSize;
        }

        public int getTitleBackgroundColor() {
            return mTitleBackgroundColor;
        }

        public void setTitleBackgroundColor(int titleBackgroundColor) {
            mTitleBackgroundColor = titleBackgroundColor;
        }

        public int getLabellingTextSize() {
            return mLabellingTextSize;
        }

        public void setLabellingTextSize(int labellingTextSize) {
            mLabellingTextSize = labellingTextSize;
        }

        public int getLabellingTextColor() {
            return mLabellingTextColor;
        }

        public void setLabellingTextColor(int labellingTextColor) {
            mLabellingTextColor = labellingTextColor;
        }

        public int getLabelling() {
            return mLabelling;
        }

        public void setLabelling(int labelling) {
            mLabelling = labelling;
        }
    }


}
