package com.osamayastal.easycare.Model.Rootes;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Controle.Home;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Home_root {
    public interface homeListener{
        void onSuccess(Home show_notif);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GetHome(final Context mcontext, final homeListener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/mobile/getHome";


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
                            listener.onSuccess(new Home(response));


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

            queue.getCache().initialize();
// add it to the RequestQueue
            queue.add(getRequest);
            queue.getCache().clear();
       }

//    public void Put_Read_notifiction(final Context mcontext,final String id_notfy,final  notificationListener lisenner){
//        RequestQueue queue = Volley.newRequestQueue(mcontext);  // this = context
//        final user_info user_info_=new user_info(mcontext);
//        String url = mcontext.getString(R.string.api)+"api/notifications/"+id_notfy;
//
//// Request a json response from the provided URL
//
//        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>()
//                {
//                    @Override
//                    public void onResponse(String response) {
//                        // response
//                        Log.d("Response", response);
//                        String jsonData = response;
//
//                    }
//                },
//                new Response.ErrorListener()
//                {
//                    @Override
//                    public void onErrorResponse(VolleyError error) {
//                        // error
//                        Log.d("Error.Response", String.valueOf(error.getMessage()));
//
//                    }
//                }
//        )
////        {
////
////            @Override
////            public Map<String, String> getHeaders() throws AuthFailureError {
////                Map<String, String>  Headers = new HashMap<String, String>();
////                Headers.put("token",user_info_.getToken());
////
////                return Headers;
////            }
////        }
//                ;
//        queue.add(postRequest);
//
//// Add the request to the RequestQueue.
//
//
//    }

}
