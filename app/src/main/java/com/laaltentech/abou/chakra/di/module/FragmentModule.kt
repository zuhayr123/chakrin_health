package com.laaltentech.abou.chakra.di.module

import com.laaltentech.abou.chakra.game.owner.fragments.GameInstructionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {
    //todo just an example to add fragments here
    @ContributesAndroidInjector
    abstract fun contributeFlickrMainFragment(): GameInstructionsFragment
}