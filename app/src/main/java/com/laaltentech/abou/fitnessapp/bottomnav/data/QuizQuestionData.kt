package com.laaltentech.abou.fitnessapp.bottomnav.data

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity(tableName = "QuizQuestionData")
class QuizQuestionData {
    @PrimaryKey(autoGenerate = true)
    @SerializedName("id")
    var id: Int = 0

    @SerializedName("questionObservation")
    var questionObservation: String = "No Data"

    @SerializedName("optionVata")
    var optionVata : String = "No Option"

    @SerializedName("optionPitta")
    var optionPitta : String = "No Option"

    @SerializedName("optionKapha")
    var optionKapha : String = "No Option"

    @SerializedName("selectedOption")
    var selectedOption : String = "0"
}