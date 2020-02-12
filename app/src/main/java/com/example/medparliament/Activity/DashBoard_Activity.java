package  com.example.medparliament.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.asksira.loopingviewpager.LoopingViewPager;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.medparliament.Adapter.AnnoucementsAdapter;
import com.example.medparliament.Adapter.Dashboard_News_Adapter;
import com.example.medparliament.Adapter.Dashboard_annoncement_adapter_new;
import com.example.medparliament.Adapter.Dashboard_events_adapter_new;
import com.example.medparliament.Adapter.Dashboard_gallery_adapter_new;
import com.example.medparliament.Adapter.Dashboard_news_adapter_new;
import com.example.medparliament.Adapter.Dashboard_video_adapter_new;
import com.example.medparliament.Adapter.DrawerI_Adapter;
import com.example.medparliament.Adapter.Event_Adapter;
import com.example.medparliament.Adapter.GalleryAdapter;
import com.example.medparliament.Adapter.Post_Adapter;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.Models.DashboardAnnouncedModel;
import com.example.medparliament.Internet.Models.DashboardGalleryModel;
import com.example.medparliament.Internet.Models.Dashboard_News_Model;
import com.example.medparliament.Internet.Models.Dashbooard_eventModel;
import com.example.medparliament.Internet.Models.DrawerModel;
import com.example.medparliament.Internet.Models.Post_Modle;
import com.example.medparliament.Internet.Models.Video_Model;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import io.fotoapparat.parameter.Flash;

public class DashBoard_Activity extends AppCompatActivity implements onResult {
    ListView listView;
    ImageView arrow;
    Segow_UI_Semi_Font moreNews;
    LinearLayout news,announce,event,gallery;
    Button login_signup;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Segow_UI_Semi_Font userName;
    ImageView logout;
    Toolbar toolbar;
    onResult onResult;
    RelativeLayout nodata;
    Post_Adapter adapterpost;
    ArrayList<Dashboard_News_Model> newslist;
    ArrayList<DashboardAnnouncedModel> announcedlist;
    ArrayList<DashboardGalleryModel>gallerylsit;
    ArrayList<Dashbooard_eventModel>eventlist;
    ArrayList<Video_Model> videolist;
    MySharedPrefrence m;
    LoopingViewPager  viewPager_gallery,viewPager_news,viewPager_announced,viewPager_event,viewPager_video;
    RecyclerView recycle_gallery,recyclerView_news,recyclerView_announced,recyclerView_event;
    String name;
    ImageView setting;
    CircleImageView profile;
    ProgressDialog progressDialog;
    FloatingActionButton cmnt;
    Dashboard_News_Adapter dashboard_news_adapter;
    LinearLayoutManager news_manager,gallery_Manager,announced_manage,event_manager;
    GalleryAdapter galleryAdapter;
    AnnoucementsAdapter annoucementsAdapter;
    Event_Adapter event_adapter;
    int counter=0;
    ImageView bell,user_login;
 LinearLayout video_layout;
    Dashboard_news_adapter_new viewpager_news_adapter;

