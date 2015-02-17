package com.android.bpcontrol.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bpcontrol.R;
import com.android.bpcontrol.model.YoutubeLink;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;

/**
 * Created by Adrian on 17/02/2015.
 */
public class YoutubeVideosRecyclerViewAdapter
                    extends RecyclerView.Adapter<YoutubeVideosRecyclerViewAdapter.ViewHolder>{

    private List<YoutubeLink> videos;
    private Context context;
    @Override
    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
        LayoutInflater inflate = LayoutInflater.from(context);
        View view = inflate.inflate(R.layout.youtubevideoscell, null);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(ViewHolder viewHolder, int i) {

        viewHolder.player.setTag(videos.get(i));
    }

    @Override
    public int getItemCount() {
        return videos.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        YouTubePlayerView player;
        public ViewHolder(View itemView) {
            super(itemView);
            player = (YouTubePlayerView) itemView.findViewById(R.id.player);
        }
    }

    public void setVideos(List<YoutubeLink> videos){

        this.videos = videos;
    }

    public void setContext(Context context){
        this.context = context;
    }
}
