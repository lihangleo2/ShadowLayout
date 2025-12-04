package com.lihang;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.os.Build;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ScrollView;

import androidx.annotation.ColorInt;
import androidx.annotation.RequiresApi;

import com.lihang.utils.AnimalUtil;


/**
 * @Author leo
 * @Address https://github.com/lihangleo2
 * @Date 2025/12/04
 * 自定义系统ScrollView指示器。
 */
public class ScrollViewIndicator extends View {
    private Paint mBackgroundPaint; //背景画笔
    private Paint mProgressPaint; //指示器画笔

    private int mViewWidth;
    private int mViewHeight;
    private int mMaxProgress = 100;//最大进度
    private float mProgress = 0;//当前进度
    private float mStartTop = 0;
    private ScrollView mBindScrollView;


    public ScrollViewIndicator(Context context) {
        this(context, null);
    }

    public ScrollViewIndicator(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ScrollViewIndicator(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init(attrs);

    }

    private void init(AttributeSet attrs) {
        TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ScrollViewIndicator);

        int backgroundColor = typedArray.getColor(R.styleable.ScrollViewIndicator_hl_indicatorBackground, getResources().getColor(R.color.default_background_color));
        mBackgroundPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mBackgroundPaint.setColor(backgroundColor);//背景颜色
        setBackgroundResource(0);//移除设置的背景资源
        //
        int indicatorColor = typedArray.getColor(R.styleable.ScrollViewIndicator_hl_indicatorColor, getResources().getColor(R.color.default_indicator_color));
        mProgressPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mProgressPaint.setColor(indicatorColor);//指示器画笔
        //
        float precent = typedArray.getFloat(R.styleable.ScrollViewIndicator_hl_indicatorLength, 0.2f);
        if (precent < 0 || precent >= 1.0f) {
            precent = 0.2f;
        }
        mProgress = precent * mMaxProgress;

        //
        this.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                if (mBindScrollView != null) {
                    if (mBindScrollView.getMeasuredHeight() < mBindScrollView.getChildAt(0).getMeasuredHeight()) {
                        ScrollViewIndicator.this.setVisibility(View.VISIBLE);
                        AnimalUtil.cancleAnimate(ScrollViewIndicator.this);
                        AnimalUtil.alphaHide(ScrollViewIndicator.this, 500, 1.0f, 0.0f, 1500);
                    } else {
                        ScrollViewIndicator.this.setVisibility(View.GONE);
                    }
                }
                ScrollViewIndicator.this.getViewTreeObserver().removeOnGlobalLayoutListener(this);
            }
        });
    }


    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        mViewWidth = getMeasuredWidth();
        mViewHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        //画背景
//        RectF bgRectF = new RectF(0, 0, mViewWidth, mViewHeight);
        Rect bgRectF = new Rect(0, 0, mViewWidth, mViewHeight);
//        canvas.drawRoundRect(bgRectF, mViewHeight / 2, mViewHeight / 2, mBackgroundPaint);
        canvas.drawRect(bgRectF, mBackgroundPaint);


        //画指示器
        float height = (float) (mViewHeight * (mProgress * 1.0f / mMaxProgress) + 0.5);
        if (height <= mViewWidth) {//圆形
            canvas.drawCircle(mViewWidth / 2, height / 2, height / 2, mProgressPaint);
        } else {
            RectF progressRectF = new RectF(0, mStartTop, mViewWidth, height + mStartTop);
            canvas.drawRoundRect(progressRectF, mViewHeight / 2, mViewHeight / 2, mProgressPaint);
        }


    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mViewWidth = w;
        mViewHeight = h;
        invalidate();
    }


    private void setProgress(float progress) {
        mStartTop = progress * (100 - mProgress) * mViewHeight / 100;
        postInvalidate();
    }


    /**
     * 设置背景颜色
     */
    public void setIndicatorBackgroundColor(@ColorInt int color) {
        mBackgroundPaint.setColor(color);
        postInvalidate();
    }

    /**
     * 设置进度条颜色
     */
    public void setIndicatorColor(@ColorInt int color) {
        mProgressPaint.setColor(color);
        postInvalidate();
    }


    public void bindScrollView(ScrollView mScrollView) {
        mBindScrollView = mScrollView;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            mScrollView.setOnScrollChangeListener((v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
                bindScrollViewFromScrollListener(v, scrollX, scrollY, oldScrollX, oldScrollY);
            });
        }
    }


    //如果项目中用了setOnScrollChangeListener，事件被覆盖，建议使用以下方式触发在setOnScrollChangeListener绑定
    public void bindScrollViewFromScrollListener(View v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
        ScrollView mScrollView = (ScrollView) v;
        mBindScrollView = mScrollView;
        if (mScrollView.getMeasuredHeight() < mScrollView.getChildAt(0).getMeasuredHeight()) {
            ScrollViewIndicator.this.setVisibility(View.VISIBLE);
            View firstView = mScrollView.getChildAt(0);
            ScrollViewIndicator.this.setProgress((((float) mScrollView.getScrollY()) / (firstView.getMeasuredHeight() - mScrollView.getHeight())));

            AnimalUtil.cancleAnimate(ScrollViewIndicator.this);
            AnimalUtil.alphaHide(ScrollViewIndicator.this, 500, 1.0f, 0.0f, 1500);
        }
    }
}
