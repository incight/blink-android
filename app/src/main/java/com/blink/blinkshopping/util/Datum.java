package com.blink.blinkshopping.util;
/*

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.google.gson.internal.LinkedTreeMap;

import java.util.ArrayList;
import java.util.List;

*/
/**
 * Created by munchado on 16/5/17.
 *//*


public class Datum<T> {
    @SerializedName("id")
    @Expose
    public String id;

    public int getSelection() {
        return selection;
    }

    public void setSelection(int selection) {
        this.selection = selection;
    }

    boolean isCalled = false;

    public boolean isCalled() {
        return isCalled;
    }

    public void setCalled(boolean called) {
        isCalled = called;
    }

    public String getSelectedWeight() {
        return selectedWeight;
    }

    public void setSelectedWeight(String selectedWeight) {
        this.selectedWeight = selectedWeight;
    }

    public String getSelectedDropType() {
        return selectedDropType;
    }

    public void setSelectedDropType(String selectedDropType) {
        this.selectedDropType = selectedDropType;
    }

    public int selection = 0;

    String isSelected ="no";

    public String getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(String isSelected) {
        this.isSelected = isSelected;
    }

    @SerializedName("is_new")
    @Expose
    public Integer is_new = 0;

    @SerializedName("name")
    @Expose
    public String name;
    @SerializedName("imageurl")
    @Expose
    public String imageurl;
    @SerializedName("sku")
    @Expose
    public String sku;
    @SerializedName("type")
    @Expose
    public String type;
    @SerializedName("spclprice")
    @Expose
    public String spclprice;
    @SerializedName("currencysymbol")
    @Expose
    public Object currencysymbol;
    @SerializedName("price")
    @Expose
    public String price;

    String actualNoOfAdded = "0";

    String selectedDropType = "";
    public String selectedWeight = "";

    public double getFinalPrice() {
        return finalPrice;
    }

    public void setFinalPrice(double finalPrice) {
        this.finalPrice = finalPrice;
    }

    double finalPrice = 0;

    public String getActualNoOfAdded() {
        return actualNoOfAdded;
    }

    public void setActualNoOfAdded(String actualNoOfAdded) {
        this.actualNoOfAdded = actualNoOfAdded;
    }

    public String getDiscountedPrice() {
        return discountedPrice;
    }

    public void setDiscountedPrice(String discountedPrice) {
        this.discountedPrice = discountedPrice;
    }

    public String discountedPrice = "";

    public double tierPricewithOptions = 0;

    public double getTierPricewithOptions() {
        return tierPricewithOptions;
    }

    public void setTierPricewithOptions(double tierPricewithOptions) {
        this.tierPricewithOptions = tierPricewithOptions;
    }

    @SerializedName("created_date")
    @Expose
    public String createdDate;
    @SerializedName("is_in_stock")
    @Expose
    public Boolean isInStock;

    @SerializedName("has_custom_option")
    @Expose
    public Integer hasCustomOption = 0;

    @SerializedName("custom_option")
    @Expose
    private List<CustomOption> customOption = null;

    @SerializedName("inventory")
    @Expose
    private List<InventryDetail> inventry = null;

    public String tag;

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public List<InventryDetail> getInventry() {
        return inventry;
    }

    public void setInventry(List<InventryDetail> inventry) {
        this.inventry = inventry;
    }

    double storeQuantity;
    double threshold = 0;
    double lowQuantity;
    float specialPrice;

    public String getPreparation_time() {
        return preparation_time;
    }

    public void setPreparation_time(String preparation_time) {
        this.preparation_time = preparation_time;
    }

    String preparation_time = "";

    ArrayList<LinkedTreeMap<String, String>> tierAttrValueList;
    String product_label;


    public ArrayList<LinkedTreeMap<String, String>> getTierAttrValueList() {

        return tierAttrValueList;
    }

    public void setTierAttrValueList(ArrayList<LinkedTreeMap<String, String>> tierAttrValueList) {
        this.tierAttrValueList = tierAttrValueList;
    }

    public String getProduct_label() {
        return product_label;
    }

    public void setProduct_label(String product_label) {
        this.product_label = product_label;
    }

    public double getThreshold() {
        return threshold;
    }

    public void setThreshold(double threshold) {
        this.threshold = threshold;
    }

    public double getLowQuantity() {
        return lowQuantity;
    }

    public void setLowQuantity(double lowQuantity) {
        this.lowQuantity = lowQuantity;
    }

    public float getSpecialPrice() {
        return specialPrice;
    }

    public void setSpecialPrice(float specialPrice) {
        this.specialPrice = specialPrice;
    }

    public double getStoreQuantity() {
        return storeQuantity;
    }

    public void setStoreQuantity(double storeQuantity) {
        this.storeQuantity = storeQuantity;
    }


    public List<CustomOption> getCustomOption() {
        return customOption;
    }

    public void setCustomOption(List<CustomOption> customOption) {
        this.customOption = customOption;
    }


    @SerializedName("custom_attribute")
    @Expose
    public CustomAttribute<T> customAttribute;

    public String getWeight() {
        return weight;
    }

    public void setWeight(String weight) {
        this.weight = weight;
    }

    public Boolean getInFav() {
        return isInFav;
    }

    public void setInFav(Boolean inFav) {
        isInFav = inFav;
    }

    @SerializedName("product_weight")
    @Expose
    public String weight = "0";

    public Boolean getIsInFav() {
        return isInFav;
    }

    public void setIsInFav(Boolean isInFav) {
        this.isInFav = isInFav;
    }

    public String getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(String categoryId) {
        this.categoryId = categoryId;
    }

    @SerializedName("is_wishlist")
    @Expose
    public Boolean isInFav = false;

    public Integer getNoOfItemsText() {
        return noOfItemsText;
    }

    public void setNoOfItemsText(Integer noOfItemsText) {
        this.noOfItemsText = noOfItemsText;
    }

    @SerializedName("hasoptions")
    @Expose
    public Integer hasoptions;

    @SerializedName("stock_quantity")
    @Expose
    public Integer stockQuantity;

    @SerializedName("is_preorder")
    @Expose
    public Integer isPreOrder = 0;

    @SerializedName("preorder_note")
    @Expose
    public String preOrderNote = "";

    @SerializedName("preorder_button")
    @Expose
    public String preOrderButton = "";

    @SerializedName("default_quantity")
    @Expose
    public String defaultQuantity;

    public String getDefaultQuantity() {
        return defaultQuantity;
    }

    public void setDefaultQuantity(String defaultQuantity) {
        this.defaultQuantity = defaultQuantity;
    }

    public Integer getIsPreOrder() {
        return isPreOrder;
    }

    public void setIsPreOrder(Integer isPreOrder) {
        this.isPreOrder = isPreOrder;
    }

    public String getPreOrderNote() {
        return preOrderNote;
    }

    public void setPreOrderNote(String preOrderNote) {
        this.preOrderNote = preOrderNote;
    }

    public String getPreOrderButton() {
        return preOrderButton;
    }

    public void setPreOrderButton(String preOrderButton) {
        this.preOrderButton = preOrderButton;
    }

    String listName;

    String categoryId;

    public Boolean getInStock() {
        return isInStock;
    }

    public void setInStock(Boolean inStock) {
        isInStock = inStock;
    }

    public String getISaDDED() {
        return ISaDDED;
    }

    public void setISaDDED(String ISaDDED) {
        this.ISaDDED = ISaDDED;
    }

    String ISaDDED = "no";

    public int getAddedNo() {
        return addedNo;
    }

    public void setAddedNo(int addedNo) {
        this.addedNo = addedNo;
    }

    int addedNo = 1;

    boolean istierPriceSet = false;

    public boolean istierPriceSet() {
        return istierPriceSet;
    }

    public void setIstierPriceSet(boolean istierPriceSet) {
        this.istierPriceSet = istierPriceSet;
    }

    int percentOff = 0;

    public int getpercentOff() {
        return percentOff;
    }

    public void setpercentOff(int percentOff) {
        this.percentOff = percentOff;
    }


    public String getListName() {
        return listName;
    }

    public void setListName(String listName) {
        this.listName = listName;
    }

    public Integer noOfItemsText = 0;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageurl() {
        return imageurl;
    }

    public void setImageurl(String imageurl) {
        this.imageurl = imageurl;
    }

    public String getSku() {
        return sku;
    }

    public void setSku(String sku) {
        this.sku = sku;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getSpclprice() {
        return spclprice;
    }

    public void setSpclprice(String spclprice) {
        this.spclprice = spclprice;
    }

    public Object getCurrencysymbol() {
        return currencysymbol;
    }

    public void setCurrencysymbol(Object currencysymbol) {
        this.currencysymbol = currencysymbol;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public Boolean getIsInStock() {
        return isInStock;
    }

    public void setIsInStock(Boolean isInStock) {
        this.isInStock = isInStock;
    }

    public Integer getHasoptions() {
        return hasoptions;
    }

    public void setHasoptions(Integer hasoptions) {
        this.hasoptions = hasoptions;
    }

    public Integer getStockQuantity() {
        return stockQuantity;
    }

    public void setStockQuantity(Integer stockQuantity) {
        this.stockQuantity = stockQuantity;
    }

    public void setHasCustomOption(Integer hasCustomOption) {
        this.hasCustomOption = hasCustomOption;
    }

    public CustomAttribute<T> getCustomAttribute() {
        return customAttribute;
    }

    public void setCustomAttribute(CustomAttribute<T> customAttribute) {
        this.customAttribute = customAttribute;
    }

    public boolean getIs_new() {
        return is_new == 1 ? true : false;
    }

    public void setIs_new(Integer is_new) {
        this.is_new = is_new;
    }


    public Datum() {

    }

    public Datum(Datum datum) {
        this.setAddedNo(datum.getAddedNo());
        this.setNoOfItemsText(datum.getNoOfItemsText());
        this.setName(datum.getName());
        this.setId(datum.getId());
        this.setCategoryId(datum.getCategoryId());
        this.setCurrencysymbol(datum.getCurrencysymbol());
        this.setCustomAttribute(datum.getCustomAttribute());
        this.setCustomOption(datum.getCustomOption());
        this.setDiscountedPrice(datum.getDiscountedPrice());
        this.setHasCustomOption(datum.getHasoptions());
        this.setISaDDED(datum.getISaDDED());
        this.setImageurl(datum.getImageurl());
        this.setInStock(datum.getInStock());
        this.setIs_new(datum.getIs_new() ? 1 : 0);
        this.setLowQuantity(datum.getLowQuantity());
        this.setListName(datum.getListName());
        this.setTierAttrValueList(datum.getTierAttrValueList());
        this.setProduct_label(datum.getProduct_label());
        this.setPrice(datum.getPrice());
        this.setSpclprice(datum.getSpclprice());
        this.setSpecialPrice(datum.getSpecialPrice());

        this.setStoreQuantity(datum.getStoreQuantity());
        this.setSku(datum.getSku());
    }


    public String getWeightOptionId() {
        if (getCustomOption() != null && getCustomOption().size() > 0) {
            return getCustomOption().get(0).getCustomOptionId();
        }
        return "";
    }

    public String getWeightOptionName() {
        if (getCustomOption() != null && getCustomOption().size() > 0) {
            return getCustomOption().get(0).getCustomOptionName();
        }
        return "";
    }

    public String getWeightOptionValueId(int position) {
        if (getCustomOption() != null && getCustomOption().size() > 0) {
            List<CustomOptionValueArray> customOptionValueArrays = getCustomOption().get(0).getCustomOptionValueArray();
            return customOptionValueArrays.get(position).getId();
        }

        return "";
    }

    public String getWeightOptionValue(int position) {
        if (getCustomOption() != null && getCustomOption().size() > 0) {
            List<CustomOptionValueArray> customOptionValueArrays = getCustomOption().get(0).getCustomOptionValueArray();
            return customOptionValueArrays.get(position).getTitle();
        }

        return "";
    }


    public String getOtherOptionId() {
        if (getCustomOption() != null && getCustomOption().size() > 1) {
            return getCustomOption().get(1).getCustomOptionId();
        }
        return "";
    }

    public String getOtherOptionName() {
        if (getCustomOption() != null && getCustomOption().size() > 1) {
            return getCustomOption().get(1).getCustomOptionName();
        }
        return "";
    }

    public String getOtherOptionValueId(int position) {
        if (getCustomOption() != null && getCustomOption().size() > 1) {
            List<CustomOptionValueArray> customOptionValueArrays = getCustomOption().get(1).getCustomOptionValueArray();
            return customOptionValueArrays.get(position).getId();
        }

        return "";
    }

    public String getOtherOptionValue(int position) {
        if (getCustomOption() != null && getCustomOption().size() > 1) {
            List<CustomOptionValueArray> customOptionValueArrays = getCustomOption().get(1).getCustomOptionValueArray();
            return customOptionValueArrays.get(position).getTitle();
        }
        return "";
    }

    public int spinner1_position = 0;

    public int getSpinner1_position() {
        return spinner1_position;
    }

    public void setSpinner1_position(int spinner1_position) {
        this.spinner1_position = spinner1_position;
    }

    public int getSpinner2_position() {
        return spinner2_position;
    }

    public void setSpinner2_position(int spinner2_position) {
        this.spinner2_position = spinner2_position;
    }

    public int spinner2_position = 0;


}
*/
