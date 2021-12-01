package com.blink.blinkshopping.models;

import java.util.ArrayList;

public class DataModel {
    public static final String SLIDER_VIEW="SLIDER_VIEW";
    public static final String ADS_BLOCK="ADS_BLOCK";
    public static final String DEALS_OF_THE_DAY="DEALS_OF_THE_DAY";

     String FROM="FROM";

    public String getFROM() {
        return FROM;
    }

    public void setFROM(String FROM) {
        this.FROM = FROM;
    }

    public Integer getViewType() {
        return viewType;
    }

    public void setViewType(Integer viewType) {
        this.viewType = viewType;
    }

    public String getTitleText() {
        return TitleText;
    }

    public void setTitleText(String titleText) {
        TitleText = titleText;
    }

    Integer viewType;
    String TitleText;

    public String getSmallImage() {
        return smallImage;
    }

    public void setSmallImage(String smallImage) {
        this.smallImage = smallImage;
    }

    public String getMainImage() {
        return mainImage;
    }

    public void setMainImage(String mainImage) {
        this.mainImage = mainImage;
    }

    String smallImage,mainImage;

    boolean categoryProductList;
    boolean categoryItemList;
    boolean categorySubItemList;

    ArrayList<String> items;

    public ArrayList<String> getItems() {
        return items;
    }

    public void setItems(ArrayList<String> items) {
        this.items = items;
    }

    Integer catItemListSize;
    Integer catSubItemListSize;

    public Integer getCatItemListSize() {
        return catItemListSize;
    }

    public void setCatItemListSize(Integer catItemListSize) {
        this.catItemListSize = catItemListSize;
    }

    public Integer getCatSubItemListSize() {
        return catSubItemListSize;
    }

    public void setCatSubItemListSize(Integer catSubItemListSize) {
        this.catSubItemListSize = catSubItemListSize;
    }

    public boolean isCategoryProductList() {
        return categoryProductList;
    }

    public void setCategoryProductList(boolean categoryProductList) {
        this.categoryProductList = categoryProductList;
    }

    public boolean isCategoryItemList() {
        return categoryItemList;
    }

    public void setCategoryItemList(boolean categoryItemList) {
        this.categoryItemList = categoryItemList;
    }

    public boolean isCategorySubItemList() {
        return categorySubItemList;
    }

    public void setCategorySubItemList(boolean categorySubItemList) {
        this.categorySubItemList = categorySubItemList;
    }


}
