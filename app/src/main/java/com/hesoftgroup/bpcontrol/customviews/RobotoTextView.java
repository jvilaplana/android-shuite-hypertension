package com.hesoftgroup.bpcontrol.customviews;

import android.annotation.TargetApi;
import android.content.Context;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.util.AttributeSet;
import android.widget.TextView;
import android.os.Build;

import com.hesoftgroup.bpcontrol.application.BPcontrolApplication;
import com.hesoftgroup.bpcontrol.utils.LogBP;

/**
 * Created by Adrian on 22/01/2015.
 */
public class RobotoTextView extends TextView {
    public RobotoTextView(Context context) {
        super(context);
    }

    public RobotoTextView(Context context, AttributeSet attrs) {
        super(context, attrs);
        addTypeface();
    }

    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        addTypeface();
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    public RobotoTextView(Context context, AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
        addTypeface();
    }

    public void addTypeface(){
        if(getTag()==null){
            setTag("RobotoRegular");
        }
        try {

            if(getTag().equals("RobotoRegular")){
                setTypeface(((BPcontrolApplication) getContext().getApplicationContext())
                               .getTypeface(BPcontrolApplication.FontsTypeface.RobotoRegular));
            }else if(getTag().equals("RobotoBold")){
                setTypeface(((BPcontrolApplication) getContext().getApplicationContext())
                                .getTypeface(BPcontrolApplication.FontsTypeface.RobotoBold));
            }else if(getTag().equals("RobotoItalic")){
                setTypeface(((BPcontrolApplication) getContext().getApplicationContext())
                                .getTypeface(BPcontrolApplication.FontsTypeface.RobotoItalic));
            }

        }catch(Exception e){
            LogBP.printStackTrace(e);
            setTypeface(((BPcontrolApplication) getContext().getApplicationContext())
                    .getTypeface(BPcontrolApplication.FontsTypeface.RobotoRegular));

        }

    }

    public void setSpannableText(SpannableStringBuilder text){

        setMovementMethod(LinkMovementMethod.getInstance());
        setText(text, TextView.BufferType.SPANNABLE);

    }
}
