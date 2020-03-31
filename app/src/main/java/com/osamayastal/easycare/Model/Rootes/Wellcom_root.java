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
import com.osamayastal.easycare.Model.Controle.StaticPages;
import com.osamayastal.easycare.Model.Controle.Wellcom;

import org.json.JSONObject;

public class Wellcom_root {
    public interface Wellcom_Listener{
        void onSuccess(Wellcom wellcom);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void Get_WellcomPages(final Context mcontext,
                          final Wellcom_Listener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/mobile/welcome";


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);
            }
            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Response", response.toString());
                            listener.onSuccess(new Wellcom(response));


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
            queue.add(getRequest);
            queue.getCache().clear();
       }


}
