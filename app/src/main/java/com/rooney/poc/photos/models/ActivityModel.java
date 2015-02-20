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
    /*    "editors": [
        {
            "user_id": "8ea239c4-c648-4009-a252-a220e018dc4b",
                "avatar_url": "http://graph.facebook.com/4735/picture?width=100&height=100",
                "username": "aaron",
                "name": "Aaron Rudenstine",
                "first_name": "Aaron",
                "last_name": "Rudenstine",
                "is_followed": false
        }
        ],*/
    /*    "direct_object": {
        "id": "95054cf7-7382-4143-8443-ed10dd4d6dfb",
                "type": 1,
                "name": "Jacqueline Kennedy Onassis Reservoir",
                "business": {
            "is_active": true,
                    "name": "Jacqueline Kennedy Onassis Reservoir",
                    "logo_image": 23112521,
                    "logo_source": "Facebook",
                    "lat": 40.78639049480198,
                    "lon": -73.96180629730225,
                    "phone": "",
                    "website": "http://www.centralparknyc.org",
                    "display_url": "http://www.centralparknyc.org",
                    "tags": "lake,lakes,track,track and field,running track,athletic track,reservoir,scenic views,counterclockwise,runners",
                    "visibility": 99,
                    "visibility_boost": 0,
                    "created_at": "2014-10-21T02:16:28Z",
                    "updated_at": "2015-01-22T06:05:13Z",
                    "hours_json": "null",
                    "attribution": "Foursquare",
                    "staged_status": 401,
                    "price": 0,
                    "phrases": "reservoir,scenic views,counterclockwise,runners",
                    "address": "Central Park",
                    "city": "New York",
                    "state": "NY",
                    "zip": "10024",
                    "country": "US",
                    "facebook_id": "152265038123750",
                    "foursquare_id": "4a229fa8f964a520797d1fe3",
                    "instagram_id": "34992",
                    "analytics_partners": 0,
                    "foursquare_rating": 9.582995574870662,
                    "foursquare_tips_count": 86,
                    "foursquare_derived_rating": 5,
                    "cover_image_url": "http://coreapi.citymaps.com/v2/image/activity/c05432ad-b0cf-4935-8496-4eba071ad48a-1422820838155",
                    "bid": "95054cf7-7382-4143-8443-ed10dd4d6dfb",
                    "category_id": 2038,
                    "category_icon": 0,
                    "category_name": "Lake",
                    "category_short_name": "Lake",
                    "categories": [
            {
                "cat_id": 2038,
                    "category_icon": 0,
                    "name": "Lake",
                    "short_name": "Lake",
                    "is_visible": true,
                    "is_searchable": true,
                    "is_autocomplete": true,
                    "is_explorable": true
            },
            {
                "cat_id": 2683,
                    "category_icon": 2800,
                    "name": "Track",
                    "short_name": "Track",
                    "is_visible": true,
                    "is_searchable": true,
                    "is_autocomplete": true,
                    "is_explorable": true
            }
            ]
        }
    }*/

}