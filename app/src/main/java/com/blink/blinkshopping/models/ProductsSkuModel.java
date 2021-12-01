package com.blink.blinkshopping.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductsSkuModel {

    @SerializedName("items")
    @Expose
    public List<Item> items = null;

    public class Item {

        @SerializedName("name")
        @Expose
        public String name;
        @SerializedName("sku")
        @Expose
        public String sku;
        @SerializedName("image")
        @Expose
        public Image image;
        @SerializedName("small_image")
        @Expose
        public SmallImage smallImage;

        public class Image {

            @SerializedName("url")
            @Expose
            public String url;
            @SerializedName("label")
            @Expose
            public String label;

        }

        public class SmallImage {

            @SerializedName("url")
            @Expose
            public String url;
            @SerializedName("label")
            @Expose
            public String label;

        }
    }

}

