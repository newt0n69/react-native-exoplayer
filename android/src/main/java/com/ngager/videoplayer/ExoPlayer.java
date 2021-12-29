package com.ngager.videoplayer;

import android.app.Activity;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.google.android.exoplayer2.C;
import com.google.android.exoplayer2.MediaItem;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.source.ProgressiveMediaSource;
import com.google.android.exoplayer2.ui.PlayerView;
import com.google.android.exoplayer2.util.Util;

public class ExoPlayer extends Activity {

//    private static final String KEY_PLAY_WHEN_READY = "play_when_ready";
//    private static final String KEY_WINDOW = "window";
//    private static final String KEY_POSITION = "position";

    private PlayerView playerView;
    private com.google.android.exoplayer2.ExoPlayer player;
//
//    private Timeline.Window window;
//    private DataSource.Factory mediaDataSourceFactory;
//    //private DefaultTrackSelector trackSelector;
//    private TrackGroupArray lastSeenTrackGroupArray;
//    private boolean shouldAutoPlay;
//    //private BandwidthMeter bandwidthMeter;
//
//    private ProgressBar progressBar;
//    private ImageView ivHideControllerButton;
//    private ImageView ivSettings;
//    private boolean playWhenReady;
//    private int currentWindow;
//    private long playbackPosition;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        this.requestWindowFeature(Window.FEATURE_NO_TITLE); //Remove title bar
        setContentView(R.layout.activity_exo_player);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN); //Remove notification bar

        if (savedInstanceState == null) {
            Bundle extras = getIntent().getExtras();
            url = extras.getString("url");
//            playWhenReady = true;
//            currentWindow = 0;
//            playbackPosition = 0;
        } else {
//            playWhenReady = savedInstanceState.getBoolean(KEY_PLAY_WHEN_READY);
//            currentWindow = savedInstanceState.getInt(KEY_WINDOW);
//            playbackPosition = savedInstanceState.getLong(KEY_POSITION);
            url = savedInstanceState.getString("url");
        }


        //shouldAutoPlay = true;
        //bandwidthMeter = new DefaultBandwidthMeter();
        //mediaDataSourceFactory = new DefaultDataSourceFactory(this, Util.getUserAgent(this, "mediaPlayerSample"));
        //window = new Timeline.Window();
        //  ivHideControllerButton = findViewById(R.id.exo_controller);
        //  ivSettings = findViewById(R.id.settings);
        //progressBar = findViewById(R.id.progress_bar);

        initializePlayer();

    }

    private void initializePlayer() {

        playerView = findViewById(R.id.player_view);
        playerView.requestFocus();
//
//        TrackSelection.Factory videoTrackSelectionFactory =
//                new AdaptiveTrackSelection.Factory(bandwidthMeter);

        //trackSelector = new DefaultTrackSelector(videoTrackSelectionFactory);
        //lastSeenTrackGroupArray = null;

        player = new com.google.android.exoplayer2.ExoPlayer.Builder(this).build();

        playerView.setPlayer(player);

        //player.addListener(new PlayerEventListener());
        //player.setPlayWhenReady(shouldAutoPlay);

        // Use Hls, Dash or other smooth streaming media source if you want to test the track quality selection.
/*        MediaSource mediaSource = new HlsMediaSource(Uri.parse("https://bitdash-a.akamaihd.net/content/sintel/hls/playlist.m3u8"),
                mediaDataSourceFactory, mainHandler, null);*/

        MediaItem mediaItem = MediaItem.fromUri(Uri.parse(url));
//        MediaSource mediaSource = new ProgressiveMediaSource.Factory(mediaDataSourceFactory)
//                .createMediaSource(mediaItem);
//
//        boolean haveStartPosition = currentWindow != C.INDEX_UNSET;
//        if (haveStartPosition) {
//            player.seekTo(currentWindow, playbackPosition);
//        }

        player.addMediaItem(mediaItem);
        player.prepare();
        player.play();
        //updateButtonVisibilities();

//        ivHideControllerButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                playerView.hideController();
//            }
//        });
    }

    private void releasePlayer() {
        if (player != null) {
            //updateStartPosition();
            ///shouldAutoPlay = player.getPlayWhenReady();
            player.release();
            player = null;
            //trackSelector = null;
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (player == null) {
            initializePlayer();
        }
    }

    @Override
    public void onPause() {
        super.onPause();
        //if (Util.SDK_INT <= 23) {
            releasePlayer();
        //}
    }

    @Override
    public void onStop() {
        super.onStop();
        //if (Util.SDK_INT > 23) {
            releasePlayer();
        //}
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        //updateStartPosition();

//        outState.putBoolean(KEY_PLAY_WHEN_READY, playWhenReady);
//        outState.putInt(KEY_WINDOW, currentWindow);
//        outState.putLong(KEY_POSITION, playbackPosition);
        outState.putString("url", url);
        super.onSaveInstanceState(outState);
    }

//    private void updateStartPosition() {
//        playbackPosition = player.getCurrentPosition();
//        currentWindow = player.getCurrentWindowIndex();
//        playWhenReady = player.getPlayWhenReady();
//    }
//
//    private void updateButtonVisibilities() {
//      //  ivSettings.setVisibility(View.GONE);
//        if (player == null) {
//            return;
//        }
//
//        MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//        if (mappedTrackInfo == null) {
//            return;
//        }
//
//        for (int i = 0; i < mappedTrackInfo.getRendererCount(); i++) {
//            TrackGroupArray trackGroups = mappedTrackInfo.getTrackGroups(i);
//            if (trackGroups.length != 0) {
//                if (player.getRendererType(i) == C.TRACK_TYPE_VIDEO) {
//                   // ivSettings.setVisibility(View.VISIBLE);
//                   // ivSettings.setOnClickListener(this);
//                   // ivSettings.setTag(i);
//                }
//            }
//        }
//    }

   // @Override
    //public void onClick(View v) {
//        if (v.getId() == R.id.settings) {
//            MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//            if (mappedTrackInfo != null) {
//                CharSequence title = getString(R.string.video);
//                int rendererIndex = (int) ivSettings.getTag();
//                Pair<AlertDialog, TrackSelectionView> dialogPair =
//                        TrackSelectionView.getDialog(this, title, trackSelector, rendererIndex);
//                dialogPair.second.setShowDisableOption(false);
//                dialogPair.second.setAllowAdaptiveSelections(true);
//                dialogPair.first.show();
//            }
//        }
   // }

//    private class PlayerEventListener extends Player.DefaultEventListener{
//
//        @Override
//        public void onPlayerStateChanged(boolean playWhenReady, int playbackState) {
//            switch (playbackState) {
//                case Player.STATE_IDLE:       // The player does not have any media to play yet.
//                    progressBar.setVisibility(View.VISIBLE);
//                    break;
//                case Player.STATE_BUFFERING:  // The player is buffering (loading the content)
//                    progressBar.setVisibility(View.VISIBLE);
//                    break;
//                case Player.STATE_READY:      // The player is able to immediately play
//                    progressBar.setVisibility(View.GONE);
//                    break;
//                case Player.STATE_ENDED:      // The player has finished playing the media
//                    Log.e("video finished","finish");
//                    VideoClassListener.getInstance().videoState(true);
//                    progressBar.setVisibility(View.GONE);
//                    break;
//            }
//            updateButtonVisibilities();
//        }
//
//        @Override
//        public void onTracksChanged(TrackGroupArray trackGroups, TrackSelectionArray trackSelections) {
//            updateButtonVisibilities();
//            // The video tracks are no supported in this device.
//            if (trackGroups != lastSeenTrackGroupArray) {
//                MappedTrackInfo mappedTrackInfo = trackSelector.getCurrentMappedTrackInfo();
//                if (mappedTrackInfo != null) {
//                    if (mappedTrackInfo.getTypeSupport(C.TRACK_TYPE_VIDEO)
//                            == MappedTrackInfo.RENDERER_SUPPORT_UNSUPPORTED_TRACKS) {
//                        Toast.makeText(ExoPlayer.this, "Error unsupported track", Toast.LENGTH_SHORT).show();
//                    }
//                }
//                lastSeenTrackGroupArray = trackGroups;
//            }
//        }
//    }
}
