package com.laaltentech.abou.fitnessapp.bottomnav.owner.activity

import android.os.Bundle
import android.util.Log
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.databinding.ActivityBottomMainLayoutBinding
import com.laaltentech.abou.fitnessapp.util.OnBackPressed
import com.razorpay.PaymentResultListener
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.android.synthetic.main.activity_bottom_main_layout.*
import kotlinx.android.synthetic.main.activity_camera_layout.*
import kotlinx.android.synthetic.main.activity_camera_layout.nav_host
import javax.inject.Inject

class BottomMainNavActivity : AppCompatActivity(), HasSupportFragmentInjector,
    PaymentResultListener {

    lateinit var binding: ActivityBottomMainLayoutBinding

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Fragment>

    override fun supportFragmentInjector(): DispatchingAndroidInjector<Fragment> = dispatchingAndroidInjector

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_bottom_main_layout)
        val navView: BottomNavigationView = findViewById(R.id.nav_view)
        val navController = findNavController(R.id.nav_host_fragment)

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_home, R.id.navigation_meditate, R.id.navigation_profile
            )
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)

        Log.e("Layout", "this layout was  created")
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Log.e("onback","on options item selected was called")
        Log.e("itemId", "the item id is ${item.itemId} and item name is ${android.R.id.home}")
        return if(item.itemId == android.R.id.home){
            onBackPressed()
            true
        } else false
    }

    override fun onBackPressed() {
        Log.e("onback","On back pressed was called")
        val currentFragment = nav_host_fragment.childFragmentManager.fragments[0]
        if (currentFragment is OnBackPressed)
            currentFragment.onBackPressed()

        else{
            super.onBackPressed()
        }
    }

    override fun onPaymentSuccess(p0: String?) {
        Toast.makeText(this, "Payment Success", Toast.LENGTH_LONG).show()
    }

    override fun onPaymentError(p0: Int, p1: String?) {
        Toast.makeText(this, "Oops! Payment Failed!!", Toast.LENGTH_LONG).show()
    }
}