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
        m=MySharedPrefrence.instanceOf(getContext());
         mRecyclerView=v.findViewById(R.id.rvCarouselRecyclerView);
         recyclerView=v.findViewById(R.id.rvCarouselRecyclerView1);
        mLinearLayoutManager = new CarouselLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        manager = new CarouselLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false);
        mRecyclerView.setLayoutManager(mLinearLayoutManager);
        recyclerView.setLayoutManager(manager);

//        ultraViewPager.getIndicator()
//                .setOrientation(UltraViewPager.Orientation.HORIZONTAL)
//                .setFocusColor(Color.GREEN)
//                .setNormalColor(Color.WHITE)
//                .setRadius((int)TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 5, getResources().getDisplayMetrics()));
////set the alignment
//        ultraViewPager.getIndicator().setGravity(Gravity.CENTER_HORIZONTAL | Gravity.BOTTOM);
////construct built-in indicator, and add it to  UltraViewPager
//        ultraViewPager.getIndicator().build();
//
////set an infinite loop
//        ultraViewPager.setInfiniteLoop(true);
////enable auto-scroll mode
//        ultraViewPager.setAutoScroll(10000);
//        ultraViewPager.disableIndicator();

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
            try {
                if (jsonObject.getJSONObject("result").getJSONArray("featured Programs").length() > 0 && jsonObject.getJSONObject("result").getJSONArray("top Rated Programs").length() > 0) {
                    try {
                        Gson gson = new GsonBuilder().create();
                        ArrayList<up_skill_model> rm = gson.fromJson(jsonObject.getJSONObject("result").getString("featured Programs"), new TypeToken<ArrayList<up_skill_model>>() {
                        }.getType());
                        ArrayList<up_skill_model> rm1 = gson.fromJson(jsonObject.getJSONObject("result").getString("top Rated Programs"), new TypeToken<ArrayList<up_skill_model>>() {
                        }.getType());
                        arrayList.clear();
                        arrayList.addAll(rm);
//                List<Integer> newItems = new ArrayList<>();
//                newItems.add(R.drawable.ic_done);
//                newItems.add(R.drawable.ic_home);
//                newItems.add(R.drawable.ic_like);
                        mAdapter = new CarouselAdapter(getContext());
                        adapter1 = new CarouselAdapter(getContext());

                        mRecyclerView.setAdapter(mAdapter);
                        recyclerView.setAdapter(adapter1);


                        mAdapter.addAll(arrayList);
                        mLinearLayoutManager.scrollToPositionWithOffset(arrayList.size() / 2, 55);
                        mAdapter.notifyDataSetChanged();


                        adapter1.addAll(rm1);
                        manager.scrollToPositionWithOffset(rm1.size() / 2, 55);
                        adapter1.notifyDataSetChanged();
                        //Log.d(TAG, "Response succeeded!!!");
//                ultraViewPager.setScrollMode(UltraViewPager.ScrollMode.HORIZONTAL);
//                //initialize UltraPagerAdapter，and add child view to UltraViewPager
//                adapter = new UltraViewPagerAdapterNew(true,arrayList,getContext());
//                ultraViewPager.setAdapter(adapter);
//
////initialize built-in indicator
//                ultraViewPager.initIndicator();
////set style of indicators
//                ultraViewPager.setMultiScreen(0.6f);
//                ultraViewPager.setItemRatio(1.0f);
//                ultraViewPager.setRatio(2.0f);
//                ultraViewPager.setMaxHeight(1000);
//                ultraViewPager.setAutoMeasureHeight(true);
//                ultraViewPager.setPageTransformer(false, new UltraDepthScaleTransformer());
//                adapter.notifyDataSetChanged();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }else {
                    nodata.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
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
