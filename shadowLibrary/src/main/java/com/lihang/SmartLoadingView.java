package com.lihang;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.PathEffect;
import android.graphics.PathMeasure;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.Rect;
import android.graphics.RectF;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.AnticipateInterpolator;
import android.view.animation.LinearInterpolator;
import android.widget.FrameLayout;

import androidx.appcompat.widget.AppCompatTextView;

import com.lihang.help.CirclBigView;
import com.lihang.help.OkView;
import com.lihang.help.UIUtil;


/**
 * @Author leo
 * @Address https://github.com/lihangleo2
 * @Date 2024/6/21 重构
 */

public class SmartLoadingView extends AppCompatTextView {
    private static int SMART_TICK = 1;
    private static int SMART_TICK_HIDE = 2;
    private static int SMART_TICK_CENTER_HIDE = 3;
    private static int SMART_BUTTON = 4;
    private static int SMART_FULL_SCREEN = 5;


    //view的宽度
    private int width;

    //View的高度
    private int height;

    //圆角半径
    private int circleAngle;

    //矩形2边需要缩短的距离
    private int default_all_distance;

    //当前矩形在x轴left的位置
    private int current_left;

    //动画执行时间
    private int duration = 500;

    //圆角矩形画笔
    private Paint paint;


    //对勾（√）画笔
    private Paint okPaint;

    //文字绘制所在矩形
    private Rect textRect = new Rect();

    //根据view的大小设置成矩形
    private RectF rectf = new RectF();

    /**
     * 动画集
     */
    //这是开始的启动
    private AnimatorSet animatorSet = new AnimatorSet();
    //这是网络错误的
    private AnimatorSet animatorNetfail = new AnimatorSet();

    //矩形到正方形过度的动画
    private ValueAnimator animator_rect_to_square;

    //正方形到矩形动画
    private ValueAnimator animator_squareToRect;

    //矩形到圆角矩形过度的动画
    private ValueAnimator animator_rect_to_angle;

    //圆角矩形到矩形的动画
    private ValueAnimator animator_angle_to_rect;


    /**
     * 以下是绘制对勾动画
     */

    //是否开始绘制对勾
    private boolean startDrawOk = false;

    //绘制对勾（√）的动画
    private ValueAnimator animator_draw_ok;

    //对路径处理实现绘制动画效果
    private PathEffect effect;

    //路径--用来获取对勾的路径
    private Path path = new Path();

    //取路径的长度
    private PathMeasure pathMeasure;

    /**
     * 加载loading动画相关
     */
    //是否开始绘画，加载转圈动画
    private boolean isDrawLoading = false;
    //是否处于加载状态，注意，这里和上面是2个概念，只要点击按钮，没有走错误和走正确的都视为在加载状态下
    private boolean isLoading = false;
    private int startAngle = 0;
    private int progAngle = 30;
    private boolean isAdd = true;

    /**
     * 文字画笔相关
     */
    //文字画笔
    private Paint textPaint;
    //文字超过一行时，进行的文字滚动动画
    private ValueAnimator animator_text_scroll;//这只是模式之一
    private ValueAnimator animator_marque;
    private int drawTextStart;
    private int drawMarqueTextStart;


    /**
     * 以下是自定义属性
     */

    //按钮背景色
    private int backgroundColor;

    //不可点击的背景颜色
    private int unEnabled_backgroundColor;

    //从用户获得的圆角
    private int corners_radius;

    //文字省略模模式；reverse--1：来回滚动 marquee--2：跑马灯效果
    private int ellipsize = 1;

    //文字省略模式下，文字的滚动速度（只有文字滚动时生效）
    private int ellipsize_speed;


    //动画结束背景色
    private int animaled_backgroundColor;

    //动画结束文字颜色
    private int animaled_textColor;

    //按钮文字
    private String normalString = "";
    private String mAnimaledText = "";
    private String currentString;//当前要绘画的TextStr

    //当前字体颜色值
    private int textColor;
    //当前字体颜色值初始颜色
    private int textColorOriginal;
    //当前字体透明度
    private int textAlpha;

    //这是全屏动画
    private CirclBigView circlBigView;

    //SmartLoadingView模式
    private int mButtonType = SMART_FULL_SCREEN;//关注的样式，默认是正常样式


    public SmartLoadingView(Context context) {
        this(context, null);
    }

