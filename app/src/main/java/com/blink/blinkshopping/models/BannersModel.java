package com.blink.blinkshopping.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class BannersModel {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("bannerId")
        @Expose
        public List<BannerId> bannerId = null;

        public class BannerId {

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
            public Object content;
            @SerializedName("image")
            @Expose
            public String image;
            @SerializedName("url_banner")
            @Expose
            public Object urlBanner;
            @SerializedName("title")
            @Expose
            public String title;
            @SerializedName("newtab")
            @Expose
            public Integer newtab;
            @SerializedName("bannersliders")
            @Expose
            public List<Bannerslider> bannersliders = null;

            public class Bannerslider {

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
                public Object autoWidth;
                @SerializedName("autoHeight")
                @Expose
                public Object autoHeight;
                @SerializedName("design")
                @Expose
                public Integer design;
                @SerializedName("loop")
                @Expose
                public Object loop;
                @SerializedName("lazyLoad")
                @Expose
                public Object lazyLoad;
                @SerializedName("autoplay")
                @Expose
                public Object autoplay;
                @SerializedName("autoplayTimeout")
                @Expose
                public String autoplayTimeout;
                @SerializedName("nav")
                @Expose
                public Object nav;
                @SerializedName("dots")
                @Expose
                public Object dots;
                @SerializedName("is_responsive")
                @Expose
                public Object isResponsive;
                @SerializedName("responsive_items")
                @Expose
                public String responsiveItems;
                @SerializedName("from_date")
                @Expose
                public String fromDate;
                @SerializedName("to_date")
                @Expose
                public String toDate;
                @SerializedName("position")
                @Expose
                public Integer position;

            }

        }

    }
}