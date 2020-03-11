package  com.medparliament.Activity;

import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.annotation.SuppressLint;
import android.app.ProgressDialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.text.Html;
import android.util.Base64;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.medparliament.Adapter.AnnoucementsAdapter;
import com.medparliament.Adapter.Dashboard_News_Adapter;
import com.medparliament.Adapter.Dashboard_Promising_Adapter;
import com.medparliament.Adapter.Dashboard_annoncement_adapter_new;
import com.medparliament.Adapter.Dashboard_council_adapter_new;
import com.medparliament.Adapter.Dashboard_events_adapter_new;
import com.medparliament.Adapter.Dashboard_market_adapter_new;
import com.medparliament.Adapter.Dashboard_news_adapter_new;
import com.medparliament.Adapter.Dashboard_video_adapter_new;
import com.medparliament.Adapter.DrawerI_Adapter;
import com.medparliament.Adapter.Event_Adapter;
import com.medparliament.Adapter.GalleryAdapter;
import com.medparliament.Adapter.Gallery_adapter;
import com.medparliament.Adapter.Post_Adapter;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.DashboardAnnouncedModel;
import com.medparliament.Internet.Models.DashboardGalleryModel;
import com.medparliament.Internet.Models.Dashboard_News_Model;
import com.medparliament.Internet.Models.Dashbooard_eventModel;
import com.medparliament.Internet.Models.DrawerModel;
import com.medparliament.Internet.Models.Video_Model;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.Internet.NewModel.up_skill_model;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

import de.hdodenhof.circleimageview.CircleImageView;

public class DashBoard_Activity extends AppCompatActivity implements onResult {

    private static final long DELAY_MS = 500;
    private static final long PERIOD_MS = 10000;
    int currentPage=0;
    int currentPage1=0;
    int currentPage2=0;
    int currentPage3=0;
    int currentPage4=0;
    int currentPage5=0;
    Segow_UI_Semi_Font v1,v2,v3,v4,v5,v7,v0,partner_view;
    ListView listView;
    RelativeLayout about;
    ImageView arrow;
    Segow_UI_Semi_Font moreNews;
    LinearLayout news,announce,event,gallery,council,market,our_partner;
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
    ArrayList<Result> marketlist;
    ArrayList<Result> prolist;
    ArrayList<DashboardAnnouncedModel> announcedlist;
    ArrayList<DashboardGalleryModel>gallerylsit;
    ArrayList<DashboardGalleryModel>partnarlist;
    ArrayList<Dashbooard_eventModel>eventlist;
    ArrayList<up_skill_model>councillist;
    ArrayList<Video_Model> videolist;
    MySharedPrefrence m;
    ViewPager viewPager_gallery,viewPager_news,viewPager_announced,viewPager_event,viewPager_video,viewPager_Promising,viewPager_council,viewPager_market;
   RecyclerView recyclerView_gallery,recyclerView_partner;
    String name;
    ImageView setting;
    CircleImageView profile;
    ProgressDialog progressDialog;
    FloatingActionButton cmnt;
    Dashboard_News_Adapter dashboard_news_adapter;
    LinearLayoutManager news_manager,gallery_Manager,announced_manage,event_manager,partner_manager;
    GalleryAdapter galleryAdapter;
    AnnoucementsAdapter annoucementsAdapter;
    Event_Adapter event_adapter;
    int counter=0;
    ImageView bell,user_login;
    LinearLayout video_layout,promising_layout;
    Dashboard_news_adapter_new viewpager_news_adapter;


    Dashboard_market_adapter_new viewpager_market_adapter;

    GalleryAdapter galleryAdapter1;

    GalleryAdapter partnerAdapter;

    Dashboard_annoncement_adapter_new viewpager_annoncemen_adapter;
    Dashboard_events_adapter_new viewpager_events_adapter;
    Dashboard_council_adapter_new viewpager_council_adapter;
    Gallery_adapter viewpager_gallery_adapter;
    Dashboard_video_adapter_new viewpager_video_adapter;
    Dashboard_Promising_Adapter viewpager_Promising_adapter;




