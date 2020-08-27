package com.osamayastal.easycare.Model.Classes.Message;

public class Message {
        private String  content,uid,_id;
        private Boolean is_driver=true;
        private Long time_long;
        public Message() {

        }

    public Message(String content, Long long_time) {
        this.content = content;
        this.is_driver = false;
        this.time_long = long_time;
    }


    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Boolean getIs_driver() {
        return is_driver;
    }

    public void setIs_driver(Boolean is_driver) {
        this.is_driver = is_driver;
    }

    public Long getTime_long() {
        return time_long;
    }

    public void setTime_long(Long time_long) {
        this.time_long = time_long;
    }
}
