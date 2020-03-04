package com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.medparliament.Adapter.News_List_Adapter;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.NewsModelList;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class All_News_Activity extends AppCompatActivity implements onResult {
    MySharedPrefrence m;
    onResult onResult;
    ImageButton bck;
    FloatingActionButton cmnt;
    RecyclerView recyclerView;
    News_List_Adapter adapter;
    ArrayList<NewsModelList> arrayList;
    LinearLayoutManager manager;
    RelativeLayout nodata;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__news_);
        m=MySharedPrefrence.instanceOf(All_News_Activity.this);
        this.onResult=this;
        progressDialog = new ProgressDialog(All_News_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        nodata=findViewById(R.id.nodata);
        bck=findViewById(R.id.bck);
        recyclerView=findViewById(R.id.recycle);
        arrayList=new ArrayList<>();
        manager=new LinearLayoutManager(All_News_Activity.this);
        recyclerView.setLayoutManager(manager);
        adapter=new News_List_Adapter(All_News_Activity.this,arrayList);
        recyclerView.setAdapter(adapter);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCall_NO_MSG(All_News_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.getNews,myPostJson(),"");
    }
    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("MyPOSTJSON",""+jsonObject);
        return jsonObject;
    }


    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(progressDialog!=null &&  progressDialog.isShowing())
            progressDialog.dismiss();
        nodata.setVisibility(View.VISIBLE);
        if(jsonObject!=null && status){
            nodata.setVisibility(View.GONE);
            Gson gson=new GsonBuilder().create();
            try {
                ArrayList<NewsModelList> rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<NewsModelList>>(){}.getType());
                arrayList.clear();
                arrayList.addAll(rm);
                adapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }
}