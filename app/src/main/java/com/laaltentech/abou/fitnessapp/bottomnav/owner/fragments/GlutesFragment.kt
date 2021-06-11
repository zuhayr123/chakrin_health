package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.content.Context
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.fragment.findNavController
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.databinding.FragmentGlutesLayoutBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.CONSTANTS
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject

class GlutesFragment : Fragment(), Injectable {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: FragmentGlutesLayoutBinding

    var counter = 0

    var counterDosha = 0
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_glutes_layout, container, false, dataBindingComponent)
        (activity as AppCompatActivity).supportActionBar!!.show()

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        val sharedPref = this.activity!!.getSharedPreferences("pref", Context.MODE_PRIVATE)
        val isSub = sharedPref.getBoolean(CONSTANTS.IS_SUB, false)
        val handler = Handler()

        if(isSub){
            binding.materialButton.visibility = View.GONE
        }
        else{
            binding.materialButton.visibility = View.VISIBLE
        }

        binding.loader.startAnimation(
            AnimationUtils.loadAnimation(activity, R.anim.rotate_but_not_fade)
        )

        binding.materialButton.setOnClickListener {
            val action = GlutesFragmentDirections.actionGlutesFragmentToFragmentSubscribeCheck()
            findNavController().navigate(action)
        }
        binding.imageButton.setOnClickListener {
            binding.textView18.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated_one_way))
            binding.textView19.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated_one_way))
            binding.textView21.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated_one_way))
            binding.pittaImageView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated_one_way))
            counterDosha +=1
            handler.postDelayed({
                when(counterDosha){
                    1 -> {
                        onImageButtonClick("vata", context = context!!)
                    }

                    2-> {
                        onImageButtonClick("pitta", context = context!!)
                    }

                    3-> {
                        onImageButtonClick("kapha", context = context!!)
                        counterDosha = 0
                    }
                }
            },1000)
        }
        autoRefresh(context = context!!)
        super.onActivityCreated(savedInstanceState)
    }

    fun autoRefresh(context: Context) {
        val h = Handler()
        val delay : Long = 4000 //milliseconds
        h.postDelayed(Runnable {
            Log.e("Counter", "The counter value is $counter")
            when(counter){
                1 -> {
                    binding.titleElement.text = "Earth"
                    binding.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_earth))
                    binding.imageView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.titleElement.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                }

                2 -> {
                    binding.titleElement.text = "Air"
                    binding.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_air))
                    binding.imageView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.titleElement.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                }

                3 -> {
                    binding.titleElement.text = "Fire"
                    binding.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_fire))
                    binding.imageView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.titleElement.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                }

                4 -> {
                    binding.titleElement.text = "Space"
                    binding.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_ether))
                    binding.imageView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.titleElement.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                }

                5 -> {
                    binding.titleElement.text = "Water"
                    binding.imageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_water))
                    binding.imageView.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.titleElement.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    binding.view.startAnimation(AnimationUtils.loadAnimation(activity, R.anim.fade_accelerated))
                    counter = 0
                }
            }
            counter += 1
            //Activity written here will be called every 2 sec
            autoRefresh(context = context)
        }, delay)
    }

    fun onImageButtonClick(dosha : String, context: Context){

        when(dosha){
            "pitta" -> {
                binding.textView18.text = "Pitta"
                binding.textView19.text = "The elements of Akash & Vayu combine to make Vata. It is the energy of movement,inhalation & exhalation. It is light,dry,subtle & clear. Vata’s main site to dwell in the body is the Colon."
                binding.textView21.text = "Excess of Vata causes – \n" +
                        "Dryness,cold,constipation,fatigue & insomnia.\n"
                binding.pittaImageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_pitta))
            }

            "kapha" -> {
                binding.textView18.text = "Kapha"
                binding.textView19.text = "The elements of Jala & Prithvi combine to make Kapha. It maintains stability,weight,bodily fluids & lubrication of joints. It is cold,wet,heavy & dense. Kapha resides in the stomach."
                binding.textView21.text = "Excess of Kapha causes – \n" +
                        "Accumulation of mucous,loss of appetite,desire to sleep,loose joints & feeling of heaviness.\n"
                binding.pittaImageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_kapha))
            }

            "vata" -> {
                binding.textView18.text = "Vata"
                binding.textView19.text = "The elements of Akash & Vayu combine to make Vata. It is the energy of movement,inhalation & exhalation. It is light,dry,subtle & clear. Vata’s main site to dwell in the body is the Colon."
                binding.textView21.text = "Excess of Vata causes – \n" +
                        "Dryness,cold,constipation,fatigue & insomnia.\n"
                binding.pittaImageView.setImageDrawable(ContextCompat.getDrawable(context,R.drawable.ic_vata))
            }
        }
    }
}