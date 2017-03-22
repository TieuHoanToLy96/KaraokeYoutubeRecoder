package android.beotron.tieuhoan.kara_2;

import android.Manifest;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.media.AudioManager;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.ArrayList;

import adapter.AdapterChinhAm;
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

    private ListView listView;
    private AudioManager audioManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_you_tube);


        popupWindow = new PopupWindow(VideoYouTube.this);

        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        playVideo();
        setUpButton();
        setUpRecycle();
    }

    public void setUpButton() {

        btnMic = (Button) findViewById(R.id.btnMic);
        btnEqualizer = (Button) findViewById(R.id.btnEqualizer);
        btnRecoder = (Button) findViewById(R.id.btnRecoder);
        btnRecoder.setOnClickListener(this);
        btnEqualizer.setOnClickListener(this);
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
        youTubePlayer.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CONTROL_ORIENTATION
                | YouTubePlayer.FULLSCREEN_FLAG_CONTROL_SYSTEM_UI
                | YouTubePlayer.FULLSCREEN_FLAG_ALWAYS_FULLSCREEN_IN_LANDSCAPE
                | YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);

        youTubePlayer.setOnFullscreenListener(new YouTubePlayer.OnFullscreenListener() {
            @Override
            public void onFullscreen(boolean b) {
                isFullScreen = b;
                seek = playVideo.getCurrentTimeMillis();
                Log.e("seek", String.valueOf(seek));
                playVideo.seekToMillis(seek);

            }
        });
    }


    @Override
    public void onInitializationFailure(YouTubePlayer.Provider provider, YouTubeInitializationResult youTubeInitializationResult) {

    }


    @Override
    public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
        playVideo.loadVideo(songs.get(i).getVideoId());
    }

    boolean isRecoder;
    Recoder recoder = new Recoder(VideoYouTube.this);
    Thread thread;
    private PopupWindow popupWindow;

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btnMic: {
                if (audioManager.isWiredHeadsetOn()) {
                    if (isRecoder) {
                        isRecoder = false;
                        Toast.makeText(VideoYouTube.this, "Dừng", Toast.LENGTH_SHORT).show();
                        btnMic.setText("Mic");
                        btnMic.setBackgroundResource(R.drawable.btnmic_not_active);
                        recoder.setCheckRecord(isRecoder);
                        recoder.record.release();
                        recoder.track.release();
                        thread.interrupt();
                    } else {
                        isRecoder = true;
                        Toast.makeText(VideoYouTube.this, "Bắt đầu", Toast.LENGTH_SHORT).show();
                        btnMic.setText("Pause");
                        btnMic.setBackgroundResource(R.drawable.btnmic_is_active);
                        thread = new Thread(recoder);
                        thread.start();
                    }
                } else {
                    Dialog dialog = new Dialog(VideoYouTube.this);
                    dialog.setTitle("Kết nối tai nghe hoặc loa bluetooth");
                    dialog.show();
                }

                break;
            }
            case R.id.btnEqualizer: {
                if (isRecoder) {
                    Dialog dialog = new Dialog(VideoYouTube.this);
                    recoder.equalizer();
                    AdapterChinhAm adapterChinhAm = new AdapterChinhAm(VideoYouTube.this, recoder.getChinhAms(), recoder.getEqualizer());
                    dialog.setContentView(R.layout.dialog_chinh_am);
                    WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
                    lp.height = 900;
                    dialog.getWindow().setAttributes(lp);
                    listView = (ListView) dialog.findViewById(R.id.listViewFre);
                    listView.setAdapter(adapterChinhAm);
                    dialog.show();

                }


                break;
            }
            case R.id.btnRecoder: {
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

    @Override
    public void onBackPressed() {
        if (isFullScreen) {
            playVideo.setFullscreen(false);
            playVideo.seekToMillis(playVideo.getCurrentTimeMillis());
            Log.e("seek back", String.valueOf(playVideo.getCurrentTimeMillis()));
        } else {
            recoder.setCheckRecord(false);
            super.onBackPressed();
        }
    }
}
