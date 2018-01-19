package com.example.henrikhoang.letsbake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;

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

    @Override
    public RecipesListAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.recipe_list_item_recyclerview;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new RecipesListAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(RecipesListAdapterViewHolder holder, int position) {
        final Recipe recipe = mRecipes.get(position);
        String selectedRecipeName = recipe.getNAME();
        holder.mRecipeNameTextView.setText(selectedRecipeName);
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

    class RecipesListAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mRecipeNameTextView;

        RecipesListAdapterViewHolder(View view) {
            super(view);
            mRecipeNameTextView = (TextView) view.findViewById(R.id.recyclerview_recipes_list);
            view.setOnClickListener(this);
        }

        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe recipe = mRecipes.get(adapterPosition);
            mClickHandler.onClick(recipe);
        }
    }
}
