package com.osamayastal.easycare.Model.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class TopRequestedProviders {
    private String _id;
    private int count;
    private List<Provider> provider;

    public TopRequestedProviders(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        provider=new ArrayList<>();
        try {
            _id=jsonObject.getString("_id");
            count=jsonObject.getInt("count");
            JSONArray jsonArray=jsonObject.getJSONArray("Providers");
            for (int i=0;i<jsonArray.length();i++){
                provider.add(new Provider(jsonArray.getJSONObject(i)));
            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<Provider> getProvider() {
        return provider;
    }

    public void setProvider(List<Provider> provider) {
        this.provider = provider;
    }
}
