package com.blink.blinkshopping.models.ltwocategorybasemodel

import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.models.bestsellers.BestSellerModel
import com.blink.blinkshopping.models.brandlist.BrandListModel
import com.blink.blinkshopping.models.brandlist.Brands
import com.blink.blinkshopping.models.gridcategory.GridCategoryData
import com.blink.blinkshopping.models.allcategorymodel.ChildrenX
import com.blink.blinkshopping.models.allcategorymodel.ChildrenXX

data class Base_LTwo_ModelList (
        var id: String,
        var type: String,
        var BaseSlider: BaseSlider,
        var adsBlocks: BaseAdsBlocks,
        var baseDailyDealsproducts: BaseDailyDealsproducts,
        var l2ProductList: MutableList<ChildrenX>?,
        var l3ProductList: MutableList<ChildrenXX>?,
        var brandList: BrandListModel,
        var baseNewArrivals: BaseNewArrivals,
        var baseRecommended: BaseRecommended,
        var baseDynamicLinks: BaseDynamicLinks,
        var baseBanners: BaseBanners,
        var baseBestSeller: BaseBestSeller,
        var mainCategorySlider: MainCategorySlider,
        var baseOfferPlate: BaseOfferPlate,
        var baseAdsImages: BaseAdsImages,
        val baseDescription: String?,
        var baseBrowseHistory: ArrayList<String>?,
        var baseProductsList: BaseProductsDetails?
){
}


//        var type: String,
//        var brandList:List<Brands>,
//        var baseNewArrivals: BaseNewArrivals,
//        var bestSeller : BestSellerModel

