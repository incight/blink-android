package com.blink.blinkshopping.util


import android.content.Context
import android.content.SharedPreferences

// Shared storage for user data
class SharedPrefForHistory private constructor(context: Context) {
    private val sharedPreferences: SharedPreferences


    fun deleteUserData() {
        sharedPreferences.edit().clear().commit()
    }

    fun getProductClickId(): String? {
        return sharedPreferences.getString(Keys.PRODUCT_CLICK, "")
    }
    fun setProductClickId(search: String?) {
        sharedPreferences.edit().putString(Keys.PRODUCT_CLICK, search).apply()
    }

    companion object {
        private var sharedStorage: SharedPrefForHistory? = null
        fun getInstance(context: Context): SharedPrefForHistory? {
            if (sharedStorage == null) sharedStorage =
                SharedPrefForHistory(context)
            return sharedStorage
        }
    }

    init {
        sharedPreferences = context.getSharedPreferences(
            Keys.SHARED_HISTORY_STORAGE_FILE,
            Context.MODE_PRIVATE
        )
    }
}
