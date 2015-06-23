package com.android.bpcontrol.webservice;

/**
 * Created by Adrian Carrera  on 4/2/15.
 */

import android.app.AlertDialog;
import android.app.DownloadManager;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.provider.UserDictionary;
import android.view.Gravity;
import android.view.View;
import android.widget.TextView;

import com.android.bpcontrol.R;
import com.android.bpcontrol.application.BPcontrolApplication;
import com.android.bpcontrol.application.BPcontrolMasterActivity;
import com.android.bpcontrol.controllers.LateralMenuController;
import com.android.bpcontrol.databases.DataStore;
import com.android.bpcontrol.fragments.InformationFragment;
import com.android.bpcontrol.model.Center;
import com.android.bpcontrol.model.Message;
import com.android.bpcontrol.model.Pressure;
import com.android.bpcontrol.model.Pressures;
import com.android.bpcontrol.model.PressuresAfternoon;
import com.android.bpcontrol.model.PressuresMorning;
import com.android.bpcontrol.model.User;
import com.android.bpcontrol.model.YoutubeVideo;
import com.android.bpcontrol.utils.DateUtils;
import com.android.bpcontrol.utils.LogBP;
import com.android.bpcontrol.utils.SharedPreferenceConstants;
import com.android.volley.AuthFailureError;
import com.android.volley.DefaultRetryPolicy;
import com.android.volley.NetworkResponse;
import com.android.volley.ParseError;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;
import com.android.volley.toolbox.JsonArrayRequest;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.JsonRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.android.volley.Request;
import com.google.android.gms.maps.model.LatLng;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.protocol.BasicHttpContext;
import org.apache.http.protocol.HttpContext;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Document;

import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Date;
import java.util.EventListener;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;


public class WSManager {

    private static final String URLBASE ="http://app2.hesoftgroup.eu";
    private static WSManager instance;
    private SharedPreferences preferences;

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

    public static interface BPcontrolApiJsonArrayCallback extends EventListener {

        public void onSuccess(JSONArray response);
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

    public static interface GetUserPressures extends EventListener{

        public void onUserPressuresReceived(ArrayList<Pressure> pressures);
    }

    public static interface GetMessages extends EventListener{
        public void onUserMessagesReceived(List<Message> listmenssages);
    }

    public static interface GetHealthCenters extends EventListener{
        public void onCentersReceived(List<Center> listmenssages);
    }

    public static interface SendMessage extends EventListener{
        public void onSendMessageServerReceived();
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


