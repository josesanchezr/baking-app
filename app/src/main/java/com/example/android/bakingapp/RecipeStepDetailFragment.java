package com.example.android.bakingapp;


import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.android.bakingapp.data.Recipe;
import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.extractor.ExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
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
    private Recipe.Step step;

    @BindView(R.id.short_description_step)
    TextView shortDescriptionStep;

    @BindView(R.id.description_step)
    TextView descriptionStep;

    @BindView(R.id.video_step)
    SimpleExoPlayerView simpleExoPlayerView;

    public RecipeStepDetailFragment() {

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments().containsKey(RECIPE_STEP_FRAGMENT)) {
            step = getArguments().getParcelable(RECIPE_STEP_FRAGMENT);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rooView = inflater.inflate(R.layout.fragment_recipe_step_detail, container, false);
        ButterKnife.bind(this, rooView);

        if (step != null) {
            shortDescriptionStep.setText(step.shortDescription);
            descriptionStep.setText("");
            descriptionStep.setText(step.description);

            if ((step.videoURL.substring(step.videoURL.lastIndexOf('.') + 1)).equals("mp4")) {

                Uri videoUri = Uri.parse(step.videoURL);
                // 1. Create a default TrackSelector
                Handler mainHandler = new Handler();
                BandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
                TrackSelection.Factory videoTrackSelectionFactory =
                        new AdaptiveTrackSelection.Factory(bandwidthMeter);
                TrackSelector trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);

                // 2. Create the player
                SimpleExoPlayer player =
                        ExoPlayerFactory.newSimpleInstance(rooView.getContext(), trackSelector);

                // Bind the player to the view.
                simpleExoPlayerView.setPlayer(player);

                // Measures bandwidth during playback. Can be null if not required.
                DefaultBandwidthMeter defaultBandwidthMeter = new DefaultBandwidthMeter();
                // Produces DataSource instances through which media data is loaded.
                DataSource.Factory dataSourceFactory = new DefaultDataSourceFactory(rooView.getContext(),
                        Util.getUserAgent(rooView.getContext(), "yourApplicationName"), defaultBandwidthMeter);
                // Produces Extractor instances for parsing the media data.
                ExtractorsFactory extractorsFactory = new DefaultExtractorsFactory();
                // This is the MediaSource representing the media to be played.
                MediaSource videoSource = new ExtractorMediaSource(videoUri,
                        dataSourceFactory, extractorsFactory, null, null);
                // Prepare the player with the source.
                player.prepare(videoSource);
            }
        }
        return rooView;
    }

}
