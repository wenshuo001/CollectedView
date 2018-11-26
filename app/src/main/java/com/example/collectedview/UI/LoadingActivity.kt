package com.example.collectedview.UI

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.example.collectedview.Dialog.CustomDialog
import com.example.collectedview.KotlinView.LoadingViewKT
import com.example.collectedview.R
import kotlinx.android.synthetic.main.loading_layout.*

class LoadingActivity : AppCompatActivity() {
    lateinit var dialog: CustomDialog
    lateinit var loading_view: LoadingViewKT
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_loading)
        showLoadingDialog()
    }

    fun showLoadingDialog(){
        val builder = CustomDialog.Builder(this)
        dialog = builder.cancelTouchout(false)
                .view(R.layout.loading_layout)
                .heightDimenRes(R.dimen.loading_dialog_h)
                .widthDimenRes(R.dimen.loading_dialog_w)
                .style(R.style.Dialog)
                .addViewOnclick(R.id.loading_dialog, View.OnClickListener {
                    hideDalog()
                }).build()
        loading_view=builder.initView(R.id.loading_dialog) as LoadingViewKT
        loading_view.startLoading()
        loading_view.setBackgroundResource(R.drawable.shape_dialog_round)
        dialog.show()
    }

    fun hideDalog(){
        loading_view.stopLoading()
        dialog.dismiss()
    }
}
