package com.example.henrikhoang.letsbake.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.utility.ThumbnailUtil;

import org.parceler.Parcels;

import butterknife.BindView;

/**
 * Created by henrikhoang on 2/15/18.
 */

public class RecipeThumbnailFragment extends Fragment {

    private static final String TAG = RecipeThumbnailFragment.class.getSimpleName();

    OnImageClickListener mCallback;

    @BindView(R.id.iv_cake_backdrop)
    ImageView mRecipeThumbnail;

    public interface OnImageClickListener {
        void onImageClicked();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnImageClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
            " must implement OnImageClickListener");
        }
    }

    private Recipe mRecipe;
    public RecipeThumbnailFragment() {
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        final View rootView = (inflater.inflate(R.layout.fragment_cake_thumbnail, container, false));

        Recipe recipe = Parcels.unwrap(getActivity().getIntent().getParcelableExtra("recipe"));
        Log.d(TAG, "INFO RECEIVED IN FRAGMENT: " + recipe.getSTEPS().get(2).getDescription());
        Log.d(TAG, "RECIPE NAME: " + recipe.getNAME());

        int thumbnailId = ThumbnailUtil.getImageResByName(getContext(), recipe.getNAME());
        Log.d(TAG, "Image Id: " + thumbnailId);

        try {
            Glide.with(getContext()).load(thumbnailId).into(mRecipeThumbnail);
        } catch (Exception e) {
            e.printStackTrace();
            Log.e(TAG, "Cannot load image");
        }
        return rootView;
    }
}
