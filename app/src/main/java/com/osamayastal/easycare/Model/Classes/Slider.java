package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Slider {
    private String _id,ads_for,expiry_date,image,url,store_id,product_id,by;
    private boolean is_ads_redirect_to_store,is_ads_have_expiry_date,isApprove;
    public Slider(JSONObject provider) {
        if (provider!=null){
            return;
        }
        try {
            _id=provider.getString("_id");
            ads_for=provider.getString("ads_for");
            expiry_date=provider.getString("expiry_date");
            image=provider.getString("image");
            url=provider.getString("url");
            store_id=provider.getString("store_id");
            product_id=provider.getString("product_id");
            by=provider.getString("by");

            is_ads_redirect_to_store=provider.getBoolean("is_ads_redirect_to_store");
            is_ads_have_expiry_date=provider.getBoolean("is_ads_have_expiry_date");
            isApprove=provider.getBoolean("isApprove");
          
        } catch (JSONException e) {
            e.printStackTrace();
        }



    }

}
