package android.beotron.tieuhoan.kara_2;

import android.Manifest;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;

import android.graphics.Color;
import android.media.AudioManager;
import android.media.MediaRecorder;
import android.os.Bundle;
import android.os.Environment;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.youtube.player.YouTubeBaseActivity;
import com.google.android.youtube.player.YouTubeInitializationResult;
import com.google.android.youtube.player.YouTubePlayer;
import com.google.android.youtube.player.YouTubePlayerView;

import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import adapter.AdapterChinhAm;
import adapter.HomeAdapter;
import model.RecoderFile;
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
    private RecyclerView recyclerView;
    private AudioManager audioManager;

    boolean isMic;
    Recoder recoder = new Recoder(VideoYouTube.this);
    Thread thread;
    private ListView listView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.video_you_tube);

        ActivityCompat.requestPermissions(this, permissions, REQUEST_RECORD_AUDIO_PERMISSION);
        audioManager = (AudioManager) getSystemService(Context.AUDIO_SERVICE);
        playVideo();
        setUpRecycle();
    }


    public void playVideo() {
        Intent intent = getIntent();
        Bundle bundle = intent.getExtras();

        if (bundle != null) {
            song = (Song) bundle.getSerializable("SONG");
            songs = (ArrayList<Song>) bundle.getSerializable("SONGS");
            Log.e("video", song.getTittle());
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
                playVideo.loadVideo(songs.get(position - 1).getVideoId());
            }
        });
        adapte.setClickButton(new HomeAdapter.OnClickButton() {
            @Override
            public void OnClickBtnMic(Button btnMic) {
                eventBtnMic(btnMic);
            }

            @Override
            public void OnClickBtnRecoder(Button btnRecoder) {
                eventBtnRecoder(btnRecoder);
            }

            @Override
            public void OnClickBtnEqualizer(Button btnEqualizer) {
                eventBtnEqualizer();
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
        playVideo.loadVideo(songs.get(i - 1).getVideoId());
    }


    @Override
    public void onClick(View view) {

    }


    private boolean permissionToRecordAccepted = false;
    private String[] permissions = {Manifest.permission.RECORD_AUDIO, Manifest.permission.WRITE_EXTERNAL_STORAGE};
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

    public void eventBtnMic(Button btnMic) {
//        if (audioManager.isWiredHeadsetOn() || audioManager.isBluetoothA2dpOn()) {
        if (isMic) {
            isMic = false;
            Toast.makeText(this, "Dừng", Toast.LENGTH_SHORT).show();
            btnMic.setText("Mic");
            btnMic.setBackgroundResource(R.drawable.btnmic_not_active);
            recoder.setCheckRecord(isMic);
            recoder.record.release();
            recoder.track.release();
            thread.interrupt();
        } else {
            isMic = true;
            Toast.makeText(this, "Bắt đầu", Toast.LENGTH_SHORT).show();
            audioManager.setMode(AudioManager.MODE_NORMAL);
            btnMic.setText("Pause");
            btnMic.setBackgroundResource(R.drawable.btnmic_is_active);
            thread = new Thread(recoder);
            thread.start();
        }
//    }
//
//    else
//
//    {
//        TextView textViewTitle = new TextView(this);
//        textViewTitle.setText("Chú ý");
//        textViewTitle.setGravity(Gravity.CENTER);
//        textViewTitle.setTextColor(Color.RED);
//        textViewTitle.setTextSize(20);
//        TextView textView = new TextView(this);
//        textView.setText("Kết nối tai nghe hoặc loa bluetooth");
//        textView.setGravity(Gravity.CENTER);
//        textView.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        textViewTitle.setLayoutParams(new RecyclerView.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
//        textView.setTextSize(15);
//
//        AlertDialog.Builder builder = new AlertDialog.Builder(this);
//        builder.setView(textView);
//        builder.setCustomTitle(textViewTitle);
//        builder.show();
//
//
//    }
    }

    public void eventBtnEqualizer() {
        if (isMic) {
            Dialog dialog = new Dialog(this);
            recoder.equalizer();
            AdapterChinhAm adapterChinhAm = new AdapterChinhAm(this, recoder.getChinhAms(), recoder.getEqualizer());
            dialog.setContentView(R.layout.dialog_chinh_am);
            WindowManager.LayoutParams lp = new WindowManager.LayoutParams();
            lp.height = 900;
            dialog.getWindow().setAttributes(lp);
            listView = (ListView) dialog.findViewById(R.id.listViewFre);
            listView.setAdapter(adapterChinhAm);
            dialog.show();

        }
    }

    private boolean isRecoder;


    public void eventBtnRecoder(Button btnRecoder) {
        if (!isRecoder) {
            Toast.makeText(this, "Bắt đầu", Toast.LENGTH_SHORT).show();
            btnRecoder.setText("Pause");
            btnRecoder.setBackgroundResource(R.drawable.btnmic_is_active);
            isRecoder = true;
            recoder(song.getTittle());

        } else if (isRecoder) {
            Toast.makeText(this, "Đã lưu vào bộ nhớ", Toast.LENGTH_SHORT).show();
            btnRecoder.setText("Recoder");
            btnRecoder.setBackgroundResource(R.drawable.btnmic_not_active);
            isRecoder = false;

            try {
                mediaRecorder.stop();
                mediaRecorder.release();

            } catch (RuntimeException e) {
            }

//            for (HashMap hashMap : getAllRecoder()) {
//                Log.e("tieuhoan recoder", hashMap.get("songTitle").toString());
//                Log.e("tieuhoan recoder", hashMap.get("date").toString());
//            }
//
        } else {
            Toast.makeText(this, "Kết nối tai nghe", Toast.LENGTH_SHORT).show();
        }
    }


    private MediaRecorder mediaRecorder;
    String path;


    public static final String FOLDER = "TieuHoan";
    public static final String PATH_FOLDER = Environment.getExternalStorageDirectory() + "/" + FOLDER;

    public void recoder(String tittleRecoder) {
        try {

            File myDirectory = new File(Environment.getExternalStorageDirectory(), FOLDER);
            if (!myDirectory.exists()) {
                myDirectory.mkdirs();
            }

            path = PATH_FOLDER + "/" + tittleRecoder + ".mp3";
            for (int i = 0; i < recoder.getAllRecoder().size(); i++) {
                if (path.equals(recoder.getAllRecoder().get(i).getPathRecoderFile())) {
                    path = PATH_FOLDER + "/" + tittleRecoder + i + ".mp3"                       ;
                    break;
                }
            }

            mediaRecorder = new MediaRecorder();
            mediaRecorder.setAudioSource(MediaRecorder.AudioSource.MIC);
            mediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.THREE_GPP);
            mediaRecorder.setAudioEncoder(MediaRecorder.OutputFormat.AMR_NB);
            mediaRecorder.setOutputFile(path);
            mediaRecorder.prepare();
            mediaRecorder.start();
        } catch (
                IOException e
                )

        {
            e.printStackTrace();
        }

    }
}
