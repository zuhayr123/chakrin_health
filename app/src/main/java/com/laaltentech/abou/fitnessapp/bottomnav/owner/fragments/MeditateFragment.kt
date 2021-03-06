package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.content.Context
import android.content.Intent
import android.net.Uri
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
import com.laaltentech.abou.fitnessapp.databinding.FragmentMeditateBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.CONSTANTS
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject


class MeditateFragment : Fragment(), Injectable {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: FragmentMeditateBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_meditate, container, false, dataBindingComponent)
        (activity as AppCompatActivity).supportActionBar!!.show()

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        val sharedPref = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val isSub = sharedPref.getBoolean(CONSTANTS.IS_SUB, false)

        Log.e("isSub", "The value of is sub is $isSub")

        if(isSub){
            binding.materialButton.visibility = View.GONE
            binding.placeHolder.visibility = View.GONE
            binding.subData.visibility = View.VISIBLE
        }
        else{
            binding.placeHolder.visibility = View.VISIBLE
            binding.subData.visibility = View.GONE
            binding.materialButton.visibility = View.VISIBLE
        }

        binding.card1.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=_hIFF8G-BG0")
                )
            )
        }

        binding.card2.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=iNNsLYog7RQ")
                )
            )
        }

        binding.card3.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=RqfkrZA_ie0")
                )
            )
        }

        binding.card4.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=oAPCPjnU1wA&t=106s")
                )
            )
        }

        binding.card5.setOnClickListener {
            startActivity(
                Intent(
                    Intent.ACTION_VIEW,
                    Uri.parse("https://www.youtube.com/watch?v=Auo8veVyRIY")
                )
            )
        }
        binding.materialButton.setOnClickListener {
            val action = MeditateFragmentDirections.actionNavigationMeditateToFragmentSubscribeCheck()
            findNavController().navigate(action)
        }
        super.onActivityCreated(savedInstanceState)
    }
}