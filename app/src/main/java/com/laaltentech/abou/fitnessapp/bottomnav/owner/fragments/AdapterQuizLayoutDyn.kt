package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.bottomnav.data.QuizQuestionData
import com.laaltentech.abou.fitnessapp.bottomnav.owner.adapters.QuizPagerAdapter
import com.laaltentech.abou.fitnessapp.databinding.FragmentQuizBinding
import com.laaltentech.abou.fitnessapp.util.AppExecutors
import com.laaltentech.abou.fitnessapp.util.FragmentDataBindingComponent
import javax.inject.Inject

class AdapterQuizLayoutDyn : Fragment() {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    var dataBindingComponent = FragmentDataBindingComponent(this)

    lateinit var binding : FragmentQuizBinding

    lateinit var adapter : QuizPagerAdapter

    var arrayOfQuestions : ArrayList<QuizQuestionData> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_quiz, container, false, dataBindingComponent)
        binding.lifecycleOwner = viewLifecycleOwner
        return binding.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {

        createQuestions(title = "Title1", optionA = "optionA", optionB = "optionB", optionC = "optionC")
        createQuestions(title = "Title2", optionA = "optionA1", optionB = "optionB", optionC = "optionC")
        createQuestions(title = "Title3", optionA = "optionA", optionB = "optionB1", optionC = "optionC")
        createQuestions(title = "Title4", optionA = "optionA", optionB = "optionB", optionC = "optionC1")
        createQuestions(title = "Title5", optionA = "optionA", optionB = "optionB", optionC = "optionC")

        adapter = QuizPagerAdapter(dataBindingComponent = DataBindingUtil.getDefaultComponent(), questions = arrayOfQuestions)
        binding.viewPager.adapter = adapter
        super.onActivityCreated(savedInstanceState)
    }

    private fun createQuestions(title:String, optionA: String, optionB: String, optionC: String){
        val questionData = QuizQuestionData()

        questionData.questionObservation = title
        questionData.optionKapha = optionA
        questionData.optionPitta = optionB
        questionData.optionVata = optionC

        arrayOfQuestions.add(questionData)
    }
}