package com.blink.blinkshopping.network

import com.apollographql.apollo.ApolloClient
import com.apollographql.apollo.rx2.Rx2Apollo
import com.blink.blinkshopping.*
import com.blink.blinkshopping.type.AggregationsInput
import io.reactivex.Observable
import javax.inject.Inject

class ApiService @Inject constructor(private var apolloClient: ApolloClient) {


    fun getSideMenuDetail(id: String): Observable<GetMenuCategoryListQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                GetMenuCategoryListQuery.builder().categoryid(id).build()
            )
        ).map {
            it.data()
        }
    }

    fun getMegamenuDetail(): Observable<GetMegaMenuQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(GetMegaMenuQuery.builder().build())).map {
            it.data()
        }
    }

    fun getLayoutDetail(id: String, device_type: String): Observable<HomeLayoutsQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                HomeLayoutsQuery.builder().layout(id).device_type(device_type).build()
            )
        )
            .map {
                it.data()
            }
    }

    fun getSlidersDetail(
        id: Int,
        page: List<String>?,
        category: List<Int>?
    ): Observable<SliderBlockQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                SliderBlockQuery.builder().id(id).page(page).category(category).build()
            )
        ).map {
            it.data()
        }
    }

    fun getBrowsingHistoryGet(): Observable<BrowsingHistoryGetQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(BrowsingHistoryGetQuery.builder().build())).map {
            it.data()
        }
    }

    fun getBrowsingHistoryAdd(id: List<Int>?): Observable<BrowsingHistoryAddMutation.Data>? {
        return Rx2Apollo.from(
            apolloClient.mutate(
                BrowsingHistoryAddMutation.builder().productIds(id).build()
            )
        ).map {
            it.data()
        }
    }

    fun getBrowsingHistoryDelete(id: List<Int>?): Observable<BrowsingHistoryDeleteMutation.Data>? {
        return Rx2Apollo.from(
            apolloClient.mutate(
                BrowsingHistoryDeleteMutation.builder().productIds(id).build()
            )
        ).map {
            it.data()
        }
    }

    fun getAdsBlockDetail(): Observable<AdsBlocksQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(AdsBlocksQuery.builder().build())).map {
            it.data()
        }
    }

    fun getproductsFilter(id: String?): Observable<ProductsQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(ProductsQuery.builder().category_id(id).build())).map {
            it.data()
        }
    }


    fun getAdsImagesDetail(id: List<Int>?): Observable<GetAdsimagesQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(GetAdsimagesQuery.builder().id(id).build())).map {
            it.data()
        }
    }


    fun getCompareWithSimilarDetail(product: Int): Observable<CompareWithSimilarProductsQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                CompareWithSimilarProductsQuery.builder().productid(product).build()
            )
        ).map {
            it.data()
        }
    }

    fun getInstalmentsDetail(sku: String, amount: String): Observable<GetInstallmentsQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                GetInstallmentsQuery.builder().sku(sku).amount(amount).build()
            )
        ).map {
            it.data()
        }
    }


    fun getStorePickupDetail(sku: String, qty: Int): Observable<StorePickupQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                StorePickupQuery.builder().sku(sku).qty(qty).build()
            )
        ).map {
            it.data()
        }
    }

    fun getOfferPlatesDetail(): Observable<OfferPlatesQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(OfferPlatesQuery.builder().build())).map {
            it.data()
        }
    }


    fun getDealsOfTheDayDetail(): Observable<DailyDealsProductsQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(DailyDealsProductsQuery.builder().build())).map {
            it.data()
        }
    }


    fun getProductsSkuDetail(sku: List<String>?): Observable<ProductsSkuByListQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                ProductsSkuByListQuery.builder().`in`(sku).build()
            )
        ).map {
            it.data()
        }
    }

    fun getSingleProductSkuDetail(sku: String?): Observable<ProductsForSKUQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(ProductsForSKUQuery.builder().sku(sku!!).build()))
            .map {
                it.data()
            }
    }

    fun getNewArrivalsDetail(categoryId: Int?): Observable<NewArrivalsProductsQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                NewArrivalsProductsQuery.builder().id(categoryId).build()
            )
        ).map {
            it.data()
        }
    }

    fun getRecommedned(categoryId: Int?): Observable<NewArrivalsProductsQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                NewArrivalsProductsQuery.builder().id(categoryId).build()
            )
        ).map {
            it.data()
        }
    }

    fun getRecommendedProductsSkuDetail(sku: List<String>?): Observable<ProductsSkuByListQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                ProductsSkuByListQuery.builder().`in`(sku).build()
            )
        ).map {
            it.data()
        }
    }

    fun getInspiredHistory(categoryId: Int?): Observable<NewArrivalsProductsQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                NewArrivalsProductsQuery.builder().id(categoryId).build()
            )
        ).map {
            it.data()
        }
    }

    fun getInspiredHistoryProductsSkuDetail(sku: List<String>?): Observable<ProductsSkuByListQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                ProductsSkuByListQuery.builder().`in`(sku).build()
            )
        ).map {
            it.data()
        }
    }

    fun getDynamicBlocksDetail(
        id: Int,
        customeremail: String?
    ): Observable<GetDynamicBlockQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                GetDynamicBlockQuery.builder().id(id).customeremail(customeremail).build()
            )
        ).map {
            it.data()
        }
    }


    fun getcategorySlidersDetail(id: Int): Observable<CategorySlidersQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(CategorySlidersQuery.builder().id(id).build()))
            .map {
                it.data()
            }
    }


    fun getBannersDetail(id: Int): Observable<GetBannersListQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(GetBannersListQuery.builder().id(id).build()))
            .map {
                it.data()
            }
    }

    fun getDeliveryOptionsDetail(
        sku: String,
        qty: Int
    ): Observable<ProductDeliveryOptionsQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                ProductDeliveryOptionsQuery.builder().sku(sku).qty(qty).build()
            )
        ).map {
            it.data()
        }
    }

    fun getFrequentBroughtDetail(sku: List<Int>?): Observable<FrequentlyBoughtTogetherQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                FrequentlyBoughtTogetherQuery.builder().id(sku!!).build()
            )
        ).map {
            it.data()
        }
    }

    fun getCategoryListDetail(categoryId: String?): Observable<GetMenuCategoryListQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                GetMenuCategoryListQuery.builder().categoryid(categoryId!!).build()
            )
        ).map {
            it.data()
        }
    }

    fun getShopByImageBrands(valueId: String?): Observable<ShopByBrandImageQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                ShopByBrandImageQuery.builder().categoryid(valueId).build()
            )
        ).map {
            it.data()
        }
    }

    fun getShopBrandsImagesList(
        atribute_code: String,
        listValue: List<String>
    ): Observable<GetBrandImageListQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                GetBrandImageListQuery.builder().id(listValue).atribute_code(atribute_code).build()
            )
        ).map {
            it.data()
        }
    }

    fun getShowByBrandsDetail(categoryId: String?): Observable<ShopByBrandsQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                ShopByBrandsQuery.builder().id(categoryId).build()
            )
        ).map {
            it.data()
        }
    }

    fun getShowByBrandsByAttributesDetail(categoryId: List<AggregationsInput>?): Observable<ShopByBrandsWithAttributesQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                ShopByBrandsWithAttributesQuery.builder().filters(categoryId).build()
            )
        ).map {
            it.data()
        }
    }

