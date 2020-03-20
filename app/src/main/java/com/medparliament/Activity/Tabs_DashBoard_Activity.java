package com.medparliament.Activity;
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
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.medparliament.Adapter.AnnoucementsAdapter;
import com.medparliament.Adapter.DashBoardViewPagerAdapter;
import com.medparliament.Adapter.Dashboard_News_Adapter;
import com.medparliament.Adapter.Dashboard_news_adapter_new;
import com.medparliament.Adapter.DrawerI_Adapter;
import com.medparliament.Adapter.Event_Adapter;
import com.medparliament.Adapter.GalleryAdapter;
import com.medparliament.Adapter.ThreadAdapter;
import com.medparliament.Internet.Models.DrawerModel;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.CustomViewPageNew;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;

import org.json.JSONObject;

import de.hdodenhof.circleimageview.CircleImageView;

public class Tabs_DashBoard_Activity extends AppCompatActivity implements onResult {
    private static final long DELAY_MS = 500;
    private static final long PERIOD_MS = 10000;

    int currentPage=0;
    int currentPage1=0;
    int currentPage2=0;
    int currentPage3=0;
    int currentPage4=0;


    TabLayout tabLayout;
    DashBoardViewPagerAdapter pageadapter;
    CustomViewPageNew viewPager;

    ListView listView;
    RelativeLayout about;
    ImageView arrow;
    Segow_UI_Semi_Font moreNews;
    LinearLayout news,announce,event,gallery;
    Button login_signup;
    ActionBarDrawerToggle actionBarDrawerToggle;
//    DrawerLayout drawerLayout;
    Segow_UI_Semi_Font userName;
    ImageView logout;
    Toolbar toolbar;
    onResult onResult;
    RelativeLayout nodata;
    MySharedPrefrence m;
    ViewPager viewPager_gallery,viewPager_news,viewPager_announced,viewPager_event,viewPager_video,viewPager_Promising;
    RecyclerView recyclerView_gallery;
    String name;
    ImageView setting;
    CircleImageView profile;
    ProgressDialog progressDialog;
    FloatingActionButton cmnt,share;
    ImageButton bck;
    Dashboard_News_Adapter dashboard_news_adapter;
    LinearLayoutManager news_manager,gallery_Manager,announced_manage,event_manager;
    GalleryAdapter galleryAdapter;
    AnnoucementsAdapter annoucementsAdapter;
    Event_Adapter event_adapter;
    int counter=0;
    ImageView bell,user_login;
    LinearLayout video_layout,promising_layout;
    Dashboard_news_adapter_new viewpager_news_adapter;
    GalleryAdapter galleryAdapter1;
    String id;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tabs__dash_board_);
        Animatoo.animateSlideLeft(Tabs_DashBoard_Activity.this);
        Intent i=getIntent();
        if(i!=null)
            id=i.getStringExtra("id");
        pageadapter = new DashBoardViewPagerAdapter(
                getSupportFragmentManager(), Tabs_DashBoard_Activity.this,id);
        ////////////////////////////////////////////////references///////////////////////////////////////
        viewPager = (CustomViewPageNew) findViewById(R.id.viewpager);
        tabLayout = (TabLayout) findViewById(R.id.tabs);
        viewPager.setAdapter(pageadapter);
        tabLayout.setupWithViewPager(viewPager);
//        setting=findViewById(R.id.setting);
//        logout=findViewById(R.id.logout);
        int idr=Integer.parseInt(id);
        viewPager.setCurrentItem(idr);
        this.onResult=this;
        bck=findViewById(R.id.bck);
//        listView = findViewById(R.id.left_drawer);

        nodata=findViewById(R.id.nodata);

          m= MySharedPrefrence.instanceOf(Tabs_DashBoard_Activity.this);
        View stub = (View) findViewById(R.id.toolbar);

//        toolbar=stub.findViewById(R.id.toolbar_actionbar);
//        drawerLayout = findViewById(R.id.drawerlayut);
//        userName=findViewById(R.id.username);
        cmnt=findViewById(R.id.cmnt);
        cmnt.setVisibility(View.GONE);
        share=findViewById(R.id.share);
//        profile=findViewById(R.id.profile);
//        moreNews=findViewById(R.id.morenews);
//        userName.setText(m.getUserName());
//        logout.setVisibility(View.GONE);
//        Comman.setRoundedImage(Tabs_DashBoard_Activity.this,profile,m.getUserProfile());

//        DrawerModel[] drawerItem = new DrawerModel[5];
//        drawerItem[0] = new DrawerModel(R.drawable.ic_internet_, "Home");
//        drawerItem[1] = new DrawerModel(R.drawable.ic_note_new, "Inbox");
//        drawerItem[2] = new DrawerModel(R.drawable.ic_sent,"Sent");
//        drawerItem[3] = new DrawerModel(R.drawable.ic_info, "About us");
//        drawerItem[4] = new DrawerModel(R.drawable.ic_star, "Rate Now");
//
//        DrawerI_Adapter adapter = new DrawerI_Adapter(this, R.layout.drawer_item_layout, drawerItem);
//        listView.setAdapter(adapter);
//        listView.setOnItemClickListener(new DrawerItemClickListener());
//        drawerLayout.addDrawerListener(actionBarDrawerToggle);
//        setupDrawerToggle();
//        setupToolbar();

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(android.content.Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MedParliament App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.text)+"https://play.google.com/store/apps/details?id=com.medparliament");
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, "Share via"));
            }
        });

        cmnt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Tabs_DashBoard_Activity.this,Comment_Activity.class));
            }
        });
        viewPager.setEnableSwipe(false);
        if(Comman.Check_Login(Tabs_DashBoard_Activity.this))
        {
            Comman.log("Login","LoginTure");
//            setting.setVisibility(View.VISIBLE);
//            profile.setVisibility(View.VISIBLE);
//            logout.setVisibility(View.VISIBLE);
            cmnt.setVisibility(View.VISIBLE);
        }else {
//            setting.setVisibility(View.GONE);
//            profile.setVisibility(View.GONE);
//            logout.setVisibility(View.GONE);
            cmnt.setVisibility(View.GONE);
        }
