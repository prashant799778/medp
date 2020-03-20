package com.medparliament.Fragments;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medparliament.Activity.Login_Signup_Activity;
import com.medparliament.Activity.News_Activity_1;
import com.medparliament.Adapter.MarketInsightsAdapter;
import com.medparliament.Adapter.NewNewsAdapter;
import com.medparliament.Adapter.Post_Adapter;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.Post_Modle;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.Segow_UI_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class News_Fragment extends Base_Fragement implements onResult {
    MySharedPrefrence m;
    ImageButton bck;
    ImageView mainImg;
    com.medparliament.Internet.onResult onResult;
    FloatingActionButton cmnt;
    RelativeLayout relativeLayout;
    RecyclerView recyclerView;
    NewNewsAdapter adapter;
    ArrayList<Result> arrayList;
    View view;
    RelativeLayout rvideo;
    YouTubePlayerView youTubePlayerView;
    RelativeLayout rimgage;
    Segow_UI_Font summery;
    LinearLayoutManager manager;
    RelativeLayout nodata;
    Result result;
    ProgressDialog progressDialog;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v= inflater.inflate(R.layout.fragment_news_, container, false);
        m=MySharedPrefrence.instanceOf(getContext());
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        this.onResult=this;
        mainImg=v.findViewById(R.id.mainimg);
        relativeLayout=v.findViewById(R.id.r);
        view=v.findViewById(R.id.layer);
        relativeLayout.setVisibility(View.GONE);
        nodata=v.findViewById(R.id.nodata);
        rimgage=v.findViewById(R.id.rimage);
        rvideo=v.findViewById(R.id.rvideo);
        recyclerView=v.findViewById(R.id.recycle);
        arrayList=new ArrayList<>();
        summery=v.findViewById(R.id.summery);
        youTubePlayerView=v.findViewById(R.id.video);
        manager=new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        adapter=new NewNewsAdapter(getContext(),arrayList,m.getUserName(),1);
        recyclerView.setAdapter(adapter);
        return v;
    }
    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("userId",""+m.getUserId()).put("key",2);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("MyPOSTJSON",""+jsonObject);
        return jsonObject;
    }

    @Override
    public void onStart() {
        super.onStart();
        Api_Calling.postMethodCall_NO_MSG(getContext(),getActivity().getWindow().getDecorView().getRootView(), onResult, URLS.landingPageDashboardtest, myPostJson(), "MY_POST_LIST");
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        JSONObject jo=new JSONObject();
        if(progressDialog!=null &&  progressDialog.isShowing())
            progressDialog.dismiss();
        nodata.setVisibility(View.VISIBLE);
        if(jsonObject!=null && status){
            nodata.setVisibility(View.GONE);
            try {
                Gson gson=new GsonBuilder().create();
                result = gson.fromJson(jsonObject.getJSONObject("result").getString("headline"), new TypeToken<Result>(){}.getType());
                if(!result.getImagePath().equalsIgnoreCase("")){
                Comman.setRectangleImage(getContext(),mainImg,result.getImagePath());
                rimgage.setVisibility(View.VISIBLE);
                rvideo.setVisibility(View.GONE);
                }else {
                    rimgage.setVisibility(View.GONE);
                    rvideo.setVisibility(View.VISIBLE);
                    youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
                    youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
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
                summery.setText(result.getSummary());
                relativeLayout.setVisibility(View.VISIBLE);
                view.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if(Comman.Check_Login(getContext())){
                            Intent intent = new Intent(getContext(), News_Activity_1.class);
                            intent.putExtra("data", result);
                            getContext().startActivity(intent);} else {
                            Intent intent = new Intent(getContext(), Login_Signup_Activity.class);
                            getContext().startActivity(intent);
                        }
                    }
                });
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Gson gson=new GsonBuilder().create();
            try {
                if(jsonObject.getJSONObject("result").getJSONArray("news").length()>0) {
                    ArrayList<Result> rm = gson.fromJson(jsonObject.getJSONObject("result").getString("news"), new TypeToken<ArrayList<Result>>() {
                    }.getType());
                    arrayList.clear();
                    arrayList.addAll(rm);
                    adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}