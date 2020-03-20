package com.medparliament.Adapter;


import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import androidx.viewpager.widget.PagerAdapter;

import com.medparliament.Activity.Login_Signup_Activity;
import com.medparliament.Activity.NewsDetails_Activity;
import com.medparliament.Activity.Tabs_DashBoard_Activity;
import com.medparliament.Internet.Models.Dashboard_News_Model;
import com.medparliament.Internet.NewModel.NewModel;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Dashboard_news_adapter_new  extends PagerAdapter {

    private Context context;
    ArrayList<NewModel> itemList;
    public Dashboard_news_adapter_new(Context context, ArrayList<NewModel> itemList,boolean flag) {
        this.context = context;
        this.itemList=itemList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        Log.d("aaa","aaaaa");
        final NewModel pm = itemList.get(position);
        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup convertView = (ViewGroup) inflater.inflate(R.layout.dashboardhighlightedlayout, collection, false);
        RelativeLayout rImage,rVideo;
        View layer;
        YouTubePlayerView video;
        rImage=convertView.findViewById(R.id.rimage);
        rVideo=convertView.findViewById(R.id.rvideo);
        video=convertView.findViewById(R.id.video);
        layer=convertView.findViewById(R.id.layer);
        rImage.setVisibility(View.GONE);
        rVideo.setVisibility(View.GONE);
        if(!pm.getImagePath().equalsIgnoreCase(""))
        {
            rVideo.setVisibility(View.GONE);
            rImage.setVisibility(View.VISIBLE);
            Comman.setImageWithCondition(context, (ImageView) convertView.findViewById(R.id.mainimg), pm.getImagePath());}else {
            rImage.setVisibility(View.GONE);
            rVideo.setVisibility(View.VISIBLE);
            video.getPlayerUiController().showYouTubeButton(false);
            video.addYouTubePlayerListener(new YouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(pm.getVideoId(), 0);
                }

                @Override
                public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {

                }

                @Override
                public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {

                }

                @Override
                public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {

                }

                @Override
                public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {

                }

                @Override
                public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

                }

                @Override
                public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

                }

                @Override
                public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

                }
            });


        }
        layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Comman.Check_Login(context)){
                    Intent intent = new Intent(context, Tabs_DashBoard_Activity.class);
                    intent.putExtra("id", "1");
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    context.startActivity(intent);} else {
                    Intent intent = new Intent(context, Login_Signup_Activity.class);


                    context.startActivity(intent);
                }
            }});
        collection.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        final NewModel pm = itemList.get(position);
        return "";
    }


    public void updateList(ArrayList<NewModel> itemList){


        this.itemList=itemList;
        super.notifyDataSetChanged();
    }

}