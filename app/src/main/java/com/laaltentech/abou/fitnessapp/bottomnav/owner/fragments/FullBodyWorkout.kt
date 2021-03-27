package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.databinding.FragmentFullBodyLayoutBinding
import com.laaltentech.abou.fitnessapp.databinding.FragmentGlutesLayoutBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.CONSTANTS
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject

class FullBodyWorkout : Fragment(), Injectable {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: FragmentFullBodyLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_full_body_layout, container, false, dataBindingComponent)
        (activity as AppCompatActivity).supportActionBar!!.show()

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val sharedPref = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val isSub = sharedPref.getBoolean(CONSTANTS.IS_SUB, false)

        if(isSub){
            binding.materialButton.visibility = View.GONE
        }
        else{
            binding.materialButton.visibility = View.VISIBLE
        }

        binding.materialButton.setOnClickListener {
            val action = FullBodyWorkoutDirections.actionFullBodyWorkoutToFragmentSubscribeCheck()
            findNavController().navigate(action)
        }
        super.onActivityCreated(savedInstanceState)
    }
}