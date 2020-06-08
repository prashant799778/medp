package  com.medparliament.Activity;

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

public class Professonal_signup_Activity extends AppCompatActivity implements View.OnClickListener, onResult, VideoListener {
    Segow_UI_EditText name,address,designation,mobile,email,pwd,cnf_pwd,area_of_activity,define,companyName,expertise;
    Button signUp;
    ArrayList<String>genderList=new ArrayList<>();
    Segow_UI_Font interest,gender,profile_category,country;
    onResult onResult;
    RelativeLayout googleLogin;
    private ProgressDialog progressDialog;
    JSONArray doctorIdArray;
    SpinnerDialog spinnerDialog;
    ImageButton bck;
    CountryCodePicker ccpLoadNumber;
    VideoListener videoListener;
    ImageView image;
    YouTubePlayerView videoView;
    LinearLayout ll;
    Segow_UI_Semi_Font title;
    JSONObject jsonObject1 = new JSONObject();
    ImageButton login_button;
    Segow_UI_Bold_Font login_txt;
    MySharedPrefrence m;
    String countrycode="91";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_professor_signup_);
        Animatoo.animateSwipeLeft(Professonal_signup_Activity.this);
        doctorIdArray=new JSONArray();
        m=MySharedPrefrence.instanceOf(Professonal_signup_Activity.this);
        this.onResult=this;
        define=findViewById(R.id.define);
        companyName=findViewById(R.id.companyname);
        ccpLoadNumber=findViewById(R.id.ccp);
        expertise=findViewById(R.id.expertise);
        name=findViewById(R.id.name);
        address=findViewById(R.id.address);
        signUp=findViewById(R.id.signup);
        googleLogin=findViewById(R.id.login);
        designation=findViewById(R.id.desgination);
        mobile=findViewById(R.id.mobile);
        ccpLoadNumber.registerCarrierNumberEditText(mobile);
        email=findViewById(R.id.email);
        pwd=findViewById(R.id.pswd);
        profile_category=findViewById(R.id.profilecatogery);
        area_of_activity=findViewById(R.id.area_activity);
        cnf_pwd=findViewById(R.id.cnfm_pswd);
        gender=findViewById(R.id.gender);
        interest=findViewById(R.id.interest);
        interest.setOnClickListener(this);
        signUp.setOnClickListener(this);
        country=findViewById(R.id.country);
        country.setOnClickListener(this);
        googleLogin.setOnClickListener(this);
        gender.setOnClickListener(this);
        profile_category.setOnClickListener(this);

        this.videoListener=this;
        image=findViewById(R.id.image);
        ll=findViewById(R.id.ll);
        videoView = (YouTubePlayerView) findViewById(R.id.video);
        title = findViewById(R.id.videoTitle);
        login_txt=findViewById(R.id.login_text);

        bck=findViewById(R.id.bck);
        bck.setOnClickListener(this);
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        Comman.setMandatory(address,Professonal_signup_Activity.this.getResources().getString(R.string.Address));
        Comman.setMandatory(expertise,Professonal_signup_Activity.this.getResources().getString(R.string.Occupation));
        Comman.setMandatory(define,Professonal_signup_Activity.this.getResources().getString(R.string.Define_in_200_words));
        Comman.setMandatory(name,Professonal_signup_Activity.this.getResources().getString(R.string.Name));
        Comman.setMandatory(designation,Professonal_signup_Activity.this.getResources().getString(R.string.Designation));
        Comman.setMandatory(mobile,Professonal_signup_Activity.this.getResources().getString(R.string.Mobile_No));
        Comman.setMandatory(email,Professonal_signup_Activity.this.getResources().getString(R.string.email));
        Comman.setMandatory(pwd,Professonal_signup_Activity.this.getResources().getString(R.string.Password));
        Comman.setMandatory(cnf_pwd,Professonal_signup_Activity.this.getResources().getString(R.string.Confirm_Password));
        Comman.setMandatory(area_of_activity,Professonal_signup_Activity.this.getResources().getString(R.string.CompanyAddress));
        Comman.setMandatory(companyName,Professonal_signup_Activity.this.getResources().getString(R.string.Comapny_Name));
        Comman.ChangeFocus(define);
        Comman.ChangeFocus(name);
        Comman.ChangeFocus(address);
        Comman.ChangeFocus(designation);
        Comman.ChangeFocus(mobile);
        Comman.ChangeFocus(email);
        Comman.ChangeFocus(pwd);
        Comman.ChangeFocus(expertise);
        Comman.ChangeFocus(cnf_pwd);
        Comman.ChangeFocus(companyName);
        Comman.ChangeFocus(area_of_activity);
        Comman.setMandatoryTextView(gender,Professonal_signup_Activity.this.getResources().getString(R.string.gender));
        Comman.setMandatoryTextView(interest,Professonal_signup_Activity.this.getResources().getString(R.string.Interests));
        Comman.setMandatoryTextView(profile_category,Professonal_signup_Activity.this.getResources().getString(R.string.Qualification));
        Comman.setMandatoryTextView(country,Professonal_signup_Activity.this.getResources().getString(R.string.Country));
        Api_Calling.getStudentIntrestList(Professonal_signup_Activity.this, getWindow().getDecorView().getRootView(),URLS.INTEREST,interestJSon());
        Api_Calling.getALLCATEGORY(Professonal_signup_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_CATEGORY);
        if(Api_Calling.QualificationList.size()==0)
            Api_Calling.getQualificationListData(Professonal_signup_Activity.this,getWindow().getDecorView().getRootView(), URLS.ALL_QUALIFICATION);
        Api_Calling.getALLCountry(Professonal_signup_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_COUNTRY);
        Api_Calling.videoApiCalling(Professonal_signup_Activity.this, getWindow().getDecorView().getRootView(), URLS.getSignUpVideo, videoJson(), videoListener);

        login_button=findViewById(R.id.login_button);
        login_button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                LinkedInBuilder.getInstance(Professonal_signup_Activity.this)
                        .setClientID(Comman.CLIENT_ID)
                        .setClientSecret(Comman.CLIENT_SECRET)
                        .setRedirectURI(Comman.REDIRECT_URL)
                        .authenticate(Comman.LINKDIN_CODE);
            }
        });


        ccpLoadNumber.setOnCountryChangeListener(new CountryCodePicker.OnCountryChangeListener() {
            @Override
            public void onCountrySelected() {
                countrycode= ccpLoadNumber.getSelectedCountryCode();
            }
        });
    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.country:
                showPopup(Api_Calling.CountrytList,"Select Country",country);
                spinnerDialog.showSpinerDialog();
                break;
            case R.id.interest:
                setMultiChoice();
                break;
            case R.id.bck:
                onBackPressed();
                break;
            case R.id.profilecatogery:
                showPopup(Api_Calling.QualificationList,"Select Qualification",profile_category);
                spinnerDialog.showSpinerDialog();
                break;
            case R.id.signup:
                if(!name.getText().toString().isEmpty() &&  !address.getText().toString().isEmpty()&& !companyName.getText().toString().isEmpty() && !designation.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !mobile.getText().toString().isEmpty()
                        && !pwd.getText().toString().isEmpty() && !cnf_pwd.getText().toString().isEmpty() && !expertise.getText().toString().isEmpty() &&
                        !area_of_activity.getText().toString().isEmpty() && !interest.getText().toString().isEmpty() && !gender.getText().toString().isEmpty() ) {
                    if(Comman.isVisible(define)){
                        if(!define.getText().toString().isEmpty())
                        {
                            if (pwd.getText().toString().equals(cnf_pwd.getText().toString())) {
                                progressDialog = new ProgressDialog(Professonal_signup_Activity.this);
                                progressDialog.setMessage("Loading...");
                                progressDialog.setCancelable(true);
                                progressDialog.show();
                                Api_Calling.postMethodCall(Professonal_signup_Activity.this, getWindow().getDecorView().getRootView(), onResult, URLS.SIGNUP, setStudentJson(), "SignUp");
                                break;
                            } else {
                                Comman.topSnakBar(Professonal_signup_Activity.this, v, Constant.PASSWORD_NOT_MATCH);
                            }

                        }else {
                            Comman.topSnakBar(Professonal_signup_Activity.this, v, Constant.PLEASE_FILL_ALL_FIELD);
                        }
                    }else {
                        if (pwd.getText().toString().equals(cnf_pwd.getText().toString())) {
                            progressDialog = new ProgressDialog(Professonal_signup_Activity.this);
                            progressDialog.setMessage("Loading...");
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                            Api_Calling.postMethodCall(Professonal_signup_Activity.this, getWindow().getDecorView().getRootView(), onResult, URLS.SIGNUP, setStudentJson(), "SignUp");
                            break;
                        } else {
                            Comman.topSnakBar(Professonal_signup_Activity.this, v, Constant.PASSWORD_NOT_MATCH);
                        }
                    }
                }else {
                    Comman.topSnakBar(Professonal_signup_Activity.this, v, Constant.PLEASE_FILL_ALL_FIELD);
                }
            case R.id.login:
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
                interest.setTextColor(Color.BLACK);
            }
        });
        builder.show();
    }


    public JSONObject setStudentJson()
    {     //ccpLoadNumber.setFullNumber(mobile.getText().toString());
        JSONObject jsonObject=new JSONObject();
        try {
            String id="";
            if((gender!=null && gender.getTag()!=null)){
                id=gender.getTag().toString();}else {
                id="";
            }
            jsonObject.put("userName",""+name.getText().toString())
                    .put("notification_token", "" + m.getFCMToken())
                    .put("mobileNo",countrycode+"-"+ mobile.getText().toString())
                    .put("email",""+email.getText().toString()) .put("address",""+address.getText().toString()).
                    put("userTypeId","9")
                    .put("gender",""+id).put("companyName",""+companyName.getText().toString())
                    .put("password",""+pwd.getText().toString()).put("deviceType","").
                    put("os","").put("ipAddress","").put("country",""+Api_Calling.CountryHash.get(country.getText().toString()))
                    .put("city","").put("deviceid",""+Comman.uniqueId(Professonal_signup_Activity.this))
                    .put("ImeiNo",""+Comman.getIMEI(Professonal_signup_Activity.this)).put("occupation",expertise.getText().toString())
                    .put("interestId",doctorIdArray).put("qualification",""+Api_Calling.QualifiactionHashMap.get(profile_category.getText().toString()))
                    .put("aboutProfile",""+define.getText().toString()).put("designation",""+designation.getText().toString()).put("companyAddress",""+area_of_activity.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignIpJSon",""+jsonObject);
        return jsonObject;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Professonal_signup_Activity.this);
        videoView.release();
    }
    @Override
    public void onResult(JSONObject jsonObject,Boolean status) {
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
                Intent i = new Intent(Professonal_signup_Activity.this, Login_Activity.class);
                i.putExtra("username", "" + Comman.getValueFromJsonObject(jsonObject1, "userName"));
//                startActivity(i);
//                finish();
                try {
                    alertBox(Professonal_signup_Activity.this,jsonObject.getString("message"),i);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    public void showPopup(ArrayList<String>items, String title, final Segow_UI_Font segow_ui_font)
    {
        spinnerDialog=new SpinnerDialog(Professonal_signup_Activity.this,items,title,"");// With No Animation
//        spinnerDialog=new SpinnerDialog(Professonal_signup_Activity.this,items,title,R.style.DialogAnimations_SmileWindow,"");// With 	Animation
        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                segow_ui_font.setText(item);
                segow_ui_font.setTextColor(Color.BLACK);
                segow_ui_font.setTag(position);
                if(item.equalsIgnoreCase("Service")||item.equalsIgnoreCase("manufacturing"))
                {
                    define.setVisibility(View.VISIBLE);
                }else {
                    define.setVisibility(View.GONE);

                }
            }
        });
    }
    public JSONObject interestJSon()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id","2");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Interst json",""+jsonObject);
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
                Comman.setRectangleImage(Professonal_signup_Activity.this,image,Comman.getValueFromJsonObject(jsonObject1,"imagePath"));
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
            jsonObject.put("userTypeId","9");
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
                    //Handle : Error in API : see logcat output for details
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
