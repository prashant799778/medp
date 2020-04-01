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
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.medparliament.Activity.Login_Signup_Activity;
import com.medparliament.Activity.New_News_Details_Activity;
import com.medparliament.Activity.NewsDetails_Activity;
import com.medparliament.Activity.News_Activity_1;
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

public class NewNewsAdapter extends RecyclerView.Adapter<NewNewsAdapter.NotificationHolder> {
    ArrayList<Result>list;
    Context context;
    int a;
    String userName;
    public NewNewsAdapter(Context context, ArrayList<Result>arrayList, String username, int a)
    {
        this.context=context;
        this.list=arrayList;
        this.userName=username;
        this.a=a;
    }
    @NonNull
    @Override
    public NewNewsAdapter.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.new_news_layout,parent,false);
        return new NewNewsAdapter.NotificationHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        final Result result=list.get(position);
        holder.title.setText(result.getNewsTitle());
        if(result.getDateCreate1()!=null)
        holder.time.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(result.getDateCreate1())));
        holder.msg.setText(Html.fromHtml(result.getNewsDesc()));
        if(!result.getImagePath().equalsIgnoreCase("") ){
            Comman.setImageWithCondition(context, holder.mainimg, result.getImagePath());
            holder.youTubePlayerView.setVisibility(View.GONE);
        }else {
            holder.mainimg.setVisibility(View.GONE);
            holder.youTubePlayerView.setVisibility(View.VISIBLE);
            holder.youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
            holder.youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                 youTubePlayer.cueVideo(result.getVideoId(),1);
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

                    Toast toast=Toast.makeText(context,"Faild",Toast.LENGTH_LONG);
                    toast.show();
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
                if(Comman.Check_Login(context)){
                Intent intent = new Intent(context, News_Activity_1.class);
                intent.putExtra("data", result);
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
        Segow_UI_Semi_Font title,time;
        YouTubePlayerView youTubePlayerView;
        Open_Sans_Regular_Font msg;
        ImageView mainimg;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            mainimg=itemView.findViewById(R.id.mainimg);
            msg=itemView.findViewById(R.id.msg);
            youTubePlayerView=itemView.findViewById(R.id.video);
            time=itemView.findViewById(R.id.date);
            title=itemView.findViewById(R.id.title);
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
