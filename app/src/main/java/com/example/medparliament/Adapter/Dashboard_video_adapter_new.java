package com.example.medparliament.Adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.example.medparliament.Activity.DashBoard_Activity;
import com.example.medparliament.Activity.Login_Signup_Activity;
import com.example.medparliament.Internet.Models.Video_Model;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Dashboard_video_adapter_new  extends PagerAdapter {

    private Context context;
    ArrayList<Video_Model> itemList;
    public Dashboard_video_adapter_new(Context context, ArrayList<Video_Model> itemList,boolean flag) {
        this.context = context;
        this.itemList=itemList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        Log.d("aaa","aaaaa");
        final Video_Model pm = itemList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup convertView = (ViewGroup) inflater.inflate(R.layout.video_item_layout, collection, false);



        YouTubePlayerView youTubePlayerView= (YouTubePlayerView) convertView.findViewById(R.id.video);
        youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
        ImageView imageView=convertView.findViewById(R.id.image);
        final View over_lay = convertView.findViewById(R.id.over_lay);
        over_lay.setVisibility(View.GONE);

        if(pm.getVideoId()!=null && pm.getVideoId()!=""){
            Log.d("vie" ,pm.getVideoId());
            if(position>=1 && !Comman.Check_Login(context)) {

                over_lay.setVisibility(View.VISIBLE);
                over_lay.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        over_lay.setVisibility(View.VISIBLE);
                        context.startActivity(new Intent(context, Login_Signup_Activity.class));


                    }
                });
            }
            youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(pm.getVideoId(),0);
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

        else{

            imageView.setVisibility(View.VISIBLE);

            Comman.setRectangleImage(context,imageView,pm.getImagePath());

        }




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
        final Video_Model pm = itemList.get(position);
        return "";
    }


    public void updateList(ArrayList<Video_Model> itemList){

          this.itemList=itemList;
          super.notifyDataSetChanged();
    }

}