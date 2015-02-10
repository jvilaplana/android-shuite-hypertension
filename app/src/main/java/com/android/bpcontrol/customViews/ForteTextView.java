package com.android.bpcontrol.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.TextView;

import com.android.bpcontrol.application.BPcontrolApplication;

/**
 * Created by Adrian on 24/1/15.
 */
public class ForteTextView extends TextView {

    public ForteTextView(Context context) {
        super(context);
    }

    public ForteTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTypeface();
    }

    public ForteTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addTypeface();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public ForteTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        addTypeface();
    }

    public void addTypeface(){

        setTypeface(((BPcontrolApplication)getContext().getApplicationContext()).getTypeface(BPcontrolApplication.FontsTypeface.ForteRegular));

    }


}
