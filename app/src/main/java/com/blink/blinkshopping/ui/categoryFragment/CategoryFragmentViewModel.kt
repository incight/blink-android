package com.blink.blinkshopping.ui.categoryFragment

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.models.BaseCategorySliders
import com.blink.blinkshopping.models.BaseRecommended
import com.blink.blinkshopping.repository.*
import com.blink.blinkshopping.type.AggregationsInput
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class CategoryFragmentViewModel @Inject constructor(
    private val layoutRepository: LayoutDetailRepository
    , // for sidemenu category page
    private val categoryDetailsRepository: MenuCategoryListRepository
    ,
    private val descriptionListRepository: DescriptionListRepository
    ,
    private val slidersRepository: SlidersDetailRepository
    ,
    private val showByBrandsRepository: ShowByBrandsRepository
    ,
    private val shopByBrandsByAttributesRepository: ShopByBrandsByAttributesRepository
    ,
    private val newArrivalsRepository: NewArrivalsDetailRepository
    ,
    private val productsSkuRepository: ProductsSkuByListRepository
    ,
    private val dynamicBlockRepository: DynamicBlocksRepository
    ,
    private val dynamicBlockProductsRepository: DynamicsBlockProductsSkuListRepository
    ,
    private val categorySlidersRepository: CategorySlidersRepository
    ,
    private val categorySlidersProductsRepository: CategorySliderProductsSkuRepository
    ,
    private val categorySliderItemsProductsSkuRepository: CategorySliderItemsProductsSkuRepository
    ,
    private val categorySliderSubItemsProductsSkuRepository: CategorySliderSubItemsProductsSkuRepository
    ,
    private val bestSellersRepository: BestSellersRepository
) : ViewModel() {

    lateinit var lauyoutLiveData: LiveData<Resource<HomeLayoutsQuery.Data>>
    fun getHomeLayout(layout: String,device_type:String) {
        lauyoutLiveData = layoutRepository.getLayoutDetail(layout,device_type)
    }

    lateinit var categoryListLiveData: LiveData<Resource<GetMenuCategoryListQuery.Data>>

    fun getCategoryListInfo(categoryId: String): LiveData<Resource<GetMenuCategoryListQuery.Data>> {
        categoryListLiveData = categoryDetailsRepository.getCategoryListDetail(categoryId)
        return categoryListLiveData
    }

    lateinit var slidersLiveData: LiveData<Resource<SliderBlockQuery.Data>>

    fun getSlidersLayoutInfo(id: Int, page: List<String>?, category: List<Int>?) {
        slidersLiveData = slidersRepository.getSlidersDetail(id, page, category)
    }

    lateinit var newArrivalsLiveData: LiveData<Resource<NewArrivalsProductsQuery.Data>>
    lateinit var newArrivalsProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>

    fun getNewArrivalsInfo(categoryId: Int?) {
        newArrivalsLiveData = newArrivalsRepository.getNewArrivalsDetail(categoryId)
    }

    fun getProductsLayoutInfo(sku: List<String>?) {
        newArrivalsProductsLiveData = productsSkuRepository.getProductsSkuDetail(sku)
    }

    lateinit var dynamicBlocksLiveData: LiveData<Resource<GetDynamicBlockQuery.Data>>
    lateinit var dynamicBlocksProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>

    fun getDynamicBlocksLayoutInfo(id: Int, customeremail: String?) {
        dynamicBlocksLiveData = dynamicBlockRepository.getDynamicBlocksDetail(id, customeremail)
    }

    fun getDynamicProductsSkuLayoutInfo(sku: List<String>?) {
        dynamicBlocksProductsLiveData = dynamicBlockProductsRepository.getProductsSkuDetail(sku)
    }


    var categorySlidersLiveData: LiveData<Resource<CategorySlidersQuery.Data>> = MutableLiveData()
    lateinit var categorySliderProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    lateinit var categorySliderItemsProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    lateinit var categorySliderSubItemsProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>

    fun getcategorySlidersLayoutInfo(id: Int): LiveData<Resource<CategorySlidersQuery.Data>> {
        categorySlidersLiveData = categorySlidersRepository.getcategorySlidersDetail(id)
        return categorySlidersLiveData
    }

    fun getCategorySliderProductsSkuInfo1(sku: List<String>?): LiveData<Resource<ProductsSkuByListQuery.Data>> {
        categorySliderProductsLiveData = categorySlidersProductsRepository.getProductsSkuDetail(sku)
        return categorySliderProductsLiveData
    }

    fun getCategorySliderItemsProductsSkuInfo1(sku: List<String>?): LiveData<Resource<ProductsSkuByListQuery.Data>> {
        categorySliderItemsProductsLiveData =
            categorySliderItemsProductsSkuRepository.getProductsSkuDetail(sku)
        return categorySliderItemsProductsLiveData
    }

    fun getCategorySliderSubItemsProductsSkuInfo1(sku: List<String>?): LiveData<Resource<ProductsSkuByListQuery.Data>> {
        categorySliderSubItemsProductsLiveData =
            categorySliderSubItemsProductsSkuRepository.getProductsSkuDetail(sku)
        return categorySliderSubItemsProductsLiveData
    }


    lateinit var shopByBrandsQueryLiveData: LiveData<Resource<ShopByBrandsQuery.Data>>
    lateinit var shopByBrandsQueryListLiveData: LiveData<Resource<ShopByBrandsWithAttributesQuery.Data>>

    fun getShowByBrandsInfo(categoryId: String?) {
        shopByBrandsQueryLiveData = showByBrandsRepository.getShowByBrandsDetail(categoryId)
    }

    fun getShowByBrandsListByAttributesInfo(filters: List<AggregationsInput>?) {
        shopByBrandsQueryListLiveData =
            shopByBrandsByAttributesRepository.getShopByBrandsByAttributesDetail(filters)
    }


    lateinit var bestSellersLiveData: LiveData<Resource<BestsellersQuery.Data>>
    fun getGetBestSelletsInfo(categoryId: Int?) {
        bestSellersLiveData = bestSellersRepository.getBestSellersDetail(categoryId)
    }


    lateinit var descriptionQueryLiveData: LiveData<Resource<GetMenuCategoryListQuery.Data>>
    fun getGetDescriptionInfo(categoryId: String?) {
        descriptionQueryLiveData = descriptionListRepository.getCategoryListDetail(categoryId)
    }

    override fun onCleared() {
        layoutRepository.clear()

        categoryDetailsRepository.clear() //Sub_category

        descriptionListRepository.clear()

        slidersRepository.clear()

        showByBrandsRepository.clear()
        shopByBrandsByAttributesRepository.clear()

        newArrivalsRepository.clear()
        productsSkuRepository.clear()

        dynamicBlockRepository.clear()
        dynamicBlockProductsRepository.clear()

        categorySlidersRepository.clear()
        categorySlidersProductsRepository.clear()
        categorySliderItemsProductsSkuRepository.clear()
        categorySliderSubItemsProductsSkuRepository.clear()


        super.onCleared()
    }

}