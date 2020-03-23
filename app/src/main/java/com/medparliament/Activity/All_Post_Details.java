package com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Adapter.ThreadAdapter;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.Post_Modle;
import com.medparliament.Internet.Models.Reply_Model;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class All_Post_Details extends AppCompatActivity  {
    onResult onResult;
    String postId="";
    MySharedPrefrence m;
    Segow_UI_Semi_Font date,created,msg;
    ImageButton bck;
    Segow_UI_Bold_Font title;
    Segow_UI_Font  likecount;
    ImageView likeImg;
    FloatingActionButton cmnts;
    ProgressDialog progressDialog;
    ArrayList<Reply_Model> arrayList;
    RecyclerView recyclerView;
    ThreadAdapter threadAdapter;
    Post_Modle pm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all__post__details);
        m=MySharedPrefrence.instanceOf(getApplicationContext());
        Animatoo.animateSwipeLeft(All_Post_Details.this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        arrayList=new ArrayList<>();


        cmnts=findViewById(R.id.cmnt);
        cmnts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(All_Post_Details.this,ReplyActivity.class);
                intent.putExtra("postId",postId);
                startActivity(intent);
            }
        });
        title=findViewById(R.id.title);
        date=findViewById(R.id.date);
        msg=findViewById(R.id.msg);
        created=findViewById(R.id.created);
        likecount =findViewById(R.id.likecount);
        likeImg=findViewById(R.id.like);

        bck=findViewById(R.id.bck);
        LinearLayoutManager manager=new LinearLayoutManager(getApplicationContext());

        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        m=MySharedPrefrence.instanceOf(All_Post_Details.this);
        onResult();
        Intent i=getIntent();

    }

    public void onResult() {
        Intent i = getIntent();
         if(i!=null){
              pm= (Post_Modle) i.getSerializableExtra("postn");
         }
         if(pm!=null) {
             title.setText(pm.getPostTitle());
             created.setText("By :-" + m.getUserName());
             date.setText("" + PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));
             msg.setText("" + pm.getPostDescription());
             likecount.setText("("+pm.getLike()+")");
//        setRed(holder.likleCount,pm.getLike().toString()," Indorse");
             if(pm.getLikeStatus()!=null) {
                 if (Integer.parseInt(pm.getLikeStatus())==1){
                    likeImg.setEnabled(false);
                     likeImg.setImageResource(R.drawable.after);
                 }
             }
             Comman.log("DDD","ddddd"+pm.getLikeStatus());
            likeImg.setOnClickListener(new View.OnClickListener() {
                 @Override
                 public void onClick(View v) {
                     Api_Calling.likePost(getApplicationContext(),likeJson(pm.getPostId()),likeImg,likecount,pm.getLike());
                 }
             });
         }
    }

    public JSONObject likeJson(String postId)
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("postId",""+postId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("LikeJson",""+jsonObject);
        return jsonObject;
    }
    @Override
    protected void onStart() {
        super.onStart();
       // Api_Calling.postMethodCall(All_Post_Details.this,getWindow().getDecorView().getRootView(),onResult, URLS.ALL_POSTS_THREAD,setjson(),"PostDetails");
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
        Animatoo.animateSwipeRight(All_Post_Details.this);
    }

}
