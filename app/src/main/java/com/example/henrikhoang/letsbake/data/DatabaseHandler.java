package com.example.henrikhoang.letsbake.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.henrikhoang.letsbake.Recipe;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by henrikhoang on 1/15/18.
 */

public class DatabaseHandler extends SQLiteOpenHelper implements IDatabaseHandler {
    private static final int DATABASE_VERSION = 1;

    private static final String DATABASE_NAME = "BakingApp.db";

    private static final String TABLE_NAME = "recipes";
    private static final String COLUMN_RECIPE_ID = "id";
    private static final String COLUMN_NAME = "name";
    private static final String COLUMN_SERVINGS = "servings";
    private static final String COLUMN_IMAGE = "image";

    private static final int COLUMN_RECIPE_ID_INDEX  = 0;
    private static final int COLUMN_NAME_INDEX = 1;
    private static final int COLUMN_SERVINGS_INDEX = 2;
    private static final int COLUMN_IMAGE_INDEX = 3;

    public DatabaseHandler(Context context)  {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        final String SQL_CREATE_RECIPE_TABLE =
                "CREATE TABLE " + TABLE_NAME + " (" +
                        COLUMN_RECIPE_ID + " INTEGER PRIMARY KEY NOT NULL, " +
                        COLUMN_NAME + " TEXT NOT NULL, " +
                        COLUMN_SERVINGS + " INTEGER NOT NULL, " +
                        COLUMN_IMAGE + " TEXT NOT NULL);";
        db.execSQL(SQL_CREATE_RECIPE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TALE IF EXISTS " + DATABASE_NAME);
        onCreate(db);
    }

    @Override
    public void addRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put(COLUMN_RECIPE_ID, recipe.getID());
        values.put(COLUMN_NAME, recipe.getNAME());
        values.put(COLUMN_IMAGE, recipe.getIMAGEURL());
        values.put(COLUMN_SERVINGS, recipe.getSERVINGS());

        db.insert(TABLE_NAME, null, values);
        db.close();
    }

    @Override
    public Recipe getRecipe(int id) {
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(TABLE_NAME, new String[] { COLUMN_RECIPE_ID,
        COLUMN_NAME, COLUMN_SERVINGS, COLUMN_IMAGE }, COLUMN_RECIPE_ID + "=?",
                new String[] { String.valueOf(id) }, null, null, null, null);

        if (cursor != null)
            cursor.moveToFirst();

        Recipe recipe = new Recipe(cursor.getInt(0), cursor.getString(1), cursor.getInt(2), cursor.getString(3));
        return recipe;
    }

    @Override
    public List<Recipe> getAllRecipes() {
        List<Recipe> recipeList = new ArrayList<Recipe>();
        String selectQuery = "SELECT * FROM " + TABLE_NAME;

        SQLiteDatabase db = this.getWritableDatabase();
        Cursor cursor = db.rawQuery(selectQuery, null);

        if (cursor.moveToFirst()) {
            do {
                Recipe recipe = new Recipe();
                recipe.setID(cursor.getInt(COLUMN_RECIPE_ID_INDEX));
                recipe.setNAME(cursor.getString(COLUMN_NAME_INDEX));
                recipe.setSERVINGS(cursor.getInt(COLUMN_SERVINGS_INDEX));
                recipe.setIMAGEURL(cursor.getString(COLUMN_IMAGE_INDEX));
                recipeList.add(recipe);
            } while (cursor.moveToNext());
        }

        return recipeList;
    }


    @Override
    public void deleteRecipe(Recipe recipe) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_NAME, COLUMN_RECIPE_ID + " = ?",
                new String[] { String.valueOf(recipe.getID()) });
                db.close();
    }

    @Override
    public int getRecipesCount() {
        String countQuery = "SELECT * FROM " + TABLE_NAME;
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery(countQuery, null);
        cursor.close();
        return cursor.getCount();
    }

}
