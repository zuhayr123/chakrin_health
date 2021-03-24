package com.laaltentech.abou.fitnessapp.login.owner.fragments

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.transition.TransitionInflater
import com.google.gson.Gson
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.bottomnav.owner.activity.BottomMainNavActivity
import com.laaltentech.abou.fitnessapp.databinding.FragmentLoginBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.login.data.SignUpResponse
import com.laaltentech.abou.fitnessapp.login.viewmodels.LoginViewModel
import com.laaltentech.abou.fitnessapp.network.Status
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.Commons
import io.alterac.blurkit.BlurKit
import javax.inject.Inject

class Login : Fragment(), Injectable{

    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    lateinit var binding : FragmentLoginBinding

    private val loginViewModel : LoginViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelFactory)
            .get(LoginViewModel::class.java)
    }

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

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModelInit()
        binding.loginViewModel = loginViewModel

        binding.login.setOnClickListener{
            loginViewModel.apiCall.value = "login"
            Log.e("Login button", "Was tried to be clicked and context was " + activity.toString())
        }
        super.onActivityCreated(savedInstanceState)
    }

    fun viewModelInit(){
        loginViewModel.let{
            it.results.observe(viewLifecycleOwner, Observer {item ->
                when(item.status){
                    Status.SUCCESS -> {
                        val intent  = Intent(activity, BottomMainNavActivity::class.java)
                        activity?.startActivity(intent)
                        Toast.makeText(context, "Successfully logged in ", Toast.LENGTH_LONG ).show()
                    }

                    Status.LOADING -> {
                        Toast.makeText(context, "Logging in, Please wait.", Toast.LENGTH_LONG ).show()
                        Log.e("LOADING", "LOADING DATA PLEASE WAIT.....")
                    }

                    Status.ERROR -> {
                        if (Commons.isNetworkAvailable(requireContext())) {
                            try {
                                item.message?.let { msg ->
                                    val response = Gson().fromJson(msg, SignUpResponse::class.java)
                                    Toast.makeText(requireContext(), response.msg, Toast.LENGTH_LONG).show()
                                } ?: false

                            } catch (e: Exception) {
                                Toast.makeText(requireContext(), "Oops!! Something went wrong!!", Toast.LENGTH_LONG).show()
                            }

                        } else {
                            Toast.makeText(activity, "No network available , login later!", Toast.LENGTH_LONG).show()
                        }
                    }
                }
            })
        }
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