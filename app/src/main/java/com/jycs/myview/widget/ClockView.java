package com.jycs.myview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.CornerPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Point;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

import com.jycs.myview.CodeVal;

/**
 * 作者: jack(黄冲)
 * 邮箱: 907755845@qq.com
 * create on 2018/5/6 09:16
 * 时针
 */

public class ClockView extends View{


    private int mWidth, mHeight;
    private float mCenterX,  mCenterY;

    private float mRadius = 300 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;
    private float mMiniRadius = 12 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;
    private float mStrokeWidth = 10 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;


    private float mIndexLengthPlus = 30 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;
    private float mIndexLengthMini = 20 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;
    private float mIndexWidthPlus = 8 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;
    private float mIndexWidthMini = 5 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;

    private final int CLICK_AREA = 40 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;
    private final int CLICK_OFFSET = 40 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;

    private float mMinPointerLength = 200 * CodeVal.sScreenWidth / CodeVal.UI_WIDTH;

    private float mMinAngle = 70;

    private Paint mPaint;
    private Path mPath;
    private float mDownX, mDownY, mMoveX, mMoveY, mOffsetX, mOffsetY;
    private Point mP, mP1, mP2, mP3, mP4;

    public ClockView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }


    private void init() {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setAntiAlias(true);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mStrokeWidth);
        mPaint.setColor(Color.BLACK);
        mPath = new Path();

        mP = new Point();
        mP1 = new Point();
        mP2 = new Point();
        mP3 = new Point();
        mP4 = new Point();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mWidth = MeasureSpec.getSize(widthMeasureSpec);
        mHeight = MeasureSpec.getSize(heightMeasureSpec);

        mCenterX = mWidth / 2;
        mCenterY = mHeight / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        drawCircle(canvas);

        drawIndex(canvas);

        drawPointer(canvas);
    }

    private void drawCircle(Canvas canvas) {
        canvas.drawCircle(mCenterX, mCenterY, mRadius, mPaint);
    }

    private void drawIndex(Canvas canvas) {
        for (int i = 0; i < 12 * 5; i++) {
            float indexLength = i % 5 == 0 ? mIndexLengthPlus : mIndexLengthMini;
            float indexWidth = i % 5 == 0 ? mIndexWidthPlus : mIndexWidthMini;
            mPaint.setStrokeWidth(indexWidth);
            canvas.drawLine(mCenterX, mCenterY - mRadius, mCenterX, mCenterY - mRadius + indexLength, mPaint);
            canvas.rotate(6, mCenterX, mCenterY);
        }
    }

    private void drawPointer(Canvas canvas) {
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(mStrokeWidth);
        canvas.drawCircle(mCenterX, mCenterY, mMiniRadius, mPaint);

        canvas.save();
        canvas.rotate(mMinAngle, mCenterX, mCenterY);
        mPath.moveTo(mCenterX + mMiniRadius / 3 * 2, mCenterY);
        mPath.lineTo(mCenterX + 3, mCenterY - mMinPointerLength);
        mPath.lineTo(mCenterX - 3, mCenterY - mMinPointerLength);
        mPath.lineTo(mCenterX - mMiniRadius / 3 * 2, mCenterY);
        mPath.close();
        mPaint.setPathEffect(new CornerPathEffect(5));
        canvas.drawPath(mPath, mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                return down(event);
            case MotionEvent.ACTION_MOVE:
                move(event);
                break;
            case MotionEvent.ACTION_UP:
                return up(event);
        }
        return true;
    }

    private boolean down(MotionEvent event) {
        mDownX = mMoveX =  event.getX();
        mDownY = mMoveY = event.getY();
        mOffsetX = mOffsetY = 0;
        return true;
    }

    private void move(MotionEvent event){
        float dx = event.getX() - mMoveX;
        float dy = event.getY() - mMoveY;

        mOffsetX += Math.abs(dx);
        mOffsetY += Math.abs(dy);

        mMoveX = event.getX();
        mMoveY = event.getY();
    }

    private boolean up(MotionEvent event){
        if (mOffsetX < CLICK_OFFSET && mOffsetY < CLICK_OFFSET){

            double len = Math.sqrt(Math.pow(CLICK_AREA, 2) + Math.pow(CLICK_AREA, 2));

            float sin = (float) (float) (Math.sin((45 + mMinAngle) * Math.PI / 180) * len);
            float cos = (float) (float) (Math.cos((45 + mMinAngle) * Math.PI / 180) * len);

            mP.x = (int) event.getX();
            mP.y = (int) event.getY();
            mP1.x = (int) (mCenterX - sin);
            mP1.y = (int) (mCenterY + cos);
            mP2.x = (int) (mCenterX + sin);
            mP2.y = (int) (mCenterY + cos);
            mP3.x = (int) (mCenterX + sin);
            mP3.y = (int) (mCenterY - mMinPointerLength - cos);
            mP4.x = (int) (mCenterX - sin);
            mP4.y = (int) (mCenterY - mMinPointerLength - cos);

            if (Quadrangle.pInQuadrangle(mP1, mP2, mP3, mP4, mP)) {
                return true;
            }
            return false;
        }
        return false;
    }

}
