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
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.City;
import com.osamayastal.easycare.Model.Controle.Favorites;
import com.osamayastal.easycare.Model.Controle.users;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Favorite_root {
    public interface FavoriteListener{
        void onSuccess(Favorites favorites);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GetFavorites(final Context mcontext,String page,
                          final FavoriteListener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/mobile/getFavorite?page="+page+"&limit=10";
        final String token =new User_info(mcontext).getToken();


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
                            listener.onSuccess(new Favorites(response));


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

    public void POSTFavorites(final Context mcontext, final String provider_id,
                              final FavoriteListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/addFavorite";
        final String token =new User_info(mcontext).getToken();

        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
            //Build.logError("Setting a new request queue");
        }
        // prepare the Request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        String jsonData = response;
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(jsonData);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                        Log.d("token", token.toString());

                        Log.d("provider_id", provider_id.toString());

                        listener.onSuccess(new Favorites(Jobject));

                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {

                    }
                }
        ) {
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  Headers = new HashMap<String, String>();
                Headers.put("token",token);
                return Headers;
            }

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  Params = new HashMap<String, String>();
                Params.put("provider_id",provider_id);
                return Params;
            }
        };
        queue.add(postRequest);


    }

    public void DELETEFavorites(final Context mcontext, final String id,
                              final FavoriteListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/deleteFavorite/"+id;
        final String token =new User_info(mcontext).getToken();

        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
            //Build.logError("Setting a new request queue");
        }
        // prepare the Request
        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, null,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());
                        listener.onSuccess(new Favorites(response));


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
