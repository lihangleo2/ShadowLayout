package com.lihang;

/**
 * Created by leo
 * on 2019/7/9.
 * 阴影控件
 */

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.FrameLayout;


public class ShadowLayout extends FrameLayout {

    private int mBackGroundColor;
    private int mShadowColor;
    private float mShadowLimit;
    private float mCornerRadius;
    private float mDx;
    private float mDy;
    private boolean leftShow;
    private boolean rightShow;
    private boolean topShow;
    private boolean bottomShow;
    private Paint shadowPaint;
    private Paint paint;

    private int leftPading;
    private int topPading;
    private int rightPading;
    private int bottomPading;
    //阴影布局子空间区域
    private RectF rectf = new RectF();


    public ShadowLayout(Context context) {
        this(context, null);
    }

    public ShadowLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }


    public ShadowLayout(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initView(context, attrs);
    }

    public void setMDx(float mDx) {
        if (Math.abs(mDx) > mShadowLimit) {
            if (mDx > 0) {
                this.mDx = mShadowLimit;
            } else {
                this.mDx = -mShadowLimit;
            }
        } else {
            this.mDx = mDx;
        }
        setPading();
    }


    public void setMDy(float mDy) {
        if (Math.abs(mDy) > mShadowLimit) {
            if (mDy > 0) {
                this.mDy = mShadowLimit;
            } else {
                this.mDy = -mShadowLimit;
            }
        } else {
            this.mDy = mDy;
        }
        setPading();
    }


    public float getmCornerRadius() {
        return mCornerRadius;
    }


    public void setmCornerRadius(int mCornerRadius) {
        this.mCornerRadius = mCornerRadius;
        setBackgroundCompat(getWidth(), getHeight());
    }


    public float getmShadowLimit() {
        return mShadowLimit;
    }

    public void setmShadowLimit(int mShadowLimit) {
        this.mShadowLimit = mShadowLimit;
        setPading();
    }


    public void setmShadowColor(int mShadowColor) {
        this.mShadowColor = mShadowColor;
        setBackgroundCompat(getWidth(), getHeight());
    }

    public void setLeftShow(boolean leftShow) {
        this.leftShow = leftShow;
        setPading();
    }

    public void setRightShow(boolean rightShow) {
        this.rightShow = rightShow;
        setPading();
    }

    public void setTopShow(boolean topShow) {
        this.topShow = topShow;
        setPading();
    }

    public void setBottomShow(boolean bottomShow) {
        this.bottomShow = bottomShow;
        setPading();
    }


    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (w > 0 && h > 0) {
            setBackgroundCompat(w, h);
        }
    }


    private void initView(Context context, AttributeSet attrs) {
        initAttributes(attrs);
        shadowPaint = new Paint();
        shadowPaint.setAntiAlias(true);
        shadowPaint.setStyle(Paint.Style.FILL);


        //矩形画笔
        paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setStyle(Paint.Style.FILL);
        paint.setColor(mBackGroundColor);

        setPading();
    }


    public void setPading() {
        int xPadding = (int) (mShadowLimit + Math.abs(mDx));
        int yPadding = (int) (mShadowLimit + Math.abs(mDy));

        if (leftShow) {
            leftPading = xPadding;
        } else {
            leftPading = 0;
        }

        if (topShow) {
            topPading = yPadding;
        } else {
            topPading = 0;
        }


        if (rightShow) {
            rightPading = xPadding;
        } else {
            rightPading = 0;
        }

        if (bottomShow) {
            bottomPading = yPadding;
        } else {
            bottomPading = 0;
        }

        setPadding(leftPading, topPading, rightPading, bottomPading);
    }


    @SuppressWarnings("deprecation")
    private void setBackgroundCompat(int w, int h) {
        Bitmap bitmap = createShadowBitmap(w, h, mCornerRadius, mShadowLimit, mDx, mDy, mShadowColor, Color.TRANSPARENT);
        BitmapDrawable drawable = new BitmapDrawable(bitmap);
        if (Build.VERSION.SDK_INT <= Build.VERSION_CODES.JELLY_BEAN) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }
    }


    private void initAttributes(AttributeSet attrs) {
        TypedArray attr = getContext().obtainStyledAttributes(attrs, R.styleable.ShadowLayout);
        if (attr == null) {
            return;
        }

        try {
            //默认是显示
            leftShow = attr.getBoolean(R.styleable.ShadowLayout_hl_leftShow, true);
            rightShow = attr.getBoolean(R.styleable.ShadowLayout_hl_rightShow, true);
            bottomShow = attr.getBoolean(R.styleable.ShadowLayout_hl_bottomShow, true);
            topShow = attr.getBoolean(R.styleable.ShadowLayout_hl_topShow, true);
            mCornerRadius = attr.getDimension(R.styleable.ShadowLayout_hl_cornerRadius, getResources().getDimension(R.dimen.dp_0));
            //默认扩散区域宽度
            mShadowLimit = attr.getDimension(R.styleable.ShadowLayout_hl_shadowLimit, getResources().getDimension(R.dimen.dp_5));

            //x轴偏移量
            mDx = attr.getDimension(R.styleable.ShadowLayout_hl_dx, 0);
            //y轴偏移量
            mDy = attr.getDimension(R.styleable.ShadowLayout_hl_dy, 0);
            mShadowColor = attr.getColor(R.styleable.ShadowLayout_hl_shadowColor, getResources().getColor(R.color.default_shadow_color));
            //判断传入的颜色值是否有透明度
            isAddAlpha(mShadowColor);
            mBackGroundColor = attr.getColor(R.styleable.ShadowLayout_hl_shadowBackColor, getResources().getColor(R.color.default_shadowback_color));
        } finally {
            attr.recycle();
        }
    }


    private Bitmap createShadowBitmap(int shadowWidth, int shadowHeight, float cornerRadius, float shadowRadius,
                                      float dx, float dy, int shadowColor, int fillColor) {
        //优化阴影bitmap大小,将尺寸缩小至原来的1/4。
        dx = dx / 4;
        dy = dy / 4;
        shadowWidth = shadowWidth / 4;
        shadowHeight = shadowHeight / 4;
        cornerRadius = cornerRadius / 4;
        shadowRadius = shadowRadius / 4;

        Bitmap output = Bitmap.createBitmap(shadowWidth, shadowHeight, Bitmap.Config.ARGB_4444);
        Canvas canvas = new Canvas(output);

        RectF shadowRect = new RectF(
                shadowRadius,
                shadowRadius,
                shadowWidth - shadowRadius,
                shadowHeight - shadowRadius);

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



        shadowPaint.setColor(fillColor);
        if (!isInEditMode()) {
            shadowPaint.setShadowLayer(shadowRadius, dx, dy, shadowColor);
        }

        canvas.drawRoundRect(shadowRect, cornerRadius, cornerRadius, shadowPaint);
        return output;
    }
    

   
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        rectf.left = leftPading;
        rectf.top = topPading;
        rectf.right = getWidth() - rightPading;
        rectf.bottom = getHeight() - bottomPading;
        int trueHeight = (int) (rectf.bottom - rectf.top);
        if (mCornerRadius > trueHeight / 2) {
            //画圆角矩形
            canvas.drawRoundRect(rectf, trueHeight / 2, trueHeight / 2, paint);
//            canvas.drawRoundRect(rectf, trueHeight / 2, trueHeight / 2, paintStroke);
        } else {
            canvas.drawRoundRect(rectf, mCornerRadius, mCornerRadius, paint);
//            canvas.drawRoundRect(rectf, mCornerRadius, mCornerRadius, paintStroke);
        }
    }
    
    
            public void isAddAlpha(int color) {
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
    
    
    public static int convertToColorInt(String argb)
			throws IllegalArgumentException {
 
		if (!argb.startsWith("#")) {
			argb = "#" + argb;
		}
 
		return Color.parseColor(argb);
	}

}

