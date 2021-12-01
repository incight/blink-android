package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

/**
 * Created by Maruthi Ram Yadav on 11/13/2020.
 */
@Parcelize
data class BaseArrayList1(
    var type: String,
    var baseCategorySliders: BaseCategorySliders,
    var BaseSlider: BaseSlider,
    var adsBlocks: BaseAdsBlocks,
    var baseDailyDealsproducts: BaseDailyDealsproducts,
    var baseNewArrivals: BaseNewArrivals,
    var baseRecommended: BaseRecommended,
    var baseDynamicLinks: BaseDynamicLinks,
    var baseBanners: BaseBanners
): Parcelable {}