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
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Maps;
import com.osamayastal.easycare.Model.Controle.Search;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Maps_root {
    public interface MapsListener{
        void onSuccess(Maps maps);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GetSearch(final Context mcontext,final Double lat,
                          final Double lng,
                          final MapsListener listener)
    {
final String token=new User_info(mcontext).getToken();
       listener.onStart();
            String url= Server_info.API +"api/mobile/getEmployeeAndProviderNearBy";


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
                            listener.onSuccess(new Maps(response));


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
                    Map<String, String>  parameters = new HashMap<String, String>();
                    parameters.put("token",token);


                    return  parameters;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>  parameters = new HashMap<String, String>();
                    parameters.put("lat",lat.toString());
                    parameters.put("lng",lng.toString());

                    return  parameters;
                }
            };

            queue.getCache().initialize();
// add it to the RequestQueue
            queue.add(getRequest);
            queue.getCache().clear();
       }


}
