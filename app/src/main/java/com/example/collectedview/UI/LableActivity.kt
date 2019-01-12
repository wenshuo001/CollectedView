package com.example.collectedview.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.collectedview.JavaView.LableView
import com.example.collectedview.R
import com.example.collectedview.R.id.lableView
import kotlinx.android.synthetic.main.activity_lable.*

class LableActivity : AppCompatActivity() {

    var string_array:Array<String> = arrayOf("三种方式", "调用", "代码中生成","大小要进行更改","三种方式", "调用")
    var lableView : LableView?=null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lable)
        lableView =findViewById<LableView>(R.id.lableView)
        lableView?.items= string_array
        lableView?.setOnItemClickListener {
            Toast.makeText(this@LableActivity,it,Toast.LENGTH_SHORT).show()
        }
    }
}
