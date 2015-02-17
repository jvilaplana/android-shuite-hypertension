package com.android.bpcontrol.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.bpcontrol.R;
import com.android.bpcontrol.adapters.YoutubeVideosRecyclerViewAdapter;
import com.android.bpcontrol.customviews.YoutubeVideosRecyclerView;
import com.android.bpcontrol.model.YoutubeLink;
import com.google.android.youtube.player.YouTubePlayerSupportFragment;

import java.util.ArrayList;

/**
 * Created by Adrian on 17/02/2015.
 */
public class VideosFragment extends YouTubePlayerSupportFragment {

    private RecyclerView recyclerView;

    public static VideosFragment newInstance() {
        VideosFragment videosFragment = new VideosFragment();
        return videosFragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View view = inflater.inflate(R.layout.videosyoutubefragment, null);
        recyclerView = (YoutubeVideosRecyclerView)view.findViewById(R.id.listrecycler);
        return view;

    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ArrayList<YoutubeLink> list = new ArrayList<>();
        list.add(new YoutubeLink("nCgQDjiotG0"));
        list.add(new YoutubeLink("nCgQDjiotG0"));
        list.add(new YoutubeLink("nCgQDjiotG0"));

        recyclerView.setHasFixedSize(false);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        linearLayoutManager.setOrientation(LinearLayoutManager.VERTICAL);
        recyclerView.setLayoutManager(linearLayoutManager);

        YoutubeVideosRecyclerViewAdapter adapter = new YoutubeVideosRecyclerViewAdapter();
        adapter.setContext(getActivity());
        adapter.setVideos(list);

        recyclerView.setAdapter(adapter);


    }
}
