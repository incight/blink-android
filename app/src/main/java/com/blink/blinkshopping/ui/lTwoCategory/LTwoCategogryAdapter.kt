package com.blink.blinkshopping.ui.lTwoCategory

import android.content.Context
import android.graphics.Paint
import android.os.CountDownTimer
import android.text.Html
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.databinding.*
import com.blink.blinkshopping.models.ProductItem
import com.blink.blinkshopping.models.ltwocategorybasemodel.Base_LTwo_ModelList
import com.blink.blinkshopping.ui.home.*
import com.blink.blinkshopping.ui.lOneCategory.L1ViewModel
import com.blink.blinkshopping.ui.lOneCategory.ShopByBrandsImageAdapter
import com.blink.blinkshopping.ui.pdp.OfferPlatesAdapter
import com.blink.blinkshopping.util.Url
import com.blink.blinkshopping.util.Utils
import com.blink.blinkshopping.utils.SortProductList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.startup.infobase.utils.Globals.ConvertingList
import kotlinx.android.synthetic.main.layout_sliders_viewholder.view.*
import java.util.*
import kotlin.collections.ArrayList

class LTwoCategogryAdapter(
    var mContext: Context,
    var baseLOneList: MutableList<Base_LTwo_ModelList>,
    var mainViewModel: L1ViewModel, //HomeViewModel
    var lifecycleOwner: LifecycleOwner,
    var onItemClick: ItemClickHandler,
    var onL2ProItemClick: L2ProductClickHandler,
    var browseClearClickHandler: browseClearClickHandler
) : RecyclerView.Adapter<RecyclerView.ViewHolder>(), DealsOfTheDayAdapter.OnItemClickListener,
    ItemClickHandler {

    private val SLIDER_VIEW = 0
    private val SUB_CATEGORIES = 1
    private val L3_CATEGORIES = 2
    private val DEALS_OF_THE_DAY = 3
    private val BRANDS = 4
    private val NEW_ARRIVALS = 5
    private val RECOMMENDED_VIEW = 6
    private val DYNAMIC_BLOCK = 7
    private val PRODUCTS_BLOCK = 8
    private val BEST_SELLER = 9
    private val DESCRIPTION = 10
    private val CATEGORY_SLIDER = 11
    private val BROWSING_HISTORY = 12
    private val OFFER_PLATE = 13


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {

            SLIDER_VIEW -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutSlidersViewholderBinding.inflate(layoutInflater, parent, false)
                return SliderViewHolder(binding)

            }

            BROWSING_HISTORY -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutNewArrivalsBinding.inflate(layoutInflater, parent, false)
                return HomeAdapter.BrowsingHistoryViewHolder(
                    binding
                )
            }

            SUB_CATEGORIES -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutL2SubcategoryBinding.inflate(layoutInflater, parent, false)
                return L2SubProdyctViewHolder(binding)
            }

            L3_CATEGORIES -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutL3ProductListBinding.inflate(layoutInflater, parent, false)
                return L3SubProdyctViewHolder(binding)
            }

            DEALS_OF_THE_DAY -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutDealsOfDayBinding.inflate(layoutInflater, parent, false)
                return DealsOfTheDayViewHolder(binding)
            }

            BRANDS -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val shopBrandBinding =
                    ShopByBrnadsImageLayoutBinding.inflate(layoutInflater, parent, false)
                return ShopByBrandViewHolder(shopBrandBinding)
            }

            NEW_ARRIVALS -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutNewArrivalsBinding.inflate(layoutInflater, parent, false)
                return NewArrivalsView(binding)
            }

            RECOMMENDED_VIEW -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutRecommendedBinding.inflate(layoutInflater, parent, false)
                return RecommendedViewHolder(binding)
            }

            DYNAMIC_BLOCK -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutPramotionalsBinding.inflate(layoutInflater, parent, false)
                return DynamicView(binding)
            }

            PRODUCTS_BLOCK -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutRecommendedBinding.inflate(layoutInflater, parent, false)
                return ProductsWithFilterViewHolder(binding)
            }

            BEST_SELLER -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = BestSellerLayoutBinding.inflate(layoutInflater, parent, false)
                return BestSellerView(binding)
            }

            DESCRIPTION -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = ItemDescriptionBinding.inflate(layoutInflater, parent, false)
                return DescriptionView(binding)
            }

            CATEGORY_SLIDER -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutCategorySlidersBinding.inflate(layoutInflater, parent, false)
                return CategorySlidersViewHolder(binding)
            }

            OFFER_PLATE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutOfferplatesBinding.inflate(layoutInflater, parent, false)
                return OfferPlateViewHolder(binding)
            }

            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutSlidersViewholderBinding.inflate(layoutInflater, parent, false)
                return SliderViewHolder(binding)
            }

        }
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val baseArray = baseLOneList[position]

        when (holder.itemViewType) {
            SLIDER_VIEW -> {
                val viewHolder: SliderViewHolder = holder as SliderViewHolder

                if (baseArray.BaseSlider.data != null) {

                    val homeSliderIds: ArrayList<Int?> = ArrayList<Int?>()
                    val homeSliderImages: ArrayList<String> = ArrayList<String>()
                    val homeSliderTitles: ArrayList<String> = ArrayList<String>()
                    baseArray.BaseSlider.data!!.sliderById.get(0).items.forEach {
                        homeSliderIds.add(it.banner_id)
                        homeSliderImages.add(it.image)
                        homeSliderTitles.add(it.title)
                    }

                    viewHolder.binding.viewPagerOffer.adapter =
                        SlidersViewPagerAdapter(
                            homeSliderIds,
                            homeSliderImages,
                            homeSliderTitles,
                            onItemClick,
                            "LOne"
                        )   //Viewpager Images
                    viewHolder.binding.indicator.setViewPager(viewHolder.itemView.view_pager_offer)
                    viewHolder.binding.indicator.isSnap = true
                    viewHolder.binding.viewPagerOffer.offscreenPageLimit = homeSliderImages!!.size
                    viewHolder.binding.viewPagerOffer.startAutoScroll(5000)
                } else {
                    viewHolder.binding.slidersLt.visibility = View.GONE
//                    val newPosition = viewHolder.getAdapterPosition()
//                    baseLOneList.removeAt(newPosition)
//                    notifyItemRemoved(newPosition)
//                    notifyItemRangeChanged(newPosition, baseLOneList.size)
                }
            }

            SUB_CATEGORIES -> {
                if (baseArray.l2ProductList != null) {

                    val gridHolder: L2SubProdyctViewHolder = holder as L2SubProdyctViewHolder
                    val children = baseArray.l2ProductList
                    val id = baseArray.id.toInt()

                    val itemListwise = HashMap<String, String>()

                    itemListwise.put("sliders", SLIDER_VIEW.toString())
                    itemListwise.put("L3products", L3_CATEGORIES.toString())
                    itemListwise.put("brands", BRANDS.toString())
                    itemListwise.put("new_arrivals", NEW_ARRIVALS.toString())
                    itemListwise.put("inspired_browsing_history", RECOMMENDED_VIEW.toString())
                    itemListwise.put("best_seller", BEST_SELLER.toString())


                    val ltCateAdapter = LTwoCateAdapter(
                        mContext,
                        baseArray.l2ProductList!!,
                        onL2ProItemClick,
                        "from_L2_Products_click",
                        itemListwise,
                        id,
                        gridHolder.gridBinding.rvcCategoryDetails
                    )
                    gridHolder.gridBinding.rvcCategoryDetails.adapter = ltCateAdapter
                    ltCateAdapter.notifyDataSetChanged()

//                    "products" -> L3_CATEGORIES
//                    "brands" -> BRANDS
//                    "new_arrivals" -> NEW_ARRIVALS
//                    "inspired_browsing_history" -> RECOMMENDED_VIEW
//                    "best_seller" -> BEST_SELLER

                }
            }

            L3_CATEGORIES -> {
                if (baseArray.l3ProductList != null) {

                    val gridHolder: L3SubProdyctViewHolder = holder as L3SubProdyctViewHolder
                    val children = baseArray.l3ProductList

                    val l3adapter = L3SubCategoryAdapter(mContext, baseArray.l3ProductList!!)
                    gridHolder.gridBinding.rvlthree.adapter = l3adapter
                    l3adapter.notifyDataSetChanged()

                }
            }

            DEALS_OF_THE_DAY -> {
                if (baseArray.baseDailyDealsproducts.data != null) {
                    val viewHolder = holder as DealsOfTheDayViewHolder
                    var skusList_deals: ArrayList<String> = arrayListOf()
                    baseArray.baseDailyDealsproducts.data!!.dailyDealsproducts.forEach {
                        skusList_deals.add(it.sku)
                    }
                    mainViewModel.getDailyProductsLayoutInfo(skusList_deals)
                    val dailyProductsLiveData = mainViewModel.dealsOfTheDayProductsLiveData

                    dailyProductsLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                    .items()!!.isNotEmpty()
                            ) {
                                viewHolder.binding.product =
                                    ConvertingList(it).data.products.items.get(0)

                                val dailyAdapter = DealsOfTheDayAdapter(
                                    SortProductList(
                                        ConvertingList(it).data.products.items,
                                        skusList_deals
                                    ),
                                    mContext, this, onItemClick, viewHolder.binding
                                )
                                val layoutManager =
                                    LinearLayoutManager(
                                        mContext,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                viewHolder.binding.rvDealsOfTheDay.setLayoutManager(layoutManager)
                                viewHolder.binding.rvDealsOfTheDay.adapter = dailyAdapter
                                dailyAdapter.notifyDataSetChanged()

                                settingBottomItemDetails(
                                    viewHolder.binding,
                                    ConvertingList(it).data.products.items.get(0)
                                )

                            }

                        }
                    })

                    viewHolder.binding.imgIncrease.setOnClickListener(View.OnClickListener {
                        var count: Int =
                            java.lang.String.valueOf(viewHolder.binding.tvQuantity.getText())
                                .toInt()
                        count++
                        viewHolder.binding.tvQuantity.setText("" + count)
                    })

                    viewHolder.binding.imgDecrease.setOnClickListener(View.OnClickListener {
                        var count: Int =
                            java.lang.String.valueOf(viewHolder.binding.tvQuantity.getText())
                                .toInt()
                        if (count == 1) {
                            viewHolder.binding.tvQuantity.setText("1")
                        } else {
                            count -= 1
                            viewHolder.binding.tvQuantity.setText("" + count)
                        }
                    })

                    initCountDownTimer(viewHolder.binding)
                }
            }

            BRANDS -> {
                val shopByBrandViewHolder: ShopByBrandViewHolder = holder as ShopByBrandViewHolder

                if (baseArray.brandList.data != null) {
                    val brand = baseArray.brandList
                    val layoutManager =
                        LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                    shopByBrandViewHolder.shopByBrandhBind.rvShopByBrands.layoutManager =
                        layoutManager

                    val brandsImageAdapter =
                        ShopByBrandsImageAdapter(mContext, brand.data!!.brandsList)
                    shopByBrandViewHolder.shopByBrandhBind.rvShopByBrands.adapter =
                        brandsImageAdapter
                } else {
                    shopByBrandViewHolder.shopByBrandhBind.txtshopByBrabds.visibility = View.GONE
                }

            }

            NEW_ARRIVALS -> {
                if (baseArray.baseNewArrivals.data != null) {
                    val viewHolder = holder as NewArrivalsView
                    var skusList: ArrayList<String> = arrayListOf()
                    baseArray.baseNewArrivals.data!!.newArrivalsproducts.forEach { it ->
                        skusList.add(it.sku)
                    }
                    mainViewModel.getProductsLayoutInfo(skusList)
                    val newArrivalsProductsLiveData = mainViewModel.newArrivalsProductsLiveData
                    newArrivalsProductsLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                    .items()!!
                                    .isNotEmpty()
                            ) {
                                val newArrivalsadapter =
                                    NewArrivalsAdapter(
                                        ConvertingList(it).data.products.items,
                                        mContext,
                                        onItemClick,
                                        "new_arrivals"
                                    )
                                val layoutManager =
                                    ItemLinearLayoutManager(
                                        mContext,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                viewHolder.binding.rvNewArrivals?.setLayoutManager(layoutManager)
                                viewHolder.binding.rvNewArrivals?.adapter = newArrivalsadapter

                                viewHolder.binding.rl1.visibility = View.VISIBLE
                                newArrivalsadapter.notifyDataSetChanged()
                            }
                        }
                    })
                }
            }

            RECOMMENDED_VIEW -> {
                if (baseArray.baseRecommended.data != null) {

                    val viewHolder = holder as RecommendedViewHolder
                    var skusList: ArrayList<String> = arrayListOf()
                    baseArray.baseRecommended.data!!.newArrivalsproducts.forEach {
                        skusList.add(it.sku)
                    }
                    mainViewModel.getRecommendedProductsLayoutInfo(skusList)
                    val recommendedLiveDataProductsLiveData =
                        mainViewModel.recommendedLiveDataProductsLiveData
                    recommendedLiveDataProductsLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                    .items()!!
                                    .isNotEmpty()
                            ) {
                                val newArrivalsadapter =
                                    NewArrivalsAdapter(
                                        ConvertingList(it).data.products.items,
                                        mContext,
                                        onItemClick,
                                        "recommended"
                                    )
                                val layoutManager =
                                    ItemLinearLayoutManager(
                                        mContext,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                viewHolder.binding.rvRecommended?.setLayoutManager(layoutManager)
                                viewHolder.binding.rvRecommended?.adapter = newArrivalsadapter
                                newArrivalsadapter.notifyDataSetChanged()
                            }
                        }
                    })
                }
            }

            BROWSING_HISTORY -> {
                if (baseArray.baseBrowseHistory != null) {
                    val viewHolder = holder as HomeAdapter.BrowsingHistoryViewHolder

                    mainViewModel.getBrosingHistoryProductsListInfo(baseArray.baseBrowseHistory)
                    val brosingHistoryLiveDataProductsLiveData =
                        mainViewModel.brosingHistoryLiveDataProductsLiveData
                    brosingHistoryLiveDataProductsLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                    .items()!!
                                    .isNotEmpty()
                            ) {
                                viewHolder.binding.rl1.visibility = View.VISIBLE
                                viewHolder.binding.txtRecommended.text = "Browsing History"
                                viewHolder.binding.txtLayout.text = "Clear"
                                viewHolder.binding.txtLayout.setTextColor(
                                    mContext.resources.getColor(
                                        R.color.price_blue_color
                                    )
                                );

                                val newArrivalsadapter =
                                    NewArrivalsAdapter(
                                        SortProductList(
                                            ConvertingList(it).data.products.items,
                                            baseArray.baseBrowseHistory!!
                                        ),
                                        mContext,
                                        onItemClick,
                                        "browsing_history"
                                    )

                                val layoutManager =
                                    ItemLinearLayoutManager(
                                        mContext,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                viewHolder.binding.rvNewArrivals?.setLayoutManager(layoutManager)
                                viewHolder.binding.rvNewArrivals?.adapter = newArrivalsadapter
                                newArrivalsadapter.notifyDataSetChanged()

                                var skusClearList: ArrayList<Int> = arrayListOf()
                                ConvertingList(it).data.products.items.iterator().forEach {
                                    skusClearList.add(Integer.parseInt(it.id!!))
                                }

                                val itemListwise = HashMap<String, String>()
                                itemListwise.put("browsing_history", BROWSING_HISTORY.toString())

                                viewHolder.binding.txtLayout.setOnClickListener {
                                    browseClearClickHandler.browseClearClickHandler(
                                        skusClearList,
                                        itemListwise
                                    )
                                }

                            }
                        }
                    })
                }
            }

            DYNAMIC_BLOCK -> {
                if (baseArray.baseDynamicLinks.data != null) {

                    val viewHolder = holder as DynamicView
                    var skusList: ArrayList<String> = arrayListOf()

                    baseArray.baseDynamicLinks.data!!.dynamicBlocks[0].items.forEach {
                        skusList.add(it.sku)
                    }

                    mainViewModel.getDynamicProductsSkuLayoutInfo(skusList)
                    val dynamicBlocksProductsLiveData = mainViewModel.dynamicBlocksProductsLiveData
                    dynamicBlocksProductsLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                    .items()!!
                                    .isNotEmpty()
                            ) {
                                val newArrivalsadapter =
                                    NewArrivalsAdapter(
                                        ConvertingList(it).data.products.items,
                                        mContext,
                                        onItemClick,
                                        "dynamic_blocks"
                                    )
                                val layoutManager =
                                    ItemLinearLayoutManager(
                                        mContext,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                viewHolder.binding.rvBestSellers?.setLayoutManager(layoutManager)
                                viewHolder.binding.rvBestSellers?.adapter = newArrivalsadapter
                                newArrivalsadapter.notifyDataSetChanged()
                            }
                        }
                    })
                }
            }

            PRODUCTS_BLOCK -> {
                if (baseArray.baseProductsList!!.data != null) {

                    val viewHolder = holder as ProductsWithFilterViewHolder

                    val productsWithFilterAdapter =
                        ProductsWithFilterAdapter(
                            baseArray.baseProductsList!!.data!!.products.items,
                            mContext,
                            onItemClick,
                            "products_with_filter"
                        )
                    val layoutManager =
                        ItemLinearLayoutManager(
                            mContext,
                            LinearLayoutManager.HORIZONTAL,
                            false
                        )
                    viewHolder.binding.txtRecommended?.text = "Products"
                    viewHolder.binding.rvRecommended?.setLayoutManager(layoutManager)
                    viewHolder.binding.rvRecommended?.adapter = productsWithFilterAdapter
                    productsWithFilterAdapter.notifyDataSetChanged()

                }
            }

            BEST_SELLER -> {
                if (baseArray.baseBestSeller.data != null) {

                    val viewHolder = holder as BestSellerView
                    var skusList: ArrayList<String> = arrayListOf()
                    baseArray.baseBestSeller.data!!.bestSeller.forEach {
                        skusList.add(it.sku)
                    }
                    mainViewModel.getBestsellersProductsLayoutInfo(skusList)
                    val bestsellersLiveDataProductsLiveData =
                        mainViewModel.bestsellersLiveDataProductsLiveData
                    bestsellersLiveDataProductsLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                    .items()!!
                                    .isNotEmpty()
                            ) {
                                val newArrivalsadapter =
                                    NewArrivalsAdapter(
                                        ConvertingList(it).data.products.items,
                                        mContext,
                                        onItemClick,
                                        "best_seller"
                                    )
                                val layoutManager =
                                    ItemLinearLayoutManager(
                                        mContext,
                                        LinearLayoutManager.HORIZONTAL,
                                        false
                                    )
                                viewHolder.binding.rvBestSellers?.setLayoutManager(layoutManager)
                                viewHolder.binding.rvBestSellers?.adapter = newArrivalsadapter
                                newArrivalsadapter.notifyDataSetChanged()
                            }
                        }
                    })
                }

            }

            DESCRIPTION -> {
                if (baseArray.baseDescription != null) {
                    val viewHolder = holder as DescriptionView
//                    viewHolder.binding.txtDescription.text = baseArray.baseDescription
                    viewHolder.binding.txtDescription.text =
                        Html.fromHtml(
                            Html.fromHtml(baseArray.baseDescription)
                                .toString()
                        ).toString()
                }
            }

            CATEGORY_SLIDER -> {

                val viewHolder = holder as CategorySlidersViewHolder

                viewHolder.binding.category = baseArray.mainCategorySlider.baseCategorySliders

                if (baseArray.mainCategorySlider.baseCategorySliders != null) {
                    if (baseArray.mainCategorySlider.baseCategorySliders!!.data != null) {

                        if (baseArray.mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                                0
                            ).isHeroSku()
                        ) {
                            var mainskuArrayList: ArrayList<String> = arrayListOf()
                            mainskuArrayList.add(
                                baseArray.mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                                    0
                                ).hero_sku
                            )
                            mainViewModel.getCategorySliderProductsSkuInfo1(mainskuArrayList)
                            mainViewModel.categorySliderProductsLiveData.observe(
                                lifecycleOwner,
                                Observer {
                                    if (it is Resource.Success) {
                                        if (it.data!!.products()!!
                                                .items() != null && it.data.products()!!
                                                .items()!!
                                                .isNotEmpty()
                                        ) {
                                            viewHolder.binding.product =
                                                ConvertingList(it).data.products.items.get(0)

                                            viewHolder.binding.txtdodActualPrice.setPaintFlags(
                                                viewHolder.binding.txtdodActualPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG
                                            )

                                        }
                                    }
                                })

                            viewHolder.binding.imgIncrease.setOnClickListener(View.OnClickListener {
                                var count: Int =
                                    java.lang.String.valueOf(viewHolder.binding.tvQuantity.getText())
                                        .toInt()
                                count++
                                viewHolder.binding.tvQuantity.setText("" + count)
                            })

                            viewHolder.binding.imgDecrease.setOnClickListener(View.OnClickListener {
                                var count: Int =
                                    java.lang.String.valueOf(viewHolder.binding.tvQuantity.getText())
                                        .toInt()
                                if (count == 1) {
                                    viewHolder.binding.tvQuantity.setText("1")
                                } else {
                                    count -= 1
                                    viewHolder.binding.tvQuantity.setText("" + count)
                                }
                            })

                        } else {
                            viewHolder.binding.ltCategory.visibility = View.GONE
                        }


                        viewHolder.binding.videoPlayer.setOnClickListener { v ->
                            onItemClick.onItemClick(
                                baseArray.mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                                    0
                                ).video,
                                null,
                                "videoplayer"
                            )
                        }

                        //Subitem
                        if (baseArray.mainCategorySlider.subProductDetails != null) {

                            var adapter =
                                CategorySliderAdapter(
                                    mContext,
                                    baseArray.mainCategorySlider.subProductDetails!!.items,
                                    mContext.resources.getString(R.string.sub_product)
                                )
                            val layoutManager =
                                ItemLinearLayoutManager(
                                    mContext,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            viewHolder.binding.rcCategory.setLayoutManager(layoutManager)
                            viewHolder.binding.rcCategory.adapter = adapter
                        }

                        //SubSubitem
                        if (baseArray.mainCategorySlider.subSubProductDetails != null) {
                            var adapter =
                                CategorySliderAdapter(
                                    mContext,
                                    baseArray.mainCategorySlider.subSubProductDetails!!.items,
                                    mContext.resources.getString(R.string.sub_sub_product)
                                )
                            val layoutManager =
                                LinearLayoutManager(
                                    mContext,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            viewHolder.binding.rcSubCategory.setLayoutManager(layoutManager)
                            viewHolder.binding.rcSubCategory.adapter = adapter
                        }
                    }
                }
            }

            OFFER_PLATE -> {
                if (baseArray.baseOfferPlate.data != null) {
                    val viewHolder = holder as OfferPlateViewHolder
                    if (baseArray.baseOfferPlate.data!!.offerplates != null && baseArray.baseOfferPlate.data!!.offerplates.isNotEmpty()
                    ) {
                        val offerPlatesAdapter =
                            OfferPlatesAdapter(
                                mContext, baseArray.baseOfferPlate.data!!, /*onItemClick,*/
                                "offerplates"
                            )
                        val layoutManager =
                            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                        viewHolder.binding.rvOfferPlates?.setLayoutManager(layoutManager)
                        viewHolder.binding.rvOfferPlates?.adapter = offerPlatesAdapter
                        offerPlatesAdapter.notifyDataSetChanged()
                    }
                }
            }

        }


    }

    override fun onDodItemClicked(
        sku: String,
        item: ProductItem?,
        binding: LayoutDealsOfDayBinding
    ) {

    }

    override fun onItemClick(position: String, item: ProductItem?, from: String) {

    }

    override fun getItemCount(): Int = baseLOneList.size

    override fun getItemViewType(position: Int): Int {
        return when (baseLOneList[position].type) {
            "sliders" -> SLIDER_VIEW
            "sub_categories" -> SUB_CATEGORIES
            "L3products" -> L3_CATEGORIES
            "deal_of_day" -> DEALS_OF_THE_DAY
            "brands" -> BRANDS
            "new_arrivals" -> NEW_ARRIVALS
            "inspired_browsing_history" -> RECOMMENDED_VIEW
            "dynamic_blocks" -> DYNAMIC_BLOCK
            "products" -> PRODUCTS_BLOCK
            "best_seller" -> BEST_SELLER
            "description" -> DESCRIPTION
            "category_slider" -> CATEGORY_SLIDER
            "browsing_history" -> BROWSING_HISTORY
            "offerplates" -> OFFER_PLATE
            else -> -1
        }
    }


    class SliderViewHolder(layoutSlidersViewholderBinding: LayoutSlidersViewholderBinding) :
        RecyclerView.ViewHolder(layoutSlidersViewholderBinding.root) {
        var binding: LayoutSlidersViewholderBinding

        init {
            this.binding = layoutSlidersViewholderBinding
        }
    }

    class L2SubProdyctViewHolder(layoutBinding: LayoutL2SubcategoryBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {
        var gridBinding: LayoutL2SubcategoryBinding

        init {
            this.gridBinding = layoutBinding
        }
    }

    class L3SubProdyctViewHolder(layoutBinding: LayoutL3ProductListBinding) :
        RecyclerView.ViewHolder(layoutBinding.root) {
        var gridBinding: LayoutL3ProductListBinding

        init {
            this.gridBinding = layoutBinding
        }
    }


    class DealsOfTheDayViewHolder(layoutDealsOfDayBinding: LayoutDealsOfDayBinding) :
        RecyclerView.ViewHolder(layoutDealsOfDayBinding.root) {
        var binding: LayoutDealsOfDayBinding

        init {
            this.binding = layoutDealsOfDayBinding
        }
    }

    class CategorySlidersViewHolder(layoutCategorySlidersBinding: LayoutCategorySlidersBinding) :
        RecyclerView.ViewHolder(layoutCategorySlidersBinding.root) {
        var binding: LayoutCategorySlidersBinding

        init {
            this.binding = layoutCategorySlidersBinding
        }
    }

    class RecommendedViewHolder(layoutRecommendedBinding: LayoutRecommendedBinding) :
        RecyclerView.ViewHolder(layoutRecommendedBinding.root) {
        var binding: LayoutRecommendedBinding

        init {
            this.binding = layoutRecommendedBinding
        }
    }

    class ProductsWithFilterViewHolder(layoutRecommendedBinding: LayoutRecommendedBinding) :
        RecyclerView.ViewHolder(layoutRecommendedBinding.root) {
        var binding: LayoutRecommendedBinding

        init {
            this.binding = layoutRecommendedBinding
        }
    }


    class ShopByBrandViewHolder(shopBrandBinding: ShopByBrnadsImageLayoutBinding) :
        RecyclerView.ViewHolder(shopBrandBinding.root) {
        var shopByBrandhBind: ShopByBrnadsImageLayoutBinding

        init {
            this.shopByBrandhBind = shopBrandBinding
        }
    }

    class NewArrivalsView(layoutNewArrivalsBinding: LayoutNewArrivalsBinding) :
        RecyclerView.ViewHolder(layoutNewArrivalsBinding.root) {
        var binding: LayoutNewArrivalsBinding = layoutNewArrivalsBinding

    }

    class BestSellerView(layoutNewArrivalsBinding: BestSellerLayoutBinding) :
        RecyclerView.ViewHolder(layoutNewArrivalsBinding.root) {
        var binding: BestSellerLayoutBinding = layoutNewArrivalsBinding

    }

    class DescriptionView(itemDescriptionBinding: ItemDescriptionBinding) :
        RecyclerView.ViewHolder(itemDescriptionBinding.root) {
        var binding: ItemDescriptionBinding = itemDescriptionBinding

    }

    class DynamicView(layoutPramotionalsBinding: LayoutPramotionalsBinding) :
        RecyclerView.ViewHolder(layoutPramotionalsBinding.root) {
        var binding: LayoutPramotionalsBinding = layoutPramotionalsBinding

    }

    class OfferPlateViewHolder(layoutOfferplatesBinding: LayoutOfferplatesBinding) :
        RecyclerView.ViewHolder(layoutOfferplatesBinding.root) {
        var binding: LayoutOfferplatesBinding

        init {
            this.binding = layoutOfferplatesBinding
        }
    }

    fun settingBottomItemDetails(binding: LayoutDealsOfDayBinding, item: ProductItem?) {
        var product = item
        var path = ""
        if (product!!.image!!.url!!.contains("https")) {
            path = ""
        } else {
            path = Url
        }
        Glide.with(mContext)
            .load(path + product.image!!.url)
            .transition(DrawableTransitionOptions.withCrossFade())
            .into(binding.dodImageMain)

        binding.txtDodName.text = product.name

        binding.txtdodActualPrice.setPaintFlags(binding.txtdodActualPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
        binding.txtdodActualPrice.text =
            Utils.regularPrice(product.price_range!!.minimum_price!!.regular_price!!.value.toString())
        binding.txtdodOfferedPrice.text =
            Utils.finalPrice(product.price_range!!.minimum_price!!.final_price!!.value.toString())
        if (Utils.checkPriceEquality(
                product.price_range!!.minimum_price!!.regular_price!!.value.toString(),
                product.price_range!!.minimum_price!!.final_price!!.value.toString()
            )
        ) {
            binding.txtdodActualPrice.visibility = View.INVISIBLE
        } else {
            binding.txtdodActualPrice.visibility = View.VISIBLE
        }

        binding.addToCart.setOnClickListener { v ->
            onItemClick.onItemClick(product.sku, null, "deals_of_the_day")
        }

    }


    var contDownTimer: CountDownTimer? = null

    fun initCountDownTimer(binding: LayoutDealsOfDayBinding) {
        val c: Calendar = Calendar.getInstance()
        c.add(Calendar.DAY_OF_MONTH, 1)
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)

        if (c.getTimeInMillis() > System.currentTimeMillis()) {
            contDownTimer =
                object : CountDownTimer(c.getTimeInMillis() - System.currentTimeMillis(), 1000) {
                    override fun onTick(millisUntilFinished: Long) {
                        timerBinding(millisUntilFinished, binding)
                    }

                    override fun onFinish() {
                        countDownFinished("In-progress", binding)
                    }
                }.start()
        } else {
            countDownFinished("Done", binding)
        }
    }

    fun countDownFinished(message: String, binding: LayoutDealsOfDayBinding) {
        binding.include.tvDays.text = message
        binding.include.tvDaysText.text = ""
        binding.include.tvHours.text = ""
        binding.include.tvHoursText.text = ""
        binding.include.tvMins.text = ""
        binding.include.tvMinsText.text = ""
        binding.include.tvSecs.text = ""
        binding.include.tvSecsText.text = ""
    }

    fun timerBinding(millisUntilFinished: Long, binding: LayoutDealsOfDayBinding) {
        var days = (millisUntilFinished / (60 * 60 * 24 * 1000))
        var millisAfterDays = millisUntilFinished - (days * 60 * 60 * 24 * 1000)
        val hours = (millisAfterDays / (1000 * 60 * 60) % 24)
        var millisAfterHours = millisAfterDays - (hours * 1000 * 60 * 60) % 24
        val minutes = (millisAfterHours / (1000 * 60) % 60)
        var millisAfterMinutes = millisAfterHours - (minutes * 1000 * 60) % 60
        val seconds = (millisAfterMinutes / 1000) % 60

        if (days.toString().equals("0")) {
            binding!!.include.tvDays.text = "00"
            binding!!.include.tvDaysText.text = " Days "
        } else {
            binding!!.include.tvDays.text = days.toString()
            binding!!.include.tvDaysText.text = " Days "
        }
        if (hours.toString().equals("0")) {
            binding!!.include.tvHours.text = ""
            binding!!.include.tvHoursText.text = ""
        } else {
            binding!!.include.tvHours.text = hours.toString()
            binding!!.include.tvHoursText.text = " Hours "
        }
        if (minutes.toString().equals("0")) {
            binding!!.include.tvMins.text = ""
            binding!!.include.tvMinsText.text = ""
        } else {
            binding!!.include.tvMins.text = minutes.toString()
            binding!!.include.tvMinsText.text = " Mins "
        }
        if (seconds.toString().equals("0")) {
            binding!!.include.tvSecs.text = ""
            binding!!.include.tvSecsText.text = ""
        } else {
            binding!!.include.tvSecs.text = seconds.toString()
            binding!!.include.tvSecsText.text = " Secs "
        }
    }
}




