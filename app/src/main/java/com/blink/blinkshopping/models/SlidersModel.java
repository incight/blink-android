package com.blink.blinkshopping.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class SlidersModel {
    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("sliderById")
        @Expose
        public List<SliderById> sliderById = null;


        public class SliderById {

            @SerializedName("slider_id")
            @Expose
            public Integer sliderId;
            @SerializedName("name")
            @Expose
            public String name;
            @SerializedName("status")
            @Expose
            public Integer status;
            @SerializedName("location")
            @Expose
            public String location;
            @SerializedName("store_ids")
            @Expose
            public String storeIds;
            @SerializedName("categorylist")
            @Expose
            public String categorylist;
            @SerializedName("customer_group_ids")
            @Expose
            public String customerGroupIds;
            @SerializedName("priority")
            @Expose
            public Integer priority;
            @SerializedName("effect")
            @Expose
            public String effect;
            @SerializedName("autoWidth")
            @Expose
            public String autoWidth;
            @SerializedName("autoHeight")
            @Expose
            public String autoHeight;
            @SerializedName("design")
            @Expose
            public Integer design;
            @SerializedName("loop")
            @Expose
            public String loop;
            @SerializedName("lazyLoad")
            @Expose
            public String lazyLoad;
            @SerializedName("autoplay")
            @Expose
            public String autoplay;
            @SerializedName("autoplayTimeout")
            @Expose
            public String autoplayTimeout;
            @SerializedName("nav")
            @Expose
            public String nav;
            @SerializedName("dots")
            @Expose
            public String dots;
            @SerializedName("is_responsive")
            @Expose
            public String isResponsive;
            @SerializedName("responsive_items")
            @Expose
            public String responsiveItems;
            @SerializedName("from_date")
            @Expose
            public String fromDate;
            @SerializedName("to_date")
            @Expose
            public String toDate;
            @SerializedName("items")
            @Expose
            public List<Item> items = null;

            public class Item {

                @SerializedName("banner_id")
                @Expose
                public Integer bannerId;
                @SerializedName("name")
                @Expose
                public String name;
                @SerializedName("status")
                @Expose
                public Integer status;
                @SerializedName("type")
                @Expose
                public Integer type;
                @SerializedName("content")
                @Expose
                public String content;
                @SerializedName("image")
                @Expose
                public String image;
                @SerializedName("url_banner")
                @Expose
                public String urlBanner;
                @SerializedName("title")
                @Expose
                public String title;
                @SerializedName("newtab")
                @Expose
                public Integer newtab;
                @SerializedName("position")
                @Expose
                public Integer position;

            }

        }
    }


}

