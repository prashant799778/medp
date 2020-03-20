package com.medparliament.Adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.medparliament.Activity.Login_Signup_Activity;
import com.medparliament.Activity.NewsDetails_Activity;
import com.medparliament.Internet.Models.Dashbooard_eventModel;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class New_Event_ extends RecyclerView.Adapter<New_Event_.NotificationHolder> {
    ArrayList<Dashbooard_eventModel>list;
    Context context;
    int a;
    String userName;
    public New_Event_(Context context, ArrayList<Dashbooard_eventModel>arrayList, String username, int a)
    {
        this.context=context;
        this.list=arrayList;
        this.userName=username;
        this.a=a;
    }
    @NonNull
    @Override
    public New_Event_.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.new_event_layout,parent,false);
        return new New_Event_.NotificationHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        final Dashbooard_eventModel result=list.get(position);
        holder.title.setText(result.getEventTitle());
        if(result.getDateCreate()!=null)
//        holder.time.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(result.getDateCreate())));
        holder.place.setText(Html.fromHtml(result.getEventLocation()));
        if(!result.getImagePath().equalsIgnoreCase(""))
        {
        holder.video.setVisibility(View.GONE);
        Comman.setImageWithCondition(context,holder.mainimg,result.getImagePath());
        }else {
            holder.mainimg.setVisibility(View.GONE);
            holder.video.setVisibility(View.VISIBLE);
            holder.video.getPlayerUiController().showYouTubeButton(false);
            holder.video.addYouTubePlayerListener(new YouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    youTubePlayer.cueVideo(result.getVideoId(),0);
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
        holder.layer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Comman.Check_Login(context)){
                Intent intent = new Intent(context, NewsDetails_Activity.class);
                intent.putExtra("event",result);
                context.startActivity(intent);} else {

                Intent intent = new Intent(context, Login_Signup_Activity.class);


                context.startActivity(intent);
            }
        }
        });





    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class NotificationHolder extends RecyclerView.ViewHolder {
        ImageView mainimg;
        Segow_UI_Bold_Font title;
        YouTubePlayerView video;
        View layer;
        Open_Sans_Regular_Font place;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            place=itemView.findViewById(R.id.location);
            mainimg=itemView.findViewById(R.id.img);
            video=itemView.findViewById(R.id.video);
            layer=itemView.findViewById(R.id.layer);

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
