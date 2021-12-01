package com.blink.blinkshopping

import android.content.Context
import android.util.AttributeSet
import android.util.Log
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.RecyclerView.Recycler


class WrapContentLinearLayoutManager : LinearLayoutManager {
    constructor(context: Context?) : super(context) {}

    //... constructor
    override fun onLayoutChildren(recycler: Recycler, state: RecyclerView.State) {
        try {
            super.onLayoutChildren(recycler, state)
            isAutoMeasureEnabled = false
        } catch (e: IndexOutOfBoundsException) {
            Log.e("Error", "IndexOutOfBoundsException in RecyclerView happens")
        }
    }

    constructor(context: Context?, orientation: Int, reverseLayout: Boolean) : super(
        context,
        orientation,
        reverseLayout
    ) {
        isAutoMeasureEnabled = false
    }



    override fun supportsPredictiveItemAnimations(): Boolean {
        return false
    }
}