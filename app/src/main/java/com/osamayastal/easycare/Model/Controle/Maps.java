package com.osamayastal.easycare.Model.Controle;

import android.util.Log;

import com.osamayastal.easycare.Model.Classes.Employee;
import com.osamayastal.easycare.Model.Classes.Pagenation;
import com.osamayastal.easycare.Model.Classes.Provider;
import com.osamayastal.easycare.Model.Classes.Provider_map;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Maps {
    private String message;
    private int status_code;
    private List<Employee> employees;
    private List<Provider_map> providers;

    public Maps(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        employees=new ArrayList<>();
        providers=new ArrayList<>();
        try {
            message=jsonObject.getString("message");
            Log.d("message",message);
            status_code=jsonObject.getInt("status_code");
            JSONArray jsonArray2=jsonObject.getJSONArray("provider");
            JSONArray jsonArray=jsonObject.getJSONArray("employee");
            for(int i=0;i<jsonArray.length();i++){
                employees.add(new Employee(jsonArray.getJSONObject(i)));
            }

            for(int i=0;i<jsonArray2.length();i++){
                providers.add(new Provider_map(jsonArray2.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public int getStatus_code() {
        return status_code;
    }

    public void setStatus_code(int status_code) {
        this.status_code = status_code;
    }

    public List<Employee> getEmployees() {
        return employees;
    }

    public void setEmployees(List<Employee> employees) {
        this.employees = employees;
    }

    public List<Provider_map> getProviders() {
        return providers;
    }

    public void setProviders(List<Provider_map> providers) {
        this.providers = providers;
    }
}
