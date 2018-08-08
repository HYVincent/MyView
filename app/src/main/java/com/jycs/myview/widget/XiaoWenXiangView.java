package com.jycs.myview.widget;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by chong on 2017/3/5.
 */

public class XiaoWenXiangView extends View {

    private Paint paint;
    private int centreX, centreY;
    private float borderLength, mLineBottom, mLineTop, mLongLineHeight, mShortLineHeight;
    private String[] texts = {"12","1","2","3","4","5","6","7","8","9","10","11"};
    private int num;
    private Rect rect;
    private float angleS, angleM, angleH;
    private String[] split;
    private boolean isChange;
    private int w;
    private boolean isRun = true;

    public XiaoWenXiangView(Context context) {
        super(context);
        init();
    }

    public XiaoWenXiangView(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        new Thread(new Runnable() {
            @Override
            public void run() {

                while (isRun){
                    SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd-HH:mm:ss");
                    Date curDate = new Date(System.currentTimeMillis());
                    String date = formatter.format(curDate);
                    split = date.split("-");
                    String[] split1 = split[3].split(":");
                    String h = split1[0];
                    String m = split1[1];
                    String s = split1[2];
                    int H =   Integer.parseInt(h) == 0 ? 0 : 360 / 12 * Integer.parseInt(h);
                    int M =   Integer.parseInt(h) == 0 ? 0 : 360 / 60 * Integer.parseInt(m);
                    int S =   Integer.parseInt(h) == 0 ? 0 : 360 / 60 * Integer.parseInt(s);
                    angleS = S;
                    angleM = M + S / 60;
                    angleH = H + M / 12;
                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                  postInvalidate();
                }
            }
        }).start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        initPaint();
        drawBorder(canvas);
        drawScale(canvas);
        drawNumPointer(canvas);
        drawPointer(canvas);
    }

    private void drawNumPointer(Canvas canvas) {
        if (split == null) return;
        paint.setColor(Color.BLACK);
        paint.setStyle(Paint.Style.FILL);
        paint.setStrokeWidth(1 * w / 720);
        paint.setTextSize(30 * w / 720);
        String date = split[3];
        Rect rect = new Rect();
        paint.getTextBounds(date, 0 , date.length(), rect);
        int width = rect.width();
        int height = rect.height();
        canvas.drawText(date, centreX + 50  * w / 720, centreY + height / 2, paint);

        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(1 * w / 720);
        if (isChange){
            paint.setColor(Color.BLUE);
            isChange = false;
        }else {
            paint.setColor(Color.GREEN);
            isChange = true;
        }
        Path path = new Path();
        path.moveTo(centreX + 40 * w / 720, centreY + height / 2 + 5  * w / 720);
        path.lineTo(centreX + 40 * w / 720 + width + 20  * w / 720, centreY + height / 2 + 5  * w / 720);
        path.lineTo(centreX + 40 * w / 720 + width + 20  * w / 720, centreY + height / 2 + 5  * w / 720 - height - 10  * w / 720);
        path.lineTo(centreX + 40 * w / 720, centreY + height / 2 + 5  * w / 720 - height - 10  * w / 720);
        path.lineTo(centreX + 40 * w / 720, centreY + height / 2 + 5  * w / 720);
        canvas.drawPath(path,paint);
    }

    private void initPaint() {
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.RED);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(10 * w / 720);
        paint.setAntiAlias(true);
        num = 0;
        rect = new Rect();
    }

    private void drawPointer(Canvas canvas) {
        //秒
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(Color.BLACK);
        canvas.drawCircle(centreX,centreY,10 * w / 720,paint);
        canvas.save();
        canvas.rotate(angleS,centreX,centreY);
        paint.setStrokeWidth(3 * w / 720);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawLine(centreX,centreY + 20 * w / 720,centreX,centreY - 250 * w / 720,paint);
        canvas.restore();

        //分
        canvas.save();
        canvas.rotate(angleM,centreX,centreY);
        paint.setStrokeWidth(5 * w / 720);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawLine(centreX,centreY,centreX,centreY - 180 * w / 720,paint);
        canvas.restore();

        //小时
        canvas.save();
        canvas.rotate(angleH,centreX,centreY);
        paint.setStrokeWidth(8 * w / 720);
        paint.setStyle(Paint.Style.FILL_AND_STROKE);
        canvas.drawLine(centreX,centreY,centreX,centreY - 120 * w / 720 ,paint);
        canvas.restore();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        this.w = w;
        centreX = w / 2;
        centreY = h / 2;
        setMeasuredDimension(w, h);
        borderLength = 300  * w / 720;
        mLineTop = centreY - borderLength;
        mLineBottom = 200 * w / 720;
        mLongLineHeight = 40 * w / 720;
        mShortLineHeight = 25 * w / 720;
    }

    private void drawBorder(Canvas canvas) {
        canvas.drawCircle(centreX, centreY, borderLength, paint);
    }

    private void drawScale(Canvas canvas) {
        for (int i = 0; i < 360; i++) {
            if (i % 30 == 0) {
                mLineBottom = mLineTop + mLongLineHeight;
                paint.setStrokeWidth(5 * w / 720);

            } else {
                mLineBottom = mLineTop + mShortLineHeight;
                paint.setStrokeWidth(2 * w / 720);
            }

            if (i % 6 == 0) {
                canvas.save();
                canvas.rotate(i, centreX, centreY);
                canvas.drawLine(centreX, mLineTop, centreX, mLineBottom, paint);
                if (i %30 == 0){
                    canvas.save();
                    paint.setStyle(Paint.Style.FILL);
                    paint.setStrokeWidth(1);
                    paint.setTextSize(40 * w / 720);
                    String text = texts[num++];
                    paint.getTextBounds(text,0, text.length(), rect);
                    int width = rect.width();
                    int height = rect.height();
                    canvas.rotate(-((num - 1) * 360 / 12), centreX, mLineBottom + height / 2 + 10 * w / 720);
                    canvas.drawText(text, centreX - width / 2, mLineBottom + height + 10 * w /720, paint);
                    canvas.restore();
                    paint.setStyle(Paint.Style.FILL_AND_STROKE);
                }
                canvas.restore();
            }
        }
    }

    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        isRun = false;
    }
}
