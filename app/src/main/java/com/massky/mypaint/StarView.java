package com.massky.mypaint;

import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Path;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Size;
import android.view.View;
import android.view.WindowManager;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class StarView extends View {
    private Paint mHelpPaint;
    private double pi = 3.14;

    public StarView(Context context) {
        super(context);
    }

    public StarView(Context context, AttributeSet attrs) {
        super(context, attrs);
        mHelpPaint = new Paint();
        mHelpPaint.setStyle(Paint.Style.STROKE);
        mHelpPaint.setColor(0xffBBC3C5);
        mHelpPaint.setAntiAlias(true);
//        mHelpPaint.style=PaintingStyle.stroke;
//        mHelpPaint.color=Color(0xffBBC3C5);
//        mHelpPaint.isAntiAlias=true;

    }

    public StarView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
//        var winSize = MediaQuery.of(context).size;
        canvas.drawPath(gridPath(20, getScreenWidth(), getScreenHeight()), mHelpPaint);
        drawCoo(canvas, canvas.getWidth() / 2, canvas.getHeight() / 2, getScreenWidth(), getScreenHeight());

        canvas.translate(160, 320);//移动到坐标系原点
        canvas.drawPath(nStarPath(5,80,40), mHelpPaint);



    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);

    }

    /**
     * n角星路径
     *
     * @param num 几角星
     * @param R   外接圆半径
     * @param r   内接圆半径
     * @return n角星路径
     */
    Path nStarPath(int num, double R, double r) {
        Path path = new Path();
        double perDeg = 360 / num; //尖角的度数
        double degA = perDeg / 2 / 2;
        double degB = 360 / (num - 1) / 2 - degA / 2 + degA;

        path.moveTo((float) (cos(_rad(degA)) * R),(float) (-sin(_rad(degA)) * R));
        for (int i = 0; i < num; i++) {
            path.lineTo(
                    (float)(cos(_rad(degA + perDeg * i)) * R), (float)(-sin(_rad(degA + perDeg * i)) * R));
            path.lineTo(
                    (float)(cos(_rad(degB + perDeg * i)) * r), (float)(-sin(_rad(degB + perDeg * i)) * r));
        }
        path.close();
        return path;
    }

    double _rad(double deg) {
        return deg * pi / 180;
    }


    /**
     * 绘制网格路径
     *
     * @param step  小正方形边长
     * @param width 屏幕尺寸
     */
    @SuppressLint("NewApi")
    Path gridPath(int step, int width, int height) {
        Path path = new Path();

        for (int i = 0; i < height / step + 1; i++) {
            path.moveTo(0, step * i);
            path.lineTo(width, step * i);
        }

        for (int i = 0; i < width / step + 1; i++) {
            path.moveTo(step * i, 0);
            path.lineTo(step * i, height);
        }
        return path;
    }

    //绘制坐标系
    void drawCoo(Canvas canvas, int coo_width, int coo_height, int width, int height) {
        //初始化网格画笔
        Paint paint = new Paint();
//        paint.strokeWidth = 2;
//        paint.style = PaintingStyle.stroke;
        paint.setStrokeWidth(2);
        paint.setStyle(Paint.Style.STROKE);

//        //绘制直线
        canvas.drawPath(cooPath(coo_width, coo_height, width, height), paint);


        //左箭头
        canvas.drawLine(width, coo_height,
                width - 10, coo_height - 6, paint);
        canvas.drawLine(width, coo_height,
                width - 10, coo_height + 6, paint);


        //下箭头
        canvas.drawLine(coo_width, height,
                coo_width - 6, height - 10, paint);


        canvas.drawLine(coo_width, height,
                coo_width + 6, height - 10, paint);

    }


    /**
     * 坐标系路径
     *
     * @param coo_width 坐标点
     * @param width     屏幕尺寸
     * @return 坐标系路径
     */
    Path cooPath(int coo_width, int coo_height, int width, int height) {
        Path path = new Path();
        //x正半轴线
        path.moveTo(coo_width, coo_height);
        path.lineTo(width, coo_height);
        //x负半轴线
        path.moveTo(coo_width, coo_height);
        path.lineTo(coo_width - width, coo_height);

        //y负半轴线
        path.moveTo(coo_width, coo_height);
        path.lineTo(coo_width, coo_height - height);
        //y负半轴线
        path.moveTo(coo_width, coo_height);
        path.lineTo(coo_width, height);
        return path;
    }


    /**
     * 得到屏幕宽度
     *
     * @return
     */
    private int getScreenWidth() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.widthPixels;
    }


    /**
     * 得到屏幕宽度
     *
     * @return
     */
    private int getScreenHeight() {
        WindowManager windowManager = (WindowManager) getContext().getSystemService(Context.WINDOW_SERVICE);
        DisplayMetrics displayMetrics = new DisplayMetrics();
        windowManager.getDefaultDisplay().getMetrics(displayMetrics);
        return displayMetrics.heightPixels;
    }


}
