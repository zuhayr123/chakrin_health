package com.laaltentech.abou.fitnessapp.login.owner.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.bottomnav.owner.activity.BottomMainNavActivity
import com.laaltentech.abou.fitnessapp.databinding.FragmentFlashLayoutBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.CONSTANTS
import javax.inject.Inject


class FragmentSplashScreen : Fragment(), Injectable {

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding: FragmentFlashLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_flash_layout,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        activity?.window?.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);

        (activity as AppCompatActivity?)!!.supportActionBar!!.hide()
        val sharedPref = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val phoneNumber = sharedPref.getString(CONSTANTS.PHONE_NUMBER, "")

        val h = Handler()

        if (!phoneNumber.isNullOrBlank()) {
            h.postDelayed(Runnable {
                val intent = Intent(context, BottomMainNavActivity::class.java)
                this.startActivity(intent)
                activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
            }, 4500)
        } else {
            h.postDelayed(Runnable {
                val action =
                    FragmentSplashScreenDirections.actionFragmentSplashScreenToFragmentLoginOrSignup()
                findNavController().navigate(action)
            }, 4500)
        }

        binding.logoImage.startAnimation(
            AnimationUtils.loadAnimation(activity, R.anim.rotate_indefinitely)
        )
    }
}