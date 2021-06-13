package com.laaltentech.abou.fitnessapp.bottomnav.owner.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.viewpager.widget.PagerAdapter
import com.laaltentech.abou.fitnessapp.R
import com.laaltentech.abou.fitnessapp.bottomnav.data.QuizQuestionData
import com.laaltentech.abou.fitnessapp.databinding.AdapterViewpagerQuizBinding

class QuizPagerAdapter(private val dataBindingComponent: DataBindingComponent?, var questions : ArrayList<QuizQuestionData>):PagerAdapter(){

    override fun getCount(): Int = questions.size

    override fun isViewFromObject(view: View, `object`: Any): Boolean {
        return view == `object`
    }

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val binding = DataBindingUtil
            .inflate<AdapterViewpagerQuizBinding>(
                LayoutInflater.from(container.context),
                R.layout.adapter_viewpager_quiz,
                container,
                false,
                dataBindingComponent
            )
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}