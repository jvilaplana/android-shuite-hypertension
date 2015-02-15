package com.android.bpcontrol.model;

/**
 * Created by Adrian on 15/02/2015.
 */
public class YoutubeLink {

    private int id;
    private String url;


    public YoutubeLink(String url){
        this.url = url;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }
}
