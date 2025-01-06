package com.lihang.help;

import android.animation.Animator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.lihang.R;
import com.lihang.SmartLoadingView;


/**
 * 圆圈扩散自定义View
 * By leo
 * 2019.5.23
 */

public class CirclBigView extends View {

    //圆圈扩散动画
    private ValueAnimator animator_big;
    private int myRadius;
    //父类控件半径(父类最短一边,长度的一半)
    private int fatherRadius;
    private Paint showPaint;
    private int y;//当前Y轴位置
    private int x;//当前X轴位置

    //最大能扩散到的半径
    private int maxRadius;


    public CirclBigView(Context context) {
        this(context, null);
    }

    public CirclBigView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public CirclBigView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        showPaint = new Paint();
        showPaint.setAntiAlias(true);
        showPaint.setStyle(Paint.Style.FILL);
        showPaint.setColor(getResources().getColor(R.color.guide_anim));
    }


    public void setXY(int x, int y) {
        this.x = x;
        this.y = y;
        int y_true = y + fatherRadius;
        //知道了view圆心，计算出到手机4个角的距离，以最大距离为准
        int left_top = (int) Math.sqrt((x * x) + (y_true * y_true));
        int left_bottom = (int) Math.sqrt((x * x) + ((UIUtil.getHeight(getContext()) - y_true) * (UIUtil.getHeight(getContext()) - y_true)));

        int right_top = (int) Math.sqrt(((UIUtil.getWidth(getContext()) - x) * (UIUtil.getWidth(getContext()) - x)) + (y_true * y_true));
        int right_bottom = (int) Math.sqrt(((UIUtil.getWidth(getContext()) - x) * (UIUtil.getWidth(getContext()) - x)) + ((UIUtil.getHeight(getContext()) - y_true) * (UIUtil.getHeight(getContext()) - y_true)));

        int left_big = left_top >= left_bottom ? left_top : left_bottom;
        int right_big = right_top >= right_bottom ? right_top : right_bottom;
        maxRadius = left_big >= right_big ? left_big : right_big;
        //这里虚拟键有个bug，我们把半径大小稍加长
        maxRadius = maxRadius + UIUtil.getWidth(getContext()) / 6;


        animator_big = ValueAnimator.ofInt(myRadius, maxRadius);
        animator_big.setDuration(200);
        animator_big.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator valueAnimator) {
                myRadius = (int) valueAnimator.getAnimatedValue();
                postInvalidate();
            }
        });

        invalidate();
    }

    public void setRadius(int radius) {
        myRadius = radius;
        fatherRadius = radius;
    }

    public void setCircleR(int currentR) {
        myRadius = currentR;
        postInvalidate();
    }


    public void setColorBg(int colorBg) {
        showPaint.setColor(colorBg);
    }


    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.drawCircle(x, y + fatherRadius, myRadius, showPaint);
    }


    public void startShowAni(final SmartLoadingView.LoadingListener listener, final SmartLoadingView smartLoadingView) {

        if (listener != null) {
            if (!animator_big.isRunning()) {
                animator_big.start();
            }
            if (listener != null) {
                animator_big.addListener(new Animator.AnimatorListener() {
                    @Override
                    public void onAnimationStart(Animator animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animator animation) {
                        listener.loadingFinish(true);
                    }

                    @Override
                    public void onAnimationCancel(Animator animation) {

                    }

                    @Override
                    public void onAnimationRepeat(Animator animation) {

                    }
                });
            }

        }
    }


    public void startShowAni(final Activity activity, final Class clazz) {

        if (!animator_big.isRunning()) {
            animator_big.start();
            animator_big.addListener(new Animator.AnimatorListener() {
                @Override
                public void onAnimationStart(Animator animation) {

                }

                @Override
                public void onAnimationEnd(Animator animation) {
                    activity.startActivity(new Intent(activity, clazz));
                    activity.finish();
                    activity.overridePendingTransition(R.anim.alpha_enter, R.anim.alpha_exit);
                }

                @Override
                public void onAnimationCancel(Animator animation) {

                }

                @Override
                public void onAnimationRepeat(Animator animation) {

                }
            });
        }
    }

}