    public static void printHashKey(Context pContext) {
        try {
            PackageInfo info = pContext.getPackageManager().getPackageInfo(pContext.getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String hashKey = new String(Base64.encode(md.digest(), 0));
                Log.i("hashkey", "printHashKey() Hash Key: " + hashKey);
            }
        } catch (NoSuchAlgorithmException e) {
            Log.e("hashkey", "printHashKey()", e);
        } catch (Exception e) {
            Log.e("hashkey" ,"printHashKey()", e);
        }}


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_);
        Comman.log("OnStartMethod","onCreare");
        video_layout=findViewById(R.id.video_layout);
        promising_layout=findViewById(R.id.promissingInitiative);
         bell=findViewById(R.id.bell);
//         user_login=findViewById(R.id.user_login);
        login_signup=findViewById(R.id.login_signup);
        cmnt=findViewById(R.id.cmnt);
        logout=findViewById(R.id.logout);
        about=findViewById(R.id.about);
        setting=findViewById(R.id.setting);
        arrow=findViewById(R.id.arrow);
        if(Comman.Check_Login(DashBoard_Activity.this)){
            bell.setVisibility(View.VISIBLE);
            cmnt.setVisibility(View.VISIBLE);
           logout.setVisibility(View.VISIBLE);
            setting.setVisibility(View.VISIBLE);
            login_signup.setVisibility(View.GONE);
            about.setVisibility(View.GONE);
        }else{
            setting.setVisibility(View.VISIBLE);
            cmnt.setVisibility(View.GONE);
//            user_login.setVisibility(View.VISIBLE);
            login_signup.setVisibility(View.VISIBLE);
        }
        Animatoo.animateSwipeLeft(DashBoard_Activity.this);
        v1=findViewById(R.id.v1);
        v2=findViewById(R.id.v3);
        v3=findViewById(R.id.v4);
        v4=findViewById(R.id.v5);
        v5=findViewById(R.id.v6);
        v7=findViewById(R.id.v7);
        v0=findViewById(R.id.v0);
        partner_view=findViewById(R.id.partnerv);
        this.onResult=this;
        Intent i=getIntent();
        if(i!=null)
            name=i.getStringExtra("username");
        listView = findViewById(R.id.left_drawer);

        announcedlist=new ArrayList<>();
        newslist=new ArrayList<>();
        marketlist=new ArrayList<>();
        prolist=new ArrayList<>();
        eventlist=new ArrayList<>();
        councillist=new ArrayList<>();
        videolist =new ArrayList<>();
        gallerylsit=new ArrayList<>();
        partnarlist=new ArrayList<>();

        nodata=findViewById(R.id.nodata);

        m=MySharedPrefrence.instanceOf(DashBoard_Activity.this);
        View stub = (View) findViewById(R.id.toolbar);


        announce=findViewById(R.id.anouced);
        news=findViewById(R.id.news);
        event=findViewById(R.id.Event);
        gallery=findViewById(R.id.gallery);
        market=findViewById(R.id.market);
        council=findViewById(R.id.council);
        our_partner=findViewById(R.id.partner);

        toolbar=stub.findViewById(R.id.toolbar_actionbar);
        drawerLayout = findViewById(R.id.drawerlayut);
        userName=findViewById(R.id.username);

        profile=findViewById(R.id.profile);
        moreNews=findViewById(R.id.morenews);
        Comman.setRoundedImage(DashBoard_Activity.this,profile,m.getUserProfile());
        progressDialog = new ProgressDialog(DashBoard_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Comman.log("UserName","jkdsf"+m.getUserName());


        DrawerModel[] drawerItem = new DrawerModel[6];
        drawerItem[0] = new DrawerModel(R.drawable.ic_internet_, "Home");
        drawerItem[1] = new DrawerModel(R.drawable.ic_note_new, "Inbox");
        drawerItem[2] = new DrawerModel(R.drawable.ic_sent,"Sent");
        drawerItem[3] = new DrawerModel(R.drawable.ic_info, "About us");
        drawerItem[4] = new DrawerModel(R.drawable.ic_star, "Rate Now");
        drawerItem[5] = new DrawerModel(R.drawable.ic_share, "Refer your friend");
        v1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(DashBoard_Activity.this,Tabs_DashBoard_Activity.class));
