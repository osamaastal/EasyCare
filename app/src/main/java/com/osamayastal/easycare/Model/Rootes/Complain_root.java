package com.osamayastal.easycare.Model.Rootes;

import android.content.Context;
import android.util.Log;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.osamayastal.easycare.Model.Classes.Complain;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.Model.Controle.City;
import com.osamayastal.easycare.Model.Controle.Complains;
import com.osamayastal.easycare.Model.Controle.users;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Complain_root {
    public interface complain_Listener{
        void onSuccess(Complains complain);
        void onStart();
        void onFailure(String msg);
    }
    private RequestQueue queue;
    public void PostComplain(final Context mcontext, final Complain complain,
                             final complain_Listener listener)
    {

       listener.onStart();
            String url= Server_info.API +"api/mobile/addComplains";


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

                        listener.onSuccess(new Complains(Jobject));

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


                parameters.put("category", complain.getCategory());
                parameters.put("title", complain.getTitle());
                parameters.put("details", complain.getDetails());

                return parameters;
            }
        };
        queue.add(postRequest);

    }


}
