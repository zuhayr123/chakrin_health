package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.content.Context
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.databinding.UpperBodyWorkBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.CONSTANTS
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject

class UpperBodyWorkout : Fragment(), Injectable {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: UpperBodyWorkBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.upper_body_work, container, false, dataBindingComponent)
        (activity as AppCompatActivity).supportActionBar!!.show()

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val sharedPref = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val isSub = sharedPref.getBoolean(CONSTANTS.IS_SUB, false)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            binding.scrollView.setOnScrollChangeListener { view, i, i2, i3, i4 ->
                if(i4/9.28> 0 && i4/9.28 <100){
                    binding.animBack.progress = (i4/9.28).toFloat()
                    binding.animBack.setMaxFrame((i4/9.28).toInt())
                    Log.e("Code", "REACHED HERE")
                }
                Log.e("SCROLL", "The value of i: $i and i2: $i2 and i3: $i3 and i4: ${(i4/9.28).toInt()}")
            }
        }
//        if(isSub){
//            binding.materialButton.visibility = View.GONE
//        }
//        else{
//            binding.materialButton.visibility = View.VISIBLE
//        }
//
//        binding.materialButton.setOnClickListener {
//            val action = UpperBodyWorkoutDirections.actionUpperBodyWorkoutToFragmentSubscribeCheck()
//            findNavController().navigate(action)
//        }
        super.onActivityCreated(savedInstanceState)
    }
}