package com.example.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.text.method.LinkMovementMethod;
import android.util.Log;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.Models.DashboardAnnouncedModel;
import com.example.medparliament.Internet.Models.Dashboard_News_Model;
import com.example.medparliament.Internet.Models.Dashbooard_eventModel;
import com.example.medparliament.Internet.Models.NewsModelList;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Utility.PrettyTimeClass;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;

import org.json.JSONException;
import org.json.JSONObject;

import io.fotoapparat.parameter.Flash;

public class NewsDetails_Activity extends AppCompatActivity implements onResult {
    onResult onResult;
    String postId = "";
    MySharedPrefrence m;
    Segow_UI_Semi_Font date, created, msg,loc,header;
    ImageButton bck;
    Segow_UI_Bold_Font title;
    ProgressDialog progressDialog;
    NewsModelList pm;
    Dashboard_News_Model newses;
    Dashbooard_eventModel eventModel;
    DashboardAnnouncedModel announcedModel;
    ImageView img;
    AppCompatButton  ab;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_details_);
        Animatoo.animateSwipeLeft(NewsDetails_Activity.this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        this.onResult = this;
        ab=findViewById(R.id.interest);
        if(!Comman.Check_Login(getApplicationContext())) {

            ab.setVisibility(View.GONE);
        }
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        loc = findViewById(R.id.loc);
        msg = findViewById(R.id.msg);
       header = findViewById(R.id.header);
        created = findViewById(R.id.created);
        bck = findViewById(R.id.bck);
         img= findViewById(R.id.cover);
//        progressDialog = new ProgressDialog(NewsDetails_Activity.this);
//        progressDialog.setMessage("Loading...");
//        progressDialog.setCancelable(true);
//        progressDialog.show();
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        m = MySharedPrefrence.instanceOf(NewsDetails_Activity.this);
        Intent i = getIntent();
        if (i != null) {

            pm=(NewsModelList) i.getSerializableExtra("news");
            postId = i.getStringExtra("postId");
            eventModel= (Dashbooard_eventModel) i.getSerializableExtra("event");
            newses =  (Dashboard_News_Model)i.getSerializableExtra("newses");
            announcedModel= (DashboardAnnouncedModel) i.getSerializableExtra("ann");

        }

        setResult();
    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        progressDialog.dismiss();
        if(status){
        ab.setText("Already Intrested");
        ab.setBackgroundResource(R.color.hintColor);}
//        if (jsonObject != null && status) {
//            try {
//                JSONObject jo = jsonObject.getJSONArray("result").getJSONObject(0);
//                m.setLoggedIn(true);
//                title.setText(Comman.getValueFromJsonObject(jo, "postTitle"));
//                created.setText("By :-" + Comman.getValueFromJsonObject(jo, "userName"));
//                date.setText("" + PrettyTimeClass.PrettyTime(Comman.timeInms(Comman.getValueFromJsonObject(jo, "DateCreate"))));
//                msg.setText("" + Comman.getValueFromJsonObject(jo, "postDescription"));
//            } catch (JSONException e) {
//                e.printStackTrace();
//            }
//        }



    }



    public  void  setResult(){
 if(pm != null){

     img.setVisibility(View.VISIBLE);
     title.setText(pm.getNewsTitle());
     created.setText(pm.getUserCreate());
      msg.setText(Html.fromHtml(pm.getNewsDesc()));
     Comman.setRectangleImage(getApplicationContext(),img,pm.getImagePath());
     date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));


 }
 if(eventModel != null){
       Log.d("AAAAAAA",eventModel.toString());
     ab.setVisibility(View.VISIBLE );
     if(eventModel.getLikedId()!=null &&  Integer.valueOf(eventModel.getLikedId())>0){
         ab.setText("Already Intrested");
         ab.setBackgroundResource(R.color.hintColor);
      }else{


         ab.setOnClickListener(new View.OnClickListener() {
             @Override
             public void onClick(View v) {
                 if(Comman.Check_Login(getApplicationContext())) {

                     progressDialog = new ProgressDialog(NewsDetails_Activity.this);
                     progressDialog.setMessage("Loading...");
                     progressDialog.setCancelable(true);
                     progressDialog.show();
                     Api_Calling.postMethodCall_NO_MSG(getApplicationContext(), getWindow().getDecorView().getRootView(), onResult, URLS.seteventInterest, myPostJson(), "eventinterest");
                 }else{
                     startActivity(new Intent(NewsDetails_Activity.this,Login_Signup_Activity.class));
                     finish();

                 }



             }
         });
     }




     header.setText("EventDetails");
     if(eventModel.getImagePath()!=null && !eventModel.getImagePath().equalsIgnoreCase(""))
            img.setVisibility(View.VISIBLE);




     created.setText(eventModel.getEventLocation());
            title.setText(eventModel.getEventTitle());
            msg.setText(Html.fromHtml(eventModel.getEventSummary()));
            Comman.setRectangleImage(getApplicationContext(),img,eventModel.getImagePath());
            date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(eventModel.getDateCreate())));


        }
 if(newses != null){

     img.setVisibility(View.VISIBLE);
     title.setText(newses.getNewsTitle());

     msg.setText(Html.fromHtml(newses.getNewsDesc()));
     msg.setMovementMethod(LinkMovementMethod.getInstance());
     Comman.setRectangleImage(getApplicationContext(),img,newses.getImagePath());
     date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(newses.getDateCreate())));


 }

        if(announcedModel != null){
            header.setText("AnnouncedDetails");
            img.setVisibility(View.VISIBLE);


            msg.setText(announcedModel.getSummary());
           title.setText(announcedModel.getTitle());

            Comman.setRectangleImage(getApplicationContext(),img,announcedModel.getImagePath());

            date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(announcedModel.getDateCreate())));


        }


    }

    @Override
    protected void onStart() {
        super.onStart();
        if(!Comman.Check_Login(getApplicationContext())) {

            ab.setVisibility(View.GONE);
        }
//        Api_Calling.postMethodCall(NewsDetails_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.ALL_POSTS,setjson(),"NewstDetails");
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
        Animatoo.animateSwipeRight(NewsDetails_Activity.this);
    }


    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            if(Comman.Check_Login(NewsDetails_Activity.this)){
                jsonObject.put("userTypeId",m.getUserTypeId());
                jsonObject.put("userId",m.getUserId());
                jsonObject.put("eventId",eventModel.getId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("MyPOSTJSON",""+jsonObject);
        return jsonObject;

    }
}