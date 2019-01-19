package com.example.collectedview

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.DialogFragment
import android.widget.Toast
import com.example.collectedview.Dialog.ShowDialogFragment
import com.example.collectedview.UI.CirlleProgressActivity
import com.example.collectedview.UI.LableActivity
import com.example.collectedview.UI.LoadingActivity
import com.example.collectedview.UI.ShepherdCheckActivity
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var fragment: ShowDialogFragment? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        fragment= ShowDialogFragment()
        fragment?.dialog?.setCancelable(false)
        fragment?.dialog?.setCanceledOnTouchOutside(false);
        fragment?.show(supportFragmentManager,"show")

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

        ShepherdCheck.setOnClickListener {
            Intent(this@MainActivity,ShepherdCheckActivity::class.java).run {
                startActivity(this)
            }
        }

        btn_circle.setOnClickListener {
            Intent(this@MainActivity, CirlleProgressActivity::class.java).run {
                startActivity(this)
            }
        }
        Toast.makeText(this,"滴滴滴",Toast.LENGTH_SHORT).show();
    }
}
