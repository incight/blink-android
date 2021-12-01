package com.startup.infobase.utils

import android.app.Activity
import android.app.Dialog
import android.content.Context
import android.graphics.Bitmap
import android.graphics.Matrix
import android.graphics.Typeface
import android.graphics.drawable.ColorDrawable
import android.media.ExifInterface
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import android.view.inputmethod.InputMethodManager
import androidx.core.content.ContextCompat
import androidx.lifecycle.LiveData
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.models.allcategorymodel.AllCategoryData
import com.blink.blinkshopping.models.brandlist.BrandListModel
import com.blink.blinkshopping.models.gridcategory.GridCategoryData
import com.blink.blinkshopping.models.shopbybrand.ShopByBrandModel
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import java.io.IOException
import java.lang.reflect.Type
import java.util.regex.Matcher
import java.util.regex.Pattern

/**
 * Created by Praveen on 23/12/2020.
 */
object Globals {

    fun extractYoutubeVideoId(ytUrl: String?): String? {
        var vId: String? = null
        val pattern = "(?<=watch\\?v=|/videos/|embed\\/)[^#\\&\\?]*"
        val compiledPattern: Pattern = Pattern.compile(pattern)
        val matcher: Matcher = compiledPattern.matcher(ytUrl)
        if (matcher.find()) {
            vId = matcher.group()
        }
        return vId
    }


    fun getHelveticaBold(context: Context): Typeface? {
        return Typeface.createFromAsset(
            context.resources.assets, "fonts/helvetica_neue_bold.otf"
        )
    }

    fun getHelveticaRegular(context: Context): Typeface? {
        return Typeface.createFromAsset(
            context.resources.assets, "fonts/helvetica_neue_regular.otf"
        )
    }

    fun getHelveticaMedium(context: Context): Typeface? {
        return Typeface.createFromAsset(
            context.resources.assets, "fonts/helvetica_neue_medium.otf"
        )
    }

