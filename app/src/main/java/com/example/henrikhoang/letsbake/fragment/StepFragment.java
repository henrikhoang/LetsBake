package com.example.henrikhoang.letsbake.fragment;

import android.annotation.SuppressLint;
import android.content.Context;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.henrikhoang.letsbake.R;
import com.example.henrikhoang.letsbake.RecipeDetailsActivity;
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

import java.util.Objects;

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

    @BindView(R.id.iv_no_video)
    ImageView mNoVideoImageView;

    OnButtonClickListener mCallback;
    private static Step mStep;

    private static String mVideoURL;
    private static String mThumbnailURL;

    private boolean playThumbnailUrl;



    public interface OnButtonClickListener {
        void onPreviousButtonClicked();
        void onNextButtonClicked();
    }

    public StepFragment() {}


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        if (!RecipeDetailsActivity.isTwoPane()) {
            try {
                mCallback = (OnButtonClickListener) context;
            } catch (ClassCastException e) {
                throw new ClassCastException(context.toString() +
                        " must implement OnButtonClickListener");
            }
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        playbackPosition = player.getCurrentPosition();
        outState.putLong("current play position", playbackPosition);
        Log.d(TAG, "SavedInstanceState " + playbackPosition);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if (savedInstanceState != null) {
            playbackPosition = savedInstanceState.getLong("current play position");
            Log.d(TAG, "SavedInstanceStateRestored: " + playbackPosition);
        }
    }

    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View rootView = inflater.inflate(R.layout.fragment_step_view, container, false);
        unbinder = ButterKnife.bind(this, rootView);
        Log.d(TAG, "onCreate");
        Step step = mStep;
        if (step != null) {
            playThumbnailUrl = false;
            mVideoURL = step.getVideoURL();
            mThumbnailURL = step.getThumbnailURL();
            ((AppCompatActivity) getActivity()).setSupportActionBar(mToolbar);
            mToolbar.setTitle(step.getShortDescription());

            String description = step.getDescription();
            mInstruction.setText(description);
            Log.d(TAG, "VIDEO URL CHECK: " + step.getVideoURL());

            boolean isVideoUrlUnavail = Objects.equals(mVideoURL, "");
            boolean isThumbnailUrlUnavail = Objects.equals(mThumbnailURL, "");

            Log.d(TAG, "VIDEO URL CHECK: " + isVideoUrlUnavail);

            if (isVideoUrlUnavail) {
               if (isThumbnailUrlUnavail) {
                   noVideoURl();
               } else if (!isThumbnailUrlUnavail) {
                   playThumbnailUrl = true;
                   hasVideoUrl();
               }
            } else {
               hasVideoUrl();
            }

            mPreviousButton.setOnClickListener(new ImageButton.OnClickListener() {

                @Override
                public void onClick(View v) {
                    mCallback.onPreviousButtonClicked();
                }
            });

            mNextButton.setOnClickListener(new ImageButton.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mCallback.onNextButtonClicked();
                }
            });

            if (RecipeDetailsActivity.isTwoPane()) {
                mNextButton.setVisibility(View.GONE);
                mPreviousButton.setVisibility(View.GONE);
            }
        }
            return rootView;

    }

    public void updateDataStep(Step step) {
        mStep = step;
        Log.d(TAG, "STEP UPDATED!");
    }
    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 ) {
            Log.d(TAG, "onStart");
           initializePlayer();
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        hideSystemUi();
        Log.d(TAG, "onResume");
        if (Util.SDK_INT <= 23 || player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d(TAG, "onPause");
        if (Util.SDK_INT <= 23) {
            Log.d(TAG, "Playback position: " + player.getCurrentPosition());
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


    public void initializePlayer() {
        if (player == null) {
            player = ExoPlayerFactory.newSimpleInstance(new DefaultRenderersFactory(getContext()),
                    new DefaultTrackSelector(), new DefaultLoadControl());
            mPlayerView.setPlayer(player);
            player.setPlayWhenReady(playWhenReady);
            player.seekTo(currentWindow, playbackPosition);
        }
        if (!playThumbnailUrl) {
            MediaSource mediaSource = buildMediaSource(Uri.parse(mVideoURL));
            player.prepare(mediaSource, false, false);
         }

        if (playThumbnailUrl) {
            MediaSource mediaSource = buildMediaSource(Uri.parse(mThumbnailURL));
            player.prepare(mediaSource, false, false);
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
        return new ExtractorMediaSource.Factory(new DefaultHttpDataSourceFactory("LetsBake"))
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



    public void noVideoURl() {
        mPlayerView.setVisibility(View.INVISIBLE);
        mNoVideoImageView.setVisibility(View.VISIBLE);
    }

    public void hasVideoUrl() {
        mPlayerView.setVisibility(View.VISIBLE);
        mNoVideoImageView.setVisibility(View.INVISIBLE);
    }

}