//                finish();
            }
        });
        v5.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DashBoard_Activity.this,Tabs_DashBoard_Activity.class));
                if(Comman.Check_Login(DashBoard_Activity.this)) {

                    Intent intent = new Intent(DashBoard_Activity.this, Tabs_DashBoard_Activity.class);
                    intent.putExtra("id", "5");
                    startActivity(intent);
                }else {
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }

//                finish();
            }
        });
        v2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DashBoard_Activity.this,Tabs_DashBoard_Activity.class));

                if(Comman.Check_Login(DashBoard_Activity.this)) {

                    Intent intent = new Intent(DashBoard_Activity.this, Tabs_DashBoard_Activity.class);
                    intent.putExtra("id", "2");
                    startActivity(intent);
                }else {
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }
//                finish();
            }
        });

        partner_view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DashBoard_Activity.this,Tabs_DashBoard_Activity.class));

                if(Comman.Check_Login(DashBoard_Activity.this)) {

                    Intent intent = new Intent(DashBoard_Activity.this, Tabs_DashBoard_Activity.class);
                    intent.putExtra("id", "7");
                    startActivity(intent);
                }else {
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }
//                finish();
            }
        });


        v3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DashBoard_Activity.this,Tabs_DashBoard_Activity.class));
                if(Comman.Check_Login(DashBoard_Activity.this)) {

                    Intent intent = new Intent(DashBoard_Activity.this, Tabs_DashBoard_Activity.class);
                    intent.putExtra("id", "3");
                    startActivity(intent);
                }else {
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }
//                finish();
            }
        });
        v4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DashBoard_Activity.this,Tabs_DashBoard_Activity.class));




                if(Comman.Check_Login(DashBoard_Activity.this)) {

                    Intent intent = new Intent(DashBoard_Activity.this, Tabs_DashBoard_Activity.class);
                    intent.putExtra("id", "4");
                    startActivity(intent);
                }else {
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }
//                finish();
            }
        });
        v7.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DashBoard_Activity.this,Tabs_DashBoard_Activity.class));
