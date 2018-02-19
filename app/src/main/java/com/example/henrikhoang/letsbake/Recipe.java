package com.example.henrikhoang.letsbake;

import org.parceler.Parcel;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by henrikhoang on 12/8/17.
 */

@Parcel
public class Recipe implements Serializable {
    String NAME;
    int ID;
    ArrayList<Ingredient> INGREDIENTS = null;
    ArrayList<Step> STEPS = null;
    int SERVINGS;
    String IMAGE_URL;

    public Recipe() {}

    public Recipe(int id, String name, int servings, String imageURL) {
        this.ID = id;
        this.NAME = name;
        this.SERVINGS = servings;
        this.IMAGE_URL = imageURL;
    }

    public Recipe(int id, String name, ArrayList<Ingredient> ingredients, ArrayList<Step> steps, int servings,
                  String imageURL) {
        this.ID =id;
        this.NAME = name;
        this.INGREDIENTS = ingredients;
        this.STEPS = steps;
        this.IMAGE_URL = imageURL;
        this.SERVINGS = servings;
    }

    public String getNAME() {
        return NAME;
    }

    public void setNAME(String NAME) {
        this.NAME = NAME;
    }

    public int getID() {
        return ID;
    }

    public void setID(int ID) {
        this.ID = ID;
    }

    public ArrayList<Ingredient> getINGREDIENTS() {
        return INGREDIENTS;
    }

    public void setINGREDIENTS(ArrayList<Ingredient> INGREDIENTS) {
        this.INGREDIENTS = INGREDIENTS;
    }

    public ArrayList<Step> getSTEPS() {
        return STEPS;
    }

    public void setSTEPS(ArrayList<Step> STEPS) {
        this.STEPS = STEPS;
    }

    public int getSERVINGS() {
        return SERVINGS;
    }

    public void setSERVINGS(int SERVINGS) {
        this.SERVINGS = SERVINGS;
    }

    public String getIMAGEURL() {
        return IMAGE_URL;
    }

    public void setIMAGEURL(String IMAGE_URL) {
        this.IMAGE_URL = IMAGE_URL;
    }


}
