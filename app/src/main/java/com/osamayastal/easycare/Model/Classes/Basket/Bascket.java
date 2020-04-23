package com.osamayastal.easycare.Model.Classes.Basket;

import com.osamayastal.easycare.Model.Classes.Categorie;
import com.osamayastal.easycare.Model.Classes.Product;
import com.osamayastal.easycare.Model.Classes.Provider.Provider;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Bascket {

    private List<Product> products;
    private List<categories_basket> categories;
private Provider provider;
private Categorie categorie;
    public Bascket(JSONObject jsonObject) {
        if (jsonObject==null){
            return;
        }

        categories=new ArrayList<>();
        products=new ArrayList<>();
        try {
            provider=new Provider(jsonObject);
            categorie=new Categorie(jsonObject.getJSONObject("type_id"));

            JSONArray jsonArray=jsonObject.getJSONArray("categories");
            for(int i=0;i<jsonArray.length();i++){
                categories.add(new categories_basket(jsonArray.getJSONObject(i)));
            }

            JSONArray jsonArray1=jsonObject.getJSONArray("products");
            for(int i=0;i<jsonArray1.length();i++){
                products.add(new Product(jsonArray1.getJSONObject(i)));
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public Categorie getCategorie() {
        return categorie;
    }

    public void setCategorie(Categorie categorie) {
        this.categorie = categorie;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }

    public List<categories_basket> getCategories() {
        return categories;
    }

    public void setCategories(List<categories_basket> categories) {
        this.categories = categories;
    }

    public Provider getProvider() {
        return provider;
    }

    public void setProvider(Provider provider) {
        this.provider = provider;
    }
}
