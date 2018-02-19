package com.example.henrikhoang.letsbake;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.widget.Toast;

import com.example.henrikhoang.letsbake.adapter.IngredientAdapter;
import com.example.henrikhoang.letsbake.fragment.IngredientStepFragment;
import com.example.henrikhoang.letsbake.fragment.RecipeThumbnailFragment;

public class IngredientsActivity extends AppCompatActivity implements
 RecipeThumbnailFragment.OnImageClickListener
{

    private static final String TAG = IngredientsActivity.class.getSimpleName();
    private RecyclerView mStepsRecyclerView;
    private Recipe mRecipe;
    private IngredientAdapter mIngredientAdapter;


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
                .commit();
    }


//        setContentView(R.layout.activity_ingredients);
//        Recipe recipe = Parcels.unwrap(getIntent().getParcelableExtra("recipe"));
//        mRecipe = recipe;
//        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar_ingredient);
//        setSupportActionBar(toolbar);
//        toolbar.setTitle(recipe.getNAME());
//        initCollapsingToolbar();
//
//        Log.d(TAG, "INFO RECEIVED: " + recipe.getSTEPS().get(2).getDescription());
//
//        mStepsRecyclerView = (RecyclerView) findViewById(R.id.rv_steps_list_item);
//
//        mIngredientAdapter = new IngredientAdapter(this, this, recipe);
//
//        mIngredientAdapter.setStepData(recipe);
//        mStepsRecyclerView.setAdapter(mIngredientAdapter);
//
//        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
//        mStepsRecyclerView.setLayoutManager(layoutManager);
//        mStepsRecyclerView.setItemAnimator(new DefaultItemAnimator());
//        int thumbnailId = ThumbnailUtil.getImageResByName(this, recipe.getNAME());
//
//        try {
//            Glide.with(this).load(thumbnailId).into((ImageView) findViewById(R.id.iv_cake_backdrop));
//        } catch (Exception e) {
//            e.printStackTrace();
//        }
//
//
//    }
//
//    private void initCollapsingToolbar() {
//        final CollapsingToolbarLayout collapsingToolbarLayout =
//                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar_ingredients);
//        collapsingToolbarLayout.setTitle(mRecipe.getNAME());
//        AppBarLayout appBarLayout = (AppBarLayout) findViewById(R.id.appbar_ingredients_steps);
//        appBarLayout.setExpanded(true);
//
//        appBarLayout.addOnOffsetChangedListener(new AppBarLayout.OnOffsetChangedListener() {
//            boolean isShow = false;
//            int scrollRange = -1;
//
//            @Override
//            public void onOffsetChanged(AppBarLayout appBarLayout, int verticalOffset) {
//                if (scrollRange == -1) {
//                    scrollRange = appBarLayout.getTotalScrollRange();
//                }
//                if (scrollRange + verticalOffset == 0) {
//                    collapsingToolbarLayout.setTitle(mRecipe.getNAME());
//                    isShow = true;
//                } else if (isShow) {
//                    collapsingToolbarLayout.setTitle(mRecipe.getNAME());
//                    isShow = false;
//                }
//            }
//        });
//    }
//
//
//
//    @Override
//    public void onCLick(Recipe.Steps step) {
//        Log.d(TAG, "Step " + step.getId() + " was clicked");
//        Context context = this;
//        Class destinationClass = IngredientsActivity.class;
//        Intent intent = new Intent(context, destinationClass);
//        intent.putExtra("step", Parcels.wrap(step));
//        startActivity(intent);
//    }


}
