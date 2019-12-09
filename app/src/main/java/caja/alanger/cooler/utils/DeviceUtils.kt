package caja.alanger.cooler.utils

import android.app.Activity
import android.content.res.Resources
import android.graphics.Point
import android.os.Build
import android.util.DisplayMetrics
import android.view.Display
import java.util.*


fun getDisplayResolution(activity: Activity): String {
    var x = 0.0
    var y = 0.0
    val mWidthPixels: Int
    val mHeightPixels: Int
    try {
        val windowManager = activity.windowManager
        val display = windowManager.defaultDisplay
        val displayMetrics = DisplayMetrics()
        display.getMetrics(displayMetrics)
        val realSize = Point()
        Display::class.java.getMethod("getRealSize", Point::class.java)
            .invoke(display, realSize)
        mWidthPixels = realSize.x
        mHeightPixels = realSize.y
        val dm = DisplayMetrics()
        activity.windowManager.defaultDisplay.getMetrics(dm)
        x = Math.pow((mWidthPixels / dm.xdpi).toDouble(), 2.0)
        y = Math.pow((mHeightPixels / dm.ydpi).toDouble(), 2.0)

    } catch (ex: Exception) {
        ex.printStackTrace()
    }

    return String.format(Locale.US, "%.2f", Math.sqrt(x + y))
}

fun getScreenWidth(): Int {
    return Resources.getSystem().getDisplayMetrics().widthPixels
}

fun getScreenHeight(): Int {
    return Resources.getSystem().getDisplayMetrics().heightPixels
}


fun getDeviceName(): String {
    val manufacturer = Build.MANUFACTURER
    val model = Build.MODEL
    return if (model.toLowerCase().startsWith(manufacturer.toLowerCase())) {
        capitalize(model)
    } else {
        capitalize(manufacturer) + " " + model
    }
}
