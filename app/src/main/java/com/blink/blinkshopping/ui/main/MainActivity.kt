package com.blink.blinkshopping.ui.main

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.NavController
import androidx.navigation.Navigation
import androidx.navigation.fragment.findNavController
import com.blink.blinkshopping.R
import com.blink.blinkshopping.R.id.nav_host_fragment
import com.blink.blinkshopping.base.BaseDaggerActivity
import com.blink.blinkshopping.databinding.ActivityMainBinding
import com.blink.blinkshopping.ui.home.HomeFragment
import com.blink.blinkshopping.ui.login.LoginFragment
import com.blink.blinkshopping.ui.login.RegistrationFragment
import com.blink.blinkshopping.ui.pdp.PdpFragment
import com.blink.blinkshopping.util.SharedStorage
import com.blink.blinkshopping.utils.navigate
import com.blink.blinkshopping.utils.setSingleClickListener
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.app_bar_home.*
import kotlinx.android.synthetic.main.app_bar_home.view.*
import javax.inject.Inject


class MainActivity : BaseDaggerActivity(), HasSupportFragmentInjector,
    LoginFragment.ItemClickListener,RegistrationFragment.ItemClickListener {
    var navController: NavController? = null

    var sharedStorage: SharedStorage? = null

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    lateinit var binding: ActivityMainBinding

    private lateinit var mainViewModel: MainViewModel
    override fun supportFragmentInjector() = dispatchingAndroidInjector

    lateinit var loginDialogFragment: LoginFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)

        navController = Navigation.findNavController(this@MainActivity, nav_host_fragment)

        mainViewModel = ViewModelProviders.of(this, viewModelFactory).get(MainViewModel::class.java)

        sharedStorage = SharedStorage.getInstance(this@MainActivity)

        if (sharedStorage!!.isLogin) {
            lay_user.visibility = View.GONE
            lay_star.visibility = View.VISIBLE
        } else {
            lay_star.visibility = View.GONE
            lay_user.visibility = View.VISIBLE
        }



        lay_user.setSingleClickListener {

            loginDialogFragment = LoginFragment()
            val transaction: FragmentTransaction = supportFragmentManager.beginTransaction()
            transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
            transaction.add(R.id.nav_host_fragment, loginDialogFragment)
            transaction.addToBackStack("loginFragmentfromUser")
            transaction.commit()


        }

        layCart.setOnClickListener {
        }

        //  hanldeHomePage()
    }

    override fun onItemClick(item: String?) {
        if (item.equals("closeSheet")) {
            // loginDialogFragment.dismiss()
        }
    }

    override fun onLoggedIn(item: Boolean?) {
        binding.coordinatorLayout.lay_user.visibility = View.GONE
        binding.coordinatorLayout.lay_star.visibility = View.VISIBLE
    }

    fun toolBarHandle(from: String) {

    }

    fun hanldeHomePage() {

        val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()
        val homeFragment = HomeFragment()
        // transaction.addToBackStack("homeFragment")
        transaction.add(R.id.frame_layout, homeFragment)
        transaction.commit()

    }

    fun hanldePdpPage(from: String, sku: String) {

        val bundle = Bundle()
        bundle.putString("sku", sku)

        val transaction: FragmentTransaction = getSupportFragmentManager().beginTransaction()

        val pdpFragment = PdpFragment()
        pdpFragment.setArguments(bundle)
        transaction.replace(R.id.frame_layout, pdpFragment)
        transaction.addToBackStack(null)
//        transaction.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_FADE)
        transaction.commit()

    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        super.onRestoreInstanceState(savedInstanceState)
    }


    override fun onBackPressed() {
        supportFragmentManager.findFragmentById(R.id.nav_host_fragment)
        if(supportFragmentManager.backStackEntryCount>=1){
            supportFragmentManager.popBackStack()
        }else{
            finish()
        }

       /* if (!navController!!.popBackStack()) {
            finish()
        }*/
    }

}




//  supportFragmentManager.popBackStack()
//   getFragmentManager().popBackStack("AllCategory_L0_Fragment", 0); // Exclusive


//        val sideMenuMainLiveData = mainViewModel.sideMenuMainLiveData
//        binding.lifecycleOwner = this
//        val bundle = Bundle()
//        bundle.putString("layout", "home")
//        val bundle1 = Bundle()
//        bundle1.putString("layout", "category_1")
//        bundle1.putString("clickedId", "1915")










//        NavHostFragment.findNavController(parentFragment!!).navigate(R.id.action_homeFragment_to_videoFragment, bundle)
//        Navigation.findNavController(view!!).navigate(R.id.pdpFragment, bundle)










