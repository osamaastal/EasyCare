package com.osamayastal.easycare.Model.Rootes;

import android.app.DownloadManager;
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
import com.google.android.gms.maps.model.LatLng;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Maps;

import org.json.JSONException;
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
    public void GetNearServic(final Context mcontext, final LatLng latLng,
                              final MapsListener listener)
    {
final String token=new User_info(mcontext).getToken();
       listener.onStart();
            String url= Server_info.API +"api/mobile/getEmployeeAndProviderNearBy";
        Log.d("token", token.toString());
        Log.d("latlng", latLng.toString());


            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
                //Build.logError("Setting a new request queue");
            }
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response.toString());
                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(response);
                    listener.onSuccess(new Maps(Jobject));
                } catch (JSONException e1) {
                    e1.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Log.d("error",error.toString());
            }
        }){
            @Override
                public Map<String, String> getHeaders() throws AuthFailureError {
                    Map<String, String>  Headers = new HashMap<String, String>();
                    Headers.put("token",token);


                    return  Headers;
                }

                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> parameters = new HashMap<>();
                    parameters.put("lat", String.valueOf(latLng.latitude).trim());
                    parameters.put("lng", String.valueOf(latLng.longitude).trim());

                    return  parameters;
                }
        };
        queue.add(request);


       }


}
