package com.task.carmoving.utils

import android.graphics.Point
import androidx.appcompat.app.AppCompatActivity

fun getMaxX(context: AppCompatActivity): Int {
    val display = context.windowManager.defaultDisplay
    val displaySize = Point()
    display.getSize(displaySize)
    return displaySize.x
}

 fun getMaxY(context: AppCompatActivity): Int {
    val display = context.windowManager.defaultDisplay
    val displaySize = Point()
    display.getSize(displaySize)
    return displaySize.y
}