package com.osamayastal.easycare.Model.Rootes;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.osamayastal.easycare.Model.Classes.Notification;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Provider;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Controle.notification;
import com.osamayastal.easycare.Model.Controle.users;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Notifications {
    public interface notificationListener{
        void onSuccess(notification show_notif);
        void onSuccess(Result result);
        void onSuccess(users users);
    }
    private RequestQueue queue;
    public void Getnotification(final Context mcontext, final notificationListener listener)
    {
        final String token=new User_info(mcontext).getToken();
        Log.d("token", token.toString());

        try{
            String url= Server_info.API +"api/mobile/notifications";


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
                //Build.logError("Setting a new request queue");
            }
            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {
                            // display response
                            Log.d("Response", response.toString());
                            try {
                                listener.onSuccess(new notification(response));
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }


                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                        Log.d("Error.Response", error.getMessage());
                        }
                    }
            ){
                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  param = new HashMap<String, String>();
                    param.put("token",token);
                    return  param;
                }
            };

            queue.getCache().initialize();
// add it to the RequestQueue
            queue.add(getRequest);
            queue.getCache().clear();
        }catch (Exception ex){

        }}
    public void Putnotification(final Context mcontext,final Boolean status, final notificationListener listener)
    {
        final String token=new User_info(mcontext).getToken();
        try{
            String url= Server_info.API +"api/mobile/updateNotification";


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
                //Build.logError("Setting a new request queue");
            }
            StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>()
                    {
                        @Override
                        public void onResponse(String response) {
                            // response
                            Log.d("Response", response);
                            String jsonData = response;
                            JSONObject Jobject = null;
                            try {
                                Jobject = new JSONObject(jsonData);
                                listener.onSuccess(new users(Jobject));
                            } catch (JSONException e1) {
                                e1.printStackTrace();
                            }



                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {
                        }
                    }
            ) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>  param = new HashMap<String, String>();
                    param.put("isEnableNotifications",status.toString());
                    Log.d("param", param.toString());

                    return  param;
                }

                @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  param = new HashMap<String, String>();
                    param.put("token",token);
                    return  param;
                }
            };
            queue.add(postRequest);

        }catch (Exception ex){

        }}
    public void Pushnotification(final Context mcontext, final Notification notification, final notificationListener listener)
    {
        final String token=new User_info(mcontext).getToken();
        try{
            String url="https://fcm.googleapis.com/fcm/send";


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
                //Build.logError("Setting a new request queue");
            }
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, notification.toJsonObject(),
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Response", response.toString());
                            Log.d("token ", token.toString());
                            listener.onSuccess(new users(null));


                        }
                    },
                    new Response.ErrorListener()
                    {
                        @Override
                        public void onErrorResponse(VolleyError error) {

//                        Log.d("Error.Response", error.getMessage());
                        }
                    }
            ){

            };
            queue.add(getRequest);

        }catch (Exception ex){

        }}

}
