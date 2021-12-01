package com.blink.blinkshopping.ui.categoryFragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import com.apollographql.apollo.api.Input
import com.blink.blinkshopping.*
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.databinding.ActivityCateogryBinding
import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.type.AggregationsInput
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.startup.infobase.utils.Globals.ConvertToDynamic
import com.startup.infobase.utils.Globals.ConvertToNewArrivals
import com.startup.infobase.utils.Globals.ConvertToSlider
import com.startup.infobase.utils.Globals.ConvertToSubCategory
import com.startup.infobase.utils.Globals.ConvertingCategoryList
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_nhome.*
import java.lang.reflect.Type
import javax.inject.Inject


class CategoryFragment : Fragment(), HasSupportFragmentInjector, ItemClickHandler {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<androidx.fragment.app.Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private lateinit var mainViewModel: CategoryFragmentViewModel
    lateinit var binding: ActivityCateogryBinding
    lateinit var adapter: CategoryAdapter

    var baseArrayList: MutableList<BaseCategoryArrayList> = mutableListOf()
    var clickedId = ""

    override fun supportFragmentInjector() = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_cateogry, container, false)
        return binding.root
    }


    companion object {
        private const val From = "from"
        private const val Layout = "layout"
        private const val Category = "category"
        fun newInstance(from: String, layout: String, category: String): CategoryFragment? {
            val fragment: CategoryFragment = CategoryFragment()
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
        mainViewModel =
            ViewModelProviders.of(this, viewModelFactory).get(CategoryFragmentViewModel::class.java)

        clickedId = arguments!!.getString("clickedId")
        mainViewModel.getHomeLayout(arguments!!.getString("layout"), "mobile")

        val layoutLiveData = mainViewModel.lauyoutLiveData
        binding.data = layoutLiveData
        binding.lifecycleOwner = viewLifecycleOwner

        adapter = CategoryAdapter(
            this@CategoryFragment.context!!,
            baseArrayList,
            mainViewModel,
            this,
            this
        )
        val layoutManager = LinearLayoutManager(
            this@CategoryFragment.context!!,
            LinearLayoutManager.VERTICAL,
            false
        )
        rvForAllViews?.setLayoutManager(layoutManager)
        rvForAllViews?.adapter = adapter

        layoutLiveData?.observe(viewLifecycleOwner, Observer { it ->

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
                            .equals("sub_categories")
                    ) {
                        SubCategoryApiCall(clickedId)
                    } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("brands")
                    ) {
                        ShopByBrandsApiCall(clickedId)
                    } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("new_arrivals")
                    ) {
                        NewArrivalsApiCall()
                    } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("inspired_browsing_history")
                    ) {
                        //  TODO
                    } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("dynamic_blocks")
                    ) {
                        val id = Integer.parseInt(
                            it.data!!.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                                .substring(
                                    it.data!!.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                                        .lastIndexOf("_") + 1
                                )
                        )
                        DynamicBlocksApiCall(id)
                    } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("best_seller")
                    ) {
                        //  BestSellersApiCall(Integer.parseInt(clickedId))
                    } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("browsing_history")
                    ) {
                        //  TODO
                    } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("description")
                    ) {
                        descriptionApiCall("442")
                    } else if (it.data!!.layouts()!!.get(0).items()!!.get(i).block_type()
                            .equals("category_slider")
                    ) {
                        val id = Integer.parseInt(
                            it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!.substring(
                                it.data.layouts()!!.get(0).items()!!.get(i).block_type_id()!!
                                    .lastIndexOf("_") + 1
                            )
                        )
                        CategorySlidersApiCall(id)
                    }
                }
            }
        })

    }

    fun SlidersApiCall(id: Int) {
        mainViewModel.getSlidersLayoutInfo(id, null, null)
        val slidersLiveData = mainViewModel.slidersLiveData
        slidersLiveData.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                if (ConvertToSlider(it).data!!.sliderById != null && ConvertToSlider(it).data!!.sliderById.isNotEmpty()) {
                    baseArrayList.add(
                        0, BaseCategoryArrayList(
                            "sliders",
                            ConvertToSlider(it),
                            BaseSubCategory(null),
                            BaseNewArrivals(null),
                            BaseDynamicLinks(null),
                            BaseCategorySliders(null)
                        )
                    )
                    indexHandling()
                    adapter.notifyDataSetChanged()
                }

            }
        })
    }

    //to get Sub Category List
    fun SubCategoryApiCall(id: String) {
        mainViewModel.getCategoryListInfo(id)
        val categoryListLiveData = mainViewModel.categoryListLiveData
        categoryListLiveData?.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                baseArrayList.add(
                    if (baseArrayList.size >= 3) 3 else baseArrayList.size,
                    BaseCategoryArrayList(
                        "sub_categories",
                        BaseSlider(null),
                        ConvertToSubCategory(it),
                        BaseNewArrivals(null),
                        BaseDynamicLinks(null),
                        BaseCategorySliders(null)
                    )
                )
                indexHandling()
                adapter.notifyDataSetChanged()
            }
        })

    }


    fun ShopByBrandsApiCall(id: String) {
        mainViewModel.getShowByBrandsInfo(id)
        val shopByBrandsQueryLiveData = mainViewModel.shopByBrandsQueryLiveData
        shopByBrandsQueryLiveData?.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                var aggregationsInputs = ArrayList<AggregationsInput>()
                if (it.data!!.products()!!.aggregations()!! != null) {
                    var attribute_code = ""

                    it.data!!.products()!!.aggregations()!!.forEach { it2 ->
                        var optionsInput = ArrayList<String>()
                        attribute_code = it2.attribute_code()

                        val attributeInput: Input<String> = Input.fromNullable(attribute_code)

                        for (i in it2.options()!!.iterator()) {
                            optionsInput.add(i.value())
                        }

                        var options: Input<List<String>> = Input.fromNullable(optionsInput)

//                        var aggregationsInput = AggregationsInput(attributeInput, options,false)
//                        aggregationsInputs.add(aggregationsInput)

                    }
                }

                mainViewModel.getShowByBrandsListByAttributesInfo(aggregationsInputs)
                val shopByBrandsQueryListLiveData = mainViewModel.shopByBrandsQueryListLiveData
                shopByBrandsQueryListLiveData?.observe(viewLifecycleOwner, Observer { it ->
                    if (it is Resource.Success) {
                    }
                })
            }
        })
    }

    fun NewArrivalsApiCall() {
        mainViewModel.getNewArrivalsInfo(null)
        val newsArrivalsLiveData = mainViewModel.newArrivalsLiveData
        newsArrivalsLiveData.observe(viewLifecycleOwner, Observer { it5 ->
            if (it5 is Resource.Success) {
                baseArrayList.add(
                    if (baseArrayList.size >= 3) 3 else baseArrayList.size,
                    BaseCategoryArrayList(
                        "new_arrivals",
                        BaseSlider(null),
                        BaseSubCategory(null),
                        ConvertToNewArrivals(it5),
                        BaseDynamicLinks(null),
                        BaseCategorySliders(null)
                    )
                )
                indexHandling()
                adapter.notifyDataSetChanged()
            }
        })
    }

    fun DynamicBlocksApiCall(id: Int) {
        mainViewModel.getDynamicBlocksLayoutInfo(id, null)
        val dynamicBlocksLiveData = mainViewModel.dynamicBlocksLiveData
        dynamicBlocksLiveData.observe(viewLifecycleOwner, Observer { it7 ->
            if (it7 is Resource.Success) {
                baseArrayList.add(
                    if (baseArrayList.size >= 5) 5 else baseArrayList.size,
                    BaseCategoryArrayList(
                        "dynamic",
                        BaseSlider(null),
                        BaseSubCategory(null),
                        BaseNewArrivals(null),
                        ConvertToDynamic(it7),
                        BaseCategorySliders(null)
                    )
                )
                indexHandling()
                adapter.notifyDataSetChanged()
            }
        })
    }

