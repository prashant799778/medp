package com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Internet.NewModel.TvModel;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class VideoDetailsActivity extends AppCompatActivity {
    ImageButton bck;
    Segow_UI_Bold_Font titel,date,msg;
    YouTubePlayerView video;
    ImageView img;
    TvModel model;
    String videoId="";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        Animatoo.animateSlideLeft(VideoDetailsActivity.this);
        video=findViewById(R.id.video);
        bck=findViewById(R.id.bck);
        titel=findViewById(R.id.title);
        img=findViewById(R.id.img);
        msg=findViewById(R.id.msg);
        date=findViewById(R.id.date);
        Intent i=getIntent();
        if(i!=null)
        {
         model=(TvModel) i.getSerializableExtra("data");
        }
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        if(model!=null) {
            if (model.getText() != null)
                titel.setText(model.getText());
            if (!model.getDateCreate().equalsIgnoreCase(""))
                date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(model.getDateCreate())));
            if (model.getText() != null)
                msg.setText(model.getText());



            if(!model.getImagePath().equalsIgnoreCase("")){
                video.setVisibility(View.GONE);
                Comman.setRectangleImage(VideoDetailsActivity.this,img,model.getImagePath());
            }else {
                img.setVisibility(View.GONE);
            video.getPlayerUiController().showYouTubeButton(false);
            video.addYouTubePlayerListener(new YouTubePlayerListener() {
                @Override
                public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                    if (model.getVideoId() != null)
                        youTubePlayer.cueVideo(model.getVideoId(), 0);
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
            });}
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(VideoDetailsActivity.this);
    }
}
