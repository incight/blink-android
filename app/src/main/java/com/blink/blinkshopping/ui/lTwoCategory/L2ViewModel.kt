package com.blink.blinkshopping.ui.lTwoCategory

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.repository.*
import javax.inject.Inject

class L2ViewModel @Inject constructor(

    private val layoutRepository: LayoutDetailRepository,

    private val slidersRepository: SlidersDetailRepository,

    private val categoryLOneRepository: CategoryLOneRepository,

    private val dealsOfTheDayRepository: DealsOfTheDayDetailRepository,
    private val dailyProductsSkuRepository: DailyDealsProductsSkuByListRepository,

    private val shopByBrandsRepository: ShopByBrandsRepository,
    private val imageBrandListRepository: ImageListRepository,

    private val recommendRepository: RecommendedRepository,
    private val recommendedProductsSkuRepository: RecommendedProductsSkuByListRepository,

    private val newArrivalsRepository: NewArrivalsDetailRepository,
    private val productsSkuRepository: ProductsSkuByListRepository,

    private val dynamicBlockRepository: DynamicBlocksRepository,
    private val dynamicBlockProductsRepository: DynamicsBlockProductsSkuListRepository,

    private val bestSellersRepository: BestSellersRepository,
    private val bestSellerProductsSkuByListRepository: InspireHistoryProductsSkuByListRepository,


    private val categorySlidersRepository: CategorySlidersRepository,
    private val categorySlidersProductsRepository: CategorySliderProductsSkuRepository,
    private val categorySliderItemsProductsSkuRepository: CategorySliderItemsProductsSkuRepository,
    private val categorySliderSubItemsProductsSkuRepository: CategorySliderSubItemsProductsSkuRepository,

    private val offerPlateRepository: OfferPlatesRepository


) : ViewModel() {

    //Home
    lateinit var lauyoutLiveData: LiveData<Resource<HomeLayoutsQuery.Data>>
    fun getHomeLayout(layout: String, device_type: String) {
        lauyoutLiveData = layoutRepository.getLayoutDetail(layout, device_type)
    }

    //Sliders
    lateinit var slidersLiveData: LiveData<Resource<SliderBlockQuery.Data>>
    fun getSlidersLayoutInfo(id: Int, page: List<String>?, category: List<Int>?) {
        slidersLiveData = slidersRepository.getSlidersDetail(id, page, category)
    }

    //sub Category Grid
    lateinit var categoryLOneLiveData: LiveData<Resource<CategoryLOneDetailsQuery.Data>>
    fun getLoneCategoryData(id: String) {
        categoryLOneLiveData = categoryLOneRepository.getCategoryLOneData(id)
    }

    //dealsOfTheDay
    lateinit var dealsOfTheDayLiveData: LiveData<Resource<DailyDealsProductsQuery.Data>>
    lateinit var dealsOfTheDayProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    fun getDealsOfTheDayLayoutInfo() {
        dealsOfTheDayLiveData = dealsOfTheDayRepository.getDealsOfTheDayDetail()
    }
    fun getDailyProductsLayoutInfo(sku: List<String>?) {
        dealsOfTheDayProductsLiveData = dailyProductsSkuRepository.getProductsSkuDetail(sku)
    }


    //ShopByBrands
    lateinit var categorShopByBrands: LiveData<Resource<ShopByBrandImageQuery.Data>>
    lateinit var brandList: MutableLiveData<Resource<GetBrandImageListQuery.Data>>
    fun getShopByImageBrands(id: String) {
        categorShopByBrands = shopByBrandsRepository.getProductListBrandList(id)
    }

    fun getBrandList(atribute_code: String, ids: List<String>) {
        brandList = imageBrandListRepository.getImageProduct(atribute_code, ids)
    }

    //Recommended
    lateinit var recommendedLiveData: LiveData<Resource<NewArrivalsProductsQuery.Data>>
    lateinit var recommendedLiveDataProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    fun getRecommendedInfo(categoryId: Int?) {
        recommendedLiveData = recommendRepository.getNewArrivalsDetail(categoryId)
    }

    fun getRecommendedProductsLayoutInfo(sku: List<String>?) {
        recommendedLiveDataProductsLiveData =
            recommendedProductsSkuRepository.getProductsSkuDetail(sku)
    }

    //NewArrivals
    lateinit var newArrivalsLiveData: LiveData<Resource<NewArrivalsProductsQuery.Data>>
    lateinit var newArrivalsProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    fun getNewArrivalsInfo(categoryId: Int?) {
        newArrivalsLiveData = newArrivalsRepository.getNewArrivalsDetail(categoryId)
    }

    fun getProductsLayoutInfo(sku: List<String>?) {
        newArrivalsProductsLiveData = productsSkuRepository.getProductsSkuDetail(sku)
    }

    //Dynamic or Promotions
    lateinit var dynamicBlocksLiveData: LiveData<Resource<GetDynamicBlockQuery.Data>>
    lateinit var dynamicBlocksProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    fun getDynamicBlocksLayoutInfo(id: Int, customeremail: String?) {
        dynamicBlocksLiveData = dynamicBlockRepository.getDynamicBlocksDetail(id, customeremail)
    }

    fun getDynamicProductsSkuLayoutInfo(sku: List<String>?) {
        dynamicBlocksProductsLiveData = dynamicBlockProductsRepository.getProductsSkuDetail(sku)
    }

    //BestSeller
    lateinit var bestsellersLiveData: LiveData<Resource<BestsellersQuery.Data>>
    lateinit var bestsellersLiveDataProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    fun getGetBestSellers(categoryId: Int?) {
        bestsellersLiveData = bestSellersRepository.getBestSellersDetail(categoryId)
    }

    fun getBestsellersProductsLayoutInfo(sku: List<String>?) {
        bestsellersLiveDataProductsLiveData =
            bestSellerProductsSkuByListRepository.getProductsSkuDetail(sku)
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


    lateinit var offerPlatesLiveData: LiveData<Resource<OfferPlatesQuery.Data>>
    fun getOfferPlatesDetail() {
        offerPlatesLiveData = offerPlateRepository.getOfferPlatesDetail()
    }



    override fun onCleared() {

        layoutRepository.clear()

        slidersRepository.clear()

        shopByBrandsRepository.clear()
        imageBrandListRepository.clear()

        dealsOfTheDayRepository.clear()
        dailyProductsSkuRepository.clear()

        categoryLOneRepository.clear()

        newArrivalsRepository.clear()
        productsSkuRepository.clear()

        recommendRepository.clear()
        recommendedProductsSkuRepository.clear()

        dynamicBlockRepository.clear()
        dynamicBlockProductsRepository.clear()


        bestSellersRepository.clear()
        bestSellerProductsSkuByListRepository.clear()

        categorySlidersRepository.clear()
        categorySlidersProductsRepository.clear()
        categorySliderItemsProductsSkuRepository.clear()
        categorySliderSubItemsProductsSkuRepository.clear()


        offerPlateRepository.clear()

        super.onCleared()
    }

}