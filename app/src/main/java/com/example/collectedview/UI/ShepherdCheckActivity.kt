package com.example.collectedview.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.collectedview.R
import kotlinx.android.synthetic.main.activity_shepherd_check.*
import java.util.*

class ShepherdCheckActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_shepherd_check)
        start()
    }

    var timer:Timer?=null;
    fun start(){
        timer = Timer()
        val task = object : TimerTask() {
            override fun run() {

            }
        }
      //  timer?.schedule(task, 2000, 2000);
    }

    override fun onStop() {
        super.onStop()
        plaid_view.StopView()
    }

    override fun onDestroy() {
        super.onDestroy()
        timer?.cancel()
    }
}
