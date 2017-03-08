package service;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;

import model.Song;

/**
 * Created by TieuHoan on 15/02/2017.
 */

public class ReaderJson {

    public static String nextPageToken;


    public static String readerJson(String theUrl) {
        StringBuilder content = new StringBuilder();

        try {
            URL url = new URL(theUrl);
            URLConnection urlConnection = url.openConnection();
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null) {
                content.append(line + "\n");
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return content.toString();
    }



    public static ArrayList<Song> getJsonObjectPlayList(String stringJson) {

        ArrayList<Song> songs = new ArrayList<>();
        String title = null, channelTitle = null, publishedAt = null, url = null;
        JSONObject thumbnails, imageThumb;
        try {
            JSONObject jsonObject = new JSONObject(stringJson);

            // get next page token
            if (jsonObject.has("nextPageToken")) {
                nextPageToken = jsonObject.getString("nextPageToken");
            } else {
                nextPageToken = "";
            }
            JSONArray items = jsonObject.getJSONArray("items");

            for (int i = 0; i < items.length(); i++) {
                JSONObject item = items.getJSONObject(i);
                JSONObject snippet = item.getJSONObject("snippet");

                if (snippet.has("title")) {
                    title = snippet.getString("title");
                }
                if (snippet.has("channelTitle")) {
                    channelTitle = snippet.getString("channelTitle");
                }
                if (snippet.has("publishedAt")) {
                    publishedAt = HangSo.cutDate(snippet.getString("publishedAt"));
                }

                String videoId = "" ;
                if(snippet.has("resourceId")){
                    videoId = snippet.getJSONObject("resourceId").getString("videoId");
                } else if(item.has("id")){
                    videoId = item.getJSONObject("id").getString("videoId");
                }

                if (snippet.has("thumbnails")) {
                    thumbnails = snippet.getJSONObject("thumbnails");
                    imageThumb = thumbnails.getJSONObject("medium");
                    url = imageThumb.getString("url");
                }

                //get song from json
                if (url != null) {
                    Song song = new Song();
                    song.setTittle(title);
                    song.setNameChannel(channelTitle);
                    song.setTimeUpLoad(publishedAt);
                    song.setUrlImageThumb(url);
                    song.setVideoId(videoId);
                    songs.add(song);
                }

            }


        } catch (JSONException e) {
            e.printStackTrace();
        }

        return songs;
    }


}
