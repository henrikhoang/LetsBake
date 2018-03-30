package com.example.henrikhoang.letsbake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.utility.ThumbnailUtil;

import java.util.List;

/**
 * Created by henrikhoang on 1/17/18.
 */

public class RecipesListAdapter extends
        RecyclerView.Adapter<RecipesListAdapter.RecipesListAdapterViewHolder> {

    public static final String TAG = RecipesListAdapter.class.getSimpleName();

    private final Context mContext;
    private List<Recipe> mRecipes;
    final private RecipesListAdapterOnClickHandler mClickHandler;

    public interface RecipesListAdapterOnClickHandler {
        void onClick(Recipe recipe);
    }

    public RecipesListAdapter(RecipesListAdapterOnClickHandler clickHandler, @NonNull Context context) {
        mClickHandler = clickHandler;
        mContext = context;
    }

    class RecipesListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {


        final ImageView mRecipeThumbnailImageView;
        final TextView mRecipeNameTextView;
        final TextView mServingTextView;

        RecipesListAdapterViewHolder(View view) {
            super(view);
            mRecipeNameTextView = (TextView) view.findViewById(R.id.tv_recipe_name);
            mRecipeThumbnailImageView = (ImageView) view.findViewById(R.id.iv_recipe_thumbnail);
            mServingTextView = (TextView) view.findViewById(R.id.tv_servings);
            view.setOnClickListener(this);
        }

        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipes.get(adapterPosition);
            mClickHandler.onClick(recipe);
        }
    }

    @Override
    public RecipesListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipes_card_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipesListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesListAdapterViewHolder holder, int position) {
        final Recipe recipe = mRecipes.get(position);
        String selectedRecipeName = recipe.getNAME();
        Log.d(TAG, "NAME OF RECIPE: " + selectedRecipeName);

        int imgRes = ThumbnailUtil.getImageResByName(mContext, selectedRecipeName);
        Log.d(TAG, "img res: " + imgRes);
        holder.mRecipeNameTextView.setText(selectedRecipeName);
        Glide.with(mContext).load(imgRes).into(holder.mRecipeThumbnailImageView);
        holder.mServingTextView.setText(String.valueOf(recipe.getSERVINGS()));
    }

    @Override
    public int getItemCount() {
        if (null == mRecipes) return 0;
        return mRecipes.size();
    }

    public void setRecipeData(List<Recipe> recipes) {
        mRecipes = recipes;
        notifyDataSetChanged();
    }


}
