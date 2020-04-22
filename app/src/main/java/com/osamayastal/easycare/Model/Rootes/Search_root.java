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
import com.google.android.gms.maps.model.LatLng;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Home;
import com.osamayastal.easycare.Model.Controle.Maps;
import com.osamayastal.easycare.Model.Controle.Search;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Search_root {
    public interface homeListener{
        void onSuccess(Search home);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GetSearch(final Context mcontext, final String category_id,
                          final String city_id,
                          final String name,
                          final String rate,
                          final int page,
                          final String raduis,
                          LatLng mLatLng, final homeListener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/mobile/getProviderSearchFilter?page="+page
                    +"&limit=10&rate="+rate+"&city_id="+city_id+"&category_id="+category_id
                    +"&name=ا"+name+"&raduis="+raduis+"&lat="+mLatLng.latitude+"&lng="+mLatLng.longitude;
        Log.d("Response", url);

        final String token=new User_info(mcontext).getToken();
            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context
                //Build.logError("Setting a new request queue");
            }
            // prepare the Request
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response.toString());
                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(response);
                    Log.d("search name",name);
                    Log.d("Response", response.toString());
                    listener.onSuccess(new Search(Jobject));

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
                Map<String, String> parameters = new HashMap<>();

                parameters.put("token",token);
                return  parameters;
            }
        };
        queue.add(request);

       }


}
