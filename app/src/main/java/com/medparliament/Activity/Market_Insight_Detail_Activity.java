package com.medparliament.Activity;



import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medparliament.Adapter.CommentListAdapter;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.Dashboard_News_Model;
import com.medparliament.Internet.Models.Dashbooard_eventModel;
import com.medparliament.Internet.NewModel.ListDataModel;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.Internet.OnListData;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.Constant;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_EditText;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Market_Insight_Detail_Activity extends AppCompatActivity implements onResult, OnListData {
    Segow_UI_Bold_Font title;
    Open_Sans_Regular_Font date,msg;
    ImageView pic_img,like,cmnt;
    MySharedPrefrence m;
    Segow_UI_Bold_Font text;
    Segow_UI_EditText editTextcmnt;
    Segow_UI_Bold_Font post;
    OnListData onListData;
    LinearLayout cmnt_Layout;
    ImageButton bck;
    onResult onResult;
    Segow_UI_Bold_Font counter;
    Result result;
    Dashboard_News_Model model;


    RecyclerView recyclerView;
    RelativeLayout rvideo;
    YouTubePlayerView video;
    Segow_UI_Semi_Font noti_counter;
    LinearLayout circle;
    ImageView share;
    ImageView bell;
    Handler ha;
    LinearLayoutManager manager;
    CommentListAdapter adapter;
    ArrayList<ListDataModel>arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market__insight__detail_);
        m=MySharedPrefrence.instanceOf(Market_Insight_Detail_Activity.this);
        noti_counter = findViewById(R.id.noti_count);
        apiCall(URLS.Notification, myPostJson2());
        noti_counter.setText(m.getCounterValue());
        Animatoo.animateSwipeLeft(Market_Insight_Detail_Activity.this);

        bell = findViewById(R.id.bell);
        if (Comman.Check_Login(Market_Insight_Detail_Activity.this))
            bell.setVisibility(View.VISIBLE);


        circle=findViewById(R.id.circle);
        share=findViewById(R.id.share_tool);



        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(Market_Insight_Detail_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(Market_Insight_Detail_Activity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(Market_Insight_Detail_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(Market_Insight_Detail_Activity.this, NotificationActivity.class));
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





        title=findViewById(R.id.title);
        date=findViewById(R.id.date);
        msg=findViewById(R.id.msg);
        counter=findViewById(R.id.counter);
        pic_img=findViewById(R.id.pic_img);
        bck=findViewById(R.id.bck);
        text=findViewById(R.id.counttext);
        like=findViewById(R.id.like);
        cmnt_Layout=findViewById(R.id.l);
        cmnt=findViewById(R.id.comnt);
        video=findViewById(R.id.video);
        rvideo=findViewById(R.id.rvideo);
        post=findViewById(R.id.post);
        editTextcmnt=findViewById(R.id.add_view);
        arrayList=new ArrayList<>();
        this.onResult=this;
        recyclerView=findViewById(R.id.recycle);
        manager=new LinearLayoutManager(Market_Insight_Detail_Activity.this);
        recyclerView.setLayoutManager(manager);
        adapter=new CommentListAdapter(Market_Insight_Detail_Activity.this,arrayList,"",3);
        recyclerView.setAdapter(adapter);
        Intent intent=getIntent();
        this.onListData=this;
        if(intent!=null)
        {
         result=(Result) intent.getSerializableExtra("date");
         if(result!=null) {
             title.setText(result.getNewsTitle());
             msg.setText(Html.fromHtml(result.getNewsDesc()));
             if (result.getDateCreate1() != null)
                 date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(result.getDateCreate1())));
                 counter.setText(result.getLikeCount()+"  Endorse");
         }
        }
        if(!result.getImagePath().equalsIgnoreCase(""))
        {
            Comman.setImageWithCondition(Market_Insight_Detail_Activity.this, pic_img, result.getImagePath());
            rvideo.setVisibility(View.GONE);

        }else {
            pic_img.setVisibility(View.GONE);
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
        cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextcmnt.requestFocus();
                editTextcmnt.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
        text.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editTextcmnt.requestFocus();
                editTextcmnt.setFocusableInTouchMode(true);
                InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.toggleSoftInput(InputMethodManager.SHOW_FORCED, 0);
            }
        });
        cmnt_Layout.setVisibility(View.VISIBLE);
