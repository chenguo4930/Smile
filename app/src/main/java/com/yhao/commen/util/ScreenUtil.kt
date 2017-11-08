package com.yhao.commen.util

import android.content.Context
import android.util.DisplayMetrics
import android.util.TypedValue
import android.view.WindowManager




object ScreenUtil {

    private var height = 0
    private var width = 0


    internal fun h(context: Context): Int {
        if (height == 0) {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            height = outMetrics.heightPixels
        }
        return height
    }

    internal fun w(context: Context): Int {
        if (width == 0) {
            val wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val outMetrics = DisplayMetrics()
            wm.defaultDisplay.getMetrics(outMetrics)
            width = outMetrics.widthPixels
        }
        return width
    }

    internal fun dp2px(context: Context, dpVal: Float): Int {
        return TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, dpVal, context.resources.displayMetrics).toInt()
    }

}
