package com.blink.blinkshopping.ui.pdp


import android.annotation.SuppressLint
import android.content.Context
import android.content.SharedPreferences
import android.graphics.Paint
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.webkit.WebSettings
import android.webkit.WebView
import android.webkit.WebViewClient
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.adapter.FragmentStateAdapter
import androidx.viewpager2.widget.ViewPager2
import com.blink.blinkshopping.ItemClickHandler
import com.blink.blinkshopping.ItemLinearLayoutManager
import com.blink.blinkshopping.R
import com.blink.blinkshopping.base.Resource
import com.blink.blinkshopping.databinding.ActivityPdpBinding
import com.blink.blinkshopping.models.*
import com.blink.blinkshopping.ui.home.NewArrivalsAdapter
import com.blink.blinkshopping.util.SharedPrefForHistory
import com.blink.blinkshopping.util.SharedStorage
import com.blink.blinkshopping.util.Utils
import com.blink.blinkshopping.util.Utils.html2text
import com.blink.blinkshopping.utils.SortProductListPDP
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.startup.infobase.utils.Globals.ConvertPdpToSlider
import com.startup.infobase.utils.Globals.ConvertingDeliveryOptions
import com.startup.infobase.utils.Globals.ConvertingList
import com.startup.infobase.utils.Globals.ConvertingMonthlyInstallments
import com.startup.infobase.utils.Globals.ConvertingOfferPlates
import com.startup.infobase.utils.Globals.ConvertingStorePickUp
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_pdp.*
import java.lang.reflect.Type
import javax.inject.Inject


