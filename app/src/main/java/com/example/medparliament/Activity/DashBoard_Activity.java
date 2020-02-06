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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.example.medparliament.Adapter.DrawerI_Adapter;
import com.example.medparliament.Adapter.Post_Adapter;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.Models.DrawerModel;
import com.example.medparliament.Internet.Models.Post_Modle;
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

public class DashBoard_Activity extends AppCompatActivity implements onResult {
    ListView listView;
    ActionBarDrawerToggle actionBarDrawerToggle;
    DrawerLayout drawerLayout;
    Segow_UI_Semi_Font userName;
    ImageView logout;
    Toolbar toolbar;
    onResult onResult;
    RelativeLayout nodata;
    Post_Adapter adapterpost;
    RecyclerView recyclerView;
    ArrayList<Post_Modle> arrayList;
    LinearLayoutManager manager;
    MySharedPrefrence m;
    String name;
    ImageView setting;
    CircleImageView profile;
    ProgressDialog progressDialog;
    FloatingActionButton cmnt;
    int counter=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dash_board_);
        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
//        window.setNavigationBarColor(Color.WHITE);
        Animatoo.animateSwipeLeft(DashBoard_Activity.this);
        this.onResult=this;
        Intent i=getIntent();
        if(i!=null)
            name=i.getStringExtra("username");
        listView = findViewById(R.id.left_drawer);
        nodata=findViewById(R.id.nodata);
        setting=findViewById(R.id.setting);
        m=MySharedPrefrence.instanceOf(DashBoard_Activity.this);
        View stub = (View) findViewById(R.id.toolbar);
        logout=findViewById(R.id.logout);
        toolbar=stub.findViewById(R.id.toolbar_actionbar);
        drawerLayout = findViewById(R.id.drawerlayut);
        userName=findViewById(R.id.username);
        cmnt=findViewById(R.id.cmnt);
        profile=findViewById(R.id.profile);
        userName.setText(m.getUserName());
        Comman.setRoundedImage(DashBoard_Activity.this,profile,m.getUserProfile());
//        Glide.with(DashBoard_Activity.this).load(m.getUserProfile()).into(profile);
        progressDialog = new ProgressDialog(DashBoard_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        Comman.log("UserName","jkdsf"+m.getUserName());
        DrawerModel[] drawerItem = new DrawerModel[4];
        drawerItem[0] = new DrawerModel(R.drawable.ic_home, "Home");
        drawerItem[1] = new DrawerModel(R.drawable.post, "My Posts");
        drawerItem[2] = new DrawerModel(R.drawable.ic_info, "About us");
        drawerItem[3] = new DrawerModel(R.drawable.ic_star, "Rate Now");
        DrawerI_Adapter adapter = new DrawerI_Adapter(this, R.layout.drawer_item_layout, drawerItem);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new DrawerItemClickListener());
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        setupDrawerToggle();
        recyclerView=findViewById(R.id.recycle);
        arrayList=new ArrayList<>();
        manager=new LinearLayoutManager(DashBoard_Activity.this);
        recyclerView.setLayoutManager(manager);
        adapterpost=new Post_Adapter(DashBoard_Activity.this,arrayList,m.getUserName(),2);
        recyclerView.setAdapter(adapterpost);
        setupToolbar();
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
                startActivity(new Intent(DashBoard_Activity.this,User_profile_Activity.class));
                drawerLayout.closeDrawers();
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
                nodata.setVisibility(View.GONE);
                ArrayList<Post_Modle> rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<Post_Modle>>() {
                }.getType());
                arrayList.clear();
                arrayList.addAll(rm);
                adapterpost.notifyDataSetChanged();
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
        Api_Calling.postMethodCall_NO_MSG(DashBoard_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.userDashBoardPost,myPostJson(),"MY_POST_LIST");
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
        {
            case 1:
                startActivity(new Intent(DashBoard_Activity.this,My_Post_Activity.class));
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
                startActivity(new Intent(DashBoard_Activity.this,Login_Activity.class));
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
            jsonObject.put("userTypeId",""+m.getUserTypeId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("MyPOSTJSON",""+jsonObject);
        return jsonObject;

    }
}
