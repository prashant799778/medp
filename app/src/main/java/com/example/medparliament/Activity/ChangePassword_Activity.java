package com.example.medparliament.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.Constant;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_EditText;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONException;
import org.json.JSONObject;

public class ChangePassword_Activity extends AppCompatActivity implements onResult {
    Segow_UI_EditText pwd,cm_pwd;
    Button done;
    onResult onResult;
    ImageButton bck;
    String email;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_change_password_);
        Animatoo.animateSwipeLeft(ChangePassword_Activity.this);
        this.onResult=this;
        Intent i=getIntent();
        if(i!=null)
        {
            email=i.getStringExtra("email");
        }
        cm_pwd=findViewById(R.id.Conform_pswd);
        pwd=findViewById(R.id.email_recovery2);
        done=findViewById(R.id.reset_new_pwsd);
        bck=findViewById(R.id.bck);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        done.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!cm_pwd.getText().toString().isEmpty() && !pwd.getText().toString().isEmpty()) {
                    if (cm_pwd.getText().toString().equalsIgnoreCase(pwd.getText().toString())) {
                        progressDialog = new ProgressDialog(ChangePassword_Activity.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                        Api_Calling.postMethodCall(ChangePassword_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.updatePassword,emialJson(),"Update");
                    } else {
                        Comman.topSnakBar(ChangePassword_Activity.this,v, Constant.PASSWORD_NOT_MATCH);
                    }
                }else {
                    Comman.topSnakBar(ChangePassword_Activity.this,v, Constant.PLEASE_FILL_ALL_FIELD);
                }
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(ChangePassword_Activity.this);
    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(progressDialog!=null &&  progressDialog.isShowing())
            progressDialog.dismiss();
        if(status)
        {
            showDialogeBox(ChangePassword_Activity.this,"","");
        }

    }
    public JSONObject emialJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("email",""+email).put("password",""+pwd.getText().toString());
            Comman.log("EmailFromMySide",""+jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }
    public void showDialogeBox(Context context, String title, String msg)
    {
        final MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(ChangePassword_Activity.this);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.password_change_popup, null, false);
        Button yes=dialogView.findViewById(R.id.login);
        alertDialog.setView(dialogView);
        alertDialog.show();
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                startActivity(new Intent(ChangePassword_Activity.this,Login_Activity.class));
                finish();
            }
        });
    }
}
