package com.android.bpcontrol.customViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.util.AttributeSet;
import android.widget.EditText;

import com.android.bpcontrol.R;
import com.android.bpcontrol.application.BPcontrolApplication;

/**
 * Created by Adrian Carrera on 03/02/2015.
 */
public class BPEditText extends EditText{

    public BPEditText(Context context) {
        super(context);
        addDrawableToBackground();
        setTypeface();
    }

    public BPEditText(Context context, AttributeSet attrs) {
        super(context, attrs);
        addDrawableToBackground();
        setTypeface();

    }

    public BPEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addDrawableToBackground();
        setTypeface();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public BPEditText(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        addDrawableToBackground();
        setTypeface();
    }

    private void addDrawableToBackground(){

        Drawable drawable = getResources().getDrawable(R.drawable.bpedittext);

        int sdk = android.os.Build.VERSION.SDK_INT;
        int jellyBean = android.os.Build.VERSION_CODES.JELLY_BEAN;
        if(sdk < jellyBean) {
            setBackgroundDrawable(drawable);
        } else {
            setBackground(drawable);
        }

    }

    private void setTypeface(){

        setTypeface(((BPcontrolApplication)getContext().getApplicationContext()).getTypeface(BPcontrolApplication.FontsTypeface.RobotoRegular));

    }

}