    private void webservicePostPressuresWithCallback(final Context context, final String url,final Map<String,String> params,
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

    private void webservicePostMessageWithCallback(final Context context, final String url,final Map<String,String> params,
                                            final Map<String,String> headers,final BPcontrolApiJsonArrayCallback callback ){

        if (queue == null) queue = Volley.newRequestQueue(context);

        JSONObject JSONobject = new JSONObject(params);
        try {
            JSONobject.put("uuid",User.getInstance().getUUID());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        JsonRequest request = new JsonArrayCustomRequest(Request.Method.POST,url,JSONobject,new Response.Listener<JSONArray>(){


            @Override
            public void onResponse(JSONArray result) {
                LogBP.writelog("POST","Respponse to "+url+" response: "+result.toString());
                callback.onSuccess(result);
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                LogBP.writelog("POST","Error in call " + url);
                callback.onFailure(volleyError);
            }
        });

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
           JSONObject userinfo = new JSONObject(response);
           JSONObject json = userinfo.getJSONObject("patient");
           User.getInstance().setUUID(json.getString("uuid"));
           User.getInstance().setActive(json.getBoolean("active"));
           User.getInstance().setBirthDate(json.getString("birthDate").split("T")[0]);
           User.getInstance().setCreationDate(json.getString("dateCreated").split("T")[0]);
           User.getInstance().setEmail(json.getString("email"));
           User.getInstance().setFirstSurname(json.getString("firstSurname"));
           User.getInstance().setIdentityCard(json.getString("identityCard"));
           User.getInstance().setLastUpdate(json.getString("lastUpdated").replace('T', ' ').replace('Z', (char) 0));
           User.getInstance().setMobileNumber(json.getString("mobileNumber"));
           User.getInstance().setMobileNumberPrefix(json.getString("mobileNumberPrefix"));
           User.getInstance().setName(json.getString("name"));
           User.getInstance().setNotes(json.getString("notes"));
           User.getInstance().setSecondSurname(json.getString("secondSurname"));
           User.getInstance().setTown(json.getString("town"));

           User.getInstance().DIANA_SYSTOLIC_INDEX= userinfo.getInt("sbp");
           User.getInstance().DIANA_DIASTOLIC_INDEX = userinfo.getInt("dbp");
           User user = User.getInstance();
           String f = "uhj";

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
        Map<String,String> params = preparePostPressures(morning, afternoon);
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
       webservicePostPressuresWithCallback(context, url, params, headers, new BPcontrolApiJsonCallback() {
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
            params.put("pulse3n", p3.getPulse());
        }

    }

    private void parseSendResponse(JSONObject json, SendPressures callback){

        String link = null;
        int pStatus = -1;
        String videoInfo="";
        try {
            pStatus = Integer.parseInt(json.getString("patientStatus"));
            link = json.getString("infoLink");
            videoInfo = json.getString("infoLinkName");

        } catch (JSONException ex) {
            LogBP.printStackTrace(ex);
        }
        if (link == null || link.equalsIgnoreCase("null")){
            callback.onSendPressures(null, pStatus);
            return;
        }

        callback.onSendPressures(new YoutubeVideo(videoInfo, link), pStatus);


    }

    public void getUserPressures(final Context context, String date, final GetUserPressures callback) throws ParseException {

        String url;
        if (date != null) {
            url= URLBASE + "/hypertensionBloodPressure/restList/"+User.getInstance().getUUID()
                    +"?date="+DateUtils.dateStringToWSdate(date);
        }else{
            url = URLBASE+"/hypertensionBloodPressure/restList/"+User.getInstance().getUUID();
        }
        webserviceCallWithCallback(context, url, new BPcontrolApiCallback() {
            @Override
            public void onSuccess(String response) {
                ArrayList<Pressure> pressures = parseUserPressures(response);
                callback.onUserPressuresReceived(pressures);
            }

            @Override
            public void onFailure(Exception e) {

                showApiConnectivityError(context);
            }
        });
    }

    private ArrayList<Pressure> parseUserPressures(String jsonresponse) {

        ArrayList<Pressure> tmp = new ArrayList<>();


            try {
                JSONArray array = new JSONArray(jsonresponse);
                if (array.length() > 0){
                Pressure pressure;
                JSONObject jsonpressure;
                for (int i = array.length()-1; i>-1; i--) {
                    jsonpressure = array.getJSONObject(i);
                    pressure = new Pressure();
                    pressure.setSystolic(jsonpressure.getString("systole"));
                    pressure.setDiastolic(jsonpressure.getString("diastole"));
                    pressure.setPulse(jsonpressure.getString("pulse"));
                    pressure.setDate(DateUtils.wsStringDateToDefaultDate(jsonpressure.getString("dateTaken")));
                    calculateDIANALimit(pressure);
                    Pressure p = pressure;
                    tmp.add(pressure);
                }
                }
            } catch (JSONException ex) {
                LogBP.printStackTrace(ex);
            } catch (ParseException ex) {
                LogBP.printStackTrace(ex);
            }

        return tmp;
    }

    private void calculateDIANALimit(Pressure pressure){

        int systolic,diastolic,pulse,status;

        if (pressure.getSystolic()==null || pressure.getSystolic().equalsIgnoreCase("null")){
            systolic = 120;
        }else{
            systolic=Integer.parseInt(pressure.getSystolic());
        }

        if (pressure.getDiastolic()==null || pressure.getDiastolic().equalsIgnoreCase("null")){
            diastolic = 80;
        }else{
            diastolic=Integer.parseInt(pressure.getDiastolic());
        }

        if (systolic>User.getInstance().DIANA_SYSTOLIC_INDEX || diastolic > User.getInstance().DIANA_DIASTOLIC_INDEX){
            status = 2;
        }else if ((User.getInstance().DIANA_SYSTOLIC_INDEX-systolic)<=5 || (User.getInstance().DIANA_DIASTOLIC_INDEX-diastolic)<=5){
            status = 1;
        }else{
            status = 0;
        }

        pressure.setSemaphore(status);
    }

    public void getUserMessagesChat(final Context context, String date, final GetMessages callback){
        String url="";
        if (date != null) {
            try {
                url= URLBASE + "/hypertensionPatientChat/restList/"+User.getInstance().getUUID()
                        +"?date="+DateUtils.dateStringToWSdate(date);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }else{
            url = URLBASE+"/hypertensionPatientChat/restList/"+User.getInstance().getUUID();
        }
        webserviceCallWithCallback(context, url, new BPcontrolApiCallback() {
            @Override
            public void onSuccess(String response) {
                List<Message> messages = parseUserMessages(context, response);
                callback.onUserMessagesReceived(messages);
            }

            @Override
            public void onFailure(Exception e) {
                showApiConnectivityError(context);
            }
        });
    }

    private  List<Message> parseUserMessages(Context context,
                                             String response){

        List<Message> messages = new ArrayList<>();
        int lastid = 0,lastMessageInPreferences = 0;
        if (preferences == null && context != null){
            preferences = context.getSharedPreferences(SharedPreferenceConstants.SHARE_PREFERENCE_KEY,Context.MODE_PRIVATE);
            lastMessageInPreferences = preferences.getInt(SharedPreferenceConstants.LASTMESSAGEID,0);
        }
        try {
            JSONArray array = new JSONArray(response);
            if (array.length() > 0){
                Message message;
                JSONObject jsonobject;
                String completedate,date,hour;
                for (int i = array.length()-1; i>-1; i--) {
                    jsonobject = array.getJSONObject(i);
                    lastid = jsonobject.getInt("id");
                    message = new Message();
                    message.setContent(jsonobject.getString("text"));
                    if (jsonobject.getString("user").equals("null")){
                        message.setUser(true);
                    }else{
                        message.setUser(false);
                    }
                    message.setSeen(jsonobject.getBoolean("seen"));
                    completedate = jsonobject.getString("dateCreated");
                    date = completedate.split("T")[0];
                    hour = completedate.split("T")[1].substring(0,5);
                    message.setDate(DateUtils.dateToString(DateUtils.wsStringDateToDefaultDate(date),DateUtils.DEFAULT_FORMAT)+" "+hour);
                    messages.add(message);
                }
            }
        } catch (JSONException ex) {
            LogBP.printStackTrace(ex);
        } catch (ParseException ex) {
            LogBP.printStackTrace(ex);
        }
        if (lastMessageInPreferences != 0){
            if (lastid > lastMessageInPreferences ) {
                SharedPreferences.Editor editor = preferences.edit();
                editor.putInt(SharedPreferenceConstants.LASTMESSAGEID, lastid);
                editor.commit();
                DataStore.getInstance().setUnReadMessages((lastMessageInPreferences - lastid));
            }
        }else{
            DataStore.getInstance().setUnReadMessages(0);
        }
        return messages;

    }

    public void sendMessage(final Context context,Message message, final SendMessage callback){

        final String url=URLBASE+"/hypertensionPatientChat/restSave";
        Map<String,String> params = new HashMap<>();
        params.put("text",message.getContent());
        Map<String,String> headers = new HashMap<>();
        headers.put("Content-Type", "application/json");
        webservicePostMessageWithCallback(context,url,params,headers, new BPcontrolApiJsonArrayCallback() {
            @Override
            public void onSuccess(JSONArray response) {

                LogBP.writelog("SEND MESSAGE", "Message receive sendMessage()");
                callback.onSendMessageServerReceived();
            }

            @Override
            public void onFailure(Exception e) {
                LogBP.printStackTrace(e);
                showApiMessagesSendError(context);
            }
        });
    }

    private void showApiPressuresSendError(final Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.pressuresendproblems));
        builder.setPositiveButton(context.getResources().getString(R.string.noconnectiondialogpositiveWS), new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

                ((BPcontrolMasterActivity) context).dissmissProgressDialog();
                ((BPcontrolMasterActivity) context).finish();
            }
        });
        AlertDialog dialog = builder.show();
        TextView messageText = (TextView)dialog.findViewById(android.R.id.message);
        messageText.setGravity(Gravity.CENTER);

        dialog.show();

    }

