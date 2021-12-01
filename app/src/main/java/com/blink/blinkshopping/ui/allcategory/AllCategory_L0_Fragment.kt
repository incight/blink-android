package com.blink.blinkshopping.ui.allcategory

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.databinding.AllCategoryBinding
import com.blink.blinkshopping.models.BaseBrandImages
import com.blink.blinkshopping.models.BaseSlider
import com.blink.blinkshopping.models.Brands1
import com.blink.blinkshopping.models.allcategorymodel.AllCategoryData
import com.blink.blinkshopping.models.allcategorymodel.ChildrenX
import com.blink.blinkshopping.models.brandlist.Brands
import com.blink.blinkshopping.models.shopbybrand.ShopByBrandModel
import com.blink.blinkshopping.ui.pdp.L0_SlidersAdapter
import com.blink.blinkshopping.util.ClickListener
import com.blink.blinkshopping.util.RecyclerTouchListener
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.startup.infobase.utils.Globals.ConvertToSlider
import com.startup.infobase.utils.Globals.convertImgData
import com.startup.infobase.utils.Globals.convertLevelData
import com.startup.infobase.utils.Globals.imageDataConverter
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.all_category.*
import java.lang.reflect.Type
import java.nio.channels.Selector
import java.util.*
import javax.inject.Inject

