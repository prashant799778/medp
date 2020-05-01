package  com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Adapter.ThreadAdapter;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.Reply_Model;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Post_Details_Activity extends AppCompatActivity implements onResult {
  onResult onResult;
  String postId="";
  MySharedPrefrence m;
  Segow_UI_Semi_Font date,created,msg;
  ImageButton bck;
  Segow_UI_Bold_Font title;
    FloatingActionButton  cmnts;
    Segow_UI_Semi_Font noti_counter;
    LinearLayout circle;
    ImageView share;
    ImageView bell;
    Handler ha;
  ProgressDialog progressDialog;
    MySharedPrefrence mySharedPrefrence;
    ArrayList<Reply_Model> arrayList;
    RecyclerView recyclerView;
    ThreadAdapter threadAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__details_);
        m=MySharedPrefrence.instanceOf(getApplicationContext());
        circle=findViewById(R.id.circle);
        apiCall(URLS.Notification, myPostJson2());

        Animatoo.animateSwipeLeft(Post_Details_Activity.this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        arrayList=new ArrayList<>();
        this.onResult=this;
        bell = findViewById(R.id.bell);
        if (Comman.Check_Login(Post_Details_Activity.this))
            bell.setVisibility(View.VISIBLE);


        noti_counter = findViewById(R.id.noti_count);
        share=findViewById(R.id.share_tool);



        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(Post_Details_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(Post_Details_Activity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(Post_Details_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(Post_Details_Activity.this, NotificationActivity.class));
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




        cmnts=findViewById(R.id.cmnt);
        cmnts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Post_Details_Activity.this,ReplyActivity.class);
                intent.putExtra("postId",postId);
                startActivity(intent);
            }
        });
        title=findViewById(R.id.title);
        date=findViewById(R.id.date);
        msg=findViewById(R.id.msg);
        created=findViewById(R.id.created);
        recyclerView=findViewById(R.id.recycle);
        bck=findViewById(R.id.bck);
        LinearLayoutManager    manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        threadAdapter=new ThreadAdapter(getApplicationContext(),arrayList,m.getUserName(),1);
        recyclerView.setAdapter(threadAdapter);
        progressDialog = new ProgressDialog(Post_Details_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        m=MySharedPrefrence.instanceOf(Post_Details_Activity.this);
        Intent i=getIntent();
        if(i!=null)
            postId=i.getStringExtra("postId");
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
         progressDialog.dismiss();
        if(jsonObject!=null && status){
            try {
                JSONObject jo=jsonObject.getJSONArray("result").getJSONArray(1).getJSONObject(0);
                m.setLoggedIn(true);
                title.setText(Comman.getValueFromJsonObject(jo,"postTitle"));
                created.setText("By :-"+Comman.getValueFromJsonObject(jo,"userName"));
                date.setText(""+ PrettyTimeClass.PrettyTime(Comman.timeInms(Comman.getValueFromJsonObject(jo,"DateCreate"))));
                msg.setText(""+Comman.getValueFromJsonObject(jo,"postDescription"));

                JSONArray jo1=jsonObject.getJSONArray("result").getJSONArray(0);

                Gson gson=new GsonBuilder().create();
                try {
                    ArrayList<Reply_Model> rm = gson.fromJson(jo1.toString(), new TypeToken<ArrayList<Reply_Model>>(){}.getType());
                    arrayList.clear();
                    arrayList.addAll(rm);
                    threadAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCall(Post_Details_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.ALL_POSTS_THREAD,setjson(),"PostDetails");
    }
    public JSONObject setjson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("postId",""+postId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("postDetailsJson",""+jsonObject);
        return jsonObject;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Post_Details_Activity.this);
    }

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
        RequestQueue requestQueue= Volley.newRequestQueue(Post_Details_Activity.this);
        requestQueue.add(jsonObjectRequest);

    }
    private JSONObject myPostJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (Comman.Check_Login(Post_Details_Activity.this)) {
                jsonObject.put("userTypeId", m.getUserTypeId());
                jsonObject.put("userId", m.getUserId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Notificationscsdcsdcdsvdsfvfdv", "" + jsonObject);
        return jsonObject;
    }
}
