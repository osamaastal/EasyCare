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
import com.osamayastal.easycare.Model.Controle.Bascket;
import com.osamayastal.easycare.Model.Controle.Favorites_get;
import com.osamayastal.easycare.Model.Controle.Result;
import com.osamayastal.easycare.Model.Controle.Search;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Bascket_root {
    public interface GetbasketListener{
        void onSuccess(Bascket bascket);
        void onStart();
        void onFailure(String msg);
    }
    public interface PostbasketListener{
        void onSuccess(Result bascket);
        void onStart();
        void onFailure(String msg);
    }
    public interface Basket_count_Listener{
        void onSuccess(int nb);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GetItemCount(final Context mcontext, final Basket_count_Listener listener)
    {
        String url= Server_info.API +"api/mobile/getCartCount";
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
                    Log.d("Response", response.toString());
                    new User_info().Basket(Jobject.getInt("items"),mcontext);
                    listener.onSuccess(Jobject.getInt("items"));
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
    public void GetBasket(final Context mcontext,
                          final int page,
                          final GetbasketListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/getCart?page="+page+"&limit=10";
        final String token=new User_info(mcontext).getToken();
        Log.d("token", token);
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
            //Build.logError("Setting a new request queue");
        }
        // prepare the Request
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(response);
                    Log.d("Response", response.toString());
                    listener.onSuccess(new Bascket(Jobject));

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

    public void PostProduct(final Context mcontext,
                          final String productID,final int qty,
                          final PostbasketListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/addProductCart";
        final String token=new User_info(mcontext).getToken();

        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
            //Build.logError("Setting a new request queue");
        }
        // prepare the Request
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response.toString());
                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(response);
                    Log.d("Response", response.toString());
                    GetItemCount(mcontext, new Basket_count_Listener() {
                        @Override
                        public void onSuccess(int nb) {

                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
                    listener.onSuccess(new Result(Jobject));

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
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();

                parameters.put("product_id",productID);
                parameters.put("qty", String.valueOf(qty));
                return  parameters;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();

                parameters.put("token",token);
                return  parameters;
            }
        };
        queue.add(request);

    }

    public void PutProduct(final Context mcontext,
                            final JSONObject product,
                            final PostbasketListener listener)
    {

        listener.onStart();
        final String url= Server_info.API +"api/mobile/UpdateCart";
        final String token=new User_info(mcontext).getToken();

        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
            //Build.logError("Setting a new request queue");
        }
        // prepare the Request
        // prepare the Request
        Log.d("product", product.toString());

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, product,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());


                        listener.onSuccess(new Result(response));
                        GetItemCount(mcontext, new Basket_count_Listener() {
                            @Override
                            public void onSuccess(int nb) {

                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFailure(String msg) {

                            }
                        });

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
    public void PostService(final Context mcontext,
                            final JSONObject service,
                            final PostbasketListener listener)
    {

        listener.onStart();
        final String url= Server_info.API +"api/mobile/addServiceCart";
        final String token=new User_info(mcontext).getToken();

        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
            //Build.logError("Setting a new request queue");
        }
        // prepare the Request
        // prepare the Request
        Log.d("service", service.toString());

        JsonObjectRequest getRequest = new JsonObjectRequest(Request.Method.POST, url, service,
                new Response.Listener<JSONObject>()
                {
                    @Override
                    public void onResponse(JSONObject response) {

                        Log.d("Response", response.toString());

                        listener.onSuccess(new Result(response));
                        GetItemCount(mcontext, new Basket_count_Listener() {
                            @Override
                            public void onSuccess(int nb) {

                            }

                            @Override
                            public void onStart() {

                            }

                            @Override
                            public void onFailure(String msg) {

                            }
                        });

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
    public void Delete(final Context mcontext,
                            final String ID,
                            final PostbasketListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/deleteItemCart/"+ID;
        final String token=new User_info(mcontext).getToken();

        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
            //Build.logError("Setting a new request queue");
        }
        // prepare the Request
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {
                Log.d("Response", response.toString());
                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(response);
                    Log.d("Response", response.toString());

                    listener.onSuccess(new Result(Jobject));
                    GetItemCount(mcontext, new Basket_count_Listener() {
                        @Override
                        public void onSuccess(int nb) {

                        }

                        @Override
                        public void onStart() {

                        }

                        @Override
                        public void onFailure(String msg) {

                        }
                    });
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
