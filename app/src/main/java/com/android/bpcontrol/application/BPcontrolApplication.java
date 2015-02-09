package com.android.bpcontrol.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;

import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;


/**
 * Created by Adrian Carrera on 22/1/15.
 */
public class BPcontrolApplication extends Application{

    public static enum FontsTypeface{
        RobotoRegular,
        RobotoBold,
        RobotoItalic,
        ForteRegular
    }

    private Typeface robotoItalic,robotoBold,robotoRegular,forteRegular;

    @Override
    public void onCreate() {
        super.onCreate();

        initImageLoader();
    }

    private void initImageLoader() {
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getApplicationContext())
                .defaultDisplayImageOptions(getImageOptions(0).build())
                .memoryCache(new WeakMemoryCache())
                .build();

        ImageLoader.getInstance().init(config);
    }



    public Typeface getTypeface(FontsTypeface type){

        switch(type){
            case RobotoRegular:
                return getRobotoRegular();
            case RobotoBold:
                return getRobotoBold();
            case RobotoItalic:
                return getRobotoItalic();
            case ForteRegular:
                return getForteRegular();
        }
        return getRobotoRegular();
    }

    private Typeface getRobotoRegular(){
        if(robotoRegular == null){
            robotoRegular = Typeface.createFromAsset(getAssets(),"Roboto-Regular.ttf");
        }
        return robotoRegular;
    }

    private Typeface getRobotoBold(){
        if(robotoBold == null){
            robotoBold = Typeface.createFromAsset(getAssets(),"Roboto-Bold.ttf");
        }
        return robotoBold;
    }

    private Typeface getRobotoItalic(){
        if(robotoItalic == null){
            robotoItalic= Typeface.createFromAsset(getAssets(),"Roboto-Italic.ttf");
        }
        return robotoItalic;
    }

    private Typeface getForteRegular(){
        if(forteRegular == null){
            forteRegular = Typeface.createFromAsset(getAssets(),"Forte.ttf");
        }
        return forteRegular;
    }

    public void loadPerfilImageView(String uuid, final ImageView imageView) {

        final String url = "http://app2.hesoftgroup.eu/hypertensionPatient/restDownloadProfileImage/a5683026-0f3b-4ea5-a129-0aec2c36c1eb";
        final boolean[] imgDisplayed = {false};

        ImageLoader.getInstance().displayImage(url, imageView, new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, final View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, final View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, final View view, final Bitmap loadedImage) {

                    imgDisplayed[0] = true;
                    imageView.setImageBitmap(loadedImage);
            }

            @Override
            public void onLoadingCancelled(String imageUri, final View view) {

            }
        });
    }

    public static DisplayImageOptions.Builder getImageOptions(int defaultImg) {
        return getImageOptions(defaultImg, true);
    }

    public static DisplayImageOptions.Builder getImageOptions(int defaultImg, boolean usePlaceHolder) {


        boolean cacheInMemory = true;
        boolean cacheOnDisc = true;
        boolean resetViewOnLoad = false;


        BitmapFactory.Options resizeOptions = new BitmapFactory.Options();
        resizeOptions.inScaled = true;


        DisplayImageOptions.Builder builder = new DisplayImageOptions.Builder();
        if (defaultImg != 0) {
            builder
                    .resetViewBeforeLoading(resetViewOnLoad)  // default
                    .cacheInMemory(cacheInMemory) // default
                    .cacheOnDisc(cacheOnDisc)
                    .considerExifParams(true)
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .decodingOptions(resizeOptions)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT);

            if (usePlaceHolder) {
                builder.showImageOnLoading(defaultImg)
                        .showImageForEmptyUri(defaultImg)
                        .showImageOnFail(defaultImg);
            }

        } else {
            builder
                    .bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .cacheInMemory(cacheInMemory)
                    .cacheOnDisc(cacheOnDisc)
                    .resetViewBeforeLoading(resetViewOnLoad)
                    .decodingOptions(resizeOptions)
                    .considerExifParams(true);
        }
        return builder;


    }
}




