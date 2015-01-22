package com.android.bpcontrol.customViews;

import android.annotation.TargetApi;
import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;
import android.os.Build;

import com.android.bpcontrol.application.BPcontrolApplication;
import com.android.bpcontrol.utils.LogBP;

/**
 * Created by Adrian on 22/01/2015.
 */
public class RobotoTextView extends TextView {
    public RobotoTextView(Context context) {
        super(context);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        setTypeface();
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        setTypeface();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        setTypeface();
    }

    public void setTypeface(){
        if(getTag()==null){
            setTag("RobotoRegular");
        }
        try {

            if(getTag().equals("RobotoRegular")){
                setTypeface(((BPcontrolApplication) getContext().getApplicationContext())
                               .getTypeface(BPcontrolApplication.RobotoTypeface.Regular));
            }else if(getTag().equals("RobotoBold")){
                setTypeface(((BPcontrolApplication) getContext().getApplicationContext())
                                .getTypeface(BPcontrolApplication.RobotoTypeface.Bold));
            }else if(getTag().equals("RobotoItalic")){
                setTypeface(((BPcontrolApplication) getContext().getApplicationContext())
                                .getTypeface(BPcontrolApplication.RobotoTypeface.Italic));
            }

        }catch(Exception e){
            LogBP.printStackTrace(e);
            setTypeface(((BPcontrolApplication) getContext().getApplicationContext())
                    .getTypeface(BPcontrolApplication.RobotoTypeface.Regular));

        }

    }
}
