package com.android.bpcontrol.webservice;

/**
 * Created by Adrian on 4/2/15.
 */

import android.content.Context;

import com.android.bpcontrol.utils.LogBP;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import java.util.EventListener;

public class WSManager {

    private static final String URLBASE ="http://app2.hesoftgroup.eu";
    private static WSManager instance;

    RequestQueue queue;

    public static WSManager getInstance(){
        if (instance == null)
            instance = new WSManager();
        return instance;
    }

    public static interface BPcontrolApiCallback extends EventListener{

        public void onSuccess(String jsonResponse);
        public void onFailure(Exception e);
    }

    public static interface SendPhoneNumber extends EventListener{

        public void onRegisterPhone();
    }

    private void webserviceCall(final Context context, final String method, final BPcontrolApiCallback callback){
        if(queue==null)queue = Volley.newRequestQueue(context);

        StringRequest jsObjRequest = new StringRequest(method, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                LogBP.writelog("Respponse to "+method+" response: "+result);
                callback.onSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogBP.writelog("Error en la llamada " + method);
                LogBP.printStackTrace(volleyError);

                    callback.onFailure(volleyError);
            }
        });
        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ));
        queue.add(jsObjRequest);
    }


    public void sendPhoneNumber(Context context,String prefixNumber,String phoneNumber, final SendPhoneNumber callback){
        final String url = URLBASE+"/hypertensionPatient/restValidateMobile/"+prefixNumber+phoneNumber;
        webserviceCall(context,url,new BPcontrolApiCallback() {
            @Override
            public void onSuccess(String jsonResponse) {
                LogBP.writelog("WSNumber","Number send");
                callback.onRegisterPhone();
            }

            @Override
            public void onFailure(Exception e) {

                LogBP.printStackTrace(e);
            }
        });
    }





}
