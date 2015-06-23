package com.hesoftgroup.bpcontrol.application;

import android.app.Application;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Typeface;
import android.view.View;
import android.widget.ImageView;

import com.crashlytics.android.Crashlytics;
import com.hesoftgroup.bpcontrol.utils.LogBP;
import com.nostra13.universalimageloader.cache.memory.impl.WeakMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.FailReason;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.listener.ImageLoadingListener;
import io.fabric.sdk.android.Fabric;


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
        Fabric.with(this, new Crashlytics());
        LogBP.setDebugMode(true);
        initImageLoaderConfiguration();
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

    public void loadPerfilImageView(final String uuid, final ImageView imageView,int placeholder) {

        final String url = "http://app2.hesoftgroup.eu/hypertensionPatient/restDownloadProfileImage/"+uuid;

        ImageLoader.getInstance().displayImage(url, imageView, makeImageOptions(placeholder, false), new ImageLoadingListener() {
            @Override
            public void onLoadingStarted(String imageUri, View view) {

            }

            @Override
            public void onLoadingFailed(String imageUri, View view, FailReason failReason) {

            }

            @Override
            public void onLoadingComplete(String imageUri, View view, Bitmap loadedImage) {

            }

            @Override
            public void onLoadingCancelled(String imageUri, View view) {

            }
        });

    }

    private void initImageLoaderConfiguration(){
        ImageLoaderConfiguration config = new ImageLoaderConfiguration.Builder(this.getApplicationContext())
                .defaultDisplayImageOptions(makeImageOptions(0,false))
                .memoryCache(new WeakMemoryCache())
                .build();

        ImageLoader.getInstance().init(config);
    }

    private DisplayImageOptions makeImageOptions(int placeHolderImage,boolean hasPlaceHolder){

        boolean cacheInMemory = true;
        boolean cacheOnDisc = true;
        boolean resetViewOnLoad = false;


        BitmapFactory.Options resizeOptions = new BitmapFactory.Options();
        resizeOptions.inScaled = true;


        DisplayImageOptions.Builder optionsBuilder = new DisplayImageOptions.Builder();

        optionsBuilder.resetViewBeforeLoading(resetViewOnLoad)
                .cacheInMemory(cacheInMemory)
                .cacheOnDisk(cacheOnDisc)
                .considerExifParams(true)
                .bitmapConfig(Bitmap.Config.RGB_565)
                .decodingOptions(resizeOptions)
                .imageScaleType(ImageScaleType.IN_SAMPLE_INT);

        if (hasPlaceHolder) {
            optionsBuilder.showImageOnLoading(placeHolderImage)
                    .showImageForEmptyUri(placeHolderImage)
                    .showImageOnFail(placeHolderImage);
        }

        return optionsBuilder.build();

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
                    .resetViewBeforeLoading(resetViewOnLoad)
                    .cacheInMemory(cacheInMemory)
                    .cacheOnDisk(cacheOnDisc)
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
             builder.bitmapConfig(Bitmap.Config.RGB_565)
                    .imageScaleType(ImageScaleType.IN_SAMPLE_INT)
                    .cacheInMemory(cacheInMemory)
                    .cacheOnDisk(cacheOnDisc)
                     .resetViewBeforeLoading(resetViewOnLoad)
                    .decodingOptions(resizeOptions)
                    .considerExifParams(true);
        }
        return builder;


    }
}




