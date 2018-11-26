package com.example.collectedview.Utils;

import android.content.Context;

/**
 * Created 2018/11/16.
 */

public class ViewUtil {
    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static float dip2px(Context context, float dpValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return   (dpValue * scale + 0.5f);
    }
}
