package com.example.medparliament.Activity;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;

import org.json.JSONException;
import org.json.JSONObject;

public class AboutUsActivity extends AppCompatActivity implements onResult {
    Button btn;
    Segow_UI_Semi_Font msg;
    onResult onResult;
    ImageView bck;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);
        this.onResult=this;
        Animatoo.animateSwipeLeft(AboutUsActivity.this);
        progressDialog = new ProgressDialog(AboutUsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        bck=findViewById(R.id.bckii);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        msg=findViewById(R.id.msg);
        btn=findViewById(R.id.enter);
        Api_Calling.postMethodCall(AboutUsActivity.this,getWindow().getDecorView().getRootView(),onResult, URLS.aboutUs,null,"d");
        if(Comman.Check_Login(AboutUsActivity.this))
        {
         btn.setVisibility(View.GONE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this,Login_Signup_Activity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(AboutUsActivity.this);
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(progressDialog!=null &&  progressDialog.isShowing())
            progressDialog.dismiss();
        if(jsonObject!=null && status)
        {
            try {
                JSONObject jsonObject1=jsonObject.getJSONArray("result").getJSONObject(0);
                msg.setText(Html.fromHtml(Comman.getValueFromJsonObject(jsonObject1,"description")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

    }
}
