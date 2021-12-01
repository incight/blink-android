package com.blink.blinkshopping.ui.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.databinding.ActivityNhomeBinding
import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.ui.main.MainActivity
import com.blink.blinkshopping.ui.pdp.PdpFragment
import com.blink.blinkshopping.util.SharedPrefForHistory
import com.blink.blinkshopping.util.SharedStorage
import com.blink.blinkshopping.utils.subSortProductListAtCategorySlider1
import com.blink.blinkshopping.utils.subsubSortProductListAtCategorySlider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.startup.infobase.utils.Globals.ConvertToBanners
import com.startup.infobase.utils.Globals.ConvertToBestSeller
import com.startup.infobase.utils.Globals.ConvertToBlocks
import com.startup.infobase.utils.Globals.ConvertToDealsOfTheDay
import com.startup.infobase.utils.Globals.ConvertToDynamic
import com.startup.infobase.utils.Globals.ConvertToInspired
import com.startup.infobase.utils.Globals.convertToNewArrivals
import com.startup.infobase.utils.Globals.ConvertToSlider
import com.startup.infobase.utils.Globals.ConvertingAdsImageList
import com.startup.infobase.utils.Globals.ConvertingCategoryList
import com.startup.infobase.utils.Globals.ConvertingList
import com.startup.infobase.utils.Globals.ConvertingMenuList
import com.startup.infobase.utils.Globals.ConvertingOfferPlates
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_nhome.*
import java.lang.reflect.Type
import javax.inject.Inject