    @Throws(IOException::class)
    fun modifyOrientation(
        bitmap: Bitmap,
        image_absolute_path: String?
    ): Bitmap? {
        val ei = ExifInterface(image_absolute_path)
        val orientation: Int =
            ei.getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)
        return when (orientation) {
            ExifInterface.ORIENTATION_ROTATE_90 -> rotate(bitmap, 90f)
            ExifInterface.ORIENTATION_ROTATE_180 -> rotate(bitmap, 180f)
            ExifInterface.ORIENTATION_ROTATE_270 -> rotate(bitmap, 270f)
            ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> flip(bitmap, true, false)
            ExifInterface.ORIENTATION_FLIP_VERTICAL -> flip(bitmap, false, true)
            else -> bitmap
        }
    }

    fun rotate(
        bitmap: Bitmap,
        degrees: Float
    ): Bitmap? {
        val matrix = Matrix()
        matrix.postRotate(degrees)
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }

    fun flip(
        bitmap: Bitmap,
        horizontal: Boolean,
        vertical: Boolean
    ): Bitmap? {
        val matrix = Matrix()
        matrix.preScale(if (horizontal) -1F else 1F, if (vertical) -1F else 1F)
        return Bitmap.createBitmap(
            bitmap,
            0,
            0,
            bitmap.width,
            bitmap.height,
            matrix,
            true
        )
    }

    fun hideKeyboard(context: Context) {
        try {
            val inputMethodManager =
                (context as Activity).getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
            inputMethodManager.hideSoftInputFromWindow(
                (context as Activity).currentFocus?.windowToken,
                0
            )
        } catch (e: Exception) {
            e.stackTrace
        }
    }

    fun isValidString(string: String?): Boolean {
        return if (string != null && string != "null" && string != null && string != "") {
            true
        } else {
            false
        }
    }



    fun convertImgData(queryData: Resource<GetBrandImageListQuery.Data>): BaseBrandImages {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseBrandImages>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertToDealsOfTheDay(queryData: Resource<DailyDealsProductsQuery.Data>): BaseDailyDealsproducts {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseDailyDealsproducts>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertingOfferPlates(queryData: Resource<OfferPlatesQuery.Data>): BaseOfferPlate {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseOfferPlate>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertToRecommended(queryData: Resource<NewArrivalsProductsQuery.Data>): BaseRecommended {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseRecommended>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }


    fun brandConverter(queryData: Resource<GetBrandImageListQuery.Data>): BrandListModel {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BrandListModel>() {}.type
        return gson.fromJson(str1, collectionType1)
    }

    fun imageDataConverter(queryData: Resource<ShopByBrandImageQuery.Data>): ShopByBrandModel {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<ShopByBrandModel>() {}.type
        return gson.fromJson(str1, collectionType1)
    }

    fun gridConverter(queryData: Resource<CategoryLOneDetailsQuery.Data>): GridCategoryData {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<GridCategoryData>() {}.type
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertToNewArrivals(queryData: Resource<NewArrivalsProductsQuery.Data>): BaseNewArrivals {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseNewArrivals>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }
    fun convertToNewArrivals(queryData: Resource<NewArrivalsProductsQuery.Data>): BaseNewArrivals {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseNewArrivals>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertToDynamic(queryData: Resource<GetDynamicBlockQuery.Data>): BaseDynamicLinks {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseDynamicLinks>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun BaseHistoryConverter(queryData: Resource<BrowsingHistoryGetQuery.Data>): BaseBrowseHistory {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BrowsingHistoryGetQuery>() {}.type
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertingMenuList(queryData: Resource<GetMegaMenuQuery.Data>): BaseMegaMenu {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseMegaMenu>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertingCountryList(queryData: Resource<GetCountriesListQuery.Data>): BaseCountry {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseCountry>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }


    fun ConvertingList(queryData: Resource<ProductsSkuByListQuery.Data>): BaseProductDetails {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseProductDetails>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertingCategoryList(queryData: LiveData<Resource<CategorySlidersQuery.Data>>): BaseCategorySliders {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseCategorySliders>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

//    TODO Praveen Uncomment and check
//    fun ConvertingList(queryData: LiveData<Resource<CategorySlidersQuery.Data>>): BaseCategorySliders {
//        var gson = Gson()
//        val str1: String = gson.toJson(queryData)
//        val collectionType1: Type = object : TypeToken<BaseCategorySliders>() {}.getType()
//        return gson.fromJson(str1, collectionType1)
//    }


    fun ConvertToBlocks(queryData: Resource<AdsBlocksQuery.Data>): BaseAdsBlocks {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseAdsBlocks>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }


    fun ConvertToInspired(queryData: Resource<NewArrivalsProductsQuery.Data>): BaseInspiredHistory {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseInspiredHistory>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertToBestSeller(queryData: Resource<BestsellersQuery.Data>): BaseBestSeller {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseBestSeller>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }


    fun ConvertToBanners(queryData: Resource<GetBannersListQuery.Data>): BaseBanners {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseBanners>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }


    fun ConvertingAdsImageList(queryData: Resource<GetAdsimagesQuery.Data>): BaseAdsImages {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseAdsImages>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertPdpToSlider(queryData: Resource<ProductsForSKUQuery.Data>): BaseSingleProductDetails {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseSingleProductDetails>() {}.type
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertToSubCategory(queryData: Resource<GetMenuCategoryListQuery.Data>): BaseSubCategory {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseSubCategory>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertingCategoryList(queryData: Resource<CategorySlidersQuery.Data>): BaseCategorySliders {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseCategorySliders>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertToSlider(queryData: Resource<SliderBlockQuery.Data>): BaseSlider {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseSlider>() {}.type
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertingDeliveryOptions(queryData: Resource<ProductDeliveryOptionsQuery.Data>): BaseDeliveryOptions {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseDeliveryOptions>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertingStorePickUp(queryData: Resource<StorePickupQuery.Data>): BaseStorePickup {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseStorePickup>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun ConvertingMonthlyInstallments(queryData: Resource<GetInstallmentsQuery.Data>): BaseInstallments {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<BaseInstallments>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }

    fun convertLevelData(queryData: Resource<LevelcategoryListQuery.Data>): AllCategoryData {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<AllCategoryData>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }


    fun convertBestProductsWithFilter(queryData: Resource<ProductsQuery.Data>): BaseProductsDetails {
        var gson = Gson()
        val str1: String = gson.toJson(queryData)
        val collectionType1: Type = object : TypeToken<ProductsQuery>() {}.getType()
        return gson.fromJson(str1, collectionType1)
    }


//    fun convertData(queryData: Resource<HomeLayoutsQuery.Data>): Base_LOne_ModelList {
//        var gson = Gson()
//        val str1: String = gson.toJson(queryData)
//        val collectionType1: Type = object : TypeToken<Base_LOne_ModelList>() {}.type
//        return gson.fromJson(str1, collectionType1)
//    }

//    fun converterData(queryData: Resource<HomeLayoutsQuery.Data>): Data {
//        var gson = Gson()
//        val str1: String = gson.toJson(queryData)
//        val collectionType1: Type = object : TypeToken<Data>() {}.type
//        return gson.fromJson(str1, collectionType1)
//    }

//    fun convertData(queryData: com.blink.blinkshopping.base.Resource<LevelcategoryListQuery.Data>): AllCategoryData {
//        var gson = Gson()
//        val str1: String = gson.toJson(queryData)
//        val collectionType1: Type = object : TypeToken<AllCategoryData>() {}.getType()
//        return gson.fromJson(str1, collectionType1)
//    }

//    fun convertBestSeller(queryData: Resource<BestsellersQuery.Data>): BestSellerModel {
//        var gson = Gson()
//        val str1: String = gson.toJson(queryData)
//        val collectionType1: Type = object : TypeToken<BestSellerModel>() {}.getType()
//        return gson.fromJson(str1, collectionType1)
//    }



//    fun showProgressbar() {
//        dismissProgressbar()
//        if (isFinishing.not()) {
//            mProgressDialog = ProgressDialog(this).apply {
//                show()
//                window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
//                setContentView(R.layout.progress_dialog)
//                isIndeterminate = true
//                setCancelable(false)
//                setCanceledOnTouchOutside(false)
//                show()
//                Handler().postDelayed({
//                    setCancelable(true)
//                    setCanceledOnTouchOutside(true)
//                }, (2 * 60 * 1000).toLong())
//            }
//        }
//    }
//
//    fun dismissProgressbar() {
//        mProgressDialog?.let { if (it.isShowing) it.cancel() }
//    }


//    fun changeFragment(frag: Fragment, @IdRes id: Int, bundle: Bundle, activity: Activity) {
//        frag.arguments = bundle
//        activity!!.supportFragmentManager
//            .beginTransaction()
//            .setCustomAnimations(
//                R.anim.slide_in_right,
//                R.anim.slide_out_left,
//                R.anim.slide_in_left,
//                R.anim.slide_out_right
//            )
//            .replace(id, frag, frag.javaClass.simpleName)
//            .addToBackStack(null)
//            .commit()
//    }

    fun commonDialog(layoutDialog: Int, isCancelable: Boolean, context: Context) =
        Dialog(context).apply {
            requestWindowFeature(Window.FEATURE_NO_TITLE)
            setCancelable(isCancelable)
            setCanceledOnTouchOutside(isCancelable)
            setContentView(layoutDialog)
            val window = window!!
            val param = window.attributes
            param.gravity = Gravity.CENTER or Gravity.CENTER_HORIZONTAL
            param.windowAnimations = R.style.CenterAlertDialogAnimation
            window.attributes = param
            window.setBackgroundDrawable(
                ColorDrawable(
                    ContextCompat.getColor(
                        context!!,
                        R.color.white_transperent
                    )
                )
            )
            window.setLayout(
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT
            )
        }

}