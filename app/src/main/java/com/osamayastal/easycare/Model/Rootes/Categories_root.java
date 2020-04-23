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
import com.osamayastal.easycare.Model.Classes.Sub_categorie;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Categories;
import com.osamayastal.easycare.Model.Controle.Products;
import com.osamayastal.easycare.Model.Controle.Search;
import com.osamayastal.easycare.Model.Controle.Sub_categories;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Categories_root {
    public interface cat_Listener{
        void onSuccess(Categories categories);
        void onStart();
        void onFailure(String msg);
    }
    public interface product_Listener{
        void onSuccess(Products products);
        void onStart();
        void onFailure(String msg);
    }
    public interface cat_Listener2{
        void onSuccess(Sub_categories sub_categorie);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GetCategories(final Context mcontext,
                          final cat_Listener listener)
    {

            String url= Server_info.API +"api/admin/type";
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
                            // display response
                            Log.d("Response", response.toString());
                            listener.onSuccess(new Categories(response));


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
    public void GetProducts(final Context mcontext,String prvider_id,int page,
                              final product_Listener listener)
    {

        String url= Server_info.API +"api/mobile/providerProducts/"+prvider_id+"?page="+page+"&limit=10";
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
                        // display response
                        Log.d("Response", response.toString());
                        listener.onSuccess(new Products(response));


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
    public void Get_SubCategories(final Context mcontext, final String categoryId, final String providerId, final cat_Listener2 listener ){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        final String url = Server_info. API+"api/mobile/getSubCategoryByCategoryId?categoryId=" +
                categoryId+"&providerId="+providerId;

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        // response
                        Log.d("Response", response);
                        Log.d("url", url);
                        String jsonData = response;
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(jsonData);

                          listener.onSuccess(new Sub_categories(Jobject));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }



                    }
                },
                new Response.ErrorListener()
                {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                    }
                }
        ) {

        };
        queue.add(postRequest);

        // prepare the Request

    }

}
