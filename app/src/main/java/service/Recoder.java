package service;

import android.media.AudioFormat;
import android.media.AudioManager;
import android.media.AudioRecord;
import android.media.AudioTrack;
import android.media.MediaRecorder;

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

    public void startRecoder() {
        bufferSizeRecord = AudioRecord.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING);
        bufferSizeTrack = AudioTrack.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING);

        record = new AudioRecord(MediaRecorder.AudioSource.VOICE_CALL, HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING, bufferSizeRecord);
        track = new AudioTrack(AudioManager.STREAM_MUSIC, HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING, bufferSizeTrack, AudioTrack.MODE_STREAM);

        record.startRecording();
        track.play();
        checkRecord = true;
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                playStream();
            }
        });
        thread.start();

    }

    public boolean isRecoding() {
        return checkRecord;
    }

    public void playStream() {
        audioData = new byte[bufferSizeRecord];
        while (isRecoding()) {
            track.write(audioData, 0, bufferSizeTrack);
            record.read(audioData, 0, bufferSizeRecord);
        }

    }

}
