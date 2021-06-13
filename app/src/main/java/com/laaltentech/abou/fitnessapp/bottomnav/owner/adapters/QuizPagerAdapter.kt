package com.laaltentech.abou.fitnessapp.bottomnav.owner.adapters

import android.animation.LayoutTransition
import android.content.res.Resources
import android.util.TypedValue
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
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

        binding.materialCardViewA.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.materialCardViewB.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.materialCardViewC.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.layoutLinear.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraintA.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraintB.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)
        binding.constraintC.layoutTransition.enableTransitionType(LayoutTransition.CHANGING)

        container.addView(binding.root)
        binding.textData.text = questions[position].questionObservation

        binding.materialCardViewA.setOnClickListener {
            val params: ViewGroup.LayoutParams = binding.materialCardViewA.layoutParams
            params.height = (TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 100f, container.context.resources.displayMetrics)).toInt()
            it.backgroundTintList = container.context.resources.getColorStateList(R.color.dot_inactive_screen2)
            binding.optionAArrow.setImageResource(R.drawable.ic_correct)
            binding.textData.text = "button was clicked"
        }
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, `object`: Any) {
        container.removeView(`object` as LinearLayout)
    }
}