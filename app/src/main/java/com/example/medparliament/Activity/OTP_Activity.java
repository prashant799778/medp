package com.example.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
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
import com.example.medparliament.Widget.Segow_UI_EditText;
import com.example.medparliament.Widget.otpEditText;

import org.json.JSONException;
import org.json.JSONObject;

public class OTP_Activity extends Base_Activity implements onResult {
    ImageButton bck;
    onResult onResult;
    otpEditText otp;
    String email;
    Button btn;
    ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_otp_);
        Animatoo.animateSwipeLeft(OTP_Activity.this);
        otp=findViewById(R.id.et_otp);
        btn=findViewById(R.id.reset_password);
        this.onResult=this;
        Intent i=getIntent();
        if(i!=null)
        email=i.getStringExtra("email");
        bck=findViewById(R.id.bck);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        otp.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }
            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }
            @Override
            public void afterTextChanged(Editable s) {
                if(s.length()==4)
                {
                    btn.setBackgroundColor(getResources().getColor(R.color.textcolor));
                }else
                {
                    btn.setBackgroundColor(getResources().getColor(R.color.bbb));
                }
            }
        });
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!otp.getText().toString().isEmpty())
                {
                    progressDialog = new ProgressDialog(OTP_Activity.this);
                    progressDialog.setMessage("Loading...");
                    progressDialog.setCancelable(true);
                    progressDialog.show();
                    Api_Calling.postMethodCall(OTP_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.verifyOtp,emialJson(),"VeriffyOTP");
                }else {
                    Comman.topSnakBar(OTP_Activity.this, v, Constant.OTP);
                }
            }
        });

    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(progressDialog!=null &&  progressDialog.isShowing())
            progressDialog.dismiss();
        if(status){
            Intent intent=new Intent(OTP_Activity.this,ChangePassword_Activity.class);
            intent.putExtra("email",""+email);
            startActivity(intent);
        }
    }
    public JSONObject emialJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("email",""+email).put("otp",""+otp.getText().toString());
            Comman.log("EmailFromMySide",""+jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return  jsonObject;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(OTP_Activity.this);
    }
}
