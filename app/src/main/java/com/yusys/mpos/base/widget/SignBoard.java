package com.yusys.mpos.base.widget;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.view.MotionEvent;
import android.view.View;

import java.util.ArrayList;

/**
 * 签字板,引用:http://www.2cto.com/kf/201409/335646.html
 *
 * @author yuanshuai (marshall.yuan@foxmail.com)
 * @since 2016-05-19 14:19
 */
public class SignBoard extends View {

    private Canvas mCanvas;
    private Path mPath;
    private Paint mBitmapPaint;
    private Bitmap mBitmap;
    private Paint mPaint;

    private ArrayList<DrawPath> savePath;
    private ArrayList<DrawPath> deletePath;
    private DrawPath dp;

    private float mX, mY;
    private static final float TOUCH_TOLERANCE = 4;

    private int bitmapWidth;
    private int bitmapHeight;

    //路径对象
    class DrawPath {
        Path path;
        Paint paint;
    }

    public SignBoard(Context c) {
        super(c);
        //得到屏幕的分辨率
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) c).getWindowManager().getDefaultDisplay().getMetrics(dm);

        bitmapWidth = dm.widthPixels;
        bitmapHeight = dm.heightPixels - 2 * 45;

        initCanvas();
        savePath = new ArrayList<>();
        deletePath = new ArrayList<>();

    }

    public SignBoard(Context c, AttributeSet attrs) {
        super(c, attrs);
        //得到屏幕的分辨率
        DisplayMetrics dm = new DisplayMetrics();
        ((Activity) c).getWindowManager().getDefaultDisplay().getMetrics(dm);

        bitmapWidth = dm.widthPixels;
        bitmapHeight = dm.heightPixels - 2 * 45;

        initCanvas();
        savePath = new ArrayList<>();
        deletePath = new ArrayList<>();
    }

    //初始化画布
    public void initCanvas() {
        mPaint = new Paint();
        mPaint.setAntiAlias(true);
        mPaint.setDither(true);
        mPaint.setColor(Color.BLACK);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeJoin(Paint.Join.ROUND);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(10);
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
        //画布大小
        mBitmap = Bitmap.createBitmap(bitmapWidth, bitmapHeight,
                Bitmap.Config.RGB_565);
        mCanvas = new Canvas(mBitmap);  //所有mCanvas画的东西都被保存在了mBitmap中
        mCanvas.drawColor(Color.WHITE);
        mPath = new Path();
        mBitmapPaint = new Paint(Paint.DITHER_FLAG);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawBitmap(mBitmap, 0, 0, mBitmapPaint);     //显示旧的画布
        if (mPath != null) {
            // 实时的显示
            canvas.drawPath(mPath, mPaint);
        }
    }

    /*
     * 清空的主要思想就是初始化画布
     * 将保存路径的两个List清空
     * */
    public void removeAllPaint() {
        //调用初始化画布函数以清空画布
        initCanvas();
        invalidate();//刷新
        savePath.clear();
        deletePath.clear();
    }

    private void touch_start(float x, float y) {
        mPath.reset();//清空path
        mPath.moveTo(x, y);
        mX = x;
        mY = y;
    }

    private void touch_move(float x, float y) {
        float dx = Math.abs(x - mX);
        float dy = Math.abs(y - mY);
        if (dx >= TOUCH_TOLERANCE || dy >= TOUCH_TOLERANCE) {
            //mPath.quadTo(mX, mY, x, y);
            mPath.quadTo(mX, mY, (x + mX) / 2, (y + mY) / 2);//源代码是这样写的，可是我没有弄明白，为什么要这样？
            mX = x;
            mY = y;
        }
    }

    private void touch_up() {
        mPath.lineTo(mX, mY);
        mCanvas.drawPath(mPath, mPaint);
        savePath.add(dp);
        mPath = null;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        float x = event.getX();
        float y = event.getY();

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:

                mPath = new Path();
                dp = new DrawPath();
                dp.path = mPath;
                dp.paint = mPaint;

                touch_start(x, y);
                invalidate(); //清屏
                break;
            case MotionEvent.ACTION_MOVE:
                touch_move(x, y);
                invalidate();
                break;
            case MotionEvent.ACTION_UP:
                touch_up();
                invalidate();
                break;
        }
        return true;
    }
}