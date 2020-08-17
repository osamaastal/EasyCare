package com.osamayastal.easycare.Model.Rootes;

import android.app.ProgressDialog;
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
import com.osamayastal.easycare.Model.Controle.Coupon;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Popups.AppPop;
import com.osamayastal.easycare.R;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Coupon_root {
    public interface Listener{
        void onSuccess(Coupon coupon);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void Check_coupon(final Context mcontext, final String coupon,
                             final String id, final Boolean isReOrder, final String orderId, final int payment, final Listener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/mobile/checkCoupon";
        final ProgressDialog dialog=new AppPop().Loading_POP(mcontext,mcontext.getString(R.string.check_coupon));
        final String token=new User_info(mcontext).getToken();
            if (queue == null) {
                queue = Volley.newRequestQueue(mcontext);  // this = context

            }
            // prepare the Request
        StringRequest postRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>()
                {
                    @Override
                    public void onResponse(String response) {
                        dialog.dismiss();
                        // response
                        Log.d("Response", response);
                        String jsonData = response;
                        JSONObject Jobject = null;
                        try {
                            Jobject = new JSONObject(jsonData);
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                        listener.onSuccess(new Coupon(Jobject));

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
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();

                parameters.put("coupon", coupon);
                parameters.put("provider_id", id);
                parameters.put("isReOrder", String.valueOf(isReOrder));
                parameters.put("PaymentType", String.valueOf(payment));
                if(isReOrder){
                    parameters.put("orderId", orderId);

                }
                Log.d("parameters", parameters.toString());

                return parameters;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String>  parameters = new HashMap<String, String>();

                parameters.put("token", token);

                return parameters;
            }
        };
        queue.add(postRequest);
       }


}
