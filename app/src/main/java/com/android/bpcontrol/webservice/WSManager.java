package com.android.bpcontrol.webservice;

/**
 * Created by Adrian Carrera  on 4/2/15.
 */

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.view.Gravity;
import android.widget.TextView;

import com.android.bpcontrol.R;
import com.android.bpcontrol.application.BPcontrolApplication;
import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.model.Pressures;
import com.android.bpcontrol.model.PressuresAfternoon;
import com.android.bpcontrol.model.PressuresMorning;
import com.android.bpcontrol.model.User;
import com.android.bpcontrol.model.YoutubeVideo;
import com.android.bpcontrol.utils.LogBP;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class WSManager {

    private static final String URLBASE ="http://app2.hesoftgroup.eu";
    private static WSManager instance;

    private RequestQueue queue;

    public static WSManager getInstance(){
        if (instance == null)
            instance = new WSManager();
        return instance;
    }

    public static interface BPcontrolApiCallback extends EventListener {

        public void onSuccess(String response);
        public void onFailure(Exception e);
    }

    public static interface BPcontrolApiJsonCallback extends EventListener {

        public void onSuccess(JSONObject response);
        public void onFailure(Exception e);
    }
    public static interface SendPhoneNumber extends EventListener{

        public void onRegisterPhone();
    }


    public static interface CorrectSMSCode extends EventListener{

        public void checkSMScode();

    }

    public static interface GetUserInfoWithUUID extends EventListener{

        public void onUserInfoObtained();
    }

    public static interface SendPressures extends EventListener{

        public void onSendPressures(YoutubeVideo youtubeVideo,int semaphore);

    }

    private void webserviceCallWithCallback(final Context context, final String url, final BPcontrolApiCallback callback){
        if(queue==null)queue = Volley.newRequestQueue(context);

        StringRequest jsObjRequest = new StringRequest(url, new Response.Listener<String>() {
            @Override
            public void onResponse(String result) {
                LogBP.writelog("GET","Respponse to "+url+" response: "+result);
                callback.onSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogBP.writelog("GET","Error in call " + url);

                callback.onFailure(volleyError);
            }
        });

        jsObjRequest.setRetryPolicy(new DefaultRetryPolicy(20000, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT ));
        queue.add(jsObjRequest);
    }


    private void webservicePressuresPost(final Context context, final String url,final Map<String,String> params,
                                            final Map<String,String> headers,final BPcontrolApiJsonCallback callback ){

        if (queue == null) queue = Volley.newRequestQueue(context);

        JSONObject JSONobject = new JSONObject(params);
        try {
            JSONobject.put("uuid",User.getInstance().getUUID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.POST,url,JSONobject,new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject result) {
                LogBP.writelog("POST","Respponse to "+url+" response: "+result.toString());
                callback.onSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogBP.writelog("POST","Error in call " + url);
                callback.onFailure(volleyError);
            }
        }){

           @Override
           public Map<String,String> getHeaders(){

               return headers;
           }
        };

        queue.add(request);

    }

    public void sendPhoneNumber(final Context context,String prefixNumber,String phoneNumber, final SendPhoneNumber callback){
        final String url = URLBASE+"/hypertensionPatient/restValidateMobile/"+prefixNumber+phoneNumber;
        webserviceCallWithCallback(context, url, new BPcontrolApiCallback() {
            @Override
            public void onSuccess(String jsonResponse) {
                LogBP.writelog("WSNumber", "Number send");
                callback.onRegisterPhone();
            }

            @Override
            public void onFailure(Exception e) {

                LogBP.printStackTrace(e);
                showApiConnectivityError(context);
            }
        });
    }

    public void sendSMScode(final Context context, String prefix, String tlfnumber, String code,final CorrectSMSCode callback){

        final String url = URLBASE+"/hypertensionPatient/restValidateCode/"+prefix+tlfnumber+"?code="+code;

        webserviceCallWithCallback(context, url, new BPcontrolApiCallback() {
            @Override
            public void onSuccess(String jsonResponse) {

                parseSMScodeResponse(jsonResponse);
                callback.checkSMScode();
            }

            @Override
            public void onFailure(Exception e) {
                LogBP.printStackTrace(e);
                showApiConnectivityError(context);
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

    public void getUserInfo(final Context context, String uuid, final GetUserInfoWithUUID callback){
       final String url = "http://app2.hesoftgroup.eu/hypertensionPatient/restShow/"+uuid;


        webserviceCallWithCallback(context,url,new BPcontrolApiCallback() {
            @Override
            public void onSuccess(String jsonResponse) {

                parseUserInfo(jsonResponse);
                callback.onUserInfoObtained();

            }

            @Override
            public void onFailure(Exception e) {

                LogBP.printStackTrace(e);
                showApiConnectivityError(context);
            }
        });

    }

   private void parseUserInfo(String response){

       try {

           JSONObject json = new JSONObject(response).getJSONObject("patient");
           User.getInstance().setUUID(json.getString("uuid"));
           User.getInstance().setActive(json.getBoolean("active"));
           User.getInstance().setBirthDate(json.getString("birthDate").split("T")[0]);
           User.getInstance().setDateCreated(json.getString("dateCreated").replace('T',' ').replace('Z',(char)0));
           User.getInstance().setEmail(json.getString("email"));
           User.getInstance().setFirstSurname(json.getString("firstSurname"));
           User.getInstance().setIdentityCard(json.getString("identityCard"));
           User.getInstance().setLastUpdate(json.getString("lastUpdated").replace('T',' ').replace('Z',(char)0));
           User.getInstance().setMobileNumber(json.getString("mobileNumber"));
           User.getInstance().setMobileNumberPrefix(json.getString("mobileNumberPrefix"));
           User.getInstance().setName(json.getString("name"));
           User.getInstance().setNotes(json.getString("notes"));
           User.getInstance().setSecondSurname(json.getString("secondSurname"));
           User.getInstance().setTown(json.getString("town"));

       } catch (JSONException ex) {
           LogBP.printStackTrace(ex);
       }


   }

    private void showApiConnectivityError(final Context context){

        final AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.noconnectiondialogWS));
        builder.setPositiveButton(context.getResources().getString(R.string.noconnectiondialogpositiveWS), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ((BPcontrolMasterActivity)context).dissmissProgressDialog();
                ((BPcontrolMasterActivity)context).finish();
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);




        dialog.show();

    }



    public void sendPressures(final Context context,PressuresMorning morning, PressuresAfternoon afternoon,final SendPressures callback){

        final String url=URLBASE+"/hypertensionBloodPressure/restSave";
        Map<String,String> params = preparePostPressures(morning,afternoon);
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
       webservicePressuresPost(context,url,params,headers, new BPcontrolApiJsonCallback() {
           @Override
           public void onSuccess(JSONObject response) {

               parseSendResponse(response, callback);

           }

           @Override
           public void onFailure(Exception e) {
                    LogBP.printStackTrace(e);
                    showApiPressuresSendError(context);
           }
       });

    }

    private Map<String,String> preparePostPressures(PressuresMorning pressuresMorning,PressuresAfternoon pressuresAfternoon){

        Map<String,String> params = new HashMap<>();

        addJsonPressureParams(pressuresMorning, params);
        addJsonPressureParams(pressuresAfternoon, params);

        return params;
    }

    private void addJsonPressureParams(Pressures pressures,Map<String,String> params){

        List<Pressure> pList= pressures.getAllPressures();
        final Pressure p1 = pList.get(0);
        final Pressure p2 = pList.get(1);
        final Pressure p3 = pList.get(2);

        if (pressures.areMorningPressures()){

            params.put("systole1m",p1.getSystolic());
            params.put("systole2m",p2.getSystolic());
            params.put("systole3m",p3.getSystolic());

            params.put("diastole1m",p1.getDiastolic());
            params.put("diastole2m",p2.getDiastolic());
            params.put("diastole3m",p3.getDiastolic());

            params.put("pulse1m",p1.getPulse());
            params.put("pulse2m",p2.getPulse());
            params.put("pulse3m",p3.getPulse());


        }else{

            params.put("systole1n",p1.getSystolic());
            params.put("systole2n",p2.getSystolic());
            params.put("systole3n",p3.getSystolic());

            params.put("diastole1n",p1.getDiastolic());
            params.put("diastole2n",p2.getDiastolic());
            params.put("diastole3n",p3.getDiastolic());

            params.put("pulse1n",p1.getPulse());
            params.put("pulse2n",p2.getPulse());
            params.put("pulse3n",p3.getPulse());
        }

    }

    private void parseSendResponse(JSONObject json, SendPressures callback){

        String link = null;
        int pStatus = -1;
        try {
            pStatus = Integer.parseInt(json.getString("patientStatus"));
            link = json.getString("infoLink");

        } catch (JSONException ex) {
            LogBP.printStackTrace(ex);
        }
        if (link == null){
            callback.onSendPressures(null,pStatus);
            return;
        }

        callback.onSendPressures(new YoutubeVideo("",link),pStatus);


    }

    private void showApiPressuresSendError(final Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.pressuresendproblems));
        builder.setPositiveButton(context.getResources().getString(R.string.noconnectiondialogpositiveWS), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ((BPcontrolMasterActivity)context).dissmissProgressDialog();
                ((BPcontrolMasterActivity)context).finish();
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);

        dialog.show();

    }

}
