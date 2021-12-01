package com.blink.blinkshopping.util


import android.content.Context
import android.content.SharedPreferences
import com.google.gson.Gson

// Shared storage for user data
class SharedStorage private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences
    var defaultArea: String?
        get() = sharedPreferences.getString(Keys.DEAFAULT_AREA, "")
        set(defaultArea) {
            sharedPreferences.edit().putString(Keys.DEAFAULT_AREA, defaultArea).commit()
        }


    //gson.fromJson(json, BoxSearch.class);
    var nearestLocation: String
        get() = sharedPreferences.getString(Keys.NEAREST_LOCATION, "")
        set(nearestLocation) {
            val gson = Gson()
            val json = gson.toJson(nearestLocation)
            sharedPreferences.edit().putString(Keys.NEAREST_LOCATION, json).commit()
            //gson.fromJson(json, BoxSearch.class);
        }

    var groupId: String?
        get() = sharedPreferences.getString(Keys.USER_GROUPID, "")
        set(groupId) {
            sharedPreferences.edit().putString(Keys.USER_GROUPID, groupId).commit()
        }

    var userFName: String?
        get() = sharedPreferences.getString(Keys.USER_F_NAME, "")
        set(fName) {
            sharedPreferences.edit().putString(Keys.USER_F_NAME, fName).commit()
        }

    var userLName: String?
        get() = sharedPreferences.getString(Keys.USER_L_NAME, "")
        set(lNAme) {
            sharedPreferences.edit().putString(Keys.USER_L_NAME, lNAme).commit()
        }

    var userEmail: String?
        get() = sharedPreferences.getString(Keys.USER_EMAIL, "")
        set(email) {
            sharedPreferences.edit().putString(Keys.USER_EMAIL, email).commit()
        }

    var userPrimaryMobile: String?
        get() = sharedPreferences.getString(Keys.USER_PRIMARY_MOBILE, "")
        set(userPrimaryMobile) {
            sharedPreferences.edit().putString(Keys.USER_PRIMARY_MOBILE, userPrimaryMobile).commit()
        }

    var userPrefLanguage: String?
        get() = sharedPreferences.getString(Keys.USER_PREF_LANG, "")
        set(preflang) {
            sharedPreferences.edit().putString(Keys.USER_PREF_LANG, preflang).commit()
        }

    var userPrefContact: String?
        get() = sharedPreferences.getString(Keys.USER_PREF_CONTACT, "")
        set(prefcont) {
            sharedPreferences.edit().putString(Keys.USER_PREF_CONTACT, prefcont).commit()
        }

    var userIsdCode: String?
        get() = sharedPreferences.getString(Keys.USER_ISD_CODE, "")
        set(isdCode) {
            sharedPreferences.edit().putString(Keys.USER_ISD_CODE, isdCode).commit()
        }

    fun setUserSession(session: String?) {
        sharedPreferences.edit().putString(Keys.USER_SESSION, session).commit()
    }

    val usersession: String
        get() = sharedPreferences.getString(Keys.USER_SESSION, "")

    var ifCouponApplied: String?
        get() = sharedPreferences.getString(Keys.COUPON_APPLIED, "no")
        set(id) {
            sharedPreferences.edit().putString(Keys.COUPON_APPLIED, id).commit()
        }

    var quoteId: String?
        get() = sharedPreferences.getString(Keys.QUOTE_ID, "")
        set(id) {
            sharedPreferences.edit().putString(Keys.QUOTE_ID, id).commit()
        }

    var userID: String?
        get() = sharedPreferences.getString(Keys.USER_ID, "")
        set(id) {
            sharedPreferences.edit().putString(Keys.USER_ID, id).commit()
        }

    fun deleteUserData() {
        sharedPreferences.edit().clear().commit()
    }

    var userDefaultLocation: String?
        get() = sharedPreferences.getString(Keys.USER_DEFAULT_LOCATION, "")
        set(location) {
            sharedPreferences.edit().putString(Keys.USER_DEFAULT_LOCATION, location).commit()
        }

    var shipedData: String?
        get() = sharedPreferences.getString("ShipedData", "")
        set(ShipedData) {
            sharedPreferences.edit().putString("ShipedData", ShipedData).commit()
        }

    fun setAppId(appId: String?) {
        sharedPreferences.edit().putString(Keys.appId, appId).commit()
    }

    fun getappId(): String {
        return sharedPreferences.getString(Keys.appId, "")
    }

    var lat: Double
        get() = java.lang.Double.longBitsToDouble(sharedPreferences.getLong(Keys.LAT, 0))
        set(lat) {
            sharedPreferences.edit().putLong(Keys.LAT, java.lang.Double.doubleToLongBits(lat))
                .commit()
        }

    var long: Double
        get() = java.lang.Double.longBitsToDouble(sharedPreferences.getLong(Keys.LONG, 0))
        set(lng) {
            sharedPreferences.edit().putLong(Keys.LONG, java.lang.Double.doubleToLongBits(lng))
                .commit()
        }

    var token: String?
        get() = sharedPreferences.getString(Keys.token, "")
        set(token) {
            sharedPreferences.edit().putString(Keys.token, token).commit()
        }

    fun setsecretKey(secretKey: String?) {
        sharedPreferences.edit().putString(Keys.secretKey, secretKey).commit()
    }

    fun getsecretKey(): String {
        return sharedPreferences.getString(Keys.secretKey, "")
    }

    fun setDefaultLocationFlag(vale: Boolean) {
        sharedPreferences.edit().putBoolean(Keys.IS_DEFAULT_LOCATION_SET, vale).commit()
    }

    var storeId: String?
        get() = sharedPreferences.getString(Keys.STORE_ID, "")
        set(appId) {
            sharedPreferences.edit().putString(Keys.STORE_ID, appId).commit()
        }

    var cityId: String?
        get() = sharedPreferences.getString(Keys.CITY_ID, "")
        set(appId) {
            sharedPreferences.edit().putString(Keys.CITY_ID, appId).commit()
        }

    var regionId: String?
        get() = sharedPreferences.getString(Keys.REGION_ID, "")
        set(appId) {
            sharedPreferences.edit().putString(Keys.REGION_ID, appId).commit()
        }

    val isDefaultLocationSet: Boolean
        get() = sharedPreferences.getBoolean(Keys.IS_DEFAULT_LOCATION_SET, false)

    fun setDefaultLocationReorderFlag(vale: Boolean) {
        sharedPreferences.edit().putBoolean(Keys.IS_DEFAULT_LOCATION_REORDER_SET, vale).commit()
    }

    val isDefaultLocationReorderSet: Boolean
        get() = sharedPreferences.getBoolean(Keys.IS_DEFAULT_LOCATION_REORDER_SET, false)

    var language: String?
        get() = sharedPreferences.getString(Keys.LANGUAGE, Keys.LANGUAGE_ENGLISH)
        set(language) {
            sharedPreferences.edit().putString(Keys.LANGUAGE, language).apply()
        }

    var adminToken: String?
        get() = sharedPreferences.getString(Keys.ADMIN_TOKEN, "")
        set(token) {
            sharedPreferences.edit().putString(Keys.ADMIN_TOKEN, token).apply()
        }

    var isGuestUserRegisteredForNotifications: Boolean?
        get() = sharedPreferences.getBoolean(Keys.GUEST_REGISTER_FOR_NOTIFICATIONS, false)
        set(registered) {
            sharedPreferences.edit().putBoolean(
                Keys.GUEST_REGISTER_FOR_NOTIFICATIONS,
                registered!!
            )
                .apply()
        }

    var couponCode: String?
        get() = sharedPreferences.getString(Keys.COUPON_CODE, "")
        set(couponCode) {
            sharedPreferences.edit().putString(Keys.COUPON_CODE, couponCode).apply()
        }

    fun set_is_loyal_enabled(isLoyalEnabled: Boolean?) {
        sharedPreferences.edit().putBoolean(Keys.IS_LOYAL_ENABLED, isLoyalEnabled!!).apply()
    }

    val isLoyalEnabled: Boolean
        get() = sharedPreferences.getBoolean(Keys.IS_LOYAL_ENABLED, false)

    var recentSearch: String?
        get() = sharedPreferences.getString(Keys.RECENT_SEARCH, "")
        set(search) {
            sharedPreferences.edit().putString(Keys.RECENT_SEARCH, search).apply()
        }

    //var productClickId: String?
