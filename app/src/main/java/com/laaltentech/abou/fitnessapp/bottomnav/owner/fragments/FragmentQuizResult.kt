package com.laaltentech.abou.fitnessapp.bottomnav.owner.fragments

import android.graphics.Color
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
import androidx.navigation.fragment.findNavController
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.utils.ColorTemplate
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
import com.laaltentech.abou.fitnessapp.util.OnBackPressed
import kotlinx.android.synthetic.main.activity_bottom_main_layout.*
import kotlinx.android.synthetic.main.fragment_quiz_result_loader.view.*
import javax.inject.Inject


class FragmentQuizResult: Fragment(), Injectable, OnBackPressed {
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

        setupPieChart();

        val gson = Gson()
        val result = FragmentQuizResultArgs.fromBundle(arguments!!).resultArray

        val quizData : QuestionDataArrayModel = gson.fromJson(result, QuestionDataArrayModel::class.java)

        Log.e("RESULT", "The result is ${quizData.questionArray?.get(1)?.selectedOption}")

        val percentageResult = calculateResult(data = quizData)

        loadPieChartData(kapha = percentageResult[0], vata = percentageResult[1], pitta = percentageResult[2])

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

    private fun setupPieChart() {
        binding.pieChart.isDrawHoleEnabled = true
        binding.pieChart.setUsePercentValues(true)
        binding.pieChart.setEntryLabelTextSize(12f)
        binding.pieChart.setEntryLabelColor(Color.BLACK)
        binding.pieChart.centerText = "Your Prakriti"
        binding.pieChart.setCenterTextSize(24f)
        binding.pieChart.description.isEnabled = false
    }

    private fun loadPieChartData(vata: Int, pitta: Int, kapha: Int) {
        val entries: ArrayList<PieEntry> = ArrayList()
        entries.add(PieEntry((vata.toFloat()/100), "Vata"))
        entries.add(PieEntry((pitta.toFloat()/100), "Pitta"))
        entries.add(PieEntry((kapha.toFloat()/100), "Kapha"))
        val colors: ArrayList<Int> = ArrayList()
        colors.add(resources.getColor(R.color.dot_active_screen3))
        colors.add(resources.getColor(R.color.colorPrimary))
        colors.add(resources.getColor(R.color.bg_color_for_welcome_slide_two))

        val dataSet = PieDataSet(entries, "")
        dataSet.colors = colors
        val data = PieData(dataSet)
        data.setDrawValues(true)
        data.setValueFormatter(PercentFormatter(binding.pieChart))
        data.setValueTextSize(12f)
        data.setValueTextColor(Color.BLACK)
        binding.pieChart.data = data
        binding.pieChart.invalidate()
        binding.pieChart.animateY(1400, Easing.EaseInOutQuad)
    }

    override fun onBackPressed() {
        findNavController().popBackStack(R.id.fullBodyWorkout, false)
    }
}