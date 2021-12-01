package com.blink.blinkshopping.di.module

import com.blink.blinkshopping.ui.allcategory.AllCategory_L0_Fragment
//import com.blink.blinkshopping.ui.allcategory.DetialViewFragment
import com.blink.blinkshopping.ui.categoryFragment.CategoryFragment
import com.blink.blinkshopping.ui.lOneCategory.LOnePageCategoryFragment
import com.blink.blinkshopping.ui.login.LoginFragment
import com.blink.blinkshopping.ui.lTwoCategory.L3CategoryFragment
import com.blink.blinkshopping.ui.lTwoCategory.LTwoPageCategoryFragment
import com.blink.blinkshopping.ui.home.HomeFragment
import com.blink.blinkshopping.ui.login.RegistrationFragment
import com.blink.blinkshopping.ui.pdp.PdpFragment
//import com.blink.blinkshopping.ui.videoplayer.VideoPlayerFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeCategoryFragment(): CategoryFragment

    @ContributesAndroidInjector
    abstract fun contributePdpFragment(): PdpFragment

    @ContributesAndroidInjector
    abstract fun loginPdpFragment(): LoginFragment

    @ContributesAndroidInjector
    abstract fun registationFragment(): RegistrationFragment

//    @ContributesAndroidInjector
//    abstract fun contributeVideoPlayerFragment(): VideoPlayerFragment


    @ContributesAndroidInjector
    abstract fun contributeAllCategoryFragment(): AllCategory_L0_Fragment

//    @ContributesAndroidInjector
//    abstract fun contributeDetialViewFragment(): DetialViewFragment

    @ContributesAndroidInjector
    abstract fun contributeCategoryDetialsFragment(): LOnePageCategoryFragment

    @ContributesAndroidInjector
    abstract fun categoryLTwo(): LTwoPageCategoryFragment

    @ContributesAndroidInjector
    abstract fun category3Contributor(): L3CategoryFragment

}
