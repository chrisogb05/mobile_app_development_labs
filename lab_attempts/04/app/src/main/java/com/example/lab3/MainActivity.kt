package com.example.lab3

import android.content.BroadcastReceiver
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.core.content.ContextCompat
import com.example.lab3.ui.WeatherApp
import com.example.lab3.ui.theme.lab3Theme

class MainActivity : ComponentActivity() {
    private val br: BroadcastReceiver = MyPowerReceiver()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            lab3Theme {
                WeatherApp()
            }
        }
    }

    override fun onResume(){
        super.onResume()
        //create an instance of IntentFilter
        val filter = IntentFilter(Intent.ACTION_POWER_CONNECTED)
        //set the receiver exported flag if your app is listening to system broadcast
        val receiverFlag = ContextCompat.RECEIVER_EXPORTED
        //register the receiver in the Activity context
        ContextCompat.registerReceiver(this, br, filter, receiverFlag)
    }

    override fun onPause(){
        super.onPause()
        //unregister the broadcast receiver to avoid leaking the receiver outside the Activity context
        unregisterReceiver(br)
    }
}