//
                if(Comman.Check_Login(DashBoard_Activity.this)) {

                    Intent intent = new Intent(DashBoard_Activity.this, Tabs_DashBoard_Activity.class);
                    intent.putExtra("id", "5");
                    startActivity(intent);
                }else {
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }
//                finish();
            }
        });

        v0.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Comman.Check_Login(DashBoard_Activity.this)) {
                    Intent intent = new Intent(DashBoard_Activity.this, Tabs_DashBoard_Activity.class);
                    intent.putExtra("id", "0");
                    startActivity(intent);
                }else {
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }
//
//                finish();
            }
        });



        DrawerI_Adapter adapter = new DrawerI_Adapter(this, R.layout.drawer_item_layout, drawerItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        setupDrawerToggle();

        viewPager_announced=findViewById(R.id.viewpager_announced);
        viewPager_news=findViewById(R.id.viewpager_news);
        viewPager_market=findViewById(R.id.viewpager_market);
        viewPager_council=findViewById(R.id.viewpager_council);
        viewPager_event=findViewById(R.id.viewpager_events);
        viewPager_gallery=findViewById(R.id.viewpager_gallery);
        viewPager_video=findViewById(R.id.viewpager_video);
        viewPager_Promising=findViewById(R.id.viewpager_promissing);
        recyclerView_gallery=findViewById(R.id.recycle_gallery);
        recyclerView_partner=findViewById(R.id.recycle_partner);
        gallery_Manager=new LinearLayoutManager(DashBoard_Activity.this);
        gallery_Manager.setOrientation(RecyclerView.HORIZONTAL);


        partner_manager=new LinearLayoutManager(DashBoard_Activity.this);
        partner_manager.setOrientation(RecyclerView.HORIZONTAL);


        recyclerView_gallery.setLayoutManager(gallery_Manager);

        recyclerView_partner.setLayoutManager(partner_manager);

        viewpager_Promising_adapter=new Dashboard_Promising_Adapter(DashBoard_Activity.this,prolist,true);
        viewpager_news_adapter=new Dashboard_news_adapter_new(DashBoard_Activity.this,newslist,true);


        viewpager_market_adapter=new Dashboard_market_adapter_new(DashBoard_Activity.this,marketlist,true);


        viewpager_video_adapter=new Dashboard_video_adapter_new(DashBoard_Activity.this,videolist,true);

        viewpager_annoncemen_adapter=new Dashboard_annoncement_adapter_new(DashBoard_Activity.this,announcedlist,true);
        viewpager_events_adapter=new Dashboard_events_adapter_new(DashBoard_Activity.this,eventlist,true);

        viewpager_council_adapter=new Dashboard_council_adapter_new(DashBoard_Activity.this,councillist,true);

        viewpager_gallery_adapter=new Gallery_adapter(DashBoard_Activity.this,gallerylsit, true);
       galleryAdapter1=new GalleryAdapter(DashBoard_Activity.this,gallerylsit);

        partnerAdapter=new GalleryAdapter(DashBoard_Activity.this,partnarlist);

       recyclerView_gallery.setAdapter(galleryAdapter1);

        recyclerView_partner.setAdapter(partnerAdapter);

        viewPager_news.setAdapter(viewpager_news_adapter);

        viewPager_market.setAdapter(viewpager_market_adapter);

        viewPager_council.setAdapter(viewpager_council_adapter);
        viewPager_video.setAdapter(viewpager_video_adapter);
        viewPager_event.setAdapter(viewpager_events_adapter);
        viewPager_gallery.setAdapter( viewpager_gallery_adapter);
        viewPager_announced.setAdapter(viewpager_annoncemen_adapter);
        viewPager_Promising.setAdapter(viewpager_Promising_adapter);

        setupToolbar();
        moreNews.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                startActivity(new Intent(DashBoard_Activity.this,Tabs_DashBoard_Activity.class));
           if(Comman.Check_Login(DashBoard_Activity.this)) {
               Intent intent = new Intent(DashBoard_Activity.this, Tabs_DashBoard_Activity.class);
               intent.putExtra("id", "1");
               startActivity(intent);
           }else {
               startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
           }


//                finish();
//                startActivity(new Intent(DashBoard_Activity.this,All_News_Activity.class));
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
//                startActivity(new Intent(DashBoard_Activity.this,AboutUsActivity.class));
                startActivity(new Intent(DashBoard_Activity.this,Login_Signup_Activity.class));
            }
        });