    public SmartLoadingView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SmartLoadingView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        circlBigView = new CirclBigView(getContext());
        init(attrs);
        initPaint();
    }


    /**
     * init
     * *********************************************************************************************
     */

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.SmartLoadingView);
        // 设置title
        if (TextUtils.isEmpty(getText())) {
            currentString = normalString;
        } else {
            normalString = (String) getText();
            currentString = normalString;
        }
        mAnimaledText = currentString;

        String animaledText = typedArray.getString(R.styleable.SmartLoadingView_hl_animaled_text);
        if (!TextUtils.isEmpty(animaledText)) {
            mAnimaledText = animaledText;
        }
        unEnabled_backgroundColor = typedArray.getColor(R.styleable.SmartLoadingView_hl_unEnabled_background, getResources().getColor(R.color.blackbb));
        //赋予背景色默认颜色值
        backgroundColor = getResources().getColor(R.color.guide_anim);
        Drawable background = getBackground();
        if (background instanceof ColorDrawable) {
            backgroundColor = ((ColorDrawable) background).getColor();
        }
        animaled_backgroundColor = typedArray.getColor(R.styleable.SmartLoadingView_hl_animaled_background, backgroundColor);

        //获取文字颜色值
        ColorStateList textColors = getTextColors();
        final int[] drawableState = getDrawableState();
        //获取textView默认颜色值
        textColor = textColors.getColorForState(drawableState, 0);
        textColorOriginal = textColor;
        animaled_textColor = typedArray.getColor(R.styleable.SmartLoadingView_hl_animaled_textColor, textColor);

        corners_radius = (int) typedArray.getDimension(R.styleable.SmartLoadingView_hl_corners_radius, getResources().getDimension(R.dimen.slv_default_corner));
        ellipsize = typedArray.getInt(R.styleable.SmartLoadingView_hl_ellipsize, 1);
        ellipsize_speed = typedArray.getInt(R.styleable.SmartLoadingView_hl_ellipsize_speed, 400);
        mButtonType = typedArray.getInt(R.styleable.SmartLoadingView_hl_button_type, SMART_FULL_SCREEN);

        int padding_horizontal = (int) getResources().getDimension(R.dimen.slv_padding_horizontal);
        int padding_vertical = (int) getResources().getDimension(R.dimen.slv_padding_vertical);
        int paddingTop = getPaddingTop() == 0 ? padding_vertical : getPaddingTop();
        int paddingBottom = getPaddingBottom() == 0 ? padding_vertical : getPaddingBottom();
        int paddingLeft = getPaddingLeft() == 0 ? padding_horizontal : getPaddingLeft();
        int paddingRight = getPaddingRight() == 0 ? padding_horizontal : getPaddingRight();
        setPadding(paddingLeft, paddingTop, paddingRight, paddingBottom);
        setBackgroundColor(0);
        setMaxLines(1);
        setGravity(Gravity.CENTER);
    }

    //画笔初始化
    private void initPaint() {

        //矩形画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStrokeWidth(4);
        paint.setStyle(Paint.Style.FILL);
        paint.setAntiAlias(true);

        if (isEnabled()) {
            paint.setColor(backgroundColor);
        } else {
            paint.setColor(unEnabled_backgroundColor);
        }

        //打勾画笔
        okPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        okPaint.setStrokeWidth(5);
        okPaint.setStyle(Paint.Style.STROKE);
        okPaint.setAntiAlias(true);
        okPaint.setStrokeCap(Paint.Cap.ROUND);


//        ColorStateList textColors = getTextColors();
//        final int[] drawableState = getDrawableState();
//        //获取textView默认颜色值
//        textColor = textColors.getColorForState(drawableState, 0);
        okPaint.setColor(textColor);
        textAlpha = Color.alpha(textColor);


        //文字画笔
        textPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        textPaint.setTextSize(getTextSize());
        textPaint.setColor(textColor);
        textPaint.setAntiAlias(true);
    }

    //动画初始化
    private void initAnimation() {
        set_rect_to_circle_animation();
        set_draw_ok_animation();
        animatorSet.play(animator_rect_to_square).with(animator_rect_to_angle);
        animatorNetfail.play(animator_squareToRect).with(animator_angle_to_rect);
    }

    //绘制对勾
    private void initOk() {
        //对勾的路径
        path.moveTo(default_all_distance + height / 8 * 3, height / 2);
        path.lineTo(default_all_distance + height / 2, height / 5 * 3);
        path.lineTo(default_all_distance + height / 3 * 2, height / 5 * 2);
        pathMeasure = new PathMeasure(path, true);
    }

    /**
     * 动画方法
     * *********************************************************************************************
     */
    /*
     * 设置圆角矩形过度到圆的动画
     * &圆到圆角矩形
     * <p>
     * 矩形到圆角矩形的动画
     * &圆角矩形到矩形的动画
     */
    private void set_rect_to_circle_animation() {
        animator_rect_to_square = ValueAnimator.ofInt(0, default_all_distance);
        animator_rect_to_square.setDuration(duration);
        animator_rect_to_square.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                current_left = (int) animation.getAnimatedValue();

                int nowAlpha = textAlpha / 2 - (current_left * textAlpha / default_all_distance) < 0 ? 0 : textAlpha / 2 - (current_left * textAlpha / default_all_distance);
                textPaint.setColor(addAlpha(textColor, nowAlpha));
                if (current_left == default_all_distance) {
                    isDrawLoading = true;
                }
                invalidate();

            }
        });


        animator_rect_to_angle = ValueAnimator.ofInt(corners_radius, height / 2);
        animator_rect_to_angle.setDuration(duration);
        animator_rect_to_angle.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                invalidate();
            }
        });


        animator_squareToRect = ValueAnimator.ofInt(default_all_distance, 0);
        animator_squareToRect.setDuration(duration);
        animator_squareToRect.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                current_left = (int) animation.getAnimatedValue();
                //当控件扩展到一半时再显示文字，不然当文案过长时，会先显示文字。超过控件
                if (current_left <= default_all_distance / 2) {
                    int nowAlpha = (default_all_distance / 2 - current_left) * textAlpha / (default_all_distance / 2);
                    textPaint.setColor(addAlpha(textColor, nowAlpha));
                }
                //错误动画全部走完之后，才能被点击
                if (current_left == 0) {
                    isLoading = false;
                    setClickable(true);
                }
                isDrawLoading = false;
                startDrawOk = false;
                postInvalidate();
            }
        });


        animator_angle_to_rect = ValueAnimator.ofInt(height / 2, corners_radius);
        animator_angle_to_rect.setDuration(duration);
        animator_angle_to_rect.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                circleAngle = (int) animation.getAnimatedValue();
                postInvalidate();
            }
        });

    }

    /*
     * 绘制对勾的动画
     */
    private void set_draw_ok_animation() {
        animator_draw_ok = ValueAnimator.ofFloat(1, 0);
        animator_draw_ok.setDuration(duration);
        animator_draw_ok.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                startDrawOk = true;
                isDrawLoading = false;
                float value = (Float) animation.getAnimatedValue();
                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, value * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                invalidate();

            }
        });
    }


    /*
     * 画圆角矩形
     * */
    private void draw_oval_to_circle(Canvas canvas) {
        rectf.left = current_left;
        rectf.top = 0;
        rectf.right = width - current_left;
        rectf.bottom = height;

        //画圆角矩形
        canvas.drawRoundRect(rectf, circleAngle, circleAngle, paint);
    }

    /**
     * *
     * *********************************************************************************************
     */
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (width == 0) {
            width = w;
            height = h;
            if (corners_radius > (height / 2)) {
                corners_radius = height / 2;
            }
            circleAngle = corners_radius;
            default_all_distance = (w - h) / 2;
            initOk();
            initAnimation();
            //如果不是精准模式，我们代码里设置第一次的长宽，成为精准模式
            //这样避免，更改文字内容时，总是会改变控件的长宽
            setWidth(width);
            setHeight(height);
//            setEnabled(isEnabled());
        }
    }

    //绘制文字相关
    private void drawText(final Canvas canvas) {
        int sc = canvas.saveLayer(0, 0, getWidth(), getHeight(), null, Canvas.ALL_SAVE_FLAG);
        rectf.left = current_left + getPaddingLeft();
        rectf.top = 0;
        rectf.right = width - current_left - getPaddingRight();
        rectf.bottom = height;
        //画圆角矩形
        canvas.drawRoundRect(rectf, circleAngle, circleAngle, paint);

        //设置混合模式
        textPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.SRC_IN));

        textRect.left = getPaddingLeft();
        textRect.top = 0;
        textRect.right = width - getPaddingRight();
        textRect.bottom = height;
        Paint.FontMetricsInt fontMetrics = textPaint.getFontMetricsInt();
        final int baseline = (textRect.bottom + textRect.top - fontMetrics.bottom - fontMetrics.top) / 2;
        //这是测量文字的长度。
        int myTotal = (int) (textPaint.measureText(currentString) + getPaddingRight() + getPaddingLeft());
        if (myTotal > getWidth()) {
            if (ellipsize == 1) {
                textPaint.setTextAlign(Paint.Align.LEFT);
                if (animator_text_scroll == null && !isLoading) {
                    //此时文字长度已经超过一行，进行文字滚动
                    animator_text_scroll = ValueAnimator.ofInt(textRect.left, (int) (textRect.left - textPaint.measureText(currentString) + (getWidth() - getPaddingLeft() - getPaddingRight())));
                    animator_text_scroll.setDuration(currentString.length() * ellipsize_speed);
                    animator_text_scroll.setRepeatMode(ValueAnimator.REVERSE);
                    animator_text_scroll.setRepeatCount(-1);
                    animator_text_scroll.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {
                            drawTextStart = (int) animation.getAnimatedValue();
                            postInvalidate();
                        }
                    });
                    animator_text_scroll.start();
                }
                canvas.drawText(currentString, drawTextStart, baseline, textPaint);
            } else {
                textPaint.setTextAlign(Paint.Align.LEFT);
                if (animator_text_scroll == null && !isLoading) {
                    //此时文字长度已经超过一行，进行文字滚动
                    animator_text_scroll = ValueAnimator.ofInt(textRect.left, (int) (textRect.left - textPaint.measureText(currentString)));
                    animator_text_scroll.setDuration(currentString.length() * ellipsize_speed);
                    animator_text_scroll.setInterpolator(new LinearInterpolator());
                    animator_text_scroll.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                        @Override
                        public void onAnimationUpdate(ValueAnimator animation) {

                            drawTextStart = (int) animation.getAnimatedValue();
                            postInvalidate();
                            if (drawTextStart == textRect.left) {
                                if (animator_marque != null) {
                                    animator_marque.cancel();
                                    animator_marque = null;
                                }
                            }
                            if (animator_marque == null && !isLoading && drawTextStart <= (int) (textRect.left - textPaint.measureText(currentString) + (getWidth() - getPaddingLeft() - getPaddingRight()) - (getWidth() - getPaddingLeft() - getPaddingRight()) / 3)) {
                                int duration = (int) (((currentString.length() * ellipsize_speed) * (textRect.right - textRect.left)) / textPaint.measureText(currentString));
                                animator_marque = ValueAnimator.ofInt(textRect.right, textRect.left);
                                animator_marque.setDuration(duration);
                                animator_marque.setInterpolator(new LinearInterpolator());
                                animator_marque.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                                    @Override
                                    public void onAnimationUpdate(ValueAnimator animation) {
                                        drawMarqueTextStart = (int) animation.getAnimatedValue();
                                        if (drawMarqueTextStart == textRect.left) {
                                            SmartLoadingView.this.postDelayed(new Runnable() {
                                                @Override
                                                public void run() {
                                                    if (animator_text_scroll != null) {
                                                        animator_text_scroll.cancel();
                                                        animator_text_scroll = null;
                                                        postInvalidate();
                                                    }
                                                }
                                            }, 1500);
                                        }
                                        postInvalidate();
                                    }
                                });
                                animator_marque.start();
                            }
                        }
                    });
                    animator_text_scroll.start();
                }
                if (animator_marque != null) {
                    canvas.drawText(currentString, drawMarqueTextStart, baseline, textPaint);
                }
                canvas.drawText(currentString, drawTextStart, baseline, textPaint);
            }

        } else {
            cancleScroll();
            textPaint.setTextAlign(Paint.Align.CENTER);
            drawTextStart = textRect.left;
            canvas.drawText(currentString, textRect.centerX(), baseline, textPaint);
        }

        // 还原混合模式
        textPaint.setXfermode(null);
        // 还原画布
        canvas.restoreToCount(sc);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        draw_oval_to_circle(canvas);
        drawText(canvas);

        //绘制加载进度
        if (isDrawLoading) {
            canvas.drawArc(new RectF(width / 2 - height / 2 + height / 4, height / 4, width / 2 + height / 2 - height / 4, height / 2 + height / 2 - height / 4), startAngle, progAngle, false, okPaint);
            startAngle += 6;
            if (progAngle >= 270) {
                progAngle -= 2;
                isAdd = false;
            } else if (progAngle <= 45) {
                progAngle += 6;
                isAdd = true;
            } else {
                if (isAdd) {
                    progAngle += 6;
                } else {
                    progAngle -= 2;
                }
            }
            postInvalidate();
        }

        //绘制打勾
        if (startDrawOk) {
            canvas.drawPath(path, okPaint);
        }

    }


    @Override
    protected void onDetachedFromWindow() {
        super.onDetachedFromWindow();
        cancleScroll();
    }


    //给TextView字体设置透明度。
    private int addAlpha(int color, int alpha) {
        int red = Color.red(color);
        int green = Color.green(color);
        int blue = Color.blue(color);
        return Color.argb(alpha, red, green, blue);
    }

    //取消所有动画
    private void cancleScroll() {
        if (animator_text_scroll != null) {
            animator_text_scroll.cancel();
            animator_text_scroll = null;
        }

        if (animator_marque != null) {
            animator_marque.cancel();
            animator_marque = null;
        }
    }


    /**
     * Function
     * *********************************************************************************************
     */

    @Override
    public void setText(CharSequence text, BufferType type) {
        super.setText(text, type);
        normalString = (String) text;
        currentString = (String) text;
        postInvalidate();
    }

    public void setAnimaledText(CharSequence text) {
        mAnimaledText = (String) text;
    }

    @Override
    public void setEnabled(boolean enabled) {
        super.setEnabled(enabled);
        if (enabled) {
            if (paint != null) paint.setColor(backgroundColor);
            postInvalidate();
        } else {
            if (paint != null) paint.setColor(unEnabled_backgroundColor);
            postInvalidate();
        }
    }

    //快速点击解决
    private long startLoading_click_millons = 0L;


    /**
     * SmartLoadingView -- startLoading：开启加载动画
     */
    public void startLoading() {
        long currentMillons = System.currentTimeMillis();
        if ((currentMillons - startLoading_click_millons) < 500L) {
            return;
        }
        startLoading_click_millons = currentMillons;
        //没有在loading的情况下才能点击（没有在请求网络的情况下）
        if (!isLoading) {
            textColor = textColorOriginal;
            cancleScroll();
            startDrawOk = false;
            currentString = normalString;
            this.setClickable(false);
            paint.setColor(backgroundColor);
            isLoading = true;
            animatorSet.start();
        }
    }

    private boolean isFinished;

    public boolean isFinished() {
        return isFinished;
    }

    private long finished_click_millons = 0L;

    /**
     * SmartLoadingView -- setFinished：设置SmartLoadingView的finished状态（无动画）
     */
    public void setFinished(boolean success) {
        long currentMillons = System.currentTimeMillis();
        if ((currentMillons - finished_click_millons) < 500L) {
            return;
        }
        finished_click_millons = currentMillons;

        if (mButtonType == SMART_FULL_SCREEN) {
            //全屏
            throw new IllegalArgumentException("hl_button_type = \"smart_full_screen\"，不属于关注模式");
        } else if (mButtonType == SMART_BUTTON) {
            //正常方式
            setFinishedReal(success, SMART_BUTTON);
        } else if (mButtonType == SMART_TICK) {
            //打勾方式
            setFinishedReal(success, SMART_TICK);
        } else if (mButtonType == SMART_TICK_HIDE) {
            //打勾--隐藏方式
            setFinishedReal(success, SMART_TICK_HIDE);
        } else if (mButtonType == SMART_TICK_CENTER_HIDE) {
            //打勾--移至中间--隐藏方式
            setFinishedReal(success, SMART_TICK_CENTER_HIDE);
        }
    }

    private void setFinishedReal(final boolean success, final int buttonType) {
        //兼容控件还未渲染到页面上
        if (getWidth() == 0 && getHeight() == 0) {
            SmartLoadingView.this.addOnLayoutChangeListener(new OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View view, int i, int i1, int i2, int i3, int i4, int i5, int i6, int i7) {
                    changeButtonStatus(success, buttonType);
                    removeOnLayoutChangeListener(this);
                }
            });
        } else {
            changeButtonStatus(success, buttonType);
        }

    }

    public void finishLoading() {
        finishLoading(true, null);
    }

    public void finishLoading(boolean success) {
        finishLoading(success, null);
    }

    public void finishLoading(LoadingListener listener) {
        finishLoading(true, listener);
    }


    /**
     * SmartLoadingView -- finishLoading：结束SmartLoadingView的finished状态（动画过渡）
     */
    private long finishLoading_click_millons = 0L;

    public void finishLoading(boolean success, LoadingListener listener) {
        long currentMillons = System.currentTimeMillis();
        if ((currentMillons - finishLoading_click_millons) < 500L) {
            return;
        }
        finishLoading_click_millons = currentMillons;

        if (mButtonType == SMART_FULL_SCREEN) {
            if (success) {
                fullScreen(listener);
            } else {
                backToEnd(listener, false);
            }
        } else if (mButtonType == SMART_BUTTON) {
            if (success) {
                isFinished = true;
                backToEnd(listener, true);
            } else {
                backToStart(listener, false);
            }
        } else if (mButtonType == SMART_TICK) {
            if (success) {
                isFinished = true;
                startTick(listener);
            } else {
                backToStart(listener, false);
            }
        } else if (mButtonType == SMART_TICK_HIDE) {
            if (success) {
                isFinished = true;
                startTickHide(listener);
            } else {
                backToStart(listener, false);
            }
        } else if (mButtonType == SMART_TICK_CENTER_HIDE) {
            if (success) {
                isFinished = true;
                startTickCenterHide(listener);
            } else {
                backToStart(listener, false);
            }
        }
    }

    /**
     * =================================  fullScreen start  =======================================
     */

    /**
     * SmartLoadingView -- finishLoadingWithFullScreen：全屏api额外封装，为快速跳转（动画过度，只支持smart_full_screen模式）
     */
    public void finishLoadingWithFullScreen(Activity activity, Class clazz) {
        if (mButtonType == SMART_FULL_SCREEN) {
            fullScreen(activity, clazz);
        } else {
            throw new IllegalArgumentException("此api只支持，hl_button_type = \"smart_full_screen\"");
        }
    }

    private void backToEnd(final LoadingListener listener, final boolean success) {
        if (isLoading) {
            if (!animatorSet.isRunning()) {
                backToEndReal(listener, success);
            } else {
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backToEndReal(listener, success);
                    }
                }, 1000);
            }
        }
    }

    private void backToEndReal(final LoadingListener listener, final boolean success) {
        if (animatorNetfail.isRunning()) {
            //防止重复播放动画
            return;
        }
        currentString = mAnimaledText;
        paint.setColor(animaled_backgroundColor);
        textColor = animaled_textColor;
        animatorNetfail.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (listener != null) {
                    listener.loadingFinish(success);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorNetfail.start();
    }

    //开启全屏动画，并监听动画进度
    private void fullScreen(final LoadingListener listener) {
        //必须，点击了最开始的动画处于，加载状态，才能获得回调
        if (isLoading) {
            if (!animatorSet.isRunning()) {
                toBigCircle(listener);
            } else {
                //当点击按钮的时候请求网络，假如动画执行时间大于网络请求时间，
                //那么咱们默认，执行完加载动画后，立即执行加载成功动画
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toBigCircle(listener);
                    }
                }, 1000);
            }
        }
    }

    //开启全屏动画，并简化跳转进度
    private void fullScreen(final Activity activity, final Class clazz) {
        //必须，点击了最开始的动画处于，加载状态，才能获得回调
        if (isLoading) {
            if (!animatorSet.isRunning()) {
                toBigCircle(activity, clazz);
            } else {
                //当点击按钮的时候请求网络，加入动画执行时间大于网络请求时间，
                //那么咱们默认，执行完加载动画后，立即执行加载成功动画
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        toBigCircle(activity, clazz);
                    }
                }, 1000);
            }
        }
    }

    /**
     * =================================  fullScreen end  =========================================
     */


    /**
     * =================================  smartButton start  =======================================
     */
    private void backToStart(final LoadingListener listener, final boolean success) {
        if (isLoading) {
            if (!animatorSet.isRunning()) {
                backToStartReal(listener, success);
            } else {
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        backToStartReal(listener, success);
                    }
                }, 1000);
            }
        }
    }

    private void backToStartReal(final LoadingListener listener, final boolean success) {
        if (animatorNetfail.isRunning()) {
            //防止重复播放动画
            return;
        }
        currentString = normalString;
        paint.setColor(backgroundColor);
        textColor = textColorOriginal;
        animatorNetfail.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animator) {

            }

            @Override
            public void onAnimationEnd(Animator animator) {
                if (listener != null) {
                    listener.loadingFinish(success);
                }
            }

            @Override
            public void onAnimationCancel(Animator animator) {

            }

            @Override
            public void onAnimationRepeat(Animator animator) {

            }
        });
        animatorNetfail.start();
    }

    private void changeButtonStatus(boolean success, int buttonType) {
        reset();
        isFinished = success;
        if (success) {
            if (buttonType == SMART_BUTTON) {
                paint.setColor(animaled_backgroundColor);
                currentString = mAnimaledText;
                textColor = animaled_textColor;
                textPaint.setColor(textColor);
                postInvalidate();
            } else {
                current_left = default_all_distance;
                int nowAlpha = textAlpha / 2 - (current_left * textAlpha / default_all_distance) < 0 ? 0 : textAlpha / 2 - (current_left * textAlpha / default_all_distance);
                textPaint.setColor(addAlpha(textColor, nowAlpha));
                if (current_left == default_all_distance) {
                    startDrawOk = true;
                }
                effect = new DashPathEffect(new float[]{pathMeasure.getLength(), pathMeasure.getLength()}, 0 * pathMeasure.getLength());
                okPaint.setPathEffect(effect);
                postInvalidate();
                //SMART_TICK | SMART_TICK_HIDE | SMART_TICK_CENTER_HIDE
                if (buttonType == SMART_TICK_HIDE || buttonType == SMART_TICK_CENTER_HIDE) {
                    setVisibility(View.INVISIBLE);
                }
            }
        }

    }

    /**
     * =================================  smartButton end  =========================================
     */


    /**
     * =================================  smartTick start  =========================================
     */

    private void startTick(final LoadingListener listener) {
        if (isLoading) {
            if (!animatorSet.isRunning()) {
                startTickAnimal(listener, false);
            } else {
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTickAnimal(listener, false);
                    }
                }, 1000);
            }
        }
    }

    private void startTickHide(final LoadingListener listener) {
        if (isLoading) {
            if (!animatorSet.isRunning()) {
                startTickAnimal(listener, true);
            } else {
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTickAnimal(listener, true);
                    }
                }, 1000);
            }
        }
    }

    private void startTickAnimal(final LoadingListener listener, final boolean hide) {
        set_draw_ok_animation();
        animator_draw_ok.start();
        animator_draw_ok.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.loadingFinish(true);
                }
                isLoading = false;
                isFinished = true;
                setClickable(true);
                if (hide) {
                    //是否隐藏
                    Animation animations = AnimationUtils.loadAnimation(getContext(), R.anim.alpha_hide);
                    setAnimation(animations);
                    animations.start();
                    setVisibility(View.INVISIBLE);
                }
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }
        });
    }


    /**
     * =================================  smartTick end  ===========================================
     */


    /**
     * =================================  smartTickCenterHide start  ===============================
     */

    private void startTickCenterHide(final LoadingListener listener) {
        if (isLoading) {
            if (!animatorSet.isRunning()) {
                startTickCenterHideAnimal(listener);
            } else {
                this.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        startTickCenterHideAnimal(listener);
                    }
                }, 1000);
            }
        }
    }

    private void startTickCenterHideAnimal(final LoadingListener listener) {
        //如果是要移动到中间的模式的话
        int[] location = new int[2];
        SmartLoadingView.this.getLocationOnScreen(location);
        FrameLayout.LayoutParams layoutParams = new FrameLayout.LayoutParams(height, height);
        layoutParams.leftMargin = location[0] + (width / 2 - height / 2);
        layoutParams.topMargin = location[1];

        final OkView okView = new OkView(getContext());
        okView.setLayoutParams(layoutParams);
        okView.setCircleColor(backgroundColor);
        okView.setOkColor(textColor);
        okView.setRadius(height / 2);

        final ViewGroup activityDecorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        activityDecorView.addView(okView);
        okView.start(duration);
        //初始真正的那个View
        setVisibility(View.INVISIBLE);
        //reset();

        //当前屏幕中心位置
        int window_center_x = UIUtil.getWidth(getContext()) / 2;
        int window_center_y = UIUtil.getHeight(getContext()) / 2;

        //okView当前的中心点
        int okView_center_x = location[0] + width / 2;
        int okView_center_y = location[1] + height / 2;

        ObjectAnimator translationY = ObjectAnimator.ofFloat(okView, "translationY", 0f, window_center_y - okView_center_y).setDuration(duration);
        ObjectAnimator translationX = ObjectAnimator.ofFloat(okView, "translationX", 0f, window_center_x - okView_center_x).setDuration(duration);

        ObjectAnimator toViewAnimatorX = ObjectAnimator.ofFloat(okView, "scaleX", 1f, 1.3f).setDuration(duration / 2);
        toViewAnimatorX.setRepeatMode(ValueAnimator.REVERSE);
        toViewAnimatorX.setRepeatCount(1);
        toViewAnimatorX.setInterpolator(new AnticipateInterpolator());
        ObjectAnimator toViewAnimatorY = ObjectAnimator.ofFloat(okView, "scaleY", 1f, 1.3f).setDuration(duration / 2);
        toViewAnimatorY.setRepeatMode(ValueAnimator.REVERSE);
        toViewAnimatorY.setRepeatCount(1);
        toViewAnimatorY.setInterpolator(new AnticipateInterpolator());
        AnimatorSet animatorScale = new AnimatorSet();
        animatorScale.playTogether(toViewAnimatorX, toViewAnimatorY);

        ObjectAnimator toViewAnimatorAlpha = ObjectAnimator.ofFloat(okView, "alpha", 1f, 0f).setDuration(duration);
        //这里就用代码实现把
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.play(translationY).with(translationX).before(animatorScale).before(toViewAnimatorAlpha);
        animatorSet.start();
        animatorSet.addListener(new Animator.AnimatorListener() {
            @Override
            public void onAnimationStart(Animator animation) {

            }

            @Override
            public void onAnimationEnd(Animator animation) {
                if (listener != null) {
                    listener.loadingFinish(true);
                }
                isLoading = false;
                setClickable(true);
                activityDecorView.removeView(okView);
            }

            @Override
            public void onAnimationCancel(Animator animation) {

            }

            @Override
            public void onAnimationRepeat(Animator animation) {

            }

        });
    }


    /**
     * =================================  smartTickCenterHide end  =================================
     */


    public interface LoadingListener {
        void loadingFinish(boolean success);

    }

    /**
     * ---------------------------------------------------------------------------------------------
     */

    //立即重置状态
    private void reset() {
        isFinished = false;
        //画笔颜色重置
        textColor = textColorOriginal;
        textPaint.setColor(textColor);
        setClickable(true);
        currentString = normalString;
        textPaint.setColor(textColor);
        circleAngle = corners_radius;
        paint.setColor(backgroundColor);
        current_left = 0;
        isDrawLoading = false;
        startDrawOk = false;
        isLoading = false;
        invalidate();

        animator_draw_ok.cancel();
        animatorSet.cancel();
        animatorNetfail.cancel();
        if (circlBigView != null) {
            circlBigView.setCircleR(0);
        }
        setVisibility(View.VISIBLE);
    }

    private void toBigCircle(LoadingListener listener) {
        circlBigView.setRadius(this.getMeasuredHeight() / 2);
        circlBigView.setColorBg(backgroundColor);
        int[] location = new int[2];
        this.getLocationOnScreen(location);
        circlBigView.setXY(location[0] + this.getMeasuredWidth() / 2, location[1]);
        ViewGroup activityDecorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activityDecorView.removeView(circlBigView);
        activityDecorView.addView(circlBigView, layoutParams);
        circlBigView.startShowAni(listener, this);
    }


    private void toBigCircle(Activity activity, Class clazz) {
        circlBigView.setRadius(this.getMeasuredHeight() / 2);
        circlBigView.setColorBg(backgroundColor);
        int[] location = new int[2];
        this.getLocationOnScreen(location);
        circlBigView.setXY(location[0] + this.getMeasuredWidth() / 2, location[1]);
        ViewGroup activityDecorView = (ViewGroup) ((Activity) getContext()).getWindow().getDecorView();
        ViewGroup.LayoutParams layoutParams = new ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT);
        activityDecorView.removeView(circlBigView);
        activityDecorView.addView(circlBigView, layoutParams);
        circlBigView.startShowAni(activity, clazz);
    }

}
