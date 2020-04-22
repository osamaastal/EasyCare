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
import com.osamayastal.easycare.Model.Controle.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Order_root {
    public interface GetbasketListener{
        void onSuccess(Bascket bascket);
        void onStart();
        void onFailure(String msg);
    }
    public interface PostOrderListener{
        void onSuccess(Result bascket);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;


    public void PostOrder(final Context mcontext,
                          final String provider_id,final String locationType,
                          final String lat,final String lng,
                          final String date,final String time,
                          final String upfrontAmount,final String couponCode,
                          final String paymentType,
                          final PostOrderListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/addOrder";
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

                parameters.put("provider_id",provider_id);
                parameters.put("locationType", locationType);
                parameters.put("lat",lat);
                parameters.put("lng", lng);
                parameters.put("date",date);
                parameters.put("time",time);
                parameters.put("upfrontAmount", upfrontAmount);
                parameters.put("couponCode", couponCode);
                parameters.put("paymentType", paymentType);

                Log.d("parameters", parameters.toString());

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




}
