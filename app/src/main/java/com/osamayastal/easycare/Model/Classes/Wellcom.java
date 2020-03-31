package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Wellcom {
    private String _id,titleAr,title,description,descriptionAr,icon;
    public Wellcom(JSONObject provider) {
        if (provider==null){
            return;
        }
        try {
            _id=provider.getString("_id");
            titleAr=provider.getString("titleAr");
            title=provider.getString("title");
            description=provider.getString("description");
            descriptionAr=provider.getString("descriptionAr");
            icon=provider.getString("icon");
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTitleAr() {
        return titleAr;
    }

    public void setTitleAr(String titleAr) {
        this.titleAr = titleAr;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDescriptionAr() {
        return descriptionAr;
    }

    public void setDescriptionAr(String descriptionAr) {
        this.descriptionAr = descriptionAr;
    }

    public String getIcon() {
        return icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }
}

