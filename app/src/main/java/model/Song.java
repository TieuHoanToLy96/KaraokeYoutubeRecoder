package model;

import java.io.Serializable;

/**
 * Created by TieuHoan on 08/02/2017.
 */

public class Song implements Serializable {
    //    private int imageThumb;
    private String tittle;
    private String nameChannel;
    private String timeUpLoad;
    private String urlImageThumb;
    private String videoId;
    private String isFavorite;

    public Song() {
        this.isFavorite = "false";
    }

    public Song(String tittle, String nameChannel, String timeUpLoad, String urlImageThumb, String videoId, String isFavorite) {
        this.tittle = tittle;
        this.nameChannel = nameChannel;
        this.timeUpLoad = timeUpLoad;
        this.urlImageThumb = urlImageThumb;
        this.videoId = videoId;
        this.isFavorite = isFavorite;
    }

    public String getTittle() {
        return tittle;
    }

    public void setTittle(String tittle) {
        this.tittle = tittle;
    }

    public String getNameChannel() {
        return nameChannel;
    }

    public void setNameChannel(String nameChannel) {
        this.nameChannel = nameChannel;
    }

    public String getTimeUpLoad() {
        return timeUpLoad;
    }

    public void setTimeUpLoad(String timeUpLoad) {
        this.timeUpLoad = timeUpLoad;
    }

    public String getUrlImageThumb() {
        return urlImageThumb;
    }

    public void setUrlImageThumb(String urlImageThumb) {
        this.urlImageThumb = urlImageThumb;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getIsFavorite() {
        return isFavorite;
    }

    public void setIsFavorite(String isFavorite) {
        this.isFavorite = isFavorite;
    }
}
