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
import com.osamayastal.easycare.Model.Controle.Order_Details;
import com.osamayastal.easycare.Model.Controle.Orders;
import com.osamayastal.easycare.Model.Controle.Result;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Order_root {
    public interface GetOrderListener{
        void onSuccess(Orders orders);
        void onStart();
        void onFailure(String msg);
    }
    public interface PostOrderListener{
        void onSuccess(Result bascket);
        void onStart();
        void onFailure(String msg);
    }

    public interface GetOrderDetailsListener{
        void onSuccess(Order_Details order_details);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;

    public Order_root() {
    }

    public Order_root(final Context mcontext) {
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
            //Build.logError("Setting a new request queue");
        }
    }

    public void PostOrderRate(final Context mcontext,
                          final String id,
                          final String not, final String rat,

                          final PostOrderListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/addRateFromUserToProvider/"+id;
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

                parameters.put("rate_from_user_to_provider",rat);
                parameters.put("note_from_user_to_provider", not);


                Log.d("parameters", parameters.toString());

                return  parameters;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();

                parameters.put("token",token);
                Log.d("token", parameters.toString());

                return  parameters;
            }
        };
        queue.add(request);

    }
    public void PostOrder(final Context mcontext,
                          final String provider_id, final int locationType,
                          final String lat, final String lng,
                          final String date, final String time,
                          final String upfrontAmount, final String couponCode,
                          final String paymentType,
                          final Boolean isUpfront,
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
                parameters.put("locationType", String.valueOf(locationType));
               if (locationType==1){
                   parameters.put("lat",lat);
                   parameters.put("lng", lng);
               }
                parameters.put("date",date);
                parameters.put("time",time);

                parameters.put("couponCode", couponCode);
                parameters.put("PaymentType", paymentType);

                if (isUpfront){
                    parameters.put("upfrontAmount", upfrontAmount);
                    parameters.put("isUpfront", String.valueOf(isUpfront));
                }else {
                    parameters.put("upfrontAmount", "0");
                    parameters.put("isUpfront", String.valueOf(isUpfront));
                }

                Log.d("parameters", parameters.toString());

                return  parameters;
            }

            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();

                parameters.put("token",token);
                Log.d("token", parameters.toString());

                return  parameters;
            }
        };
        queue.add(request);

    }

    public void GetAllOrder(final Context mcontext,
                          final int page,final int statusID,
                          final GetOrderListener listener)
    {
        if (queue != null){
            queue.stop();
        }

        listener.onStart();
        String url= Server_info.API +"api/mobile/getUserOrder?StatusId="+statusID+"&page="+page+"&limit=10";
        final String token=new User_info(mcontext).getToken();
        Log.d("token", token);
        Log.d("statusID", statusID+"");
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        // prepare the Request
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(response);
                    Log.d("Response", response.toString());
                    listener.onSuccess(new Orders(Jobject));

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
        queue.start();
        queue.add(request);


    }

    public void GetOrderDetails(final Context mcontext,
                          final String id,
                          final GetOrderDetailsListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/getOrderDetails/"+id;
        final String token=new User_info(mcontext).getToken();
        Log.d("token", token);
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);
        }
        // prepare the Request
        StringRequest request = new StringRequest(Request.Method.GET, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                JSONObject Jobject = null;
                try {
                    Jobject = new JSONObject(response);
                    Log.d("Response", response.toString());
                    listener.onSuccess(new Order_Details(Jobject));

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

    public void CancelOrder(final Context mcontext,
                                final String id,
                                final PostOrderListener listener)
    {

        listener.onStart();
        String url= Server_info.API +"api/mobile/updateOrder/"+id;
        final String token=new User_info(mcontext).getToken();
        Log.d("token", token);
        if (queue == null) {
            queue = Volley.newRequestQueue(mcontext);
        }
        // prepare the Request
        StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

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
            public Map<String, String> getHeaders() throws AuthFailureError {
                Map<String, String> parameters = new HashMap<>();

                parameters.put("token",token);
                return  parameters;
            }
        };
        queue.add(request);

    }

}
