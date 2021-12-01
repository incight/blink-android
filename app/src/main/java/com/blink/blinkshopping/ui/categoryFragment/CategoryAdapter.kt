package com.blink.blinkshopping.ui.categoryFragment

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.ItemLinearLayoutManager
import com.blink.blinkshopping.ProductsSkuByListQuery
import com.blink.blinkshopping.R
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.databinding.*
import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.ui.home.*
import com.blink.blinkshopping.ui.home.CategorySliderAdapter
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.startup.infobase.utils.Globals.ConvertingList
import kotlinx.android.synthetic.main.layout_sliders_viewholder.view.*
import org.jsoup.Jsoup
import java.lang.reflect.Type


class CategoryAdapter(
    var mContext: Context,
    var baseArrayList: MutableList<BaseCategoryArrayList>,
    var mainViewModel: CategoryFragmentViewModel,
    var lifecycleOwner: LifecycleOwner,
    var onItemClick: ItemClickHandler
) :
    RecyclerView.Adapter<RecyclerView.ViewHolder>(),
    ItemClickHandler {

    private val SLIDER_VIEW = 0
    private val NEW_ARRIVALS = 3
    private val DYNAMIC_BLOCK = 5
    private val CATEGORY_SLIDER_VIEW = 7

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val view: View
        return when (viewType) {
            SLIDER_VIEW -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutSlidersViewholderBinding.inflate(layoutInflater, parent, false)
                return SliderViewHolder(binding)
            }
            NEW_ARRIVALS -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutNewArrivalsBinding.inflate(layoutInflater, parent, false)
                return NewArrivalsViewHolder(binding)
            }
            DYNAMIC_BLOCK -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutDynamicBlocksBinding.inflate(layoutInflater, parent, false)
                return DynamicBlockViewHolder(binding)
            }
            CATEGORY_SLIDER_VIEW -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutCategorySlidersBinding.inflate(layoutInflater, parent, false)
                return CategorySlidersViewHolder(binding)
            }
            else -> {
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = LayoutNewArrivalsBinding.inflate(layoutInflater, parent, false)
                return NewArrivalsViewHolder(binding)
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
                val viewHolder: SliderViewHolder = holder as SliderViewHolder

                val homeSliderIds: ArrayList<Int?> = ArrayList<Int?>()
                val homeSliderImages: ArrayList<String> = ArrayList<String>()
                val homeSliderTitles: ArrayList<String> = ArrayList<String>()
                baseArray.baseSlider.data!!.sliderById.get(0).items.forEach {
                    homeSliderIds.add(it.banner_id)
                    homeSliderImages.add(it.image)
                    homeSliderTitles.add(it.name)
                }

                viewHolder.binding.viewPagerOffer.adapter =
                    SlidersViewPagerAdapter(
                        homeSliderIds,
                        homeSliderImages,
                        homeSliderTitles,
                        onItemClick,
                        "categoryAdapter"
                    )   //Viewpager Images
                viewHolder.binding.indicator.setViewPager(viewHolder.itemView.view_pager_offer)
                viewHolder.binding.indicator.isSnap = true
                viewHolder.binding.viewPagerOffer.offscreenPageLimit = homeSliderImages!!.size
                viewHolder.binding.viewPagerOffer.startAutoScroll(5000)

            }

            NEW_ARRIVALS -> {
                val viewHolder = holder as NewArrivalsViewHolder
                var skusList: ArrayList<String> = arrayListOf()
                baseArray.baseNewArrivals.data!!.newArrivalsproducts.forEach {
                    skusList.add(it.sku)
                }
                mainViewModel.getProductsLayoutInfo(skusList)
                val newArrivalsProductsLiveData = mainViewModel.newArrivalsProductsLiveData
                newArrivalsProductsLiveData.observe(lifecycleOwner, Observer {
                    if (it is Resource.Success) {
                        if (it.data!!.products()!!.items() != null && it.data.products()!!.items()!!
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
                                ItemLinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                            viewHolder.binding.rvNewArrivals?.setLayoutManager(layoutManager)
                            viewHolder.binding.rvNewArrivals?.adapter = newArrivalsadapter
                            newArrivalsadapter.notifyDataSetChanged()
                        }
                    }
                })
            }

            DYNAMIC_BLOCK -> {
                val viewHolder = holder as DynamicBlockViewHolder
                var skusList: ArrayList<String> = arrayListOf()
                baseArray.baseDynamicLinks.data!!.dynamicBlocks.get(0).items.forEach {
                    skusList.add(it.sku)
                }
                mainViewModel.getDynamicProductsSkuLayoutInfo(skusList)
                val dynamicBlocksProductsLiveData = mainViewModel.dynamicBlocksProductsLiveData
                dynamicBlocksProductsLiveData?.observe(lifecycleOwner, Observer { it ->
                    if (it is Resource.Success) {
                        if (it.data!!.products()!!.items() != null && it.data.products()!!.items()!!
                                .isNotEmpty()
                        ) {

                            if (baseArray.baseDynamicLinks.data!!.dynamicBlocks.get(0).title != null && baseArray.baseDynamicLinks.data!!.dynamicBlocks.get(
                                    0
                                ).title.isNotEmpty()
                            )
                                viewHolder.binding.dynamicBannerText.setText(
                                    html2text(
                                        baseArray.baseDynamicLinks.data!!.dynamicBlocks.get(
                                            0
                                        ).title
                                    )
                                )
                            val dynamicBlocksAdapter = DynamicBlocksAdapter(
                                ConvertingList(it).data.products.items,
                                mContext, onItemClick
                            )
                            val layoutManager =
                                LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                            viewHolder.binding.rvDynamicBlocks?.setLayoutManager(layoutManager)
                            viewHolder.binding.rvDynamicBlocks?.adapter = dynamicBlocksAdapter
                            dynamicBlocksAdapter.notifyDataSetChanged()
                        }
                    }
                })
            }

            CATEGORY_SLIDER_VIEW -> {
                val viewHolder: CategorySlidersViewHolder = holder as CategorySlidersViewHolder
                viewHolder.binding.category = baseArray.baseCategorySliders

                if (baseArray.baseCategorySliders.data!!.categorySliders.get(0).isHeroSku()
                ) {
                    var mainskuArrayList: ArrayList<String> = arrayListOf()
                    mainskuArrayList.add(baseArray.baseCategorySliders.data!!.categorySliders.get(0).hero_sku)
                    mainViewModel.getCategorySliderProductsSkuInfo1(mainskuArrayList)
                    mainViewModel.categorySliderProductsLiveData.observe(lifecycleOwner, Observer {
                        if (it is Resource.Success) {
                            if (it.data!!.products()!!.items() != null && it.data.products()!!
                                    .items()!!
                                    .isNotEmpty()
                            ) {
                                viewHolder.binding.product =
                                    ConvertingList(it).data.products.items.get(0)
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
                var skuArrayList: ArrayList<String> = arrayListOf()
                baseArray.baseCategorySliders.data!!.categorySliders.get(0).items.forEach {
                    skuArrayList.add(it.sku)
                }
                mainViewModel.getCategorySliderItemsProductsSkuInfo1(skuArrayList)
                mainViewModel.categorySliderItemsProductsLiveData.observe(lifecycleOwner, Observer {
                    if (it is Resource.Success) {
                        var adapter =
                            CategorySliderAdapter(
                                mContext,
                                ConvertingList(it).data.products.items,
                                mContext.resources.getString(R.string.sub_product)
                            )
                        val layoutManager =
                            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                        viewHolder.binding.rcCategory.setLayoutManager(layoutManager)
                        viewHolder.binding.rcCategory.adapter = adapter
                    }
                })

                var subskuArrayList: ArrayList<String> = arrayListOf()
                baseArray.baseCategorySliders.data!!.categorySliders.get(0).belowitems.forEach {
                    subskuArrayList.add(it.sku)
                }

                mainViewModel.getCategorySliderSubItemsProductsSkuInfo1(subskuArrayList)
                mainViewModel.categorySliderItemsProductsLiveData.observe(lifecycleOwner, Observer {
                    if (it is Resource.Success) {
                        var adapter =
                            CategorySliderAdapter(
                                mContext,
                                ConvertingList(it).data.products.items,
                                mContext.resources.getString(R.string.sub_sub_product)
                            )
                        val layoutManager =
                            LinearLayoutManager(mContext, LinearLayoutManager.HORIZONTAL, false)
                        viewHolder.binding.rcSubCategory.setLayoutManager(layoutManager)
                        viewHolder.binding.rcSubCategory.adapter = adapter
                    }
                })
            }

        }
    }

    fun html2text(html: String?): String? {
        return Jsoup.parse(html).text()
    }

    class SliderViewHolder(layoutSlidersViewholderBinding: LayoutSlidersViewholderBinding) :
        RecyclerView.ViewHolder(layoutSlidersViewholderBinding.root) {
        var binding: LayoutSlidersViewholderBinding

        init {
            this.binding = layoutSlidersViewholderBinding
        }
    }

    class NewArrivalsViewHolder(layoutNewArrivalsBinding: LayoutNewArrivalsBinding) :
        RecyclerView.ViewHolder(layoutNewArrivalsBinding.root) {
        var binding: LayoutNewArrivalsBinding

        init {
            this.binding = layoutNewArrivalsBinding
        }
    }

    class DynamicBlockViewHolder(layoutDynamicBlocksBinding: LayoutDynamicBlocksBinding) :
        RecyclerView.ViewHolder(layoutDynamicBlocksBinding.root) {
        var binding: LayoutDynamicBlocksBinding

        init {
            this.binding = layoutDynamicBlocksBinding
        }
    }

    class CategorySlidersViewHolder(layoutCategorySlidersBinding: LayoutCategorySlidersBinding) :
        RecyclerView.ViewHolder(layoutCategorySlidersBinding.root) {
        var binding: LayoutCategorySlidersBinding

        init {
            this.binding = layoutCategorySlidersBinding
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (baseArrayList.get(position).type) {
            "sliders" -> SLIDER_VIEW
            "new_arrivals" -> NEW_ARRIVALS
            "dynamic" -> DYNAMIC_BLOCK
            "category_slider" -> CATEGORY_SLIDER_VIEW
            else -> -1
        }
    }


    override fun onItemClick(position: String, item: ProductItem?, from: String) {
        //settingBottomItemDetails(,item)
    }

}

