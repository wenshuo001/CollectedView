package com.example.collectedview.JavaView;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.Log;
import android.util.TypedValue;
import android.view.View;
import android.widget.ProgressBar;

import com.example.collectedview.R;

/**
 * 自定义View，圆形进度条
 */
public class CustomCircleProgress extends ProgressBar{

    //定义一些属性常量
    private static final int PROGRESS_DEFAULT_COLOR = 0xFFd3d6da;//默认圆(边框)的颜色
    private static final int PROGRESS_REACHED_COLOR = 0XFF83B530;//进度条的颜色
    private static final int PROGRESS_REACHED_HEIGHT = 3;//进度条的高度
    private static final int PROGRESS_DEFAULT_HEIGHT = 2;//默认圆的高度
    private static final int PROGRESS_RADIUS = 15;//圆的半径

    //View的当前状态，默认为未开始
    private Status mStatus = Status.start;
    //画笔
    private Paint mPaint;
    //进度条的颜色
    private int mReachedColor = PROGRESS_REACHED_COLOR;
    //进度条的高度
    private int mReachedHeight = dp2px(PROGRESS_REACHED_HEIGHT);
    //默认圆(边框)的颜色
    private int mDefaultColor = PROGRESS_DEFAULT_COLOR;
    //默认圆(边框)的高度
    private int mDefaultHeight = dp2px(PROGRESS_DEFAULT_HEIGHT);
    //圆的半径
    private int mRadius = dp2px(PROGRESS_RADIUS);

    private int bitmap,bitmapEnd;

    private Bitmap drawBitmap,drawBitmapEnd;

    private int height;
    private int width;

    private int dheight;

    private int dwidth;

    private int dwidth1;

    private int height2;
    public CustomCircleProgress(Context context) {
        this(context,null);
    }

    public CustomCircleProgress(Context context, AttributeSet attrs) {
        this(context, attrs,0);
    }

    public CustomCircleProgress(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        //获取自定义属性的值
        TypedArray array = getContext().obtainStyledAttributes(attrs, R.styleable.CustomCircleProgress);
        //默认圆的颜色
        mDefaultColor = array.getColor(R.styleable.CustomCircleProgress_progress_default_color, PROGRESS_DEFAULT_COLOR);
        //进度条的颜色
        mReachedColor = array.getColor(R.styleable.CustomCircleProgress_progress_reached_color, PROGRESS_REACHED_COLOR);
        //默认圆的高度
        mDefaultHeight = (int) array.getDimension(R.styleable.CustomCircleProgress_progress_default_height, mDefaultHeight);
        //进度条的高度
        mReachedHeight = (int) array.getDimension(R.styleable.CustomCircleProgress_progress_reached_height, mReachedHeight);
        //圆的半径
        mRadius = (int) array.getDimension(R.styleable.CustomCircleProgress_circle_radius, mRadius);
        bitmap=array.getResourceId(R.styleable.CustomCircleProgress_imagers,R.mipmap.dowmload);

        bitmapEnd=array.getResourceId(R.styleable.CustomCircleProgress_imagersEnd,R.mipmap.more_book_next);
        /**
         * 把图片资源转为Bitmap对象 并获取bitmap的宽高 因为要把图绘制到控件中心
         */
        drawBitmap= BitmapFactory.decodeResource(context.getResources(),bitmap);
        dwidth=drawBitmap.getWidth();
        dheight=drawBitmap.getHeight();
        drawBitmapEnd=BitmapFactory.decodeResource(context.getResources(),bitmapEnd);

        dwidth1=drawBitmapEnd.getWidth();
        height2=drawBitmapEnd.getHeight();
        //最后不要忘了回收 TypedArray
        array.recycle();

        //设置画笔（new画笔的操作一般不要放在onDraw方法中，因为在绘制的过程中onDraw方法会被多次调用）
        setPaint();
    }

    private void setPaint() {
        mPaint = new Paint();
        //下面是设置画笔的一些属性
        mPaint.setAntiAlias(true);//抗锯齿
        mPaint.setDither(true);//防抖动，绘制出来的图要更加柔和清晰
        mPaint.setStyle(Paint.Style.STROKE);//设置填充样式
        /**
         *  Paint.Style.FILL    :填充内部
         *  Paint.Style.FILL_AND_STROKE  ：填充内部和描边
         *  Paint.Style.STROKE  ：仅描边
         */
        mPaint.setStrokeCap(Paint.Cap.ROUND);//设置画笔笔刷类型
    }

