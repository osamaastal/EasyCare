package com.osamayastal.easycare.Model.Classes.Provider;

import org.json.JSONException;
import org.json.JSONObject;

public class ProviderSetting {
    private String _id,name,min,max,value,provider_id;


    public ProviderSetting(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        try {
            _id=jsonObject.getString("_id");
            name=jsonObject.getString("name");
            min=jsonObject.getString("min");
            max=jsonObject.getString("max");
            value=jsonObject.getString("value");
            provider_id=jsonObject.getString("provider_id");



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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMin() {
        return min;
    }

    public void setMin(String min) {
        this.min = min;
    }

    public String getMax() {
        return max;
    }

    public void setMax(String max) {
        this.max = max;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getProvider_id() {
        return provider_id;
    }

    public void setProvider_id(String provider_id) {
        this.provider_id = provider_id;
    }
}
