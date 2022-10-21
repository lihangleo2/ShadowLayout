package com.lihang;


import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.DashPathEffect;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.GradientDrawable;
import android.graphics.drawable.RippleDrawable;
import android.graphics.drawable.ShapeDrawable;
import android.graphics.drawable.shapes.RoundRectShape;
import android.os.Build;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;

/**
 * Created by leo
 * on 2019/7/9.
 * 阴影控件
 */

public class ShadowLayout extends FrameLayout {
    /***
     * Shadow
     */
    private Paint shadowPaint;//阴影画笔
    private int mShadowColor;//阴影颜色
    private float mShadowLimit;//阴影扩散区域大小
    //阴影x,y偏移量
    private float mDx;
    private float mDy;
    //阴影圆角属性，也是shape的圆角属性
    private float mCornerRadius;//圆角大小，如四角没有单独设置，则为大小为mCornerRadius
    private float mCornerRadius_leftTop;//单独设置左上角圆角大小。（同理以下）
    private float mCornerRadius_rightTop;
    private float mCornerRadius_leftBottom;
    private float mCornerRadius_rightBottom;
    //阴影四边可见属性
    private boolean leftShow;//false表示左边不可见
    private boolean rightShow;
    private boolean topShow;
    private boolean bottomShow;
    //子布局与父布局的padding（即通过padding来实现mShadowLimit的大小和阴影展示）
    private int leftPadding;
    private int topPadding;
    private int rightPadding;
    private int bottomPadding;
    private RectF rectf = new RectF();//阴影布局子空间区域
    private View firstView;//如有子View则为子View，否则为ShadowLayout本身
    //
    private boolean isSym;//控件区域是否对称，如不对称则区域跟随阴影走
    private boolean isShowShadow = true;//是否使用阴影，可能存在不使用阴影只使用shape


    /**
     * Shape
     */
    private static final int MODE_PRESSED = 1;
    private static final int MODE_SELECTED = 2;
    private static final int MODE_RIPPLE = 3;
    private static final int MODE_DASHLINE = 4;
    //
    private int shapeModeType;//ShadowLayout的shapeMode，默认是pressed.
    GradientDrawable gradientDrawable;//shape功能最终用系统类GradientDrawable代替
    private Drawable layoutBackground;//正常情况下的drawable（与mBackGroundColor不可共存）
    private Drawable layoutBackground_true;
    private int mBackGroundColor;//正常情况下的color（默认为白色，与layoutBackground不可共存）
    private int mBackGroundColor_true = -101;
    //填充渐变色
    private int startColor;
    private int centerColor;
    private int endColor;
    private int angle;
    //边框画笔
    private int current_stroke_color;
    private float stroke_with;
    private int stroke_color;
    private int stroke_color_true;
    private float stroke_dashWidth = -1;
    private float stroke_dashGap = -1;

    /**
     * ClickAble
     */
    private boolean isClickable;//是否可点击
    private Drawable clickAbleFalseDrawable;//不可点击状态下的drawable（与clickAbleFalseColor不可共存）
    private int clickAbleFalseColor = -101;//不可点击状态下的填充color（与clickAbleFalseDrawable不可共存）

    /**
     * ShadowLayout绑定的textView
     */
    private int mTextViewResId = -1;
    private TextView mTextView;
    private int textColor;
    private int textColor_true;
    private String text;
    private String text_true;

