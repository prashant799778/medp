package com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Internet.NewModel.TvModel;
import com.medparliament.Internet.URLS;
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
import org.json.JSONException;
import org.json.JSONObject;

import static com.medparliament.Utility.Comman.REQUEST_ACCEPT;

public class VideoDetailsActivity extends AppCompatActivity {
    ImageButton bck;
    Segow_UI_Bold_Font titel,date,msg;
    YouTubePlayerView video;
    Segow_UI_Semi_Font noti_counter;
    LinearLayout circle;
    ImageView share;
    ImageView bell;
    Handler ha;
    ImageView img;
    MySharedPrefrence m;
    TvModel model;
    String videoId="";
    BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_details);
        m=MySharedPrefrence.instanceOf(VideoDetailsActivity.this);
        noti_counter = findViewById(R.id.noti_count);
//         apiCall(URLS.Notification, myPostJson2());
        noti_counter.setText(m.getCounterValue());
        Animatoo.animateSlideLeft(VideoDetailsActivity.this);

        bell = findViewById(R.id.bell);
        if (Comman.Check_Login(VideoDetailsActivity.this))
            bell.setVisibility(View.VISIBLE);


        circle=findViewById(R.id.circle);
        share=findViewById(R.id.share_tool);



        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(VideoDetailsActivity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(VideoDetailsActivity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(VideoDetailsActivity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(VideoDetailsActivity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MedParliament App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.text) + "https://play.google.com/store/apps/details?id=com.medparliament");
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, "Share via"));
            }
        });

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    String   value= intent.getStringExtra("count_value");
                    if(value!=null && !value.isEmpty()){
                        noti_counter.setText(value);

                        circle.setVisibility(View.VISIBLE);

                    }else{
                        circle.setVisibility(View.GONE);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };






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
                        youTubePlayer.cueVideo(model.getVideoId(), 1);
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

    private void apiCall(String URL, JSONObject jsonObject)
    {
        Comman.log("NotificationApi","Callling");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Comman.log("NotificationApi","Response"+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("true") && (!Comman.getValueFromJsonObject(response,"totalcount").equalsIgnoreCase("0")) )
                    {
//                        noti_counter.setText(Comman.getValueFromJsonObject(response,"totalcount"));
                        m.setCounterValue(Comman.getValueFromJsonObject(response,"totalcount"));
                        circle.setVisibility(View.VISIBLE);
                    }else {
                        circle.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(VideoDetailsActivity.this);
        requestQueue.add(jsonObjectRequest);

    }
    private JSONObject myPostJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (Comman.Check_Login(VideoDetailsActivity.this)) {
                jsonObject.put("userTypeId", m.getUserTypeId());
                jsonObject.put("userId", m.getUserId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Notificationscsdcsdcdsvdsfvfdv", "" + jsonObject);
        return jsonObject;
    }


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(VideoDetailsActivity.this).registerReceiver((receiver),
                new IntentFilter(REQUEST_ACCEPT)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(VideoDetailsActivity .this).unregisterReceiver(receiver);
    }
}
