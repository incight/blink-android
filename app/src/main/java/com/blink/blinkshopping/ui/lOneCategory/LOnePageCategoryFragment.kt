package com.blink.blinkshopping.ui.lOneCategory

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.base.Resource.Success
import com.blink.blinkshopping.databinding.FragmentL1PageCategoryBinding
import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.models.allcategorymodel.ChildrenXX
import com.blink.blinkshopping.models.brandlist.BrandListModel
import com.blink.blinkshopping.models.brandlist.Brands
import com.blink.blinkshopping.models.categorydetailslone.Base_LOne_ModelList
import com.blink.blinkshopping.models.gridcategory.GridCategoryData
import com.blink.blinkshopping.ui.allcategory.SharedViewModel
import com.blink.blinkshopping.ui.main.MainActivity
import com.blink.blinkshopping.util.SharedPrefForHistory
import com.blink.blinkshopping.util.SharedStorage
import com.startup.infobase.utils.Globals.ConvertToDealsOfTheDay
import com.blink.blinkshopping.utils.subSortProductListAtCategorySlider1
import com.blink.blinkshopping.utils.subsubSortProductListAtCategorySlider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.startup.infobase.utils.Globals.ConvertToBestSeller
import com.startup.infobase.utils.Globals.ConvertToDynamic
import com.startup.infobase.utils.Globals.ConvertToRecommended
import com.startup.infobase.utils.Globals.ConvertToSlider
import com.startup.infobase.utils.Globals.ConvertingCategoryList
import com.startup.infobase.utils.Globals.ConvertingList
import com.startup.infobase.utils.Globals.ConvertingOfferPlates
import com.startup.infobase.utils.Globals.brandConverter
import com.startup.infobase.utils.Globals.convertToNewArrivals
import com.startup.infobase.utils.Globals.gridConverter
import com.startup.infobase.utils.Globals.imageDataConverter
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject


