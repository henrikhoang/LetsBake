package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;

import com.example.henrikhoang.letsbake.fragment.IngredientsFragment;

public class IngredientActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredient);
        FragmentManager fragmentManager = getSupportFragmentManager();
        IngredientsFragment fragment = new IngredientsFragment();
        fragmentManager.beginTransaction()
                .add(R.id.ingredient_fragment, fragment)
                .commit();

    }
}
