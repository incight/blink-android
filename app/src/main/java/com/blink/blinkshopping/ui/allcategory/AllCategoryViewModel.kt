package com.blink.blinkshopping.ui.allcategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.models.allcategorymodel.AllCategoryData
import com.blink.blinkshopping.models.allcategorymodel.ChildrenX
import com.blink.blinkshopping.repository.*
import com.blink.blinkshopping.type.AggregationsInput
import javax.inject.Inject


class AllCategoryViewModel @Inject constructor(
    private val allCategoryRepository: AllCategoryListRepository,

    private val layoutRepository: LayoutDetailRepository,

    private val slidersRepository: SlidersDetailRepository,

    private val shopByBrandsRepository: ShopByBrandsRepository,
    private val shopBrandsListRepository: ImageListRepository,
    private val shopByBrandsByAttributesRepository: ShopByBrandsByAttributesRepository


) : ViewModel() {

    lateinit var allCategoryLiveData: LiveData<Resource<LevelcategoryListQuery.Data>>


    lateinit var lauyoutLiveData: LiveData<Resource<HomeLayoutsQuery.Data>>
    fun getHomeLayout(layout: String, device_type: String) {
        lauyoutLiveData = layoutRepository.getLayoutDetail(layout, device_type)
    }

    lateinit var slidersLiveData: LiveData<Resource<SliderBlockQuery.Data>>
    fun getSlidersLayoutInfo(id: Int, page: List<String>?, category: List<Int>?) {
        slidersLiveData = slidersRepository.getSlidersDetail(id, page, category)
    }

    lateinit var categorShopByBrands:LiveData<Resource<ShopByBrandImageQuery.Data>>
    fun getShopByImageBrands(id:String){
        categorShopByBrands =shopByBrandsRepository.getProductListBrandList(id)
    }
    lateinit var shopBrandsImageList:LiveData<Resource<GetBrandImageListQuery.Data>>
    fun getShopByImageBrandsList(atribute_code:String,ids: List<String>){
        shopBrandsImageList =shopBrandsListRepository.getImageProduct(atribute_code,ids)
    }
    lateinit var shopByBrandsQueryListLiveData: LiveData<Resource<ShopByBrandsWithAttributesQuery.Data>>
    fun getShowByBrandsListByAttributesInfo(filters: List<AggregationsInput>?) {
        shopByBrandsQueryListLiveData = shopByBrandsByAttributesRepository.getShopByBrandsByAttributesDetail(filters)
    }


    fun getAllCategoryListData(id: String) {
        allCategoryLiveData = allCategoryRepository.getAllCategoryList(id)
    }


    val categoryUiData = MutableLiveData<MutableList<ChildrenX>>()
    fun select(data: MutableList<ChildrenX>) {
        categoryUiData.value = data
    }

//    val toL2ChildPosition = MutableLiveData<Int>()
//    fun settoL2ChildPosition(id: Int) {
//        toL2ChildPosition.value = id
//    }

    val L2ProPosition = MutableLiveData<Int>()
    fun setL2ProPosition(id: Int) {
        L2ProPosition.value = id
    }


    val L2ProId = MutableLiveData<Int>()
    fun setL2ProId(id: Int) {
        L2ProId.value = id
    }


    override fun onCleared() {

        allCategoryRepository.clear()

        layoutRepository.clear()

        slidersRepository.clear()

        shopByBrandsRepository.clear()
        shopBrandsListRepository.clear()
        shopByBrandsByAttributesRepository.clear()

        super.onCleared()
    }
}


