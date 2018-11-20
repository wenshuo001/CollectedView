package com.example.collectedview.JavaView;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.collectedview.R;
import com.example.collectedview.Utils.ViewUtil;

/**
 * popView
 * @author Krear 2018/08/10
 */
public class LableView extends ViewGroup implements View.OnClickListener {

    private int popSpacing;
    private int popPadding;
    private int textSize;
    private int textColor;
    private int lastTop, lastRight;
    private OnItemClickListener itemListener;
    private String[] items;

    /**
     * item事件对象
     */
    public interface OnItemClickListener
    {
        void onItemClicked(String hotKey);
    }

    /**
     * 设置item点击事件
     * @param itemListener
     */
    public void setOnItemClickListener(OnItemClickListener itemListener)
    {
        this.itemListener = itemListener;
    }

    public LableView(Context context) {
        this(context, null);
    }

    public LableView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public LableView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        initConfig();
    }

    /**
     * 初始化配置
     */
    private void initConfig()
    {
        popSpacing = (int) ViewUtil.dip2px(getContext(), 10f);
        popPadding = (int) ViewUtil.dip2px(getContext(), 8f);
        textColor = Color.parseColor("#A0ACAC");
        textSize = 14;//sp单位
    }

    /**
     * 设置item
     * @param strings
     */
    public void setItems(String[] strings)
    {
        items = strings;
        removeAllViews();
        if (strings==null||strings.length==0)
            return;

        for (String str : strings)
        {
            TextView textView = new TextView(getContext());
            PopLayoutParams params = new PopLayoutParams(PopLayoutParams.WRAP_CONTENT,
                    PopLayoutParams.WRAP_CONTENT);
            params.setMargins(popSpacing, popSpacing, popSpacing, popSpacing);
            textView.setLayoutParams(params);
            textView.setText(str);
            textView.setPadding(popPadding*2, popPadding, popPadding*2, popPadding);
            //textView.setBackground(popBackgroundDrawable);
            textView.setBackgroundResource(R.drawable.shape_pop_round);
            textView.setTextColor(textColor);
            textView.setTextSize(textSize);
            textView.setGravity(Gravity.CENTER);
            textView.setOnClickListener(this);
            addView(textView);
        }
    }


    public String[] getItems()
    {
        return items;
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b)
    {
        int childCount = getChildCount();
        for (int i=0; i<childCount; i++)//循环确定子View的位置（top，bottom，left，right）
        {
            View childView = getChildAt(i);
            PopLayoutParams params = (PopLayoutParams) childView.getLayoutParams();
            int childWidth = childView.getMeasuredWidth();
            int childHeight = childView.getMeasuredHeight();
            if (i>0)
            {
                int left = lastRight + params.leftMargin;
                int right = left + childWidth;
                int top = lastTop;

                if (right>getWidth())
                {
                    left = params.leftMargin;
                    right = left + childWidth;
                    top = lastTop + childHeight + params.topMargin;
                    lastTop = top;
                }
                int bottom = top + childHeight;
                lastRight = right;
                childView.layout(left, top, right, bottom);
            }
            else
            {
                int left = params.leftMargin;
                int right = childWidth + left;
                int top = params.topMargin;
                int bottom = childHeight + top;

                lastTop = top;
                lastRight = right;
                childView.layout(left, top, right, bottom);
            }

        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        int heightSize = MeasureSpec.getSize(heightMeasureSpec);
        int heightModel = MeasureSpec.getMode(heightMeasureSpec);
        boolean isChange = false;
        for (int i=0; i<getChildCount(); i++)
        {
            //测量子View的宽高（带margin）
            measureChildWithMargins(getChildAt(i), widthMeasureSpec,0, heightMeasureSpec, 0);
        }

        if (heightModel==MeasureSpec.AT_MOST)
        {
            isChange = true;
            int l_right = 0;
            int l_bottom = 0;
            for (int i=0; i<getChildCount(); i++)
            {
                View childView = getChildAt(i);
                PopLayoutParams params = (PopLayoutParams) childView.getLayoutParams();
                int childWidth = childView.getMeasuredWidth();
                int childHeight = childView.getMeasuredHeight();
                if (i>0)
                {
                    int left = l_right + params.leftMargin;
                    int right = left + childWidth;
                    if (right>getWidth())
                    {
                        left = params.leftMargin;
                        right = left + childWidth;
                        l_bottom = l_bottom + childHeight + params.topMargin;
                    }
                    l_right = right;
                }
                else
                {
                    int left = params.leftMargin;
                    int right = childWidth + left;
                    int top = params.topMargin;
                    int bottom = childHeight + top;

                    l_bottom = bottom;
                    l_right = right;
                }
            }
            heightSize = l_bottom + (2 * popSpacing);
            heightModel = MeasureSpec.EXACTLY;
        }

        if (isChange)
        {
            Log.i("Pop最终高度 - ",""+heightSize);
            //内容变化，重新测量
            measure(widthMeasureSpec, MeasureSpec.makeMeasureSpec(heightSize, heightModel));
        }
    }

    @Override
    public void onClick(View v) {
        TextView tv = (TextView) v;
        if (itemListener!=null)
            itemListener.onItemClicked(tv.getText().toString());
    }

    /**
     * 布局参数
     */
    public static class PopLayoutParams extends MarginLayoutParams
    {

        public PopLayoutParams(Context c, AttributeSet attrs) {
            super(c, attrs);
            Log.i("msg", "MyLayoutParams_create_attr");
        }

        public PopLayoutParams(int width, int height) {
            super(width, height);//动态创建时使用
            Log.i("msg", "MyLayoutParams_create_active");
        }

        public PopLayoutParams(MarginLayoutParams source) {
            super(source);
            Log.i("msg", "MyLayoutParams_create_margin");
        }

        public PopLayoutParams(LayoutParams source) {
            super(source);
            Log.i("msg", "MyLayoutParams_create_base");
        }
    }
}