    private void showApiMessagesSendError(final Context context){

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setMessage(context.getResources().getString(R.string.failconnectionmessage));
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

    private class JsonArrayCustomRequest extends JsonRequest{

        public JsonArrayCustomRequest(int post, String url, JSONObject objectrequest, Response.Listener<JSONArray> listener, Response.ErrorListener error) {
            super(Method.POST, url,objectrequest == null ? null : objectrequest.toString() ,listener, error);

        }

        @Override
        protected Response<JSONArray> parseNetworkResponse(NetworkResponse response) {
            String json = null;
            try {
                json = new String(
                        response.data, HttpHeaderParser.parseCharset(response.headers));
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
            // The WS returns JSONArray with all chat messages,in this moment, it is not necessary for application.
            try {
                return Response.success(new JSONArray("[]"),
                        HttpHeaderParser.parseCacheHeaders(response));
            } catch (JSONException e) {
                LogBP.printStackTrace(e);
                return Response.error(new ParseError(e));
            }

        }

        @Override
        public Map getHeaders() throws AuthFailureError {
            Map headers = new HashMap();
            headers.put("Content-Type", "application/json");
            return headers;
        }

        @Override
        public int compareTo(Object another) {

            return 0;
        }
    }

    public void getAssociateHealthCenters(final Context context, final GetHealthCenters callback){

        final String url = URLBASE+"/admin/restOrganizationList/";

        webserviceCallWithCallback(context,url,new BPcontrolApiCallback() {
            @Override
            public void onSuccess(String response) {
               List<Center> centers = parseCenters(response);
                callback.onCentersReceived(centers);
            }

            @Override
            public void onFailure(Exception e) {

            }
        });


    }

    private List<Center> parseCenters(String response){

        List<Center> centers= new ArrayList<Center>();

        try {
            JSONArray array = new JSONArray(response);
            JSONObject tmp;
            Center center;
            double longitude,latitude;
            for (int i = 0;i<array.length();i++){
                tmp = array.getJSONObject(i);
                center = new Center();

                longitude =tmp.getDouble("longitude");
                latitude = tmp.getDouble("latitude");
                center.setLocation(new LatLng(latitude,longitude));
                center.setName(tmp.getString("description"));
                center.setContactAddress(tmp.getString("contactAddress"));
                center.setTlf(tmp.getString("contactPhone"));
                center.setEmail(tmp.getString("contactEmail"));
                centers.add(center);
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

        return centers;

    }

    public Document getGoogleMapsRouteDocument(final String url){

        try {
            HttpClient httpClient = new DefaultHttpClient();
            HttpContext localContext = new BasicHttpContext();
            HttpPost httpPost = new HttpPost(url);
            HttpResponse response = httpClient.execute(httpPost, localContext);
            InputStream in = response.getEntity().getContent();
            DocumentBuilder builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
            Document doc = builder.parse(in);
            return doc;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;


    }

}
