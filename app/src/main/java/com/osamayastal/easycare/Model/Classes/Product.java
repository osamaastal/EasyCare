package com.osamayastal.easycare.Model.Classes;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private String _id,name,description,image,by_user_id,createat,cart_id;
    private  Double price,discountPrice,Total;
    private int upFront,qty;
    private boolean isUpFront,status;
    private List<String> images;

    public Product(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }
        images=new ArrayList<>();
        try {
            _id=jsonObject.getString("_id");
            name=jsonObject.getString("name");
            description=jsonObject.getString("description");
            image=jsonObject.getString("image");
            by_user_id=jsonObject.getString("by_user_id");
            by_user_id=jsonObject.getString("by_user_id");
            createat=jsonObject.getString("createat");

            isUpFront=jsonObject.getBoolean("isUpFront");
            status=jsonObject.getBoolean("status");
            upFront=jsonObject.getInt("upFront");
            price=jsonObject.getDouble("price");
            discountPrice=jsonObject.getDouble("discountPrice");

            JSONArray jsonArray=jsonObject.getJSONArray("images");
            for (int i=0;i<jsonArray.length();i++){
                jsonArray.getString(i).replace("http", "https");

                images.add(jsonArray.getString(i));
            }
            image.replace("http", "https");

        } catch (JSONException e) {
            e.printStackTrace();
        }

        try {
            cart_id=jsonObject.getString("cart_id");
            qty=jsonObject.getInt("qty");
            Total=jsonObject.getDouble("Total");
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public JSONObject Order_JSON(List<Product> products)  {
        JSONObject jsonObject=new JSONObject();

        if(products != null && products.size() > 0){
            JSONArray itemsJsonArray = new JSONArray();
            for(Product itemsElement : products){
                try {
                    itemsJsonArray.put(itemsElement.toJsonObject());
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
            try {
                jsonObject.put("Cart", itemsJsonArray);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


        return jsonObject;
    }

    private JSONObject toJsonObject() throws JSONException {
        JSONObject jsonObject=new JSONObject();
        jsonObject.put("cart_id",cart_id);
        jsonObject.put("qty",qty);

        return jsonObject;
    }

    public String getCart_id() {
        return cart_id;
    }

    public void setCart_id(String cart_id) {
        this.cart_id = cart_id;
    }

    public Double getTotal() {
        return Total;
    }

    public void setTotal(Double total) {
        Total = total;
    }

    public int getQty() {
        return qty;
    }

    public void setQty(int qty) {
        this.qty = qty;
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getBy_user_id() {
        return by_user_id;
    }

    public void setBy_user_id(String by_user_id) {
        this.by_user_id = by_user_id;
    }

    public String getCreateat() {
        return createat;
    }

    public void setCreateat(String createat) {
        this.createat = createat;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Double getDiscountPrice() {
        return discountPrice;
    }

    public void setDiscountPrice(Double discountPrice) {
        this.discountPrice = discountPrice;
    }

    public int getUpFront() {
        return upFront;
    }

    public void setUpFront(int upFront) {
        this.upFront = upFront;
    }

    public boolean isUpFront() {
        return isUpFront;
    }

    public void setUpFront(boolean upFront) {
        isUpFront = upFront;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }

    public List<String> getImages() {
        return images;
    }

    public void setImages(List<String> images) {
        this.images = images;
    }
}
