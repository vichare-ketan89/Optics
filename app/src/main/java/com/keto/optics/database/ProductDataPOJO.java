package com.keto.optics.database;

import java.io.Serializable;

/**
 * Created by ketanvichare on 10/11/16.
 */
public class ProductDataPOJO implements Serializable {
    private String mProductName;
    private String mModelName;
    private String mPrice;
    private String imageData;

    public String getmProductName() {
        return mProductName;
    }

    public void setmProductName(String mProductName) {
        this.mProductName = mProductName;
    }

    public String getmModelName() {
        return mModelName;
    }

    public void setmModelName(String mModelName) {
        this.mModelName = mModelName;
    }

    public String getmPrice() {
        return mPrice;
    }

    public void setmPrice(String mPrice) {
        this.mPrice = mPrice;
    }

    public String getImageData() {
        return imageData;
    }

    public void setImageData(String imageData) {
        this.imageData = imageData;
    }
}
