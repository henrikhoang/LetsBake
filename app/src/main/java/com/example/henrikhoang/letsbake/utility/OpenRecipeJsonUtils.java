package com.example.henrikhoang.letsbake.utility;

import android.content.Context;
import android.support.annotation.Nullable;

import com.example.henrikhoang.letsbake.IdlingResource.SimpleIdlingResource;
import com.example.henrikhoang.letsbake.Ingredient;
import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.Step;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by henrikhoang on 12/7/17.
 */

public final class OpenRecipeJsonUtils {
    private static final String ID = "id";
    private static final String NAME = "name";
    private static final String INGREDIENTS = "ingredients";
    private static final String Iquantity = "quantity";
    private static final String Imeasure = "measure";
    private static final String Iingredient = "ingredient";
    private static final String STEPS = "steps";
    private static final String SHORT_DESCRIPTION = "shortDescription";
    private static final String DESCRIPTION = "description";
    private static final String VIDEO_URL = "videoURL";
    private static final String THUMBNAIL_URL = "thumbnailURL";
    private static final String SERVINGS = "servings";
    private static final String IMAGE = "image";

    public static ArrayList<Recipe> getRecipeFromJson(Context context, String recipeJsonStr,
                                                      @Nullable final SimpleIdlingResource idlingResource)
    throws JSONException {

        if (idlingResource != null) {
            idlingResource.setIdleState(false);
        }

        JSONArray recipeArray = new JSONArray(recipeJsonStr);
        ArrayList<Recipe> recipes = new ArrayList<>(); //List of recipes

        for (int i = 0; i < recipeArray.length(); i++) {
            JSONObject dish = recipeArray.getJSONObject(i);
            Recipe recipe = new Recipe(); //Recipe of one dish only
            recipe.setID(dish.getInt(ID));
            recipe.setNAME(dish.getString(NAME));
            recipe.setSERVINGS(dish.getInt(SERVINGS));
            recipe.setIMAGEURL(dish.getString(IMAGE));
            JSONArray ingredientsArray = dish.getJSONArray(INGREDIENTS);

            ArrayList<Ingredient> allIngredients = new ArrayList<>(); //List of ingredients of a SINGLE recipe

            for (int m = 0; m < ingredientsArray.length(); m++) {
                JSONObject componentIngredient = ingredientsArray.getJSONObject(m);

                int quantity = componentIngredient.getInt(Iquantity);
                String measurement = componentIngredient.getString(Imeasure);
                String ingredientName = componentIngredient.getString(Iingredient);

                Ingredient tempIngredient = new Ingredient(quantity, measurement, ingredientName);
                allIngredients.add(tempIngredient); //A single ingredient is added to the list of ingredients
            }

            JSONArray stepsArray = dish.getJSONArray(STEPS);
            ArrayList<Step> allSteps = new ArrayList<>(); //List of steps of a SINGLE recipe

            for (int n = 0; n < stepsArray.length(); n++) {
                JSONObject listOfSteps = stepsArray.getJSONObject(n);

                int id = listOfSteps.getInt(ID);
                String shortDescription = listOfSteps.getString(SHORT_DESCRIPTION);
                String description = listOfSteps.getString(DESCRIPTION);
                String videoURL = listOfSteps.getString(VIDEO_URL);
                String thumbnailURL = listOfSteps.getString(THUMBNAIL_URL);

                Step tempStep = new Step(id, shortDescription, description, videoURL, thumbnailURL);
                allSteps.add(tempStep); //A SINGLE step is added to the list of steps
            }
            recipe.setINGREDIENTS(allIngredients);
            recipe.setSTEPS(allSteps);
            recipes.add(recipe);
        }

        if (idlingResource != null) {
            idlingResource.setIdleState(true);
        }

        return recipes;
    }
}