    Dashboard_annoncement_adapter_new viewpager_annoncemen_adapter;
    Dashboard_events_adapter_new viewpager_events_adapter;
    Dashboard_gallery_adapter_new viewpager_gallery_adapter;
    Dashboard_video_adapter_new   viewpager_video_adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_);
        video_layout=findViewById(R.id.video_layout);
         bell=findViewById(R.id.bell);
         user_login=findViewById(R.id.user_login);
        login_signup=findViewById(R.id.login_signup);
        cmnt=findViewById(R.id.cmnt);
        logout=findViewById(R.id.logout);
        setting=findViewById(R.id.setting);
        arrow=findViewById(R.id.arrow);
        if(Comman.Check_Login(DashBoard_Activity.this)){
            bell.setVisibility(View.VISIBLE);
            cmnt.setVisibility(View.VISIBLE);
           logout.setVisibility(View.VISIBLE);
            setting.setVisibility(View.VISIBLE);
            login_signup.setVisibility(View.GONE);
        }else{
            setting.setVisibility(View.VISIBLE);
            cmnt.setVisibility(View.GONE);
            user_login.setVisibility(View.VISIBLE);
            login_signup.setVisibility(View.VISIBLE);
        }
        Animatoo.animateSwipeLeft(DashBoard_Activity.this);
        this.onResult=this;
        Intent i=getIntent();
        if(i!=null)
            name=i.getStringExtra("username");
        listView = findViewById(R.id.left_drawer);

        announcedlist=new ArrayList<>();
        newslist=new ArrayList<>();
        eventlist=new ArrayList<>();
        videolist =new ArrayList<>();
        gallerylsit=new ArrayList<>();

        nodata=findViewById(R.id.nodata);

        m=MySharedPrefrence.instanceOf(DashBoard_Activity.this);
        View stub = (View) findViewById(R.id.toolbar);


        announce=findViewById(R.id.anouced);
        news=findViewById(R.id.news);
        event=findViewById(R.id.Event);
        gallery=findViewById(R.id.gallery);

        toolbar=stub.findViewById(R.id.toolbar_actionbar);
        drawerLayout = findViewById(R.id.drawerlayut);
        userName=findViewById(R.id.username);

        profile=findViewById(R.id.profile);
        moreNews=findViewById(R.id.morenews);
        userName.setText(m.getUserName());
        Comman.setRoundedImage(DashBoard_Activity.this,profile,m.getUserProfile());
        progressDialog = new ProgressDialog(DashBoard_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Comman.log("UserName","jkdsf"+m.getUserName());


           DrawerModel[] drawerItem = new DrawerModel[5];
           drawerItem[0] = new DrawerModel(R.drawable.ic_home, "Home");
            drawerItem[1] = new DrawerModel(R.drawable.post, "My Posts");
            drawerItem[2] = new DrawerModel(R.drawable.post,"All Post");
            drawerItem[3] = new DrawerModel(R.drawable.ic_info, "About us");
            drawerItem[4] = new DrawerModel(R.drawable.ic_star, "Rate Now");





        DrawerI_Adapter adapter = new DrawerI_Adapter(this, R.layout.drawer_item_layout, drawerItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        setupDrawerToggle();
        recyclerView_announced=findViewById(R.id.recycle_announced);
        recyclerView_news=findViewById(R.id.recycle_news);
        recyclerView_event=findViewById(R.id.recycle_event);
        recycle_gallery=findViewById(R.id.recycle_gallery);


        viewPager_announced=findViewById(R.id.viewpager_announced);
        viewPager_news=findViewById(R.id.viewpager_news);
        viewPager_event=findViewById(R.id.viewpager_events);
        viewPager_gallery=findViewById(R.id.viewpager_gallery);
        viewPager_video=findViewById(R.id.viewpager_video);
         announced_manage=new LinearLayoutManager(DashBoard_Activity.this);
        news_manager=new LinearLayoutManager(DashBoard_Activity.this);
        event_manager=new LinearLayoutManager(DashBoard_Activity.this);
        gallery_Manager=new LinearLayoutManager(DashBoard_Activity.this);

///
        viewpager_annoncemen_adapter=new Dashboard_annoncement_adapter_new(DashBoard_Activity.this,announcedlist,true);
        viewpager_news_adapter=new Dashboard_news_adapter_new(DashBoard_Activity.this,newslist,true);

       viewpager_events_adapter=new Dashboard_events_adapter_new(DashBoard_Activity.this,eventlist,true);
       viewpager_gallery_adapter=new Dashboard_gallery_adapter_new(DashBoard_Activity.this,gallerylsit,true);
        viewpager_video_adapter=new Dashboard_video_adapter_new(DashBoard_Activity.this,videolist,true);
        viewPager_event.setAdapter(viewpager_events_adapter);
        viewPager_gallery.setAdapter( viewpager_gallery_adapter);
       viewPager_news.setAdapter(viewpager_news_adapter);
        viewPager_announced.setAdapter(viewpager_annoncemen_adapter);

        viewPager_video.setAdapter(viewpager_video_adapter);

        ///

        annoucementsAdapter=new AnnoucementsAdapter(DashBoard_Activity.this,announcedlist);
        dashboard_news_adapter=new Dashboard_News_Adapter(DashBoard_Activity.this,newslist);
        event_adapter=new Event_Adapter(DashBoard_Activity.this,eventlist);
        galleryAdapter=new GalleryAdapter(DashBoard_Activity.this,gallerylsit);


        announced_manage.setOrientation(RecyclerView.HORIZONTAL);
        news_manager.setOrientation(RecyclerView.HORIZONTAL);
        event_manager.setOrientation(RecyclerView.HORIZONTAL);
        gallery_Manager.setOrientation(RecyclerView.HORIZONTAL);

        recyclerView_announced.setLayoutManager(announced_manage);
        recyclerView_news.setLayoutManager(news_manager);
        recyclerView_event.setLayoutManager(event_manager);
        recycle_gallery.setLayoutManager(gallery_Manager);

        recyclerView_announced.setAdapter(annoucementsAdapter);
        recyclerView_news.setAdapter(dashboard_news_adapter);
        recyclerView_event.setAdapter(event_adapter);
        recycle_gallery.setAdapter(galleryAdapter);
        setupToolbar();
        moreNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard_Activity.this,All_News_Activity.class));
