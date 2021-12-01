package com.blink.blinkshopping.models

import android.os.Parcelable
import com.blink.blinkshopping.BrowsingHistoryGetQuery
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/13/2020.
 */

data class BaseArrayList(
    var type: String,
    var mainCategorySlider: MainCategorySlider,
    var BaseSlider: BaseSlider,
    var adsBlocks: BaseAdsBlocks,
    var baseDailyDealsproducts: BaseDailyDealsproducts,
    var baseNewArrivals: BaseNewArrivals,
    var baseRecommended: BaseRecommended,
    var baseInspiredHistory: BaseInspiredHistory,
    var baseDynamicLinks: BaseDynamicLinks,
    var baseBanners: BaseBanners,
    var baseBestSeller: BaseBestSeller,
    var baseOfferPlate: BaseOfferPlate,
    var baseAdsImages: BaseAdsImages,
    var baseBrowseHistory: ArrayList<String>?
    //var baseBrowseHistory: MutableList<BrowsingHistoryGetQuery.BrowsingHistory>?
)