//    fun BestSellersApiCall(id: Int) {
//        mainViewModel.getGetBestSelletsInfo(id)
//        val bestSellersLiveData = mainViewModel.bestSellersLiveData
//        bestSellersLiveData.observe(viewLifecycleOwner, Observer { it20 ->
//            if (it20 is Resource.Success) {
//            }
//        })
//    }

    fun CategorySlidersApiCall(id: Int) {
        mainViewModel.getcategorySlidersLayoutInfo(id)
            .observe(viewLifecycleOwner, androidx.lifecycle.Observer {

                if (it is Resource.Success) {
                    var there = false
                    baseArrayList.forEach { it1 ->
                        if (it1.baseCategorySliders.data != null)
                            there =
                                it1.baseCategorySliders.data!!.categorySliders.get(0).id == it.data!!.categorySliders()!!
                                    .get(0).id()
                    }
                    if (!there) {
                        baseArrayList.add(
                            BaseCategoryArrayList(
                                "category_slider",
                                BaseSlider(null),
                                BaseSubCategory(null),
                                BaseNewArrivals(null),
                                BaseDynamicLinks(null),
                                ConvertingCategoryList(it)
                            )
                        )
                        indexHandling()
                        adapter.notifyDataSetChanged()
                    }
                }
            })
    }

    fun descriptionApiCall(clickedId: String) {
        mainViewModel.getGetDescriptionInfo(clickedId)
        val descriptionQueryLiveData = mainViewModel.descriptionQueryLiveData
        descriptionQueryLiveData.observe(viewLifecycleOwner, Observer { it20 ->
            if (it20 is Resource.Success) {
                //TODO Change here
                /*baseArrayList.add(
                    if (baseArrayList.size >= 3) 3 else baseArrayList.size,
                    BaseCategoryArrayList(
                        "description",
                        BaseSlider(null),
                        BaseSubCategory(null),
                        BaseNewArrivals(null),
                        BaseDynamicLinks(null),
                        BaseCategorySliders(null)
                    )
                )
                indexHandling()
                adapter.notifyDataSetChanged()*/
            }
        })
    }

    fun indexHandling() {
        for (i in 0..baseArrayList.size - 1) {
            if (baseArrayList.get(i).type.equals("sliders")) {
                if (i != 0) {
                    var baseObject: BaseCategoryArrayList = baseArrayList.get(i)
                    baseArrayList.removeAt(i)
                    baseArrayList.add(0, baseObject)
                }
            } else if (baseArrayList.get(i).type.equals("ads_block")) {
                if (i != 1) {
                    var baseObject: BaseCategoryArrayList = baseArrayList.get(i)
                    baseArrayList.removeAt(i)
                    baseArrayList.add(
                        if (baseArrayList.size >= 1) 1 else baseArrayList.size,
                        baseObject
                    )
                }
            } else if (baseArrayList.get(i).type.equals("deal_of_day")) {
                if (i != 2) {
                    var baseObject: BaseCategoryArrayList = baseArrayList.get(i)
                    baseArrayList.removeAt(i)
                    baseArrayList.add(
                        if (baseArrayList.size >= 2) 2 else baseArrayList.size,
                        baseObject
                    )
                }
            } else if (baseArrayList.get(i).type.equals("new_arrivals")) {
                if (i != 3) {
                    var baseObject: BaseCategoryArrayList = baseArrayList.get(i)
                    baseArrayList.removeAt(i)
                    baseArrayList.add(
                        if (baseArrayList.size >= 3) 3 else baseArrayList.size,
                        baseObject
                    )
                }
            } else if (baseArrayList.get(i).type.equals("recommended")) {
                if (i != 4) {
                    var baseObject: BaseCategoryArrayList = baseArrayList.get(i)
                    baseArrayList.removeAt(i)
                    baseArrayList.add(
                        if (baseArrayList.size >= 4) 4 else baseArrayList.size,
                        baseObject
                    )
                }
            } else if (baseArrayList.get(i).type.equals("dynamic")) {
                if (i != 5) {
                    var baseObject: BaseCategoryArrayList = baseArrayList.get(i)
                    baseArrayList.removeAt(i)
                    baseArrayList.add(
                        if (baseArrayList.size >= 5) 5 else baseArrayList.size,
                        baseObject
                    )
                }
            } else if (baseArrayList.get(i).type.equals("banners")) {
                if (i != 6) {
                    var baseObject: BaseCategoryArrayList = baseArrayList.get(i)
                    baseArrayList.removeAt(i)
                    baseArrayList.add(
                        if (baseArrayList.size >= 6) 6 else baseArrayList.size,
                        baseObject
                    )
                }
            } else if (baseArrayList.get(i).type.equals("category_slider")) {
                if (i != baseArrayList.size) {
                    var baseObject: BaseCategoryArrayList = baseArrayList.get(i)
                    baseArrayList.removeAt(i)
                    baseArrayList.add(baseArrayList.size, baseObject)
                }
            }
        }
    }



    override fun onItemClick(sku: String, item: ProductItem?, from: String) {
        if (from.equals("sliders")) {
            Toast.makeText(activity!!, " Cliked on Sliders Id " + sku, Toast.LENGTH_SHORT)
                .show()


        } else if (from.equals("new_arrivals")) {
            Toast.makeText(
                activity,
                " Cliked on  new_arrivals Sku   " + sku,
                Toast.LENGTH_SHORT
            ).show()

        } else if (from.equals("DynamicBlock")) {
            Toast.makeText(
                activity,
                " Cliked on  DynamicBlock Sku   " + sku,
                Toast.LENGTH_SHORT
            ).show()

            val bundle = Bundle()
            bundle.putString("sku", sku)

        }
    }



}
