package com.blink.blinkshopping.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatTextView
import com.startup.infobase.utils.Globals

/**
 * Created by Praveen on 23/12/2020.
 */
class CustomBoldTextView : AppCompatTextView {
    constructor(context: Context) : super(context) {
        typeface = Globals.getHelveticaBold(context)
        init()
    }

    constructor(context: Context, attrs: AttributeSet) : super(context, attrs) {
        typeface = Globals.getHelveticaBold(context)
        init()
    }

    constructor(context: Context, attrs: AttributeSet, defStyleAttr: Int) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        typeface = Globals.getHelveticaBold(context)
        init()
    }

    fun init() {
    }
}