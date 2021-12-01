package com.blink.blinkshopping

import com.blink.blinkshopping.models.allcategorymodel.ChildrenXX
import java.text.FieldPosition
import java.util.HashMap


interface L2ProductClickHandler {
    fun onL2ProItemClick(L2ProId: String, l3ProductList: MutableList<ChildrenXX>?, from: String,itemListwise: HashMap<String, String>,position: Int)
}