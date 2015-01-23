package com.android.bpcontrol.customViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.os.Build;
import android.widget.RelativeLayout;

/**
 * Created by Adrian on 23/01/2015.
 */
public class HomeGridCell extends RelativeLayout {
    public HomeGridCell(Context context) {
        super(context);
    }

    public HomeGridCell(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public HomeGridCell(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }


}