class LOnePageCategoryFragment : Fragment(), HasSupportFragmentInjector, ItemClickHandler,
    L2ProductClickHandler, browseClearClickHandler {

    private var dataId: String? = null
    var sliderIdForFurther: Int? = null
    var sharedStorage: SharedStorage? = null
    var sharedStorageHistory: SharedPrefForHistory? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var selectItemViewModel: SharedViewModel

    //var gridChild: List<Children> = listOf()
    var brandList: List<Brands> = listOf()

    private lateinit var binding: FragmentL1PageCategoryBinding
    private lateinit var mainViewModel: L1ViewModel
    lateinit var adapter: LOneCategoryAdapter
    var categoryLOneList: MutableList<Base_LOne_ModelList> = mutableListOf()
    //var listValue: MutableList<String> = mutableListOf()

    // var baseArrayList: MutableList<BaseArrayList1> = mutableListOf()
    var subCatIndexModel: ArrayList<CategorySliderIndexModel> = arrayListOf()
    var subsubCatIndexModel: ArrayList<CategorySliderIndexModel> = arrayListOf()
    var categorySliderIndexModel: ArrayList<CategorySliderIndexModel> = arrayListOf()

    var navController: NavController? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_l1_page_category, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        categoryLOneList.clear()
        sharedStorage = SharedStorage.getInstance(this@LOnePageCategoryFragment.context!!)
        sharedStorageHistory =
            SharedPrefForHistory.getInstance(this@LOnePageCategoryFragment.context!!)


        selectItemViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        mainViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(L1ViewModel::class.java)


        selectItemViewModel.selectId.observe(viewLifecycleOwner, Observer { it ->
            dataId = it.toString()
        })

        binding.lifecycleOwner = viewLifecycleOwner
        adapter =
            LOneCategoryAdapter(
                requireContext(),
                categoryLOneList,
                mainViewModel,
                this,
                this,
                this,
                this
            )
        binding.rvcCategoryDetails.adapter = adapter
        mainViewModel.getHomeLayout("category_1", "mobile")
        val layoutLiveData = mainViewModel.lauyoutLiveData
        binding.dataLayout = layoutLiveData
        layoutLiveData.observe(viewLifecycleOwner, Observer {
            if (it is Success) {
                if (dataId != null && !dataId!!.isEmpty()) {
                    apiIdData(it)
                }
            }
        })


    }


    override fun onL2ProItemClick(
        L1ProId: String,
        l3ProductList: MutableList<ChildrenXX>?,
        from: String, itemListwise: HashMap<String, String>,
        position: Int
    ) {
        if (from.equals("from_L1_Products_click")) {

            dataId = L1ProId
            val catId = listOf(Integer.parseInt(dataId))
            navController!!.navigate(R.id.action_lOneCategoryDetialsFragment_to_lOneCategoryDetialsFragment)
//            ,
//                null,
//                NavOptions.Builder()
//                    .setPopUpTo(R.id.allCategoryFragment,
//                        true).build()
//            ) // TODO CHECK BackPress Praveen
            selectItemViewModel.setId(Integer.parseInt(dataId))

//            slidersApiCall(Intgeger.parseInt(itemListwise.get("sliders")), sliderIdForFurther!!, catId)
//            ShopBrandApiCall(Inteer.parseInt(itemListwise.get("brands")), dataId!!)
//            NewArrivalsApiCall(Integer.parseInt(itemListwise.get("new_arrivals")), dataId!!.toInt())
//            RecommendedApiCall(Integer.parseInt(itemListwise.get("inspired_browsing_history")), dataId!!.toInt())
//            BestSellerApiCall(Integer.parseInt(itemListwise.get("best_seller")), dataId!!.toInt())

        }
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
                sliderIdForFurther = id
                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "sliders",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )

                val catId = listOf(Integer.parseInt(dataId))
                slidersApiCall(categoryLOneList.size - 1, id, catId)
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("sub_categories")
            ) {
                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "sub_categories",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                subCategoryGrid(categoryLOneList.size - 1, id, "2")
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("brands")
            ) {
                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "brands",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                //ShoByBrands
                ShopBrandApiCall(categoryLOneList.size - 1, dataId!!)

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("new_arrivals")
            ) {
                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "new_arrivals",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                //NewArrivals
                NewArrivalsApiCall(categoryLOneList.size - 1, dataId!!.toInt())

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("inspired_browsing_history")
            ) {
                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "inspired_browsing_history",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                //Recommended
                RecommendedApiCall(categoryLOneList.size - 1, dataId!!.toInt())

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("dynamic_blocks")
            ) {
                val id = Integer.parseInt(
                    it.data.layouts()!![0].items()!![i].block_type_id()!!
                        .substring(
                            it.data.layouts()!![0].items()!![i].block_type_id()!!
                                .lastIndexOf("_") + 1
                        )
                )
                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "dynamic_blocks",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                //DynamicBlock
                DynamicBlockApiCall(categoryLOneList.size - 1, id)

            } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("best_seller")
            ) {
                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "best_seller",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                //BestSellers
                BestSellerApiCall(categoryLOneList.size - 1, dataId!!.toInt())
            } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("browsing_history")
            ) {
                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "browsing_history",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                BrowsingHistoryApiCall(categoryLOneList.size - 1)

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("deal_of_day")
            ) {
                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "deal_of_day",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                DealsOfTheDayApiCall(categoryLOneList.size - 1)
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("description")
            ) {

                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "description",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                DescriptionApiCall(categoryLOneList.size - 1)

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()!!
                    .equals("category_slider")
            ) {
                val id = Integer.parseInt(
                    it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!.substring(
                        it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                            .lastIndexOf("_") + 1
                    )
                )

                categoryLOneList.add(
                    Base_LOne_ModelList(
                        "",
                        "category_slider",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )

                categorySliderIndexModel.add(
                    CategorySliderIndexModel(
                        categoryLOneList.size - 1,
                        id
                    )
                )
                subCatIndexModel.add(CategorySliderIndexModel(categoryLOneList.size - 1, id))
                subsubCatIndexModel.add(CategorySliderIndexModel(categoryLOneList.size - 1, id))
            }
        }

        CategorySlidersApiCall(categorySliderIndexModel)

        categoryLOneList.add(
            Base_LOne_ModelList(
                "",
                "offerplates",
                BaseSlider(null),
                BaseAdsBlocks(null),
                BaseDailyDealsproducts(null),
                GridCategoryData(null),
                BrandListModel(null),
                BaseNewArrivals(null),
                BaseRecommended(null),
                BaseDynamicLinks(null),
                BaseBanners(null),
                BaseBestSeller(null),
                MainCategorySlider(null, null, null),
                BaseOfferPlate(null),
                BaseAdsImages(null),
                null,
                null
            )
        )

        OfferPlatesApi(categoryLOneList.size - 1)

    }

    private fun slidersApiCall(index: Int, id: Int, catId: List<Int>?) {

        if (catId != null) {
            mainViewModel.getSlidersLayoutInfo(id, null, catId)
        } else {
            mainViewModel.getSlidersLayoutInfo(id, null, null)
        }

        val slidersLiveData = mainViewModel.slidersLiveData
        slidersLiveData.observe(requireActivity(), Observer { sliderData ->
            if (sliderData is Success) {


                if (ConvertToSlider(sliderData).data!!.sliderById.isNotEmpty()) {
                    val slideData = ConvertToSlider(sliderData)
                    categoryLOneList.set(
                        index,
                        Base_LOne_ModelList(
                            "",
                            "sliders",
                            slideData,
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            GridCategoryData(null),
                            BrandListModel(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            MainCategorySlider(null, null, null),
                            BaseOfferPlate(null),
                            BaseAdsImages(null),
                            null,
                            null
                        )
                    )
                    adapter.notifyItemChanged(index)
                } else {
                    // categoryLOneList.removeAt(index)

//                    categoryLOneList.set(
//                        index,
//                        Base_LOne_ModelList(
//                            "",
//                            "sliders",
//                            BaseSlider(null),
//                            BaseAdsBlocks(null),
//                            BaseDailyDealsproducts(null),
//                            GridCategoryData(null),
//                            BrandListModel(null),
//                            BaseNewArrivals(null),
//                            BaseRecommended(null),
//                            BaseDynamicLinks(null),
//                            BaseBanners(null),
//                            BaseBestSeller(null),
//                            MainCategorySlider(null, null, null),
//                            BaseOfferPlate(null),
//                            BaseAdsImages(null),
//                            null,
//                        null
//                        )
//                    )
//                    adapter.notifyItemChanged(index)

                    adapter.notifyItemRemoved(adapter.getItemViewType(index))
                    adapter.notifyDataSetChanged()

                    // adapter.notifyDataSetChanged();
                }
            }
        })
    }

    private fun subCategoryGrid(index: Int, id: Int, catId: String) {
        mainViewModel.getLoneCategoryData(catId)
        val catGridData = mainViewModel.categoryLOneLiveData
        catGridData.observe(requireActivity(), Observer {
            if (it is Success) {
                categoryLOneList.set(
                    index,
                    Base_LOne_ModelList(
                        dataId!!,
                        "sub_categories",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        gridConverter(it),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                adapter.notifyItemChanged(index)
            }
        })
    }

    fun DealsOfTheDayApiCall(index: Int) {
        mainViewModel.getDealsOfTheDayLayoutInfo()
        mainViewModel.dealsOfTheDayLiveData.observe(viewLifecycleOwner, Observer { it3 ->
            if (it3 is Resource.Success) {
                categoryLOneList.set(
                    index,
                    Base_LOne_ModelList(
                        "",
                        "deal_of_day",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        ConvertToDealsOfTheDay(it3),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                adapter.notifyItemChanged(index)

            }
        })
    }

    private fun ShopBrandApiCall(index: Int, dataId: String) {
        mainViewModel.getShopByImageBrands(dataId!!)
        val shopByBrandData = mainViewModel.categorShopByBrands
        shopByBrandData.observe(viewLifecycleOwner, Observer {
            if (it is Success) {
                val list: ArrayList<String> = arrayListOf()
                val listData = imageDataConverter(it).data.products.aggregations

                for ((i, Aggregation) in listData.withIndex()) {
                    if (Aggregation.label == "Brand") {
                        val listValue = Aggregation.options
                        listValue.forEach {
                            list.add(it.value)
                        }
                        mainViewModel.getBrandList(Aggregation.attribute_code, list)
                        val shopByBrandDataImgList = mainViewModel.brandList
                        shopByBrandDataImgList.observe(viewLifecycleOwner, Observer { it ->
                            if (it is Success) {

                                categoryLOneList.set(
                                    index,
                                    Base_LOne_ModelList(
                                        "",
                                        "brands",
                                        BaseSlider(null),
                                        BaseAdsBlocks(null),
                                        BaseDailyDealsproducts(null),
                                        GridCategoryData(null),
                                        brandConverter(it),
                                        BaseNewArrivals(null),
                                        BaseRecommended(null),
                                        BaseDynamicLinks(null),
                                        BaseBanners(null),
                                        BaseBestSeller(null),
                                        MainCategorySlider(null, null, null),
                                        BaseOfferPlate(null),
                                        BaseAdsImages(null),
                                        null,
                                        null
                                    )
                                )
                                adapter.notifyItemChanged(index)

                            }
                        })
                    }

                }

            }
        })
    }

    private fun NewArrivalsApiCall(index: Int, dataId: Int) {
        mainViewModel.getNewArrivalsInfo(dataId!!.toInt())
        val newsArrivalsLiveData = mainViewModel.newArrivalsLiveData
        newsArrivalsLiveData.observe(viewLifecycleOwner, Observer { it5 ->
            if (it5 is Success) {
                categoryLOneList.set(
                    index,
                    Base_LOne_ModelList(
                        "",
                        "new_arrivals",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        convertToNewArrivals(it5),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                adapter.notifyItemChanged(index)

            }
        })
    }

    private fun RecommendedApiCall(index: Int, dataId: Int) {
        mainViewModel.getRecommendedInfo(dataId!!.toInt())
        val recommendedLiveData = mainViewModel.recommendedLiveData
        recommendedLiveData.observe(viewLifecycleOwner, Observer { recomanded ->
            if (recomanded is Success) {
                categoryLOneList.set(
                    index,
                    Base_LOne_ModelList(
                        "",
                        "inspired_browsing_history",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        ConvertToRecommended(recomanded),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                adapter.notifyItemChanged(index)
            }
        })
    }

    private fun DynamicBlockApiCall(index: Int, id: Int) {
        mainViewModel.getDynamicBlocksLayoutInfo(id, null)
        val dynamicBlocksLiveData = mainViewModel.dynamicBlocksLiveData
        dynamicBlocksLiveData.observe(viewLifecycleOwner, Observer { dynamics ->
            if (dynamics is Success) {
                categoryLOneList.set(
                    index,
                    Base_LOne_ModelList(
                        "",
                        "dynamic_blocks",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        ConvertToDynamic(dynamics),
                        BaseBanners(null),
                        BaseBestSeller(null),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                adapter.notifyItemChanged(index)
            }
        })
    }

    private fun BestSellerApiCall(index: Int, dataId: Int) {
        mainViewModel.getGetBestSellers(dataId!!.toInt())
        val bestSellerLiveData = mainViewModel.bestsellersLiveData
        bestSellerLiveData.observe(viewLifecycleOwner, Observer { bestSellers ->
            if (bestSellers is Success) {
                categoryLOneList.set(
                    index,
                    Base_LOne_ModelList(
                        "",
                        "best_seller",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        GridCategoryData(null),
                        BrandListModel(null),
                        BaseNewArrivals(null),
                        BaseRecommended(null),
                        BaseDynamicLinks(null),
                        BaseBanners(null),
                        ConvertToBestSeller(bestSellers),
                        MainCategorySlider(null, null, null),
                        BaseOfferPlate(null),
                        BaseAdsImages(null),
                        null,
                        null
                    )
                )
                adapter.notifyItemChanged(index)
            }
        })
    }

    var mHistoryList: ArrayList<BrowseSavingModel>? = null

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
                        categoryLOneList.set(
                            index, (
                                    Base_LOne_ModelList(
                                        "",
                                        "browsing_history",
                                        BaseSlider(null),
                                        BaseAdsBlocks(null),
                                        BaseDailyDealsproducts(null),
                                        GridCategoryData(null),
                                        BrandListModel(null),
                                        BaseNewArrivals(null),
                                        BaseRecommended(null),
                                        BaseDynamicLinks(null),
                                        BaseBanners(null),
                                        BaseBestSeller(null),
                                        MainCategorySlider(null, null, null),
                                        BaseOfferPlate(null),
                                        BaseAdsImages(null),
                                        null,
                                        skusList_History
                                    )
                                    )
                        )
                        adapter.notifyDataSetChanged()
                    } else {
                        categoryLOneList.removeAt(index)
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


                categoryLOneList.set(
                    index, (
                            Base_LOne_ModelList(
                                "",
                                "browsing_history",
                                BaseSlider(null),
                                BaseAdsBlocks(null),
                                BaseDailyDealsproducts(null),
                                GridCategoryData(null),
                                BrandListModel(null),
                                BaseNewArrivals(null),
                                BaseRecommended(null),
                                BaseDynamicLinks(null),
                                BaseBanners(null),
                                BaseBestSeller(null),
                                MainCategorySlider(null, null, null),
                                BaseOfferPlate(null),
                                BaseAdsImages(null),
                                null,
                                skuFromLocalHistory
                            )
                            )
                )
                adapter.notifyDataSetChanged()

            } else {
            }


        }

    }


    private fun DescriptionApiCall(index: Int) {

        selectItemViewModel.childrenL1.observe(viewLifecycleOwner, Observer { it ->

            categoryLOneList.set(
                index,
                Base_LOne_ModelList(
                    "",
                    "description",
                    BaseSlider(null),
                    BaseAdsBlocks(null),
                    BaseDailyDealsproducts(null),
                    GridCategoryData(null),
                    BrandListModel(null),
                    BaseNewArrivals(null),
                    BaseRecommended(null),
                    BaseDynamicLinks(null),
                    BaseBanners(null),
                    BaseBestSeller(null),
                    MainCategorySlider(null, null, null),
                    BaseOfferPlate(null),
                    BaseAdsImages(null),
                    it.description,
                    null
                )
            )
            adapter.notifyItemChanged(index)

        })


    }

    fun CategorySlidersApiCall(categorySliderIndexModel: ArrayList<CategorySliderIndexModel>) {
        // dataId  == which_category  //TODO displaying category based on condition
        if (categorySliderIndexModel.size != 0) {
            mainViewModel.getcategorySlidersLayoutInfo(categorySliderIndexModel.get(0).id)
                .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    if (it is Resource.Success) {

                        if (dataId.equals(ConvertingCategoryList(it).data!!.categorySliders.get(0).which_category)) {
                            categoryLOneList.set(
                                categorySliderIndexModel.get(0).intex,
                                Base_LOne_ModelList(
                                    "",
                                    "category_slider",
                                    BaseSlider(null),
                                    BaseAdsBlocks(null),
                                    BaseDailyDealsproducts(null),
                                    GridCategoryData(null),
                                    BrandListModel(null),
                                    BaseNewArrivals(null),
                                    BaseRecommended(null),
                                    BaseDynamicLinks(null),
                                    BaseBanners(null),
                                    BaseBestSeller(null),
                                    MainCategorySlider(ConvertingCategoryList(it), null, null),
                                    BaseOfferPlate(null),
                                    BaseAdsImages(null),
                                    null,
                                    null
                                )
                            )
                            adapter.notifyItemChanged(categorySliderIndexModel.get(0).intex)
                            categorySliderIndexModel.removeAt(0)
                            if (categorySliderIndexModel.size != 0) {
                                CategorySlidersApiCall(categorySliderIndexModel)
                            } else {
                                subCatData()
                            }
                        }

                    }
                })
        }
    }

    fun subCatData() {
        if (subCatIndexModel.size != 0) {

            var skuArrayList: ArrayList<String> = arrayListOf()
            categoryLOneList.get(subCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                0
            ).items.forEach {
                skuArrayList.add(it.sku)
            }
            mainViewModel.getCategorySliderItemsProductsSkuInfo1(skuArrayList)
            mainViewModel.categorySliderItemsProductsLiveData.observe(viewLifecycleOwner, Observer {
                if (it is Resource.Success) {
                    var skuArrayListnew: ArrayList<String> = arrayListOf()
                    categoryLOneList.get(subCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                        0
                    ).items.forEach {
                        skuArrayListnew.add(it.sku)
                    }

                    categoryLOneList.set(
                        categorySliderIndexModel.get(0).intex,
                        Base_LOne_ModelList(
                            "",
                            "category_slider",
                            BaseSlider(null),
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            GridCategoryData(null),
                            BrandListModel(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            MainCategorySlider(
                                categoryLOneList.get(subCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders,
                                subSortProductListAtCategorySlider1(
                                    ConvertingList(it).data.products,
                                    skuArrayListnew
                                ), null
                            ),
                            BaseOfferPlate(null),
                            BaseAdsImages(null),
                            null,
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
            categoryLOneList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
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
                        categoryLOneList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                            0
                        ).belowitems.forEach {
                            subskuArrayListNew.add(it.sku)
                        }
                        categoryLOneList.set(
                            categorySliderIndexModel.get(0).intex,
                            Base_LOne_ModelList(
                                "",
                                "category_slider",
                                BaseSlider(null),
                                BaseAdsBlocks(null),
                                BaseDailyDealsproducts(null),
                                GridCategoryData(null),
                                BrandListModel(null),
                                BaseNewArrivals(null),
                                BaseRecommended(null),
                                BaseDynamicLinks(null),
                                BaseBanners(null),
                                BaseBestSeller(null),
                                MainCategorySlider(
                                    categoryLOneList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders,
                                    categoryLOneList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.subProductDetails,
                                    subsubSortProductListAtCategorySlider(
                                        ConvertingList(it).data.products,
                                        subskuArrayListNew
                                    )
                                ),
                                BaseOfferPlate(null),
                                BaseAdsImages(null),
                                null,
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

    private fun OfferPlatesApi(index: Int) {
        mainViewModel.getOfferPlatesDetail()
        mainViewModel.offerPlatesLiveData?.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                if (ConvertingOfferPlates(it).data != null) {
                    categoryLOneList.set(
                        index,
                        Base_LOne_ModelList(
                            "",
                            "offerplates",
                            BaseSlider(null),
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            GridCategoryData(null),
                            BrandListModel(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            MainCategorySlider(null, null, null),
                            ConvertingOfferPlates(it),
                            BaseAdsImages(null),
                            null,
                            null
                        )
                    )
                    adapter.notifyItemChanged(index)
                }
            }
        })
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }


    override fun onItemClick(sku: String, item: ProductItem?, from: String) {
        if (from.equals("new_arrivals")) {
            Toast.makeText(
                activity!!,
                " Cliked on  new_arrivals Sku   " + sku,
                Toast.LENGTH_SHORT
            ).show()

            toPdpPage(sku)
        }
    }

    fun toPdpPage(sku: String) {
        (activity!! as MainActivity).toolBarHandle("ToPdp")

        val bundle = Bundle()
        bundle.putString("sku", sku)
        navController!!.navigate(R.id.action_lOneCategoryDetialsFragment_to_pdpFragment, bundle)
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




//    override fun onDestroyView() {
//        super.onDestroyView()
//        requireActivity().onBackPressedDispatcher.addCallback(this) {
//            val navController = navController!!.findNavController()
//            if (navController.currentBackStackEntry?.destination?.id != null) {
//                navController!!.findNavController().popBackStackAllInstances(
//                    navController.currentBackStackEntry?.destination?.id!!,
//                    true
//                )
//            } else
//                navController.popBackStack()
//        }
//    }



