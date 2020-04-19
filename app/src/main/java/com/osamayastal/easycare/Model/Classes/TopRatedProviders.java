package com.osamayastal.easycare.Model.Classes;

import com.osamayastal.easycare.Model.Classes.Provider.Provider;

import org.json.JSONException;
import org.json.JSONObject;

public class TopRatedProviders {
    private String _id;
    private int count;
    private Provider provider;

    public TopRatedProviders(JSONObject jsonObject) {
        if (jsonObject!=null){
            return;
        }
        try {
            _id=jsonObject.getString("_id");
            count=jsonObject.getInt("count");
            provider=new Provider(jsonObject.getJSONObject("providers"));

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

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
