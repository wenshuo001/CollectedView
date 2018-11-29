package com.example.collectedview.JavaView;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.view.View;

/**
 * Rotate Grid
 * Created by Krear on 2018/9/22.
 */

public class GridRotateView extends View
{
    private Paint gridPaint;
    private Paint backGroundPaint;
    private Path rectPath;
    private boolean isBlack;
    private ValueAnimator animator1, animator2, animator3, animator4, animator5, animator6, animator7, animator8;
    private int[] degrees;
    private float radius;
    private boolean isRun;

    public GridRotateView(Context context) {
        this(context, null);
    }

    public GridRotateView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public GridRotateView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        gridPaint = new Paint();
        gridPaint.setStyle(Paint.Style.FILL);
        gridPaint.setColor(Color.WHITE);
        gridPaint.setAntiAlias(true);

        backGroundPaint = new Paint();
        backGroundPaint.setStyle(Paint.Style.FILL);
        backGroundPaint.setColor(Color.BLACK);
        backGroundPaint.setAntiAlias(true);

        rectPath = new Path();
        animator1 = ValueAnimator.ofInt(225, 315);
        animator1.setDuration(500);
        animator2 = ValueAnimator.ofInt(225, 315);
        animator2.setDuration(500);
        animator2.setStartDelay(150);
        animator3 = ValueAnimator.ofInt(225, 315);
        animator3.setDuration(500);
        animator3.setStartDelay(300);
        animator4 = ValueAnimator.ofInt(225, 315);
        animator4.setDuration(500);
        animator4.setStartDelay(450);
        animator5 = ValueAnimator.ofInt(225, 315);
        animator5.setDuration(500);
        animator5.setStartDelay(600);
        animator6 = ValueAnimator.ofInt(225, 315);
        animator6.setDuration(500);
        animator6.setStartDelay(750);
        animator7 = ValueAnimator.ofInt(225, 315);
        animator7.setDuration(500);
        animator7.setStartDelay(900);
        animator8 = ValueAnimator.ofInt(225, 315);
        animator8.setDuration(500);
        animator8.setStartDelay(1050);

        degrees = new int[]{225,225,225,225,225,225,225,225};
        animator1.addUpdateListener(animation -> {
            degrees[0] = (int) animation.getAnimatedValue();
            invalidate();
        });

        animator2.addUpdateListener(animation -> {
            degrees[1] = (int) animation.getAnimatedValue();
            invalidate();
        });

        animator3.addUpdateListener(animation -> {
            degrees[2] = (int) animation.getAnimatedValue();
            invalidate();
        });

        animator4.addUpdateListener(animation -> {
            degrees[3] = (int) animation.getAnimatedValue();
            invalidate();
        });

        animator5.addUpdateListener(animation -> {
            degrees[4] = (int) animation.getAnimatedValue();
            invalidate();
        });

        animator6.addUpdateListener(animation -> {
            degrees[5] = (int) animation.getAnimatedValue();
            invalidate();
        });

        animator7.addUpdateListener(animation -> {
            degrees[6] = (int) animation.getAnimatedValue();
            invalidate();
        });

        animator8.addUpdateListener(animation -> {
            degrees[7] = (int) animation.getAnimatedValue();
            invalidate();
        });

        animator8.addListener(new AnimatorListenerAdapter() {
            @Override
            public void onAnimationEnd(Animator animation) {
                isBlack = !isBlack;
                startAnimation();
            }
        });
    }

    @Override
    protected void onDraw(Canvas canvas) {
        int top = (getHeight()-getWidth())/2;//顶部距离
        int size = getWidth()/8;//格子宽高
        float s = size/2;
        if (!isRun)
            radius = (float) Math.sqrt(s*s + s*s);//对角线的平方等于两个边长的平方的和.例如,边长为2的正方形  所以这里求出半径

        if (isBlack) {
            gridPaint.setColor(Color.BLACK);
            backGroundPaint.setColor(Color.WHITE);
        }
        else {
            gridPaint.setColor(Color.WHITE);
            backGroundPaint.setColor(Color.BLACK);
        }

        RectF rectF = new RectF(0, top, getWidth(), getHeight() - top);
        canvas.drawRect(rectF, backGroundPaint);

        for (int i=0; i<8; i++)
        {
            int degree = degrees[i];
            for(int j=0; j<4; j++)
            {
                float startX;
                float startY = top + i*size;
                if (isBlack)
                    startX = size + size*2*j;
                else
                    startX = 0 + size*2*j;

                rectPath.reset();
                rectPath.moveTo(getRotateX(startX + s, degree + 0), getRotateY(startY + s, degree + 0));
                rectPath.lineTo(getRotateX(startX + s, degree + 90), getRotateY(startY + s, degree + 90));
                rectPath.lineTo(getRotateX(startX + s, degree + 180), getRotateY(startY + s, degree + 180));
                rectPath.lineTo(getRotateX(startX + s, degree + 270), getRotateY(startY + s, degree + 270));
                rectPath.close();
                canvas.drawPath(rectPath, gridPaint);
            }
            isBlack = !isBlack;
        }

        if (!isRun)
        {
            isRun = true;
            startAnimation();
        }
    }

    private void startAnimation()
    {
        animator1.start();
        animator2.start();
        animator3.start();
        animator4.start();
        animator5.start();
        animator6.start();
        animator7.start();
        animator8.start();
    }

    public float getRotateX(float centerX, int degree)
    {
        return (float) (centerX + radius * Math.cos(degree * 3.14 / 180));
    }

    public float getRotateY(float centerY, int degree)
    {
        return (float) (centerY +radius * Math.sin(degree *3.14 /180));
    }
}
