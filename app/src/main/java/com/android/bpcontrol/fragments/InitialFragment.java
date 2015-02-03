package com.android.bpcontrol.fragments;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.android.bpcontrol.R;
import com.android.bpcontrol.SendTlfRegister;
import com.android.bpcontrol.utils.LogBP;

/**
 * Created by Adrian on 02/02/2015.
 */
public class InitialFragment extends Fragment {

    private boolean inTop;
    private int currentImagePosition = 0;
    private SlidingImages slidingThread;
    private Button patientAccess;

   public static InitialFragment getNewInstance(){

       InitialFragment  initialFragment = new InitialFragment();
       return initialFragment;
   }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.initialfragmentlayout, null);
        patientAccess = (Button) view.findViewById(R.id.patientAreaAccess);
        return view;

    }

    @Override
    public void onResume() {
        super.onResume();
        inTop = true;
        slidingThread =  new SlidingImages((ImageView)getActivity().findViewById(R.id.slidingImage),currentImagePosition);
        slidingThread.start();
    }


    @Override
    public void onPause() {
        super.onPause();
        inTop = false;
        currentImagePosition = slidingThread.getCounter();
        LogBP.writelog(slidingThread.getCounter() + "");
    }


    @Override
    public void onStop() {
        super.onStop();
        inTop = false;
        currentImagePosition = slidingThread.getCounter();
        LogBP.writelog(slidingThread.getCounter()+"");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        inTop = false;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        patientAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getActivity(), SendTlfRegister.class));
            }
        });

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

            DisplayMetrics displayMetrics = getActivity().getResources().getDisplayMetrics();

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
                    getActivity().runOnUiThread(new Runnable() {

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

            final Animation anim = AnimationUtils.loadAnimation(getActivity(), R.anim.fade_in);
            image.setAnimation(anim);
        }
    }

    private boolean isActivityInTop(){

        return inTop;
    }


}
