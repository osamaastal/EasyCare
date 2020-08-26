package com.osamayastal.easycare.Model.Classes.Message;



public class Messages {
        private String  last_msg,order_id,order_number;
        private Long edit_time_long;
        private Boolean isRead_driver,isRead_user=false,is_driver_delete=false,is_user_delete=false,can_open=true;
        private User user;
        private User driver;
        public Messages() {

        }

        public Boolean getCan_open() {
                return can_open;
        }

        public void setCan_open(Boolean can_open) {
                this.can_open = can_open;
        }

        public Boolean getIs_driver_delete() {
                return is_driver_delete;
        }

        public void setIs_driver_delete(Boolean is_driver_delete) {
                this.is_driver_delete = is_driver_delete;
        }

        public Boolean getIs_user_delete() {
                return is_user_delete;
        }

        public void setIs_user_delete(Boolean is_user_delete) {
                this.is_user_delete = is_user_delete;
        }

        public String getLast_msg() {
                return last_msg;
        }

        public User getDriver() {
                return driver;
        }

        public void setDriver(User driver) {
                this.driver = driver;
        }

        public String getOrder_id() {
                return order_id;
        }

        public void setOrder_id(String order_id) {
                this.order_id = order_id;
        }

        public void setLast_msg(String last_msg) {
                this.last_msg = last_msg;
        }

        public Long getEdit_time_long() {
                return edit_time_long;
        }

        public void setEdit_time_long(Long edit_time_long) {
                this.edit_time_long = edit_time_long;
        }

        public Boolean getRead_driver() {
                return isRead_driver;
        }

        public void setRead_driver(Boolean read_driver) {
                isRead_driver = read_driver;
        }

        public Boolean getRead_user() {
                return isRead_user;
        }

        public void setRead_user(Boolean read_user) {
                isRead_user = read_user;
        }

        public User getUser() {
                return user;
        }

        public void setUser(User user) {
                this.user = user;
        }

        public String getOrder_number() {
                return order_number;
        }

        public void setOrder_number(String order_number) {
                this.order_number = order_number;
        }
}
