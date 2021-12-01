package com.blink.blinkshopping.ui.allcategory

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blink.blinkshopping.models.allcategorymodel.Children
import com.blink.blinkshopping.models.allcategorymodel.ChildrenX

class SharedViewModel : ViewModel() {

    val toL2ChildData = MutableLiveData<ChildrenX>()
    fun setChildData(data: ChildrenX) {
        toL2ChildData.value = data
    }

    val childrenL1 = MutableLiveData<Children>()
    fun setChildDrenL1(data: Children) {
        childrenL1.value = data
    }

    val selectedName = MutableLiveData<String>()
    fun setName(data: String) {
        selectedName.value = data
    }

    val selectId = MutableLiveData<Int>()
    fun setId(id: Int) {
        selectId.value = id
    }


}