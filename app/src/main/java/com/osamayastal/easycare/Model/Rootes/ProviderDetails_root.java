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
import com.osamayastal.easycare.Model.Controle.City;
import com.osamayastal.easycare.Model.Controle.Provider_Details;

import org.json.JSONObject;

public class ProviderDetails_root {
    public interface DetailsListener{
        void onSuccess(Provider_Details details);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GetDetailsListener(final Context mcontext,String id,
                          final DetailsListener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/providerDetails/"+id;


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
            }
            // prepare the Request
            JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.GET, url, null,
                    new Response.Listener<JSONObject>()
                    {
                        @Override
                        public void onResponse(JSONObject response) {

                            Log.d("Response", response.toString());
                            listener.onSuccess(new Provider_Details(response));


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
