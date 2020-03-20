package com.medparliament.Adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.Html;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.medparliament.Activity.Login_Signup_Activity;
import com.medparliament.Activity.Market_Insight_Detail_Activity;
import com.medparliament.Activity.NewsDetails_Activity;
import com.medparliament.Activity.Post_Details_Activity;
import com.medparliament.Internet.Models.Post_Modle;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Date;

public class MarketInsightsAdapter extends RecyclerView.Adapter<MarketInsightsAdapter.NotificationHolder> {
    ArrayList<Result>list;
    Context context;
    int a;
    String userName;
    public MarketInsightsAdapter(Context context, ArrayList<Result>arrayList, String username, int a)
    {
        this.context=context;
        this.list=arrayList;
        this.userName=username;
        this.a=a;
    }
    @NonNull
    @Override
    public MarketInsightsAdapter.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.market_insight_layout,parent,false);
        return new MarketInsightsAdapter.NotificationHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final NotificationHolder holder, int position) {
        final Result result=list.get(position);
        holder.title.setText(result.getNewsTitle());
        if(result.getDateCreate1()!=null)
        holder.time.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(result.getDateCreate1())));
        holder.msg.setText(Html.fromHtml(result.getNewsDesc()));
        if(!result.getImagePath().equalsIgnoreCase(""))
        {
            Comman.log("Insde","ImageSegment"+result.getImagePath());
            holder.rvideo.setVisibility(View.GONE);
            Comman.setImageWithCondition(context,holder.mainimg,result.getImagePath());
        }else {
            Comman.log("Insde","VideoSegment"+result.getVideoId());
            holder.mainimg.setVisibility(View.GONE);
            holder.rvideo.setVisibility(View.VISIBLE);
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
                    Intent intent = new Intent(context, Market_Insight_Detail_Activity.class);
                    intent.putExtra("date",result);
                    context.startActivity(intent);} else {
                    Intent intent = new Intent(context, Login_Signup_Activity.class);
                    context.startActivity(intent);
                }
            }
        });
        holder.itemView.measure(View.MeasureSpec.UNSPECIFIED, View.MeasureSpec.UNSPECIFIED);
        int width = holder.itemView.getMeasuredWidth();
        int height = holder.itemView.getMeasuredHeight();
//      if(height>500)
//      {
////          DisplayMetrics displaymetrics = new DisplayMetrics();
////          ((Activity) context).getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
//          //if you need same height as width you can set devicewidth in holder.image_view.getLayoutParams().height
//          holder.itemView.getLayoutParams().height = 800;
//
//      }


    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class NotificationHolder extends RecyclerView.ViewHolder {
        Open_Sans_Regular_Font title;
        Segow_UI_Font msg;
        ImageView mainimg;
        Segow_UI_Bold_Font time;
        RelativeLayout rvideo;
        View layer;
        YouTubePlayerView video;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            mainimg=itemView.findViewById(R.id.mainimg);
            msg=itemView.findViewById(R.id.msg);
            time=itemView.findViewById(R.id.heading);
            title=itemView.findViewById(R.id.streme);
            video=itemView.findViewById(R.id.video);
            rvideo=itemView.findViewById(R.id.rvideo);
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
