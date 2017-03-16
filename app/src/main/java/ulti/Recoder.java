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

public class Recoder {

    private int bufferSizeTrack;
    private int bufferSizeRecord;
    private AudioRecord record;
    private AudioTrack track;
    private boolean checkRecord;
    private byte[] audioData;

    public Recoder(boolean checkRecord) {
        this.checkRecord = checkRecord;
    }

    public boolean getCheckRecord() {
        return checkRecord;
    }

    public void setCheckRecord(boolean checkRecord) {
        this.checkRecord = checkRecord;
    }

    public void startRecoder() {

        bufferSizeRecord = AudioRecord.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING);
//            bufferSizeTrack = AudioTrack.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING);

        record = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING, bufferSizeRecord);
        track = new AudioTrack(AudioManager.STREAM_MUSIC, HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING, bufferSizeRecord, AudioTrack.MODE_STREAM);

        record.startRecording();
        track.play();
        setCheckRecord(true);


        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                audioData = new byte[bufferSizeRecord];
                while (getCheckRecord()) {
                    record.read(audioData, 0, bufferSizeRecord);
                    track.write(audioData, 0, bufferSizeRecord);
                }
            }
        });
        thread.start();
    }

    private static int[] mSampleRates = new int[]{8000, 11025, 22050, 44100};

}
