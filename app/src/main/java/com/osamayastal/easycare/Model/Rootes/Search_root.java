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
import com.osamayastal.easycare.Model.Controle.Home;
import com.osamayastal.easycare.Model.Controle.Search;

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
                          final Float rate,
                          final int page,
                          final homeListener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/mobile/getProviderSearchFilter?page="+page+"&limit=10";


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
                            listener.onSuccess(new Search(response));


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
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String>  parameters = new HashMap<String, String>();
                    parameters.put("rate",rate.toString());
                    parameters.put("city_id",city_id);
                    parameters.put("category_id",category_id);
                    parameters.put("name",name);
                    return  parameters;
                }
            };

            queue.getCache().initialize();
// add it to the RequestQueue
            queue.add(getRequest);
            queue.getCache().clear();
       }


}
