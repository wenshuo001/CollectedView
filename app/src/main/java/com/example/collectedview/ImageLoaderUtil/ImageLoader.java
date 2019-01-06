package com.example.collectedview.ImageLoaderUtil;

import android.content.Context;
import android.graphics.Bitmap;
import android.os.Handler;
import android.os.Looper;
import android.os.Message;
import android.support.annotation.NonNull;
import android.util.Log;
import android.util.LruCache;
import android.widget.ImageView;

import com.example.collectedview.R;

import java.util.concurrent.Executor;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.logging.LogRecord;

/**
 * Creator :Wen
 * DataTime: 2018/12/4
 * Description:
 */
public class ImageLoader {
    private static final String TAG = "ImageLoader";

    public static final int MESSAGE_POST_RESULT = 1;

    private static final int CPU_COUNT = Runtime.getRuntime().availableProcessors();

    private static final int CORE_POOL_SIZE = CPU_COUNT +1 ;

    private static final int MAXIMUM_PIIL_SIZE = CPU_COUNT*2+1;
    private static final long KEEP_ALIVE = 10L;

    private static final int TAG_KEY_URI = R.id.imageloader_uri;

    private static final long DISK_CACHE_SIZE = 1024 * 1024 *50;
    private static final int IO_BUFFER_SIZE = 8*1024;
    private static final int DISK_CACHE_INDEX = 0;

    private boolean mIsDisLruCacheCreated = false ;

    private static final ThreadFactory sThreadFactory=new ThreadFactory() {

        private final AtomicInteger mCount=new AtomicInteger(1);
        @Override
        public Thread newThread(@NonNull Runnable r) {
            return new Thread(r,"ImageLoader#"+mCount.getAndIncrement());
        }
    };
    //创建一个线程池
    public static final Executor THREAD_POOL_EXECUTOR =new ThreadPoolExecutor(CORE_POOL_SIZE,MAXIMUM_PIIL_SIZE,
            KEEP_ALIVE,
            TimeUnit.SECONDS,
            new LinkedBlockingQueue<Runnable>(),
            sThreadFactory);


    private Handler mMainHandler =new Handler(Looper.getMainLooper()) {

        @Override
        public void handleMessage(Message msg) {
            LoaderResult result= (LoaderResult)msg.obj;
            ImageView imageView =result.imageView;
            String uri= (String) imageView.getTag(TAG_KEY_URI);
            if(uri.equals(result.uri)){
                imageView.setImageBitmap(result.bitmap);
            }else {
                Log.w(TAG,"set image bitmap,but has changed,ignored");
            }
        }
    };

    private Context mContext;
    private ImageResizer mImageResizer =new ImageResizer();
    private LruCache<String ,Bitmap> mMemoryCache;
    private DiskLruCache mDiskLruCache;

    private static class LoaderResult {
        public ImageView imageView;
        public String uri;
        public Bitmap bitmap;

        public LoaderResult(ImageView imageView, String uri, Bitmap bitmap) {
            this.imageView = imageView;
            this.uri = uri;
            this.bitmap = bitmap;
        }
    }
}

