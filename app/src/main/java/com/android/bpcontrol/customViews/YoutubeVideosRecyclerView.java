package com.android.bpcontrol.customviews;


import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.util.AttributeSet;

import com.android.bpcontrol.utils.LogBP;

/**
 * Created by Adrian on 17/02/2015.
 */
public class YoutubeVideosRecyclerView extends RecyclerView {


    public YoutubeVideosRecyclerView(Context context) {
        super(context);
    }

    public YoutubeVideosRecyclerView(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public YoutubeVideosRecyclerView(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
    }

    //BUG
    @Override
    public void stopScroll() {
        try {
            super.stopScroll();
        } catch (Exception exception) {
           LogBP.printStackTrace(exception);
        }
    }

}
