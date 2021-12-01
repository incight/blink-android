package com.blink.blinkshopping.ui.lTwoCategory

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.blink.blinkshopping.R
import com.blink.blinkshopping.ui.allcategory.AllCategoryViewModel
import com.blink.blinkshopping.ui.allcategory.SharedViewModel
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.AndroidSupportInjection
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.fragment_l3_category.*
import javax.inject.Inject


class L3CategoryFragment : Fragment(), HasSupportFragmentInjector {
    private lateinit var selectItemViewModel: SharedViewModel
    private lateinit var allCategoryViewModel: AllCategoryViewModel
    private lateinit var adapter: L3SubCategoryAdapter

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_l3_category, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        AndroidSupportInjection.inject(this)
        allCategoryViewModel = ViewModelProvider(
            requireActivity(),
            viewModelFactory
        ).get(AllCategoryViewModel::class.java)


        allCategoryViewModel.categoryUiData.observe(viewLifecycleOwner, Observer {

            allCategoryViewModel.L2ProPosition.observe(viewLifecycleOwner, Observer { it1 ->
                val dataList = it[it1].children.toMutableList()
                adapter =
                    L3SubCategoryAdapter(requireContext(), dataList)
                rvlthree.adapter = adapter
                adapter.notifyDataSetChanged()

            })
        })




    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return dispatchingAndroidInjector
    }
}