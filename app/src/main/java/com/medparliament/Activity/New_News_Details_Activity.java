package com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;

public class New_News_Details_Activity extends AppCompatActivity {
    MySharedPrefrence m;
    Segow_UI_Semi_Font date, created, msg,loc,header;
    ImageButton bck;
    Segow_UI_Bold_Font title;
    ImageView img;
    YouTubePlayerView video;
    Result result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new__news__details_);
        Animatoo.animateSwipeLeft(New_News_Details_Activity.this);
        Intent i=getIntent();
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        loc = findViewById(R.id.loc);
        msg = findViewById(R.id.msg);
        header = findViewById(R.id.header);
        created = findViewById(R.id.created);
        bck = findViewById(R.id.bck);
        video=findViewById(R.id.video);
        img= findViewById(R.id.cover);
        if(i!=null)
        {
            result=(Result) i.getSerializableExtra("data");
            msg.setText(Html.fromHtml(result.getNewsDesc()));
            title.setText(Html.fromHtml(result.getNewsTitle()));
            if(!result.getDateCreate1().equalsIgnoreCase(""))
            created.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(result.getDateCreate1())));

        }
        if(!result.getImagePath().equalsIgnoreCase(""))
        {
            Comman.setImageWithCondition(New_News_Details_Activity.this,img,result.getImagePath());
            video.setVisibility(View.GONE);
        }else {
            img.setVisibility(View.GONE);
            video.getPlayerUiController().showYouTubeButton(false);
            video.addYouTubePlayerListener(new YouTubePlayerListener() {
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
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        m = MySharedPrefrence.instanceOf(New_News_Details_Activity.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(New_News_Details_Activity.this);
    }
}
