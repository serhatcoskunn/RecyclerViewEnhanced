package com.ahe.recyclerviewenhanced

import android.view.MotionEvent

interface OnActivityTouchListener {
    fun getTouchCoordinates(ev: MotionEvent)
}
