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
import com.osamayastal.easycare.Model.Const.Server_info;
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
    public void GetSearch(final Context mcontext,final String category_id,
                          final String city_id,
                          final String name,
                          final int rate,
                          final int page,
                          final int raduis,
                          final homeListener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/mobile/getProviderSearchFilter?page="+page
                    +"&limit=10&rate="+rate+"&city_id="+city_id+"&category_id="+category_id
                    +"&name=ا"+name+"&raduis="+raduis;


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


//            @Override
//            protected Map<String, String> getParams() throws AuthFailureError {
//                Map<String, String> parameters = new HashMap<>();
//                if (rate!=-1) parameters.put("rate",rate+"");
//                    if (city_id!=null) parameters.put("city_id",city_id);
//                    if (category_id!=null) parameters.put("category_id",category_id);
//                parameters.put("name",name);
//                return  parameters;
//            }
        };
        queue.add(request);

       }


}
