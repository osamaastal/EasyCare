package com.osamayastal.easycare.Model.Classes;

import org.json.JSONException;
import org.json.JSONObject;

public class Pagenation {
    private int size,totalElements,totalPages,pageNumber;

    public Pagenation(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        try {
            size=jsonObject.getInt("size");
            totalElements=jsonObject.getInt("totalElements");
            totalPages=jsonObject.getInt("totalPages");
            pageNumber=jsonObject.getInt("pageNumber");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotalElements() {
        return totalElements;
    }

    public void setTotalElements(int totalElements) {
        this.totalElements = totalElements;
    }

    public int getTotalPages() {
        return totalPages;
    }

    public void setTotalPages(int totalPages) {
        this.totalPages = totalPages;
    }

    public int getPageNumber() {
        return pageNumber;
    }

    public void setPageNumber(int pageNumber) {
        this.pageNumber = pageNumber;
    }
}
