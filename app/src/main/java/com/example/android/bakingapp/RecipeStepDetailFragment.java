package com.example.android.bakingapp;


import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.data.Recipe;
import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.ExoPlaybackException;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.PlaybackParameters;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.Timeline;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.TrackGroupArray;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelectionArray;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.BandwidthMeter;
import com.google.android.exoplayer2.upstream.DataSource;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;


public class RecipeStepDetailFragment extends Fragment {

    public static final String RECIPE_STEP_FRAGMENT = "recipe_step_fragment";
    public static final String RECIPE_STEP_VIDEO_POSITION = "recipe_step_video_position";
    private Recipe.Step step;

    @BindView(R.id.short_description_step)
    TextView shortDescriptionStep;

    @BindView(R.id.description_step)
    TextView descriptionStep;

    @BindView(R.id.video_step)
    SimpleExoPlayerView simpleExoPlayerView;

    private SimpleExoPlayer player;
    private Uri videoUri;
    private long videoPosition;

    public RecipeStepDetailFragment() {

    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putParcelable(RECIPE_STEP_FRAGMENT, step);
        outState.putLong(RECIPE_STEP_VIDEO_POSITION, videoPosition);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            if (getArguments().containsKey(RECIPE_STEP_FRAGMENT)) {
                step = getArguments().getParcelable(RECIPE_STEP_FRAGMENT);
                videoPosition = C.TIME_UNSET;
            }
        } else {
            step = savedInstanceState.getParcelable(RECIPE_STEP_FRAGMENT);
            videoPosition = savedInstanceState.getLong(RECIPE_STEP_VIDEO_POSITION, C.TIME_UNSET);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rooView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        ButterKnife.bind(this, rooView);

        if (step != null) {
            shortDescriptionStep.setText(step.shortDescription);
            descriptionStep.setText(step.description);

            if ((step.videoURL.substring(step.videoURL.lastIndexOf('.') + 1)).equals("mp4")) {

                videoUri = Uri.parse(step.videoURL);
                initializePlayer(videoUri, rooView.getContext());
            }
        }
        return rooView;
    }

    private void initializePlayer(Uri videoUri, Context context) {
        // 1. Create a default TrackSelector
        BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
        TrackSelection.Factory videoTrackSelectionFactory =
                new AdaptiveTrackSelection.Factory(bandwidthMeter);
        TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

        // 2. Create the player
        player = ExoPlayerFactory.newSimpleInstance(context, trackSelector);

        // Bind the player to the view.
        simpleExoPlayerView.setPlayer(player);

        // Measures bandwidth during playback. Can be null if not required.
        DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
        // Produces DataSource instances through which media data is loaded.
        DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(context,
                Util.getUserAgent(context, "yourApplicationName"), defaultBandwidthMeter);

        // Produces Extractor instances for parsing the media data.
        ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
        // This is the MediaSource representing the media to be played.
        MediaSource videoSource = new ExtractorMediaSource(videoUri,
                dataSourceFactory, extractorsFactory, null, null);

        player.setPlayWhenReady(true);

        if (videoPosition != C.TIME_UNSET) {
            player.seekTo(videoPosition);
        }
        // Prepare the player with the source.
        player.prepare(videoSource);
    }

    @Override
    public void onPause() {
        super.onPause();
        Log.d("RECIPE-STEP", "On Pause");
        if (player != null) {
            videoPosition = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.d("RECIPE-STEP", "On Stop");
        if (player != null) {
            videoPosition = player.getCurrentPosition();
            player.stop();
            player.release();
            player = null;
        }
    }


}