//        get() = sharedPreferences.getString(Keys.PRODUCT_CLICK, "")
//        set(search) {
//            sharedPreferences.edit().putString(Keys.PRODUCT_CLICK, search).apply()
//        }

//    fun getProductClickId(): String? {
//        return sharedPreferences.getString(Keys.PRODUCT_CLICK, "")
//    }
//    fun setProductClickId(search: String?) {
//        sharedPreferences.edit().putString(Keys.PRODUCT_CLICK, search).apply()
//    }


    fun setUserLogin(islogin: Boolean) {
        sharedPreferences.edit().putBoolean(Keys.IS_LOGIN, islogin).commit()
    }

    val isLogin: Boolean
        get() = sharedPreferences.getBoolean(Keys.IS_LOGIN, false)

    fun setUserEmail1(email: String?) {
        sharedPreferences.edit().putString(Keys.USER_EMAIL, email).commit()
    }

    fun getUserEmail1(): String? {
        return sharedPreferences.getString(Keys.USER_EMAIL, "")
    }

    fun setUserPassword(password: String?) {
        sharedPreferences.edit().putString(Keys.USER_PASSWORD, password).commit()
    }

    fun getUserPassword(): String? {
        return sharedPreferences.getString(Keys.USER_PASSWORD, "")
    }

    fun setUserToken(token: String?) {
        sharedPreferences.edit().putString(Keys.USER_TOKEN, token).commit()
    }

    fun getUserToken(): String? {
        return sharedPreferences.getString(Keys.USER_TOKEN, "")
    }


    companion object {
        private var sharedStorage: SharedStorage? = null
        fun getInstance(context: Context): SharedStorage? {
            if (sharedStorage == null) sharedStorage =
                SharedStorage(context)
            return sharedStorage
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences(
            Keys.SHARED_STORAGE_FILE,
            Context.MODE_PRIVATE
        )
    }
}
