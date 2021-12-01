package com.blink.blinkshopping.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.startup.infobase.utils.Globals

/**
 * Created by Praveen on 23/12/2020.
 */
class CustomMediumTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        typeface = Globals.getHelveticaMedium(context)
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        typeface = Globals.getHelveticaMedium(context)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        typeface = Globals.getHelveticaMedium(context)
        init()
    }

    fun init() {
    }
}