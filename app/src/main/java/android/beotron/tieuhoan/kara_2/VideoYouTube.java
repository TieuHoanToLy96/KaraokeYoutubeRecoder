package android.beotron.tieuhoan.kara_2;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import java.util.ArrayList;

import adapter.HomeAdapter;
import model.Song;
import ulti.HangSo;
import ulti.Recoder;

public class VideoYouTube extends YouTubeBaseActivity implements YouTubePlayer.OnInitializedListener, AdapterView.OnItemClickListener,
        View.OnClickListener {
    private Song song;
    private ArrayList<Song> songs;
    private YouTubePlayerView youTubePlayerView;
    private YouTubePlayer playVideo;
    private boolean isFullScreen;
    private ListView listView;

    private Button btnRecoder, btnMic, btnEqualizer;

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);

        setContentView(R.layout.video_you_tube);
        playVideo();
        setUpButton();
        setUpListView();
    }

    public void setUpButton() {

        btnMic = (Button) findViewById(R.id.btnMic);
        btnMic.setOnClickListener(this);
    }

    public void playVideo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();
        if (bundle != null) {
            song = (Song) bundle.getSerializable("SONG");
            songs = (ArrayList<Song>) bundle.getSerializable("SONGS");
        }

        youTubePlayerView = (YouTubePlayerView) findViewById(R.id.videoYoutube);
        youTubePlayerView.initialize(HangSo.APP_KEY, this);
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    public void setUpListView() {
        listView = (ListView) findViewById(R.id.listView);
        HomeAdapter popularAdapter = new HomeAdapter(this, songs);
        listView.setAdapter(popularAdapter);
        listView.setNestedScrollingEnabled(true);
        listView.setOnItemClickListener(this);
    }

    int seek;

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        playVideo = youTubePlayer;
        youTubePlayer.setShowFullscreenButton(true);
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
        youTubePlayer.cueVideo(song.getVideoId());
        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                isFullScreen = b;
                seek = playVideo.getCurrentTimeMillis();
                Log.e("seek", String.valueOf(seek));
                playVideo.seekToMillis(seek);
                playVideo.play();
            }
        });
    }

    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }

    @Override
    public void onBackPressed() {
        if (isFullScreen) {
            playVideo.setFullscreen(false);
            playVideo.seekToMillis(playVideo.getCurrentTimeMillis());
            Log.e("seek back", String.valueOf(playVideo.getCurrentTimeMillis()));
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        playVideo.cueVideo(songs.get(i).getVideoId());
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMic: {
                Toast.makeText(VideoYouTube.this, "click", Toast.LENGTH_SHORT).show();
                Thread thread = new Thread(new Runnable() {
                    @Override
                    public void run() {
                        Recoder recoder = new Recoder();
                        recoder.startRecoder();
                    }
                });
                thread.start();
                break;
            }
            case R.id.btnEqualizer: {

                break;
            }
        }
    }

    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO};
    private static final int REQUEST_RECORD_AUDIO_PERMISSION = 200;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        switch (requestCode) {
            case REQUEST_RECORD_AUDIO_PERMISSION:
                permissionToRecordAccepted = grantResults[0] == PackageManager.PERMISSION_GRANTED;
                break;
        }
        if (!permissionToRecordAccepted) finish();

    }
}
