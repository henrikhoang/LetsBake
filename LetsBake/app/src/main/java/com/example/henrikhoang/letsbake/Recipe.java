package com.example.henrikhoang.letsbake;

import org.parceler.Parcel;

import java.util.ArrayList;

/**
 * Created by henrikhoang on 12/8/17.
 */

@Parcel
public class Recipe {
    String NAME;
    int ID;
    ArrayList<Ingredients> INGREDIENTS;
    ArrayList<Steps> STEPS;
    int SERVINGS;
    String IMAGE_URL;

    public Recipe() {}

    public Recipe(int id, String name, int servings, String imageURL) {
        this.ID = id;
        this.NAME = name;
        this.SERVINGS = servings;
        this.IMAGE_URL = imageURL;
    }
    public Recipe(int id, String name, ArrayList<Ingredients> ingredients, ArrayList<Steps> steps, int servings,
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

    public ArrayList<Ingredients> getINGREDIENTS() {
        return INGREDIENTS;
    }

    public void setINGREDIENTS(ArrayList<Ingredients> INGREDIENTS) {
        this.INGREDIENTS = INGREDIENTS;
    }

    public ArrayList<Steps> getSTEPS() {
        return STEPS;
    }

    public void setSTEPS(ArrayList<Steps> STEPS) {
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

    public static class Ingredients {
        int mQuantity;
        String mMeasure;
        String mIngredientName;



        public Ingredients(int quantity, String measure, String ingredientName) {
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

    public static class Steps {
        int id;
        String shortDescription;
        String description;
        String videoURL;
        String thumbnailURL;

        public Steps(int id, String shortDescription, String description, String videoURL, String thumbnailURL) {
            this.id = id;
            this.shortDescription = shortDescription;
            this.description = description;
            this.videoURL = videoURL;
            this.thumbnailURL = thumbnailURL;
        }
        public int getId() {
            return id;
        }

        public String getShortDescription() {return shortDescription;}

        public String getDescription() {
            return description;
        }

        public String getVideoURL() {
            return videoURL;
        }

        public String getThumbnailURL() {
            return thumbnailURL;
        }

        public void setId(int id) {
            this.id = id;
        }

        public void setShortDescription(String shortDescription) {
            this.shortDescription = shortDescription;
        }

        public void setDescription(String description) {
            this.description = description;
        }

        public void setVideoURL(String videoURL) {
            this.videoURL = videoURL;
        }

        public void setThumbnailURL(String thumbnailURL) {
            this.thumbnailURL = thumbnailURL;
        }
    }

}
