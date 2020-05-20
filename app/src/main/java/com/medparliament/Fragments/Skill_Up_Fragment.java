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
import com.medparliament.Widget.Segow_UI_Bold_Font;
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


public class Skill_Up_Fragment extends Base_Fragement implements onResult {
    UltraViewPager ultraViewPager;
    ArrayList<up_skill_model>arrayList;
    MySharedPrefrence m;
    UltraViewPagerAdapterNew adapter;
    ProgressDialog progressDialog;
    RelativeLayout nodata;
    onResult onResult;
    private CarouselAdapter mAdapter,adapter1;
    private LinearLayoutManager mLinearLayoutManager,manager;
    RecyclerView mRecyclerView,recyclerView;
    Segow_UI_Bold_Font cavailable , havailable;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_skill__up_, container, false);
        // ultraViewPager = (UltraViewPager)v.findViewById(R.id.v1);
         arrayList=new ArrayList<>();
        progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        this.onResult=this;
        nodata=v.findViewById(R.id.nodata);
        cavailable = v.findViewById(R.id.t1) ;
        havailable = v.findViewById(R.id.t2) ;
        m=MySharedPrefrence.instanceOf(getContext());
         mRecyclerView=v.findViewById(R.id.rvCarouselRecyclerView);
         recyclerView=v.findViewById(R.id.rvCarouselRecyclerView1);
        mLinearLayoutManager = new CarouselLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        manager = new CarouselLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setLayoutManager(manager);
        return v;
    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        JSONObject jo = new JSONObject();
        Comman.log("ONResultMewthod", "" + jsonObject);
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        nodata.setVisibility(View.VISIBLE);
        if (jsonObject != null && status) {
            nodata.setVisibility(View.GONE);
            try
            {
                if (jsonObject.getJSONObject("result").getJSONArray("featured Programs").length() > 0)
                {
                    cavailable.setVisibility(View.VISIBLE);
                    Gson gson = new GsonBuilder().create();
                    ArrayList<up_skill_model> rm = gson.fromJson(jsonObject.getJSONObject("result").getString("featured Programs"), new TypeToken<ArrayList<up_skill_model>>() {
                    }.getType());
                    arrayList.clear();
                    arrayList.addAll(rm);
                    mAdapter = new CarouselAdapter(getContext());
                    mRecyclerView.setAdapter(mAdapter);
                    mAdapter.addAll(arrayList);
                    mLinearLayoutManager.scrollToPositionWithOffset(arrayList.size() / 2, 55);
                    mAdapter.notifyDataSetChanged();
                }

                if (jsonObject.getJSONObject("result").getJSONArray("top Rated Programs").length() > 0) {
                    try {
                        havailable.setVisibility(View.VISIBLE);
                        Gson gson = new GsonBuilder().create();
                        ArrayList<up_skill_model> rm1 = gson.fromJson(jsonObject.getJSONObject("result").getString("top Rated Programs"), new TypeToken<ArrayList<up_skill_model>>() {
                        }.getType());
                        adapter1 = new CarouselAdapter(getContext());
                        recyclerView.setAdapter(adapter1);
                        adapter1.addAll(rm1);
                        manager.scrollToPositionWithOffset(rm1.size() / 2, 55);
                        adapter1.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            } catch (JSONException e) {
                nodata.setVisibility(View.VISIBLE);
                e.printStackTrace();
            }
        }
    }
    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("userId",""+m.getUserId()).put("key",8);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    @Override
    public void onStart() {
        super.onStart();
        Api_Calling.postMethodCall_NO_MSG(getContext(),getActivity().getWindow().getDecorView().getRootView(), onResult, URLS.landingPageDashboardtest, myPostJson(), "ACCCCCCCCCCCCCCCCCCCccc");
    }
}
