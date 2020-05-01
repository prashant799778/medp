package com.medparliament.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
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
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medparliament.Adapter.CommentListAdapter;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.DashboardAnnouncedModel;
import com.medparliament.Internet.Models.Dashboard_News_Model;
import com.medparliament.Internet.Models.Dashbooard_eventModel;
import com.medparliament.Internet.Models.NewsModelList;
import com.medparliament.Internet.NewModel.ListDataModel;
import com.medparliament.Internet.OnListData;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.Constant;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Utility.PrettyTimeClass;
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

public class NewsDetails_Activity extends AppCompatActivity implements onResult, OnListData {
    onResult onResult;
    String postId = "";
    MySharedPrefrence m;
    Segow_UI_Semi_Font date, created, msg,loc,header;
    ImageButton bck;
    Segow_UI_Bold_Font title;
    Segow_UI_Semi_Font noti_counter;
    LinearLayout circle;
    ImageView share;
    ImageView bell;
    Handler ha;
    Segow_UI_Bold_Font counter;
    ProgressDialog progressDialog;
    NewsModelList pm;
    Dashboard_News_Model newses;
    View view;
    Dashbooard_eventModel eventModel;
    DashboardAnnouncedModel announcedModel;
    ImageView img;
    AppCompatButton  ab;
    Intent i;

    ImageView pic_img,like,cmnt;
    Segow_UI_Bold_Font text;
    YouTubePlayerView youTubePlayerView;
    Segow_UI_EditText editTextcmnt;
    Segow_UI_Bold_Font post;
    LinearLayout cmnt_Layout;



