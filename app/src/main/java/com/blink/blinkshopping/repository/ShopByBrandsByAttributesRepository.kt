package com.blink.blinkshopping.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.blink.blinkshopping.ShopByBrandsQuery
import com.blink.blinkshopping.ShopByBrandsWithAttributesQuery
import com.blink.blinkshopping.base.Repository
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.network.ApiService
import com.blink.blinkshopping.type.AggregationsInput
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

class ShopByBrandsByAttributesRepository @Inject constructor(private var apiService: ApiService) : Repository {

    private val compositeDisposable = CompositeDisposable()

    private val result = MutableLiveData<Resource<ShopByBrandsWithAttributesQuery.Data>>()

    fun getShopByBrandsByAttributesDetail(categoryId: List<AggregationsInput>?): LiveData<Resource<ShopByBrandsWithAttributesQuery.Data>> {
        addDisposable(
            apiService.getShowByBrandsByAttributesDetail(categoryId)!!
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .doOnSubscribe { whenStart() }
                .subscribe(
                    { result -> whenSuccess(result) },
                    { cause -> whenError(cause.toString()) }
                )
        )
        return result
    }

    private fun whenStart() {
        result.value = Resource.Loading()
    }

    fun whenSuccess(pokemon: ShopByBrandsWithAttributesQuery.Data) {
        result.value = Resource.Success(pokemon)
    }

    fun whenError(cause: String) {
        result.value = Resource.Failure(cause)
    }

    override fun addDisposable(disposable: Disposable) {
        compositeDisposable.add(disposable)
    }

    override fun clear() {
        compositeDisposable.clear()
    }
}