package com.rooney.poc.photos.models;

import android.graphics.Bitmap;

public class ActivityModel {
    public String activity_id;
    public int activity_type;
    public String created_at;
    public String updated_at;
    public boolean is_active;
    public User user;
    public int image_count;
    public int likes_count;
    public int comments_count;
    public boolean is_liked;
    public int privacy_level;
    public String[] image_url;
    public Bitmap imageBitmap;
    public DirectObject direct_object;


}