package com.osamayastal.easycare.Model.Rootes;

import android.content.Context;
import android.util.Base64;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.Getpayment;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Getway {
    private static final String PASSWORD ="ea5dd2ccc7cce0e8fae6368b7c15593d" ;
    private static final String USERNAME = "merchant.TEST550200051327";

    public interface GetwayListener{
        void onSuccess(Getpayment home);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void GoPayment(final Context mcontext, final Double amount, final String coupontxt_, final GetwayListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/addPayments";
        final String token= new User_info(mcontext).getToken();


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
                    listener.onSuccess(new Getpayment(Jobject));

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

            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();

                parameters.put("amount", String.valueOf(amount));
                if (coupontxt_!=null){
                    parameters.put("couponCode", String.valueOf(coupontxt_));
                }
                return  parameters;
            }


        };
        queue.add(request);
    }
    public void CheckPayment(final Context mcontext,final String orderID, final GetwayListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/checkPayments/"+orderID;
        final String token= new User_info(mcontext).getToken();


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
                    listener.onSuccess(new Getpayment(Jobject));

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
                Map<String, String> headers = new HashMap<>();

                headers.put("token",token);

                String credentials = USERNAME+":"+PASSWORD;
                String auth = "Basic "
                        + Base64.encodeToString(credentials.getBytes(),
                        Base64.NO_WRAP);
                headers.put("Authorization", auth);
                return  headers;
            }




        };
        queue.add(request);
    }

    public void SendUpfront(final Context mcontext,final String orderID, final GetwayListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/updateRemain/"+orderID;
        final String token= new User_info(mcontext).getToken();


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
                    listener.onSuccess(new Getpayment(Jobject));

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
