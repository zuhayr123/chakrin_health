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
import com.laaltentech.abou.fitnessapp.databinding.LoginOrSignupBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject

class LoginOrSignupChoose : Fragment(), Injectable {

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: LoginOrSignupBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        Log.e("TAG", "fragment was tried to be inflated")
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.login_or_signup,
            container,
            false
        )
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        initLayoutAnim()
    }

    private fun initLayoutAnim(){
        binding.login.translationX = -1600f
        binding.signUp.translationX = -1600f
        binding.login.apply {
            animate().translationXBy(1600f).duration = 500
        }
        binding.signUp.apply {
            animate().translationXBy(1600f).duration = 800
        }
    }
}