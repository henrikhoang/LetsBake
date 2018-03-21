package com.example.henrikhoang.letsbake.adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.Step;

import org.parceler.Parcels;

/**
 * Created by henrikhoang on 2/6/18.
 */

public class StepAdapter extends
        RecyclerView.Adapter<StepAdapter.StepAdapterViewHolder> {

    public static final String TAG = StepAdapter.class.getSimpleName();

    private final Context mContext;
    private Recipe mRecipe;
    final private StepAdapterOnClickHandler mClickHandler;

    public interface StepAdapterOnClickHandler {
        void onCLick(Bundle bundle);
    }

    public StepAdapter(StepAdapterOnClickHandler clickHandler, @NonNull Context context, Recipe recipe) {
        mClickHandler = clickHandler;
        mContext = context;
        mRecipe = recipe;
    }

    class StepAdapterViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        final TextView mStepShortDescription;

        public StepAdapterViewHolder(View itemView) {
            super(itemView);
            mStepShortDescription = (TextView) itemView.findViewById(R.id.tv_shortDescription);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int adapterPosition = getAdapterPosition();
            Bundle bundle = new Bundle();
            bundle.putParcelable("list of steps", Parcels.wrap(mRecipe.getSTEPS()));
            bundle.putInt("step index", adapterPosition);
            mClickHandler.onCLick(bundle);
        }
    }
    @Override
    public StepAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        int layoutIdForListItem = R.layout.steps_card_view;
        LayoutInflater inflater = LayoutInflater.from(context);
        boolean shouldAttachToParentImmediately = false;

        View view = inflater.inflate(layoutIdForListItem, parent, shouldAttachToParentImmediately);
        return new StepAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(StepAdapterViewHolder holder, int position) {
        final Step step = mRecipe.getSTEPS().get(position);
        String shortDescription = step.getShortDescription();
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



}
