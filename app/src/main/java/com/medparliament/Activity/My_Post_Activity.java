package  com.medparliament.Activity;

import androidx.localbroadcastmanager.content.LocalBroadcastManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager.widget.ViewPager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Adapter.Post_Adapter;
import com.medparliament.Adapter.Post_Page_Adapter;
import com.medparliament.Internet.Models.Post_Modle;
import com.medparliament.Internet.NewModel.onNotificationResult;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.tabs.TabLayout;
import com.medparliament.Widget.Segow_UI_Semi_Font;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.medparliament.Utility.Comman.REQUEST_ACCEPT;

public class My_Post_Activity extends Base_Activity implements onResult , onNotificationResult {
    ImageButton bck;
    FloatingActionButton cmnt;
    RecyclerView recyclerView;

////////////////////////////////////////////////////////////////////////
    Segow_UI_Semi_Font noti_counter;
    LinearLayout circle;
    ImageView share;
    ImageView bell;
    Handler ha;
/////////////////////////////////////////////////////////////////

    BroadcastReceiver receiver;
    MySharedPrefrence m;
    onNotificationResult onNotificationResult;
    Post_Adapter adapter;

    ArrayList<Post_Modle>arrayList;
    LinearLayoutManager manager;
    onResult onResult;
    Post_Page_Adapter post_page_adapter;
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
        setContentView(R.layout.activity_my__post_);
        m=MySharedPrefrence.instanceOf(My_Post_Activity.this);
        noti_counter = findViewById(R.id.noti_count);
//        apiCall(URLS.Notification, myPostJson2());
        noti_counter.setText(m.getCounterValue());

        this.onResult=this;
        this.onNotificationResult = this;
/////////////////////////////////////////////////////////////////////////////////////////////////////////
        bell = findViewById(R.id.bell);
        if (Comman.Check_Login(My_Post_Activity.this))
            bell.setVisibility(View.VISIBLE);


        circle=findViewById(R.id.circle);
        share=findViewById(R.id.share_tool);
        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    String   value= intent.getStringExtra("count_value");
                    if(value!=null && !value.isEmpty()){
                        noti_counter.setText(value);

                        circle.setVisibility(View.VISIBLE);

                    }else{
                        circle.setVisibility(View.GONE);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };




        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(My_Post_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(My_Post_Activity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(My_Post_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(My_Post_Activity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MedParliament App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.text) + "https://play.google.com/store/apps/details?id=com.medparliament");
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, "Share via"));
            }
        });




//////////////////////////////////////////////////////////////////////////////////////////////////////////////////////









        Window window = getWindow();
        nodata=findViewById(R.id.nodata);
        viewPager=findViewById(R.id.viewpager);
        tabLayout=findViewById(R.id.tabs);
        tabLayout.setVisibility(View.GONE);
        post_page_adapter=new Post_Page_Adapter(getSupportFragmentManager(),this);
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
        Animatoo.animateSwipeLeft(My_Post_Activity.this);
        bck=findViewById(R.id.bck);
        cmnt=findViewById(R.id.cmnt);
        progressDialog = new ProgressDialog(My_Post_Activity.this);
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
                startActivity(new Intent(My_Post_Activity.this,Comment_Activity.class));
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
        Animatoo.animateSwipeRight(My_Post_Activity.this);
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

    @Override
    public void onNotificationResult(JSONObject jsonObject, Boolean status) {

    }





///////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    private void apiCall(String URL,JSONObject jsonObject)
    {
        Comman.log("NotificationApi","Callling");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Comman.log("NotificationApi","Response"+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("true") && (!Comman.getValueFromJsonObject(response,"totalcount").equalsIgnoreCase("0")) )
                    {
//                        noti_counter.setText(Comman.getValueFromJsonObject(response,"totalcount"));
                        m.setCounterValue(Comman.getValueFromJsonObject(response,"totalcount"));
                        circle.setVisibility(View.VISIBLE);
                    }else {
                        circle.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(My_Post_Activity.this);
        requestQueue.add(jsonObjectRequest);

    }
    private JSONObject myPostJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (Comman.Check_Login(My_Post_Activity.this)) {
                jsonObject.put("userTypeId", m.getUserTypeId());
                jsonObject.put("userId", m.getUserId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Notificationscsdcsdcdsvdsfvfdv", "" + jsonObject);
        return jsonObject;
    }
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


    @Override
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(My_Post_Activity.this).registerReceiver((receiver),
                new IntentFilter(REQUEST_ACCEPT)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(My_Post_Activity .this).unregisterReceiver(receiver);
    }

}
