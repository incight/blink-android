package com.blink.blinkshopping.ui.lTwoCategory

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.NavOptions
import androidx.navigation.Navigation
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.databinding.FragmentL2PageCategoryBinding
import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.models.allcategorymodel.ChildrenX
import com.blink.blinkshopping.models.allcategorymodel.ChildrenXX
import com.blink.blinkshopping.models.brandlist.BrandListModel
import com.blink.blinkshopping.models.brandlist.Brands
import com.blink.blinkshopping.models.ltwocategorybasemodel.Base_LTwo_ModelList
import com.blink.blinkshopping.ui.allcategory.AllCategoryViewModel
import com.blink.blinkshopping.ui.allcategory.SharedViewModel
import com.blink.blinkshopping.ui.lOneCategory.L1ViewModel
import com.blink.blinkshopping.ui.main.MainActivity
import com.blink.blinkshopping.util.SharedPrefForHistory
import com.blink.blinkshopping.util.SharedStorage
import com.startup.infobase.utils.Globals.ConvertToDealsOfTheDay
import com.blink.blinkshopping.utils.subSortProductListAtCategorySlider1
import com.blink.blinkshopping.utils.subsubSortProductListAtCategorySlider
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.startup.infobase.utils.Globals
import com.startup.infobase.utils.Globals.ConvertToBestSeller
import com.startup.infobase.utils.Globals.ConvertToDynamic
import com.startup.infobase.utils.Globals.ConvertToRecommended
import com.startup.infobase.utils.Globals.ConvertToSlider
import com.startup.infobase.utils.Globals.ConvertingCategoryList
import com.startup.infobase.utils.Globals.ConvertingList
import com.startup.infobase.utils.Globals.brandConverter
import com.startup.infobase.utils.Globals.convertBestProductsWithFilter
import com.startup.infobase.utils.Globals.convertToNewArrivals
import com.startup.infobase.utils.Globals.imageDataConverter
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_l2_page_category.*
import java.lang.reflect.Type
import java.util.*
import javax.inject.Inject

class LTwoPageCategoryFragment : Fragment(), HasSupportFragmentInjector, L2ProductClickHandler,
    ItemClickHandler, browseClearClickHandler {
    private var mContext: Context? = null
    var sharedStorage: SharedStorage? = null
    var sharedStorageHistory: SharedPrefForHistory? = null

    private lateinit var allCategoryViewModel: AllCategoryViewModel
    private lateinit var selectItemViewModel: SharedViewModel
    private lateinit var categoryLTwoAdapter: LTwoCategogryAdapter

    var categoryLTwoList: MutableList<Base_LTwo_ModelList> = mutableListOf()
    var subCatIndexModel: ArrayList<CategorySliderIndexModel> = arrayListOf()
    var subsubCatIndexModel: ArrayList<CategorySliderIndexModel> = arrayListOf()
    var categorySliderIndexModel: ArrayList<CategorySliderIndexModel> = arrayListOf()

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    //private var valId: Int? = null

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var mainViewModel: L1ViewModel

    private lateinit var binding: FragmentL2PageCategoryBinding

    // var gridChild: MutableList<ChildrenX> = mutableListOf()
    var brandList: MutableList<Brands> = mutableListOf()

    var L2_ProductDataList: MutableList<ChildrenX> = mutableListOf()
    var dataList: MutableList<ChildrenXX> = mutableListOf()
    var sliderIdForFurther: Int? = null

    var navController: NavController? = null

    // private lateinit var l3adapter: L3SubCategoryAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)

        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_l2_page_category, container, false)
        return binding.root
    }

    var dataId = ""
    var L2ProPosition = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        categoryLTwoList.clear()
        sharedStorage = SharedStorage.getInstance(this@LTwoPageCategoryFragment.context!!)
        sharedStorageHistory =
            SharedPrefForHistory.getInstance(this@LTwoPageCategoryFragment.context!!)

        allCategoryViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(AllCategoryViewModel::class.java)
        selectItemViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)
        mainViewModel =
            ViewModelProvider(requireActivity(), viewModelFactory).get(L1ViewModel::class.java)
        mainViewModel.getHomeLayout("category_2", "mobile")
        val layoutLiveData = mainViewModel.lauyoutLiveData
        binding.datalayout = layoutLiveData
        binding.lifecycleOwner = viewLifecycleOwner

        categoryLTwoAdapter =
            LTwoCategogryAdapter(
                requireContext(),
                categoryLTwoList,
                mainViewModel,
                this,
                this,
                this,
                this
            )
        rvLTwoCategory.adapter = categoryLTwoAdapter


        selectItemViewModel.childrenL1.observe(viewLifecycleOwner, Observer { it ->

            L2_ProductDataList = it.children

            allCategoryViewModel.select(L2_ProductDataList)

//            allCategoryViewModel.setL2ProId(L2_ProductDataList.get(0).id)

        })


        allCategoryViewModel.categoryUiData.observe(viewLifecycleOwner, Observer {
            allCategoryViewModel.L2ProPosition.observe(viewLifecycleOwner, Observer { it1 ->
                dataList = it[it1].children
                L2ProPosition = it1
//                l3adapter = L3SubCategoryAdapter(requireContext(), dataList)
//                rvlthree.adapter = l3adapter
//                l3adapter.notifyDataSetChanged()
            })
        })

        layoutLiveData.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                apiIdData(it)
            }
        })

