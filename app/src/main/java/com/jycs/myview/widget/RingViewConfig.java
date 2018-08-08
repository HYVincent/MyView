package com.jycs.myview.widget;

import android.graphics.Color;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/1/3 16:28
 */

public class RingViewConfig {

    //默认的圆环颜色
    private static final int DEFAULT_RING_COLOR = Color.parseColor("#D5DBE5");
    //默认的圆环填充色
    private static final int DEFAULT_SOLID_COLOR = Color.parseColor("#FC5012");
    //默认的圆环宽度
    private static final float DEFAULT_RING_WIDTH = 5f;
    //默认的圆环开始角度
    private static final int DEFAULT_RING_START_ANGLE = 12 ;
    //默认的圆环结束角度
    private static final int DEFAULT_RING_SWEEP_ANGLE = 180 - DEFAULT_RING_START_ANGLE * 2;
    //默认的圆半径
    private static final float DEFAULT_CIRCLE_SIZE = 26f;
    //默认的圆阴影Y轴偏移量
    private static final float DEFAULT_CIRCLE_SHADE_OFFSET_Y = 3f;
    //默认的圆阴影大小
    private static final float DEFAULT_CIRCLE_SHADE_SIZE = 8f;
    //默认的圆阴影颜色
    private static final int DEFAULT_CIRCLE_SHADE_COLOR = Color.parseColor("#92FC5012");
    //默认的内容字体大小
    private static final int DEFAULT_CONTENT_TEXT_SIZE = 60;
    //默认的内容字体颜色
    private static final int DEFAULT_CONTENT_TEXT_COLOR = Color.parseColor("#FC5012");
    //默认的内容字体上边距
    private static final float DEFAULT_CONTENT_TEXT_MARGIN_TOP = 70f;
    //默认的%号字体大小
    private static final int DEFAULT_UNIT_TEXT_SIZE = 28;
    //默认的圆内的字体大小
    private static final int DEFAULT_CIRCLE_TEXT_SIZE = 20;
    //默认的圆内的字体颜色
    private static final int DEFAULT_CIRCLE_TEXT_COLOR = Color.WHITE;


    //圆环颜色
    private int mRingColor = DEFAULT_RING_COLOR;
    //圆环填充色
    private int mSolidColor = DEFAULT_SOLID_COLOR;
    //圆环宽度
    private float mRingWidth = DEFAULT_RING_WIDTH;
    //圆环开始角度
    private float mRingStartAngle = DEFAULT_RING_START_ANGLE;
    //圆环结束角度
    private float mRingSweepAngle = DEFAULT_RING_SWEEP_ANGLE;
    //圆半径
    private float mCircleSize = DEFAULT_CIRCLE_SIZE;
    //圆阴影Y轴偏移量
    private float mCircleShadeOffsetY = DEFAULT_CIRCLE_SHADE_OFFSET_Y;
    //圆阴影大小
    private float mCircleShadeSize = DEFAULT_CIRCLE_SHADE_SIZE;
    //圆阴影颜色
    private int mCircleShadeColor = DEFAULT_CIRCLE_SHADE_COLOR;
    //内容字体大小
    private int mContentTextSize = DEFAULT_CONTENT_TEXT_SIZE;
    //内容字体颜色
    private int mContentTextColor = DEFAULT_CONTENT_TEXT_COLOR;
    //内容字体上边距
    private float mContentTextMarginTop = DEFAULT_CONTENT_TEXT_MARGIN_TOP;
    //%号字体大小
    private int mUnitTextSize = DEFAULT_UNIT_TEXT_SIZE;
    //圆内的字体大小
    private int mCircleTextSize = DEFAULT_CIRCLE_TEXT_SIZE;
    //圆内的字体颜色
    private int mCircleTextColor = DEFAULT_CIRCLE_TEXT_COLOR;


    private RingViewConfig() {

    }

    public int getRingColor() {
        return mRingColor;
    }

    public int getSolidColor() {
        return mSolidColor;
    }

    public float getRingWidth() {
        return mRingWidth;
    }

    public float getCircleSize() {
        return mCircleSize;
    }

    public float getCircleShadeOffsetY() {
        return mCircleShadeOffsetY;
    }

    public float getCircleShadeSize() {
        return mCircleShadeSize;
    }

    public int getCircleShadeColor() {
        return mCircleShadeColor;
    }

    public int getContentTextSize() {
        return mContentTextSize;
    }

    public int getContentTextColor() {
        return mContentTextColor;
    }

    public float getContentTextMarginTop() {
        return mContentTextMarginTop;
    }

    public int getUnitTextSize() {
        return mUnitTextSize;
    }

    public int getCircleTextSize() {
        return mCircleTextSize;
    }

    public int getCircleTextColor() {
        return mCircleTextColor;
    }

    public float getRingStartAngle() {
        return mRingStartAngle;
    }

    public float getRingSweepAngle() {
        return mRingSweepAngle;
    }

    public static final class Builder{

        private RingViewConfig mConfig;

        public Builder() {
            mConfig = new RingViewConfig();
        }

        public Builder setRingColor(int ringColor) {
            mConfig.mRingColor = ringColor;
            return this;
        }

        public Builder setSolidColor(int solidColor) {
            mConfig.mSolidColor = solidColor;
            return this;
        }

        public Builder setRingWidth(float ringWidth) {
            mConfig.mRingWidth = ringWidth;
            return this;
        }

        public Builder setCircleSize(float circleSize) {
            mConfig.mCircleSize = circleSize;
            return this;
        }

        public Builder setCircleShadeOffsetY(float circleShadeOffsetY) {
            mConfig.mCircleShadeOffsetY = circleShadeOffsetY;
            return this;
        }

        public Builder setCircleShadeSize(float circleShadeSize) {
            mConfig.mCircleShadeSize = circleShadeSize;
            return this;
        }

        public Builder setCircleShadeColor(int circleShadeColor) {
            mConfig.mCircleShadeColor = circleShadeColor;
            return this;
        }

        public Builder setContentTextSize(int contentTextSize) {
            mConfig.mContentTextSize = contentTextSize;
            return this;
        }

        public Builder setContentTextColor(int contentTextColor) {
            mConfig.mContentTextColor = contentTextColor;
            return this;
        }

        public Builder setContentTextMarginTop(float contentTextMarginTop) {
            mConfig.mContentTextMarginTop = contentTextMarginTop;
            return this;
        }

        public Builder setUnitTextSize(int unitTextSize) {
            mConfig.mUnitTextSize = unitTextSize;
            return this;
        }

        public Builder setCircleTextSize(int circleTextSize) {
            mConfig.mCircleTextSize = circleTextSize;
            return this;
        }

        public Builder setCircleTextColor(int circleTextColor) {
            mConfig.mCircleTextColor = circleTextColor;
            return this;
        }

        public Builder setRingStartAngle(int startAngle) {
            mConfig.mRingStartAngle = startAngle;
            return this;
        }

        public Builder setRingSweepAngle(int sweepAngle) {
            mConfig.mRingSweepAngle = sweepAngle;
            return this;
        }

        public RingViewConfig build(){
            return mConfig;
        }
    }
}
