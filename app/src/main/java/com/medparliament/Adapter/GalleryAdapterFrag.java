package com.medparliament.Adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.medparliament.Activity.Login_Signup_Activity;
import com.medparliament.Activity.Tabs_DashBoard_Activity;
import com.medparliament.Internet.Models.DashboardGalleryModel;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.stfalcon.frescoimageviewer.ImageViewer;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class GalleryAdapterFrag extends RecyclerView.Adapter<GalleryAdapterFrag.NotificationHolder> {
    ArrayList<DashboardGalleryModel>list;
    Context context;
    int a;
    String userName;
    public GalleryAdapterFrag(Context context, ArrayList<DashboardGalleryModel>arrayList)
    {
        this.context=context;
        this.list=arrayList;
        this.a=a;
    }
    @NonNull
    @Override
    public GalleryAdapterFrag.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.imagelayout,parent,false);
        return new GalleryAdapterFrag.NotificationHolder(view);
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final NotificationHolder holder, final int position) {
        final DashboardGalleryModel pm=list.get(position);
        if(!pm.getImagePath().equalsIgnoreCase("")){
            holder.youTubePlayerView.setVisibility(View.GONE);
            Comman.setRectangleImage(context,holder.imageView,pm.getImagePath());}else {
            holder.imageView.setVisibility(View.GONE);
            holder.youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
            holder.youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
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

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new ImageViewer.Builder<DashboardGalleryModel>(context, list).setStartPosition(position)
                        .setFormatter(new ImageViewer.Formatter<DashboardGalleryModel>() {
                            @Override
                            public String format(DashboardGalleryModel customImage) {
                                return customImage.getImagePath();
                            }
                        })
                        .show();
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class NotificationHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        YouTubePlayerView youTubePlayerView;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.main_img);
            youTubePlayerView=itemView.findViewById(R.id.video);

        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String setDate(String date)
    {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = new Date();
        try {
            d = input.parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String formatted="";
        if(output.format(d)!=null)
            formatted= output.format(d);
        return formatted;
    }
}
