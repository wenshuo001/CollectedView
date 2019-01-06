package com.example.collectedview.ImageLoaderUtil;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import java.io.FileDescriptor;
import java.util.ArrayList;
import java.util.Vector;

/**
 * Creator :Wen
 * DataTime: 2018/12/3
 * Description: 图片压缩功能的实现
 */
public class ImageResizer {
    private static final String TAG = "ImageResizer";

    ArrayList list=new ArrayList();
    public ImageResizer() {
        list.add(1);
        new Vector<>().add(1);
    }

    public Bitmap decodeSampledBitmapFromResource(Resources res,int resId,int reqWidth,int reqHeight){
        final BitmapFactory.Options options=new BitmapFactory.Options();
        options.inJustDecodeBounds= true;
        BitmapFactory.decodeResource(res,resId,options);

        options.inSampleSize = calulateInSampleSize(options,reqWidth,reqHeight);

        options.inJustDecodeBounds=false;
        return  BitmapFactory.decodeResource(res ,resId, options);
    }

    public int calulateInSampleSize(BitmapFactory.Options options,int reqWidth,int reqHeight){
        if(reqWidth==0 || reqHeight == 0){
            return 1;
        }

        final int height = options.outHeight;
        final int width = options.outWidth;

        int inSampleSize = 1;
        if(height > reqHeight || width > reqWidth){
            final int halfHeight = height/2;
            final int halfwidth = width/2;

            while ((halfHeight/inSampleSize)>=reqHeight && (halfwidth/inSampleSize) >= reqWidth){
                inSampleSize *=2;
            }
        }
        return inSampleSize;
    }

    public Bitmap decodeSampledBitmapFromFileDescriptor(FileDescriptor fd,int reqWidth,int reqHeight){
        final BitmapFactory.Options options= new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFileDescriptor(fd,null,options);
        options.inSampleSize = calulateInSampleSize(options,reqWidth,reqHeight);
        options.inJustDecodeBounds= false;
        return  BitmapFactory.decodeFileDescriptor(fd,null,options);
    }
}
