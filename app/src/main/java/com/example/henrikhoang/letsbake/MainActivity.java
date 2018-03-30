package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.annotation.VisibleForTesting;
import android.support.test.espresso.IdlingResource;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.henrikhoang.letsbake.IdlingResource.SimpleIdlingResource;
import com.example.henrikhoang.letsbake.fragment.MenuRecipeFragment;

public class MainActivity extends AppCompatActivity {

    @Nullable
    public static SimpleIdlingResource mIdlingResource;

    public static boolean mLargeScreen;

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
        MenuRecipeFragment.setGridSpanCount(2);
        mLargeScreen = false;
        setContentView(R.layout.activity_main);
        if (findViewById(R.id.tv_large_screen) != null) {
            mLargeScreen = true;
            MenuRecipeFragment.setGridSpanCount(3);
            FragmentManager fragmentManager = getSupportFragmentManager();
            MenuRecipeFragment fragment = new MenuRecipeFragment();
            fragmentManager.beginTransaction()
                    .add(R.id.recipe_menu_fragment, fragment)
                    .commit();
        }
        getIdlingResource();
    }
}
