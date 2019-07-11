package com.tool;

import android.content.Context;

public class DpiTools
{

    public static int dip2px(Context context, float dipValue)
    {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public static int px2dip(Context context, float pxValue)
    {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / m + 0.5f);
    }
}