    OnListData onListData;
    RecyclerView recyclerView;
    LinearLayoutManager manager;
    CommentListAdapter adapter;
    ArrayList<ListDataModel> arrayList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details_);
        m = MySharedPrefrence.instanceOf(NewsDetails_Activity.this);
        noti_counter = findViewById(R.id.noti_count);
        apiCall(URLS.Notification, myPostJson2());
        noti_counter.setText(m.getCounterValue());

        Animatoo.animateSwipeLeft(NewsDetails_Activity.this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        this.onResult = this;
        bell = findViewById(R.id.bell);
        if (Comman.Check_Login(NewsDetails_Activity.this))
            bell.setVisibility(View.VISIBLE);


        circle=findViewById(R.id.circle);
        share=findViewById(R.id.share_tool);



        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(NewsDetails_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(NewsDetails_Activity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(NewsDetails_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(NewsDetails_Activity.this, NotificationActivity.class));
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




        ab = findViewById(R.id.interest);
        if (!Comman.Check_Login(getApplicationContext())) {

            ab.setVisibility(View.GONE);
        }
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        loc = findViewById(R.id.loc);
        msg = findViewById(R.id.msg);
        header = findViewById(R.id.header);
        created = findViewById(R.id.created);
        bck = findViewById(R.id.bck);
        img = findViewById(R.id.cover);



        arrayList=new ArrayList<>();
        recyclerView=findViewById(R.id.recycle);
        manager=new LinearLayoutManager(NewsDetails_Activity.this);
        recyclerView.setLayoutManager(manager);
        adapter=new CommentListAdapter(NewsDetails_Activity.this,arrayList,"",3);

        recyclerView.setAdapter(adapter);
        text=findViewById(R.id.counttext);
        youTubePlayerView=findViewById(R.id.video);
        like=findViewById(R.id.like);
        counter=findViewById(R.id.counter);
        cmnt_Layout=findViewById(R.id.l);
        cmnt=findViewById(R.id.comnt);
        post=findViewById(R.id.post);
        editTextcmnt=findViewById(R.id.add_view);
        this.onListData=this;


//        progressDialog = new ProgressDialog(NewsDetails_Activity.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(true);
//        progressDialog.show();
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
         i = getIntent();
        if (i != null) {
            pm = (NewsModelList) i.getSerializableExtra("news");
            postId = i.getStringExtra("postId");
            eventModel = (Dashbooard_eventModel) i.getSerializableExtra("event");
            newses = (Dashboard_News_Model) i.getSerializableExtra("newses");
            announcedModel = (DashboardAnnouncedModel) i.getSerializableExtra("ann");
        }
        setResult();
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
        if(eventModel.getMakedone().equalsIgnoreCase("1")) {
            like.setEnabled(false);
            like.setImageResource(R.drawable.ic_like_after);
        }
        counter.setText(eventModel.getLikeCount()+"  Like");
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api_Calling.likePostEvent(NewsDetails_Activity.this,likejson(),like,counter,eventModel.getLikeCount());
            }
        });
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comman.hideKeyboard(NewsDetails_Activity.this);
                onBackPressed();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comman.log("DDDDEd","dddssdd");
                if(!editTextcmnt.getText().toString().isEmpty())
                {
                    Api_Calling.postMethodCall(NewsDetails_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.commentsEvent,conmtJson(),"");

                }else { Comman.topSnakBar(NewsDetails_Activity.this,v, Constant.MSG);

                }
            }
        });




    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {

        if(jsonObject!=null && status)
        {
            Gson gson = new GsonBuilder().create();
            try {
                editTextcmnt.setText("");
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


//        if(status){
//            ab.setText("Already Intrested");
//            ab.setBackgroundResource(R.color.hintColor);}
//        if (jsonObject != null && status) {
//            try {
//                JSONObject jo = jsonObject.getJSONArray("result").getJSONObject(0);
//                m.setLoggedIn(true);
//                title.setText(Comman.getValueFromJsonObject(jo, "postTitle"));
//                created.setText("By :-" + Comman.getValueFromJsonObject(jo, "userName"));
//                date.setText("" + PrettyTimeClass.PrettyTime(Comman.timeInms(Comman.getValueFromJsonObject(jo, "DateCreate"))));
//                msg.setText("" + Comman.getValueFromJsonObject(jo, "postDescription"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }
//

    }







    public  void  setResult(){
 if(pm != null){

     img.setVisibility(View.VISIBLE);
     title.setText(pm.getNewsTitle());
     created.setText(pm.getUserCreate());
      msg.setText(Html.fromHtml(pm.getNewsDesc()));
     Comman.setRectangleImage(getApplicationContext(),img,pm.getImagePath());
     date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));


 }
 if(eventModel != null){
       //Log.d("AAAAAAA",eventModel.getInterestCount());
     ab.setVisibility(View.VISIBLE );
     if(eventModel.getInterestCount()!=null &&  !eventModel.getInterestCount().equalsIgnoreCase("0")){
         ab.setText("Already Registered");
         ab.setBackgroundResource(R.color.hintColor);
         ab.setEnabled(false);
      }else{

         ab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(Comman.Check_Login(getApplicationContext())) {
                     progressDialog = new ProgressDialog(NewsDetails_Activity.this);
                     progressDialog.setMessage("Loading...");
                     progressDialog.setCancelable(true);
                     progressDialog.show();
                     Api_Calling.postMethodCall_Interest(NewsDetails_Activity.this , getWindow().getDecorView().getRootView(), onResult, URLS.seteventInterest, myPostJson(), "eventinterest",ab,progressDialog);
                 }else{
                     startActivity(new Intent(NewsDetails_Activity.this,Login_Signup_Activity.class));
                     finish();
                 }
             }
         });
     }




     header.setText("Event Details");
     if(eventModel.getImagePath()!=null && !eventModel.getImagePath().equalsIgnoreCase(""))
            img.setVisibility(View.VISIBLE);




     created.setText(eventModel.getEventLocation());
            title.setText(eventModel.getEventTitle());
            msg.setText(Html.fromHtml(eventModel.getEventSummary()));
            if(!eventModel.getImagePath().equalsIgnoreCase("")) {
                Comman.setRectangleImage(getApplicationContext(), img, eventModel.getImagePath());
                youTubePlayerView.setVisibility(View.GONE);
            }else {
                img.setVisibility(View.GONE);
                youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
                youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo(eventModel.getVideoId(),0);
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
            date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(eventModel.getDateCreate())));


        }
 if(newses != null){

     img.setVisibility(View.VISIBLE);
     title.setText(newses.getNewsTitle());

     msg.setText(Html.fromHtml(newses.getNewsDesc()));
     msg.setMovementMethod(LinkMovementMethod.getInstance());
     Comman.setRectangleImage(getApplicationContext(),img,newses.getImagePath());
     date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(newses.getDateCreate())));


 }

        if(announcedModel != null){
            header.setText("AnnouncedDetails");
            img.setVisibility(View.VISIBLE);


            msg.setText(announcedModel.getSummary());
           title.setText(announcedModel.getTitle());

            Comman.setRectangleImage(getApplicationContext(),img,announcedModel.getImagePath());

            date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(announcedModel.getDateCreate())));


        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCallForListData(NewsDetails_Activity.this,getWindow().getDecorView().getRootView(),onListData,URLS.allEventThread,getDataJson(),"");

        if(!Comman.Check_Login(getApplicationContext())) {

            ab.setVisibility(View.GONE);
        }
        if (i != null) {
            pm = (NewsModelList) i.getSerializableExtra("news");
            postId = i.getStringExtra("postId");
            eventModel = (Dashbooard_eventModel) i.getSerializableExtra("event");
            newses = (Dashboard_News_Model) i.getSerializableExtra("newses");
            announcedModel = (DashboardAnnouncedModel) i.getSerializableExtra("ann");
//            eventModel.notify();
        }
        setResult();
