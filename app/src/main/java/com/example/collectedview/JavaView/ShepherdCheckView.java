package com.example.collectedview.JavaView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.View;

import com.example.collectedview.Utils.LogUtils;
import com.example.collectedview.Utils.ScreenUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Creator :Wen
 * DataTime: 2018/11/26
 * Description:
 */
public class ShepherdCheckView extends View{
    public ShepherdCheckView(Context context) {
        super(context);
        init();
    }

    public ShepherdCheckView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public ShepherdCheckView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }
    private Paint mPaint;
    List<List<RectF>> black_rects=new ArrayList<>();
    List<List<RectF>> white_rects=new ArrayList<>();
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        float plaid_width= screenWidth/8;
        float plaid_height= plaid_width;
        int left=0,top=0,right=0,bottom=0;
        for(int j=0;j<8;j++){

            //左上角不变  变右下角
            bottom += plaid_height;

            List<RectF> brect=new ArrayList<>();
            List<RectF> wrect=new ArrayList<>();
            for(int i=0;i<8/2;i++){

                right  += plaid_width;
                RectF rect1= new RectF(left,top,right,bottom);
                if(j%2==0){
                    //画白格子
                    mPaint.setColor(Color.WHITE);
                    wrect.add(rect1);
                }else {
                    mPaint.setColor(Color.BLACK);
                    brect.add(rect1);
                }

                canvas.drawRect(rect1,mPaint);

                right += plaid_width;
                left += plaid_width;
                RectF rect2= new RectF(left,top,right,bottom);
                if(j%2==0){
                    //画黑格子
                    mPaint.setColor(Color.BLACK);
                    brect.add(rect2);
                }else {
                    mPaint.setColor(Color.WHITE);
                    wrect.add(rect2);
                }
                //x轴变 y不变

                canvas.drawRect(rect2,mPaint);
                right+=plaid_width;
                left+=plaid_width;


            }
            right=0;
            left=0;
            top+=plaid_width;
            black_rects.add(brect);
            white_rects.add(wrect);
        }
        invate(canvas);
    }

    float screenWidth= 0.0f;
    int degress=0;
    private void init(){
        mPaint = new Paint();
        screenWidth=ScreenUtils.getScreenWidth();
    }
    Timer timer = new Timer();
    public void invate(Canvas canvas){
//        for(int i=0;i<black_rects.size();i++) {
//            for (int j = 0; j < black_rects.get(i).size(); j++) {
//                Rect rect = black_rects.get(i).get(j);
//                Matrix matrix = new Matrix();
//                matrix.setRotate(90);
//
////                float py = (rect.bottom - rect.top) / 2;
////                float px = (rect.right - rect.left) / 2;
//
//                TimerTask task = new TimerTask() {
//                    @Override
//                    public void run() {
//                        int degress=0;
//                        degress++;
//                        if(degress==90){
//                            degress=0;
//                        }else {
//
//                            invalidate();
//                        }
//                    }
//                };
//
//                timer.schedule(task, 10);
//            }
//        }
    }


    public void StopView(){
        timer.cancel();
    }

}
