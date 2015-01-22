package com.android.bpcontrol.customViews;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;

/**
 * Created by Adrian on 22/1/15.
 */
public class BPcontrolProgressDialog extends ProgressDialog{


    private String message;

    public BPcontrolProgressDialog(Context context){
        super(context);
        init();
    }

    public BPcontrolProgressDialog(Context context, int theme){
         super(context, theme);
         init();
    }

    public void setMessage(String message){
        this.message = message;

    }

    private void init(){
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    //FUNCION SHOW

}
