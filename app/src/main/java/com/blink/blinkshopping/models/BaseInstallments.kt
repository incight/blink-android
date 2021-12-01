package com.blink.blinkshopping.models

import android.os.Parcelable
import com.blink.blinkshopping.util.Utils.DecimalLimitter
import kotlinx.android.parcel.Parcelize


@Parcelize
data class BaseInstallments(
    val `data`: InstallmentsData
) : Parcelable {}

@Parcelize
data class InstallmentsData(
    val minstallments: Minstallments
) : Parcelable {}

@Parcelize
data class Minstallments(
    val about: String,
    val how_to_apply: String,
    val installmentinfo: MutableList<Installmentinfo>,
    val requirements: String
) : Parcelable {}

@Parcelize
data class Installmentinfo(
    val emi_calculation: String,
    val time_interval: String,
    val interval: Int,
    val minimum_amount: Double,
    val percentage: String,
    val time: String
) : Parcelable {

    fun time_interval(): String {
        if (time_interval != null) {
            time_interval.toLowerCase()
        }
        return time_interval
    }

    fun emi_calculation(): String {
        var emi = ""
        if (emi_calculation != null) {
            emi = DecimalLimitter(emi_calculation) + " KD monthly"
        }
        return emi
    }

}