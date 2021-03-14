package com.laaltentech.abou.fitnessapp.login.owner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.transition.TransitionInflater
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.databinding.FragmentLoginBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import io.alterac.blurkit.BlurKit
import javax.inject.Inject

class Login : Fragment(), Injectable{

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding : FragmentLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        BlurKit.init(requireContext())
        sharedElementEnterTransition = TransitionInflater.from(context).inflateTransition(android.R.transition.move)

        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(
            inflater,
            R.layout.fragment_login,
            container,
            false)
        initLayoutAnim()
        return binding.root
    }

    private fun initLayoutAnim(){
        binding.regPhoneNumber.translationY = -1600f
        binding.regPwd.translationY = -1600f
        binding.regPhoneNumber.apply {
            animate().translationYBy(1600f).duration = 800
        }
        binding.regPwd.apply {
            animate().translationYBy(1600f).duration = 500
        }
    }
}