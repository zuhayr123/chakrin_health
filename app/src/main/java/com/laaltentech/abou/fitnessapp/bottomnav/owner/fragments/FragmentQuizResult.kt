package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.bottomnav.data.QuestionDataArrayModel
import com.laaltentech.abou.fitnessapp.bottomnav.owner.adapters.QuizPagerAdapter.Companion.KAPHA
import com.laaltentech.abou.fitnessapp.bottomnav.owner.adapters.QuizPagerAdapter.Companion.PITTA
import com.laaltentech.abou.fitnessapp.bottomnav.owner.adapters.QuizPagerAdapter.Companion.VATA
import com.laaltentech.abou.fitnessapp.databinding.FragmentQuizResultLayoutBinding
import com.laaltentech.abou.fitnessapp.di.Injectable
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import kotlinx.android.synthetic.main.activity_bottom_main_layout.*
import kotlinx.android.synthetic.main.fragment_quiz_result_loader.view.*
import javax.inject.Inject

class FragmentQuizResult: Fragment(), Injectable {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding: FragmentQuizResultLayoutBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz_result_layout, container, false, dataBindingComponent)
        (activity as AppCompatActivity).supportActionBar!!.show()
        (activity as AppCompatActivity).nav_view!!.visibility = View.GONE

        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        activity?.overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out)

        val gson = Gson()
        val result = FragmentQuizResultArgs.fromBundle(arguments!!).resultArray

        val quizData : QuestionDataArrayModel = gson.fromJson(result, QuestionDataArrayModel::class.java)

        Log.e("RESULT", "The result is ${quizData.questionArray?.get(1)?.selectedOption}")

        val percentageResult = calculateResult(data = quizData)

        Log.e("mapping was done", "the values Kapha :  ${percentageResult[0]},the values Vata :  ${percentageResult[1]},the values Pitta:  ${percentageResult[2]} ")

        binding.root.logo_image.startAnimation(
            AnimationUtils.loadAnimation(activity, R.anim.rotate_indefinitely)
        )

        val h = Handler()
            h.postDelayed(Runnable {
                binding.loaderNew.root.visibility = View.GONE
            }, 4500)
        super.onActivityCreated(savedInstanceState)
    }

    fun calculateResult(data : QuestionDataArrayModel) : ArrayList<Int>{
        val totalNumberOfQuestions = data.questionArray?.size

        var kaphaNumbers = 0
        var pittaNumbers = 0
        var vataNumbers = 0

        val result : ArrayList<Int> = ArrayList()
        data.questionArray?.forEach {
            when(it.selectedOption){
                KAPHA -> {
                    kaphaNumbers += 1
                }

                VATA -> {
                    vataNumbers += 1
                }

                PITTA -> {
                    pittaNumbers += 1
                }
            }
        }

        result.add(kaphaNumbers*100/totalNumberOfQuestions!!)
        result.add(vataNumbers*100/totalNumberOfQuestions)
        result.add(100 - result[0] - result[1])

        return result
    }
}