package com.osamayastal.easycare.Model.Classes.Basket;

import com.osamayastal.easycare.Model.Classes.Size;
import com.osamayastal.easycare.Model.Classes.Sub_service;

import java.util.ArrayList;
import java.util.List;

public class Service_for_basket {
    private List<Sub_service> sub_serviceList;
    private Size size;

    public Service_for_basket() {
        sub_serviceList=new ArrayList<>();
        size=null;
    }

    public List<Sub_service> getSub_serviceList() {
        return sub_serviceList;
    }

    public void setSub_serviceList(List<Sub_service> sub_serviceList) {
        this.sub_serviceList = sub_serviceList;
    }

    public Size getSize() {
        return size;
    }

    public void setSize(Size size) {
        this.size = size;
    }
}
