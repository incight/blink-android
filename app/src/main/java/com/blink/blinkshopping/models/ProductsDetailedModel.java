package com.blink.blinkshopping.models;

import java.util.List;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;


public class ProductsDetailedModel {

    @SerializedName("data")
    @Expose
    public Data data;

    public class Data {

        @SerializedName("products")
        @Expose
        public Products products;

        public class Products {

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
                @SerializedName("price_range")
                @Expose
                public PriceRange priceRange;

                public class PriceRange {

                    @SerializedName("minimum_price")
                    @Expose
                    public MinimumPrice minimumPrice;

                    public class MinimumPrice {

                        @SerializedName("discount")
                        @Expose
                        public Discount discount;
                        @SerializedName("regular_price")
                        @Expose
                        public RegularPrice regularPrice;
                        @SerializedName("final_price")
                        @Expose
                        public FinalPrice finalPrice;

                        public class RegularPrice {

                            @SerializedName("value")
                            @Expose
                            public Float value;
                            @SerializedName("currency")
                            @Expose
                            public String currency;

                        }

                        public class Discount {

                            @SerializedName("amount_off")
                            @Expose
                            public Float amountOff;
                            @SerializedName("percent_off")
                            @Expose
                            public Float percentOff;

                        }

                        public class FinalPrice {

                            @SerializedName("value")
                            @Expose
                            public Float value;
                            @SerializedName("currency")
                            @Expose
                            public String currency;

                        }
                    }

                }

                public class SmallImage {

                    @SerializedName("url")
                    @Expose
                    public String url;
                    @SerializedName("label")
                    @Expose
                    public String label;

                }

                public class Image {

                    @SerializedName("url")
                    @Expose
                    public String url;
                    @SerializedName("label")
                    @Expose
                    public String label;

                }

            }
        }

    }

}
