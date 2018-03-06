package com.example.henrikhoang.letsbake.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.Recipe;
import com.example.henrikhoang.letsbake.Step;
import com.google.android.exoplayer2.DefaultLoadControl;
import com.google.android.exoplayer2.DefaultRenderersFactory;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultHttpDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import org.parceler.Parcels;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.Unbinder;

/**
 * Created by henrikhoang
 */

public class StepFragment extends Fragment {

    private static final String TAG = StepFragment.class.getSimpleName();
    private SimpleExoPlayer player;


    private long playbackPosition;
    private int currentWindow;
    private boolean playWhenReady = true;

    Unbinder unbinder;

    @BindView(R.id.playerView)
    SimpleExoPlayerView mPlayerView;

    @BindView(R.id.tv_step_instruction)
    TextView mInstruction;


    @BindView(R.id.button_previous_step)
    ImageButton mPreviousButton;

    @BindView(R.id.button_next_step)
    ImageButton mNextButton;

    @BindView(R.id.step_toolbar)
    Toolbar mToolbar;

    OnButtonClickListener mCallback;
    private Step mStep;
    private Recipe mRecipe;

    private int stepIndex;

    public interface OnButtonClickListener {
        void onPreviousButtonClicked();
        void onNextButtonClicked();
    }

    public StepFragment() {}


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        try {
            mCallback = (OnButtonClickListener) context;
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
            " must implement OnButtonClickListener");
        }
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_view, container, false);
        unbinder = ButterKnife.bind(this, rootView);

        Step step = Parcels.unwrap(getActivity().getIntent().getParcelableExtra("step"));

        mStep = step;

        ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
        mToolbar.setTitle(mStep.getShortDescription());

        String description = mStep.getDescription();
        mInstruction.setText(description);
        Log.d(TAG, "VIDEO URL CHECK: " + mStep.getVideoURL());
        if (mStep.getVideoURL() == null || mStep.getVideoURL() == "") {
            mPlayerView.setVisibility(View.GONE);
        } else {
            mPlayerView.setVisibility(View.VISIBLE);
        }

//        mPreviousButton.setOnClickListener(new ImageButton.OnClickListener() {
//
//            @Override
//            public void onClick(View v) {
//                    mCallback.onPreviousButtonClicked();
//                }
//            });
//
//        mNextButton.setOnClickListener(new ImageButton.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                    mCallback.onNextButtonClicked();
//                }
//            });
        return rootView;
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23) {
            initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        Log.d(TAG, "onResume");
        if ((Util.SDK_INT <= 23 || player == null)) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (Util.SDK_INT <= 23) {
            releasePlayer();
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) {
            releasePlayer();
        }
    }

    @Override
    public void onViewStateRestored(@Nullable Bundle savedInstanceState) {
        super.onViewStateRestored(savedInstanceState);
    }

    public void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mPlayerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        if (mStep.getVideoURL() != null || mStep.getVideoURL() != "") {
            MediaSource mediaSource = buildMediaSource(Uri.parse(mStep.getVideoURL()));
            player.prepare(mediaSource, true, false);
        }
    }

    public void releasePlayer() {
        if (player != null) {
            playbackPosition = player.getCurrentPosition();
            currentWindow = player.getCurrentWindowIndex();
            playWhenReady = player.getPlayWhenReady();
            player.release();
            player = null;
        }
    }

    private MediaSource buildMediaSource(Uri uri) {
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("testing"))
                .createMediaSource(uri);
    }

    @SuppressLint("InlinedApi")
    private void hideSystemUi() {
        mPlayerView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LOW_PROFILE
                | View.SYSTEM_UI_FLAG_FULLSCREEN
                | View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
    }
    @Override
    public void onDestroyView() {
        super.onDestroyView();
        unbinder.unbind();
    }

//    public void setPreviousButtonOff() {
//        mPreviousButton.setVisibility(View.GONE);
//    }
//
//    public void setPreviousButtonOn() {
//        mPreviousButton.setVisibility(View.VISIBLE);
//    }
//
//    public void setNextButtonOff() {
//        mNextButton.setVisibility(View.GONE);
//    }
//
//    public void setNextButtonOn() {
//        mNextButton.setVisibility(View.VISIBLE);
//    }
}
