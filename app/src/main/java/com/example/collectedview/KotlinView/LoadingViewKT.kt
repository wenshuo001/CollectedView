package com.example.collectedview.KotlinView

import android.animation.ValueAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.util.Half.toFloat
import android.view.View
import android.R.string.cancel
import com.example.collectedview.Utils.ViewUtil


/**
 * Created by liupeng on 2018/11/16.
 */
class LoadingViewKT :View {

    private var dotRadius: Float =0.0f
    private var offset1: Float =0.0f
    private var offset2: Float =0.0f
    private var offset3: Float =0.0f
    lateinit var dotPaint: Paint
    lateinit var dotAnimator1: ValueAnimator
    lateinit var dotAnimator2: ValueAnimator
    lateinit var dotAnimator3: ValueAnimator
    private  var dotSpacing: Int = 1

    constructor(context: Context?) : this(context, null)

    constructor(context: Context?, attrs: AttributeSet?) : this(context, attrs, 0)

    constructor(context: Context?, attrs: AttributeSet?, defStyleAttr: Int) : super(context, attrs, defStyleAttr) {
        dotRadius= ViewUtil.dip2px(getContext(), 6F)
        dotSpacing = ViewUtil.dip2px(getContext(),8F).toInt()

        dotPaint =Paint()
        dotPaint.style = Paint.Style.FILL
        dotPaint.color = Color.parseColor("#FFD102")

        dotAnimator1 = ValueAnimator.ofFloat(0F,dotRadius)
        dotAnimator1.duration = 600
        dotAnimator1.repeatMode =ValueAnimator.REVERSE
        dotAnimator1.repeatCount = ValueAnimator.INFINITE

        dotAnimator1.addUpdateListener {
            offset1 = it.getAnimatedValue() as Float
            invalidate()
        }

        dotAnimator2 = ValueAnimator.ofFloat(0F,dotRadius)
        dotAnimator2.duration = 600
        dotAnimator2.repeatMode =ValueAnimator.REVERSE
        dotAnimator2.repeatCount = ValueAnimator.INFINITE
        dotAnimator2.startDelay = 200
        dotAnimator2.addUpdateListener {
            offset2 = it.getAnimatedValue() as Float
            invalidate()
        }

        dotAnimator3 = ValueAnimator.ofFloat(0F,dotRadius)
        dotAnimator3.duration = 600
        dotAnimator3.repeatMode =ValueAnimator.REVERSE
        dotAnimator3.repeatCount = ValueAnimator.INFINITE
        dotAnimator3.startDelay = 400
        dotAnimator3.addUpdateListener {
            offset3 = it.getAnimatedValue() as Float
            invalidate()
        }
    }

    override fun onDraw(canvas: Canvas?) {
        super.onDraw(canvas)
        var cx : Int = width/2
        var cy : Int = height/2

        dotPaint.alpha= (255-(255*(offset2/dotRadius))).toInt()
        canvas?.drawCircle(cx.toFloat(),cy.toFloat(),dotRadius-offset2,dotPaint)

        dotPaint.setAlpha( (255-(255*((offset1)/dotRadius))).toInt())
        canvas?.drawCircle(cx - dotRadius*2 - dotSpacing, cy.toFloat(), dotRadius-offset1, dotPaint)

        dotPaint.setAlpha( ((255-(255*(offset3/dotRadius))).toInt()))
        canvas?.drawCircle(cx + dotRadius*2 + dotSpacing, cy.toFloat(), dotRadius-offset3, dotPaint)
    }

    fun startLoading(){
        if(!dotAnimator1.isRunning){
            dotAnimator1.start()
            dotAnimator2.start()
            dotAnimator3.start()
        }
    }

    fun stopLoading() {
        if (dotAnimator1.isRunning) {
            dotAnimator1.cancel()
            dotAnimator2.cancel()
            dotAnimator3.cancel()
        }
    }

}