class HomeFragment : Fragment(), HasSupportFragmentInjector, ItemClickHandler,
    browseClearClickHandler {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: HomeViewModel
    lateinit var binding: ActivityNhomeBinding
    lateinit var adapter: HomeAdapter
    lateinit var megaMenuAdapter: MegaMenuAdapter

    var sharedStorage: SharedStorage? = null
    var sharedStorageHistory: SharedPrefForHistory? = null


    var baseArrayList: MutableList<BaseArrayList> = mutableListOf()
    var categorySliderIndexModel: ArrayList<CategorySliderIndexModel> = arrayListOf()
    var subCatIndexModel: ArrayList<CategorySliderIndexModel> = arrayListOf()
    var subsubCatIndexModel: ArrayList<CategorySliderIndexModel> = arrayListOf()


    var id1: ArrayList<Int> = ArrayList()

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    var mHistoryList: ArrayList<BrowseSavingModel>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_nhome, container, false)
        return binding.root
    }

    companion object {
        private const val From = "from"
        private const val Layout = "layout"
        private const val Category = "category"
        fun newInstance(from: String, layout: String, category: String): HomeFragment? {
            val fragment: HomeFragment =
                HomeFragment()
            val args = Bundle()
            args.putString(From, from)
            args.putString(Layout, layout)
            args.putString(Category, category)
            fragment.setArguments(args)
            return fragment
        }
    }


    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        baseArrayList.clear()
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(HomeViewModel::class.java)
        mainViewModel.getHomeLayout("home", "mobile")
        mainViewModel.getMegaMenuLayout()

        sharedStorage = SharedStorage.getInstance(this@HomeFragment.context!!)
        sharedStorageHistory = SharedPrefForHistory.getInstance(this@HomeFragment.context!!)


        val megaMenuLiveData = mainViewModel.megaMenuLiveData
        val layoutLiveData = mainViewModel.lauyoutLiveData


        binding.data = layoutLiveData
        binding.lifecycleOwner = viewLifecycleOwner

        megaMenuLiveData.observe(viewLifecycleOwner, Observer { it12 ->
            if (it12 is Resource.Success) {

                megaMenuAdapter =
                    MegaMenuAdapter(
                        this@HomeFragment.context!!,
                        ConvertingMenuList(it12).data!!,
                        this
                    )
                val layoutManager1 =
                    LinearLayoutManager(
                        this@HomeFragment.context!!,
                        LinearLayoutManager.HORIZONTAL,
                        false
                    )
                rvMegaMenu?.setLayoutManager(layoutManager1)
                rvMegaMenu?.adapter = megaMenuAdapter

                val item = MegaMenuLink(
                    "All Categories",
                    "https://mgdev2.westeurope.cloudapp.azure.com/"
                )
                megaMenuAdapter.addItem(item)
            }
        })

        adapter =
            HomeAdapter(
                this@HomeFragment.context!!,
                baseArrayList,
                mainViewModel,
                this,
                this, this
            )
        //LinearLayoutManager
        val layoutManager =
            WrapContentLinearLayoutManager(
                this@HomeFragment.context!!,
                LinearLayoutManager.VERTICAL,
                false
            )
        rvForAllViews?.setLayoutManager(layoutManager)
        rvForAllViews?.adapter = adapter


        if (sharedStorage!!.isLogin) {

            val gson = Gson()
            val json: String = sharedStorageHistory!!.getProductClickId()!!


            if (json != null && !json.isEmpty()) {

                val type: Type = object : TypeToken<ArrayList<BrowseSavingModel?>?>() {}.getType()
                mHistoryList = gson.fromJson(json, type)

                var entityFrmLocalHistory: ArrayList<Int> = arrayListOf()
                for (i in mHistoryList!!.indices) {
                    entityFrmLocalHistory.add(Integer.parseInt(mHistoryList!!.get(i).entity_id))
                }

                mainViewModel.addBrosingHistory(entityFrmLocalHistory)
                mainViewModel.browsingHistoryAddLiveData.observe(
                    viewLifecycleOwner,
                    Observer { itBh ->
                        if (itBh is Resource.Success) {
                            if (itBh.data!!.browsingHistoryMutation() != null) {
                                sharedStorageHistory!!.deleteUserData()

                            }
                        }
                    })

            }
        }


        layoutLiveData?.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                apiIdData(it)
            }
        })


    }


    private fun apiIdData(it: Resource.Success<HomeLayoutsQuery.Data>) {

        for (i in it.data!!.layouts()!!.get(0).items()!!.indices) {

            if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("sliders")
            ) {
                val id = Integer.parseInt(
                    it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!.substring(
                        it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                            .lastIndexOf("_") + 1
                    )
                )
                baseArrayList.add(
                    BaseArrayList(
                        "sliders",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                SlidersApiCall(baseArrayList.size - 1, id)
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("ads_block")
            ) {
                baseArrayList.add(
                    BaseArrayList(
                        "ads_block",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                AdsBlockApiCall(baseArrayList.size - 1)
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("deal_of_day")
            ) {
                baseArrayList.add(
                    BaseArrayList(
                        "deal_of_day",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                DealsOfTheDayApiCall(baseArrayList.size - 1)
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("new_arrivals")
            ) {
                baseArrayList.add(
                    BaseArrayList(
                        "new_arrivals",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                NewArrivalsApiCall(baseArrayList.size - 1)

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("inspired_browsing_history")
            ) {

                baseArrayList.add(
                    BaseArrayList(
                        "inspirebrowsing",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )

                InspireBrowsingHistoryApiCall(baseArrayList.size - 1)

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("dynamic_blocks")
            ) {
                val id = Integer.parseInt(
                    it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                        .substring(
                            it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                                .lastIndexOf("_") + 1
                        )
                )
                baseArrayList.add(
                    BaseArrayList(
                        "dynamic",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                DynamicBlocksApiCall(baseArrayList.size - 1, id)
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()!!
                    .equals("banner")
            ) {
                val id = Integer.parseInt(
                    it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                        .substring(
                            it.data!!.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                                .lastIndexOf("_") + 1
                        )
                )
                baseArrayList.add(
                    BaseArrayList(
                        "banners",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                BannersApiCall(baseArrayList.size - 1, id)
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()!!
                    .equals("category_slider")
            ) {
                val id = Integer.parseInt(
                    it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!.substring(
                        it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                            .lastIndexOf("_") + 1
                    )
                )
                baseArrayList.add(
                    BaseArrayList(
                        "category_slider",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                categorySliderIndexModel.add(CategorySliderIndexModel(baseArrayList.size - 1, id))
                subCatIndexModel.add(CategorySliderIndexModel(baseArrayList.size - 1, id))
                subsubCatIndexModel.add(CategorySliderIndexModel(baseArrayList.size - 1, id))

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()!!
                    .equals("ads_images")
            ) {
                val id = Integer.parseInt(
                    it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!.substring(
                        it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                            .lastIndexOf("_") + 1
                    )
                )
                id1.add(id)

                baseArrayList.add(
                    BaseArrayList(
                        "ads_images",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                AdsImagesApiCall(baseArrayList.size - 1, id1)


            } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("best_seller")
            ) {
                baseArrayList.add(
                    BaseArrayList(
                        "bestseller",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                BestSellersApiCall(baseArrayList.size - 1)

            } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("browsing_history")
            ) {
                baseArrayList.add(
                    BaseArrayList(
                        "browsing_history",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                BrowsingHistoryApiCall(baseArrayList.size - 1)
            }
        }

        CategorySlidersApiCall(categorySliderIndexModel)

        baseArrayList.add(
            BaseArrayList(
                "offerplates",
                MainCategorySlider(null, null, null),
                BaseSlider(null),
                BaseAdsBlocks(null),
                BaseDailyDealsproducts(null),
                BaseNewArrivals(null),
                BaseRecommended(null),
                BaseInspiredHistory(null),
                BaseDynamicLinks(null),
                BaseBanners(null),
                BaseBestSeller(null),
                BaseOfferPlate(null),
                BaseAdsImages(null),
                null
            )
        )
        OfferPlatesApi(baseArrayList.size - 1)


    }


    fun SlidersApiCall(index: Int, id: Int) {
        mainViewModel.getSlidersLayoutInfo(id, null, null)
        val slidersLiveData = mainViewModel.slidersLiveData
        slidersLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                if (ConvertToSlider(it).data!!.sliderById != null && ConvertToSlider(it).data!!.sliderById.isNotEmpty()) {
                    baseArrayList.set(
                        index, BaseArrayList(
                            "sliders",
                            MainCategorySlider(null, null, null),
                            ConvertToSlider(it),
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseInspiredHistory(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            BaseOfferPlate(null),
                            BaseAdsImages(null),
                            null
                        )
                    )
                    adapter.notifyItemChanged(index)
                }

            }
        })
    }

    fun AdsBlockApiCall(index: Int) {
        mainViewModel.getAdsBlockLayoutInfo()
        val adsBlockLiveData = mainViewModel.adsBlockLiveData
        adsBlockLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                if (ConvertToBlocks(it).data!!.adsBlocks != null && ConvertToBlocks(it).data!!.adsBlocks.isNotEmpty()) {
                    baseArrayList.set(
                        index,
                        BaseArrayList(
                            "ads_block",
                            MainCategorySlider(null, null, null),
                            BaseSlider(null),
                            ConvertToBlocks(it),
                            BaseDailyDealsproducts(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseInspiredHistory(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            BaseOfferPlate(null),
                            BaseAdsImages(null),
                            null
                        )
                    )
                    adapter.notifyItemChanged(index)
                }
            }
        })
    }

    fun DealsOfTheDayApiCall(index: Int) {
        mainViewModel.getDealsOfTheDayLayoutInfo()
        //val dealsOfTheDayLiveData = mainViewModel.dealsOfTheDayLiveData
        mainViewModel.dealsOfTheDayLiveData.observe(viewLifecycleOwner, Observer { it3 ->
            if (it3 is Resource.Success) {
                baseArrayList.set(
                    index,
                    BaseArrayList(
                        "deal_of_day",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        ConvertToDealsOfTheDay(it3),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                adapter.notifyItemChanged(index)
            }
        })
    }

    fun NewArrivalsApiCall(index: Int) {
        mainViewModel.getNewArrivalsInfo(null)
        //val newsArrivalsLiveData = mainViewModel.newArrivalsLiveData
        mainViewModel.newArrivalsLiveData.observe(viewLifecycleOwner, Observer { it5 ->
            if (it5 is Resource.Success) {

                baseArrayList.set(
                    index,
                    BaseArrayList(
                        "new_arrivals",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        convertToNewArrivals(it5),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                adapter.notifyItemChanged(index)

            }
        })
    }

    fun InspireBrowsingHistoryApiCall(index: Int) {
        mainViewModel.getInspiredHistoryInfo(null)
        //val recommendedLiveData = mainViewModel.recommendedLiveData
        mainViewModel.recommendedLiveData.observe(viewLifecycleOwner, Observer { it6 ->
            if (it6 is Resource.Success) {
                baseArrayList.set(
                    index,
                    BaseArrayList(
                        "inspirebrowsing",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        ConvertToInspired(it6),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                adapter.notifyItemChanged(index)
            }
        })
    }

    fun BestSellersApiCall(index: Int) {
        mainViewModel.getBestsellersInfo(null)
        mainViewModel.bestsellersLiveData.observe(viewLifecycleOwner, Observer { it8 ->
            if (it8 is Resource.Success) {
                baseArrayList.set(
                    index,
                    BaseArrayList(
                        "bestseller",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        ConvertToBestSeller(it8),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                adapter.notifyDataSetChanged()
            }
        })
    }

    fun BrowsingHistoryApiCall(index: Int) {

        if (sharedStorage!!.isLogin) {

            mainViewModel.getBrosingHistory()
            mainViewModel.browsingHistoryGetLiveData.observe(viewLifecycleOwner, Observer { itBh ->

                if (itBh is Resource.Success) {

                    if (itBh.data!!.browsingHistory() != null && !itBh.data!!.browsingHistory()!!
                            .isEmpty()
                    ) {

                        var skusList_History: ArrayList<String> = arrayListOf()
                        itBh.data!!.browsingHistory()!!.forEach {
                            skusList_History.add(it.sku()!!)
                        }
                        baseArrayList.set(
                            index,
                            BaseArrayList(
                                "browsing_history",
                                MainCategorySlider(null, null, null),
                                BaseSlider(null),
                                BaseAdsBlocks(null),
                                BaseDailyDealsproducts(null),
                                BaseNewArrivals(null),
                                BaseRecommended(null),
                                BaseInspiredHistory(null),
                                BaseDynamicLinks(null),
                                BaseBanners(null),
                                BaseBestSeller(null),
                                BaseOfferPlate(null),
                                BaseAdsImages(null),
                                skusList_History
                            )
                        )
                        adapter.notifyDataSetChanged()
                    } else {
                        baseArrayList.removeAt(index)
                    }
                }
            })

        } else {

            val gson = Gson()
            val json: String = sharedStorageHistory!!.getProductClickId()!!

            if (json != null && !json.isEmpty()) {
                if (mHistoryList == null) {
                    mHistoryList = ArrayList<BrowseSavingModel>()
                }

                val type: Type = object : TypeToken<ArrayList<BrowseSavingModel?>?>() {}.getType()
                mHistoryList = gson.fromJson(json, type)

                var skuFromLocalHistory: ArrayList<String> = arrayListOf()
                for (i in mHistoryList!!.indices) {
                    skuFromLocalHistory.add(mHistoryList!!.get(i).sku)
                }


                baseArrayList.set(
                    index,
                    BaseArrayList(
                        "browsing_history",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        skuFromLocalHistory
                    )
                )
                adapter.notifyDataSetChanged()

            } else {
                //baseArrayList.removeAt(index)
            }

        }

    }

    fun DynamicBlocksApiCall(index: Int, id: Int) {
        mainViewModel.getDynamicBlocksLayoutInfo(id, null)
        //val dynamicBlocksLiveData = mainViewModel.dynamicBlocksLiveData
        mainViewModel.dynamicBlocksLiveData.observe(viewLifecycleOwner, Observer { it7 ->
            if (it7 is Resource.Success) {
                baseArrayList.set(
                    index,
                    BaseArrayList(
                        "dynamic",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        ConvertToDynamic(it7),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                adapter.notifyItemChanged(index)
            }
        })
    }

    fun BannersApiCall(index: Int, id: Int) {
        mainViewModel.getBannersLayoutInfo(id)
        //val bannersLiveData = mainViewModel.bannersLiveData
        mainViewModel.bannersLiveData.observe(viewLifecycleOwner, Observer { it9 ->
            if (it9 is Resource.Success) {
                baseArrayList.set(
                    index,
                    BaseArrayList(
                        "banners",
                        MainCategorySlider(null, null, null),
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseInspiredHistory(null),
                        BaseDynamicLinks(null),
                        ConvertToBanners(it9),
                        BaseBestSeller(null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null
                    )
                )
                adapter.notifyDataSetChanged()
            }
        })
    }

    fun CategorySlidersApiCall(categorySliderIndexModel: MutableList<CategorySliderIndexModel>) {

        if (categorySliderIndexModel.size > 0) {

            mainViewModel.getcategorySlidersLayoutInfo(categorySliderIndexModel.get(0).id)
                .observe(viewLifecycleOwner, Observer {
                    if (it is Resource.Success) {
                        println(
                            "ConvertingCategoryList(it) " + ConvertingCategoryList(it).data!!.categorySliders.get(
                                0
                            ).fixed_type
                        )
                        baseArrayList.set(
                            categorySliderIndexModel.get(0).intex,
                            BaseArrayList(
                                "category_slider",
                                MainCategorySlider(ConvertingCategoryList(it), null, null),
                                BaseSlider(null),
                                BaseAdsBlocks(null),
                                BaseDailyDealsproducts(null),
                                BaseNewArrivals(null),
                                BaseRecommended(null),
                                BaseInspiredHistory(null),
                                BaseDynamicLinks(null),
                                BaseBanners(null),
                                BaseBestSeller(null),
                                BaseOfferPlate(null),
                                BaseAdsImages(null),
                                null
                            )
                        )

                        adapter.notifyItemChanged(categorySliderIndexModel.get(0).intex)
                        println(categorySliderIndexModel.get(0).intex)
                        categorySliderIndexModel.removeAt(0)


                        if (categorySliderIndexModel.size > 0 ) {
                            CategorySlidersApiCall(categorySliderIndexModel)
                        } else {
                            subCatData()
                        }
                    }
                })

        }
    }

    fun subCatData() {
        if (subCatIndexModel.size != 0) {

            var skuArrayList: ArrayList<String> = arrayListOf()
            baseArrayList.get(subCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                0
            ).items.forEach {
                skuArrayList.add(it.sku)
            }
            mainViewModel.getCategorySliderItemsProductsSkuInfo1(skuArrayList)
            mainViewModel.categorySliderItemsProductsLiveData.observe(viewLifecycleOwner, Observer {
                if (it is Resource.Success) {

                    var skuArrayListnew: ArrayList<String> = arrayListOf()
                    baseArrayList.get(subCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                        0
                    ).items.forEach {
                        skuArrayListnew.add(it.sku)
                    }

                    baseArrayList.set(
                        subCatIndexModel.get(0).intex,
                        BaseArrayList(
                            "category_slider",
                            MainCategorySlider(
                                baseArrayList.get(subCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders,
                                subSortProductListAtCategorySlider1(
                                    ConvertingList(it).data.products,
                                    skuArrayListnew
                                ), null
                            ),
                            BaseSlider(null),
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseInspiredHistory(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            BaseOfferPlate(null),
                            BaseAdsImages(null),
                            null
                        )
                    )

                    adapter.notifyItemChanged(subCatIndexModel.get(0).intex)
                    subCatIndexModel.removeAt(0)
                    if (subCatIndexModel.size != 0) {
                        subCatData()
                    } else {
                        subsubCatData()
                    }
                }
            })
        }
    }

    fun subsubCatData() {
        if (subsubCatIndexModel.size != 0) {
            var subskuArrayList: ArrayList<String> = arrayListOf()
            baseArrayList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                0
            ).belowitems.forEach {
                subskuArrayList.add(it.sku)
            }

            mainViewModel.getCategorySliderSubItemsProductsSkuInfo1(subskuArrayList)
            mainViewModel.categorySliderSubItemsProductsLiveData.observe(
                viewLifecycleOwner,
                Observer {
                    if (it is Resource.Success) {
                        var subskuArrayListNew: ArrayList<String> = arrayListOf()
                        baseArrayList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                            0
                        ).belowitems.forEach {
                            subskuArrayListNew.add(it.sku)
                        }

                        baseArrayList.set(
                            subsubCatIndexModel.get(0).intex,
                            BaseArrayList(
                                "category_slider",
                                MainCategorySlider(
                                    baseArrayList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders,
                                    baseArrayList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.subProductDetails,
                                    subsubSortProductListAtCategorySlider(
                                        ConvertingList(it).data.products,
                                        subskuArrayListNew
                                    )
                                ),
                                BaseSlider(null),
                                BaseAdsBlocks(null),
                                BaseDailyDealsproducts(null),
                                BaseNewArrivals(null),
                                BaseRecommended(null),
                                BaseInspiredHistory(null),
                                BaseDynamicLinks(null),
                                BaseBanners(null),
                                BaseBestSeller(null),
                                BaseOfferPlate(null),
                                BaseAdsImages(null),
                                null
                            )
                        )

                        adapter.notifyItemChanged(subsubCatIndexModel.get(0).intex)
                        subsubCatIndexModel.removeAt(0)
                        if (subsubCatIndexModel.size != 0) {
                            subsubCatData()
                        }
                    }
                })
        }
    }

    fun AdsImagesApiCall(index: Int, id: List<Int>) {
        mainViewModel.getAdsImagesLayout(id)
        mainViewModel.adsimagesLiveData.observe(viewLifecycleOwner, androidx.lifecycle.Observer {
            if (it is Resource.Success) {
                if (!ConvertingAdsImageList(it).data!!.adsimages.isNullOrEmpty()) {
                    baseArrayList.set(
                        index,
                        BaseArrayList(
                            "ads_images",
                            MainCategorySlider(null, null, null),
                            BaseSlider(null),
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseInspiredHistory(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            BaseOfferPlate(null),
                            ConvertingAdsImageList(it),
                            null
                        )
                    )
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    private fun OfferPlatesApi(index: Int) {
        mainViewModel.getOfferPlatesDetail()
        mainViewModel.offerPlatesLiveData?.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                if (ConvertingOfferPlates(it).data != null) {
                    baseArrayList.set(
                        index,
                        BaseArrayList(
                            "offerplates",
                            MainCategorySlider(null, null, null),
                            BaseSlider(null),
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseInspiredHistory(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            ConvertingOfferPlates(it),
                            BaseAdsImages(null),
                            null
                        )
                    )
                    adapter.notifyDataSetChanged()
                }
            }
        })
    }

    override fun onItemClick(sku: String, item: ProductItem?, from: String) {
        if (from.equals("sliders")) {
            Toast.makeText(activity!!, " Cliked on Sliders Id " + sku, Toast.LENGTH_SHORT)
                .show()

        } else if (from.equals("ads_block")) {
            Toast.makeText(
                activity!!,
                " Cliked on  ads_block Id   " + sku,
                Toast.LENGTH_SHORT
            ).show()
        } else if (from.equals("deals_of_the_day")) {
//            Toast.makeText(
//                activity!!,
//                " Cliked on  deals_of_the_day Sku   " + sku,
//                Toast.LENGTH_SHORT
//            ).show()
        } else if (from.equals("mega_menu")) {

            findNavController().navigate(R.id.action_homeFragment_to_allCategoryFragment)

        } else if (from.equals("new_arrivals")) {
            Toast.makeText(
                activity!!,
                " Cliked on  new_arrivals Sku   " + sku,
                Toast.LENGTH_SHORT
            ).show()

            toPdpPage(sku)


        } else if (from.equals("browsing_history")) {
            Toast.makeText(
                activity!!,
                " Cliked on  browsing_history Sku   " + sku,
                Toast.LENGTH_SHORT
            ).show()
        } else if (from.equals("videoplayer")) {

            var KEY_VIDEO_URI = "video"

            val bundle = Bundle()
            bundle.putString(KEY_VIDEO_URI, sku)


            //findNavController().navigate(R.id.action_homeFragment_to_videoFragment,bundle)

        } else if (from.equals("recommended")) {
            Toast.makeText(
                activity!!,
                " Cliked on  recommended Sku   " + sku,
                Toast.LENGTH_SHORT
            ).show()
        } else if (from.equals("bestseller")) {
            Toast.makeText(
                activity!!,
                " Cliked on  bestseller Sku   " + sku,
                Toast.LENGTH_SHORT
            ).show()
        } else if (from.equals("banner")) {
            Toast.makeText(
                activity!!,
                " Cliked on  banner Url   " + sku,
                Toast.LENGTH_SHORT
            ).show()
        } else if (from.equals("DynamicBlock")) {
            Toast.makeText(
                activity!!,
                " Cliked on  DynamicBlock Sku   " + sku,
                Toast.LENGTH_SHORT
            ).show()

            toPdpPage(sku)
        }


    }

//                    val bundle = bundleOf("recipient" to input_recipient.text.toString())

    fun toPdpPage(sku: String) {
        (activity!! as MainActivity).toolBarHandle("ToPdp")
        // (activity!! as MainActivity).hanldePdpPage("home",sku)
        val bundle = Bundle()
        bundle.putString("sku", sku)

        findNavController().navigate(R.id.action_homeFragment_to_pdpFragment, bundle)

    }

    override fun browseClearClickHandler(
        skuClarList: ArrayList<Int>,
        itemListwise: HashMap<String, String>
    ) {
        mainViewModel.deleteBrosingHistory(skuClarList)
        mainViewModel.browsingHistoryDeleteLiveData?.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                BrowsingHistoryApiCall(Integer.parseInt(itemListwise.get("browsing_history")))
            }
        })

    }


}