class AllCategory_L0_Fragment : Fragment(), HasSupportFragmentInjector,
    L0_SlidersAdapter.SliderItemClickHandler {

    private var name: String? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>
    private lateinit var itemSelector: Selector


    var navController: NavController? = null
    var dataId: Int = 0
    private lateinit var detialdViewAdapter: L0_GridDetailsViewAdapter


    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private lateinit var allCategoryViewModel: AllCategoryViewModel
    private lateinit var selectItemViewModel: SharedViewModel
    lateinit var binding: AllCategoryBinding
    var categoryList: MutableList<AllCategoryData> = mutableListOf()

    private lateinit var mainAdapter: AllCategoryMainAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        navController = Navigation.findNavController(requireActivity(), R.id.nav_host_fragment)
        binding = DataBindingUtil.inflate(inflater, R.layout.all_category, container, false)
        return binding.root
    }

    var ProId = 0

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        binding.categoryRecycler.layoutManager = LinearLayoutManager(requireContext())
        binding.categoryRecycler.addItemDecoration(
            DividerItemDecoration(
                binding.categoryRecycler.context,
                (binding.categoryRecycler.layoutManager as LinearLayoutManager).orientation
            )
        )

        allCategoryViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(AllCategoryViewModel::class.java)
        selectItemViewModel = ViewModelProvider(requireActivity()).get(SharedViewModel::class.java)





        allCategoryViewModel.getAllCategoryListData("2")
        val allCategoryLiveData = allCategoryViewModel.allCategoryLiveData
        allCategoryLiveData.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {
                Log.d("data:", it.toString())
                if (convertLevelData(it).data.categoryList.isNotEmpty()) {
                    mainAdapter = AllCategoryMainAdapter(convertLevelData(it))
                    categoryRecycler.adapter = mainAdapter
                    mainAdapter.notifyDataSetChanged()

                    name = convertLevelData(it).data.categoryList[0].children[0].name


                    val childrenData =
                        convertLevelData(it).data.categoryList[0].children[0].children.toMutableList()
                    selectItemViewModel.setName(name!!)
                    allCategoryViewModel.select(childrenData)

                    ProId = convertLevelData(it).data.categoryList[0].children[0].id
                    selectItemViewModel.setChildDrenL1(convertLevelData(it).data.categoryList[0].children[0])


                    categoryRecycler.addOnItemTouchListener(
                        RecyclerTouchListener(requireContext(),
                            categoryRecycler, object : ClickListener {
                                override fun onClick(
                                    view: View?,
                                    position: Int
                                ) {
                                    name =
                                        convertLevelData(it).data.categoryList[0].children[position].name
                                    val childrenData =
                                        convertLevelData(it).data.categoryList[0].children[position].children.toMutableList()
                                    selectItemViewModel.setName(name!!)
                                    allCategoryViewModel.select(childrenData)

                                    getShopBrand(convertLevelData(it).data.categoryList[0].children[position].id.toString())

                                    ProId = convertLevelData(it).data.categoryList[0].children[position].id

                                    selectItemViewModel.setChildDrenL1(convertLevelData(it).data.categoryList[0].children[position])

                                }

                                override fun onLongClick(
                                    view: View?,
                                    position: Int
                                ) {
                                }
                            })
                    )
                }
            }
        })


        allCategoryViewModel.getHomeLayout("category_0", "mobile")
        val lauyoutLiveData = allCategoryViewModel.lauyoutLiveData
        lauyoutLiveData.observe(viewLifecycleOwner, Observer {
            if (it is Resource.Success) {

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

                        SlidersApiCall(id)

                    } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("brands")
                    ) {
                        // getShopBrand(ProId)
                    } else if (it.data.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("same_level_categories")
                    ) {

                    }
                }


            }
        })


        //allCategoryViewModel.getAllCategoryListData("2")

        binding.rvMainView.layoutManager = LinearLayoutManager(requireContext())
        selectItemViewModel.selectedName.observe(viewLifecycleOwner, Observer { dataItem ->
            binding.tvLOneHeadingView.text = dataItem
        })

        selectItemViewModel.selectId.observe(viewLifecycleOwner, Observer { it ->
            dataId = it
        })

        binding.tvLOneHeadingView.setOnClickListener {
            navController!!.navigate(R.id.action_allCategoryFragment_to_lOneCategoryDetialsFragment)
            selectItemViewModel.setId(ProId)
        }

        allCategoryViewModel.categoryUiData.observe(
            viewLifecycleOwner,
            Observer<MutableList<ChildrenX>> { dataItem ->
                detialdViewAdapter =
                    L0_GridDetailsViewAdapter(requireContext(), dataItem, allCategoryViewModel)
                binding.rvMainView.adapter = detialdViewAdapter
                detialdViewAdapter.notifyDataSetChanged()
            })

    }

    fun SlidersApiCall(id: Int) {
        allCategoryViewModel.getSlidersLayoutInfo(id, null, null)
        val slidersLiveData = allCategoryViewModel.slidersLiveData
        slidersLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                if (ConvertToSlider(it).data!!.sliderById != null && ConvertToSlider(it).data!!.sliderById.isNotEmpty()) {
                    binding.viewPagerOffer.adapter =
                        L0_SlidersAdapter(
                            ConvertToSlider(it),
                            this,
                            "L0_Sliders"
                        )
                    binding.indicator.setViewPager(binding.viewPagerOffer)
                    binding.indicator.isSnap = true
                    binding.viewPagerOffer.offscreenPageLimit =
                        ConvertToSlider(it).data!!.sliderById.get(0).items!!.size
                    binding.viewPagerOffer.startAutoScroll(5000)
                }
            }
        })
    }

    var brandList: List<Brands> = listOf()
    private lateinit var shopByBrandsAdpater: ShopByBrandsAdpater

    private fun getShopBrand(toString: String) {
        allCategoryViewModel.getShopByImageBrands(toString)
        val productData = allCategoryViewModel.categorShopByBrands
        productData.observe(viewLifecycleOwner, Observer {

            if (it is Resource.Success) {

                val listData = imageDataConverter(it).data.products.aggregations
                for ((i, Aggregation) in listData.withIndex()) {
                    if (Aggregation.label == "Brand") {
                        val listValue = Aggregation.options
                        val list: ArrayList<String> = arrayListOf()
                        listValue.forEach {
                            list.add(it.value)
                        }
                        shopByBrandsApiCall(Aggregation.attribute_code, list)
                    }
                }


//                var aggregationsInputs = ArrayList<AggregationsInput1>()
//                if (it.data!!.products()!!.aggregations()!! != null) {
//                    var attribute_code = ""
//                    var aggregation = ArrayList<AggregationsInput>()
//                    it.data!!.products()!!.aggregations()!!.forEach { it2 ->
//                        var optionsInput = ArrayList<String>()
//                        attribute_code = it2.attribute_code()
//                        val attributeInput: Input<String> = Input.fromNullable(attribute_code)
//                        for (i in it2.options()!!.iterator()) {
//                            optionsInput.add(i.value())
//                        }
//                        var options: Input<List<String>> = Input.fromNullable(optionsInput)
//                        var aggregationsInput = AggregationsInput1(attributeInput, options)
//                        var mobj = aggregationsInput as AggregationsInput
//                        // aggregationsInputs.add(aggregationsInput)
//                        aggregation.add(mobj)
//                    }
                //  aggregationsInputs11.addAll(aggregationsInputs)
                //aggregation = aggregationsInputs as ArrayList<AggregationsInput>
//                    allCategoryViewModel.getShowByBrandsListByAttributesInfo(aggregation)
//                    val shopByBrandsQueryListLiveData =
//                        allCategoryViewModel.shopByBrandsQueryListLiveData
//                    shopByBrandsQueryListLiveData?.observe(viewLifecycleOwner, Observer { it ->
//                        if (it is Resource.Success) {
//                        }
//                    })
//                }


            }

        })
    }


    fun shopByBrandsApiCall(
        attributeCode: String,
        list: ArrayList<String>
    ) {

        allCategoryViewModel.getShopByImageBrandsList(
            attributeCode,
            list
        )
        val productData = allCategoryViewModel.shopBrandsImageList
        productData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {

                baseBrandImages = convertImgData(it).data.brandsList

                shopByBrandsAdpater =
                    ShopByBrandsAdpater(requireContext(), baseBrandImages/*,this*/)
                val layoutManager = GridLayoutManager(context, 3)
                rvShopByBrands?.setLayoutManager(layoutManager)
                rvShopByBrands?.adapter = shopByBrandsAdpater


                shopByBrandsAdpater.notifyDataSetChanged()

//                brandConverter(it).data.brandsList.forEach { i ->
//                    shopByBrandsAdpater.addItem(i)
//                }

            }
        })
    }

    var baseBrandImages: MutableList<Brands1> = mutableListOf()

//                var size = list.size
//                layoutManager.spanSizeLookup =
//                    object : GridLayoutManager.SpanSizeLookup() {
//                        override fun getSpanSize(position: Int): Int {
//                            return if (position == size - 1 && position and 1 == 0) 3 else 1
//                        }
//                    }







    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }

    override fun onItemClick(banner_id: String, from: String) {
        if (from.equals("L0_Sliders")) {
        }
    }

}