//        allCategoryViewModel.L2ProId.observe(viewLifecycleOwner, Observer { l2ProId ->
//            dataId = l2ProId.toString()
//        })

        // dataId = arguments?.getString("productId").toString()
        val bundle = this.arguments
        if (bundle != null) {
            dataId = bundle.getString("productId", "")
        }


    }

    override fun onL2ProItemClick(
        L2ProId: String,
        l3ProductList: MutableList<ChildrenXX>?,
        from: String, itemListwise: HashMap<String, String>,
        position: Int
    ) {
        if (from.equals("from_L2_Products_click")) {

            dataList = l3ProductList!!
//            dataId = L2ProId
//            val catId = listOf(Integer.parseInt(dataId))

            val bundle = Bundle()
            bundle.putString("productId", L2ProId)
            navController!!.navigate(R.id.action_LTwoCatFragment_to_LTwoCatFragment, bundle) // TODO CHECK BackPress Praveen
//,
//                NavOptions.Builder()
//                    .setPopUpTo(
//                        R.id.allCategoryFragment,
//                        true
//                    ).build()

            allCategoryViewModel.setL2ProPosition(position)

//            L3ProductsListCall(Integer.parseInt(itemListwise.get("L3products")))
//            slidersApiCall(Integer.parseInt(itemListwise.get("sliders")), sliderIdForFurther!!, catId)
//            ShopBrandApiCall(Integer.parseInt(itemListwise.get("brands")), dataId!!)
//            NewArrivalsApiCall(Integer.parseInt(itemListwise.get("new_arrivals")), dataId!!.toInt())
//            BestSellerApiCall(Integer.parseInt(itemListwise.get("best_seller")), dataId!!.toInt())

// RecommendedApiCall(Integer.parseInt(itemListwise.get("inspired_browsing_history")), dataId!!.toInt())

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

                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "sliders",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                val catId = listOf(Integer.parseInt(dataId))
                slidersApiCall(categoryLTwoList.size - 1, id, catId)

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("sub_categories")
            ) {
                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "sub_categories",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                subCategoryGrid(categoryLTwoList.size - 1)

                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "L3products",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                L3ProductsListCall(categoryLTwoList.size - 1)


            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("products")
            ) {
//                categoryLTwoList.add(
//                    Base_LTwo_ModelList(
//                        "",
//                        "products",
//                        BaseSlider(null),
//                        BaseAdsBlocks(null),
//                        BaseDailyDealsproducts(null),
//                        null,
//                        null,
//                        BrandListModel(null),
//                        BaseNewArrivals(null),
//                        BaseRecommended(null),
//                        BaseDynamicLinks(null),
//                        BaseBanners(null),
//                        BaseBestSeller(null),
//                        MainCategorySlider(null, null, null),
//                        BaseOfferPlate(null),
//                        BaseAdsImages(null),
//                        null,
//                        null,
//                        BaseProductsDetails(null)
//                    )
//                )
                //ProductsApiCall(categoryLTwoList.size - 1)
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("brands")
            ) {
                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "brands",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                //ShoByBrands
                ShopBrandApiCall(categoryLTwoList.size - 1, dataId!!)

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("new_arrivals")
            ) {
                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "new_arrivals",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                //NewArrivals
                NewArrivalsApiCall(categoryLTwoList.size - 1, dataId!!.toInt())

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("inspired_browsing_history")
            ) {
                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "inspired_browsing_history",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                //Recommended
                RecommendedApiCall(categoryLTwoList.size - 1, dataId!!.toInt())

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
                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "dynamic_blocks",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                //DynamicBlock
                DynamicBlockApiCall(categoryLTwoList.size - 1, id)

            } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("best_seller")
            ) {
                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "best_seller",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                //BestSellers
                BestSellerApiCall(categoryLTwoList.size - 1, dataId!!.toInt())
            } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("browsing_history")
            ) {
                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "browsing_history",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                BrowsingHistoryApiCall(categoryLTwoList.size - 1)

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("deal_of_day")
            ) {
                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "deal_of_day",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                DealsOfTheDayApiCall(categoryLTwoList.size - 1)
            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                    .equals("description")
            ) {

                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "description",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                DescriptionApiCall(categoryLTwoList.size - 1)

            } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()!!
                    .equals("category_slider")
            ) {
                val id = Integer.parseInt(
                    it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!.substring(
                        it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                            .lastIndexOf("_") + 1
                    )
                )

                categoryLTwoList.add(
                    Base_LTwo_ModelList(
                        "",
                        "category_slider",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )

                categorySliderIndexModel.add(
                    CategorySliderIndexModel(
                        categoryLTwoList.size - 1,
                        id
                    )
                )
                subCatIndexModel.add(CategorySliderIndexModel(categoryLTwoList.size - 1, id))
                subsubCatIndexModel.add(CategorySliderIndexModel(categoryLTwoList.size - 1, id))
            }
        }

        CategorySlidersApiCall(categorySliderIndexModel)

        categoryLTwoList.add(
            Base_LTwo_ModelList(
                "",
                "offerplates",
                BaseSlider(null),
                BaseAdsBlocks(null),
                BaseDailyDealsproducts(null),
                null,
                null,
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
                null,
                BaseProductsDetails(null)
            )
        )

        OfferPlatesApi(categoryLTwoList.size - 1)

    }

    private fun slidersApiCall(index: Int, id: Int, catId: List<Int>) {

        if (catId != null) {
            mainViewModel.getSlidersLayoutInfo(id, null, catId)
        } else {
            mainViewModel.getSlidersLayoutInfo(id, null, null)
        }

        val slidersLiveData = mainViewModel.slidersLiveData
        slidersLiveData.observe(requireActivity(), Observer { sliderData ->
            if (sliderData is Resource.Success) {

                if (ConvertToSlider(sliderData).data!!.sliderById.isNotEmpty()) {
                    val slideData = ConvertToSlider(sliderData)
                    categoryLTwoList.set(
                        index,
                        Base_LTwo_ModelList(
                            "",
                            "sliders",
                            slideData,
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            null,
                            null,
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
                            null,
                            BaseProductsDetails(null)
                        )
                    )
                    categoryLTwoAdapter.notifyItemChanged(index)
                } else {
//                    categoryLTwoList.set(
//                        index,
//                        Base_LTwo_ModelList(
//                            "",
//                            "sliders",
//                            BaseSlider(null),
//                            BaseAdsBlocks(null),
//                            BaseDailyDealsproducts(null),
//                            null,
//                            null,
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
//                        null,
//                        BaseProductsDetails(null)
//                        )
//                    )
//                    categoryLTwoAdapter.notifyItemChanged(index)
                    categoryLTwoAdapter.notifyItemRemoved(categoryLTwoAdapter.getItemViewType(index))
                    categoryLTwoAdapter.notifyDataSetChanged();

                }
            }
        })
    }

    private fun subCategoryGrid(index: Int) {

        categoryLTwoList.set(
            index,
            Base_LTwo_ModelList(
                L2ProPosition.toString()!!,
                "sub_categories",
                BaseSlider(null),
                BaseAdsBlocks(null),
                BaseDailyDealsproducts(null),
                L2_ProductDataList,
                null,
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
                null,
                BaseProductsDetails(null)
            )
        )
        categoryLTwoAdapter.notifyItemChanged(index)


    }

    private fun L3ProductsListCall(index: Int) {

        categoryLTwoList.set(
            index,
            Base_LTwo_ModelList(
                "",
                "L3products",
                BaseSlider(null),
                BaseAdsBlocks(null),
                BaseDailyDealsproducts(null),
                null,
                dataList,
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
                null,
                BaseProductsDetails(null)
            )
        )
        categoryLTwoAdapter.notifyItemChanged(index)

    }

    private fun ProductsApiCall(index: Int) {
        mainViewModel.ProductsQueryCall("442")
        val ProductsQueryLiveData = mainViewModel.ProductsQueryLiveData
        ProductsQueryLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {

                categoryLTwoList.set(
                    index,
                    Base_LTwo_ModelList(
                        "",
                        "products",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        convertBestProductsWithFilter(it)
                    )
                )
                categoryLTwoAdapter.notifyItemChanged(index)

            }
            if (it is Resource.Failure) {
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
                        categoryLTwoList.set(
                            index,
                            Base_LTwo_ModelList(
                                "",
                                "browsing_history",
                                BaseSlider(null),
                                BaseAdsBlocks(null),
                                BaseDailyDealsproducts(null),
                                null,
                                dataList,
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
                                skusList_History,
                                BaseProductsDetails(null)
                            )
                        )
                        categoryLTwoAdapter.notifyItemChanged(index)

                    } else {
                        categoryLTwoList.removeAt(index)
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


                categoryLTwoList.set(
                    index,
                    Base_LTwo_ModelList(
                        "",
                        "browsing_history",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        dataList,
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
                        skuFromLocalHistory,
                        BaseProductsDetails(null)
                    )
                )
                categoryLTwoAdapter.notifyItemChanged(index)

            } else {
            }

        }

    }

    fun DealsOfTheDayApiCall(index: Int) {
        mainViewModel.getDealsOfTheDayLayoutInfo()
        mainViewModel.dealsOfTheDayLiveData.observe(viewLifecycleOwner, Observer { it3 ->
            if (it3 is Resource.Success) {
                categoryLTwoList.set(
                    index,
                    Base_LTwo_ModelList(
                        "",
                        "deal_of_day",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        ConvertToDealsOfTheDay(it3),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                categoryLTwoAdapter.notifyItemChanged(index)

            }
        })
    }

    private fun ShopBrandApiCall(index: Int, dataId: String) {
        mainViewModel.getShopByImageBrands(dataId!!)
        val shopByBrandData = mainViewModel.categorShopByBrands
        shopByBrandData.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
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
                            if (it is Resource.Success) {

                                categoryLTwoList.set(
                                    index,
                                    Base_LTwo_ModelList(
                                        "",
                                        "brands",
                                        BaseSlider(null),
                                        BaseAdsBlocks(null),
                                        BaseDailyDealsproducts(null),
                                        null,
                                        null,
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
                                        null,
                                        BaseProductsDetails(null)
                                    )
                                )
                                categoryLTwoAdapter.notifyItemChanged(index)

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
            if (it5 is Resource.Success) {
                categoryLTwoList.set(
                    index,
                    Base_LTwo_ModelList(
                        "",
                        "new_arrivals",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                categoryLTwoAdapter.notifyItemChanged(index)

            }
        })
    }

    private fun RecommendedApiCall(index: Int, dataId: Int) {
        mainViewModel.getRecommendedInfo(dataId!!.toInt())
        val recommendedLiveData = mainViewModel.recommendedLiveData
        recommendedLiveData.observe(viewLifecycleOwner, Observer { recomanded ->
            if (recomanded is Resource.Success) {
                categoryLTwoList.set(
                    index,
                    Base_LTwo_ModelList(
                        "",
                        "inspired_browsing_history",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                categoryLTwoAdapter.notifyItemChanged(index)
            }
        })
    }

    private fun DynamicBlockApiCall(index: Int, id: Int) {
        mainViewModel.getDynamicBlocksLayoutInfo(id, null)
        val dynamicBlocksLiveData = mainViewModel.dynamicBlocksLiveData
        dynamicBlocksLiveData.observe(viewLifecycleOwner, Observer { dynamics ->
            if (dynamics is Resource.Success) {
                categoryLTwoList.set(
                    index,
                    Base_LTwo_ModelList(
                        "",
                        "dynamic_blocks",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                categoryLTwoAdapter.notifyItemChanged(index)
            }
        })
    }

    private fun BestSellerApiCall(index: Int, dataId: Int) {
        mainViewModel.getGetBestSellers(dataId!!.toInt())
        val bestSellerLiveData = mainViewModel.bestsellersLiveData
        bestSellerLiveData.observe(viewLifecycleOwner, Observer { bestSellers ->
            if (bestSellers is Resource.Success) {
                categoryLTwoList.set(
                    index,
                    Base_LTwo_ModelList(
                        "",
                        "best_seller",
                        BaseSlider(null),
                        BaseAdsBlocks(null),
                        BaseDailyDealsproducts(null),
                        null,
                        null,
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
                        null,
                        BaseProductsDetails(null)
                    )
                )
                categoryLTwoAdapter.notifyItemChanged(index)
            }
        })
    }

    private fun DescriptionApiCall(index: Int) {

        selectItemViewModel.childrenL1.observe(viewLifecycleOwner, Observer { it ->

            categoryLTwoList.set(
                index,
                Base_LTwo_ModelList(
                    "",
                    "description",
                    BaseSlider(null),
                    BaseAdsBlocks(null),
                    BaseDailyDealsproducts(null),
                    null,
                    null,
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
                    null,
                    BaseProductsDetails(null)
                )
            )
            categoryLTwoAdapter.notifyItemChanged(index)

        })


    }

    fun CategorySlidersApiCall(categorySliderIndexModel: ArrayList<CategorySliderIndexModel>) {
        if (categorySliderIndexModel.size != 0) {

            mainViewModel.getcategorySlidersLayoutInfo(categorySliderIndexModel.get(0).id)
                .observe(viewLifecycleOwner, androidx.lifecycle.Observer {
                    if (it is Resource.Success) {

                        if (dataId.equals(ConvertingCategoryList(it).data!!.categorySliders.get(0).which_category)) {

                            categoryLTwoList.set(
                                categorySliderIndexModel.get(0).intex,
                                Base_LTwo_ModelList(
                                    "",
                                    "category_slider",
                                    BaseSlider(null),
                                    BaseAdsBlocks(null),
                                    BaseDailyDealsproducts(null),
                                    null,
                                    null,
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
                                    null,
                                    BaseProductsDetails(null)
                                )
                            )
                            categoryLTwoAdapter.notifyItemChanged(categorySliderIndexModel.get(0).intex)
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
            categoryLTwoList.get(subCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                0
            ).items.forEach {
                skuArrayList.add(it.sku)
            }
            mainViewModel.getCategorySliderItemsProductsSkuInfo1(skuArrayList)
            mainViewModel.categorySliderItemsProductsLiveData.observe(viewLifecycleOwner, Observer {
                if (it is Resource.Success) {
                    var skuArrayListnew: ArrayList<String> = arrayListOf()
                    categoryLTwoList.get(subCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                        0
                    ).items.forEach {
                        skuArrayListnew.add(it.sku)
                    }

                    categoryLTwoList.set(
                        categorySliderIndexModel.get(0).intex,
                        Base_LTwo_ModelList(
                            "",
                            "category_slider",
                            BaseSlider(null),
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            null,
                            null,
                            BrandListModel(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            MainCategorySlider(
                                categoryLTwoList.get(subCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders,
                                subSortProductListAtCategorySlider1(
                                    ConvertingList(it).data.products,
                                    skuArrayListnew
                                ), null
                            ),
                            BaseOfferPlate(null),
                            BaseAdsImages(null),
                            null,
                            null,
                            BaseProductsDetails(null)
                        )
                    )

                    categoryLTwoAdapter.notifyItemChanged(subCatIndexModel.get(0).intex)
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
            categoryLTwoList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
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
                        categoryLTwoList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders!!.data!!.categorySliders.get(
                            0
                        ).belowitems.forEach {
                            subskuArrayListNew.add(it.sku)
                        }
                        categoryLTwoList.set(
                            categorySliderIndexModel.get(0).intex,
                            Base_LTwo_ModelList(
                                "",
                                "category_slider",
                                BaseSlider(null),
                                BaseAdsBlocks(null),
                                BaseDailyDealsproducts(null),
                                null,
                                null,
                                BrandListModel(null),
                                BaseNewArrivals(null),
                                BaseRecommended(null),
                                BaseDynamicLinks(null),
                                BaseBanners(null),
                                BaseBestSeller(null),
                                MainCategorySlider(
                                    categoryLTwoList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.baseCategorySliders,
                                    categoryLTwoList.get(subsubCatIndexModel.get(0).intex).mainCategorySlider.subProductDetails,
                                    subsubSortProductListAtCategorySlider(
                                        ConvertingList(it).data.products,
                                        subskuArrayListNew
                                    )
                                ),
                                BaseOfferPlate(null),
                                BaseAdsImages(null),
                                null,
                                null,
                                BaseProductsDetails(null)
                            )
                        )

                        categoryLTwoAdapter.notifyItemChanged(subsubCatIndexModel.get(0).intex)
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
                if (Globals.ConvertingOfferPlates(it).data != null) {
                    categoryLTwoList.set(
                        index,
                        Base_LTwo_ModelList(
                            "",
                            "offerplates",
                            BaseSlider(null),
                            BaseAdsBlocks(null),
                            BaseDailyDealsproducts(null),
                            null,
                            null,
                            BrandListModel(null),
                            BaseNewArrivals(null),
                            BaseRecommended(null),
                            BaseDynamicLinks(null),
                            BaseBanners(null),
                            BaseBestSeller(null),
                            MainCategorySlider(null, null, null),
                            Globals.ConvertingOfferPlates(it),
                            BaseAdsImages(null),
                            null,
                            null,
                            BaseProductsDetails(null)
                        )
                    )
                    categoryLTwoAdapter.notifyItemChanged(index)
                }
            }
        })
    }


    fun <T> MutableLiveData<T>.observeOnce(owner: LifecycleOwner, observer: (T) -> Unit) {
        observe(owner, object : Observer<T> {
            override fun onChanged(value: T) {
                removeObserver(this)
                observer(value)
            }
        })
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        mContext = context
    }

    override fun onDetach() {
        super.onDetach()
        mContext = null
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
        navController!!.navigate(R.id.action_LTwoCatFragment_to_pdpFragment, bundle)
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

/*  private fun getBestSeller() {
      mainViewModel.getGetBestSellers(null)
      val bestSellerLiveData = mainViewModel.bestsellersLiveData
      bestSellerLiveData.observe(viewLifecycleOwner, Observer { bestSellers ->
          if (bestSellers is Resource.Success) {
              categoryLTwoList.add(
                  if (categoryLTwoList.size >= 2) 2 else categoryLTwoList.size,
                  Base_LTwo_ModelList(
                      "best_seller",
                      brandList,
                      BaseNewArrivals(null),
                      convertBestSeller(bestSellers)
                  )
              )
              categoryLTwoAdapter.notifyDataSetChanged()
          }
      })
  }

  private fun getNewArrival() {
      mainViewModel.getNewArrivalsInfo(null)
      val newsArrivalsLiveData = mainViewModel.newArrivalsLiveData
      newsArrivalsLiveData.observe(viewLifecycleOwner, Observer { newArrival ->
          if (newArrival is Resource.Success) {
              categoryLTwoList.add(
                  if (categoryLTwoList.size == 0) 0 else 1,
                  Base_LTwo_ModelList(
                      "new_arrivals",
                      brandList,
                      convertToNewArrivals(newArrival),
                      BestSellerModel(null)
                  )
              )
              categoryLTwoAdapter.notifyDataSetChanged()
          }
      })
  }

  val list: ArrayList<String> = arrayListOf()

  private fun getShopBrand(toString: String) {
      mainViewModel.getShopByImageBrands("1915")
      val productData = mainViewModel.categorShopByBrands
      productData.observe(viewLifecycleOwner, Observer {
          if (it is Resource.Success) {


              val listData = imageDataConverter(it).data.products.aggregations
              for ((i, Aggregation) in listData.withIndex()) {
                  if (Aggregation.label == "Brand") {
                      val listValue = Aggregation.options
                      list.clear()
                      listValue.forEach {
                          list.add(it.value)
                      }

                      mainViewModel.getBrandList(Aggregation.attribute_code, list)
                      val dataImage = mainViewModel.brandList
                      dataImage.observe(viewLifecycleOwner, Observer {
                          if (it is Resource.Success) {
                              dataImage.removeObservers(viewLifecycleOwner)
                              brandList = brandConverter(it).data!!.brandsList.toMutableList()
                              categoryLTwoList.add(
                                  0,
                                  Base_LTwo_ModelList(
                                      "brands",
                                      brandList,
                                      BaseNewArrivals(null),
                                      BestSellerModel(null)
                                  )
                              )
                              categoryLTwoAdapter.notifyDataSetChanged()
                              dataImage.value = null
                          }
                      })
                  }
              }
          }
      })
  }*/

/* fun <T> LiveData<T>.observeOnce(lifecycleOwner: LifecycleOwner, observer: Observer<T>) {
     observe(lifecycleOwner, object : Observer<T> {
         override fun onChanged(t: T?) {
            if(t is Resource.Success<*>){
                removeObserver(this)
                observer.onChanged(t)

            }


         }
     })
 }*/

//allCategoryViewModel.setL2ProPosition(0)
//allCategoryViewModel.settoL2ChildPosition()
//            val ltCateAdapter = LTwoCateAdapter(requireContext(), L2_ProductDataList, this)
//            rvcCategoryDetails.adapter = ltCateAdapter
//            ltCateAdapter.notifyDataSetChanged()


//            rvcCategoryDetails.addOnItemTouchListener(
//                RecyclerTouchListener(requireContext(),
//                    rvcCategoryDetails, object : ClickListener {
//                        override fun onClick(
//                            view: View?,
//                            position: Int
//                        ) {
//                            valId = it.id
//                            //getShopBrand(valId.toString())
//                            allCategoryViewModel.setL2ProPosition(position)
//                            allCategoryViewModel.setL2ProId(it.children.get(position).id)
//                        }
//                        override fun onLongClick(
//                            view: View?,
//                            position: Int
//                        ) {
//                        }
//                    })
//            )