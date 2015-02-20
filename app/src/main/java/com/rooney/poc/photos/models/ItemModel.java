package com.rooney.poc.photos.models;

import android.graphics.Bitmap;

public class ItemModel {
    public int index;
    public String _id;
    public String title;
    public String description;
    public String price;
    public String salePrice;
    public String image;
    public Bitmap imageBitmap;
    public String guid;






    public ItemModel(int index, String id, String title, String description, String originalPrice, String salePrice, String image) {
        this.index = index;
        this._id = id;
        this.title = title;
        this.description = description;
        this.price = originalPrice;
        this.salePrice = salePrice;
        this.image = image;
    }

    @Override
    public String toString() {
        return title;
    }
}