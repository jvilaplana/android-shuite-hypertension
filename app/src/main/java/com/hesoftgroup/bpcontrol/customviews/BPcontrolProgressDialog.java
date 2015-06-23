package com.hesoftgroup.bpcontrol.customviews;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.view.View;

import com.hesoftgroup.bpcontrol.R;
import com.hesoftgroup.bpcontrol.utils.LogBP;

/**
 * Created by Adrian Carrera on 22/1/15.
 */
public class BPcontrolProgressDialog extends ProgressDialog{


    private String message;
    private boolean textVisibily;

    public BPcontrolProgressDialog(Context context){
        super(context);
        init();
    }

    public BPcontrolProgressDialog(Context context, int theme){
         super(context, theme);
         init();
    }

    public void setMessage(String message,boolean visibility){
        this.message = message;
        this.textVisibily = visibility;

    }

    private void init(){
        setCanceledOnTouchOutside(false);
        getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
    }

    @Override
    public void show(){

        try{
            super.show();
            View view = getLayoutInflater().inflate(R.layout.custumprogressdialog,null);
            RobotoTextView textview = (RobotoTextView) view.findViewById(R.id.progressMessage);
            if (message != null && !message.equals("") && textVisibily) {
                textview.setText(message);
            }else{

                textview.setVisibility(View.GONE);
            }
            setContentView(view);

        }catch (Exception e){
            LogBP.printStackTrace(e);
        }



    }


}
