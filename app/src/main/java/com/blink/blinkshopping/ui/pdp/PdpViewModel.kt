package com.blink.blinkshopping.ui.pdp

import androidx.lifecycle.LiveData
import androidx.lifecycle.ViewModel
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.repository.*
import javax.inject.Inject

class PdpViewModel @Inject constructor(
    private val pdpRepository: SingleProductSkuRepository,

    private val offerPlateRepository: OfferPlatesRepository,

    private val deliveryOptionsRepository: DeliveryOptionsRepository,

    private val frequentBroughtRepository: FrequentBroughtRepository,
    private val productsSkuRepository: ProductsSkuByListRepository,

    private val storePickupRepository: StorePickupRepository,

    private val installmentsRepository: InstallmentsRepository,

    private val compareSimilarRepository: CompareSimilarRepository,


    private val newArrivalsRepository: NewArrivalsDetailRepository,
    private val newArrivalsProductsSkuByListRepository:  NewArrivalsProductsSkuByListRepository,
    private val recommendRepository: RecommendedRepository,
    private val recommendedProductsSkuByListRepository:  RecommendedProductsSkuByListRepository,

    private val browsinghistoryaddrepository: BrowsingHistoryAddRepository
) : ViewModel() {

    lateinit var browsingHistoryAddLiveData: LiveData<Resource<BrowsingHistoryAddMutation.Data>>
    fun addBrosingHistory(id: List<Int>?) {
        browsingHistoryAddLiveData = browsinghistoryaddrepository.getBrowsingHistoryAdd(id)
    }


    lateinit var pdpsku: LiveData<Resource<ProductsForSKUQuery.Data>>
    fun getSingleProductSkuDetail(layout: String) {
        pdpsku = pdpRepository.getSingleProductSkuDetail(layout)
    }


    lateinit var offerPlatesLiveData: LiveData<Resource<OfferPlatesQuery.Data>>
    fun getOfferPlatesDetail() {
        offerPlatesLiveData = offerPlateRepository.getOfferPlatesDetail()
    }


    lateinit var deliverOptionsLiveData: LiveData<Resource<ProductDeliveryOptionsQuery.Data>>
    fun getDeliveryOptionsDetail(sku: String, qty: Int) {
        deliverOptionsLiveData = deliveryOptionsRepository.getDeliveryOptionsDetail(sku, qty)
    }

    lateinit var storePickupQuery: LiveData<Resource<StorePickupQuery.Data>>
    fun getStorePickupDetail(sku: String, qty: Int) {
        storePickupQuery = storePickupRepository.getStorePickupDetail(sku, qty)
    }


    lateinit var installmentsLiveData: LiveData<Resource<GetInstallmentsQuery.Data>>
    fun getInstallmentsDetail(sku: String, amount: String) {
        installmentsLiveData = installmentsRepository.getInstalmentsDetail(sku, amount)
    }

    lateinit var compareWithSimilarProduct: LiveData<Resource<CompareWithSimilarProductsQuery.Data>>
    fun getCompareWithSimilarDetail(product: Int) {
        compareWithSimilarProduct = compareSimilarRepository.getCompareWithSimilarDetail(product)
    }


    lateinit var frequentlyBoughtTogetherLiveData: LiveData<Resource<FrequentlyBoughtTogetherQuery.Data>>
    fun getFrequentlyBoughtDetail(sku: List<Int>?) {
        frequentlyBoughtTogetherLiveData = frequentBroughtRepository.getFrequentBroughtDetail(sku)
    }

    lateinit var productsSkuByListLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    fun getFrequentProductList(sku: List<String>?) {
        productsSkuByListLiveData = productsSkuRepository.getProductsSkuDetail(sku)
    }

    lateinit var newArrivalsLiveData: LiveData<Resource<NewArrivalsProductsQuery.Data>>
    fun getNewArrivalsInfo(categoryId: Int?) {
        newArrivalsLiveData = newArrivalsRepository.getNewArrivalsDetail(categoryId)
    }

    lateinit var newArrivalsProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    fun getProductsLayoutInfo(sku: List<String>?) {
        newArrivalsProductsLiveData = newArrivalsProductsSkuByListRepository.getProductsSkuDetail(sku)
    }

    lateinit var recommendedLiveData: LiveData<Resource<NewArrivalsProductsQuery.Data>>
    fun getRecommendedInfo(categoryId: Int?) {
        recommendedLiveData = recommendRepository.getNewArrivalsDetail(categoryId)
    }

    lateinit var recommendedProductsLiveData: LiveData<Resource<ProductsSkuByListQuery.Data>>
    fun getRecommendedProductsLayoutInfo(sku: List<String>?) {
        recommendedProductsLiveData = recommendedProductsSkuByListRepository.getProductsSkuDetail(sku)
    }


    override fun onCleared() {

        pdpRepository.clear()
        offerPlateRepository.clear()

        deliveryOptionsRepository.clear()
        storePickupRepository.clear()

        frequentBroughtRepository.clear()
        productsSkuRepository.clear()

        installmentsRepository.clear()

        newArrivalsRepository.clear()
        newArrivalsProductsSkuByListRepository.clear()

        recommendRepository.clear()
        recommendedProductsSkuByListRepository.clear()

        browsinghistoryaddrepository.clear()
        super.onCleared()
    }

}