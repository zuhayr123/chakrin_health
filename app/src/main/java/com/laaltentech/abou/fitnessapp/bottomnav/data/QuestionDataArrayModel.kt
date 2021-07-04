package com.laaltentech.abou.fitnessapp.bottomnav.data

import com.google.gson.annotations.SerializedName

class QuestionDataArrayModel {
    @SerializedName("result")
    var questionArray: ArrayList<QuizQuestionData>? = null
}