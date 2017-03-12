package ulti;

import android.app.Dialog;
import android.beotron.tieuhoan.kara_2.R;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.AsyncTask;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

import java.util.ArrayList;

import model.Song;

import static ulti.Json.Load.songs;
import static ulti.Json.Load.stringJson;
import static ulti.ReaderJson.getJsonObjectPlayList;


/**
 * Created by TieuHoan on 23/02/2017.
 */
public class Json {
    public static class Load extends AsyncTask<String, Void, ArrayList<Song>> {

        private Context context;
        private Dialog dialog;
        public static String stringJson;
        public static ArrayList<Song> songs;
        private Handler handler;

        public Load(Handler handler, Context context) {
            this.handler = handler;
            this.context = context;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            dialog = new Dialog(context);
            dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
            dialog.setContentView(R.layout.dialog_process);
            dialog.show();
        }

        @Override
        public ArrayList<Song> doInBackground(String... params) {
            songs = new ArrayList<>();
            stringJson = ReaderJson.readerJson(params[0]);
            songs = getJsonObjectPlayList(stringJson);
            return songs;
        }

        @Override
        public void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);
            Log.e("songs size", String.valueOf(songs.size()));
            dialog.dismiss();
            Message message = new Message();
            message.what = HangSo.KEY_HANDLER;
            message.obj = songs;
            handler.sendMessage(message);
        }
    }

    public static class LoadAdd extends AsyncTask<Void, Void, ArrayList<Song>> {

        private Handler handler;
        private ListView listView;
        private Context context;
        private View view;

        public LoadAdd(Handler handler, ListView listView, Context context) {
            this.handler = handler;
            this.listView = listView;
            this.context = context;
        }


        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.dialog_process, null);
            listView.addFooterView(view);
        }

        @Override
        protected ArrayList<Song> doInBackground(Void... voids) {


            songs.clear();
            if (ReaderJson.nextPageToken != "") {
                String path = HangSo.API_URI + "&maxResults=10" + "&pageToken=" + ReaderJson.nextPageToken + "&playlistId=" + HangSo.PLAYLIST_ID + "&key=" + HangSo.KEY_BROWSE;
                stringJson = ReaderJson.readerJson(path);
                songs.addAll(ReaderJson.getJsonObjectPlayList(stringJson));
            }
            return songs;
        }

        @Override
        protected void onPostExecute(ArrayList<Song> songs) {
            super.onPostExecute(songs);
            Message message = new Message();
            message.what = HangSo.KEY_HANDLER2;
            message.obj = songs;
            handler.sendMessage(message);
            listView.removeFooterView(view);
        }
    }

}
