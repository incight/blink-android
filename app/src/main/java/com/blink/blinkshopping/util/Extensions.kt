package com.blink.blinkshopping.utils

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.net.ConnectivityManager
import android.os.Build
import android.provider.Settings
import android.text.InputType
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.util.Patterns
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.navigation.NavDirections
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import com.blink.blinkshopping.DailyDealsProductsQuery
import com.blink.blinkshopping.R
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.models.BaseDailyDealsproducts
import com.blink.blinkshopping.models.ProductDetails
import com.blink.blinkshopping.models.ProductItem
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import kotlinx.android.synthetic.main.activity_nhome.*
import java.lang.reflect.Type
import java.text.SimpleDateFormat
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern


fun Fragment.isOnline(): Boolean {
    try {
        val cm = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
        val activeNetwork = cm?.activeNetworkInfo
        return activeNetwork?.isConnectedOrConnecting == true
    } catch (e: java.lang.Exception) {
        logE(e)
    }
    return false
}

fun Fragment.navigate(directions: NavDirections) {
    NavHostFragment.findNavController(parentFragment!!).navigate(directions)
}

fun Fragment.popBackStack() {
    NavHostFragment.findNavController(parentFragment!!).popBackStack()
}

fun AppCompatActivity.navigate(directions: NavDirections) {
    Navigation.findNavController(this, R.id.nav_graph).navigate(directions)
}

fun View.setSingleClickListener(onSingleClick: (View) -> Unit) {
    val singleClickListener = SingleClickListener {
        onSingleClick(it)
    }
    setOnClickListener(singleClickListener)
}


fun toast(activity: Activity, message: String) {
    activity?.let {
        Toast.makeText(it, message, Toast.LENGTH_SHORT).show()
    }
}

fun setFocuasables(value: Boolean, editText: EditText) {
    editText.setEnabled(value)
    editText.setClickable(value)
}

fun Any?.logE(message: String) {
    Log.e(toTag(), message)
}

fun Any?.logE(throwable: Throwable?) {
    throwable?.let {
        logE(throwable.message ?: "", throwable)
    }
}

fun Any?.logE(message: String, throwable: Throwable?) {
    Log.e(toTag(), message, throwable)
}

fun Any?.logE(message: String, exception: Exception?) {
    Log.e(toTag(), message, exception)
}


fun Throwable?.log() {
    this?.let {
        Log.e(toTag(), message, this)
    }
}

fun Any?.logI(message: String) {
    Log.i(toTag(), message)
}

fun Any?.toTag(): String = this?.javaClass?.simpleName ?: ""

/*

fun View.clickObservable(): Observable<Unit>? {
    return clicks()
        .throttleFirst(1, TimeUnit.SECONDS)
        .observeOn(AndroidSchedulers.mainThread())
}

fun <T> Observable<T>.onSuccess(view: BaseView? = null, onSuccess: (T) -> Unit) {
    subscribe({
        view?.dismissProgress()
        onSuccess(it)
    }, {
        logE(it)
    })
}

fun <T> Single<T>.onSuccess(view: BaseView? = null, onSuccess: (T) -> Unit) {
    subscribe({
        view?.dismissProgress()
        onSuccess(it)
    }, {
        view?.dismissProgress()
        // view?.handle(it)
        logE(it)
    })
}


fun Double?.toAmount(): Amount {
    val doubleValue = this ?: 0.0
    val amountValue = (doubleValue * 100).toLong()
    return Amount(value = amountValue)
}

fun Double?.toRupees(): String {
    val currency = Currency.getInstance(CurrencyUtils.CURRENT_CURRENCY)
    val formatter = NumberFormat.getCurrencyInstance().apply {
        setCurrency(currency)
    }
    return formatter.format(this ?: 0)
}

fun Amount?.formattedString(): String {
    this?.let {
        val amount = value.toDouble() / 100
        return amount.toRupees()
    }
    return ""
}

fun Amount?.toRupees(): Double {
    this?.let {
        var result = (BigDecimal(value).divide(BigDecimal(100)))
        result = result.setScale(2, RoundingMode.HALF_EVEN)
        return result.toDouble()
    }
    return 0.0
}*/

fun String?.isValidEmail(): Boolean {
    val emailRegex = "\\b[\\w.%-]+@[-.\\w]+\\.[A-Za-z]{2,4}\\b"
    return (this ?: "").matches(Regex(emailRegex))
}

//fun String?.isValidMobile(phone: String): Boolean {
//    var MobilePattern = "[0-9]{10}"
//    return (this ?: "").matches(Regex(MobilePattern))
//}

fun isValidMobile(target: CharSequence?): Boolean {
    return if (target == null) {
        false
    } else {
        Patterns.PHONE.matcher(target).matches()
    }
}

fun isValidMobile(phone: String?) =
    (phone.isNullOrBlank() || phone.length < 10 || phone.length > 11).not() && Patterns.PHONE.matcher(
        phone
    ).matches()

fun firstName(firstName: String): Boolean {
    return firstName.matches(Regex("[A-Z][a-z]*"))
}

fun isValidPassword(password: String?): Boolean {
    val regex = ("^(?=.*[0-9])"
            + "(?=.*[a-z])(?=.*[A-Z])"
            + "(?=.*[@#$%^&+=])"
            + "(?=\\S+$).{8,20}$")

    val p: Pattern = Pattern.compile(regex)
    if (password == null) {
        return false
    }
    val m: Matcher = p.matcher(password)
    return m.matches()
}

