package com.example.henrikhoang.letsbake.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;

/**
 * Created by henrikhoang on 2/6/18.
 */

public class IngredientAdapter extends
        RecyclerView.Adapter<IngredientAdapter.IngredientAdapterViewHolder> {

    public static final String TAG = IngredientAdapter.class.getSimpleName();

    private final Context mContext;
    private Recipe mRecipe;
    final private IngredientAdapterOnClickHandler mClickHandler;

    public interface IngredientAdapterOnClickHandler {
        void onCLick(Recipe.Steps step);
    }

    public IngredientAdapter(IngredientAdapterOnClickHandler clickHandler, @NonNull Context context) {
        mClickHandler = clickHandler;
        mContext = context;
    }

    @Override
    public IngredientAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.steps_card_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new IngredientAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(IngredientAdapterViewHolder holder, int position) {
        final Recipe.Steps step = mRecipe.getSTEPS().get(position);
        int stepOrder = step.getId() + 1;
        Log.d(TAG, "STEP NUMBER: " + stepOrder);
        String shortDescription = step.getShortDescription();
        holder.mStepOrderTextView.setText(String.valueOf(stepOrder));
        holder.mStepShortDescription.setText(shortDescription);
    }

    @Override
    public int getItemCount() {
        if (null == mRecipe.getSTEPS()) return 0;
        return mRecipe.getSTEPS().size();
    }

    public void setStepData(Recipe recipe) {
        mRecipe = recipe;
        notifyDataSetChanged();
    }

    class IngredientAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mStepOrderTextView;
        final TextView mStepShortDescription;

        public IngredientAdapterViewHolder(View itemView) {
            super(itemView);
            mStepShortDescription = (TextView) itemView.findViewById(R.id.tv_shortDescription);
            mStepOrderTextView = (TextView) itemView.findViewById(R.id.tv_step_number);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Recipe.Steps step = mRecipe.getSTEPS().get(adapterPosition);
            mClickHandler.onCLick(step);
        }
    }

}
