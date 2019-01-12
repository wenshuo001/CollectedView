package com.example.collectedview.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.os.Handler
import android.os.Message
import com.example.collectedview.JavaView.CustomCircleProgress
import kotlinx.android.synthetic.main.loading_layout.*
import com.example.collectedview.R
import java.util.*

class CirlleProgressActivity : AppCompatActivity() {
     val PROGRESS_CIRCLE_STARTING = 0x110
     lateinit var circleProgress: CustomCircleProgress
     var Cprogress = 0



    private val mhandle :Handler = Handler{
          when(it.what){
              PROGRESS_CIRCLE_STARTING -> {
                  Cprogress= circleProgress.progress
                  circleProgress.progress= ++Cprogress
                  if(Cprogress>=100){
                      it.target.removeMessages(PROGRESS_CIRCLE_STARTING)
                      Cprogress=0
                      circleProgress.progress=0
                      circleProgress.status=CustomCircleProgress.Status.End
                  }else{
                      it.target.sendEmptyMessageDelayed(PROGRESS_CIRCLE_STARTING,100);

                  }
              }
          }
        false
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_cirlle_progress)
        circleProgress = findViewById<CustomCircleProgress>(R.id.circleProgress)
        circleProgress.setOnClickListener{
             when(circleProgress.status){
                 CustomCircleProgress.Status.start ->{
                     //点击开始
                     circleProgress.status= CustomCircleProgress.Status.starting
                     val msg =Message()
                     msg.what=PROGRESS_CIRCLE_STARTING
                     mhandle.sendMessage(msg)
                 }
                 CustomCircleProgress.Status.starting ->{
                     circleProgress.status= CustomCircleProgress.Status.Stop
                     mhandle.removeMessages(PROGRESS_CIRCLE_STARTING)
                 }
                 CustomCircleProgress.Status.Stop ->{
                     circleProgress.status= CustomCircleProgress.Status.starting;
                     val msg =Message()
                     msg.what=PROGRESS_CIRCLE_STARTING
                     mhandle.sendMessage(msg)
                 }
                 CustomCircleProgress.Status.End ->{
                     circleProgress.status =  CustomCircleProgress.Status.starting
                     val msg =Message()
                     msg.what=PROGRESS_CIRCLE_STARTING
                     mhandle.sendMessage(msg)
                 }
             }
        }
    }


}
