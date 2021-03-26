package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.google.gson.Gson
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.bottomnav.observer.ProfileViewModel
import com.laaltentech.abou.fitnessapp.databinding.FragmentProfileBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.login.data.SignUpResponse
import com.laaltentech.abou.fitnessapp.network.Status
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.CONSTANTS
import com.laaltentech.abou.fitnessapp.util.Commons
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject

class ProfileFragment : Fragment(), Injectable {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: FragmentProfileBinding

    private val profileViewModel: ProfileViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelFactory)
            .get(ProfileViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_profile, container, false, dataBindingComponent)
        (activity as AppCompatActivity).supportActionBar!!.show()

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        viewModelInit()
        val sharedPref = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val phoneNumber = sharedPref.getString(CONSTANTS.PHONE_NUMBER, "")
        Log.e("Phone Number", "The phone number is $phoneNumber")
        profileViewModel.phoneNumber = phoneNumber!!
        profileViewModel.apiCall.value = "available"
        super.onActivityCreated(savedInstanceState)
    }

    fun viewModelInit(){
        profileViewModel.let{
            it.results.observe(viewLifecycleOwner, Observer { item ->
                when(item.status){
                    Status.SUCCESS ->{
                        binding.profileData = item.data
                        Glide.with(binding.root)
                            .load(item.data?.userPhoto)
                            .apply( RequestOptions()
                                .placeholder(R.mipmap.camera_click)
                                .error(R.mipmap.camera_click)
                                .fitCenter())
                            .into(binding.profileImageProfile)
//                        binding.progress.progressBar.visibility = View.INVISIBLE
                    }

                    Status.LOADING -> {
//                        binding.progress.progressBar.visibility = View.VISIBLE
                        Log.e("LOADING", "LOADING DATA PLEASE WAIT.....")
                    }

                    Status.ERROR -> {
//                        binding.progress.progressBar.visibility = View.INVISIBLE
                        if (Commons.isNetworkAvailable(requireContext())) {
                            try {
                                item.message?.let { msg ->
                                    val response = Gson().fromJson(msg, SignUpResponse::class.java)
                                    Toast.makeText(
                                        requireContext(),
                                        response.msg,
                                        Toast.LENGTH_LONG
                                    ).show()
                                } ?: false

                            } catch (e: Exception) {
                                Toast.makeText(
                                    requireContext(),
                                    "Oops!! Something went wrong!!",
                                    Toast.LENGTH_LONG
                                ).show()
                            }

                        } else {
                            Toast.makeText(
                                activity,
                                "No network available , unable to reload!",
                                Toast.LENGTH_LONG
                            ).show()
                        }
                    }
                }
            })
        }
    }
}