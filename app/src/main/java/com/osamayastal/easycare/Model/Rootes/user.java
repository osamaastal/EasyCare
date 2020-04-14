package com.osamayastal.easycare.Model.Rootes;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.osamayastal.easycare.Model.Classes.User;
import com.osamayastal.easycare.Model.Const.User_info;
import com.osamayastal.easycare.Model.Controle.users;
import com.osamayastal.easycare.Model.Const.Server_info;
import com.osamayastal.easycare.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class user {
    public interface user_Listener{
        void onSuccess(users new_account);
        void onStart();
        void onFailure(String msg);
    }


    RequestQueue queue=null;
    public void Get_Token(final Context mcontext){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = Server_info. API+"api/mobile/guestToken";

        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
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
                            new User_info().Token(Jobject.getString("items"),mcontext);
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

    public void Post_create_user(final Context mcontext, final User user_, final user_Listener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);
        }
        String url = Server_info.API+"api/mobile/users";;
        lisenner.onStart();
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


                        lisenner.onSuccess(new users(Jobject));

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

                parameters.put("password", user_.getPassword());
                parameters.put("email", user_.getEmail());
                parameters.put("os", "android");
                parameters.put("full_name", user_.getFullName());
                parameters.put("phone_number",user_.getPhoneNumber());
                parameters.put("city_id", user_.getCity_id());
                parameters.put("lat",String.valueOf(user_.getLat()));
                parameters.put("lng", String.valueOf(user_.getLng()));
                return parameters;
            }
        };
        queue.add(postRequest);



    }
    public void Post_Login(final Context mcontext, final String fcmtoken, final String password, final String phone_number, final user_Listener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = Server_info. API+"api/mobile/login";
        lisenner.onStart();
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


                        lisenner.onSuccess(new users(Jobject));

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
//
                parameters.put("password", password);
                parameters.put("phone_number", phone_number);
                parameters.put("fcmToken", fcmtoken);
                parameters.put("os", "Android");

                return parameters;
            }
        };
        queue.add(postRequest);

        // prepare the Request

    }
    public void Post_forget_password(final Context mcontext, final String email, final user_Listener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = Server_info.API+"api/mobile/forgetPassword";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new users(Jobject));
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

            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();


                parameters.put("email", email);

                return parameters;
            }
        };
        queue.add(postRequest);

        // prepare the Request

    }
    public void Post_Logout(final Context mcontext,  final user_Listener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        final String token=new User_info(mcontext).getToken();

        Log.d("token",token);


        String url = Server_info.API+"api/mobile/logout";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new users(Jobject));
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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  parameters = new HashMap<String, String>();
                parameters.put("token",token);
                return  parameters;
            }

        };
         queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(postRequest);
        queue.getCache().clear();

        // prepare the Request

    }
    public void Post_change_password(final Context mcontext, final String newpassword, final user_Listener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        final User_info user_info=new User_info(mcontext);
        final String token=user_info.getToken();
        String url = Server_info.API+"api/mobile/changePassword";
        lisenner.onStart();
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

                        lisenner.onSuccess(new users(Jobject));

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

                Map<String, String>  parameters = new HashMap<String, String>();
                parameters.put("token",token);
                return  parameters;
            }
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//

                parameters.put("password", newpassword);
                parameters.put("id", user_info.getId());

                return parameters;
            }
        };
         queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(postRequest);
        queue.getCache().clear();

        // prepare the Request

    }
    public void GET_profil(final Context mcontext,  final user_Listener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = Server_info. API+"api/mobile/showprofile";
        final String token=new User_info(mcontext).getToken();
        lisenner.onStart();
        StringRequest postRequest = new StringRequest(Request.Method.GET, url,
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
                            lisenner.onSuccess(new users(Jobject));
                        } catch (JSONException e1) {
                            e1.printStackTrace();
                        }


                        lisenner.onSuccess(new users(Jobject));

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
                Map<String, String>  parameters = new HashMap<String, String>();

                parameters.put("token", token);

                return parameters;
            }
        };
        queue.add(postRequest);

        // prepare the Request

    }

    public void Post_update_user(final Context mcontext, final User user, final user_Listener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        final String token=new User_info(mcontext).getToken();
        Log.d("Token", token);

        String url = Server_info.API+"api/mobile/updateprofileAndroid";
        lisenner.onStart();
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

                        lisenner.onSuccess(new users(Jobject));

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
//                Headers.put("Content-Type","multipart/form-data; boundary=<calculated when request is sent>");
//                Headers.put("Content-Length","<calculated when request is sent>");
//                Headers.put("Host","<calculated when request is sent>");
//                Headers.put("User-Agent","PostmanRuntime/7.24.0");
//                Headers.put("Accept","*/*");
//                Headers.put("Accept-Encoding","gzip, deflate, br");
//                Headers.put("Connection","keep-alive");
                Headers.put("token",token);

                return  Headers;
            }
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
                parameters.put("full_name",user.getFullName());
                parameters.put("address",user.getAddress());
                parameters.put("email",user.getEmail());
                parameters.put("city_id",user.getCity_id());
                Log.d("parametters",parameters.toString());
                return parameters;
            }
        };

        queue.add(postRequest);


    }

    public void Post_update_fcmToken(final Context mcontext, final String token, final String fcmtoken, final user_Listener lisenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }
        String url = Server_info.API+"api/changePassword";//"http://httpbin.org/post";
        lisenner.onStart();
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
                        try {

                            String mssg=Jobject.getString("message");
                            Log.d("Response_mssg",mssg );
                            lisenner.onSuccess(new users(Jobject));

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
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {

                Map<String, String>  parameters = new HashMap<String, String>();
                parameters.put("token",token);
                return  parameters;
            }
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//

                parameters.put("fcmToken", fcmtoken);

                return parameters;
            }
        };
         queue.getCache().initialize();
// add it to the RequestQueue
        queue.add(postRequest);
        queue.getCache().clear();

        // prepare the Request

    }
    public void Put_ActvitPhone_user(final Context mcontext, final String id,
                                     final String cod, final String fcmtoken, final user_Listener listenner){
        if (queue==null) {
            queue = Volley.newRequestQueue(mcontext);  // this = context
        }

        String url = Server_info.API+"api/mobile/verfiy";

        listenner.onStart();
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

                            listenner.onSuccess(new users(new JSONObject(jsonData)));
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
            @Override
            protected Map<String, String> getParams()
            {
                Map<String, String>  parameters = new HashMap<String, String>();
//
                parameters.put("id",id);
                parameters.put("verify_code", cod);
                parameters.put("fcmToken", fcmtoken);

                return parameters;
            }
        };
        queue.add(postRequest);

        // prepare the Request

    }


}
