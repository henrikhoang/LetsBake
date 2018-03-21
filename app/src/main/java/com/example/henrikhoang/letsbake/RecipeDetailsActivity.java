package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.henrikhoang.letsbake.IdlingResource.SimpleIdlingResource;
import com.example.henrikhoang.letsbake.fragment.RecipeDetailFragment;
import com.example.henrikhoang.letsbake.fragment.RecipeThumbnailFragment;

public class RecipeDetailsActivity extends AppCompatActivity implements
 RecipeThumbnailFragment.OnImageClickListener
{

    private static final String TAG = RecipeDetailsActivity.class.getSimpleName();

    @Nullable
    public static SimpleIdlingResource mIdlingResource;

    @VisibleForTesting
    @NonNull
    public IdlingResource getIdlingResource() {
        if (mIdlingResource == null) {
            mIdlingResource = new SimpleIdlingResource();
        }
        return mIdlingResource;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_details);
        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeThumbnailFragment recipeThumbnailFragment = new RecipeThumbnailFragment();

        fragmentManager.beginTransaction()
                .add(R.id.ingre_step_fragment, recipeThumbnailFragment)
                .commit();
        getIdlingResource();
    }

    @Override
    public void onImageClicked() {
        RecipeDetailFragment newFragment = new RecipeDetailFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ingre_step_fragment, newFragment)
                .addToBackStack(null)
                .commit();
    }
}
