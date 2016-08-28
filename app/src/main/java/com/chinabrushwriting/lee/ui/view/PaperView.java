package com.chinabrushwriting.lee.ui.view;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.VelocityTracker;
import android.view.View;

import com.chinabrushwriting.lee.utils.UIUtils;

/**
 * 定义一个画画板
 */
public class PaperView extends View{

    private static final String TAG = "PaperView";
    private float pX;
    private float pY;
    private float startY;
    private float startX;
    private Bitmap bitmap;
    private Canvas canvas;
    private VelocityTracker vt;
    private Paint mPaint;

    public PaperView(Context context) {
        super(context);
        initView();
    }

    public PaperView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }

    public PaperView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView();
    }

    private void initView() {

        //获取屏幕宽度
        int width = UIUtils.getScreenWidth();
        int height = UIUtils.getScreenHeight();
        //创建一个画布
        canvas = new Canvas();
        //创建一个承载画图内容的位图
        bitmap = Bitmap.createBitmap(width, height, Bitmap.Config.ARGB_8888);

        canvas.setBitmap(bitmap);


    }

    /**
     * 将.xml布局文件读完后执行该方法
     * 一般用来初始化数据的
     */
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setColor(Color.BLACK);
        //设置线条粗细
        mPaint.setStrokeWidth(50);

    }

    /**
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawBitmap(bitmap, 0, 0, mPaint);

    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {

        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                startX = event.getX();
                startY = event.getY();

                if (vt == null) {
                    vt = VelocityTracker.obtain();
                } else {
                    vt.clear();
                }

                break;
            case MotionEvent.ACTION_MOVE:
                vt.addMovement(event);
                pX = event.getX();
                pY = event.getY();
                vt.computeCurrentVelocity(1000);
                Log.i(TAG, vt.getXVelocity() + " " + vt.getYVelocity());
                //获取x轴，y轴速度
                int vX = (int) Math.abs(vt.getXVelocity());
                int vY = (int) Math.abs(vt.getYVelocity());
                if (vX < 200 && vY < 200) {
                    int value=((vX+vY)/2)/10-10;
                    canvas.drawCircle(pX, pY, 50-value, mPaint);
                }
                // canvas.drawCircle(pX, pY, 50, mPaint);
                //  canvas.drawLine(startX, startY, pX, pY, mPaint);
                //  canvas.drawLine(pX, pY, startX, startY, mPaint);
                canvas.drawPoint(pX, pY, mPaint);
                invalidate();
                startX = pX;
                startY = pY;
                break;
            case MotionEvent.ACTION_UP:
                break;
        }
        return true;
    }

}