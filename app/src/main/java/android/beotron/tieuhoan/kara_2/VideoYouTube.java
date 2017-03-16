package android.beotron.tieuhoan.kara_2;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

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
    private Button btnRecoder, btnMic, btnEqualizer;
    private RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_you_tube);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        playVideo();
        setUpButton();
        setUpRecycle();
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
        youTubePlayerView.initialize(HangSo.APP_KEY, VideoYouTube.this);

    }

    public void setUpRecycle() {
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(VideoYouTube.this, LinearLayoutManager.VERTICAL, false);
        HomeAdapter adapte = new HomeAdapter(songs, VideoYouTube.this);
        recyclerView = (RecyclerView) findViewById(R.id.listView);
        recyclerView.setAdapter(adapte);
        adapte.setOnItemClickRecycle(new HomeAdapter.OnItemClickRecycle() {
            @Override
            public void OnItemClick(View view, int position) {
                playVideo.loadVideo(songs.get(position).getVideoId());
            }
        });
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    int seek;

    @Override
    public void onInitializationSuccess(YouTubePlayer.Provider provider, YouTubePlayer youTubePlayer, boolean b) {
        playVideo = youTubePlayer;
        youTubePlayer.setShowFullscreenButton(true);
        youTubePlayer.loadVideo(song.getVideoId());
        youTubePlayer.play();
//        youTubePlayer.setPlayerStyle(YouTubePlayer.PlayerStyle.CHROMELESS);
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);

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
        playVideo.loadVideo(songs.get(i).getVideoId());
    }

    boolean isRecoder;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMic: {

                Recoder recoder = new Recoder(isRecoder);

                if (isRecoder == true) {
                    isRecoder = false;
                    recoder.setCheckRecord(isRecoder);
                    Toast.makeText(VideoYouTube.this, "Dừng", Toast.LENGTH_SHORT).show();
                    btnMic.setText("Mic");
                } else {
                    isRecoder = true;
                    recoder.setCheckRecord(isRecoder);
                    recoder.startRecoder();
                    Toast.makeText(VideoYouTube.this, "Bắt đầu", Toast.LENGTH_SHORT).show();
                    btnMic.setText("");

                }

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
