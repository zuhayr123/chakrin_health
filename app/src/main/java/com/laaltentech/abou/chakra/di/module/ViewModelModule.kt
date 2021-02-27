package com.laaltentech.abou.chakra.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.laaltentech.abou.chakra.di.ViewModelKey
import com.laaltentech.abou.chakra.factory.AppModelFactory
import com.laaltentech.abou.chakra.game.observer.GameDataViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Suppress("unused")
@Module
abstract class ViewModelModule {

    //todo add these details to the app model factory
    @Binds
    @IntoMap
    @ViewModelKey(GameDataViewModel::class)
    abstract fun bindGameDataViewModel(newGameDataViewModel: GameDataViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppModelFactory): ViewModelProvider.Factory
}