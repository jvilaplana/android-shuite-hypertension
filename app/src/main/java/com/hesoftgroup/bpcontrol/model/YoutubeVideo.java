package com.hesoftgroup.bpcontrol.model;

/**
 * Created by Adrian on 18/02/2015.
 */
public class YoutubeVideo {

    private int id = -1;
    private final String text;
    private final String videoId;

    public YoutubeVideo(String text, String videoId) {
        this.text = text;
        this.videoId = videoId;
    }

    public void setId(int id){
        this.id = id;
    }
    public int getID(){
        return id;
    }
    public String getVideoId() {
        return videoId;
    }

    public String getText() {
        return text;
    }
}
