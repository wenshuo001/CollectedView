package com.example.collectedview.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.example.collectedview.R
import kotlinx.android.synthetic.main.activity_lable.*

class LableActivity : AppCompatActivity() {

    var string_array:Array<String> = arrayOf("三种方式", "调用", "代码中生成","大小要进行更改","三种方式", "调用")

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lable)
        lableView.items= string_array
    }
}
