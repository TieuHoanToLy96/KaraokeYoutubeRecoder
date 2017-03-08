package model;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * Created by TieuHoan on 25/02/2017.
 */

public class Singer implements Serializable{

    private ArrayList<Song> songs;
    private String nameSinger;
    private String imageSinger;
    public Singer() {
    }

    public Singer(ArrayList<Song> songs, String nameSinger, String imageSinger) {
        this.songs = songs;
        this.nameSinger = nameSinger;
        this.imageSinger = imageSinger;
    }

    public ArrayList<Song> getSongs() {
        return songs;
    }

    public void setSongs(ArrayList<Song> songs) {
        this.songs = songs;
    }

    public String getImageSinger() {
        return imageSinger;
    }

    public void setImageSinger(String imageSinger) {
        this.imageSinger = imageSinger;
    }

    public String getNameSinger() {
        return nameSinger;
    }

    public void setNameSinger(String nameSinger) {
        this.nameSinger = nameSinger;
    }
}
