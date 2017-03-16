package ulti;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;
import android.util.Log;

/**
 * Created by TieuHoan on 24/02/2017.
 */

public class Recoder implements Runnable{

    private int bufferSizeTrack;
    private int bufferSizeRecord;

    public AudioRecord record;
    private AudioTrack track;
    private boolean checkRecord;
    private byte[] audioData;


    public boolean getCheckRecord() {
        return checkRecord;
    }

    public void setCheckRecord(boolean checkRecord) {
        this.checkRecord = checkRecord;
    }

    public void startRecoder() {
        System.out.println("00000000");
        bufferSizeRecord = AudioRecord.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING);
//            bufferSizeTrack = AudioTrack.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING);

        record = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING, bufferSizeRecord);
        track = new AudioTrack(AudioManager.STREAM_MUSIC, HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING, bufferSizeRecord, AudioTrack.MODE_STREAM);

        record.startRecording();
        track.play();
        checkRecord = true;
        System.out.println("11111");
        audioData = new byte[bufferSizeRecord];
        while (checkRecord) {
            System.out.println("zzzzz");;
            record.read(audioData, 0, bufferSizeRecord);
            track.write(audioData, 0, bufferSizeRecord);
        }

    }



    private static int[] mSampleRates = new int[]{8000, 11025, 22050, 44100};

    @Override
    public void run() {
        startRecoder();
    }
}
