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


    public void startRecoder() {
        try {
//            bufferSizeRecord = AudioRecord.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING);
//            bufferSizeTrack = AudioTrack.getMinBufferSize(HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING);
//
//            record = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, HangSo.SAMPLERATE, AudioFormat.CHANNEL_IN_MONO, HangSo.ENCODING, bufferSizeRecord);
//            track = new AudioTrack(AudioManager.STREAM_MUSIC, HangSo.SAMPLERATE, AudioFormat.CHANNEL_OUT_MONO, HangSo.ENCODING, bufferSizeTrack, AudioTrack.MODE_STREAM);
//
//            record.startRecording();
//            track.play();
//            checkRecord = true;
//
//
//            Thread thread = new Thread(new Runnable() {
//                @Override
//                public void run() {
//                    playStream();
//                }
//            });
//            thread.start();

            record = findAudioRecord();


//            audioData = new short[bufferSizeRecord / 2];
//            while (checkRecord) {
//                int shortsRead = record.read(audioData, 0, bufferSizeRecord / 2);
//                if (shortsRead == AudioRecord.ERROR_BAD_VALUE || shortsRead == AudioRecord.ERROR_INVALID_OPERATION) {
//                    Log.e("record()", "Error reading from microphone.");
//                    checkRecord = false;
//                    break;
//                }
//
//            }
        } finally {
            if (record != null) {
                record.release();
            }
        }


    }


    private static int[] mSampleRates = new int[]{8000, 11025, 22050, 44100};

    public AudioRecord findAudioRecord() {
        AudioRecord recorder = null;
        for (int rate : mSampleRates) {
            for (short audioFormat : new short[]{AudioFormat.ENCODING_PCM_8BIT, AudioFormat.ENCODING_PCM_16BIT}) {
                for (short channelConfig : new short[]{AudioFormat.CHANNEL_IN_MONO, AudioFormat.CHANNEL_IN_STEREO}) {
                    try {
                        Log.w("DEBUG", "Attempting rate " + rate + "Hz, bits: " + audioFormat + ", channel: "
                                + channelConfig);
                        int bufferSize = AudioRecord.getMinBufferSize(rate, channelConfig, audioFormat);

                        if (bufferSize != AudioRecord.ERROR_BAD_VALUE) {
                            // check if we can instantiate and have a success
                            recorder = new AudioRecord(MediaRecorder.AudioSource.VOICE_RECOGNITION, rate, channelConfig, audioFormat, bufferSize);

                            if (recorder.getState() == AudioRecord.STATE_INITIALIZED)
                                return recorder;
                        }
                    } catch (Exception e) {
                        Log.w("DEBUG", rate + "Exception, keep trying.", e);
                    } finally {
                        if (recorder != null) {
                            recorder.stop();
                            recorder.release();
                        }
                    }

                }
            }
        }
        return null;
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
