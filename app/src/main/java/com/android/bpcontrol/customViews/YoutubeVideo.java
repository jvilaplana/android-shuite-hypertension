package com.android.bpcontrol.customviews;

/**
 * Created by Adrian on 18/02/2015.
 */
public class YoutubeVideo {

    private final String text;
    private final String videoId;

    public YoutubeVideo(String text, String videoId) {
        this.text = text;
        this.videoId = videoId;
    }

    public String getVideoId() {
        return videoId;
    }

    public String getText() {
        return text;
    }
}