class PdpFragment : Fragment(), HasSupportFragmentInjector, ItemClickHandler,
    DeliveryOptionViewAdapter.SingleClickListener, StorePickUpAdapter.SingleClickListener {

    fun PdpFragment() {
    }

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    override fun supportFragmentInjector() = dispatchingAndroidInjector
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    var sheetBehavior: BottomSheetBehavior<*>? = null
    var sharedStorage: SharedStorage? = null
    var sharedStorageHistory: SharedPrefForHistory? = null


    private lateinit var mainViewModel: PdpViewModel
    lateinit var binding: ActivityPdpBinding
    var productId = ""
    lateinit var offerPlatesAdapter: OfferPlatesAdapter
    lateinit var deliveryOptionsAdapter: DeliveryOptionViewAdapter
    lateinit var storePickUpAdapter: StorePickUpAdapter
    lateinit var installmentsAdapter: InstallmentsAdapter
    var baseArrayList: MutableList<BaseConfurableArrayList> = mutableListOf()

    var baseArrayListNew: MutableList<BaseConfigArrayList> = mutableListOf()
    lateinit var configurAdapter: CofigAdapter

    //private lateinit var pdpImageAdapter: PdpImageAdapter
    private lateinit var models: ArrayList<PdpImagesModel>
    private var dotscount = 0

    lateinit var pagerAdapter: FragmentStateAdapter

    var mHistoryList: ArrayList<BrowseSavingModel>? = null
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.activity_pdp, container, false)

        return binding.root
    }

    companion object {
        private const val From = "from"
        private const val Layout = "layout"
        private const val Category = "category"
        fun newInstance(from: String, layout: String, category: String): PdpFragment? {
            val fragment: PdpFragment = PdpFragment()
            val args = Bundle()
            args.putString(From, from)
            args.putString(Layout, layout)
            args.putString(Category, category)
            fragment.setArguments(args)
            return fragment
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    @SuppressLint("SetTextI18n")
    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(PdpViewModel::class.java)

        sharedStorage = SharedStorage.getInstance(this@PdpFragment.context!!)
        sharedStorageHistory = SharedPrefForHistory.getInstance(this@PdpFragment.context!!)

        baseArrayListNew.clear()

        productId = arguments!!.getString("sku")
        //productId = "14080"
        //binding.data = layoutLiveData

        mainViewModel.getSingleProductSkuDetail(productId)//"574327") //""Apple iphone pro")   "719836")//
        val layoutLiveData = mainViewModel.pdpsku
        binding.lifecycleOwner = viewLifecycleOwner

        models = ArrayList()

        layoutLiveData?.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {

                if (it.data!!.products()!!.items()!!.isNotEmpty() && it.data!!.products()!!
                        .items()!! != null
                ) {

                    SaveBrowsingHistoryApiCall(
                        ConvertPdpToSlider(it).data.products.items.get(0).id.toString(),
                        ConvertPdpToSlider(it).data.products.items.get(0).sku
                    )

                    binding.product = ConvertPdpToSlider(it)
                }

                val list = ConvertPdpToSlider(it).data.products.items.get(0)

                setUpImgSliders(ConvertPdpToSlider(it))

                MonthlyInstallments(
                    productId,
                    list.price_range!!.minimum_price!!.final_price!!.value.toString(), list.name
                )

                binding.txtdodActualPrice.setPaintFlags(binding.txtdodActualPrice.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)
                binding.txtdodActualPrice1.setPaintFlags(binding.txtdodActualPrice1.getPaintFlags() or Paint.STRIKE_THRU_TEXT_FLAG)

                configurAdapter =
                    CofigAdapter(this@PdpFragment.context!!, list.configurable_options, this)

                val layoutManager =
                    LinearLayoutManager(
                        this@PdpFragment.context!!,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                rvConfigurableOptionsTest?.setLayoutManager(layoutManager)
                rvConfigurableOptionsTest?.adapter = configurAdapter

                val layoutManager1 =
                    LinearLayoutManager(
                        this@PdpFragment.context!!,
                        LinearLayoutManager.VERTICAL,
                        false
                    )
                rvConfigurableOptionsTest1?.setLayoutManager(layoutManager1)
                rvConfigurableOptionsTest1?.adapter = configurAdapter



//                webDescription.setWebViewClient(object : WebViewClient() {
//                    override fun shouldOverrideUrlLoading(view: WebView, url: String?): Boolean {
//                        Log.i("TAG", "Processing webview url click...")
//                        view.loadUrl(url)
//                        return true
//                    }
//                    override fun onPageFinished(view: WebView?, url: String) {
//                        Log.i("TAG", "Finished loading URL: $url")
////                        if (pDialog.isShowing()) {
////                            pDialog.dismiss()
////                        }
//                    }
//                    override fun onReceivedError(
//                        view: WebView?,
//                        errorCode: Int,
//                        description: String,
//                        failingUrl: String?
//                    ) {
//                        Log.e("TAG", "Error: $description")
//                        Toast.makeText(this@PdpFragment.context, "Page Load Error description", Toast.LENGTH_SHORT).show()
//                    }
//                })
//
//                webDescription.getSettings().setJavaScriptEnabled(true)
//                webDescription.getSettings().setUseWideViewPort(true)
//                webDescription.getSettings().setDomStorageEnabled(true);
//                webDescription.getSettings().setAppCacheEnabled(true);
//                webDescription.getSettings().setLoadsImagesAutomatically(true);
//                webDescription.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
//
//                webDescription.loadData(list.description.html, "text/html", "utf-8")


            }

        })

        mainViewModel.getOfferPlatesDetail()
        val offerPlatesLiveData = mainViewModel.offerPlatesLiveData
        offerPlatesLiveData?.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {
                offerPlatesAdapter =
                    OfferPlatesAdapter(
                        this@PdpFragment.context!!, ConvertingOfferPlates(it).data!!,
                        "offerplates"
                    )

                val layoutManager = LinearLayoutManager(
                    this@PdpFragment.context!!,
                    LinearLayoutManager.HORIZONTAL,
                    false
                )
                rvOfferPlates?.setLayoutManager(layoutManager)
                rvOfferPlates?.adapter = offerPlatesAdapter

            }
        })


        var frequent_skusList: ArrayList<Int> = arrayListOf()
        frequent_skusList.add(1560)
        mainViewModel.getFrequentlyBoughtDetail(frequent_skusList) //TODO Static
        val frequentlyBoughtTogetherLiveData = mainViewModel.frequentlyBoughtTogetherLiveData
        frequentlyBoughtTogetherLiveData?.observe(viewLifecycleOwner, Observer { it_Frequent ->
            if (it_Frequent is Resource.Success) {
                var skusList1: ArrayList<String> = arrayListOf()
                it_Frequent.data!!.frequentlyBoughtTogether()!!.get(0).items()!!.forEach {
                    skusList1.add(it.sku()!!)
                }

                mainViewModel.getFrequentProductList(skusList1)
                val productsSkuByListLiveData = mainViewModel.productsSkuByListLiveData
                productsSkuByListLiveData.observe(viewLifecycleOwner, Observer {
                    if (it is Resource.Success) {
                        if (it.data!!.products()!!.items() != null && it.data.products()!!.items()!!
                                .isNotEmpty()
                        ) {
                            val frequentlyBroughtAdapter =
                                FrequentlyBroughtAdapter(
                                    this@PdpFragment.context!!,
                                    SortProductListPDP(
                                        ConvertingList(it).data.products.items,
                                        skusList1
                                    )
                                )
                            val layoutManager =
                                LinearLayoutManager(activity, LinearLayoutManager.VERTICAL, false)
                            rvFrequentlyBrought?.setLayoutManager(layoutManager)
                            rvFrequentlyBrought?.adapter = frequentlyBroughtAdapter
                            frequentlyBroughtAdapter.notifyDataSetChanged()
                        }
                    }
                })
            }
        })


        mainViewModel.getDeliveryOptionsDetail("719836", 1)  //for dev2 delivery options
        val deliverOptionsLiveData = mainViewModel.deliverOptionsLiveData
        deliverOptionsLiveData?.observe(viewLifecycleOwner, Observer { it ->
            if (it is Resource.Success) {


                deliveryOptionsAdapter = DeliveryOptionViewAdapter(
                    ConvertingDeliveryOptions(it).data.deliveryoptions.get(0)
                )
                rvDeliveryOptions?.setAdapter(deliveryOptionsAdapter)
                rvDeliveryOptions?.setHasFixedSize(true)
                val layoutManager: RecyclerView.LayoutManager = LinearLayoutManager(activity)
                rvDeliveryOptions?.setLayoutManager(layoutManager)

                deliveryOptionsAdapter.setOnItemClickListener(this)

                rvDeliveryOptions?.setLayoutManager(layoutManager)
                rvDeliveryOptions?.adapter = deliveryOptionsAdapter
                //lLayout.visibility = View.VISIBLE

                StorePickUpApiCall("18577", 1)


            } else if (it is Resource.Failure) {
            }
        })

        sheetBehavior = BottomSheetBehavior.from(binding.bottomSheet)

        binding.monthlyClick.setOnClickListener(View.OnClickListener {
            if (sheetBehavior!!.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED)
                binding.shadowLayout.setVisibility(View.VISIBLE)
            } else {
                sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                binding.shadowLayout.setVisibility(View.GONE)
            }
        })

        binding.monthlyClose.setOnClickListener(View.OnClickListener {
            if (sheetBehavior!!.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED)
                binding.shadowLayout.setVisibility(View.VISIBLE)
            } else {
                sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                binding.shadowLayout.setVisibility(View.GONE)
            }
        })

        binding.shadowLayout.setOnClickListener(View.OnClickListener {
            if (sheetBehavior!!.getState() != BottomSheetBehavior.STATE_EXPANDED) {
                sheetBehavior!!.setState(BottomSheetBehavior.STATE_EXPANDED)
                binding.shadowLayout.setVisibility(View.VISIBLE)
            } else {
                sheetBehavior!!.setState(BottomSheetBehavior.STATE_COLLAPSED)
                binding.shadowLayout.setVisibility(View.GONE)
            }
        })

        binding.calculationTxt.setOnClickListener {
            binding.calculationTxt.setTextColor(resources.getColor(R.color.price_blue_color))
            binding.aboutTxtTitle.setTextColor(resources.getColor(R.color.black))
            binding.requirmentsTxt.setTextColor(resources.getColor(R.color.black))
            binding.howtoApplyTxt.setTextColor(resources.getColor(R.color.black))

            binding.calculationLt.visibility = View.VISIBLE
            binding.aboutLt.visibility = View.GONE
            binding.requirementLt.visibility = View.GONE
            binding.howToApplyLt.visibility = View.GONE
        }

        binding.aboutTxtTitle.setOnClickListener {

            binding.calculationTxt.setTextColor(resources.getColor(R.color.black))
            binding.aboutTxtTitle.setTextColor(resources.getColor(R.color.price_blue_color))
            binding.requirmentsTxt.setTextColor(resources.getColor(R.color.black))
            binding.howtoApplyTxt.setTextColor(resources.getColor(R.color.black))

            binding.calculationLt.visibility = View.GONE
            binding.aboutLt.visibility = View.VISIBLE
            binding.requirementLt.visibility = View.GONE
            binding.howToApplyLt.visibility = View.GONE
        }

        binding.requirmentsTxt.setOnClickListener {

            binding.calculationTxt.setTextColor(resources.getColor(R.color.black))
            binding.aboutTxtTitle.setTextColor(resources.getColor(R.color.black))
            binding.requirmentsTxt.setTextColor(resources.getColor(R.color.price_blue_color))
            binding.howtoApplyTxt.setTextColor(resources.getColor(R.color.black))

            binding.calculationLt.visibility = View.GONE
            binding.aboutLt.visibility = View.GONE
            binding.requirementLt.visibility = View.VISIBLE
            binding.howToApplyLt.visibility = View.GONE
        }

        binding.howtoApplyTxt.setOnClickListener {

            binding.calculationTxt.setTextColor(resources.getColor(R.color.black))
            binding.aboutTxtTitle.setTextColor(resources.getColor(R.color.black))
            binding.requirmentsTxt.setTextColor(resources.getColor(R.color.black))
            binding.howtoApplyTxt.setTextColor(resources.getColor(R.color.price_blue_color))

            binding.calculationLt.visibility = View.GONE
            binding.aboutLt.visibility = View.GONE
            binding.requirementLt.visibility = View.GONE
            binding.howToApplyLt.visibility = View.VISIBLE
        }


        /**
         * bottom sheet state change listener
         * we are changing button text when sheet changed state
         */
        sheetBehavior!!.setBottomSheetCallback(object : BottomSheetCallback() {
            override fun onStateChanged(view: View, newState: Int) {
                when (newState) {
                    BottomSheetBehavior.STATE_HIDDEN -> {
                    }
                    BottomSheetBehavior.STATE_EXPANDED -> {
                    }
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        binding.shadowLayout.setVisibility(View.GONE)
                    }
                    BottomSheetBehavior.STATE_DRAGGING -> {
                    }
                    BottomSheetBehavior.STATE_SETTLING -> {
                    }
                }
            }

            override fun onSlide(view: View, v: Float) {}
        })


        NewArrivalsApiCall()
        RecommendedApiCall()


        binding.productDetailsRl.setOnClickListener {

            binding.productDescription.visibility =
                if (binding.productDescription.visibility == View.GONE) {
                    binding.productDetailsTxt.setTextColor(activity!!.resources.getColor(R.color.price_blue_color))
                    binding.imgProduct.setImageResource(R.drawable.ic_down_arrorw);
                    View.VISIBLE
                } else {
                    binding.productDetailsTxt.setTextColor(activity!!.resources.getColor(R.color.black))
                    binding.imgProduct.setImageResource(R.drawable.ic_right_arror);
                    View.GONE
                }
        }

        binding.speRl.setOnClickListener {
            binding.specifiRl.visibility =
                if (binding.specifiRl.visibility == View.GONE) {
                    binding.speciTxt.setTextColor(activity!!.resources.getColor(R.color.price_blue_color))
                    binding.imgSpecification.setImageResource(R.drawable.ic_down_arrorw);
                    View.VISIBLE
                } else {
                    binding.speciTxt.setTextColor(activity!!.resources.getColor(R.color.black))
                    binding.imgSpecification.setImageResource(R.drawable.ic_right_arror);
                    View.GONE
                }
        }

        binding.OtherBuyingRl.setOnClickListener {
            binding.rvConfigurableOptionsTest1.visibility =
                if (binding.rvConfigurableOptionsTest1.visibility == View.GONE) {
                    binding.otherTxt.setTextColor(activity!!.resources.getColor(R.color.price_blue_color))
                    binding.imgOther.setImageResource(R.drawable.ic_down_arrorw);
                    View.VISIBLE
                } else {
                    binding.otherTxt.setTextColor(activity!!.resources.getColor(R.color.black))
                    binding.imgOther.setImageResource(R.drawable.ic_right_arror);
                    View.GONE
                }
        }

        binding.reviewsRl.setOnClickListener {
            binding.reviewTxtDEscr.visibility =
                if (binding.reviewTxtDEscr.visibility == View.GONE) {
                    binding.reviewTxt1.setTextColor(activity!!.resources.getColor(R.color.price_blue_color))
                    binding.imgReview.setImageResource(R.drawable.ic_down_arrorw);
                    View.VISIBLE
                } else {
                    binding.reviewTxt1.setTextColor(activity!!.resources.getColor(R.color.black))
                    binding.imgReview.setImageResource(R.drawable.ic_right_arror);
                    View.GONE
                }
        }

        binding.rdRl.setOnClickListener {
            binding.qaTxtDEscr.visibility =
                if (binding.qaTxtDEscr.visibility == View.GONE) {
                    binding.qaTxt.setTextColor(activity!!.resources.getColor(R.color.price_blue_color))
                    binding.imgQa.setImageResource(R.drawable.ic_down_arrorw);
                    View.VISIBLE
                } else {
                    binding.qaTxt.setTextColor(activity!!.resources.getColor(R.color.black))
                    binding.imgQa.setImageResource(R.drawable.ic_right_arror);
                    View.GONE
                }
        }

        binding.proCompariRl.setOnClickListener {
            binding.proTxtDEscr.visibility =
                if (binding.proTxtDEscr.visibility == View.GONE) {
                    binding.proTxt.setTextColor(activity!!.resources.getColor(R.color.price_blue_color))
                    binding.imgPro.setImageResource(R.drawable.ic_down_arrorw);
                    View.VISIBLE
                } else {
                    binding.proTxt.setTextColor(activity!!.resources.getColor(R.color.black))
                    binding.imgPro.setImageResource(R.drawable.ic_right_arror);
                    View.GONE
                }
        }


        if (mHistoryList == null) {
            mHistoryList = ArrayList<BrowseSavingModel>()
        }


    }

    // code is self explanatory
    // you can make it more simple by inline conditioning I have kept this way for readability
    private fun getDrawableResourceForDot(index: Int, isActive: Boolean): Int {
        val item = models[index]
        return if (isActive)
            if (item.isVideo)
                R.drawable.ic_play_active
            else
                R.drawable.ic_dot_active
        else
            if (item.isVideo)
                R.drawable.ic_play_inactive
            else
                R.drawable.ic_dot_inactive
    }


    fun saveArray(
        array: Array<String?>,
        arrayName: String,
        mContext: Context
    ): Boolean {
        val prefs: SharedPreferences = mContext.getSharedPreferences("preferencename", 0)
        val editor: SharedPreferences.Editor = prefs.edit()
        editor.putInt(arrayName + "_size", array.size)
        for (i in array.indices) editor.putString(arrayName + "_" + i, array[i])
        return editor.commit()
    }

    fun loadArray(arrayName: String, mContext: Context): Array<String?>? {
        val prefs: SharedPreferences = mContext.getSharedPreferences("preferencename", 0)
        val size: Int = prefs.getInt(arrayName + "_size", 0)
        val array = arrayOfNulls<String>(size)
        for (i in 0 until size) array[i] = prefs.getString(arrayName + "_" + i, null)
        return array
    }

    private fun StorePickUpApiCall(sku: String, qty: Int) {
        mainViewModel.getStorePickupDetail(sku, qty)
        val storePickupQuery = mainViewModel.storePickupQuery
        storePickupQuery.observe(viewLifecycleOwner, Observer { it5 ->
            if (it5 is Resource.Success) {

                val item =
                    DOption(9554444, "Store pickup", 143, "store") //Adding lost item by statically
                deliveryOptionsAdapter.addItem(item)

                storePickUpAdapter =
                    StorePickUpAdapter(
                        this@PdpFragment.context!!,
                        ConvertingStorePickUp(it5).data.getSourceStores
                    )

                val layoutManager = LinearLayoutManager(
                    this@PdpFragment.context!!,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                rvStorePickup?.setLayoutManager(layoutManager)
                rvStorePickup?.adapter = storePickUpAdapter
                // ltStore.visibility = View.VISIBLE
                storePickUpAdapter.setOnItemClickListener(this)

            }
        })
    }

    private fun MonthlyInstallments(sku: String, amount: String, proName: String) {

        mainViewModel.getInstallmentsDetail(sku, amount)
        val installmentsLiveData = mainViewModel.installmentsLiveData
        installmentsLiveData.observe(viewLifecycleOwner, Observer { it5 ->
            if (it5 is Resource.Success) {

                if (ConvertingMonthlyInstallments(it5).data.minstallments.installmentinfo != null) {
                    binding.ltMonthly.visibility = View.VISIBLE
                    binding.ltMonthlyPrice.visibility = View.VISIBLE
                } else {
                    binding.ltMonthly.visibility = View.GONE
                    binding.ltMonthlyPrice.visibility = View.GONE
                }

                var emi_calculation =
                    ConvertingMonthlyInstallments(it5).data.minstallments.installmentinfo.get(
                        ConvertingMonthlyInstallments(it5).data.minstallments.installmentinfo!!.size - 1
                    ).emi_calculation

                val interval =
                    ConvertingMonthlyInstallments(it5).data.minstallments.installmentinfo.get(
                        ConvertingMonthlyInstallments(it5).data.minstallments.installmentinfo.size - 1
                    ).interval

                binding.txtMonthlyInstall.text =
                    Utils.DecimalLimitter(emi_calculation) + "KD" + "/" + interval.toString() + "m"

                binding.txtMonthlyInstall1.text =
                    Utils.DecimalLimitter(emi_calculation) + "KD" + "/" + interval.toString() + "m"

                installmentsAdapter =
                    InstallmentsAdapter(
                        this@PdpFragment.context!!,
                        ConvertingMonthlyInstallments(it5).data.minstallments.installmentinfo
                    )

                val layoutManager = LinearLayoutManager(
                    this@PdpFragment.context!!,
                    LinearLayoutManager.VERTICAL,
                    false
                )
                binding.rvInstallments?.setLayoutManager(layoutManager)
                binding.rvInstallments?.adapter = installmentsAdapter

                binding.aboutTxt.text =
                    html2text(ConvertingMonthlyInstallments(it5).data.minstallments.about)
                binding.requirementTxt.text =
                    html2text(ConvertingMonthlyInstallments(it5).data.minstallments.requirements)
                binding.howToApplyTxt.text =
                    html2text(ConvertingMonthlyInstallments(it5).data.minstallments.how_to_apply)

            }
        })
    }

    fun NewArrivalsApiCall() {
        mainViewModel.getNewArrivalsInfo(null)
        val newsArrivalsLiveData = mainViewModel.newArrivalsLiveData
        newsArrivalsLiveData.observe(viewLifecycleOwner, Observer { it5 ->
            if (it5 is Resource.Success) {

                var skusList: ArrayList<String> = arrayListOf()
                it5.data!!.newArrivalsproducts()!!.forEach {
                    skusList.add(it.sku()!!)
                }
                mainViewModel.getProductsLayoutInfo(skusList)
                val newArrivalsProductsLiveData = mainViewModel.newArrivalsProductsLiveData
                newArrivalsProductsLiveData.observe(viewLifecycleOwner, Observer {
                    if (it is Resource.Success) {
                        if (it.data!!.products()!!.items() != null && it.data.products()!!.items()!!
                                .isNotEmpty()
                        ) {
                            val newArrivalsadapter =
                                NewArrivalsAdapter(
                                    SortProductListPDP(
                                        ConvertingList(it).data.products.items,
                                        skusList
                                    ),
                                    this@PdpFragment.context!!,
                                    this,
                                    "new_arrivals"
                                )
                            val layoutManager =
                                ItemLinearLayoutManager(
                                    this@PdpFragment.context!!,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            rvNewArrivals?.setLayoutManager(layoutManager)
                            rvNewArrivals?.adapter = newArrivalsadapter

                            val layoutManager1 =
                                ItemLinearLayoutManager(
                                    this@PdpFragment.context!!,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )

                            rvTopRated?.setLayoutManager(layoutManager1)
                            rvTopRated?.adapter = newArrivalsadapter

                            newArrivalsadapter.notifyDataSetChanged()


                        }
                    }
                })
            }
        })
    }

    fun RecommendedApiCall() {
        mainViewModel.getRecommendedInfo(null)
        val recommendedLiveData = mainViewModel.recommendedLiveData
        recommendedLiveData.observe(viewLifecycleOwner, Observer { it6 ->
            if (it6 is Resource.Success) {

                var skusList: ArrayList<String> = arrayListOf()
                it6.data!!.newArrivalsproducts()!!.forEach {
                    skusList.add(it.sku()!!)
                }
                mainViewModel.getRecommendedProductsLayoutInfo(skusList)
                val recommendedProductsLiveData = mainViewModel.recommendedProductsLiveData
                recommendedProductsLiveData.observe(viewLifecycleOwner, Observer {
                    if (it is Resource.Success) {
                        if (it.data!!.products()!!.items() != null && it.data.products()!!.items()!!
                                .isNotEmpty()
                        ) {
                            val newArrivalsadapter =
                                NewArrivalsAdapter(
                                    SortProductListPDP(
                                        ConvertingList(it).data.products.items,
                                        skusList
                                    ),
                                    this@PdpFragment.context!!,
                                    this,
                                    "recommended"
                                )
                            val layoutManager =
                                ItemLinearLayoutManager(
                                    this@PdpFragment.context,
                                    LinearLayoutManager.HORIZONTAL,
                                    false
                                )
                            rvRecommended?.setLayoutManager(layoutManager)
                            rvRecommended?.adapter = newArrivalsadapter
                            newArrivalsadapter.notifyDataSetChanged()
                        }
                    }
                })

            }
        })
    }

    fun setUpImgSliders(slidersData: BaseSingleProductDetails) {
        if (slidersData.data!!.products.items.get(0).media_gallery != null) {

            models.add(
                PdpImagesModel(
                    0,
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/ForBiggerBlazes.mp4",
                    "video",
                    true
                )
            )


            slidersData.data!!.products.items.get(0).media_gallery.forEach {
                if (it.video_content != null) {
                    models.add(
                        PdpImagesModel(
                            0,
                            it.url,
                            it.video_content!!.video_url,
                            it.video_content!!.media_type,
                            true
                        )
                    )
                } else {
                    models.add(PdpImagesModel(0, it.url, "", "normal", false))
                }

            }


            models.add(
                PdpImagesModel(
                    0,
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/images/BigBuckBunny.jpg",
                    "https://commondatastorage.googleapis.com/gtv-videos-bucket/sample/BigBuckBunny.mp4",
                    "video",
                    true
                )
            )


            pagerAdapter = PdpViewPagerAdapter(this@PdpFragment, models, "PdpFragment")
            view_pager.adapter = pagerAdapter
            view_pager.orientation = ViewPager2.ORIENTATION_HORIZONTAL

//            pdpImageAdapter = PdpImageAdapter(models, activity!!, this)
//            view_pager.adapter = pdpImageAdapter

            view_pager.setPadding(30, 0, 30, 0)
            dotscount = models.size

            val dots = arrayOfNulls<ImageView>(dotscount)

            // add images of required dots (circle, play) to the custom(linearLayout) indication
            for (i in 0 until dotscount) {
                val imageView = ImageView(activity)
                imageView.layoutParams = LinearLayout.LayoutParams(10, 10)

                dots[i] = imageView

                val resId = getDrawableResourceForDot(i, false)

                dots[i]!!.setImageDrawable(ContextCompat.getDrawable(activity!!, resId))
                val params = LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT
                )
                params.setMargins(8, 0, 8, 0)
                slider_dots!!.addView(dots[i], params)
            }

            val resId = getDrawableResourceForDot(
                0,
                true
            ) // First indication is active initially when screen opens
            dots[0]?.setImageDrawable(ContextCompat.getDrawable(activity!!, resId))

//        view_pager.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {
//            override fun onPageScrollStateChanged(state: Int) {}
//
//            override fun onPageScrolled(
//                position: Int,
//                positionOffset: Float,
//                positionOffsetPixels: Int
//            ) {
//            }
//
//            override fun onPageSelected(position: Int) {
//
//                // mark all inactive when a page is selected
//                for (i in 0 until dotscount) {
//                    dots[i]!!.setImageDrawable(
//                        ContextCompat.getDrawable(
//                            activity!!,
//                            getDrawableResourceForDot(i, false)
//                        )
//                    )
//                }
//
//                // mark the current selected active & use respective active icons
//                dots[position]?.setImageDrawable(
//                    ContextCompat.getDrawable(
//                        activity!!,
//                        getDrawableResourceForDot(position, true)
//                    )
//                )
//            }
//        })

            view_pager.registerOnPageChangeCallback(object : ViewPager2.OnPageChangeCallback() {
                //                var currentPosition = 0
                override fun onPageScrollStateChanged(state: Int) {
                    super.onPageScrollStateChanged(state)
                }

                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {
                    super.onPageScrolled(position, positionOffset, positionOffsetPixels)
                }

                override fun onPageSelected(position: Int) {
                    super.onPageSelected(position)

//                    val fragmentToHide: FragmentLifecycle = pagerAdapter.getItem(currentPosition) as FragmentLifecycle
//                    fragmentToHide.onPauseFragment()
//
//                    val fragmentToShow: FragmentLifecycle = pagerAdapter.getItem(position) as FragmentLifecycle
//                    fragmentToShow.onResumeFragment()
//                    currentPosition = position


                    Log.e("Selected_Page", position.toString())
                    // mark all inactive when a page is selected
                    for (i in 0 until dotscount) {
                        dots[i]!!.setImageDrawable(
                            ContextCompat.getDrawable(
                                activity!!,
                                getDrawableResourceForDot(i, false)
                            )
                        )
                    }
                    // mark the current selected active & use respective active icons
                    dots[position]?.setImageDrawable(
                        ContextCompat.getDrawable(
                            activity!!,
                            getDrawableResourceForDot(position, true)
                        )
                    )
                }

            })


        }


    }

    override fun onItemClick(sku: String, item: ProductItem?, from: String) {
    }

    override fun onItemClickListener(position: Int, from: String, shortForm: String) {
        deliveryOptionsAdapter.selectedItem()
        if (from.equals("Store pickup"))
            ltStore.visibility = View.VISIBLE
        else
            ltStore.visibility = View.GONE
    }

    override fun onStoreItemClickListener(position: Int, from: String, shortForm: String) {
        storePickUpAdapter.selectedItem()
    }


    fun SaveBrowsingHistoryApiCall(id: String, sku: String) {

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
                entityFrmLocalHistory.add(Integer.parseInt(id))

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

            } else {

                var idList: ArrayList<Int> = arrayListOf()
                idList.add(Integer.parseInt(id))

                mainViewModel.addBrosingHistory(idList)
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

        } else {

            var list = sharedStorageHistory!!.getProductClickId()!!

            if (list != null && !list.isEmpty()) {
                loadData(id, sku)
            } else {
                saveData(id, sku)
            }

        }

    }

    private fun saveData(id: String, sku: String) {

        mHistoryList!!.add(BrowseSavingModel(id, sku))
        val gson = Gson()
        val json = gson.toJson(mHistoryList)
        sharedStorageHistory!!.setProductClickId(json)!!

    }


    private fun loadData(id: String, sku: String) {

        val gson = Gson()
        val json: String = sharedStorageHistory!!.getProductClickId()!!
        val type: Type = object : TypeToken<ArrayList<BrowseSavingModel?>?>() {}.getType()
        mHistoryList = gson.fromJson(json, type)

        if (mHistoryList == null) {
            mHistoryList = ArrayList<BrowseSavingModel>()
        }

        saveData(id, sku)

    }

}

