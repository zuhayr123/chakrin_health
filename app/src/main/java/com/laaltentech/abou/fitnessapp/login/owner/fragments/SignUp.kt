package com.laaltentech.abou.fitnessapp.login.owner.fragments

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.databinding.FragmentSignupBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import javax.inject.Inject

class SignUp : Fragment(), Injectable{

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding : FragmentSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_signup, container, false)
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        Log.e("Visible", "The view was visible")
        initLayoutAnim()
        super.onActivityCreated(savedInstanceState)
    }

    private fun initLayoutAnim(){
        binding.firstName.translationY = -1600f
        binding.regPhoneNumber.translationY = -1600f
        binding.lastName.translationY = -1600f
        binding.password.translationY = -1600f
        binding.confirmPassword.translationY = -1600f
        binding.regEmailId.translationY = -1600f
        binding.addressLine3.translationY = -1600f
        binding.addressLine2.translationY = -1600f
        binding.addressLine1.translationY = -1600f
        binding.industryName.translationY = -1600f
        binding.chipGroup.translationY = -1600f
        binding.profileImageText.translationY = -1600f
        binding.addProfileImage.translationY = -1600f

        binding.addProfileImage.apply {
            animate().translationYBy(1600f).duration = 850
        }

        binding.profileImageText.apply {
            animate().translationYBy(1600f).duration = 850
        }

        binding.chipGroup.apply {
            animate().translationYBy(1600f).duration = 800
        }
        binding.industryName.apply {
            animate().translationYBy(1600f).duration = 750
        }

        binding.firstName.apply {
            animate().translationYBy(1600f).duration = 700
        }

        binding.lastName.apply {
            animate().translationYBy(1600f).duration = 700
        }

        binding.regPhoneNumber.apply {
            animate().translationYBy(1600f).duration = 650
        }

        binding.password.apply {
            animate().translationYBy(1600f).duration = 600
        }

        binding.confirmPassword.apply {
            animate().translationYBy(1600f).duration = 550
        }

        binding.regEmailId.apply {
            animate().translationYBy(1600f).duration = 500
        }

        binding.addressLine1.apply {
            animate().translationYBy(1600f).duration = 450
        }

        binding.addressLine2.apply {
            animate().translationYBy(1600f).duration = 400
        }

        binding.addressLine3.apply {
            animate().translationYBy(1600f).duration = 350
        }
    }

}