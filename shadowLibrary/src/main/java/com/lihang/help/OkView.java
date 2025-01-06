package com.lihang.help;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by leo
 * on 2019/11/21.
 */
public class OkView extends View {
    //绘制一个小圆圈
    private Paint paint;
    //绘制打勾paint
    private Paint okPaint;

    //背景圆圈的半径
    private int myRadius;
    //绘制打勾的路径
    private Path path = new Path();
    //绘制路径的长度，也可以理解为完成度
    private PathMeasure pathMeasure;

    //绘制对勾（√）的动画
    private ValueAnimator animator_draw_ok;

    //对路径处理实现绘制动画效果
    private PathEffect effect;

    private boolean startDrawOk;

    public OkView(Context context) {
        this(context, null);
    }

    public OkView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public OkView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        paint = new Paint();
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);


        okPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        okPaint.setStrokeWidth(5);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setStrokeCap(Paint.Cap.ROUND);


    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(getWidth() / 2, getHeight() / 2, myRadius, paint);

        if (startDrawOk) {
            canvas.drawPath(path, okPaint);
        }
    }

    public void setOkColor(int color) {
        okPaint.setColor(color);
    }

    public void setCircleColor(int color) {
        paint.setColor(color);
    }


    public void setRadius(int radius) {
        myRadius = radius;
        //对勾的路径
        int cHeight = radius * 2;
        path.moveTo(+cHeight / 8 * 3, cHeight / 2);
        path.lineTo(+cHeight / 2, cHeight / 5 * 3);
        path.lineTo(+cHeight / 3 * 2, cHeight / 5 * 2);
        pathMeasure = new PathMeasure(path, true);

        invalidate();
    }


    public void start(int duration) {
        animator_draw_ok = ValueAnimator.ofFloat(1, 0);
        animator_draw_ok.setDuration(duration);
        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startDrawOk = true;
                float value = (Float) animation.getAnimatedValue();
                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                invalidate();
            }
        });
        animator_draw_ok.start();
    }


}
