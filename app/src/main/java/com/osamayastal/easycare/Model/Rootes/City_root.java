package com.osamayastal.easycare.Model.Rootes;

import android.content.Context;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Controle.City;
import com.osamayastal.easycare.Model.Controle.Search;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class City_root {
    public interface cityListener{
        void onSuccess(City cities);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GetCities(final Context mcontext,
                          final cityListener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/admin/city";


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

                            Log.d("Response", response.toString());
                            listener.onSuccess(new City(response,mcontext));


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

    public void GetComplain_type(final Context mcontext,
                          final cityListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/ComplainsType";


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

                        Log.d("Response", response.toString());
                        listener.onSuccess(new City(response,mcontext));


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
    public void GetResons(final Context mcontext,
                          final cityListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/admin/reason";


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

                        Log.d("Response", response.toString());
                        listener.onSuccess(new City(response,mcontext));


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

}