    /**
     * 使用onMeasure方法是因为我们的自定义圆形View的一些属性(如：进度条宽度等)都交给用户自己去自定义了，所以我们需要去测量下
     * 看是否符合要求
     * @param widthMeasureSpec
     * @param heightMeasureSpec
     */
    @Override
    protected synchronized void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int widthMode = MeasureSpec.getMode(widthMeasureSpec);
        int heightMode = MeasureSpec.getMode(heightMeasureSpec);
        int widthSize = MeasureSpec.getSize(widthMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int paintHeight = Math.max(mReachedHeight, mDefaultHeight);//比较两数，取最大值

        if(heightMode != MeasureSpec.EXACTLY){
            //如果用户没有精确指出宽高时，我们就要测量整个View所需要分配的高度了，测量自定义圆形View设置的上下内边距+圆形view的直径+圆形描边边框的高度
            int exceptHeight = getPaddingTop() + getPaddingBottom() + mRadius*2 + paintHeight;
            //然后再将测量后的值作为精确值传给父类，告诉他我需要这么大的空间，你给我分配吧
            heightMeasureSpec = MeasureSpec.makeMeasureSpec(exceptHeight, MeasureSpec.EXACTLY);
        }
        if(widthMode != MeasureSpec.EXACTLY){
            //这里在自定义属性中没有设置圆形边框的宽度，所以这里直接用高度代替
            int exceptWidth = getPaddingLeft() + getPaddingRight() + mRadius*2 + paintHeight;
            widthMeasureSpec = MeasureSpec.makeMeasureSpec(exceptWidth, MeasureSpec.EXACTLY);
        }

     //   Log.e("End","width"+width+"---height"+height);

    }

    @Override
    protected synchronized void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        /**
         * 这里canvas.save();和canvas.restore();是两个相互匹配出现的，作用是用来保存画布的状态和取出保存的状态的
         * 当我们对画布进行旋转，缩放，平移等操作的时候其实我们是想对特定的元素进行操作,但是当你用canvas的方法来进行这些操作的时候，其实是对整个画布进行了操作，
         * 那么之后在画布上的元素都会受到影响，所以我们在操作之前调用canvas.save()来保存画布当前的状态，当操作之后取出之前保存过的状态，
         * (比如：前面元素设置了平移或旋转的操作后，下一个元素在进行绘制之前执行了canvas.save();和canvas.restore()操作)这样后面的元素就不会受到(平移或旋转的)影响
         */
        canvas.save();
        //获得控件宽高
        final int paddingLeft=getPaddingLeft();
        final int paddingRight=getPaddingRight();
        final int paddingTop=getPaddingTop();
        final int paddingBottom=getPaddingBottom();
        //获取屏幕中心点
        width=(getWidth()-paddingLeft-paddingRight)/2;
        height=(getHeight()-paddingBottom-paddingTop)/2;
        if(mStatus == Status.start||mStatus == Status.starting||mStatus == Status.Stop){//开始
            //为了保证最外层的圆弧全部显示，我们通常会设置自定义view的padding属性，这样就有了内边距，所以画笔应该平移到内边距的位置，这样画笔才会刚好在最外层的圆弧上
            //画笔平移到指定paddingLeft， getPaddingTop()位置
            canvas.translate(getPaddingLeft(),getPaddingTop());
            mPaint.setStyle(Paint.Style.STROKE);


            //画进度条的一些设置
            mPaint.setColor(mReachedColor);
            mPaint.setStrokeWidth(mReachedHeight);

            //根据进度绘制圆弧
            float sweepAngle = getProgress() * 1.0f / getMax() * 360;
            //这里的RectF是确定一个矩形而圆则在矩形之中  圆形则是根据矩形的左上角和右下角的坐标xy轴相减若俩个角的xy轴相减的结果相等就是圆 否则是椭圆
            canvas.drawArc(new RectF(width-dwidth/2-2,height-dheight/2-2,width+mRadius+2,height+mRadius+2), -90, sweepAngle, false, mPaint);//drawArc：绘制圆弧
            //画默认圆(边框)的一些设置
            mPaint.setColor(mDefaultColor);
            mPaint.setStrokeWidth(mDefaultHeight);
            //drawCircles()是以前俩个参数为中心点绘制
            //canvas.drawCircle(getWidth()/2,getHeight()/2,mRadius,mPaint);


            //drawBitmap()是以第2个和第3个确定左上角坐标绘制
            canvas.drawBitmap(drawBitmap,width-dwidth/2,height-dheight/2,mPaint);
        }  else {//结束
            mPaint.setColor(0xFFd3d6da);
            //中心点画圆
            canvas.drawCircle(getWidth()/2,getHeight()/2,mRadius,mPaint);
            //
            canvas.drawBitmap(drawBitmapEnd,width-dwidth1/2,height-height2/2,mPaint);
        }
        canvas.restore();
    }

    /**
     * dp 2 px
     *
     * @param dpVal
     */
    protected int dp2px(int dpVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,
                dpVal, getResources().getDisplayMetrics());
    }

    /**
     * sp 2 px
     *
     * @param spVal
     * @return
     */
    protected int sp2px(int spVal)
    {
        return (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_SP,
                spVal, getResources().getDisplayMetrics());

    }


    //当前view显示的状态
    public enum Status{
        start,//未开始
        starting,//正在下载
        Stop,//停止下载
        End//结束
    }
    //设置Status的set/get方法
    public Status getStatus(){
        return mStatus;
    }

    public void setStatus(Status status){
        this.mStatus = status;
        invalidate();
    }



}
