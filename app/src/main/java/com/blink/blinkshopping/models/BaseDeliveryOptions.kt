package com.blink.blinkshopping.models

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

@Parcelize
data class BaseDeliveryOptions(
    val data: DeliveryData
) : Parcelable {}

@Parcelize
data class DeliveryData(
    val deliveryoptions: MutableList<Deliveryoption>
) : Parcelable {}

@Parcelize
data class Deliveryoption(
    val best_option: String,
    val options: MutableList<DOption>,
    val qty: Int,
    val warehouses: MutableList<String>
) : Parcelable {
    fun list(): MutableList<DOption> {
        for (i in options.iterator()) {
            if (i.id.equals(best_option)) {
                val itemPos: Int = options.indexOf(i)
                options.removeAt(itemPos)
                options.add(0, i)
            }
        }
        return options
    }
}

@Parcelize
data class DOption(
    val id: Int,
    val name: String,
    val price: Int,
    val short_form: String
) : Parcelable {

    fun deliveryOptionsName(): String {
        var path = ""
        if (name != null) {
            if (price == 0) {
                path = name + " Free"
            } else if (price == 143 && name.equals("Store pickup")) {
                path = name
            } else {
                path = name + " " + price + " KD"
            }
        }
        return path
    }

}