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
import com.osamayastal.easycare.Model.Controle.City;
import com.osamayastal.easycare.Model.Controle.Provider_Details;
import com.osamayastal.easycare.Model.Controle.Search;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class ProviderDetails_root {
    public interface DetailsListener{
        void onSuccess(Provider_Details details);
        void onStart();
        void onFailure(String msg);
    }
    public interface AppProv_Listener{
        void onSuccess(Search prov);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GetDetailsListener(final Context mcontext,String id,
                          final DetailsListener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/admin/getproviderDetails/"+id;


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

    public void GetALL_pro_By_cat(final Context mcontext,String id,int page,
                                   final AppProv_Listener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/getProviderByCategoryId/"+id+"?page="+page+"&limit=10";
        final String token =new User_info(mcontext).getToken();


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
                        Log.d("token ", token.toString());
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
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  Headers = new HashMap<String, String>();
                Headers.put("token",token);
                return Headers;
            }
        };

        queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(getRequest);
        queue.getCache().clear();
    }
}
