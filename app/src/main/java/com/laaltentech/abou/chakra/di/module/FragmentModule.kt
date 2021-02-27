package com.laaltentech.abou.chakra.di.module

import com.laaltentech.abou.chakra.bottomnav.owner.fragments.HomeFragment
import com.laaltentech.abou.chakra.bottomnav.owner.fragments.MeditateFragment
import com.laaltentech.abou.chakra.bottomnav.owner.fragments.ProfileFragment
import com.laaltentech.abou.chakra.game.owner.fragments.GameInstructionsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Suppress("unused")
@Module
abstract class FragmentModule {
    //todo just an example to add fragments here
    @ContributesAndroidInjector
    abstract fun contributeFlickrMainFragment(): GameInstructionsFragment

    @ContributesAndroidInjector
    abstract fun contributeHomeFragment(): HomeFragment

    @ContributesAndroidInjector
    abstract fun contributeMeditateFragment(): MeditateFragment

    @ContributesAndroidInjector
    abstract fun contributeProfileFragment(): ProfileFragment
}