package com.example.collectedview.Utils;

import android.app.Dialog;
import android.content.DialogInterface;
import android.os.Handler;

import java.util.LinkedList;
import java.util.List;
import java.util.Queue;

public class DialogQueueUtils {

    private static final String TAG = "DialogQueueUtils";
    private Queue<Dialog> dialogQueue;


    private Dialog mCurrentDialog = null;//当前显示的Dialog

    private DialogQueueUtils() {
        dialogQueue = new LinkedList<>();
    }

    public static DialogQueueUtils getInstance() {
        return DialogQueueHolder.singleton;
    }

    /**
     * 单例模式->静态内部类<br/>
     * 多线程情况下，使用合理一些,推荐
     */
    static class DialogQueueHolder {
        private static DialogQueueUtils singleton = new DialogQueueUtils();
    }

    public void addDialog(List<Dialog> dialogs){
        for (Dialog dialog : dialogs) {
            if (dialog != null){
                dialogQueue.offer(dialog);
            }
        }
    }

    public void addDialog(Dialog dialog){
        if (dialog != null){
            dialogQueue.offer(dialog);
        }
    }

    public void show(){
        if (mCurrentDialog == null){
            //从队列中拿出一个Dialog实例,并从列表中移除
            mCurrentDialog = dialogQueue.poll();
            //当队列为空的时候拿出来的会是null

            if (mCurrentDialog != null){
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        mCurrentDialog.show();
                        mCurrentDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {
                            @Override
                            public void onDismiss(DialogInterface dialog) {
                                //这边设置了dismiss监听,在监听回调中再次调用show方法,可以获取下一个弹窗
                                mCurrentDialog = null;
                                show();
                            }
                        });
                    }
                }, 500);

            }
        }
    }
}
