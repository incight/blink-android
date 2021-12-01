package com.blink.blinkshopping.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.blink.blinkshopping.base.ViewModelFactory
import com.blink.blinkshopping.base.ViewModelKey
import com.blink.blinkshopping.ui.allcategory.AllCategoryViewModel
import com.blink.blinkshopping.ui.categoryFragment.CategoryFragmentViewModel
import com.blink.blinkshopping.ui.lOneCategory.L1ViewModel
import com.blink.blinkshopping.ui.home.HomeViewModel
import com.blink.blinkshopping.ui.login.LoginViewModel
import com.blink.blinkshopping.ui.main.MainViewModel
import com.blink.blinkshopping.ui.pdp.PdpViewModel
import com.blink.blinkshopping.ui.splash.SplashScreenViewModel
import com.blink.blinkshopping.ui.videoplayer.VideoPlayeryFragmentViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class ViewModelModule {

    @Binds
    internal abstract fun bindViewModelFactory(factory: ViewModelFactory): ViewModelProvider.Factory

    @Binds
    @IntoMap
    @ViewModelKey(SplashScreenViewModel::class)
    internal abstract fun bindSplashViewModel(splashScreenViewModel: SplashScreenViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    internal abstract fun bindMainViewModel(mainViewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(HomeViewModel::class)
    internal abstract fun bindHomeViewModel(homeViewModel: HomeViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(L1ViewModel::class)
    internal abstract fun bindL1ViewModel(l1ViewModel: L1ViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(CategoryFragmentViewModel::class)
    internal abstract fun bindCategoryViewModel(categoryFragmentViewModel: CategoryFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PdpViewModel::class)
    internal abstract fun bindPdpViewModel(pdpFragmentViewModel: PdpViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    internal abstract fun bindLoginViewModel(loginFragmentViewModel: LoginViewModel): ViewModel


    @Binds
    @IntoMap
    @ViewModelKey(VideoPlayeryFragmentViewModel::class)
    internal abstract fun bindVideoPlayerViewModel(videoPlayeryFragmentViewModel: VideoPlayeryFragmentViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AllCategoryViewModel::class)
    internal abstract fun bindallCategoryViewModel(allCategoryViewModel: AllCategoryViewModel): ViewModel


    /*@Binds
    @IntoMap
    @ViewModelKey(PokemonDetailViewModel::class)
    internal abstract fun bindPokemonDetailViewModel(pokemonDetailViewModel: PokemonDetailViewModel): ViewModel
    */
}