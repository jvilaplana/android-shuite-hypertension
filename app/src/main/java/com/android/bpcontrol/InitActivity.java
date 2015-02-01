package com.android.bpcontrol;

import android.content.Context;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;

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
        getActionBar().hide();


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

            image.setAn;
            this.image = image;
            this.counter = currentImagePosition;

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
    }

    private boolean isActivityInTop(){

        return inTop;
    }


}
