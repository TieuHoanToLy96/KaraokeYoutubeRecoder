package ulti;

import android.beotron.tieuhoan.kara_2.R;
import android.media.AudioFormat;

/**
 * Created by TieuHoan on 23/02/2017.
 */

public class HangSo {
    public static String API_URI = "https://www.googleapis.com/youtube/v3/playlistItems?part=snippet";
    public static String API_URI2 = " https://www.googleapis.com/youtube/v3/search?part=snippet";
    public static String PLAYLIST_ID = "PL75PeaMqYS56hHy9Obnj2JlQmYy-vgxox";
    public static String KEY_BROWSE = "AIzaSyBT_FE1lBGjxNlbVNfL1y1l660jQEFI0y0";
    public static int KEY_HANDLER = 1, KEY_HANDLER2 = 1;
    public static String APP_KEY = "AIzaSyB_iZfaci4AdsEDaY_N-hlsw1h6w_Xrz_o";
    public static final int SAMPLERATE = 44100;
    public static final int ENCODING = AudioFormat.ENCODING_PCM_16BIT;

    public static String cutDate(String date) {
        return date.substring(0, 9);
    }

    public static int[] imageResources = new int[]{
            R.mipmap.heart,
            R.mipmap.home,
            R.mipmap.share
    };

}
