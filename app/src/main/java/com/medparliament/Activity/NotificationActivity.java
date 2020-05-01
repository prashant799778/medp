package com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medparliament.Adapter.Post_Adapter;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.Post_Modle;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class NotificationActivity extends AppCompatActivity implements com.medparliament.Internet.onResult {
    MySharedPrefrence m;
    com.medparliament.Internet.onResult onResult;
    ImageButton bck;
    FloatingActionButton cmnt;
    RecyclerView recyclerView;
    Post_Adapter adapter;
    ArrayList<Post_Modle> arrayList;
    LinearLayoutManager manager;
    RelativeLayout nodata;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notification);
        Animatoo.animateSlideLeft(NotificationActivity.this);
        m=MySharedPrefrence.instanceOf(NotificationActivity.this);
        progressDialog = new ProgressDialog(NotificationActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        this.onResult=this;
        nodata=findViewById(R.id.nodata);
        bck=findViewById(R.id.bck);
        recyclerView=findViewById(R.id.noti_recycleView);
        arrayList=new ArrayList<>();
        manager=new LinearLayoutManager(NotificationActivity.this);
        recyclerView.setLayoutManager(manager);
        adapter=new Post_Adapter(NotificationActivity.this,arrayList,m.getUserName(),1);
        recyclerView.setAdapter(adapter);

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("userId",""+m.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("MyPOSTJSONsxsacasc",""+jsonObject);
        return jsonObject;
    }

    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCall_NO_MSG(NotificationActivity.this,getWindow().getDecorView().getRootView(),onResult, URLS.Notification2, myPostJson(), "MY_POST_LIST");

    }




    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(progressDialog!=null &&  progressDialog.isShowing())
            progressDialog.dismiss();


        if(jsonObject!=null && status){
            nodata.setVisibility(View.GONE);
            Gson gson=new GsonBuilder().create();
            try {
                if(jsonObject.getJSONArray("result").length()>0){
                    ArrayList<Post_Modle> rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<Post_Modle>>(){}.getType());
                    arrayList.clear();
                    arrayList.addAll(rm);
                    adapter.notifyDataSetChanged();}else {
                    arrayList.clear();
                    adapter.notifyDataSetChanged();

                    nodata.setVisibility(View.VISIBLE);
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

         else{
            arrayList.clear();
            adapter.notifyDataSetChanged();

            nodata.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSlideRight(NotificationActivity.this);

    }
}