//        Api_Calling.postMethodCall(NewsDetails_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.ALL_POSTS,setjson(),"NewstDetails");
    }
    public JSONObject setjson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("postId",""+postId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("postDetailsJson",""+jsonObject);
        return jsonObject;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Comman.hideKeyboard(NewsDetails_Activity.this);
        Animatoo.animateSwipeRight(NewsDetails_Activity.this);

    }


    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            if(Comman.Check_Login(NewsDetails_Activity.this)){
                jsonObject.put("userTypeId",m.getUserTypeId());
                jsonObject.put("userId",m.getUserId());
                jsonObject.put("eventId",eventModel.getId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("MyPOSTJSON",""+jsonObject);
        return jsonObject;

    }


    public JSONObject conmtJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("eventId",""+eventModel.getId()).put("commentDescription",""+editTextcmnt.getText().toString());
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
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("eventId",""+eventModel.getId());
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
                ArrayList<ListDataModel>  rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<ListDataModel>>(){}.getType());
                arrayList.clear();
                arrayList.addAll(rm);
                adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
    public JSONObject getDataJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("Id",""+eventModel.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("likejson",""+jsonObject);

        return jsonObject;
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
        RequestQueue requestQueue= Volley.newRequestQueue(NewsDetails_Activity.this);
        requestQueue.add(jsonObjectRequest);

    }
    private JSONObject myPostJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (Comman.Check_Login(NewsDetails_Activity.this)) {
                jsonObject.put("userTypeId", m.getUserTypeId());
                jsonObject.put("userId", m.getUserId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Notificationscsdcsdcdsvdsfvfdv", "" + jsonObject);
        return jsonObject;
    }
    public static void showDialogeBox(Context context, String title, String msg)
    {
        final MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context,R.style.custom_dialog);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_layout, null, false);
        Button yes=dialogView.findViewById(R.id.yes);
        Button no=dialogView.findViewById(R.id.no);
        ImageView mainimg=dialogView.findViewById(R.id.mainimg);
        no.setVisibility(View.GONE);
        Segow_UI_Bold_Font title1=dialogView.findViewById(R.id.title);
        Segow_UI_Semi_Font msg1=dialogView.findViewById(R.id.msg);
        title1.setText(title);
        msg1.setText(msg);
//        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
//        lp.dimAmount=0.7f;
//        alertDialog.getWindow().setAttributes(lp);
//        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        alertDialog.setView(dialogView);
        alertDialog.show();
        yes.setText("Ok");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
//                m.clearData();
//                startActivity(new Intent(Up_Skill_Details_Activity.this,DashBoard_Activity.class));
//                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }
}