//                if(Comman.Check_Login(DashBoard_Activity.this)){
//
//                    startActivity(new Intent(DashBoard_Activity.this,All_News_Activity.class));
//                }else {
//                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
//                }

            }
        });
        cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard_Activity.this,Comment_Activity.class));
            }
        });
        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDialogeBox(DashBoard_Activity.this,"Information","Do you want to Logout from your Account?");
            }
        });
        profile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Comman.Check_Login(DashBoard_Activity.this)) {
                    startActivity(new Intent(DashBoard_Activity.this, User_profile_Activity.class));
                }else{
                    startActivity(new Intent(DashBoard_Activity.this,Login_Signup_Activity.class));
                }drawerLayout.closeDrawers();
            }
        });

        setting.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Comman.Check_Login(DashBoard_Activity.this)) {
                    //startActivity(new Intent(DashBoard_Activity.this, User_profile_Activity.class));
                }else{
                    startActivity(new Intent(DashBoard_Activity.this,Login_Signup_Activity.class));
                }drawerLayout.closeDrawers();
            }
        });
        login_signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard_Activity.this,AboutUsActivity.class));
            }
        });
        user_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard_Activity.this,Login_Signup_Activity.class));
            }
        });
         arrow.setVisibility(View.GONE);
        arrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                recyclerView_announced.getLayoutManager().scrollToPosition(announced_manage.findLastVisibleItemPosition() + 1);
                Comman.log("asdfdsafdfsdas",":adsffadsdasf");
            }
        });
    }
    @Override
    public void onResult(JSONObject jsonObject,Boolean status) {
        if(progressDialog!=null &&  progressDialog.isShowing())
        progressDialog.dismiss();
        nodata.setVisibility(View.VISIBLE);
        if(jsonObject!=null && status) {
            Gson gson = new GsonBuilder().create();
            try {
                Comman.log("DashBoarding",""+jsonObject);
                nodata.setVisibility(View.GONE);

                if(jsonObject.getJSONArray("announcement").length()>0) {
                    ArrayList<DashboardAnnouncedModel> dash_anounc_list = gson.fromJson(jsonObject.getString("announcement"), new TypeToken<ArrayList<DashboardAnnouncedModel>>() {
                    }.getType());
                    if(dash_anounc_list!=null){
                        announcedlist.clear();
                        announcedlist.addAll(dash_anounc_list);
                        if(Comman.Check_Login(DashBoard_Activity.this)){
                        announce.setVisibility(View.VISIBLE);}}
                }
                if(jsonObject.getJSONArray("news").length()>0){
                ArrayList<Dashboard_News_Model> dash_news_list = gson.fromJson(jsonObject.getString("news"), new TypeToken<ArrayList<Dashboard_News_Model>>() {}.getType());
                    if(newslist!=null){
                        newslist.clear();
                        newslist.addAll(dash_news_list);

                    news.setVisibility(View.VISIBLE);
                    }
                }
                if((jsonObject.getJSONArray("event").length()>0)){
                ArrayList<Dashbooard_eventModel> dash_event_list = gson.fromJson(jsonObject.getString("event"), new TypeToken<ArrayList<Dashbooard_eventModel>>() {}.getType());
                    if(eventlist!=null){
                        eventlist.clear();
                        eventlist.addAll(dash_event_list);
                       event.setVisibility(View.VISIBLE);
                    }
                }
                if((jsonObject.getJSONArray("gallery").length()>0)){
                ArrayList<DashboardGalleryModel> dash_gallery_list = gson.fromJson(jsonObject.getString("gallery"), new TypeToken<ArrayList<DashboardGalleryModel>>() {}.getType());
                    if(gallerylsit!=null){
                        gallerylsit.clear();
                        gallerylsit.addAll(dash_gallery_list);
                    gallery.setVisibility(View.VISIBLE);}
                }
                if((jsonObject.getJSONArray("promisingInitiatives").length()>0)){
                    Log.d("videos",jsonObject.toString());
                    ArrayList<Video_Model> dash_vid_list = gson.fromJson(jsonObject.getString("promisingInitiatives"), new TypeToken<ArrayList<Video_Model>>() {}.getType());
                    if(videolist!=null){
                        videolist.clear();
                        videolist.addAll(dash_vid_list);

                        video_layout.setVisibility(View.VISIBLE);}
                }




                Comman.log("SizeAAA",""+announcedlist.size());
                Comman.log("SizeNNN",""+newslist.size());
                Comman.log("SizeEEE",""+eventlist.size());
                Comman.log("SizeGGG",""+gallerylsit.size());
               viewpager_news_adapter.setItemList(newslist);
                viewPager_news.reset(); //In order t
                viewpager_events_adapter.setItemList(eventlist);
                viewPager_event.reset(); //In order t
                viewpager_annoncemen_adapter.setItemList(announcedlist);
                viewPager_announced.reset(); //In order t
                viewpager_gallery_adapter.setItemList(gallerylsit);
                viewPager_gallery.reset(); //In order t
                viewpager_video_adapter.setItemList(videolist);
                viewPager_video.reset(); //In order t

                annoucementsAdapter.notifyDataSetChanged();
                dashboard_news_adapter.notifyDataSetChanged();
                event_adapter.notifyDataSetChanged();
                galleryAdapter.notifyDataSetChanged();
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
    private class DrawerItemClickListener implements ListView.OnItemClickListener {

        @SuppressLint("ResourceAsColor")
        @Override
        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            selectItem(position);
        }

    }

    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCall_NO_MSG(DashBoard_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.landingPageDashboard,myPostJson(),"MY_POST_LIST");
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Comman.log("SSSS","SSSSSS");
//        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
//            return true;
//        }
//        return super.onOptionsItemSelected(item);
        if (item.getItemId() == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            else {
                drawerLayout.openDrawer(GravityCompat.START);
            }

            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        actionBarDrawerToggle.syncState();
    }
    private void selectItem(int position) {
        switch (position)
        {     case 0:
            startActivity(new Intent(DashBoard_Activity.this, DashBoard_Activity.class));

            drawerLayout.closeDrawers();
            break;
            case 1:
                if(Comman.Check_Login(DashBoard_Activity.this)) {
                    startActivity(new Intent(DashBoard_Activity.this, My_Post_Activity.class));
                }else{
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }

                drawerLayout.closeDrawers();
                break;

            case 2:
                if(Comman.Check_Login(DashBoard_Activity.this)){
                    startActivity(new Intent(DashBoard_Activity.this, All_Post_Activity.class));
                }else {
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));

                }
                drawerLayout.closeDrawers();
                break;

            case 3:
                if(Comman.Check_Login(DashBoard_Activity.this)) {

                }else{
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }

                drawerLayout.closeDrawers();
                break;
            case 4:
                if(Comman.Check_Login(DashBoard_Activity.this)) {

                }else{
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }

                drawerLayout.closeDrawers();
                break;

        }

    }

    void setupToolbar() {
        setSupportActionBar(toolbar);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowTitleEnabled(false);
        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
    }

    void
    setupDrawerToggle() {
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
//        This is necessary to change the icon of the Drawer Toggle upon state change.;
//        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
//        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
        actionBarDrawerToggle.syncState();
    }


    public void showDialogeBox(Context context, String title, String msg)
    {
        final MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context,R.style.custom_dialog);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_layout, null, false);
        Button yes=dialogView.findViewById(R.id.yes);
        Button no=dialogView.findViewById(R.id.no);
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
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                m.clearData();
                startActivity(new Intent(DashBoard_Activity.this,DashBoard_Activity.class));
                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
            finishAffinity();
        Animatoo.animateSwipeRight(DashBoard_Activity.this);
    }


    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            if(Comman.Check_Login(DashBoard_Activity.this)){
           jsonObject.put("userTypeId",m.getUserTypeId());
            jsonObject.put("userId",m.getUserId());}
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("MyPOSTJSON",""+jsonObject);
        return jsonObject;

    }
}
