package com.example.collectedview.JavaView;

import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.collectedview.Utils.ViewUtil;


/**
 * 加载视图
 */
public class LoadingView extends View {

    private float dotRadius, offset1, offset2, offset3;
    private int dotSpacing;
    private Paint dotPaint;
    private ValueAnimator dotAnimator1, dotAnimator2, dotAnimator3;

    public LoadingView(Context context) {
        this(context, null);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LoadingView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        dotRadius =(int) ViewUtil.dip2px(getContext(), 6.f);
        dotSpacing =(int) ViewUtil.dip2px(getContext(), 8.f);

        dotPaint = new Paint();
        dotPaint.setStyle(Paint.Style.FILL);
        dotPaint.setColor(Color.parseColor("#FFD102"));
        dotPaint.setAntiAlias(true);

        dotAnimator1 = ValueAnimator.ofFloat(0, dotRadius);
        dotAnimator1.setDuration(600);
        dotAnimator1.setRepeatMode(ValueAnimator.REVERSE);
        dotAnimator1.setRepeatCount(ValueAnimator.INFINITE);
        dotAnimator1.addUpdateListener((animator) ->{
            offset1 = (float) animator.getAnimatedValue();

            invalidate();
        });

        dotAnimator2 = ValueAnimator.ofFloat(0, dotRadius);
        dotAnimator2.setDuration(600);
        dotAnimator2.setStartDelay(200);
        dotAnimator2.setRepeatMode(ValueAnimator.REVERSE);
        dotAnimator2.setRepeatCount(ValueAnimator.INFINITE);
        dotAnimator2.addUpdateListener((animator) ->{
            offset2 = (float) animator.getAnimatedValue();
            invalidate();
        });

        dotAnimator3 = ValueAnimator.ofFloat(0, dotRadius);
        dotAnimator3.setDuration(600);
        dotAnimator3.setStartDelay(400);
        dotAnimator3.setRepeatMode(ValueAnimator.REVERSE);
        dotAnimator3.setRepeatCount(ValueAnimator.INFINITE);
        dotAnimator3.addUpdateListener((animator) ->{
            offset3 = (float) animator.getAnimatedValue();
            invalidate();
        });
    }


    @Override
    protected void onDraw(Canvas canvas)
    {
        int cx = getWidth()/2;
        int cy = getHeight()/2;
        dotPaint.setAlpha((int) (255-(255*(offset2/dotRadius))));
        canvas.drawCircle(cx, cy, dotRadius-offset2, dotPaint);
        dotPaint.setAlpha((int) (255-(255*((offset1)/dotRadius))));
        canvas.drawCircle(cx - dotRadius*2 - dotSpacing, cy, dotRadius-offset1, dotPaint);
        dotPaint.setAlpha((int) (255-(255*(offset3/dotRadius))));
        canvas.drawCircle(cx + dotRadius*2 + dotSpacing, cy, dotRadius-offset3, dotPaint);
    }

    /**
     * 开始加载
     */
    public void startLoading()
    {
        if(!dotAnimator1.isRunning())
        {
            dotAnimator1.start();
            dotAnimator2.start();
            dotAnimator3.start();
        }
    }

    /**
     * 停止加载
     */
    public void stopLoading()
    {
        if(dotAnimator1.isRunning())
        {
            dotAnimator1.cancel();
            dotAnimator2.cancel();
            dotAnimator3.cancel();
        }
    }
}
