package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isNotEmpty
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.findNavController
import com.google.gson.Gson
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.bottomnav.observer.ProfileViewModel
import com.laaltentech.abou.fitnessapp.bottomnav.observer.SubscribeStatusUpdateViewModel
import com.laaltentech.abou.fitnessapp.databinding.FragmentSubscribeLayoutBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.login.data.SignUpResponse
import com.laaltentech.abou.fitnessapp.network.Status
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.CONSTANTS
import com.laaltentech.abou.fitnessapp.util.Commons
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject

class FragmentSubscribeCheck : Fragment(), Injectable {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: FragmentSubscribeLayoutBinding

    private val subscribeStatusUpdateViewModel: SubscribeStatusUpdateViewModel by lazy {
        ViewModelProviders.of(activity!!, viewModelFactory)
            .get(SubscribeStatusUpdateViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_subscribe_layout, container, false, dataBindingComponent)
        (activity as AppCompatActivity).supportActionBar!!.show()

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val sharedPref = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val phoneNumber = sharedPref.getString(CONSTANTS.PHONE_NUMBER, "")

        subscribeStatusUpdateViewModel.phoneNumber = phoneNumber!!
        binding.submit.setOnClickListener {
            if(binding.firstName.isNotEmpty() && binding.lastName.isNotEmpty() && binding.regPhoneNumber.isNotEmpty()){
                subscribeStatusUpdateViewModel.apiCall.value = "available"
            }

            else{
                Toast.makeText(
                    activity,
                    "One of the fields are empty!!",
                    Toast.LENGTH_LONG
                ).show()
            }
        }
        initViewModel()
        super.onActivityCreated(savedInstanceState)
    }

    fun initViewModel(){
        subscribeStatusUpdateViewModel.let {
            it.results.observe(viewLifecycleOwner, Observer { item ->
                when(item.status){
                    Status.SUCCESS ->{
                        Toast.makeText(
                            activity,
                            "Successfully Subscribed!",
                            Toast.LENGTH_LONG
                        ).show()

                        val sharedPref = requireActivity().getSharedPreferences("pref", Context.MODE_PRIVATE)
                        with(sharedPref.edit()) {
                            putBoolean(CONSTANTS.IS_SUB, true)
                            commit()
                        }
                        findNavController().popBackStack()
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