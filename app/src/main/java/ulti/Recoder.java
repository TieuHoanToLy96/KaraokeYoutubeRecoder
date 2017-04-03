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
import android.os.Environment;
import android.util.Log;
import android.widget.ListView;

import java.io.File;
import java.io.FilenameFilter;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import adapter.AdapterChinhAm;
import model.ChinhAm;
import model.RecoderFile;
import model.Song;

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

    public Recoder() {
    }

    public Recoder(Context context) {
        this.context = context;
        chinhAms = new ArrayList<>();

    }

    public ArrayList<RecoderFile> getAllRecoder() {
        File home = new File(VideoYouTube.PATH_FOLDER);
        ArrayList<RecoderFile> recoderFiles = new ArrayList<>();
        if (home.listFiles(new FileFilter()).length > 0) {
            for (File file : home.listFiles(new FileFilter())) {
                RecoderFile recoderFile = new RecoderFile();
                Date date = new Date(file.lastModified());
                SimpleDateFormat df = new SimpleDateFormat("dd-MMM-yyyy");
                String formattedDate = df.format(date);

                recoderFile.setNameRecoderFile(file.getName().substring(0, (file.getName().length() - 4)));
                recoderFile.setDateRecoderFile(formattedDate);
                recoderFile.setPathRecoderFile(file.getPath());

                // Adding each song to SongList
                recoderFiles.add(recoderFile);
            }
        }

        return recoderFiles;
    }

    public class FileFilter implements FilenameFilter {
        @Override
        public boolean accept(File dir, String name) {
            return (name.endsWith(".mp3") || name.endsWith(".MP3"));
        }

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
