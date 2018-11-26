package com.example.collectedview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.collectedview.UI.LableActivity
import com.example.collectedview.UI.LoadingActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**
         * 消息等待提示框
         */
        loading_dialog.setOnClickListener {
         Intent(this@MainActivity,LoadingActivity::class.java).run {
             startActivity(this)
           }
        }
        /**
         * 流式布局标签
         */
        lableView_btn.setOnClickListener {
            Intent(this@MainActivity,LableActivity::class.java).run {
                startActivity(this)
            }
        }
    }
}
