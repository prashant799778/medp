package com.medparliament.Fragments;

import android.app.ProgressDialog;
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
import com.medparliament.Adapter.GalleryAdapter;
import com.medparliament.Adapter.NewNewsAdapter;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.DashboardGalleryModel;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.Segow_UI_Font;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Gallery_Fragment extends Base_Fragement implements onResult {
    MySharedPrefrence m;
    ImageButton bck;
    ImageView mainImg;
    FloatingActionButton cmnt;
    RecyclerView recyclerView;
    GalleryAdapter adapter;
    onResult onResult;
    ArrayList<DashboardGalleryModel>gallerylsit;
    Segow_UI_Font summery;
    LinearLayoutManager manager;
    RelativeLayout nodata;
    ProgressDialog progressDialog;
     @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View  v= inflater.inflate(R.layout.fragment_gallery_, container, false);
         m=MySharedPrefrence.instanceOf(getContext());
         progressDialog = new ProgressDialog(getContext());
         progressDialog.setMessage("Loading...");
         progressDialog.setCancelable(true);
         progressDialog.show();
         this.onResult=this;
         mainImg=v.findViewById(R.id.mainimg);
         nodata=v.findViewById(R.id.nodata);
         recyclerView=v.findViewById(R.id.recycle);
         gallerylsit=new ArrayList<>();
         summery=v.findViewById(R.id.summery);
         manager=new LinearLayoutManager(getContext());
         recyclerView.setLayoutManager(manager);
         adapter=new GalleryAdapter(getContext(),gallerylsit);
         recyclerView.setAdapter(adapter);
         return v;
    }

    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("userId",""+m.getUserId()).put("key",3);
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
            Gson gson=new GsonBuilder().create();
            try {
                if(jsonObject.getJSONArray("result").length()>0){
                ArrayList<DashboardGalleryModel> rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<DashboardGalleryModel>>(){}.getType());
                gallerylsit.clear();
                gallerylsit.addAll(rm);
                adapter.notifyDataSetChanged();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}