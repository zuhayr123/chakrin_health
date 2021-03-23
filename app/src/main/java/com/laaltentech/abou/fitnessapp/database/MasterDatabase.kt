package com.laaltentech.abou.fitnessapp.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.laaltentech.abou.fitnessapp.game.data.GameDAO
import com.laaltentech.abou.fitnessapp.game.data.GameData
import com.laaltentech.abou.fitnessapp.game.data.IndividualGameScore
import com.laaltentech.abou.fitnessapp.login.data.LoginDAO
import com.laaltentech.abou.fitnessapp.login.data.SignUpData

@Database(entities =
[
    (GameData::class),
    (IndividualGameScore::class),
    (SignUpData::class)
], version = 1, exportSchema = false)

@TypeConverters(DateConverter::class)

abstract class MasterDatabase : RoomDatabase() {

    abstract fun gameDAO(): GameDAO

    abstract fun loginDAO(): LoginDAO
}