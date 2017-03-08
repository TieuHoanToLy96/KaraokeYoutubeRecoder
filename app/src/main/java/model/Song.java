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
    private boolean isFavorite ;

    public boolean getFavorite() {
        return isFavorite;
    }

    public void setFavorite(boolean favorite) {
        isFavorite = favorite;
    }

    public Song() {
    }

    public Song(String tittle, String videoId, String nameChannel, String timeUpLoad, String urlImageThumb) {
        this.tittle = tittle;
        this.videoId = videoId;
        this.nameChannel = nameChannel;
        this.timeUpLoad = timeUpLoad;
        this.urlImageThumb = urlImageThumb;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
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


}
