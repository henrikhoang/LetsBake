package com.example.henrikhoang.letsbake.data;

import com.example.henrikhoang.letsbake.Recipe;

import java.util.List;

/**
 * Created by henrikhoang on 1/15/18.
 */

public interface IDatabaseHandler {
    void addRecipe(Recipe recipe);
    Recipe getRecipe(int id);
    List<Recipe> getAllRecipes();
    void deleteRecipe(Recipe recipe);
    int getRecipesCount();
}
