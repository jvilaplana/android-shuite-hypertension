package com.android.bpcontrol;


import android.os.Bundle;

import android.util.DisplayMetrics;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.utils.LogBP;

/**
 * Created by Adrian on 31/01/2015.
 */
public class InitActivity extends BPcontrolMasterActivity {

    private SlidingImages slidingThread ;
    private boolean inTop;
    private int currentImagePosition = 0;

    @Override
    public void onCreate(Bundle onInstanceState){
        super.onCreate(onInstanceState);
        setContentView(R.layout.initactivitylayout);
        View view = getActionBarView();
        view.findViewById(R.id.textviewbpcontrol).setVisibility(View.GONE);
        view.findViewById(R.id.fakeInit).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.actionBarMenu).setVisibility(View.INVISIBLE);
        view.findViewById(R.id.imageinit).setVisibility(View.VISIBLE);


    }

    @Override
    protected void onResume() {
        super.onResume();
        inTop = true;
        slidingThread =  new SlidingImages((ImageView)findViewById(R.id.slidingImage),currentImagePosition);
        slidingThread.start();

    }

    @Override
    protected void onPause() {
        super.onPause();
        inTop = false;
        try {
            slidingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentImagePosition = slidingThread.getCounter();
        LogBP.writelog(slidingThread.getCounter()+"");
    }

    @Override
    protected void onStop() {
        super.onStop();
        inTop = false;
        try {
            slidingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        currentImagePosition = slidingThread.getCounter();
        LogBP.writelog(slidingThread.getCounter()+"");

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        inTop = false;
        try {
            slidingThread.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    private class SlidingImages extends Thread {

        private int counter = 0;
        private final int[] slidingImages = {R.drawable.foto00, R.drawable.foto22,R.drawable.foto33,
                                             R.drawable.foto44, R.drawable.smaria3,R.drawable.arnau3};
        private ImageView image;

        public SlidingImages(ImageView image, int currentImagePosition) {


            this.image = image;
            this.counter = currentImagePosition;
            addDimensions();
            addAnimation();

        }

        private void addDimensions() {

            DisplayMetrics displayMetrics = InitActivity.this.getResources().getDisplayMetrics();

            float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
            float dpWidth = displayMetrics.widthPixels / displayMetrics.density;


            float imagensizeHeight = (dpHeight *300) / 592;
            float imagensizeWidth = (dpWidth * 300) / 360;
            image.getLayoutParams().height = (int)(imagensizeHeight * displayMetrics.density);
            image.getLayoutParams().width = (int)(imagensizeWidth * displayMetrics.density);

            RelativeLayout.LayoutParams params = (RelativeLayout.LayoutParams)image.getLayoutParams();
            params.setMargins((int)(30*displayMetrics.density),(int)(25*displayMetrics.density),(int)(30*displayMetrics.density),0);
            image.setLayoutParams(params);

        }

        @Override
        public void run() {
            while (isActivityInTop()) {
                if (counter == slidingImages.length - 1) {
                    counter = 0;
                }
                try {
                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            image.setImageResource(slidingImages[counter]);
                        }
                    });
                    Thread.sleep(7000);
                } catch (InterruptedException e) {
                    LogBP.printStackTrace(e);
                }
                counter++;
            }
        }

        public int getCounter() {
            return counter;
        }

        private void addAnimation(){

            final Animation anim = AnimationUtils.loadAnimation(InitActivity.this,R.anim.fade_in);
            image.setAnimation(anim);

        }
    }

    private boolean isActivityInTop(){

        return inTop;
    }


}
