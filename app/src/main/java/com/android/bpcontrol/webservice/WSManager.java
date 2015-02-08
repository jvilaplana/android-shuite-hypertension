package com.android.bpcontrol.webservice;

/**
 * Created by Adrian Carrera  on 4/2/15.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import com.android.bpcontrol.model.User;
import com.android.bpcontrol.utils.LogBP;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONException;
import org.json.JSONObject;


import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;
import java.nio.charset.Charset;
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

    public static interface BPcontrolApiCallback extends EventListener {

        public void onSuccess(String response);
        public void onFailure(Exception e);
    }

    public static interface SendPhoneNumber extends EventListener{

        public void onRegisterPhone();
    }


    public static interface CorrectSMSCode extends EventListener{

        public void checkSMScode();

    }



    private void webserviceCall(final Context context, final String url, final BPcontrolApiCallback callback){
        if(queue==null)queue = Volley.newRequestQueue(context);

        StringRequest jsObjRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                LogBP.writelog("Respponse to "+url+" response: "+result);
                callback.onSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogBP.writelog("Error en la llamada " + url);
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

    public void sendSMScode(Context context, String prefix, String tlfnumber, String code,final CorrectSMSCode callback){

        final String url = URLBASE+"/hypertensionPatient/restValidateCode/"+prefix+tlfnumber+"?code="+code;

        webserviceCall(context,url,new BPcontrolApiCallback() {
            @Override
            public void onSuccess(String jsonResponse) {

                parseSMScodeResponse(jsonResponse);
                callback.checkSMScode();
            }

            @Override
            public void onFailure(Exception e) {
                LogBP.printStackTrace(e);
            }
        });

    }

    private void parseSMScodeResponse(String jsonresponse) {
        try {

            JSONObject json_uuid = new JSONObject(jsonresponse);
            User.getInstance().setUUID(json_uuid.getString("uuid"));
            LogBP.writelog("json1 " + json_uuid.getString("uuid"));
        } catch (JSONException ex) {
            LogBP.printStackTrace(ex);
            User.getInstance().setUUID("");
        }
    }



}
