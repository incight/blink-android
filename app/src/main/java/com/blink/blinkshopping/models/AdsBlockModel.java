package com.blink.blinkshopping.models;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class AdsBlockModel {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("adsBlocks")
        @Expose
        public List<AdsBlock> adsBlocks = null;

        public class AdsBlock {

            @SerializedName("ads_block_id")
            @Expose
            public Integer adsBlockId;
            @SerializedName("status")
            @Expose
            public Integer status;
            @SerializedName("title")
            @Expose
            public String title;
            @SerializedName("store_id")
            @Expose
            public String storeId;
            @SerializedName("number_of_blocks")
            @Expose
            public Object numberOfBlocks;
            @SerializedName("in_which_add_block")
            @Expose
            public Integer inWhichAddBlock;
            @SerializedName("items")
            @Expose
            public List<Item> items = null;

            public class Item {

                @SerializedName("ads_block_id")
                @Expose
                public String adsBlockId;
                @SerializedName("ads_block_image_id")
                @Expose
                public String adsBlockImageId;
                @SerializedName("image")
                @Expose
                public String image;
                @SerializedName("status")
                @Expose
                public Integer status;
                @SerializedName("title")
                @Expose
                public String title;
                @SerializedName("url")
                @Expose
                public String url;

            }
        }
    }

}


