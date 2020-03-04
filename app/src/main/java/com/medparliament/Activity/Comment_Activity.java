package com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.ImageButton;
import android.widget.TextView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;

import com.medparliament.Internet.URLS;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.onResult;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.Segow_UI_EditText;
import com.medparliament.Widget.Segow_UI_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.medparliament.R;


import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Comment_Activity extends AppCompatActivity implements onResult {
    ImageButton bck;
    Segow_UI_Semi_Font done;
    onResult onResult;
    MySharedPrefrence m;
    Segow_UI_Font forwhat;
    SpinnerDialog spinnerDialog;
    ArrayList<String>genderList=new ArrayList<>();
    Segow_UI_EditText cmnt_msg,title;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment_);
        Animatoo.animateSwipeLeft(Comment_Activity.this);
        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        this.onResult=this;
        bck=findViewById(R.id.bck);
        done=findViewById(R.id.done);
        cmnt_msg=findViewById(R.id.cmnt);
        m=MySharedPrefrence.instanceOf(Comment_Activity.this);
        title=findViewById(R.id.title);
        forwhat=findViewById(R.id.forwhat);
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
                   Api_Calling.multiPartCall(Comment_Activity.this,getWindow().getDecorView().getRootView(), URLS.USER_POST,conmtJson(),onResult,"Multi");
               }else {
                   Comman.topSnakBar(Comment_Activity.this,getWindow().getDecorView().getRootView(),"Write Something Please!!!");
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
        spinnerDialog=new SpinnerDialog(Comment_Activity.this,items,title,"");// With No Animation
//        spinnerDialog=new SpinnerDialog(Comment_Activity.this,items,title,R.style.DialogAnimations_SmileWindow,"");// With 	Animation
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
//        if(forwhat!=null && forwhat.getTag()!=null)
//            forwhatid=forwhat.getTag().toString();
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("postTitle",""+title.getText().toString()).put("postDescription",""+cmnt_msg.getText().toString())
                    .put("showuserTypeId","").put("flag","n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("CommentJson",""+jsonObject);
        return jsonObject;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Comment_Activity.this);
    }
}
