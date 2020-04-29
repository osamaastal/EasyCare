package com.osamayastal.easycare.Model.Classes.Message;



public class Messages {
        private String  last_msg,order_id;
        private Long edit_time_long;
        private Boolean isRead_driver,isRead_user=false;
        private User user;
        private User driver;
        public Messages() {

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
}