    /**
     * 虚线
     */
    private Paint mPaintDash;
    private Path dashPath;


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ShadowLayout(Context context) {
        this(context, null);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initView(Context context, AttributeSet attrs) {
        initAttributes(attrs);
        if (isDashLine()) {
            return;
        }
        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStyle(Paint.Style.FILL);
        gradientDrawable = new GradientDrawable();
        gradientDrawable.setColors(new int[]{mBackGroundColor, mBackGroundColor});
        if (stroke_color != -101) {
            current_stroke_color = stroke_color;
        }
        setPadding();
    }

    //是否为线性模式
    private boolean isDashLine() {
        return shapeModeType == ShadowLayout.MODE_DASHLINE;
    }

    private void initDashLine() {
        mPaintDash = new Paint();
        mPaintDash.setAntiAlias(true);
        mPaintDash.setColor(stroke_color);
        mPaintDash.setStyle(Paint.Style.STROKE);
        mPaintDash.setPathEffect(new DashPathEffect(new float[]{stroke_dashWidth, stroke_dashGap}, 0));
        dashPath = new Path();
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void initAttributes(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        shapeModeType = attr.getInt(R.styleable.ShadowLayout_hl_shapeMode, ShadowLayout.MODE_PRESSED);
        //如果为线性模式，只用到以下属性
        if (isDashLine()) {
            stroke_color = attr.getColor(R.styleable.ShadowLayout_hl_strokeColor, -101);
            stroke_dashWidth = attr.getDimension(R.styleable.ShadowLayout_hl_stroke_dashWidth, -1);
            stroke_dashGap = attr.getDimension(R.styleable.ShadowLayout_hl_stroke_dashGap, -1);

            if (stroke_color == -101) {
                throw new UnsupportedOperationException("shapeMode为MODE_DASHLINE,需设置stroke_color值");
            }

            if (stroke_dashWidth == -1) {
                throw new UnsupportedOperationException("shapeMode为MODE_DASHLINE,需设置stroke_dashWidth值");
            }

            if ((stroke_dashWidth == -1 && stroke_dashGap != -1) || (stroke_dashWidth != -1 && stroke_dashGap == -1)) {
                throw new UnsupportedOperationException("使用了虚线边框,必须设置以下2个属性：ShadowLayout_hl_stroke_dashWidth，ShadowLayout_hl_stroke_dashGap");
            }
            initDashLine();

            attr.recycle();
            return;
        }

        isShowShadow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHidden, false);
        leftShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenLeft, false);
        rightShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenRight, false);
        bottomShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenBottom, false);
        topShow = !attr.getBoolean(R.styleable.ShadowLayout_hl_shadowHiddenTop, false);
        mCornerRadius = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius, getResources().getDimension(R.dimen.dp_0));
        mCornerRadius_leftTop = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftTop, -1);
        mCornerRadius_leftBottom = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_leftBottom, -1);
        mCornerRadius_rightTop = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightTop, -1);
        mCornerRadius_rightBottom = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius_rightBottom, -1);

        //默认扩散区域宽度
        mShadowLimit = attr.getDimension(R.styleable.ShadowLayout_hl_shadowLimit, 0);
        if (mShadowLimit == 0) {
            //如果阴影没有设置阴影扩散区域，那么默认隐藏阴影
            isShowShadow = false;
        }

        //x轴偏移量
        mDx = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetX, 0);
        //y轴偏移量
        mDy = attr.getDimension(R.styleable.ShadowLayout_hl_shadowOffsetY, 0);
        mShadowColor = attr.getColor(R.styleable.ShadowLayout_hl_shadowColor, getResources().getColor(R.color.default_shadow_color));

        isSym = attr.getBoolean(R.styleable.ShadowLayout_hl_shadowSymmetry, true);

        //背景颜色的点击(默认颜色为白色)
        mBackGroundColor = getResources().getColor(R.color.default_shadowback_color);

        Drawable background = attr.getDrawable(R.styleable.ShadowLayout_hl_layoutBackground);
        if (background != null) {
            if (background instanceof ColorDrawable) {
                ColorDrawable colorDrawable = (ColorDrawable) background;
                mBackGroundColor = colorDrawable.getColor();

            } else {
                layoutBackground = background;
            }
        }

        Drawable trueBackground = attr.getDrawable(R.styleable.ShadowLayout_hl_layoutBackground_true);
        if (trueBackground != null) {
            if (trueBackground instanceof ColorDrawable) {
                ColorDrawable colorDrawableTrue = (ColorDrawable) trueBackground;
                mBackGroundColor_true = colorDrawableTrue.getColor();

            } else {
                layoutBackground_true = trueBackground;
            }
        }

        if (mBackGroundColor_true != -101 && layoutBackground != null) {
            throw new UnsupportedOperationException("使用了ShadowLayout_hl_layoutBackground_true属性，必须先设置ShadowLayout_hl_layoutBackground属性。且设置颜色时，必须保持都为颜色");
        }

        if (layoutBackground == null && layoutBackground_true != null) {
            throw new UnsupportedOperationException("使用了ShadowLayout_hl_layoutBackground_true属性，必须先设置ShadowLayout_hl_layoutBackground属性。且设置图片时，必须保持都为图片");
        }


        //边框颜色的点击
        stroke_color = attr.getColor(R.styleable.ShadowLayout_hl_strokeColor, -101);
        stroke_color_true = attr.getColor(R.styleable.ShadowLayout_hl_strokeColor_true, -101);

        if (stroke_color == -101 && stroke_color_true != -101) {
            throw new UnsupportedOperationException("使用了ShadowLayout_hl_strokeColor_true属性，必须先设置ShadowLayout_hl_strokeColor属性");
        }

        stroke_with = attr.getDimension(R.styleable.ShadowLayout_hl_strokeWith, dip2px(1));

        stroke_dashWidth = attr.getDimension(R.styleable.ShadowLayout_hl_stroke_dashWidth, -1);
        stroke_dashGap = attr.getDimension(R.styleable.ShadowLayout_hl_stroke_dashGap, -1);
        if ((stroke_dashWidth == -1 && stroke_dashGap != -1) || (stroke_dashWidth != -1 && stroke_dashGap == -1)) {
            throw new UnsupportedOperationException("使用了虚线边框,必须设置以下2个属性：ShadowLayout_hl_stroke_dashWidth，ShadowLayout_hl_stroke_dashGap");
        }

        Drawable clickAbleFalseBackground = attr.getDrawable(R.styleable.ShadowLayout_hl_layoutBackground_clickFalse);
        if (clickAbleFalseBackground != null) {
            if (clickAbleFalseBackground instanceof ColorDrawable) {
                ColorDrawable colorDrawableClickableFalse = (ColorDrawable) clickAbleFalseBackground;
                clickAbleFalseColor = colorDrawableClickableFalse.getColor();
            } else {
                clickAbleFalseDrawable = clickAbleFalseBackground;
            }
        }


        startColor = attr.getColor(R.styleable.ShadowLayout_hl_startColor, -101);
        centerColor = attr.getColor(R.styleable.ShadowLayout_hl_centerColor, -101);
        endColor = attr.getColor(R.styleable.ShadowLayout_hl_endColor, -101);
        if (startColor != -101) {
            //说明设置了渐变色的起始色
            if (endColor == -101) {
                throw new UnsupportedOperationException("使用了ShadowLayout_hl_startColor渐变起始色，必须搭配终止色ShadowLayout_hl_endColor");
            }
        }


        angle = attr.getInt(R.styleable.ShadowLayout_hl_angle, 0);
        if (angle % 45 != 0) {
            throw new IllegalArgumentException("Linear gradient requires 'angle' attribute to be a multiple of 45");
        }


        if (shapeModeType == ShadowLayout.MODE_RIPPLE) {
            //如果是ripple的话
            if (mBackGroundColor == -101 || mBackGroundColor_true == -101) {
                throw new NullPointerException("使用了ShadowLayout的水波纹，必须设置使用了ShadowLayout_hl_layoutBackground和使用了ShadowLayout_hl_layoutBackground_true属性，且为颜色值");
            }

            //如果是设置了图片的话，那么也不支持水波纹
            if (layoutBackground != null) {
                shapeModeType = ShadowLayout.MODE_PRESSED;
            }
        }


        mTextViewResId = attr.getResourceId(R.styleable.ShadowLayout_hl_bindTextView, -1);
        textColor = attr.getColor(R.styleable.ShadowLayout_hl_textColor, -101);
        textColor_true = attr.getColor(R.styleable.ShadowLayout_hl_textColor_true, -101);
        text = attr.getString(R.styleable.ShadowLayout_hl_text);
        text_true = attr.getString(R.styleable.ShadowLayout_hl_text_true);

        isClickable = attr.getBoolean(R.styleable.ShadowLayout_clickable, true);
        setClickable(isClickable);
        attr.recycle();

    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (isDashLine()) {
            //解决不执行onDraw方法的bug
            this.setBackgroundColor(Color.parseColor("#00000000"));
            return;
        }
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h);
            if (startColor != -101) {
                gradient(gradientDrawable);
            }
        }
    }


    /**
     * 被加载到页面后触发。也就是执行Inflater.inflate()方法后
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (isDashLine()) {
            if (getChildAt(0) != null) {
                throw new UnsupportedOperationException("shapeMode为MODE_DASHLINE，不支持子view");
            }
            return;
        }
        if (mTextViewResId != -1) {
            mTextView = findViewById(mTextViewResId);
            if (mTextView == null) {
                throw new NullPointerException("ShadowLayout找不到hl_bindTextView，请确保绑定的资源id在ShadowLayout内");
            } else {
                if (textColor == -101) {
                    textColor = mTextView.getCurrentTextColor();
                }


                if (textColor_true == -101) {
                    textColor_true = mTextView.getCurrentTextColor();
                }

                mTextView.setTextColor(textColor);
                if (!TextUtils.isEmpty(text)) {
                    mTextView.setText(text);
                }
            }
        }

        firstView = getChildAt(0);

        if (layoutBackground != null) {
            if (isShowShadow == true && mShadowLimit > 0 && getChildAt(0) == null) {
                throw new UnsupportedOperationException("使用了图片又加上阴影的情况下，必须加上子view才会生效!~");
            }
        }

        if (firstView == null) {
            firstView = ShadowLayout.this;
            //当子View都没有的时候。默认不使用阴影
            isShowShadow = false;
        }

        if (firstView != null) {

            //selector样式不受clickable的影响
            if (shapeModeType == ShadowLayout.MODE_SELECTED) {
                setmBackGround(layoutBackground, "onFinishInflate");
            } else {
                if (isClickable) {
                    setmBackGround(layoutBackground, "onFinishInflate");
                } else {
                    setmBackGround(clickAbleFalseDrawable, "onFinishInflate");
                    if (clickAbleFalseColor != -101) {
                        gradientDrawable.setColors(new int[]{clickAbleFalseColor, clickAbleFalseColor});
                    }
                }
            }

        }

    }


    /**
     * 重写点击事件
     *
     * @param event
     * @return
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (shapeModeType == ShadowLayout.MODE_RIPPLE) {
            //如果是水波纹模式，那么不需要进行下面的渲染，采用系统ripper即可
            if (isClickable) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mTextView != null) {
                            mTextView.setTextColor(textColor_true);
                            if (!TextUtils.isEmpty(text_true)) {
                                mTextView.setText(text_true);
                            }
                        }
                        break;

                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        if (mTextView != null) {
                            mTextView.setTextColor(textColor);
                            if (!TextUtils.isEmpty(text)) {
                                mTextView.setText(text);
                            }
                        }
                        break;
                }
            }
            return super.onTouchEvent(event);
        }

        if (mBackGroundColor_true != -101 || stroke_color_true != -101 || layoutBackground_true != null) {
            if (isClickable && shapeModeType == ShadowLayout.MODE_PRESSED) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        if (mBackGroundColor_true != -101) {
                            //切换的颜色
                            gradientDrawable.setColors(new int[]{mBackGroundColor_true, mBackGroundColor_true});
                        }
                        if (stroke_color_true != -101) {
                            current_stroke_color = stroke_color_true;
                        }

                        if (layoutBackground_true != null) {
                            setmBackGround(layoutBackground_true, "onTouchEvent");
                        }
                        postInvalidate();

                        if (mTextView != null) {
                            mTextView.setTextColor(textColor_true);
                            if (!TextUtils.isEmpty(text_true)) {
                                mTextView.setText(text_true);
                            }
                        }
                        break;

                    case MotionEvent.ACTION_CANCEL:
                    case MotionEvent.ACTION_UP:
                        gradientDrawable.setColors(new int[]{mBackGroundColor, mBackGroundColor});
                        if (startColor != -101) {
                            gradient(gradientDrawable);
                        }
                        if (stroke_color != -101) {
                            current_stroke_color = stroke_color;
                        }

                        if (layoutBackground != null) {
                            setmBackGround(layoutBackground, "onTouchEvent");
                        }
                        postInvalidate();

                        if (mTextView != null) {
                            mTextView.setTextColor(textColor);
                            if (!TextUtils.isEmpty(text)) {
                                mTextView.setText(text);
                            }
                        }
                        break;
                }
            }
        }
        return super.onTouchEvent(event);
    }


    /**
     * 切换select样式
     *
     * @param selected
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @Override
    public void setSelected(boolean selected) {
        super.setSelected(selected);
        if (getWidth() != 0) {
            if (shapeModeType == ShadowLayout.MODE_SELECTED) {
                if (selected) {
                    if (mBackGroundColor_true != -101) {
                        gradientDrawable.setColors(new int[]{mBackGroundColor_true, mBackGroundColor_true});
                    }

                    if (stroke_color_true != -101) {
                        current_stroke_color = stroke_color_true;
                    }
                    if (layoutBackground_true != null) {
                        setmBackGround(layoutBackground_true, "setSelected");
                    }

                    if (mTextView != null) {
                        mTextView.setTextColor(textColor_true);
                        if (!TextUtils.isEmpty(text_true)) {
                            mTextView.setText(text_true);
                        }
                    }

                } else {
                    gradientDrawable.setColors(new int[]{mBackGroundColor, mBackGroundColor});
                    if (startColor != -101) {
                        gradient(gradientDrawable);
                    }

                    if (stroke_color != -101) {
                        current_stroke_color = stroke_color;
                    }

                    if (layoutBackground != null) {
                        setmBackGround(layoutBackground, "setSelected");
                    }

                    if (mTextView != null) {
                        mTextView.setTextColor(textColor);
                        if (!TextUtils.isEmpty(text)) {
                            mTextView.setText(text);
                        }
                    }

                }
                postInvalidate();
            }
        } else {
            addOnLayoutChangeListener(new View.OnLayoutChangeListener() {
                @Override
                public void onLayoutChange(View v, int left, int top, int right, int bottom, int oldLeft, int oldTop, int oldRight, int oldBottom) {
                    removeOnLayoutChangeListener(this);
                    setSelected(isSelected());
                }
            });
        }
    }


    //解决xml设置clickable = false时。代码设置true时，点击事件无效的bug
    private OnClickListener onClickListener;

    /**
     * 重写系统setOnClickListener,以配合自定义属性isClickable实现是否可点击事件
     *
     * @param l
     */
    @Override
    public void setOnClickListener(@Nullable OnClickListener l) {
        this.onClickListener = l;
        if (isClickable) {
            super.setOnClickListener(l);
        }
    }

    /**
     * clickable发生变化时，shadowLayout样式切换的方法。
     * 由public改为private不向用户提供，只能在内部使用
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void changeSwitchClickable() {
        //不可点击的状态只在press mode的模式下生效
        if (shapeModeType == ShadowLayout.MODE_PRESSED && firstView != null) {

            //press mode
            if (!isClickable) {
                //不可点击的状态。
                if (clickAbleFalseColor != -101) {
                    //说明设置了颜色
                    if (layoutBackground != null) {
                        //说明此时是设置了图片的模式
                        firstView.getBackground().setAlpha(0);
                    }
                    gradientDrawable.setColors(new int[]{clickAbleFalseColor, clickAbleFalseColor});
                    postInvalidate();


                } else if (clickAbleFalseDrawable != null) {
                    //说明设置了背景图
                    setmBackGround(clickAbleFalseDrawable, "changeSwitchClickable");
                    gradientDrawable.setColors(new int[]{Color.parseColor("#00000000"), Color.parseColor("#00000000")});
                    postInvalidate();
                }
            } else {
                //可点击的状态
                if (layoutBackground != null) {
                    setmBackGround(layoutBackground, "changeSwitchClickable");
                } else {
                    if (firstView.getBackground() != null) {
                        firstView.getBackground().setAlpha(0);
                    }
                }
                gradientDrawable.setColors(new int[]{mBackGroundColor, mBackGroundColor});
                postInvalidate();
            }
        }
    }


    /**
     * 对子View进行绘制，也是剪裁子view的关键
     *
     * @param canvas
     */
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void dispatchDraw(Canvas canvas) {

        int trueHeight = (int) (rectf.bottom - rectf.top);
        if (getChildAt(0) != null) {
            if (mCornerRadius_leftTop == -1 && mCornerRadius_leftBottom == -1 && mCornerRadius_rightTop == -1 && mCornerRadius_rightBottom == -1) {
                //说明没有设置过任何特殊角、且是半圆。
                if (mCornerRadius > trueHeight / 2) {
                    Path path = new Path();
                    path.addRoundRect(rectf, trueHeight / 2, trueHeight / 2, Path.Direction.CW);
                    canvas.clipPath(path);
                } else {
                    Path path = new Path();
                    path.addRoundRect(rectf, mCornerRadius, mCornerRadius, Path.Direction.CW);
                    canvas.clipPath(path);
                }
            } else {
                float[] outerR = getCornerValue(trueHeight);
                Path path = new Path();
                path.addRoundRect(leftPadding, topPadding, getWidth() - rightPadding, getHeight() - bottomPadding, outerR, Path.Direction.CW);
                canvas.clipPath(path);
            }

        }
        super.dispatchDraw(canvas);
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //如果为MODE_DASHLINE的情况下，走drawLine方法
        if (isDashLine()) {
            drawLine(canvas);
            return;
        }

        rectf.left = leftPadding;
        rectf.top = topPadding;
        rectf.right = getWidth() - rightPadding;
        rectf.bottom = getHeight() - bottomPadding;
        int trueHeight = (int) (rectf.bottom - rectf.top);

        //是否设置了stroke
        if (stroke_color != -101) {
            //判断当前stroke高度是否大于控件高度的一半
            if (stroke_with > trueHeight / 2) {
                stroke_with = trueHeight / 2;
            }
        }

        //设置了背景图片的话就不做处理
        if (layoutBackground == null && layoutBackground_true == null) {
            //outerR 已经做了对特殊角的判断。无论是特殊角还是统一的都返回一组数组角度 outerR
            float[] outerR = getCornerValue(trueHeight);
            //如果不是ripple模式
            if (shapeModeType != ShadowLayout.MODE_RIPPLE) {
                drawGradientDrawable(canvas, rectf, outerR);
            } else {
                ripple(outerR);
            }
        }
    }

    public void drawLine(Canvas canvas) {
        int currentWidth = getWidth();
        int currentHeight = getHeight();

        if (currentWidth > currentHeight) {
            //说明是横向的
            mPaintDash.setStrokeWidth(currentHeight);
            dashPath.reset();
            dashPath.moveTo(0,currentHeight/2);
            dashPath.lineTo(currentWidth,currentHeight/2);
        } else {
            //说明是纵向的
            mPaintDash.setStrokeWidth(currentWidth);
            dashPath.reset();
            dashPath.moveTo(currentWidth/2,0);
            dashPath.lineTo(currentWidth/2,currentHeight);

        }

        canvas.drawPath(dashPath, mPaintDash);
    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void drawGradientDrawable(Canvas canvas, RectF rectf, float[] cornerRadiusArr) {
        //solidcolor
//        gradientDrawable.setColor(paint.getColor());
        //区域
        gradientDrawable.setBounds((int) rectf.left, (int) rectf.top, (int) rectf.right, (int) rectf.bottom);
        //stroke
        if (stroke_color != -101) {
            //根据系统shape,对stroke_with,进行四舍五入
            if (stroke_dashWidth != -1) {
                gradientDrawable.setStroke(Math.round(stroke_with), current_stroke_color, stroke_dashWidth, stroke_dashGap);
            } else {
                gradientDrawable.setStroke(Math.round(stroke_with), current_stroke_color);
            }
        }
        //cornerRadiusArr 已经判断，如果没有特殊角也是返回一数组
        gradientDrawable.setCornerRadii(cornerRadiusArr);
        gradientDrawable.draw(canvas);


    }


    private float[] getCornerValue(int trueHeight) {
        int leftTop;
        int rightTop;
        int rightBottom;
        int leftBottom;
        if (mCornerRadius_leftTop == -1) {
            leftTop = (int) mCornerRadius;
        } else {
            leftTop = (int) mCornerRadius_leftTop;
        }

        if (leftTop > trueHeight / 2) {
            leftTop = trueHeight / 2;
        }

        if (mCornerRadius_rightTop == -1) {
            rightTop = (int) mCornerRadius;
        } else {
            rightTop = (int) mCornerRadius_rightTop;
        }

        if (rightTop > trueHeight / 2) {
            rightTop = trueHeight / 2;
        }

        if (mCornerRadius_rightBottom == -1) {
            rightBottom = (int) mCornerRadius;
        } else {
            rightBottom = (int) mCornerRadius_rightBottom;
        }

        if (rightBottom > trueHeight / 2) {
            rightBottom = trueHeight / 2;
        }


        if (mCornerRadius_leftBottom == -1) {
            leftBottom = (int) mCornerRadius;
        } else {
            leftBottom = (int) mCornerRadius_leftBottom;
        }

        if (leftBottom > trueHeight / 2) {
            leftBottom = trueHeight / 2;
        }

        float[] outerR = new float[]{leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom};//左上，右上，右下，左下
        return outerR;
    }


    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void ripple(float[] outRadius) {

        int[][] stateList = new int[][]{
                new int[]{android.R.attr.state_pressed},
                new int[]{android.R.attr.state_focused},
                new int[]{android.R.attr.state_activated},
                new int[]{}
        };

        //正常色
        int normalColor = mBackGroundColor;
        //按压后的颜色
        int pressedColor = mBackGroundColor_true;
        int[] stateColorList = new int[]{
                pressedColor,
                pressedColor,
                pressedColor,
                normalColor
        };
        ColorStateList colorStateList = new ColorStateList(stateList, stateColorList);

        RoundRectShape roundRectShape = new RoundRectShape(outRadius, null, null);
        ShapeDrawable maskDrawable = new ShapeDrawable();
        maskDrawable.setShape(roundRectShape);
        maskDrawable.getPaint().setStyle(Paint.Style.FILL);

        if (stroke_color != -101) {
            if (stroke_dashWidth != -1) {
                gradientDrawable.setStroke(Math.round(stroke_with), current_stroke_color, stroke_dashWidth, stroke_dashGap);
            } else {
                gradientDrawable.setStroke(Math.round(stroke_with), current_stroke_color);
            }
        }
        //outRadius 无论是否有特殊角都返回的数组
        gradientDrawable.setCornerRadii(outRadius);
        if (startColor != -101) {
            gradient(gradientDrawable);
        }

        //contentDrawable实际是默认初始化时展示的；maskDrawable 控制了rippleDrawable的范围
        RippleDrawable rippleDrawable = new RippleDrawable(colorStateList, gradientDrawable, maskDrawable);
        firstView.setBackground(rippleDrawable);

    }


    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    private void gradient(GradientDrawable gradientDrawable) {
        if (!isClickable) {
            return;
        }

        //左上 x,y   leftPadding, topPadding,
        //右下 x,y   getWidth() - rightPadding, getHeight() - bottomPadding
        int[] colors;
        if (centerColor == -101) {
            colors = new int[]{startColor, endColor};
        } else {
            colors = new int[]{startColor, centerColor, endColor};
        }
        gradientDrawable.setColors(colors);

        if (angle < 0) {
            int trueAngle = angle % 360;
            angle = trueAngle + 360;
        }

        //当设置的角度大于0的时候
        //这个要算出每隔45度
        int trueAngle = angle % 360;
        int angleFlag = trueAngle / 45;
        switch (angleFlag) {
            case 0://0°
                gradientDrawable.setOrientation(GradientDrawable.Orientation.LEFT_RIGHT);
//                linearGradient = new LinearGradient(leftPadding, topPadding, getWidth() - rightPadding, topPadding, colors, null, Shader.TileMode.CLAMP);
//                paint.setShader(linearGradient);
                break;

            case 1://45°
                gradientDrawable.setOrientation(GradientDrawable.Orientation.BL_TR);
                break;

            case 2://90°
                gradientDrawable.setOrientation(GradientDrawable.Orientation.BOTTOM_TOP);
                break;

            case 3://135°
                gradientDrawable.setOrientation(GradientDrawable.Orientation.BR_TL);
                break;
            case 4://180°
                gradientDrawable.setOrientation(GradientDrawable.Orientation.RIGHT_LEFT);
                break;

            case 5://225°
                gradientDrawable.setOrientation(GradientDrawable.Orientation.TR_BL);
                break;

            case 6://270°
                gradientDrawable.setOrientation(GradientDrawable.Orientation.TOP_BOTTOM);
                break;

            case 7://315°
                gradientDrawable.setOrientation(GradientDrawable.Orientation.TL_BR);
                break;

        }

    }


    private int dip2px(float dipValue) {
        float scale = getContext().getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    private void setPadding() {
        if (isShowShadow && mShadowLimit > 0) {
            //控件区域是否对称，默认是对称。不对称的话，那么控件区域随着阴影区域走
            if (isSym) {
                int xPadding = (int) (mShadowLimit + Math.abs(mDx));
                int yPadding = (int) (mShadowLimit + Math.abs(mDy));

                if (leftShow) {
                    leftPadding = xPadding;
                } else {
                    leftPadding = 0;
                }

                if (topShow) {
                    topPadding = yPadding;
                } else {
                    topPadding = 0;
                }


                if (rightShow) {
                    rightPadding = xPadding;
                } else {
                    rightPadding = 0;
                }

                if (bottomShow) {
                    bottomPadding = yPadding;
                } else {
                    bottomPadding = 0;
                }


            } else {
                if (Math.abs(mDy) > mShadowLimit) {
                    if (mDy > 0) {
                        mDy = mShadowLimit;
                    } else {
                        mDy = 0 - mShadowLimit;
                    }
                }


                if (Math.abs(mDx) > mShadowLimit) {
                    if (mDx > 0) {
                        mDx = mShadowLimit;
                    } else {
                        mDx = 0 - mShadowLimit;
                    }
                }

                if (topShow) {
                    topPadding = (int) (mShadowLimit - mDy);
                } else {
                    topPadding = 0;
                }

                if (bottomShow) {
                    bottomPadding = (int) (mShadowLimit + mDy);
                } else {
                    bottomPadding = 0;
                }


                if (rightShow) {
                    rightPadding = (int) (mShadowLimit - mDx);
                } else {
                    rightPadding = 0;
                }


                if (leftShow) {
                    leftPadding = (int) (mShadowLimit + mDx);
                } else {
                    leftPadding = 0;
                }
            }
            setPadding(leftPadding, topPadding, rightPadding, bottomPadding);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(int w, int h) {
        if (isShowShadow) {
            //判断传入的颜色值是否有透明度
            isAddAlpha(mShadowColor);
            Bitmap bitmap = createShadowBitmap(w, h, mCornerRadius, mShadowLimit, mDx, mDy, mShadowColor, Color.TRANSPARENT);
            BitmapDrawable drawable = new BitmapDrawable(bitmap);
            if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
                setBackgroundDrawable(drawable);
            } else {
                setBackground(drawable);
            }
        } else {
            if (getChildAt(0) == null) {
                if (layoutBackground != null) {
                    firstView = ShadowLayout.this;
                    if (isClickable) {
                        setmBackGround(layoutBackground, "setBackgroundCompat");
                    } else {
                        changeSwitchClickable();
                    }
                } else {
                    //解决不执行onDraw方法的bug就是给其设置一个透明色
                    this.setBackgroundColor(Color.parseColor("#00000000"));
                }
            } else {
                this.setBackgroundColor(Color.parseColor("#00000000"));
            }
        }

    }


    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {
        //优化阴影bitmap大小,将尺寸缩小至原来的1/4。
        dx = dx / 4;
        dy = dy / 4;
        shadowWidth = shadowWidth / 4 == 0 ? 1 : shadowWidth / 4;
        shadowHeight = shadowHeight / 4 == 0 ? 1 : shadowHeight / 4;
        cornerRadius = cornerRadius / 4;
        shadowRadius = shadowRadius / 4;

        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        //这里缩小limit的是因为，setShadowLayer后会将bitmap扩散到shadowWidth，shadowHeight
        //同时也要根据某边的隐藏情况去改变

        float rect_left = 0;
        float rect_right = 0;
        float rect_top = 0;
        float rect_bottom = 0;
        if (leftShow) {
            rect_left = shadowRadius;
        } else {
//            rect_left = 0;
            float maxLeftTop = Math.max(cornerRadius, mCornerRadius_leftTop);
            float maxLeftBottom = Math.max(cornerRadius, mCornerRadius_leftBottom);
            float maxLeft = Math.max(maxLeftTop, maxLeftBottom);
//            rect_left = maxLeft;
            float trueMaxLeft = Math.max(maxLeft, shadowRadius);
            rect_left = trueMaxLeft / 2;

        }

        if (topShow) {
            rect_top = shadowRadius;
        } else {
//            rect_top = 0;
            float maxLeftTop = Math.max(cornerRadius, mCornerRadius_leftTop);
            float maxRightTop = Math.max(cornerRadius, mCornerRadius_rightTop);
            float maxTop = Math.max(maxLeftTop, maxRightTop);
//            rect_top = maxTop;
            float trueMaxTop = Math.max(maxTop, shadowRadius);
            rect_top = trueMaxTop / 2;

        }

        if (rightShow) {
            rect_right = shadowWidth - shadowRadius;
        } else {
//            rect_right = shadowWidth;
            float maxRightTop = Math.max(cornerRadius, mCornerRadius_rightTop);
            float maxRightBottom = Math.max(cornerRadius, mCornerRadius_rightBottom);
            float maxRight = Math.max(maxRightTop, maxRightBottom);
//            rect_right = shadowWidth - maxRight;
            float trueMaxRight = Math.max(maxRight, shadowRadius);
            rect_right = shadowWidth - trueMaxRight / 2;
        }


        if (bottomShow) {
            rect_bottom = shadowHeight - shadowRadius;
        } else {
//            rect_bottom = shadowHeight;
            float maxLeftBottom = Math.max(cornerRadius, mCornerRadius_leftBottom);
            float maxRightBottom = Math.max(cornerRadius, mCornerRadius_rightBottom);
            float maxBottom = Math.max(maxLeftBottom, maxRightBottom);
//            rect_bottom = shadowHeight - maxBottom;
            float trueMaxBottom = Math.max(maxBottom, shadowRadius);
            rect_bottom = shadowHeight - trueMaxBottom / 2;
        }


        RectF shadowRect = new RectF(
                rect_left,
                rect_top,
                rect_right,
                rect_bottom);

        if (isSym) {
            if (dy > 0) {
                shadowRect.top += dy;
                shadowRect.bottom -= dy;
            } else if (dy < 0) {
                shadowRect.top += Math.abs(dy);
                shadowRect.bottom -= Math.abs(dy);
            }

            if (dx > 0) {
                shadowRect.left += dx;
                shadowRect.right -= dx;
            } else if (dx < 0) {

                shadowRect.left += Math.abs(dx);
                shadowRect.right -= Math.abs(dx);
            }
        } else {
            shadowRect.top -= dy;
            shadowRect.bottom -= dy;
            shadowRect.right -= dx;
            shadowRect.left -= dx;
        }


        shadowPaint.setColor(fillColor);
        if (!isInEditMode()) {//dx  dy
            shadowPaint.setShadowLayer(shadowRadius / 2, dx, dy, shadowColor);
        }

        if (mCornerRadius_leftBottom == -1 && mCornerRadius_leftTop == -1 && mCornerRadius_rightTop == -1 && mCornerRadius_rightBottom == -1) {
            //如果没有设置整个属性，那么按原始去画
            canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);
        } else {
            //目前最佳的解决方案
            rectf.left = leftPadding;
            rectf.top = topPadding;
            rectf.right = getWidth() - rightPadding;
            rectf.bottom = getHeight() - bottomPadding;


            shadowPaint.setAntiAlias(true);
            int leftTop;
            if (mCornerRadius_leftTop == -1) {
                leftTop = (int) mCornerRadius / 4;
            } else {
                leftTop = (int) mCornerRadius_leftTop / 4;
            }
            int leftBottom;
            if (mCornerRadius_leftBottom == -1) {
                leftBottom = (int) mCornerRadius / 4;
            } else {
                leftBottom = (int) mCornerRadius_leftBottom / 4;
            }

            int rightTop;
            if (mCornerRadius_rightTop == -1) {
                rightTop = (int) mCornerRadius / 4;
            } else {
                rightTop = (int) mCornerRadius_rightTop / 4;
            }

            int rightBottom;
            if (mCornerRadius_rightBottom == -1) {
                rightBottom = (int) mCornerRadius / 4;
            } else {
                rightBottom = (int) mCornerRadius_rightBottom / 4;
            }

            float[] outerR = new float[]{leftTop, leftTop, rightTop, rightTop, rightBottom, rightBottom, leftBottom, leftBottom};//左上，右上，右下，左下
            Path path = new Path();
            path.addRoundRect(shadowRect, outerR, Path.Direction.CW);
            canvas.drawPath(path, shadowPaint);
        }

        return output;
    }


    private void isAddAlpha(int color) {
        //获取单签颜色值的透明度，如果没有设置透明度，默认加上#2a
        if (Color.alpha(color) == 255) {
            String red = Integer.toHexString(Color.red(color));
            String green = Integer.toHexString(Color.green(color));
            String blue = Integer.toHexString(Color.blue(color));

            if (red.length() == 1) {
                red = "0" + red;
            }

            if (green.length() == 1) {
                green = "0" + green;
            }

            if (blue.length() == 1) {
                blue = "0" + blue;
            }
            String endColor = "#2a" + red + green + blue;
            mShadowColor = convertToColorInt(endColor);
        }
    }


    private static int convertToColorInt(String argb)
            throws IllegalArgumentException {

        if (!argb.startsWith("#")) {
            argb = "#" + argb;
        }

        return Color.parseColor(argb);
    }


    private void setmBackGround(Drawable drawable, String currentTag) {
        firstView.setTag(R.id.action_container, currentTag);
        if (firstView != null && drawable != null) {
            if (mCornerRadius_leftTop == -1 && mCornerRadius_leftBottom == -1 && mCornerRadius_rightTop == -1 && mCornerRadius_rightBottom == -1) {
                GlideRoundUtils.setRoundCorner(firstView, drawable, mCornerRadius, currentTag);
            } else {
                int leftTop;
                if (mCornerRadius_leftTop == -1) {
                    leftTop = (int) mCornerRadius;
                } else {
                    leftTop = (int) mCornerRadius_leftTop;
                }
                int leftBottom;
                if (mCornerRadius_leftBottom == -1) {
                    leftBottom = (int) mCornerRadius;
                } else {
                    leftBottom = (int) mCornerRadius_leftBottom;
                }

                int rightTop;
                if (mCornerRadius_rightTop == -1) {
                    rightTop = (int) mCornerRadius;
                } else {
                    rightTop = (int) mCornerRadius_rightTop;
                }

                int rightBottom;
                if (mCornerRadius_rightBottom == -1) {
                    rightBottom = (int) mCornerRadius;
                } else {
                    rightBottom = (int) mCornerRadius_rightBottom;
                }

                GlideRoundUtils.setCorners(firstView, drawable, leftTop, leftBottom, rightTop, rightBottom, currentTag);
            }
        }
    }

    /**
     * 改变按钮是否为可点击状态
     *
     * @param clickable
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setClickable(boolean clickable) {
        isExceptionByDashLine();
        super.setClickable(clickable);
        this.isClickable = clickable;
        changeSwitchClickable();
        if (isClickable) {
            super.setOnClickListener(onClickListener);
        }

        if (gradientDrawable != null) {
            if (startColor != -101 && endColor != -101) {
                gradient(gradientDrawable);
            }
        }
    }

    /**
     * 设置渐变颜色和渐变方向
     *
     * @param startColor
     * @param endColor
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setGradientColor(int startColor, int endColor) {
        setGradientColor(angle, startColor, endColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setGradientColor(int angle, int startColor, int endColor) {

        setGradientColor(angle, startColor, -101, endColor);
    }

    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setGradientColor(int angle, int startColor, int centerColor, int endColor) {
        isExceptionByDashLine();
        if (angle % 45 != 0) {
            throw new IllegalArgumentException("Linear gradient requires 'angle' attribute to be a multiple of 45");
        }
        this.angle = angle;
        this.startColor = startColor;
        this.centerColor = centerColor;
        this.endColor = endColor;
        gradient(gradientDrawable);
        postInvalidate();
    }


    private void isExceptionByDashLine() {
        if (isDashLine()) {
            throw new RuntimeException("shapeMode为MODE_DASHLINE,不允许设置此属性");
        }
    }

    /**
     * 是否隐藏阴影
     *
     * @param isShowShadow
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setShadowHidden(boolean isShowShadow) {
        isExceptionByDashLine();
        this.isShowShadow = !isShowShadow;
        if (getWidth() != 0 && getHeight() != 0) {
            setBackgroundCompat(getWidth(), getHeight());
        }
    }

    /**
     * 设置x轴阴影的偏移量
     *
     * @param mDx
     */
    public void setShadowOffsetX(float mDx) {
        isExceptionByDashLine();
        if (isShowShadow) {
            if (Math.abs(mDx) > mShadowLimit) {
                if (mDx > 0) {
                    this.mDx = mShadowLimit;
                } else {
                    this.mDx = -mShadowLimit;
                }
            } else {
                this.mDx = mDx;
            }
            setPadding();
        }
    }

    /**
     * 设置y轴阴影的偏移量
     *
     * @param mDy
     */
    public void setShadowOffsetY(float mDy) {
        isExceptionByDashLine();
        if (isShowShadow) {
            if (Math.abs(mDy) > mShadowLimit) {
                if (mDy > 0) {
                    this.mDy = mShadowLimit;
                } else {
                    this.mDy = -mShadowLimit;
                }
            } else {
                this.mDy = mDy;
            }
            setPadding();
        }
    }

    /**
     * 获取当前的圆角值
     *
     * @return
     */
    public float getCornerRadius() {
        return mCornerRadius;
    }

    /**
     * 设置shadowLayout圆角
     *
     * @param mCornerRadius
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setCornerRadius(int mCornerRadius) {
        isExceptionByDashLine();
        this.mCornerRadius = mCornerRadius;
        if (getWidth() != 0 && getHeight() != 0) {
            setBackgroundCompat(getWidth(), getHeight());
        }
    }

    /**
     * 获取阴影扩散区域值
     *
     * @return
     */
    public float getShadowLimit() {
        return mShadowLimit;
    }

    /**
     * 设置阴影扩散区域
     *
     * @param mShadowLimit
     */
    public void setShadowLimit(int mShadowLimit) {
        isExceptionByDashLine();
        if (isShowShadow) {
            this.mShadowLimit = mShadowLimit;
            setPadding();
        }
    }

    /**
     * 设置阴影颜色值
     *
     * @param mShadowColor
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setShadowColor(int mShadowColor) {
        isExceptionByDashLine();
        this.mShadowColor = mShadowColor;
        if (getWidth() != 0 && getHeight() != 0) {
            setBackgroundCompat(getWidth(), getHeight());
        }
    }


    /**
     * 单独设置4个圆角属性
     *
     * @param leftTop
     * @param rightTop
     * @param leftBottom
     * @param rightBottom
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setSpecialCorner(int leftTop, int rightTop, int leftBottom, int rightBottom) {
        isExceptionByDashLine();
        mCornerRadius_leftTop = leftTop;
        mCornerRadius_rightTop = rightTop;
        mCornerRadius_leftBottom = leftBottom;
        mCornerRadius_rightBottom = rightBottom;
        if (getWidth() != 0 && getHeight() != 0) {
            setBackgroundCompat(getWidth(), getHeight());
        }
    }


    /**
     * 单独隐藏某边
     * setShadowHiddenTop：是否隐藏阴影的上边部分
     *
     * @param topShow
     */
    public void setShadowHiddenTop(boolean topShow) {
        isExceptionByDashLine();
        this.topShow = !topShow;
        setPadding();
    }

    public void setShadowHiddenBottom(boolean bottomShow) {
        isExceptionByDashLine();
        this.bottomShow = !bottomShow;
        setPadding();
    }


    public void setShadowHiddenRight(boolean rightShow) {
        isExceptionByDashLine();
        this.rightShow = !rightShow;
        setPadding();
    }


    public void setShadowHiddenLeft(boolean leftShow) {
        isExceptionByDashLine();
        this.leftShow = !leftShow;
        setPadding();
    }


    /**
     * 设置背景颜色值
     *
     * @param color
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setLayoutBackground(int color) {
        isExceptionByDashLine();
        //如果设置了clickable为false，那么不允许动态更换背景
        if (!isClickable) {
            return;
        }

        if (layoutBackground_true != null) {
            throw new UnsupportedOperationException("使用了ShadowLayout_hl_layoutBackground_true属性，要与ShadowLayout_hl_layoutBackground属性统一为颜色");
        }
        mBackGroundColor = color;
        //代码设置背景色后，将渐变色重置
        this.startColor = -101;
        this.centerColor = -101;
        this.endColor = -101;
        if (shapeModeType == ShadowLayout.MODE_SELECTED) {
            //select模式
            if (!this.isSelected()) {
                gradientDrawable.setColors(new int[]{mBackGroundColor, mBackGroundColor});
            }
        } else {
            gradientDrawable.setColors(new int[]{mBackGroundColor, mBackGroundColor});
        }
        postInvalidate();
    }

    /**
     * 设置选中背景颜色值
     *
     * @param color
     */
    @RequiresApi(api = Build.VERSION_CODES.JELLY_BEAN)
    public void setLayoutBackgroundTrue(int color) {
        isExceptionByDashLine();
        if (layoutBackground != null) {
            throw new UnsupportedOperationException("使用了ShadowLayout_hl_layoutBackground属性，要与ShadowLayout_hl_layoutBackground_true属性统一为颜色");
        }
        mBackGroundColor_true = color;
        if (shapeModeType == ShadowLayout.MODE_SELECTED) {
            //select模式
            if (this.isSelected()) {
                gradientDrawable.setColors(new int[]{mBackGroundColor_true, mBackGroundColor_true});
            }
        }
        postInvalidate();
    }


    /**
     * 设置边框颜色值
     *
     * @param color
     */
    public void setStrokeColor(int color) {
        isExceptionByDashLine();
        stroke_color = color;
        if (shapeModeType == ShadowLayout.MODE_SELECTED) {
            //select模式
            if (!this.isSelected()) {
                current_stroke_color = stroke_color;
            }
        } else {
            current_stroke_color = stroke_color;
        }
        postInvalidate();
    }


    /**
     * 设置选中边框颜色值
     *
     * @param color
     */
    public void setStrokeColorTrue(int color) {
        isExceptionByDashLine();
        stroke_color_true = color;
        if (shapeModeType == ShadowLayout.MODE_SELECTED) {
            //select模式
            if (this.isSelected()) {
                current_stroke_color = stroke_color_true;
            }
        }
        postInvalidate();
    }

    /**
     * 设置边框宽度
     *
     * @param stokeWidth
     */
    public void setStrokeWidth(float stokeWidth) {
        isExceptionByDashLine();
        this.stroke_with = stokeWidth;
        postInvalidate();
    }
}