//        if(m.getUserTypeId().equalsIgnoreCase("7"))
//        {
//            cmnt_Layout.setVisibility(View.VISIBLE);
//        }else {
//            cmnt_Layout.setVisibility(View.GONE);
//        }
        if(result.getMakedone().equalsIgnoreCase("1")) {
            like.setEnabled(false);
            like.setImageResource(R.drawable.ic_like_after);
        }
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Api_Calling.likePostMarket(Market_Insight_Detail_Activity.this,likejson(),like,counter,result.getLikeCount());
            }
        });
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comman.hideKeyboard(Market_Insight_Detail_Activity.this);
                onBackPressed();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comman.log("DDDDEd","dddssdd");
                if(!editTextcmnt.getText().toString().isEmpty())
                {
                    Api_Calling.postMethodCall(Market_Insight_Detail_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.commentsMarketingInsight,conmtJson(),"");

                }else { Comman.topSnakBar(Market_Insight_Detail_Activity.this,v, Constant.MSG);

                }
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCallForListData(Market_Insight_Detail_Activity.this,getWindow().getDecorView().getRootView(),onListData,URLS.allMarketingInsightThread,getDataJson(),"");
    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(jsonObject!=null && status) {
            editTextcmnt.setText("");
                Gson gson = new GsonBuilder().create();
                try {
                    Comman.log("DDDDDDDDDDDD",""+jsonObject.toString());
                    if (!jsonObject.getString("result").equalsIgnoreCase("")) {
                        ArrayList<ListDataModel> rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<ListDataModel>>() {
                        }.getType());
                        arrayList.addAll(rm);
                        adapter.notifyDataSetChanged();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Comman.hideKeyboard(Market_Insight_Detail_Activity.this);
        Animatoo.animateSwipeRight(Market_Insight_Detail_Activity.this);

    }

    public JSONObject conmtJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("marketingInsightId",""+result.getId()).put("commentDescription",""+editTextcmnt.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("CommentJson",""+jsonObject);
        return jsonObject;
    }
    public JSONObject likejson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("marketingInsightId",""+result.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("likejson",""+jsonObject);
        return jsonObject;
    }

    public JSONObject getDataJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("Id",""+result.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("likejson",""+jsonObject);
        return jsonObject;
    }

    @Override
    public void onListData(JSONObject jsonObject, Boolean status) {
        if(jsonObject!=null && status){
            Gson gson=new GsonBuilder().create();
            try {
                if(!jsonObject.getString("result").equalsIgnoreCase("")){
                ArrayList<ListDataModel> rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<ListDataModel>>(){}.getType());
                arrayList.clear();
                arrayList.addAll(rm);
                adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    private void apiCall(String URL,JSONObject jsonObject)
    {
        Comman.log("NotificationApi","Callling");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Comman.log("NotificationApi","Response"+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("true") && (!Comman.getValueFromJsonObject(response,"totalcount").equalsIgnoreCase("0")) )
                    {
                        m.setCounterValue(Comman.getValueFromJsonObject(response,"totalcount"));
//                        noti_counter.setText(Comman.getValueFromJsonObject(response,"totalcount"));
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
        RequestQueue requestQueue= Volley.newRequestQueue(Market_Insight_Detail_Activity.this);
        requestQueue.add(jsonObjectRequest);

    }
    private JSONObject myPostJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (Comman.Check_Login(Market_Insight_Detail_Activity.this)) {
                jsonObject.put("userTypeId", m.getUserTypeId());
                jsonObject.put("userId", m.getUserId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Notificationscsdcsdcdsvdsfvfdv", "" + jsonObject);
        return jsonObject;
    }
}
