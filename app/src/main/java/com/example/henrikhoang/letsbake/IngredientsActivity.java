package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.widget.Toast;

import com.example.henrikhoang.letsbake.fragment.IngredientStepFragment;
import com.example.henrikhoang.letsbake.fragment.RecipeThumbnailFragment;

public class IngredientsActivity extends AppCompatActivity implements
 RecipeThumbnailFragment.OnImageClickListener
{

    private static final String TAG = IngredientsActivity.class.getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ingredients);
        FragmentManager fragmentManager = getSupportFragmentManager();

        RecipeThumbnailFragment recipeThumbnailFragment = new RecipeThumbnailFragment();

        fragmentManager.beginTransaction()
                .add(R.id.ingre_step_fragment, recipeThumbnailFragment)
                .commit();
    }

    @Override
    public void onImageClicked() {
        Toast.makeText(this, "Image has been clicked", Toast.LENGTH_SHORT).show();
        IngredientStepFragment newFragment = new IngredientStepFragment();
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.ingre_step_fragment, newFragment)
                .addToBackStack(null)
                .commit();
    }
}
