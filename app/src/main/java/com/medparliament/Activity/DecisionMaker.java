package com.medparliament.Activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.hbb20.CountryCodePicker;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.VideoListener;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.Constant;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_EditText;
import com.medparliament.Widget.Segow_UI_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.shantanudeshmukh.linkedinsdk.LinkedInBuilder;
import com.shantanudeshmukh.linkedinsdk.helpers.LinkedInUser;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class DecisionMaker extends AppCompatActivity implements View.OnClickListener,onResult, VideoListener {
    Segow_UI_EditText name,designation,organization,mobile,about_profile,email,pwd,cofm_pwd;
    Segow_UI_Font country,gender,interest;
    Button signUp;
    com.medparliament.Internet.onResult onResult;
    RelativeLayout googleLogin;
    private ProgressDialog progressDialog;
    JSONArray doctorIdArray;
    SpinnerDialog spinnerDialog;
    ImageButton bck;
    MySharedPrefrence m;
    ArrayList<String> genderList=new ArrayList<>();
    String countrycode="91";

    CountryCodePicker ccpLoadNumber;

    VideoListener videoListener;
    ImageView image;
    YouTubePlayerView videoView;
    LinearLayout ll;
    Segow_UI_Semi_Font title;
    JSONObject jsonObject1 = new JSONObject();

    ImageButton login_button;
    Segow_UI_Bold_Font login_txt;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_decision_maker);
        m=MySharedPrefrence.instanceOf(DecisionMaker.this);
        name=findViewById(R.id.name);
        this.onResult=this;
        doctorIdArray=new JSONArray();
        designation=findViewById(R.id.desgination);
        organization=findViewById(R.id.organization);
        mobile=findViewById(R.id.mobile);
        ccpLoadNumber=findViewById(R.id.ccp);
        ccpLoadNumber.registerCarrierNumberEditText(mobile);
        ccpLoadNumber.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countrycode= ccpLoadNumber.getSelectedCountryCode();
            }
        });
        about_profile=findViewById(R.id.about_profile);
        email=findViewById(R.id.email);
        bck=findViewById(R.id.bck);
        cofm_pwd=findViewById(R.id.cnfm_pswd);
        pwd=findViewById(R.id.pswd);
        country=findViewById(R.id.country);
        gender=findViewById(R.id.gender);
        interest=findViewById(R.id.interest);
        signUp=findViewById(R.id.signup);
        googleLogin=findViewById(R.id.login);
        interest.setOnClickListener(this);
        signUp.setOnClickListener(this);
        country.setOnClickListener(this);
        googleLogin.setOnClickListener(this);
        gender.setOnClickListener(this);
        bck.setOnClickListener(this);
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");


        this.videoListener=this;
        image=findViewById(R.id.image);
        ll=findViewById(R.id.ll);
        videoView = (YouTubePlayerView) findViewById(R.id.video);
        title = findViewById(R.id.videoTitle);

        Api_Calling.getALLCountry(DecisionMaker.this,getWindow().getDecorView().getRootView(),URLS.ALL_COUNTRY);
        Comman.setMandatory(name,DecisionMaker.this.getResources().getString(R.string.Name));
        Comman.setMandatory(designation,DecisionMaker.this.getResources().getString(R.string.Designation));
        Comman.setMandatory(organization,DecisionMaker.this.getResources().getString(R.string.Organisation));
        Comman.setMandatory(mobile,DecisionMaker.this.getResources().getString(R.string.Mobile_No));
        Comman.setMandatory(about_profile,DecisionMaker.this.getResources().getString(R.string.About_Your_Profile));
        Comman.setMandatory(email,DecisionMaker.this.getResources().getString(R.string.email));
        Comman.setMandatory(pwd,DecisionMaker.this.getResources().getString(R.string.Password));
        Comman.setMandatory(cofm_pwd,DecisionMaker.this.getResources().getString(R.string.Confirm_Password));
        Comman.ChangeFocus(name);
        Comman.ChangeFocus(designation);
        Comman.ChangeFocus(organization);
        Comman.ChangeFocus(mobile);
        Comman.ChangeFocus(about_profile);
        Comman.ChangeFocus(email);
        Comman.ChangeFocus(pwd);
        Comman.ChangeFocus(cofm_pwd);

        Comman.setMandatoryTextView(country,DecisionMaker.this.getResources().getString(R.string.Country));
        Comman.setMandatoryTextView(gender,DecisionMaker.this.getResources().getString(R.string.gender));
        Api_Calling.videoApiCalling(DecisionMaker.this, getWindow().getDecorView().getRootView(), URLS.getSignUpVideo, videoJson(), videoListener);


         login_txt=findViewById(R.id.login_text);
        login_button=findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                LinkedInBuilder.getInstance(DecisionMaker.this)
                        .setClientID(Comman.CLIENT_ID)
                        .setClientSecret(Comman.CLIENT_SECRET)
                        .setRedirectURI(Comman.REDIRECT_URL)
                        .authenticate(Comman.LINKDIN_CODE);
            }
        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(DecisionMaker.this);
        videoView.release();
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.interest:
                setMultiChoice();
                break;
            case R.id.bck:
                onBackPressed();
                break;
            case R.id.signup:
                if(!name.getText().toString().isEmpty() && !country.getText().toString().isEmpty()
                        && !gender.getText().toString().isEmpty() && !mobile.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !pwd.getText().toString().isEmpty() && !cofm_pwd.getText().toString().isEmpty()) {
                    if (pwd.getText().toString().equals(cofm_pwd.getText().toString())) {
                        progressDialog = new ProgressDialog(DecisionMaker.this);
                        progressDialog.setMessage("Loading...");
                        progressDialog.setCancelable(true);
                        progressDialog.show();
                        Api_Calling.postMethodCall(DecisionMaker.this, getWindow().getDecorView().getRootView(), onResult, URLS.SIGNUP, setStudentJson(), "SignUp");
                        break;
                    } else {
                        Comman.topSnakBar(DecisionMaker.this, v, Constant.PASSWORD_NOT_MATCH);
                    }
                }else {
                    Comman.topSnakBar(DecisionMaker.this, v, Constant.PLEASE_FILL_ALL_FIELD);
                }
            case R.id.login:
                break;
            case R.id.country:
                showPopup(Api_Calling.CountrytList,"Select Country",country);
                spinnerDialog.showSpinerDialog();
                break;
            case R.id.gender:
                showPopup(genderList,"Select Gender",gender);
                spinnerDialog.showSpinerDialog();
                break;
        }

    }

    public void setMultiChoice()
    {
        interest.setText("");
        doctorIdArray=new JSONArray();
        final ArrayList<String> value=new ArrayList<>();
        final String []str=new String[Api_Calling.StudentIntrestList.size()];
        boolean []barray=new boolean[Api_Calling.StudentIntrestList.size()];
        for (int i=0;i<Api_Calling.StudentIntrestList.size();i++)
        {
            str[i]=Api_Calling.StudentIntrestList.get(i);
            barray[i]=false;
        }
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this,R.style.MyCheckBox);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setMessage("Select Interest").setTextColor(Color.BLACK);
        builder.setMultiChoiceItems(str,barray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index, boolean b) {
                Comman.log("Selected",""+str[index]+" Status"+b);
                if(b)
                {
                    value.add(str[index]);
                    Comman.log("interesr",""+Api_Calling.StudentIntrestHash.get(str[index]));
                    doctorIdArray.put(Integer.valueOf(Api_Calling.StudentIntrestHash.get(str[index])));
                }else {
                    doctorIdArray.remove(index);
                    value.remove(str[index]);
                }
            }
        });
        builder.addButton("  Ok   ", Color.parseColor("#ffffff"), Color.parseColor("#0e68a7"), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Comman.log("Intersest Final Arrya",""+doctorIdArray.toString());
                interest.setText(value.toString().replace("[","").replace("]","").trim());
            }
        });
        builder.show();
    }


    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        progressDialog.dismiss();
        if(jsonObject!=null && status) {
            JSONObject jsonObject1 = new JSONObject();
            Comman.log("OnResultStudent", "" + jsonObject.toString());
            if (jsonObject != null) {
                try {
                    jsonObject1 = jsonObject.getJSONArray("result").getJSONObject(0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                m.setLoggedIn(false);
//                m.setUserName(Comman.getValueFromJsonObject(jsonObject1, "userName"));
//                m.setUserId(Comman.getValueFromJsonObject(jsonObject1, "userId"));
//                m.setUserTypeId(Comman.getValueFromJsonObject(jsonObject1, "userTypeId"));
//                m.setUserProfile(Comman.getValueFromJsonObject(jsonObject1, "profilePic"));
                Comman.log("USername", "dsfa" + m.getUserName());
                videoView.release();
                Intent i = new Intent(DecisionMaker.this, Login_Activity.class);
                i.putExtra("username", "" + Comman.getValueFromJsonObject(jsonObject1, "userName"));
//                startActivity(i);
//                finish();
                try {
                    alertBox(DecisionMaker.this,jsonObject.getString("message"),i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }
    }
    public void showPopup(ArrayList<String>items, String title, final Segow_UI_Font segow_ui_font)
    {
        spinnerDialog=new SpinnerDialog(DecisionMaker.this,items,title,"");// With No Animation
//        spinnerDialog=new SpinnerDialog(Policy_Maker_SignUp_Activity.this,items,title,R.style.DialogAnimations_SmileWindow,"");// With 	Animation
        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                segow_ui_font.setText(item);
                segow_ui_font.setTextColor(Color.BLACK);
                segow_ui_font.setTag(position);
            }
        });
    }
    public JSONObject setStudentJson()
    {   //ccpLoadNumber.setFullNumber(mobile.getText().toString());
        JSONObject jsonObject=new JSONObject();
        try {
            String id="";
            int userid=13;
            if((gender!=null && gender.getTag()!=null)){
                id=gender.getTag().toString();}else {
                id="";
            }
            jsonObject.put("userName",""+name.getText().toString())
                    .put("notification_token", "" + m.getFCMToken())
                    .put("mobileNo",countrycode+"-"+  mobile.getText().toString())
                    .put("email",""+email.getText().toString()).
                    put("userTypeId","13")
                    .put("gender",""+id)
                    .put("password",""+pwd.getText().toString()).put("deviceType","").
                    put("os","").put("ipAddress","").put("country",""+Api_Calling.CountryHash.get(country.getText().toString()))
                    .put("city","").put("deviceid",""+Comman.uniqueId(DecisionMaker.this))
                    .put("ImeiNo",""+Comman.getIMEI(DecisionMaker.this))
                    .put("interestId", doctorIdArray).put("profileCategoryId","")
                    .put("aboutProfile",""+about_profile.getText().toString()).put("designation",""+designation.getText().toString()).put("areaofActivity",""+about_profile.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignIpJSon",""+jsonObject);
        return jsonObject;
    }

    @Override
    public void onVideoResult(JSONObject jsonObject, Boolean status) {


        if (status && jsonObject != null) {
            try {
                jsonObject1 = jsonObject.getJSONArray("result").getJSONObject(0);
                if (Comman.getValueFromJsonObject(jsonObject1, "text").equalsIgnoreCase("")) {
                    title.setVisibility(View.GONE);
                } else {
                    title.setVisibility(View.VISIBLE);
                    title.setText(Comman.getValueFromJsonObject(jsonObject1, "text"));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
            Comman.log("llllltext",""+Comman.getValueFromJsonObject(jsonObject1,"text"));
            Comman.log("lllll",""+Comman.getValueFromJsonObject(jsonObject1,"imagePath"));
            if(Comman.getValueFromJsonObject(jsonObject1,"imagePath").equalsIgnoreCase(""))
            {
                image.setVisibility(View.GONE);
            }else {
                image.setVisibility(View.VISIBLE);
                Comman.setRectangleImage(DecisionMaker.this,image,Comman.getValueFromJsonObject(jsonObject1,"imagePath"));
            }
            if(Comman.getValueFromJsonObject(jsonObject1,"videoId").equalsIgnoreCase(""))
            {
                Comman.log("ffffIFFFFFF",""+Comman.getValueFromJsonObject(jsonObject1,"videoId"));
                videoView.setVisibility(View.GONE);
            }else {
                videoView.setVisibility(View.VISIBLE);
                videoView.addYouTubePlayerListener(new AbstractYouTubePlayerListener() {
                    @Override
                    public void onReady(@NonNull YouTubePlayer youTubePlayer) {
//                        youTubePlayer.cueVideo("fGleUo_95zk", 0);
                        youTubePlayer.cueVideo(Comman.getValueFromJsonObject(jsonObject1,"videoId"),0);
                    }
                });
            }
            if(Comman.getValueFromJsonObject(jsonObject1,"videoId").equalsIgnoreCase("") && Comman.getValueFromJsonObject(jsonObject1,"imagePath").equalsIgnoreCase(""))
            {
                ll.setVisibility(View.GONE);
            }else {
                ll.setVisibility(View.VISIBLE);
            }
        }
    }

    public JSONObject videoJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId","0");
            Comman.log("Video",""+jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == Comman.LINKDIN_CODE && data != null) {
            if (resultCode == RESULT_OK) {
                //Successfully signed in
                LinkedInUser user = data.getParcelableExtra("social_login");

                if(user!=null){

                    login_txt.setVisibility(View.VISIBLE);
                    login_button.setVisibility(View.GONE);
                    name.setText(user.getFirstName() +" "+user.getLastName());
                    email.setText(user.getEmail());
                    //acessing user info

                    Log.i("LinkedInLogin", user.getEmail());
                }


            } else {

                if (data.getIntExtra("err_code", 0) == LinkedInBuilder.ERROR_USER_DENIED) {
                    //Handle : user denied access to account
                    Comman.topSnakBar(getApplicationContext(),getWindow().getDecorView().getRootView(),"user denied access to account");
                } else if (data.getIntExtra("err_code", 0) == LinkedInBuilder.ERROR_FAILED) {
                    Comman.topSnakBar(getApplicationContext(),getWindow().getDecorView().getRootView(),data.getStringExtra("err_message"));
                    //Handle : Error in API : see logcat output for detai
                    //Log.e("LINKEDIN ERROR", data.getStringExtra("err_message"));
                }
            }
        }

    }

    public   void   alertBox(Context context, String msg, final Intent intent){
        androidx.appcompat.app.AlertDialog.Builder builder1 = new androidx.appcompat.app.AlertDialog.Builder(context);
        builder1.setMessage(msg);
        builder1.setCancelable(true);

        builder1.setPositiveButton(
                "OK",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                        startActivity(intent);
                        finish();

                    }
                });
//
//        builder1.setNegativeButton(
//                "No",
//                new DialogInterface.OnClickListener() {
//                    public void onClick(DialogInterface dialog, int id) {
//                        dialog.cancel();
//                    }
//                });

        AlertDialog alert11 = builder1.create();
        alert11.show();

    }

}
