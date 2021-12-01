package com.blink.blinkshopping.models.categorydetailslone

import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.models.brandlist.BrandListModel
import com.blink.blinkshopping.models.allcategorymodel.Children

import com.blink.blinkshopping.models.gridcategory.GridCategoryData

data class Base_LOne_ModelList(
    var id: String,
    var type: String,
    var BaseSlider: BaseSlider,
    var adsBlocks: BaseAdsBlocks,
    var baseDailyDealsproducts: BaseDailyDealsproducts,
    var gridChildData: GridCategoryData,
    var brandList: BrandListModel,
    var baseNewArrivals: BaseNewArrivals,
    var baseRecommended: BaseRecommended,
    var baseDynamicLinks: BaseDynamicLinks,
    var baseBanners: BaseBanners,
    var baseBestSeller: BaseBestSeller, //  val bestSellerModel: BestSellerModel,
    var mainCategorySlider: MainCategorySlider,
    var baseOfferPlate: BaseOfferPlate,
    var baseAdsImages: BaseAdsImages,
    val baseDescription: String?,
    var baseBrowseHistory: ArrayList<String>?

)