//        user_login.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                startActivity(new Intent(DashBoard_Activity.this,Login_Signup_Activity.class));
//            }
//        });
         arrow.setVisibility(View.GONE);






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

                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {

                            public void run() {
                                if (currentPage1 == announcedlist.size()-1) {
                                    currentPage1 = 0;
                                }
                                viewPager_announced.setCurrentItem(currentPage1++, true);
                            }
                        };

                        Timer   timer = new Timer(); // This will create a new Thread
                        timer.schedule(new TimerTask() { // task to be scheduled
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);




                        announce.setVisibility(View.GONE);}
                }
                if(jsonObject.getJSONArray("news").length()>0){
                ArrayList<Dashboard_News_Model> dash_news_list = gson.fromJson(jsonObject.getString("news"), new TypeToken<ArrayList<Dashboard_News_Model>>() {}.getType());
                    if(newslist!=null){
                        newslist.clear();
                        newslist.addAll(dash_news_list);

                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {

                            public void run() {
                                if (currentPage == newslist.size()-1) {
                                    currentPage = 0;
                                }
                                viewPager_news.setCurrentItem(currentPage++, true);
                            }
                        };

                        Timer   timer = new Timer(); // This will create a new Thread
                        timer.schedule(new TimerTask() { // task to be scheduled
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);
                     promising_layout.setVisibility(View.VISIBLE);
                    news.setVisibility(View.VISIBLE);
                    }
                }


                if(jsonObject.getJSONArray("marketingInsights").length()>0){
                    ArrayList<Result> dash_news_list = gson.fromJson(jsonObject.getString("marketingInsights"), new TypeToken<ArrayList<Result>>() {}.getType());
                    if(marketlist!=null){
                        marketlist.clear();
                        marketlist.addAll(dash_news_list);
                        viewpager_market_adapter=new Dashboard_market_adapter_new(DashBoard_Activity.this,marketlist,true);

//                        final Handler handler = new Handler();
//                        final Runnable Update = new Runnable() {
//
//                            public void run() {
//                                if (currentPage == newslist.size()-1) {
//                                    currentPage = 0;
//                                }
//                                viewPager_market.setCurrentItem(currentPage++, true);
//                            }
//                        };
//
//                        Timer   timer = new Timer(); // This will create a new Thread
//                        timer.schedule(new TimerTask() { // task to be scheduled
//                            @Override
//                            public void run() {
//                                handler.post(Update);
//                            }
//                        }, DELAY_MS, PERIOD_MS);
//                        promising_layout.setVisibility(View.VISIBLE);
                        viewPager_market.setAdapter(viewpager_market_adapter);
                        viewpager_market_adapter.notifyDataSetChanged();
                        market.setVisibility(View.VISIBLE);
                    }
                }
                if((jsonObject.getJSONArray("event").length()>0)){
                    ArrayList<Dashbooard_eventModel> dash_event_list = gson.fromJson(jsonObject.getString("event"), new TypeToken<ArrayList<Dashbooard_eventModel>>() {}.getType());
                    if(eventlist!=null){
                        eventlist.clear();
                        eventlist.addAll(dash_event_list);
                        viewpager_events_adapter=new Dashboard_events_adapter_new(DashBoard_Activity.this,eventlist,true);
                        viewPager_event.setAdapter(viewpager_events_adapter);
//                        Comman.log("AAAAAAA","DASHBOARD"+eventlist.get(0).getMakedone());
//                        final Handler handler = new Handler();
//                        final Runnable Update = new Runnable() {
//
//                            public void run() {
//                                if (currentPage2 == eventlist.size()-1) {
//                                    currentPage2 = 0;
//                                }
//                                viewPager_event.setCurrentItem(currentPage2++, true);
//                            }
//                        };
//
//                        Timer   timer = new Timer(); // This will create a new Thread
//                        timer.schedule(new TimerTask() { // task to be scheduled
//                            @Override
//                            public void run() {
//                                handler.post(Update);
//                            }
//                        }, DELAY_MS, PERIOD_MS);
//                        Comman.log("AAAAAAA","DASHBOARD@@"+eventlist.get(0).getMakedone());
                        event.setVisibility(View.VISIBLE);
                        viewpager_events_adapter.updateList(eventlist);
                        viewpager_events_adapter.notifyDataSetChanged();
                    }
                }


                if((jsonObject.getJSONObject("upSkillsOpportunity").getJSONArray("featured Programs").length()>0)){
                    ArrayList<up_skill_model> dash_event_list = gson.fromJson(jsonObject.getJSONObject("upSkillsOpportunity").getString("featured Programs"), new TypeToken<ArrayList<up_skill_model>>() {}.getType());
                    if(councillist!=null){
                        councillist.clear();
                        councillist.addAll(dash_event_list);

//                        final Handler handler = new Handler();
//                        final Runnable Update = new Runnable() {

//                            public void run() {
//                                if (currentPage5 == councillist.size()-1) {
//                                    currentPage5 = 0;
//                                }
//                                viewPager_council.setCurrentItem(currentPage5++, true);
//                            }
//                        };
//                        Timer   timer = new Timer(); // This will create a new Thread
//                        timer.schedule(new TimerTask() { // task to be scheduled
//                            @Override
//                            public void run() {
//                                handler.post(Update);
//                            }
//                        }, DELAY_MS, PERIOD_MS);
                        viewpager_council_adapter.notifyDataSetChanged();
                        council.setVisibility(View.VISIBLE);
                    }
                }





                if((jsonObject.getJSONArray("gallery").length()>0)){
                ArrayList<DashboardGalleryModel> dash_gallery_list = gson.fromJson(jsonObject.getString("gallery"), new TypeToken<ArrayList<DashboardGalleryModel>>() {}.getType());
                    if(gallerylsit!=null){
                        gallerylsit.clear();
                        gallerylsit.addAll(dash_gallery_list);



                        gallery.setVisibility(View.VISIBLE);}
                }


                if((jsonObject.getJSONObject("ourPartners").getJSONArray("result").length()>0)){
                    ArrayList<DashboardGalleryModel> dash_gallery_list = gson.fromJson(jsonObject.getJSONObject("ourPartners").getString("result"), new TypeToken<ArrayList<DashboardGalleryModel>>() {}.getType());
                    if(partnarlist!=null){
                        partnarlist.clear();
                        partnarlist.addAll(dash_gallery_list);



                        our_partner.setVisibility(View.VISIBLE);}
                }



                if((jsonObject.getJSONArray("promisingInitiatives").length()>0)){
                    Log.d("videos",jsonObject.toString());
                    ArrayList<Video_Model> dash_vid_list = gson.fromJson(jsonObject.getString("promisingInitiatives"), new TypeToken<ArrayList<Video_Model>>() {}.getType());
                    if(videolist!=null){
                        //videolist.clear();
                        videolist.addAll(dash_vid_list);
                        video_layout.setVisibility(View.VISIBLE);}
                }
                if(jsonObject.getJSONArray("promissingIntiatives").length()>0){
                    ArrayList<Result> dash_news_list = gson.fromJson(jsonObject.getString("promissingIntiatives"), new TypeToken<ArrayList<Result>>() {}.getType());
                    if(prolist!=null){
                        prolist.clear();
                        prolist.addAll(dash_news_list);

                        final Handler handler = new Handler();
                        final Runnable Update = new Runnable() {

                            public void run() {
                                if (currentPage4 == prolist.size()-1) {
                                    currentPage4 = 0;
                                }
                                viewPager_news.setCurrentItem(currentPage4++, true);
                            }
                        };

                        Timer   timer = new Timer(); // This will create a new Thread
                        timer.schedule(new TimerTask() { // task to be scheduled
                            @Override
                            public void run() {
                                handler.post(Update);
                            }
                        }, DELAY_MS, PERIOD_MS);
                     promising_layout.setVisibility(View.VISIBLE);
                    }
                }


                Comman.log("SizeAAA",""+announcedlist.size());
                Comman.log("SizeNNN",""+newslist.size());
                Comman.log("SizeGGG",""+gallerylsit.size());
                viewpager_video_adapter.updateList(videolist);
                viewpager_news_adapter.updateList(newslist);
                viewpager_Promising_adapter.updateList(prolist);
                Comman.log("SizeNNN",""+newslist.size());
                viewpager_annoncemen_adapter.updateList(announcedlist);
                viewpager_gallery_adapter.updateList(gallerylsit);
                galleryAdapter1.notifyDataSetChanged();

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
        Comman.log("OnStartMethod","onStartMethod");
        Comman.setRoundedImage(DashBoard_Activity.this,profile,m.getUserProfile());
        userName.setText(m.getUserName());
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
//                if(Comman.Check_Login(DashBoard_Activity.this)) {
                    startActivity(new Intent(DashBoard_Activity.this, AboutUsActivity.class));

//                }else{
//                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
//                }
                drawerLayout.closeDrawers();
                break;
            case 4:
                if(Comman.Check_Login(DashBoard_Activity.this)) {
                        try {
//                            startActivity(new Intent(Intent.ACTION_VIEW,
//                                    Uri.parse("market://details?id=" + getPackageName())));
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("market://details?id=" + getPackageName())));
                        } catch (ActivityNotFoundException e) {
                            startActivity(new Intent(Intent.ACTION_VIEW,
                                    Uri.parse("http://play.google.com/store/apps/details?id=" + getPackageName())));
                        }
                }else{
                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
                }

                drawerLayout.closeDrawers();
                break;
            case 5:
//                if(Comman.Check_Login(DashBoard_Activity.this)) {


                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MedParliament App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.text)+"https://play.google.com/store/apps/details?id=com.medparliament");
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, "Share via"));


//                    Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
//                    String link="https://play.google.com/store/apps/details?id=com.medparliament";
//                    sharingIntent.setType("text/plain");
//                    String shareBody =getResources().getString(R.string.text);
//                    sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, shareBody);
//                    sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, link);
//                    startActivity(Intent.createChooser(sharingIntent, "Share via"));
//                }else{
//                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
//                }

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
