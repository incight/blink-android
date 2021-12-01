package com.blink.blinkshopping.ui.home

import android.content.Context
import android.graphics.Paint
import android.os.CountDownTimer
import android.view.LayoutInflater
import android.view.View
import android.view.View.GONE
import android.view.View.VISIBLE
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.ItemLinearLayoutManager
import com.blink.blinkshopping.R
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.browseClearClickHandler
import com.blink.blinkshopping.databinding.*
import com.blink.blinkshopping.models.BaseArrayList
import com.blink.blinkshopping.models.ProductItem
import com.blink.blinkshopping.ui.pdp.ExoPlayerFragment
import com.blink.blinkshopping.ui.pdp.OfferPlatesAdapter
import com.blink.blinkshopping.util.Url
import com.blink.blinkshopping.util.Utils
import com.blink.blinkshopping.utils.SortInspireProductList
import com.blink.blinkshopping.utils.SortProductList
import com.bumptech.glide.Glide
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.startup.infobase.utils.Globals.ConvertingList
import kotlinx.android.synthetic.main.layout_category_sliders.view.*
import kotlinx.android.synthetic.main.layout_sliders_viewholder.view.*
import java.util.*
import kotlin.collections.ArrayList


class HomeAdapter(
        var mContext: Context,
        var baseArrayList: MutableList<BaseArrayList>,
        var mainViewModel: HomeViewModel,
        var lifecycleOwner: LifecycleOwner,
        var onItemClick: ItemClickHandler,
        var browseClearClickHandler: browseClearClickHandler
) :
        RecyclerView.Adapter<RecyclerView.ViewHolder>(), DealsOfTheDayAdapter.OnItemClickListener,
        ItemClickHandler {

    private val SLIDER_VIEW = 0
    private val ADS_BLOCK = 1
    private val DEALS_OF_THE_DAY = 2
    private val NEW_ARRIVALS = 3
    private val INSPIRED_BROWSING = 4
    private val DYNAMIC_BLOCK = 5
    private val BANNER_VIEW = 6
    private val CATEGORY_SLIDER_VIEW = 7
    private val ADS_IMAGES = 8
    private val BEST_SELLER = 9
    private val BROWSING_HISTORY = 10
    private val OFFER_PLATE = 11


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {
            SLIDER_VIEW -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutSlidersViewholderBinding.inflate(layoutInflater, parent, false)
                return SliderViewHolder(
                        binding
                )
            }
            ADS_BLOCK -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutAdsblockBinding.inflate(layoutInflater, parent, false)
                AdsBlockViewHolder(
                        binding
                )
            }
            DEALS_OF_THE_DAY -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutDealsOfDayBinding.inflate(layoutInflater, parent, false)
                return DealsOfTheDayViewHolder(
                        binding
                )
            }
            NEW_ARRIVALS -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutNewArrivalsBinding.inflate(layoutInflater, parent, false)
                return NewArrivalsViewHolder(
                        binding
                )
            }
            INSPIRED_BROWSING -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutInspiredHistoryBinding.inflate(layoutInflater, parent, false)
                return InspiredBrowsingViewHolder(
                        binding
                )
            }
            BROWSING_HISTORY -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutNewArrivalsBinding.inflate(layoutInflater, parent, false)
                return BrowsingHistoryViewHolder(
                        binding
                )
            }

            DYNAMIC_BLOCK -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutDynamicBlocksBinding.inflate(layoutInflater, parent, false)
                return DynamicBlockViewHolder(
                        binding
                )
            }
            BANNER_VIEW -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutBannersBinding.inflate(layoutInflater, parent, false)
                return BannersViewHolder(
                        binding
                )
            }
            CATEGORY_SLIDER_VIEW -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutCategorySlidersBinding.inflate(layoutInflater, parent, false)
                return CategorySlidersViewHolder(
                        binding
                )
            }
            BEST_SELLER -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutNewArrivalsBinding.inflate(layoutInflater, parent, false)
                return BestSellerViewHolder(
                        binding
                )
            }
            OFFER_PLATE -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutOfferplatesBinding.inflate(layoutInflater, parent, false)
                return OfferPlateViewHolder(
                        binding
                )
            }
            ADS_IMAGES -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutBannersBinding.inflate(layoutInflater, parent, false)
                return BannersViewHolder(
                        binding
                )
            }

            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutBannersBinding.inflate(layoutInflater, parent, false)
                return BannersViewHolder(
                        binding
                )
            }
        }
    }

    override fun getItemCount(): Int {
        return baseArrayList.size
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val baseArray = baseArrayList[position]

        when (holder.itemViewType) {

            SLIDER_VIEW -> {
                if (baseArray.BaseSlider.data != null) {
                    val viewHolder: SliderViewHolder = holder as SliderViewHolder

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
                                    "home"
                            )   //Viewpager Images
                    viewHolder.binding.indicator.setViewPager(viewHolder.itemView.view_pager_offer)
                    viewHolder.binding.indicator.isSnap = true
                    viewHolder.binding.viewPagerOffer.offscreenPageLimit = homeSliderImages!!.size
                    viewHolder.binding.viewPagerOffer.startAutoScroll(5000)
                }
            }

            ADS_BLOCK -> {
                if (baseArray.adsBlocks.data != null) {
                    val viewHolder = holder as AdsBlockViewHolder

                    var staggeredAdapter =
                            StaggeredAdapter(
                                    mContext,
                                    baseArray.adsBlocks.data!!.adsBlocks,
                                    onItemClick
                            )
                    val layoutManager: GridLayoutManager
                    layoutManager = GridLayoutManager(mContext, 2)
                    // if(baseArray.BaseSlider.data!! != null) {
                    // viewHolder.binding.ltAdsblocks.layout(0,-60,0,0);
                    val params = ConstraintLayout.LayoutParams(
                            ConstraintLayout.LayoutParams.WRAP_CONTENT,
                            ConstraintLayout.LayoutParams.WRAP_CONTENT
                    )
                    params.setMargins(0, -80, 0, 0)
                    viewHolder.binding.ltAdsblocks.setLayoutParams(params)
                    //  }

                    viewHolder.binding.rvAdsBlock.layoutManager = layoutManager
                    viewHolder.binding.rvAdsBlock.adapter = staggeredAdapter
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

            NEW_ARRIVALS -> {
                if (baseArray.baseNewArrivals.data != null) {
                    val viewHolder = holder as NewArrivalsViewHolder
                    var skusList_new: ArrayList<String> = arrayListOf()
                    baseArray.baseNewArrivals.data!!.newArrivalsproducts.forEach {
                        skusList_new.add(it.sku)
                    }
                    mainViewModel.getProductsLayoutInfo(skusList_new)
                    val newArrivalsProductsLiveData = mainViewModel.newArrivalsProductsLiveData
                    newArrivalsProductsLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                            .items()!!
                                            .isNotEmpty()
                            ) {
                                viewHolder.binding.rl1.visibility = View.VISIBLE
                                val newArrivalsadapter =
                                        NewArrivalsAdapter(
                                                SortProductList(
                                                        ConvertingList(it).data.products.items,
                                                        skusList_new
                                                ),
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
                                newArrivalsadapter.notifyDataSetChanged()
                            }
                        }
                    })
                }
            }

            INSPIRED_BROWSING -> {
                if (baseArray.baseInspiredHistory.data != null) {
                    val viewHolder = holder as InspiredBrowsingViewHolder
                    var skusList_inspire: ArrayList<String> = arrayListOf()
                    baseArray.baseInspiredHistory.data!!.newArrivalsproducts.forEach {
                        skusList_inspire.add(it.sku)
                    }

                    mainViewModel.getInspiredHistoryProductsLayoutInfo(skusList_inspire)
                    val inspiredHistoryLiveData =
                            mainViewModel.recommendedLiveDataProductsLiveData
                    inspiredHistoryLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                            .items()!!
                                            .isNotEmpty()
                            ) {

                                viewHolder.binding.rl1.visibility = View.VISIBLE
                                val newArrivalsadapter =
                                        NewArrivalsAdapter(
                                                SortInspireProductList(
                                                        ConvertingList(it).data.products.items,
                                                        skusList_inspire
                                                ),
                                                mContext,
                                                onItemClick,
                                                "inspirebrowsing"
                                        )
                                val layoutManager =
                                        ItemLinearLayoutManager(
                                                mContext,
                                                LinearLayoutManager.HORIZONTAL,
                                                false
                                        )
                                viewHolder.binding.rvInspired?.setLayoutManager(layoutManager)
                                viewHolder.binding.rvInspired?.adapter = newArrivalsadapter
                                newArrivalsadapter.notifyDataSetChanged()
                            }
                        }
                    })
                }
            }

            DYNAMIC_BLOCK -> {
                if (baseArray.baseDynamicLinks.data != null) {
                    val viewHolder = holder as DynamicBlockViewHolder
                    var skusList_dynamic: ArrayList<String> = arrayListOf()

                    if (baseArray.baseDynamicLinks.data!!.dynamicBlocks != null) {

                        baseArray.baseDynamicLinks.data!!.dynamicBlocks.get(0).items.forEach {
                            skusList_dynamic.add(it.sku)
                        }
                        viewHolder.binding.dynamicblocks = baseArray.baseDynamicLinks

                        mainViewModel.getDynamicProductsSkuLayoutInfo(skusList_dynamic)
                        val dynamicBlocksProductsLiveData =
                                mainViewModel.dynamicBlocksProductsLiveData
                        dynamicBlocksProductsLiveData?.observe(lifecycleOwner, Observer { it ->
                            if (it is Resource.Success) {
                                if (it.data!!.products()!!.items() != null && it.data.products()!!
                                                .items()!!
                                                .isNotEmpty()
                                ) {
                                    viewHolder.binding.rlDynamic.visibility = View.VISIBLE
                                    val dynamicBlocksAdapter = DynamicBlocksAdapter(
                                            SortProductList(
                                                    ConvertingList(it).data.products.items,
                                                    skusList_dynamic
                                            ),
                                            mContext, onItemClick
                                    )
                                    val layoutManager =
                                            ItemLinearLayoutManager(
                                                    mContext,
                                                    LinearLayoutManager.HORIZONTAL,
                                                    false
                                            )
                                    viewHolder.binding.rvDynamicBlocks?.setLayoutManager(
                                            layoutManager
                                    )
                                    viewHolder.binding.rvDynamicBlocks?.adapter =
                                            dynamicBlocksAdapter
                                    dynamicBlocksAdapter.notifyDataSetChanged()
                                }
                            }
                        })

                    } else {
                        viewHolder.binding.rlDynamic.visibility = View.GONE
                    }
                }
            }

            BANNER_VIEW -> {
                if (baseArray.baseBanners.data != null) {
                    val viewHolder = holder as BannersViewHolder

                    var size = baseArray.baseBanners.data!!.bannerId.size
                    var staggeredAdapter =
                            BannerStaggeredAdapter(
                                    mContext,
                                    baseArray.baseBanners!!.data!!.bannerId!!,
                                    onItemClick
                            )
                    val layoutManager: GridLayoutManager
                    layoutManager = GridLayoutManager(mContext, 2)
                    viewHolder.binding.rvBanners.layoutManager = layoutManager
                    layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                        override fun getSpanSize(position: Int): Int {
                            return if (position == size - 1 && position and 1 == 0) 2 else 1
                        }
                    }
                    viewHolder.binding.rvBanners.setHasFixedSize(true)
                    viewHolder.binding.rvBanners.adapter = staggeredAdapter
                    staggeredAdapter.notifyDataSetChanged()
                }
            }

            CATEGORY_SLIDER_VIEW -> {
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

                        }

                        if (baseArray.mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                                        0
                                ).fixed_type.equals("video")
                        ) {

                            if (viewHolder.binding.videoPlayer.getVisibility() == VISIBLE) {
                                // Its visible
//                                val videoExoPlayerFragment = ExoPlayerFragment(
//                                        "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4", 1, "HomeFrag_Video"
//                                )
//                                (mContext as FragmentActivity).supportFragmentManager
//                                        .beginTransaction()
//                                        .add(R.id.homeVideoFrameLayout, videoExoPlayerFragment)
//                                        .commit()
                                println(" visible")

                                //viewHolder.binding.homeVideoFrameLayout.visibility  = VISIBLE
                            } else {
                                // Either gone or invisible
                                println("not visible")
                            }


                        } else {
                            viewHolder.binding.homeVideoFrameLayout.visibility = GONE
                        }

                        //Subitem
                        if (baseArray.mainCategorySlider.subProductDetails != null) {

                            var adapter =
                                    CategorySliderAdapter(
                                            mContext,
                                            baseArray.mainCategorySlider.subProductDetails!!.items,
                                            mContext.resources.getString(R.string.sub_product)
                                    )
                            /*SortProductListPDP(
                                    baseArray.mainCategorySlider.subProductDetails!!,
                                    skusList
                                )*/
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


//                        viewHolder.binding.videoPlayer.setOnClickListener { v ->
//                            onItemClick.onItemClick(
//                                baseArray.mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
//                                    0
//                                ).video, null, "videoplayer"
//                            )
//                        }
                    }
                }
            }

            ADS_IMAGES -> {
                if (baseArray.baseAdsImages.data != null) {
                    for (i in baseArray.baseAdsImages!!.data!!.adsimages.indices) {
                        val viewHolder = holder as BannersViewHolder
                        var size = baseArray.baseAdsImages.data!!.adsimages.get(i).items.size
                        var adsImagesAdapter =
                                AdsImagesViewAdapter(
                                        mContext,
                                        baseArray.baseAdsImages!!.data!!.adsimages.get(i).items,
                                        onItemClick
                                )
                        val layoutManager: GridLayoutManager
                        layoutManager = GridLayoutManager(mContext, 2)
                        viewHolder.binding.rvBanners.layoutManager = layoutManager
                        layoutManager.spanSizeLookup = object : GridLayoutManager.SpanSizeLookup() {
                            override fun getSpanSize(position: Int): Int {
                                return if (position == size - 1 && position and 1 == 0) 2 else 1
                            }
                        }
                        viewHolder.binding.rvBanners.setHasFixedSize(true)
                        viewHolder.binding.rvBanners.adapter = adsImagesAdapter
                        adsImagesAdapter.notifyDataSetChanged()

                    }

                }
            }

            BEST_SELLER -> {
                if (baseArray.baseBestSeller.data != null) {
                    val viewHolder = holder as BestSellerViewHolder
                    var skusList_best: ArrayList<String> = arrayListOf()
                    baseArray.baseBestSeller.data!!.bestSeller.forEach {
                        skusList_best.add(it.sku)
                    }
                    viewHolder.binding.txtRecommended.text = "Best Seller"

                    mainViewModel.getBestsellersProductsLayoutInfo(skusList_best)
                    val bestsellersLiveDataProductsLiveData =
                            mainViewModel.bestsellersLiveDataProductsLiveData
                    bestsellersLiveDataProductsLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                            .items()!!
                                            .isNotEmpty()
                            ) {
                                viewHolder.binding.rl1.visibility = View.VISIBLE
                                val newArrivalsadapter =
                                        NewArrivalsAdapter(
                                                SortProductList(
                                                        ConvertingList(it).data.products.items,
                                                        skusList_best
                                                ),
                                                mContext,
                                                onItemClick,
                                                "bestseller"
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
                            }
                        }
                    })
                }
            }

            BROWSING_HISTORY -> {
                if (baseArray.baseBrowseHistory != null) {
                    val viewHolder = holder as BrowsingHistoryViewHolder
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


    class SliderViewHolder(layoutSlidersViewholderBinding: LayoutSlidersViewholderBinding) :
            RecyclerView.ViewHolder(layoutSlidersViewholderBinding.root) {
        var binding: LayoutSlidersViewholderBinding

        init {
            this.binding = layoutSlidersViewholderBinding
        }
    }

    class AdsBlockViewHolder(layoutAdsblockBinding: LayoutAdsblockBinding) :
            RecyclerView.ViewHolder(layoutAdsblockBinding.root) {
        var binding: LayoutAdsblockBinding

        init {
            this.binding = layoutAdsblockBinding
        }
    }

    class DealsOfTheDayViewHolder(layoutDealsOfDayBinding: LayoutDealsOfDayBinding) :
            RecyclerView.ViewHolder(layoutDealsOfDayBinding.root) {
        var binding: LayoutDealsOfDayBinding

        init {
            this.binding = layoutDealsOfDayBinding
        }
    }

    class NewArrivalsViewHolder(layoutNewArrivalsBinding: LayoutNewArrivalsBinding) :
            RecyclerView.ViewHolder(layoutNewArrivalsBinding.root) {
        var binding: LayoutNewArrivalsBinding

        init {
            this.binding = layoutNewArrivalsBinding
        }
    }

    class BrowsingHistoryViewHolder(layoutNewArrivalsBinding: LayoutNewArrivalsBinding) :
            RecyclerView.ViewHolder(layoutNewArrivalsBinding.root) {
        var binding: LayoutNewArrivalsBinding

        init {
            this.binding = layoutNewArrivalsBinding
        }
    }

    class InspiredBrowsingViewHolder(layoutInspiredHistoryBinding: LayoutInspiredHistoryBinding) :
            RecyclerView.ViewHolder(layoutInspiredHistoryBinding.root) {
        var binding: LayoutInspiredHistoryBinding

        init {
            this.binding = layoutInspiredHistoryBinding
        }
    }

    class DynamicBlockViewHolder(layoutDynamicBlocksBinding: LayoutDynamicBlocksBinding) :
            RecyclerView.ViewHolder(layoutDynamicBlocksBinding.root) {
        var binding: LayoutDynamicBlocksBinding

        init {
            this.binding = layoutDynamicBlocksBinding
        }
    }

    class BannersViewHolder(layoutBannersBinding: LayoutBannersBinding) :
            RecyclerView.ViewHolder(layoutBannersBinding.root) {
        var binding: LayoutBannersBinding

        init {
            this.binding = layoutBannersBinding
        }
    }

    class CategorySlidersViewHolder(layoutCategorySlidersBinding: LayoutCategorySlidersBinding) :
            RecyclerView.ViewHolder(layoutCategorySlidersBinding.root) {
        var binding: LayoutCategorySlidersBinding

        init {
            this.binding = layoutCategorySlidersBinding
        }
    }

    class BestSellerViewHolder(layoutBestSellerBinding: LayoutNewArrivalsBinding) :
            RecyclerView.ViewHolder(layoutBestSellerBinding.root) {
        var binding: LayoutNewArrivalsBinding

        init {
            this.binding = layoutBestSellerBinding
        }
    }

    class OfferPlateViewHolder(layoutOfferplatesBinding: LayoutOfferplatesBinding) :
            RecyclerView.ViewHolder(layoutOfferplatesBinding.root) {
        var binding: LayoutOfferplatesBinding

        init {
            this.binding = layoutOfferplatesBinding
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (baseArrayList.get(position).type) {
            "sliders" -> SLIDER_VIEW
            "ads_block" -> ADS_BLOCK
            "deal_of_day" -> DEALS_OF_THE_DAY
            "new_arrivals" -> NEW_ARRIVALS
            "inspirebrowsing" -> INSPIRED_BROWSING
            "dynamic" -> DYNAMIC_BLOCK
            "banners" -> BANNER_VIEW
            "category_slider" -> CATEGORY_SLIDER_VIEW
            "ads_images" -> ADS_IMAGES
            "bestseller" -> BEST_SELLER
            "browsing_history" -> BROWSING_HISTORY
            "offerplates" -> OFFER_PLATE
            else -> -1
        }
    }


    override fun onItemClick(position: String, item: ProductItem?, from: String) {
        //settingBottomItemDetails(,item)
    }

    override fun onDodItemClicked(
            sku: String,
            item: ProductItem?,
            binding: LayoutDealsOfDayBinding
    ) {
        settingBottomItemDetails(binding, item)
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
