package com.android.bpcontrol.adapters;

import android.app.Activity;
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bpcontrol.HomeActivity;
import com.android.bpcontrol.youtube.GoogleDeveloperKey;
import com.google.android.youtube.player.YouTubePlayer;

import com.android.bpcontrol.R;
import com.android.bpcontrol.model.YoutubeLink;
import com.google.android.youtube.player.YouTubePlayerFragment;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;
import com.google.android.youtube.player.YouTubePlayerView;

import java.util.List;
import java.util.zip.Inflater;

/**
 * Created by Adrian on 17/02/2015.
 */
public class YoutubeVideosRecyclerViewAdapter{


//    private List<YoutubeLink> videos;
//    private Context context;
//    private VideosFragment fragment;
//    private String videoSelected;
//
//    @Override
//    public ViewHolder onCreateViewHolder(ViewGroup viewGroup, int i) {
//        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        View view = inflater.inflate(R.layout.videosyoutubefragment,null);
//        return new ViewHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(ViewHolder viewHolder, int i) {
//
//        viewHolder.player.setTag(videos.get(i));
//        viewHolder.cell.setTag(viewHolder.player);
//        viewHolder.cell.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//                YouTubePlayerView ypv = (YouTubePlayerView)v;
//                videoSelected = (String)ypv.getTag();
//                ypv.initialize(GoogleDeveloperKey.YOUTUBE_API_V3,fragment);
//
//            }
//        });
//
//    }
//
//    @Override
//    public int getItemCount() {
//        return videos.size();
//    }
//
//    public static class ViewHolder extends RecyclerView.ViewHolder{
//
//        YouTubePlayerView player;
//        View cell;
//        public ViewHolder(View itemView) {
//            super(itemView);
//            player = (YouTubePlayerView) itemView.findViewById(R.id.player);
//            cell = itemView;
//        }
//    }
//
//    public void setVideos(List<YoutubeLink> videos){
//
//        this.videos = videos;
//    }
//
//    public void setContext(HomeActivity context){
//        this.context = context;
//    }
//
//    public void setListener(VideosFragment fragment){
//
//        this.fragment = fragment;
//    }
//
//    public String getVideoSelected(){
//
//      return videoSelected;
//    }
}