var isPasswordVisible = false

fun togglePassVisability(activity: Activity, editText: EditText, imageView: ImageView) {
    if (isPasswordVisible) {
        val pass: String = editText.getText().toString()
        editText.setTransformationMethod(PasswordTransformationMethod.getInstance())
        editText.setInputType(InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD)
        editText.setText(pass)
        editText.setSelection(pass.length)

        imageView.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_eyestrike))

    } else {
        val pass: String = editText.getText().toString()
        editText.setTransformationMethod(HideReturnsTransformationMethod.getInstance())
        editText.setInputType(InputType.TYPE_CLASS_TEXT)
        editText.setText(pass)
        editText.setSelection(pass.length)

        imageView.setImageDrawable(ContextCompat.getDrawable(activity!!, R.drawable.ic_eye))

    }
    isPasswordVisible = !isPasswordVisible
}

fun String?.isValidName(): Boolean {
    val nameRegex = "^[a-zA-Z0-9 .]+$"
    return (this ?: "").matches(Regex(nameRegex))
}

fun Long.toFormattedDate(): String {
    val format = SimpleDateFormat("dd/MM/yyyy")
    return format.format(this)
}

fun Context.hideKeyboard(view: View) {
    val inputMethodManager = getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
    inputMethodManager.hideSoftInputFromWindow(view.windowToken, 0)
}

fun Calendar.clearTime() {
    this.set(Calendar.HOUR_OF_DAY, 0)
    this.set(Calendar.MINUTE, 0)
    this.set(Calendar.SECOND, 0)
    this.set(Calendar.MILLISECOND, 0)
}

fun messageDialog(a: Activity?, title: String?, message: String?) {
    val dialog =
        AlertDialog.Builder(a!!)
    dialog.setTitle(title)
    dialog.setMessage(message)
    dialog.setNeutralButton("OK", null)
    dialog.create().show()
}


fun SortProductList(
    items: MutableList<ProductItem>,
    skusList: ArrayList<String>
): MutableList<ProductItem> {
    var sortedItems: MutableList<ProductItem> = arrayListOf()

    skusList.forEach { sku ->
        if (sku != null && !sku.isEmpty()) {
            for (j in 0..items.size - 1) {
                if (items.get(j).sku != null && !items.get(j).sku.isEmpty()) {
                    if (sku.equals(items.get(j).sku)) {
                        sortedItems.add(items.get(j))
                        break
                    }
                }
            }
        }
    }
    return sortedItems
}

fun SortInspireProductList(
    items: MutableList<ProductItem>,
    skusList: ArrayList<String>
): MutableList<ProductItem> {
    var sortedItems: MutableList<ProductItem> = arrayListOf()

    skusList.forEach { sku ->
        if (sku != null && !sku.isEmpty()) {
            for (j in 0..items.size - 1) {
                if (items.get(j).sku != null && !items.get(j).sku.isEmpty()) {
                    if (sku.equals(items.get(j).sku)) {
                        sortedItems.add(items.get(j))
                        break
                    }
                }
            }
        }
    }
    return sortedItems
}

fun SortProductListPDP(
    items: MutableList<ProductItem>,
    skusList: ArrayList<String>
): MutableList<ProductItem> {
    var sortedItems: MutableList<ProductItem> = arrayListOf()

    skusList.forEach { sku ->
        if (sku != null && !sku.isEmpty()) {
            for (j in 0..items.size - 1) {
                if (items.get(j).sku != null && !items.get(j).sku.isEmpty()) {
                    if (sku.equals(items.get(j).sku)) {
                        sortedItems.add(items.get(j))
                        break
                    }
                }
            }
        }
    }

    return sortedItems
}

fun subSortProductListAtCategorySlider1(
    item: ProductDetails?,
    skusList: ArrayList<String>
): ProductDetails {
    var sortedItems = ProductDetails(arrayListOf())
    skusList.forEach { sku ->
        for (j in 0..item!!.items.size - 1) {
            if (sku.equals(item!!.items.get(j).sku)) {
                sortedItems!!.items.add(item!!.items.get(j))
                break
            }
        }
    }
    return sortedItems
}

fun subsubSortProductListAtCategorySlider(
    item: ProductDetails?,
    skusList: ArrayList<String>
): ProductDetails {
    var sortedItems = ProductDetails(arrayListOf())
    skusList.forEach { sku ->
        for (j in 0..item!!.items.size - 1) {
            if (sku.equals(item!!.items.get(j).sku)) {
                sortedItems!!.items.add(item!!.items.get(j))
                break
            }
        }
    }
    return sortedItems
}

fun plainAlertDialog(message: String, activity: Activity) {
    val builder =
        android.app.AlertDialog.Builder(activity)
    builder.setMessage(message)
    builder.setCancelable(false)
    builder.setPositiveButton(
        "ok"
    ) { dialog, id ->
        dialog.cancel()
    }
    val alertDialog = builder.create()
    alertDialog.show()
}

fun alertDialog(title: String, message: String, activity: Activity) {
    val builder =
        android.app.AlertDialog.Builder(activity)
    builder.setTitle(title)
    builder.setMessage(message)
    builder.setCancelable(false)
    builder.setPositiveButton(
        "ok"
    ) { dialog, id ->
        dialog.cancel()
    }
    val alertDialog = builder.create()
    alertDialog.show()
}



