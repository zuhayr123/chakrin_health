package com.laaltentech.abou.fitnessapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import com.laaltentech.abou.fitnessapp.databinding.ActivityTestBinding
import android.widget.Toast

import android.bluetooth.BluetoothDevice

import android.content.Intent

import android.content.BroadcastReceiver
import android.content.Context


class TestActivity : AppCompatActivity() {

    lateinit var binding: ActivityTestBinding

    var actionString : String = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_test)

        binding.buttonClick.setOnClickListener {
            val rssi = intent.getShortExtra(BluetoothDevice.EXTRA_RSSI, Short.MIN_VALUE).toInt()
            Toast.makeText(applicationContext, "  RSSI: " + rssi + "dBm", Toast.LENGTH_SHORT)
                .show()
        }
    }

}