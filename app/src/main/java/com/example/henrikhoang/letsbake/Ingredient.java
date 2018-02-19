package com.example.henrikhoang.letsbake;

import org.parceler.Parcel;

import java.io.Serializable;

/**
 * Created by henrikhoang
 */

@Parcel
public class Ingredient implements Serializable {
    int mQuantity;
    String mMeasure;
    String mIngredientName;

    public Ingredient() {}

    public Ingredient(int quantity, String measure, String ingredientName) {
        this.mQuantity = quantity;
        this.mMeasure = measure;
        this.mIngredientName = ingredientName;
    }
    public int getQuantity() { return mQuantity;}

    public String getMeasure() {return mMeasure;}

    public String getIngredientName() { return mIngredientName;}

    public void setmQuantity(int mQuantity) {
        this.mQuantity = mQuantity;
    }

    public void setmMeasure(String mMeasure) {
        this.mMeasure = mMeasure;
    }

    public void setmIngredientName(String mIngredientName) {
        this.mIngredientName = mIngredientName;
    }


}