//    fun getBestSellersDetail(): Observable<BestsellersQuery.Data>? {
//        return Rx2Apollo.from(apolloClient.query(BestsellersQuery.builder().build())).map {
//            it.data()
//        }
//    }

    fun getBestSellersDetail(categoryId: Int?): Observable<BestsellersQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                BestsellersQuery.builder().categoryid(categoryId).build()
            )
        ).map {
            it.data()
        }
    }

    fun getCountryDetail(): Observable<GetCountriesListQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(GetCountriesListQuery.builder().build())).map {
            it.data()
        }
    }


    fun getSendOtpDetail(
        email: String,
        mobilenumber: String,
        type: String
    ): Observable<SendOtpMutation.Data>? {
        return Rx2Apollo.from(
            apolloClient.mutate(
                SendOtpMutation.builder().email(email).mobilenumber(mobilenumber).type(type).build()
            )
        ).map {
            it.data()
        }
    }

    fun getVeirfyOtpDetail(
        email: String,
        mobileOtpcode: String,
        emailotp: String
    ): Observable<VerifyOtpMutation.Data>? {
        return Rx2Apollo.from(
            apolloClient.mutate(
                VerifyOtpMutation.builder().email(email).mobileOtpcode(mobileOtpcode)
                    .emailotp(emailotp).build()
            )
        ).map {
            it.data()
        }
    }

    fun getRegisterUserDetail(
        name: String,
        country: Int,
        dob: String,
        mobile_number: String,
        lname: String,
        email: String,
        password: String,
        gender: Int,
        isSubscribe: Boolean
    ): Observable<RegisterUserMutation.Data>? {
        return Rx2Apollo.from(
            apolloClient.mutate(
                RegisterUserMutation.builder()
                    .name(name)
                    .country(country)
                    .dob(dob)
                    .mobile_number(mobile_number)
                    .lname(lname)
                    .email(email)
                    .password(password)
                    .gender(gender)
                    .isSubscribe(isSubscribe)
                    .build()
            )
        ).map {
            it.data()
        }
    }

    fun getLoginTokenDetail(email: String, password: String): Observable<LoginMutation.Data>? {
        return Rx2Apollo.from(
            apolloClient.mutate(
                LoginMutation.builder().email(email).password(password).build()
            )
        ).map {
            it.data()
        }
    }

    fun getResetVerifyEmailOtpDetail(
        email: String,
        otp: String
    ): Observable<ResetVerifyEmailOtpMutation.Data>? {
        return Rx2Apollo.from(
            apolloClient.mutate(
                ResetVerifyEmailOtpMutation.builder().email(email).otp(otp).build()
            )
        ).map {
            it.data()
        }
    }


    fun getResetPwdDetail(email: String, pwd: String): Observable<ResetpasswordMutation.Data>? {
        return Rx2Apollo.from(
            apolloClient.mutate(
                ResetpasswordMutation.builder().email(email).newpassword(pwd).build()
            )
        ).map {
            it.data()
        }
    }

    fun getLOneCategoryDetails(lOneId: String?): Observable<CategoryLOneDetailsQuery.Data>? {
        return Rx2Apollo.from(
            apolloClient.query(
                CategoryLOneDetailsQuery.builder().category(lOneId).build()
            )
        ).map {
            it.data()
        }
    }

    fun getListCategoryData(id: String): Observable<LevelcategoryListQuery.Data>? {
        return Rx2Apollo.from(apolloClient.query(LevelcategoryListQuery.builder().id(id).build()))
            .map {
                it.data()
            }
    }

}


/*
    internal func perforTheBrandGraphQL() {
        let graphQL1 =  ProductsFromCategoryIdQuery(key: self._category?.idString ?? "")

        GraphQL.fetch(query: graphQL1) { result in
                guard let data = try? result.get().data else { return }
            guard let items = (data.products?.aggregations) else { return }

            var aggregationsInputs = [aggregationsInput]()
            for item in items {

                if let itemm = item {
                    let code = itemm.attributeCode
                    let option = itemm.options!.compactMap { $0?.value}
                    aggregationsInputs.append(aggregationsInput(attributeCode: code, options: option))
                }
            }

            let graphQL1 = BrandsWithAttributesQuery(att: aggregationsInputs)
            GraphQL.callArray(query: graphQL1, with: "brandsList", response: Brand()) { (result) in
                    switch result {
                case .success(let response):
                print("Response = \(response)")
                case .failure(let error):
                print("Errpr1 = \(error.localizedDescription)")
            }
            }    }

        }*/
