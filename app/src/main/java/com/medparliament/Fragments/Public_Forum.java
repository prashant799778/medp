package com.medparliament.Fragments;

import android.app.ProgressDialog;
import android.graphics.Color;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.PagerAdapter;

import android.util.Log;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebView;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medparliament.Adapter.UltraPagerAdapter;
import com.medparliament.Adapter.UltraViewPagerAdapterNew;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.Internet.NewModel.up_skill_model;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.carouselrecyclerview.CarouselAdapter;
import com.medparliament.carouselrecyclerview.CarouselLayoutManager;
import com.tmall.ultraviewpager.UltraViewPager;
import com.tmall.ultraviewpager.transformer.UltraDepthScaleTransformer;
import com.tmall.ultraviewpager.transformer.UltraScaleTransformer;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;


public class Public_Forum extends Base_Fragement implements onResult {
    WebView   webView  ;
    public static final String TAG = "TimeLineActivity";
    private static final String baseURl = "http://twitter.com";

    private static final String widgetInfo ="<a class=\"twitter-timeline\" href=\"https://twitter.com/medachievers?ref_src=twsrc%5Etfw\">Tweets by medachievers</a> <script async src=\"https://platform.twitter.com/widgets.js\" charset=\"utf-8\"></script>";

     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_public__forum, container, false);
        // ultraViewPager = (UltraViewPager)v.findViewById(R.id.v1);
 webView =    v.findViewById(R.id.webView);
        load_background_color();


        webView.getSettings().setDomStorageEnabled(true);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadDataWithBaseURL(baseURl, widgetInfo, "text/html", "UTF-8", null);

         WebView.setWebContentsDebuggingEnabled(false);
        return v;
    }

    private void load_background_color() {

        //webView.setBackgroundColor(getResources().getColor(R.color.twitter_dark));
        webView.setBackgroundColor(0); // transparent
    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        JSONObject jo = new JSONObject();
        Comman.log("ONResultMewthod", "" + jsonObject);

    }

    @Override
    public void onStart() {
        super.onStart();

    }
}
