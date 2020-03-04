package com.medparliament.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.Constant;
import com.medparliament.Widget.Segow_UI_EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class Forgot_Password_Activity extends Base_Activity implements onResult {
     Button next;
     onResult onResult;
     ImageButton bck;
     ProgressDialog progressDialog;
    Segow_UI_EditText email;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forgot__password);
        Animatoo.animateSwipeLeft(Forgot_Password_Activity.this);
        email=findViewById(R.id.email_recovery1);
        next=findViewById(R.id.continue_bbt);
        bck=findViewById(R.id.bck);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        this.onResult=this;
        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
             if(s.length()>0)
             {
                 next.setBackgroundColor(getResources().getColor(R.color.textcolor));
             }else {
                 next.setBackgroundColor(getResources().getColor(R.color.bbb));
             }
            }
        });
        next.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!email.getText().toString().isEmpty()){
                    progressDialog = new ProgressDialog(Forgot_Password_Activity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    Api_Calling.postMethodCall(Forgot_Password_Activity.this,getWindow().getDecorView().getRootView(),onResult,URLS.GENERATEOTP,emialJson(),"Email");}else {
                    Comman.topSnakBar(Forgot_Password_Activity.this, v, Constant.ENTER_EMIAL);
                }
            }
        });
    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(progressDialog!=null &&  progressDialog.isShowing())
            progressDialog.dismiss();
        if(status){
            Intent intent=new Intent(Forgot_Password_Activity.this,OTP_Activity.class);
            intent.putExtra("email",""+email.getText().toString());
            startActivity(intent);
        }
    }
    public JSONObject emialJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("email",""+email.getText().toString());
            Comman.log("EmailFromMySide",""+jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Forgot_Password_Activity.this);
    }
}
