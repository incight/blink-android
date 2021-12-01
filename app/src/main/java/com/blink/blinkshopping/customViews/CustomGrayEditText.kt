package com.blink.blinkshopping.customViews

import android.content.Context
import android.util.AttributeSet
import androidx.appcompat.widget.AppCompatEditText
import com.blink.blinkshopping.R
import com.startup.infobase.utils.Globals

class CustomGrayEditText : AppCompatEditText {

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
//        setTextColor(resources.getColor(R.color.txt_color))
//        setHintTextColor(resources.getColor(R.color.txt_color))
    }

}
