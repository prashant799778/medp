package com.medparliament.Activity;

import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Adapter.All_Post_Adapter;
import com.medparliament.Adapter.Post_Adapter;
import com.medparliament.Internet.Models.Post_Modle;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class All_Post_Activity extends Base_Activity implements onResult {

    ImageButton bck;
    FloatingActionButton cmnt;
    RecyclerView recyclerView;
    MySharedPrefrence m;
    Post_Adapter adapter;
    ArrayList<Post_Modle> arrayList;
    LinearLayoutManager manager;
    onResult onResult;
    All_Post_Adapter post_page_adapter;
    RelativeLayout nodata;
    TabLayout tabLayout;
    ViewPager viewPager;
    ProgressDialog progressDialog;
    private int[] tabIcons = {
            R.drawable.post,
            R.drawable.white_note,
            R.drawable.white_note
    };
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__post_);
        m=MySharedPrefrence.instanceOf(All_Post_Activity.this);
        this.onResult=this;
        Window window = getWindow();
        nodata=findViewById(R.id.nodata);
        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tabs);
        tabLayout.setVisibility(View.GONE);
        post_page_adapter=new All_Post_Adapter(getSupportFragmentManager(),this);
        viewPager.setAdapter(post_page_adapter);
        tabLayout.setupWithViewPager(viewPager);

        setupTabIcons();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        recyclerView=findViewById(R.id.recycle);
//        arrayList=new ArrayList<>();
//        manager=new LinearLayoutManager(My_Post_Activity.this);
//        recyclerView.setLayoutManager(manager);
//        adapter=new Post_Adapter(My_Post_Activity.this,arrayList,m.getUserName(),1);
//        recyclerView.setAdapter(adapter);
        Animatoo.animateSwipeLeft(All_Post_Activity.this);
        bck=findViewById(R.id.bck);
        cmnt=findViewById(R.id.cmnt);
        progressDialog = new ProgressDialog(All_Post_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
//        progressDialog.show();
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(All_Post_Activity.this, Comment_Activity.class));
            }
        });
        tabLayout.getTabAt(0).setIcon(R.drawable.post);
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.post);

            }
            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(R.drawable.white_note);

            }
            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(All_Post_Activity.this);
    }
    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("userId",""+m.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("MyPOSTJSON",""+jsonObject);
        return jsonObject;
    }

    @Override
    public void onResult(JSONObject jsonObject,Boolean status) {
//        progressDialog.dismiss();
//        nodata.setVisibility(View.VISIBLE);
//        if(jsonObject!=null && status){
//            nodata.setVisibility(View.GONE);
////        Comman.log("RRRR",""+jsonObject);
//        Gson gson=new GsonBuilder().create();
//        progressBar.setVisibility(View.GONE);
//        try {
//            ArrayList<Post_Modle> rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<Post_Modle>>(){}.getType());
//            arrayList.clear();
//            arrayList.addAll(rm);
//            adapter.notifyDataSetChanged();
//        } catch (JSONException e) {
//            e.printStackTrace();
//        }
//    }
    }
    private void setupTabIcons() {
        tabLayout.getTabAt(0).setIcon(tabIcons[0]);
//        tabLayout.getTabAt(1).setIcon(tabIcons[1]);
//        tabLayout.getTabAt(2).setIcon(tabIcons[2]);
    }
}
