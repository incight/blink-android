package com.blink.blinkshopping.ui.main

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.repository.*
import javax.inject.Inject

class MainViewModel @Inject constructor(
    private val sideMenuLevelsRepository: SideMenuLevelsRepository
    ,
    private val layoutRepository: LayoutDetailRepository

) : ViewModel() {

    lateinit var sideMenuMainLiveData: LiveData<Resource<GetMenuCategoryListQuery.Data>>
    lateinit var lauyoutLiveData: LiveData<Resource<HomeLayoutsQuery.Data>>

    init {
        getSideMenuLevelsLayout("2")
        getHomeLayout()
    }

    private fun getSideMenuLevelsLayout(id: String) {
        sideMenuMainLiveData = sideMenuLevelsRepository.getSideMenuLayoutDetail(id)
    }

    private fun getHomeLayout() {
        lauyoutLiveData = layoutRepository.getLayoutDetail("home","mobile")
    }

    override fun onCleared() {
        sideMenuLevelsRepository.clear()
        layoutRepository.clear()
        super.onCleared()
    }

}