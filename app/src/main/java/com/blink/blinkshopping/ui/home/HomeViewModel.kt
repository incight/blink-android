package com.blink.blinkshopping.ui.home

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.models.BaseCategorySliders
import com.blink.blinkshopping.models.BaseRecommended
import com.blink.blinkshopping.models.InspiredHistoryproduct
import com.blink.blinkshopping.repository.*
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.lang.reflect.Type
import javax.inject.Inject

class HomeViewModel @Inject constructor(
    private val sideMenuLevelsRepository: SideMenuLevelsRepository
    ,
    private val megaMenuRepository: MegaMenuRepository
    ,
    private val layoutRepository: LayoutDetailRepository
    ,
    private val slidersRepository: SlidersDetailRepository
    ,
    private val adsBlockRepository: AdsBlockDetailRepository
    ,
    private val dealsOfTheDayRepository: DealsOfTheDayDetailRepository
    ,
    private val newArrivalsRepository: NewArrivalsDetailRepository,

    private val inspiredHistoryRepository: InspiredHistoryRepository,

    private val bestSellersRepository: BestSellersRepository,

    private val bestSellerProductsSkuByListRepository: InspireHistoryProductsSkuByListRepository,
    private val recommendedProductsSkuByListRepository: RecommendedProductsSkuByListRepository,

    private val dynamicBlockRepository: DynamicBlocksRepository
    ,
    private val bannersRepository: BannersDetailRepository
    ,
    private val productsSkuRepository: ProductsSkuByListRepository
    ,
    private val dailyProductsSkuRepository: DailyDealsProductsSkuByListRepository
    ,
    private val dynamicBlockProductsRepository: DynamicsBlockProductsSkuListRepository
    ,
    private val categorySlidersRepository: CategorySlidersRepository
    ,
    private val categorySlidersProductsRepository: CategorySliderProductsSkuRepository
    ,
    private val categorySliderItemsProductsSkuRepository: CategorySliderItemsProductsSkuRepository
    ,
    private val categorySliderSubItemsProductsSkuRepository: CategorySliderSubItemsProductsSkuRepository,

    private val offerPlateRepository: OfferPlatesRepository,

    private val adsImagesRepository: AdsImagesRepository,
    private val categoryLOneRepository: CategoryLOneRepository,

    private val allCategoryRepository: AllCategoryListRepository,

    private val shopByBrandsRepository: ShopByBrandsRepository,
    private val imageBrandListRepository: ImageListRepository,

    private val browsinghistorygetrepository: BrowsingHistoryGetRepository,
    private val browseHistoryProductsSkuByListRepository: BrowseHistoryProductsSkuByListRepository,
    private val browsingHistoryDeleteRepository: BrowsingHistoryDeleteRepository,
    private val browsinghistoryaddrepository: BrowsingHistoryAddRepository

) : ViewModel() {

    lateinit var browsingHistoryAddLiveData: LiveData<Resource<BrowsingHistoryAddMutation.Data>>
    fun addBrosingHistory(id: List<Int>?) {
        browsingHistoryAddLiveData = browsinghistoryaddrepository.getBrowsingHistoryAdd(id)
    }


    val loadingVisibility: MutableLiveData<Int> = MutableLiveData()


    lateinit var sideMenuMainLiveData: LiveData<Resource<GetMenuCategoryListQuery.Data>>

    lateinit var megaMenuLiveData: LiveData<Resource<GetMegaMenuQuery.Data>>

    lateinit var lauyoutLiveData: LiveData<Resource<HomeLayoutsQuery.Data>>

    lateinit var slidersLiveData: LiveData<Resource<SliderBlockQuery.Data>>

    lateinit var adsBlockLiveData: LiveData<Resource<AdsBlocksQuery.Data>>
    lateinit var bannersLiveData: LiveData<Resource<GetBannersListQuery.Data>>

    lateinit var newArrivalsLiveData: LiveData<Resource<NewArrivalsProductsQuery.Data>>
    lateinit var newArrivalsProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>


    lateinit var dealsOfTheDayLiveData: LiveData<Resource<DailyDealsProductsQuery.Data>>
    lateinit var dealsOfTheDayProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>

    lateinit var dynamicBlocksLiveData: LiveData<Resource<GetDynamicBlockQuery.Data>>
    lateinit var dynamicBlocksProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>

    lateinit var categorySlidersLiveData: LiveData<Resource<CategorySlidersQuery.Data>>
    lateinit var categorySliderProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    lateinit var categorySliderItemsProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    lateinit var categorySliderSubItemsProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    //var baseCategorySliders: MutableList<BaseCategorySliders> = mutableListOf()

    lateinit var categorShopByBrands: LiveData<Resource<ShopByBrandImageQuery.Data>>
    fun getShopByImageBrands(id: String) {
        categorShopByBrands = shopByBrandsRepository.getProductListBrandList(id)
    }

    lateinit var brandList: MutableLiveData<Resource<GetBrandImageListQuery.Data>>
    fun getBrandList(atribute_code: String, ids: List<String>) {
        brandList = imageBrandListRepository.getImageProduct(atribute_code, ids)
    }

    lateinit var allCategoryLiveData: LiveData<Resource<LevelcategoryListQuery.Data>>
    fun getAllCategoryListData(id: String) {
        allCategoryLiveData = allCategoryRepository.getAllCategoryList(id)
    }


    lateinit var adsimagesLiveData: LiveData<Resource<GetAdsimagesQuery.Data>>
    fun getAdsImagesLayout(id: List<Int>?) {
        adsimagesLiveData = adsImagesRepository.getAdsImagesDetail(id)
    }


    private fun getSideMenuLevelsLayout(id: String) {
        sideMenuMainLiveData = sideMenuLevelsRepository.getSideMenuLayoutDetail(id)
    }

    fun getMegaMenuLayout() {
        megaMenuLiveData = megaMenuRepository.getMegaMenuLayoutDetail()
    }

    fun getHomeLayout(layout: String, device_type: String) {
        lauyoutLiveData = layoutRepository.getLayoutDetail(layout, device_type)
    }

    fun getSlidersLayoutInfo(id: Int, page: List<String>?, category: List<Int>?) {
        slidersLiveData = slidersRepository.getSlidersDetail(id, page, category)
    }

    fun getAdsBlockLayoutInfo() {
        adsBlockLiveData = adsBlockRepository.getAdsBlockDetail()
    }

    fun getNewArrivalsInfo(categoryId: Int?) {
        newArrivalsLiveData = newArrivalsRepository.getNewArrivalsDetail(categoryId)
    }

    fun getProductsLayoutInfo(sku: List<String>?) {
        newArrivalsProductsLiveData = productsSkuRepository.getProductsSkuDetail(sku)
    }

    lateinit var recommendedLiveData: LiveData<Resource<NewArrivalsProductsQuery.Data>>
    lateinit var recommendedLiveDataProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>

    fun getInspiredHistoryInfo(categoryId: Int?) {
        recommendedLiveData = inspiredHistoryRepository.getInspiredHistoryDetail(categoryId)
    }

    fun getInspiredHistoryProductsLayoutInfo(sku: List<String>?) {
        recommendedLiveDataProductsLiveData =
            recommendedProductsSkuByListRepository.getProductsSkuDetail(sku)
    }


    lateinit var bestsellersLiveData: LiveData<Resource<BestsellersQuery.Data>>
    lateinit var bestsellersLiveDataProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>

    fun getBestsellersInfo(categoryId: Int?) {
        bestsellersLiveData = bestSellersRepository.getBestSellersDetail(categoryId)
    }

    fun getBestsellersProductsLayoutInfo(sku: List<String>?) {
        bestsellersLiveDataProductsLiveData =
            bestSellerProductsSkuByListRepository.getProductsSkuDetail(sku)
    }

    fun getGetBestSellers(categoryId: Int?) {
        bestsellersLiveData = bestSellersRepository.getBestSellersDetail(categoryId)
    }

    lateinit var offerPlatesLiveData: LiveData<Resource<OfferPlatesQuery.Data>>
    fun getOfferPlatesDetail() {
        offerPlatesLiveData = offerPlateRepository.getOfferPlatesDetail()
    }


    fun getDealsOfTheDayLayoutInfo() {
        dealsOfTheDayLiveData = dealsOfTheDayRepository.getDealsOfTheDayDetail()
    }

    fun getDailyProductsLayoutInfo(sku: List<String>?) {
        dealsOfTheDayProductsLiveData = dailyProductsSkuRepository.getProductsSkuDetail(sku)
    }

    fun getDynamicBlocksLayoutInfo(id: Int, customeremail: String?) {
        dynamicBlocksLiveData = dynamicBlockRepository.getDynamicBlocksDetail(id, customeremail)
    }

    fun getDynamicProductsSkuLayoutInfo(sku: List<String>?) {
        dynamicBlocksProductsLiveData = dynamicBlockProductsRepository.getProductsSkuDetail(sku)
    }

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


    //banner
    fun getBannersLayoutInfo(id: Int) {
        bannersLiveData = bannersRepository.getBannersDetail(id)
    }

    lateinit var categoryLOneLiveData: LiveData<Resource<CategoryLOneDetailsQuery.Data>>

    fun getLoneCategoryData(id: String) {
        categoryLOneLiveData = categoryLOneRepository.getCategoryLOneData(id)
    }

    //If logged in
    lateinit var browsingHistoryGetLiveData: LiveData<Resource<BrowsingHistoryGetQuery.Data>>
    fun getBrosingHistory() {
        browsingHistoryGetLiveData = browsinghistorygetrepository.getBrowsingHistoryGet()
    }

    lateinit var brosingHistoryLiveDataProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    fun getBrosingHistoryProductsListInfo(sku: List<String>?) {
        brosingHistoryLiveDataProductsLiveData =
            browseHistoryProductsSkuByListRepository.getProductsSkuDetail(sku)
    }

    lateinit var browsingHistoryDeleteLiveData: LiveData<Resource<BrowsingHistoryDeleteMutation.Data>>
    fun deleteBrosingHistory(id: List<Int>?) {
        browsingHistoryDeleteLiveData = browsingHistoryDeleteRepository.getBrowsingHistoryDelete(id)
    }


    override fun onCleared() {
        browsinghistorygetrepository.clear()
        browseHistoryProductsSkuByListRepository.clear()
        browsinghistoryaddrepository.clear()

        sideMenuLevelsRepository.clear()

        megaMenuRepository.clear()

        layoutRepository.clear()

        slidersRepository.clear()

        adsBlockRepository.clear()

        newArrivalsRepository.clear()

        inspiredHistoryRepository.clear()

        bestSellerProductsSkuByListRepository.clear()
        recommendedProductsSkuByListRepository.clear()
        dealsOfTheDayRepository.clear()

        dynamicBlockRepository.clear()

        bannersRepository.clear()

        productsSkuRepository.clear()

        dailyProductsSkuRepository.clear()

        categorySlidersRepository.clear()

        categorySlidersProductsRepository.clear()

        categorySliderItemsProductsSkuRepository.clear()

        categorySliderSubItemsProductsSkuRepository.clear()

        bestSellersRepository.clear()

        offerPlateRepository.clear()

        categoryLOneRepository.clear()

        allCategoryRepository.clear()

        shopByBrandsRepository.clear()

        imageBrandListRepository.clear()

        super.onCleared()
    }

}