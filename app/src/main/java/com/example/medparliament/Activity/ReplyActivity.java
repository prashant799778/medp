package com.example.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Widget.Segow_UI_EditText;
import com.example.medparliament.Widget.Segow_UI_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;
import com.example.medparliament.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class ReplyActivity extends AppCompatActivity implements onResult {
    ImageButton bck;
    Segow_UI_Semi_Font done;
    onResult onResult;
    MySharedPrefrence m;

    SpinnerDialog spinnerDialog;
    ArrayList<String>genderList=new ArrayList<>();
    Segow_UI_EditText cmnt_msg;
    String postId="";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reply);
        Animatoo.animateSwipeLeft(ReplyActivity.this);
        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        this.onResult=this;

        Intent i= getIntent();
        if(i!=null){
            postId=i.getStringExtra("postId");
        }
        bck=findViewById(R.id.bck);
        done=findViewById(R.id.done);
        cmnt_msg=findViewById(R.id.cmnt);
        m=MySharedPrefrence.instanceOf(ReplyActivity.this);

        genderList.add("For Student");
        genderList.add("For Policy Maker");
        genderList.add("For Entreprenuer");
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cmnt_msg.getText().toString().isEmpty()){
                    Api_Calling.postMethodCall(ReplyActivity.this,getWindow().getDecorView().getRootView(),onResult, URLS.USER_REPLY,conmtJson(),"Multi");
                }else {
                    Comman.topSnakBar(ReplyActivity.this,getWindow().getDecorView().getRootView(),"Write Something Please!!!");
                }
            }
        });
//        forwhat.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                showPopup(genderList,"Select For What",forwhat);
//                spinnerDialog.showSpinerDialog();
//            }
//        });
    }
    public void showPopup(ArrayList<String> items, String title, final Segow_UI_Font segow_ui_font)
    {
        spinnerDialog=new SpinnerDialog(ReplyActivity.this,items,title,"");// With No Animation
//        spinnerDialog=new SpinnerDialog(ReplyActivity.this,items,title,R.style.DialogAnimations_SmileWindow,"");// With 	Animation
        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                segow_ui_font.setText(item);
                segow_ui_font.setTextColor(Color.WHITE);
                getForWhatId(segow_ui_font.getText().toString(),segow_ui_font);
            }
        });
    }

    public void getForWhatId(String  key, TextView editText)
    {
        switch (key)
        {
            case "For Student":
                editText.setTag("7");
                break;
            case "For Policy Maker":
                editText.setTag("5");
                break;
            case "For Entreprenuer":
                editText.setTag("6");
                break;
        }
    }

    @Override
    public void onResult(JSONObject jsonObject,Boolean status) {
        if(jsonObject!=null)
            onBackPressed();
    }
    public JSONObject conmtJson()
    {
        String forwhatid="";


        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("approvedUserId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("postId",""+postId).put("commentDescription",""+cmnt_msg.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("CommentJson",""+jsonObject);
        return jsonObject;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(ReplyActivity.this);
    }
}