//        setting.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Comman.Check_Login(Tabs_DashBoard_Activity.this)) {
//                    startActivity(new Intent(Tabs_DashBoard_Activity.this, User_profile_Activity.class));
//                }else{
//                    startActivity(new Intent(Tabs_DashBoard_Activity.this,Login_Signup_Activity.class));
//                }drawerLayout.closeDrawers();
//            }
//        });
//        profile.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(Comman.Check_Login(Tabs_DashBoard_Activity.this)) {
//                    startActivity(new Intent(Tabs_DashBoard_Activity.this, User_profile_Activity.class));
//                }else{
//                    startActivity(new Intent(Tabs_DashBoard_Activity.this,Login_Signup_Activity.class));
//                }drawerLayout.closeDrawers();
//            }
//        });
//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showDialogeBox(Tabs_DashBoard_Activity.this,"Information","Do you want to Logout from your Account?");
//            }
//        });
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();

            }
        });
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {




    }
//    private class DrawerItemClickListener implements ListView.OnItemClickListener {
//
//        @SuppressLint("ResourceAsColor")
//        @Override
//        public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//            selectItem(position);
//        }
//
//    }
//    @Override
//    public boolean onOptionsItemSelected(MenuItem item) {
//        Comman.log("SSSS","SSSSSS");
////        if (actionBarDrawerToggle.onOptionsItemSelected(item)) {
////            return true;
////        }
////        return super.onOptionsItemSelected(item);
////        if (item.getItemId() == android.R.id.home) {
////            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
////                drawerLayout.closeDrawer(GravityCompat.START);
////            }
////            else {
////                drawerLayout.openDrawer(GravityCompat.START);
////            }
////
////            return true;
////        }
////        startActivity(new Intent(Tabs_DashBoard_Activity.this,DashBoard_Activity.class));
//            onBackPressed();
//            return true;
//
//
////        return super.onOptionsItemSelected(item);
//    }

//    @Override
//    protected void onPostCreate(Bundle savedInstanceState) {
//        super.onPostCreate(savedInstanceState);
////        actionBarDrawerToggle.syncState();
//    }
//    private void selectItem(int position) {
//        switch (position)
//        {     case 0:
//            startActivity(new Intent(Tabs_DashBoard_Activity.this, DashBoard_Activity.class));
//
//            drawerLayout.closeDrawers();
//            break;
//            case 1:
//                if(Comman.Check_Login(Tabs_DashBoard_Activity.this)) {
//                    startActivity(new Intent(Tabs_DashBoard_Activity.this, My_Post_Activity.class));
//                }else{
//                    startActivity(new Intent(Tabs_DashBoard_Activity.this, Login_Signup_Activity.class));
//                }
//
//                drawerLayout.closeDrawers();
//                break;
//
//            case 2:
//                if(Comman.Check_Login(Tabs_DashBoard_Activity.this)){
//                    startActivity(new Intent(Tabs_DashBoard_Activity.this, All_Post_Activity.class));
//                }else {
//                    startActivity(new Intent(Tabs_DashBoard_Activity.this, Login_Signup_Activity.class));
//
//                }
//                drawerLayout.closeDrawers();
//                break;
//
//            case 3:
////                if(Comman.Check_Login(DashBoard_Activity.this)) {
//                startActivity(new Intent(Tabs_DashBoard_Activity.this, AboutUsActivity.class));
//
////                }else{
////                    startActivity(new Intent(DashBoard_Activity.this, Login_Signup_Activity.class));
////                }
//                drawerLayout.closeDrawers();
//                break;
//            case 4:
//                if(Comman.Check_Login(Tabs_DashBoard_Activity.this)) {
//
//                }else{
//                    startActivity(new Intent(Tabs_DashBoard_Activity.this, Login_Signup_Activity.class));
//                }
//
//                drawerLayout.closeDrawers();
//                break;
//
//        }
//
//    }
//
//    void setupToolbar() {
////        setSupportActionBar(toolbar);
//        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
//        getSupportActionBar().setDisplayShowHomeEnabled(true);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
//        getSupportActionBar().setDisplayShowTitleEnabled(false);
//        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_back);
//    }

    void
    setupDrawerToggle() {
//        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.app_name, R.string.app_name);
//        This is necessary to change the icon of the Drawer Toggle upon state change.;
//        actionBarDrawerToggle.setDrawerIndicatorEnabled(false);
//        actionBarDrawerToggle.setHomeAsUpIndicator(R.drawable.ic_menu);
//        actionBarDrawerToggle.syncState();
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
                startActivity(new Intent(Tabs_DashBoard_Activity.this,DashBoard_Activity.class));
//                finish();
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
        Animatoo.animateSlideRight(Tabs_DashBoard_Activity.this);
    }


}
