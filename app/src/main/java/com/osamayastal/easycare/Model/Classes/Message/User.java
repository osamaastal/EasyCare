package com.osamayastal.easycare.Model.Classes.Message;

import java.io.Serializable;

public class User implements Serializable {
        private String  _id,name,img;

        public User() {

        }

    public User(String _id, String name, String img) {
        this._id = _id;
        this.name = name;
        this.img = img;
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

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
