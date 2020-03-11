package com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.NewModel.up_skill_model;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Up_Skill_Details_Activity extends AppCompatActivity implements onResult {
    Segow_UI_Semi_Font  msg;
    ImageButton bck;
    Button enroll,enroll1;
    Segow_UI_Bold_Font title;
    ProgressDialog progressDialog;
    MySharedPrefrence m;
    onResult onResult;
    ImageView image;
    up_skill_model skill_model;
    String id="";
    RelativeLayout nodata;
    Segow_UI_Semi_Font lenght,effort,price,institute,subject,level,language,videotext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up__skill__details_);
        Animatoo.animateSwipeLeft(Up_Skill_Details_Activity.this);
        msg=findViewById(R.id.msg);
        m=MySharedPrefrence.instanceOf(Up_Skill_Details_Activity.this);
        bck=findViewById(R.id.bck);
        enroll=findViewById(R.id.enroll);
        enroll1=findViewById(R.id.enroll_1);
        progressDialog = new ProgressDialog(Up_Skill_Details_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        nodata=findViewById(R.id.nodata);
        this.onResult=this;
        lenght=findViewById(R.id.lenght);
        effort=findViewById(R.id.effort);
        price=findViewById(R.id.price);
        institute=findViewById(R.id.institute);
        subject=findViewById(R.id.subject);
        level=findViewById(R.id.level);
        image=findViewById(R.id.cover);
        language=findViewById(R.id.language);
        videotext=findViewById(R.id.videotext);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        });
        Intent intent=getIntent();
        if(intent!=null)
        {
            skill_model=(up_skill_model) intent.getSerializableExtra("data");
        }

        Comman.log("Status",""+skill_model.getMakedone());
        if(skill_model.getMakedone().equalsIgnoreCase("1"))
        {
            Comman.log("Statusttt",""+skill_model.getMakedone());
            enroll1.setEnabled(false);
            enroll.setEnabled(false);
            enroll.setText("Already Done");
            enroll1.setText("Already Done");
            enroll.setBackgroundColor(Color.GRAY);
            enroll1.setBackgroundColor(Color.GRAY);
        }
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Up_Skill_Details_Activity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
             Api_Calling.postMethodCall_1(Up_Skill_Details_Activity.this,getWindow().getDecorView().getRootView(),URLS.enrollUpSkills,myPostJson(),"",enroll,enroll1,progressDialog);
            }
        });
        enroll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Up_Skill_Details_Activity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                Api_Calling.postMethodCall_1(Up_Skill_Details_Activity.this,getWindow().getDecorView().getRootView(),URLS.enrollUpSkills,myPostJson(),"",enroll,enroll1,progressDialog);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCall(Up_Skill_Details_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.getUpSkillsOpportunity,setJson(),"");
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        JSONObject jo = new JSONObject();
        Comman.log("ONResultMewthod", "" + jsonObject);
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        nodata.setVisibility(View.VISIBLE);
        if (jsonObject != null && status) {
            nodata.setVisibility(View.GONE);
            Gson gson = new GsonBuilder().create();
            try {
                ArrayList<up_skill_model> rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<up_skill_model>>(){}.getType());
                up_skill_model r=rm.get(0);
                lenght.setText(r.getLength());
                effort.setText(r.getEffort());
                price.setText(r.getPrice());
                institute.setText(r.getInstitutions());
                subject.setText(r.getSummary());
                level.setText(r.getLevel());
                language.setText(r.getLanguage());
                videotext.setText(r.getVideoTranscript());
                msg.setText(Html.fromHtml(r.getNewsDesc()));
                Comman.setImageWithCondition(Up_Skill_Details_Activity.this,image,r.getImagePath());
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Up_Skill_Details_Activity.this);
    }
    public JSONObject setJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",""+skill_model.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("userId",""+m.getUserId()).put("upSkillsId",""+skill_model.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("EnrollJson",""+jsonObject);
        return jsonObject;
    }



}
