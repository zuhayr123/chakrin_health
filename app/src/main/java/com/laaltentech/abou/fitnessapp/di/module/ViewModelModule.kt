package com.laaltentech.abou.fitnessapp.di.module

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.laaltentech.abou.fitnessapp.di.ViewModelKey
import com.laaltentech.abou.fitnessapp.factory.AppModelFactory
import com.laaltentech.abou.fitnessapp.game.observer.GameDataViewModel
import com.laaltentech.abou.fitnessapp.login.viewmodels.LoginViewModel
import com.laaltentech.abou.fitnessapp.login.viewmodels.SignUpViewModel
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
    @IntoMap
    @ViewModelKey(SignUpViewModel::class)
    abstract fun bindSignUpViewModel(newSignUpViewModel: SignUpViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(LoginViewModel::class)
    abstract fun bindLoginViewModel(newLoginViewModel: LoginViewModel): ViewModel

    @Binds
    abstract fun bindViewModelFactory(factory: AppModelFactory): ViewModelProvider.Factory
}