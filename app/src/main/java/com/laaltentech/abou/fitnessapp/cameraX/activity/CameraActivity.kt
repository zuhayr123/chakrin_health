package com.laaltentech.abou.fitnessapp.cameraX.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.databinding.ActivityCameraLayoutBinding
import com.laaltentech.abou.fitnessapp.util.OnBackPressed
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_camera_layout.*
import javax.inject.Inject

class CameraActivity : AppCompatActivity(), HasSupportFragmentInjector {
    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> = dispatchingAndroidInjector

    lateinit var binding: ActivityCameraLayoutBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_camera_layout)
    }

    override fun onBackPressed() {
        val currentFragment = nav_host.childFragmentManager.fragments[0]
        if (currentFragment is OnBackPressed)
            currentFragment.onBackPressed()
        else
            super.onBackPressed()
    }
}