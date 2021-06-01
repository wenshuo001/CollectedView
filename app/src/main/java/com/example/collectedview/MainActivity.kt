package com.example.collectedview

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v4.app.ActivityCompat
import android.support.v4.app.DialogFragment
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.widget.Toast
import com.example.collectedview.Dialog.ShowDialogFragment
import com.example.collectedview.UI.*
import com.example.collectedview.adapter.MainAdapter
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val strList = arrayOf("DialogFragment弹窗", "RepalceFragmentActivity", "LoadingActivity", "流式布局标签LableActivity",
                "ShepherdCheckActivity", "圆形下载进度条CirlleProgressActivity", "SpinnerActivity", "MusicActivity", "IOSActivity")

        main_rv.layoutManager = LinearLayoutManager(this)
        val mainAdapter =  MainAdapter(this, strList.toList() as ArrayList<String>)
        mainAdapter.setOnItemClickListener {
                        when (it) {
                            0 -> {
                                var fragment = ShowDialogFragment()
                                fragment?.dialog?.setCancelable(false)
                                fragment?.dialog?.setCanceledOnTouchOutside(false);
                                fragment?.show(supportFragmentManager, "show")
                            }
                            1 -> {
                                Intent(this@MainActivity, RepalceFragmentActivity::class.java).run {
                                    startActivity(this)
                                }
                            }
                            2 -> {
                                Intent(this@MainActivity, LoadingActivity::class.java).run {
                                    startActivity(this)
                                }
                            }
                            3 -> {
                                Intent(this@MainActivity, LableActivity::class.java).run {
                                    startActivity(this)
                                }
                            }
                            4 -> {
                                Intent(this@MainActivity, ShepherdCheckActivity::class.java).run {
                                    startActivity(this)
                                }
                            }
                            5 -> {
                                Intent(this@MainActivity, CirlleProgressActivity::class.java).run {
                                    startActivity(this)
                                }
                            }
                            6 -> {
                                Intent(this@MainActivity, SpinnerActivity::class.java).run {
                                    startActivity(this)
                                }
                            }
                            7 -> {
                                Intent(this@MainActivity, MusicActivity::class.java).run {
                                    startActivity(this)
                                }
                            }
                            8 -> {
                                Intent(this@MainActivity, IOSActivity::class.java).run {
                                    startActivity(this)
                                }
                            }
                        }
                    }
        main_rv.adapter = mainAdapter

        }

//        dialogFragment.setOnClickListener {
//            fragment= ShowDialogFragment()
//        fragment?.dialog?.setCancelable(false)
//        fragment?.dialog?.setCanceledOnTouchOutside(false);
//        fragment?.show(supportFragmentManager,"show")
//        }
//
//
//        listFenZuFragment.setOnClickListener {
//            Intent(this@MainActivity,RepalceFragmentActivity::class.java).run {
//                startActivity(this)
//            }
//        }
//
//        /**
//         * 消息等待提示框
//         */
//        loading_dialog.setOnClickListener {
//         Intent(this@MainActivity,LoadingActivity::class.java).run {
//             startActivity(this)
//           }
//        }
//        /**
//         * 流式布局标签
//         */
//        lableView_btn.setOnClickListener {
//            Intent(this@MainActivity,LableActivity::class.java).run {
//                startActivity(this)
//            }
//        }
//
//        ShepherdCheck.setOnClickListener {
//            Intent(this@MainActivity,ShepherdCheckActivity::class.java).run {
//                startActivity(this)
//            }
//        }
//
//        btn_circle.setOnClickListener {
//            Intent(this@MainActivity, CirlleProgressActivity::class.java).run {
//                startActivity(this)
//            }
//        }
//
//        btn_sipnner.setOnClickListener {
//            Intent(this@MainActivity,SpinnerActivity::class.java).run {
//                startActivity(this)
//            }
//        }
//
//        button.setOnClickListener {
//            Intent(this@MainActivity,MusicActivity::class.java).run {
//                startActivity(this)
//            }
//        }
//
//        switch_ios.setOnClickListener {
//            Intent(this@MainActivity,IOSActivity::class.java).run {
//                startActivity(this)
//            }
//        }

}
