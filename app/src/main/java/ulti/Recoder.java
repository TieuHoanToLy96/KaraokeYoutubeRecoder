package ulti;

import android.app.Activity;
import android.beotron.tieuhoan.kara_2.R;
import android.beotron.tieuhoan.kara_2.VideoYouTube;
import android.content.Context;
import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.media.audiofx.Equalizer;
import android.media.audiofx.NoiseSuppressor;
import android.os.Build;
import android.util.Log;
import android.widget.ListView;

import java.util.ArrayList;

import adapter.AdapterChinhAm;
import model.ChinhAm;

/**
 * Created by TieuHoan on 24/02/2017.
 */

public class Recoder implements Runnable {

    private int bufferSizeTrack;
    private int bufferSizeRecord;

    public AudioRecord record;
    public AudioTrack track;
    private boolean checkRecord;
    private byte[] audioData;

    public Equalizer getEqualizer() {
        return equalizer;
    }

    private Equalizer equalizer;

    private Context context;
    private short lowerBand, upperBand;


    private ArrayList<ChinhAm> chinhAms;


    public ArrayList<ChinhAm> getChinhAms() {
        return chinhAms;
    }

    public Recoder(Context context) {
        this.context = context;
        chinhAms = new ArrayList<>();

    }

    public void setCheckRecord(boolean checkRecord) {
        this.checkRecord = checkRecord;
    }

    public void startRecoder() {

        bufferSizeRecord = AudioRecord.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING);
        record = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING, bufferSizeRecord);
        track = new AudioTrack(AudioManager.STREAM_MUSIC, HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING, bufferSizeRecord, AudioTrack.MODE_STREAM);

//        NoiseSuppressor noiseSuppressor = NoiseSuppressor.create(record.getAudioSessionId());
//        noiseSuppressor.setEnabled(true);


//            bufferSizeTrack = AudioTrack.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING);

        record.startRecording();
        track.play();
        checkRecord = true;
        audioData = new byte[bufferSizeRecord];
        while (checkRecord) {
            record.read(audioData, 0, bufferSizeRecord);
            track.write(audioData, 0, bufferSizeRecord);
        }

    }

    public void equalizer() {


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {

            //tạo 1 cái equalizer để
            //lấy được các dải tần số của 1 audio track thông qua ID của track đó
            equalizer = new Equalizer(0, track.getAudioSessionId());
            equalizer.setEnabled(true);

            //Lấy cường độ lớn nhất và nhở nhất của equalizer
            //đọc docucment thấy trả về 1 mảng 2 phần tử
            //phần tử thứ nhất là min , phân tử thứ 2 là max
            lowerBand = equalizer.getBandLevelRange()[0];
            upperBand = equalizer.getBandLevelRange()[1];


            //tạo adapter để tạo các dải tần số
            chinhAms.clear();

            //tạo 1 mảng chỉnh âm đổ vào listView
            //hiện tất cả các seekbar lên listView
            //equalizer.getNumberOfBands() lấy ra số dải tần số , ở đây là 5 dải
            for (int i = 0; i < equalizer.getNumberOfBands(); i++) {
                ChinhAm chinhAm = new ChinhAm();
                chinhAm.setMax(upperBand - lowerBand);
                chinhAm.setProcess((upperBand - lowerBand) / 2);
                chinhAm.setLower(lowerBand);
//                    chinhAm.setUpper((equalizer.getPresetName((short) i)));
                chinhAms.add(chinhAm);
            }


        }
    }

//    private static int[] mSampleRates = new int[]{8000, 11025, 22050, 44100};

    @Override
    public void run() {
        startRecoder();
    }
}
