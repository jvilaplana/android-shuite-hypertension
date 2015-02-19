 package com.android.bpcontrol.fragments;


    import static android.view.ViewGroup.LayoutParams.MATCH_PARENT;
    import static android.view.ViewGroup.LayoutParams.WRAP_CONTENT;

    import com.android.bpcontrol.HomeActivity;
    import com.android.bpcontrol.R;
    import com.android.bpcontrol.customviews.YoutubeVideo;
    import com.android.bpcontrol.youtube.GoogleDeveloperKey;
    import com.google.android.youtube.player.YouTubeApiServiceUtil;
    import com.google.android.youtube.player.YouTubeInitializationResult;
    import com.google.android.youtube.player.YouTubePlayer;
    import com.google.android.youtube.player.YouTubePlayer.OnFullscreenListener;
    import com.google.android.youtube.player.YouTubePlayer.OnInitializedListener;
    import com.google.android.youtube.player.YouTubePlayer.Provider;
    import com.google.android.youtube.player.YouTubePlayerFragment;
    import com.google.android.youtube.player.YouTubePlayerSupportFragment;
    import com.google.android.youtube.player.YouTubeThumbnailLoader;
    import com.google.android.youtube.player.YouTubeThumbnailLoader.ErrorReason;
    import com.google.android.youtube.player.YouTubeThumbnailView;

    import android.animation.Animator;
    import android.animation.AnimatorListenerAdapter;
    import android.annotation.TargetApi;
    import android.app.Activity;
    import android.app.ListFragment;
    import android.content.Context;
    import android.content.Intent;
    import android.content.res.Configuration;
    import android.os.Build;
    import android.os.Bundle;
    import android.support.annotation.Nullable;
    import android.support.v4.app.Fragment;
    import android.view.Gravity;
    import android.view.LayoutInflater;
    import android.view.View;
    import android.view.ViewGroup;
    import android.view.ViewGroup.LayoutParams;
    import android.view.ViewPropertyAnimator;
    import android.widget.BaseAdapter;
    import android.widget.FrameLayout;
    import android.widget.GridView;
    import android.widget.ListView;
    import android.widget.TextView;
    import android.widget.Toast;

    import java.util.ArrayList;
    import java.util.Collections;
    import java.util.HashMap;
    import java.util.List;
    import java.util.Map;

    /**
     * A sample Activity showing how to manage multiple YouTubeThumbnailViews in an adapter for display
     * in a List. When the list items are clicked, the video is played by using a YouTubePlayerFragment.
     * <p>
     * The demo supports custom fullscreen and transitioning between portrait and landscape without
     * rebuffering.
     */
    @TargetApi(13)
    public class YoutubeVideoListFragment extends Fragment  {

//        /** The duration of the animation sliding up the video in portrait. */
//        private static final int ANIMATION_DURATION_MILLIS = 300;
//        /** The padding between the video list and the video in landscape orientation. */
//        private static final int LANDSCAPE_VIDEO_PADDING_DP = 5;
//
//        /** The request code when calling startActivityForResult to recover from an API service error. */
//        private static final int RECOVERY_DIALOG_REQUEST = 1;
//
//        private VideoListFragment listFragment;
//        private VideoFragment videoFragment;
//
//        private View videoBox;
//        private View closeButton;
//
//        private View layout;
//
//        private boolean isFullscreen = false;
//
//        public static YoutubeVideoListFragment newInstance(View layout,VideoFragment videoFragment,VideoListFragment videoListFragment){
//            YoutubeVideoListFragment youtubeVideoListFragment = new YoutubeVideoListFragment()
//                    .setView(layout).addFragments(videoFragment,videoListFragment);
//            return youtubeVideoListFragment;
//        }
//
//        public YoutubeVideoListFragment setView(View layout){
//            this.layout = layout;
//            return this;
//        }
//
//        public YoutubeVideoListFragment addFragments(VideoFragment videoFragment,VideoListFragment videoListFragment){
//            this.listFragment = videoListFragment;
//            this.videoFragment = videoFragment;
//
//            return this;
//        }
//
//        @Override
//        public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//            super.onCreateView(inflater, container, savedInstanceState);
//            FrameLayout wrapper = new FrameLayout(getActivity());
//            View view = null;
//
//            if (layout==null) {
//                view = ((HomeActivity)getActivity()).getYoutubeView();
//
//            }else{
//                view = layout;
//            }
//            videoBox = view.findViewById(R.id.video_box);
//            closeButton = view.findViewById(R.id.close_button);
//            videoBox.setVisibility(View.INVISIBLE);
//            return view;
//
//        }
//
//        @Override
//        public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
//            super.onViewCreated(view, savedInstanceState);
//
//            layout(isFullscreen);
//
//            checkYouTubeApi();
//        }
//
//        private void checkYouTubeApi() {
//            YouTubeInitializationResult errorReason =
//                    YouTubeApiServiceUtil.isYouTubeApiServiceAvailable(getActivity());
//            if (errorReason.isUserRecoverableError()) {
//                errorReason.getErrorDialog(getActivity(), RECOVERY_DIALOG_REQUEST).show();
//            } else if (errorReason != YouTubeInitializationResult.SUCCESS) {
//                String errorMessage =
//                        String.format("Error", errorReason.toString());
//
//            }
//        }
//
////        @Override
////        public void onActivityResult(int requestCode, int resultCode, Intent data) {
////            if (requestCode == RECOVERY_DIALOG_REQUEST) {
////                // Recreate the activity if user performed a recovery action
////                recreate();
////            }
////        }
//
//        @Override
//        public void onConfigurationChanged(Configuration newConfig) {
//            super.onConfigurationChanged(newConfig);
//
//            layout(isFullscreen);
//        }
//
//        /**
//         * Sets up the layout programatically for the three different states. Portrait, landscape or
//         * fullscreen+landscape. This has to be done programmatically because we handle the orientation
//         * changes ourselves in order to get fluent fullscreen transitions, so the xml layout resources
//         * do not get reloaded.
//         */
//        public void layout(boolean isFullscreen) {
//            this.isFullscreen = isFullscreen;
//            boolean isPortrait =
//                    getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT;
//
//            listFragment.getView().setVisibility(isFullscreen ? View.GONE : View.VISIBLE);
//            listFragment.setLabelVisibility(isPortrait);
//            closeButton.setVisibility(isPortrait ? View.VISIBLE : View.GONE);
//
//            if (isFullscreen) {
//                videoBox.setTranslationY(0); // Reset any translation that was applied in portrait.
//                setLayoutSize(videoFragment.getView(), MATCH_PARENT, MATCH_PARENT);
//                setLayoutSizeAndGravity(videoBox, MATCH_PARENT, MATCH_PARENT, Gravity.TOP | Gravity.LEFT);
//            } else if (isPortrait) {
//                setLayoutSize(listFragment.getView(), MATCH_PARENT, MATCH_PARENT);
//                setLayoutSize(videoFragment.getView(), MATCH_PARENT, WRAP_CONTENT);
//                setLayoutSizeAndGravity(videoBox, MATCH_PARENT, WRAP_CONTENT, Gravity.BOTTOM);
//            } else {
//                videoBox.setTranslationY(0); // Reset any translation that was applied in portrait.
//                int screenWidth = dpToPx(getResources().getConfiguration().screenWidthDp);
//                setLayoutSize(listFragment.getView(), screenWidth / 4, MATCH_PARENT);
//                int videoWidth = screenWidth - screenWidth / 4 - dpToPx(LANDSCAPE_VIDEO_PADDING_DP);
//                setLayoutSize(videoFragment.getView(), videoWidth, WRAP_CONTENT);
//                setLayoutSizeAndGravity(videoBox, videoWidth, WRAP_CONTENT,
//                        Gravity.RIGHT | Gravity.CENTER_VERTICAL);
//            }
//        }
//
//        public void onClickClose(@SuppressWarnings("unused") View view) {
//            listFragment.getListView().clearChoices();
//            listFragment.getListView().requestLayout();
//            videoFragment.pause();
//            ViewPropertyAnimator animator = videoBox.animate()
//                    .translationYBy(videoBox.getHeight())
//                    .setDuration(ANIMATION_DURATION_MILLIS);
//            runOnAnimationEnd(animator, new Runnable() {
//                @Override
//                public void run() {
//                    videoBox.setVisibility(View.INVISIBLE);
//                }
//            });
//        }
//
//        @TargetApi(16)
//        private void runOnAnimationEnd(ViewPropertyAnimator animator, final Runnable runnable) {
//            if (Build.VERSION.SDK_INT >= 16) {
//                animator.withEndAction(runnable);
//            } else {
//                animator.setListener(new AnimatorListenerAdapter() {
//                    @Override
//                    public void onAnimationEnd(Animator animation) {
//                        runnable.run();
//                    }
//                });
//            }
//        }
//
//        /**
//         * A fragment that shows a static list of videos.
//         */
//        public static final class VideoListFragment extends android.support.v4.app.ListFragment {
//
//            private static final List<VideoEntry> VIDEO_LIST;
//            static {
//                List<VideoEntry> list = new ArrayList<VideoEntry>();
//                list.add(new VideoEntry("YouTube Collection", "Y_UmWdcTrrc"));
//                list.add(new VideoEntry("GMail Tap", "1KhZKNZO8mQ"));
//                list.add(new VideoEntry("Chrome Multitask", "UiLSiqyDf4Y"));
//                list.add(new VideoEntry("Google Fiber", "re0VRK6ouwI"));
//                list.add(new VideoEntry("Autocompleter", "blB_X38YSxQ"));
//                list.add(new VideoEntry("GMail Motion", "Bu927_ul_X0"));
//                list.add(new VideoEntry("Translate for Animals", "3I24bSteJpw"));
//                VIDEO_LIST = Collections.unmodifiableList(list);
//            }
//
//            private PageAdapter adapter;
//            private View videoBox;
//
//            @Override
//            public void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//                adapter = new PageAdapter(getActivity(), VIDEO_LIST);
//            }
//
//            @Override
//            public void onActivityCreated(Bundle savedInstanceState) {
//                super.onActivityCreated(savedInstanceState);
//                getListView().setChoiceMode(ListView.CHOICE_MODE_SINGLE);
//                setListAdapter(adapter);
//            }
//
//            @Override
//            public void onListItemClick(ListView l, View v, int position, long id) {
//                String videoId = VIDEO_LIST.get(position).videoId;
//
//                VideoFragment videoFragment =
//                        (VideoFragment) getActivity().getSupportFragmentManager().findFragmentById(R.id.video_fragment_container);
//                videoFragment.setVideoId(videoId);
//
//                // The videoBox is INVISIBLE if no video was previously selected, so we need to show it now.
//                if (videoBox.getVisibility() != View.VISIBLE) {
//                    if (getResources().getConfiguration().orientation == Configuration.ORIENTATION_PORTRAIT) {
//                        // Initially translate off the screen so that it can be animated in from below.
//                        videoBox.setTranslationY(videoBox.getHeight());
//                    }
//                    videoBox.setVisibility(View.VISIBLE);
//                }
//
//                // If the fragment is off the screen, we animate it in.
//                if (videoBox.getTranslationY() > 0) {
//                    videoBox.animate().translationY(0).setDuration(ANIMATION_DURATION_MILLIS);
//                }
//            }
//
//            @Override
//            public void onDestroyView() {
//                super.onDestroyView();
//
//                adapter.releaseLoaders();
//            }
//
//            public void setLabelVisibility(boolean visible) {
//                adapter.setLabelVisibility(visible);
//            }
//           public void videoBox(View videoBox){
//              this.videoBox = videoBox;
//           }
//
//        }
//
//        /**
//         * Adapter for the video list. Manages a set of YouTubeThumbnailViews, including initializing each
//         * of them only once and keeping track of the loader of each one. When the ListFragment gets
//         * destroyed it releases all the loaders.
//         */
//        private static final class PageAdapter extends BaseAdapter {
//
//            private final List<VideoEntry> entries;
//            private final List<View> entryViews;
//            private final Map<YouTubeThumbnailView, YouTubeThumbnailLoader> thumbnailViewToLoaderMap;
//            private final LayoutInflater inflater;
//            private final ThumbnailListener thumbnailListener;
//
//            private boolean labelsVisible;
//
//            public PageAdapter(Context context, List<VideoEntry> entries) {
//                this.entries = entries;
//
//                entryViews = new ArrayList<View>();
//                thumbnailViewToLoaderMap = new HashMap<YouTubeThumbnailView, YouTubeThumbnailLoader>();
//                inflater = LayoutInflater.from(context);
//                thumbnailListener = new ThumbnailListener();
//
//                labelsVisible = true;
//            }
//
//            public void releaseLoaders() {
//                for (YouTubeThumbnailLoader loader : thumbnailViewToLoaderMap.values()) {
//                    loader.release();
//                }
//            }
//
//            public void setLabelVisibility(boolean visible) {
//                labelsVisible = visible;
//                for (View view : entryViews) {
//                    view.findViewById(R.id.text).setVisibility(visible ? View.VISIBLE : View.GONE);
//                }
//            }
//
//            @Override
//            public int getCount() {
//                return entries.size();
//            }
//
//            @Override
//            public VideoEntry getItem(int position) {
//                return entries.get(position);
//            }
//
//            @Override
//            public long getItemId(int position) {
//                return 0;
//            }
//
//            @Override
//            public View getView(int position, View convertView, ViewGroup parent) {
//                View view = convertView;
//                VideoEntry entry = entries.get(position);
//
//                // There are three cases here
//                if (view == null) {
//                    // 1) The view has not yet been created - we need to initialize the YouTubeThumbnailView.
//                    view = inflater.inflate(R.layout.video_list_item, parent, false);
//                    YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
//                    thumbnail.setTag(entry.videoId);
//                    thumbnail.initialize(GoogleDeveloperKey.YOUTUBE_API_V3, thumbnailListener);
//                } else {
//                    YouTubeThumbnailView thumbnail = (YouTubeThumbnailView) view.findViewById(R.id.thumbnail);
//                    YouTubeThumbnailLoader loader = thumbnailViewToLoaderMap.get(thumbnail);
//                    if (loader == null) {
//                        // 2) The view is already created, and is currently being initialized. We store the
//                        //    current videoId in the tag.
//                        thumbnail.setTag(entry.videoId);
//                    } else {
//                        // 3) The view is already created and already initialized. Simply set the right videoId
//                        //    on the loader.
//                        thumbnail.setImageResource(R.drawable.loading_thumbnail);
//                        loader.setVideo(entry.videoId);
//                    }
//                }
//                TextView label = ((TextView) view.findViewById(R.id.text));
//                label.setText(entry.text);
//                label.setVisibility(labelsVisible ? View.VISIBLE : View.GONE);
//                return view;
//            }
//
//            private final class ThumbnailListener implements
//                    YouTubeThumbnailView.OnInitializedListener,
//                    YouTubeThumbnailLoader.OnThumbnailLoadedListener {
//
//                @Override
//                public void onInitializationSuccess(
//                        YouTubeThumbnailView view, YouTubeThumbnailLoader loader) {
//                    loader.setOnThumbnailLoadedListener(this);
//                    thumbnailViewToLoaderMap.put(view, loader);
//                    view.setImageResource(R.drawable.loading_thumbnail);
//                    String videoId = (String) view.getTag();
//                    loader.setVideo(videoId);
//                }
//
//                @Override
//                public void onInitializationFailure(
//                        YouTubeThumbnailView view, YouTubeInitializationResult loader) {
//                    view.setImageResource(R.drawable.no_thumbnail);
//                }
//
//                @Override
//                public void onThumbnailLoaded(YouTubeThumbnailView view, String videoId) {
//                }
//
//                @Override
//                public void onThumbnailError(YouTubeThumbnailView view, ErrorReason errorReason) {
//                    view.setImageResource(R.drawable.no_thumbnail);
//                }
//            }
//
//        }
//
//        public static final class VideoFragment extends YouTubePlayerSupportFragment
//                implements OnInitializedListener {
//
//            private YouTubePlayer player;
//            private String videoId;
//            private OnFullscreenListener listener;
//
//            public static VideoFragment newInstance() {
//                return new VideoFragment();
//            }
//
//            @Override
//            public void onCreate(Bundle savedInstanceState) {
//                super.onCreate(savedInstanceState);
//
//                initialize(GoogleDeveloperKey.YOUTUBE_API_V3, this);
//            }
//
//            @Override
//            public void onDestroy() {
//                if (player != null) {
//                    player.release();
//                }
//                super.onDestroy();
//            }
//
//            public void setVideoId(String videoId) {
//                if (videoId != null && !videoId.equals(this.videoId)) {
//                    this.videoId = videoId;
//                    if (player != null) {
//                        player.cueVideo(videoId);
//                    }
//                }
//            }
//
//            public void pause() {
//                if (player != null) {
//                    player.pause();
//                }
//            }
//
//            @Override
//            public void onInitializationSuccess(Provider provider, YouTubePlayer player, boolean restored) {
//                this.player = player;
//                player.addFullscreenControlFlag(YouTubePlayer.FULLSCREEN_FLAG_CUSTOM_LAYOUT);
//                player.setOnFullscreenListener((HomeActivity)getActivity());
//                if (!restored && videoId != null) {
//                    player.cueVideo(videoId);
//                }
//            }
//
//            @Override
//            public void onInitializationFailure(Provider provider, YouTubeInitializationResult result) {
//                this.player = null;
//            }
//
//            public void setFullScreenListener (OnFullscreenListener listener){
//
//                this.listener = listener;
//            }
//
//        }
//
//        private static final class VideoEntry {
//            private final String text;
//            private final String videoId;
//
//            public VideoEntry(String text, String videoId) {
//                this.text = text;
//                this.videoId = videoId;
//            }
//        }
//
//        // Utility methods for layouting.
//
//        private int dpToPx(int dp) {
//            return (int) (dp * getResources().getDisplayMetrics().density + 0.5f);
//        }
//
//        private static void setLayoutSize(View view, int width, int height) {
//            LayoutParams params = view.getLayoutParams();
//            params.width = width;
//            params.height = height;
//            view.setLayoutParams(params);
//        }
//
//        private static void setLayoutSizeAndGravity(View view, int width, int height, int gravity) {
//            FrameLayout.LayoutParams params = (FrameLayout.LayoutParams) view.getLayoutParams();
//            params.width = width;
//            params.height = height;
//            params.gravity = gravity;
//            view.setLayoutParams(params);
//        }

    }
