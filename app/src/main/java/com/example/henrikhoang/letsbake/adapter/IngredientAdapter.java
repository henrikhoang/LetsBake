package com.example.henrikhoang.letsbake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henrikhoang.letsbake.Ingredient;
import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;

/**
 * Created by henrikhoang
 */

public class IngredientAdapter extends
        RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    public static final String TAG = IngredientAdapter.class.getSimpleName();
    private Recipe mRecipe;
    private final Context mContext;

    public IngredientAdapter(@NonNull Context context, Recipe recipe) {
        mRecipe = recipe;
        mContext = context;
    }

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.ingredients_list_item_recyclerview;
        LayoutInflater inflater = LayoutInflater.from(context);
        View view = inflater.inflate(layoutIdForListItem, parent, false);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {
        final Ingredient ingredient = mRecipe.getINGREDIENTS().get(position);
        String ingredientName = ingredient.getIngredientName();
        String measure = ingredient.getMeasure();
        int quantity = ingredient.getQuantity();
        holder.mNameTextView.setText(ingredientName);
        holder.mQuantityTextView.setText(String.valueOf(quantity) + " " + measure);
    }

    @Override
    public int getItemCount() {
        if (null == mRecipe.getINGREDIENTS()) return 0;
        return mRecipe.getINGREDIENTS().size();
    }

    public void setIngredientData(Recipe recipe) {
        mRecipe = recipe;
        notifyDataSetChanged();
    }

    class IngredientAdapterViewHolder extends RecyclerView.ViewHolder {
        final TextView mNameTextView;
        final TextView mQuantityTextView;

        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            mNameTextView = (TextView) itemView.findViewById(R.id.tv_ingredients_name);
            mQuantityTextView = (TextView) itemView.findViewById(R.id.tv_ingredients_quantity);
        }
    }
}


