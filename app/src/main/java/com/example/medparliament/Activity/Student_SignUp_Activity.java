package com.example.medparliament.Activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.provider.Settings;
import android.text.Editable;
import android.text.Html;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.VideoListener;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.Constant;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_EditText;
import com.example.medparliament.Widget.Segow_UI_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;
import com.google.android.gms.common.api.Api;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.AbstractYouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Student_SignUp_Activity extends Base_Activity implements View.OnClickListener, onResult, VideoListener {
    Button signup;
    JSONArray doctorIdArray;
    RelativeLayout googleLogin;
    ArrayList<String> items = new ArrayList<>();
    ArrayList<String> genderList = new ArrayList<>();
    SpinnerDialog spinnerDialog;
    onResult onResult;

    VideoListener videoListener;
    ImageView image;
    YouTubePlayerView videoView;
    LinearLayout ll;
    Segow_UI_Semi_Font title;
    JSONObject jsonObject1 = new JSONObject();
    MySharedPrefrence m;
    String s1 = "";
    String s2 = "";
    private ProgressDialog progressDialog;
    Segow_UI_Font gender, interest, bacth, country;
    Segow_UI_EditText name, mobile, email, pwd, cnfpwd, address, instuteName, universityaddress, define, qualifiaction, university;
    ImageButton bck;
    String userTypeId = "";
    String emailPattern = "[a-zA-Z0-9._-]+@[a-z]+\\.+[a-z]+";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student__sign_up_);
        Animatoo.animateSwipeLeft(Student_SignUp_Activity.this);
        this.onResult = this;
        this.videoListener = this;
        m = MySharedPrefrence.instanceOf(Student_SignUp_Activity.this);
        Intent i = getIntent();
        if (i != null)
            userTypeId = i.getStringExtra("userType");
        name = findViewById(R.id.name);
        mobile = findViewById(R.id.mobile);
        interest = findViewById(R.id.interest);
        email = findViewById(R.id.email);
        pwd = findViewById(R.id.pswd);
        cnfpwd = findViewById(R.id.confr_pswd);
        address = findViewById(R.id.address);
        qualifiaction = findViewById(R.id.qualificatin);
        bacth = findViewById(R.id.bach);

        image=findViewById(R.id.image);
        ll=findViewById(R.id.ll);
        videoView = (YouTubePlayerView) findViewById(R.id.video);
        title = findViewById(R.id.videoTitle);



        gender = findViewById(R.id.gender);
        instuteName = findViewById(R.id.instutionName);
        university = findViewById(R.id.universityname);
        universityaddress = findViewById(R.id.universityAddress);
        define = findViewById(R.id.define);
        signup = findViewById(R.id.signup);
        country = findViewById(R.id.country);
        googleLogin = findViewById(R.id.googleLogin);
        qualifiaction.setOnClickListener(this);
        university.setOnClickListener(this);
        gender.setOnClickListener(this);
        signup.setOnClickListener(this);
        interest.setOnClickListener(this);
        bacth.setOnClickListener(this);
        bck = findViewById(R.id.bck);
        bck.setOnClickListener(this);
        country.setOnClickListener(this);
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        doctorIdArray = new JSONArray();
        Api_Calling.getALLCountry(Student_SignUp_Activity.this, getWindow().getDecorView().getRootView(), URLS.ALL_COUNTRY);
        Api_Calling.videoApiCalling(Student_SignUp_Activity.this, getWindow().getDecorView().getRootView(), URLS.getSignUpVideo, videoJson(), videoListener);
//        if(Api_Calling.QualificationList.size()==0)
//            Api_Calling.getQualificationListData(Student_SignUp_Activity.this,getWindow().getDecorView().getRootView(), URLS.ALL_QUALIFICATION);
//        if(Api_Calling.UniversityList.size()==0)
//            Api_Calling.getUniversityListData(Student_SignUp_Activity.this,getWindow().getDecorView().getRootView(), URLS.ALL_UNIVERSITY);
        Api_Calling.getStudentIntrestList(Student_SignUp_Activity.this, getWindow().getDecorView().getRootView(), URLS.INTEREST, interestJSon());
        Comman.setMandatory(name, Student_SignUp_Activity.this.getResources().getString(R.string.Name));
        Comman.setMandatory(mobile, Student_SignUp_Activity.this.getResources().getString(R.string.Mobile_No));
        Comman.setMandatory(pwd, Student_SignUp_Activity.this.getResources().getString(R.string.Password));
        Comman.setMandatory(cnfpwd, Student_SignUp_Activity.this.getResources().getString(R.string.Confirm_Password));
        Comman.setMandatory(address, Student_SignUp_Activity.this.getResources().getString(R.string.Address));
        Comman.setMandatory(instuteName, Student_SignUp_Activity.this.getResources().getString(R.string.Institution_Name));
        Comman.setMandatory(universityaddress, Student_SignUp_Activity.this.getResources().getString(R.string.University_Address));
        Comman.setMandatory(define, Student_SignUp_Activity.this.getResources().getString(R.string.Define_in_200_words));
        Comman.setMandatory(email, Student_SignUp_Activity.this.getResources().getString(R.string.email));
        Comman.setMandatoryTextView(gender, Student_SignUp_Activity.this.getResources().getString(R.string.gender));
        Comman.setMandatory(qualifiaction, Student_SignUp_Activity.this.getResources().getString(R.string.Qualification));
        Comman.setMandatoryTextView(bacth, Student_SignUp_Activity.this.getResources().getString(R.string.Batch_of_Qualification));
        Comman.setMandatoryTextView(interest, Student_SignUp_Activity.this.getResources().getString(R.string.Interests));
        Comman.setMandatory(university, Student_SignUp_Activity.this.getResources().getString(R.string.University));
        Comman.setMandatoryTextView(country, Student_SignUp_Activity.this.getResources().getString(R.string.Country));
        Comman.ChangeFocus(name);
        Comman.ChangeFocus(mobile);
        Comman.ChangeFocus(pwd);
        Comman.ChangeFocus(cnfpwd);
        Comman.ChangeFocus(address);
        Comman.ChangeFocus(instuteName);
        Comman.ChangeFocus(universityaddress);
        Comman.ChangeFocus(define);
        Comman.ChangeFocus(email);
        Comman.ChangeFocus(qualifiaction);
        Comman.ChangeFocus(university);


//        email.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//              if(email.getText().toString().trim().matches(emailPattern) && email.getText().toString().length()>0){}else {
//                  Comman.topSnakBar(Student_SignUp_Activity.this, getWindow().getDecorView().getRootView(), Constant.INVALIDE_MAIL);
//              }
//            }
//        });
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Student_SignUp_Activity.this);
        videoView.release();
    }

    public JSONObject setStudentJson() {
        JSONObject jsonObject = new JSONObject();
        try {
            String id = "";
            if ((gender != null && gender.getTag() != null)) {
                id = gender.getTag().toString();
            } else {
                id = "";
            }
            jsonObject.put("userName", "" + name.getText().toString())
                    .put("mobileNo", "" + mobile.getText().toString())
                    .put("email", "" + email.getText().toString()).
                    put("userTypeId", "7")
                    .put("gender", "" + id)
                    .put("password", "" + pwd.getText().toString()).put("deviceType", "").
                    put("os", "").put("ipAddress", "").put("country", "" + Api_Calling.CountryHash.get(country.getText().toString()))
                    .put("city", "").put("deviceid", "" + Comman.uniqueId(Student_SignUp_Activity.this))
                    .put("ImeiNo", "" + Comman.getIMEI(Student_SignUp_Activity.this))
                    .put("address", "" + address.getText().toString())
                    .put("qualification", "" + qualifiaction.getText().toString())
                    .put("batchofQualification", "" + bacth.getText().toString()).put("institutionName", "" + instuteName.getText().toString())
                    .put("universityName", "" + university.getText().toString()).put("universityAddress", "" + universityaddress.getText().toString())
                    .put("interestId", doctorIdArray)
                    .put("aboutProfile", "" + define.getText().toString()).put("designation", "");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignIpJSon", "" + jsonObject);
        return jsonObject;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.country:
                showPopup(Api_Calling.CountrytList, "Select Country", country);
                spinnerDialog.showSpinerDialog();
                break;
            case R.id.bach:
                setBatch(bacth);
                break;
            case R.id.gender:
                showPopup(genderList, "Select Gender", gender);
                spinnerDialog.showSpinerDialog();
                break;
            case R.id.interest:
                setMultiChoice();
                break;
            case R.id.bck:
                onBackPressed();
                break;
            case R.id.signup:
                if (!name.getText().toString().isEmpty() && !mobile.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !gender.getText().toString().isEmpty()
                        && !pwd.getText().toString().isEmpty() && !cnfpwd.getText().toString().isEmpty() && !address.getText().toString().isEmpty() && !qualifiaction.getText().toString().isEmpty()
                        && !bacth.getText().toString().isEmpty() && !instuteName.getText().toString().isEmpty() && !university.getText().toString().isEmpty()
                        && !universityaddress.getText().toString().isEmpty() && !interest.getText().toString().isEmpty()) {
                    if (Comman.isVisible(define)) {
                        if (!define.getText().toString().isEmpty()) {
                            if (pwd.getText().toString().equals(cnfpwd.getText().toString())) {
                                progressDialog = new ProgressDialog(Student_SignUp_Activity.this);
                                progressDialog.setMessage("Loading...");
                                progressDialog.setCancelable(true);
                                progressDialog.show();
                                Api_Calling.postMethodCall(Student_SignUp_Activity.this, getWindow().getDecorView().getRootView(), onResult, URLS.SIGNUP, setStudentJson(), "SignUp");
                                break;
                            } else {
                                Comman.topSnakBar(Student_SignUp_Activity.this, v, Constant.PASSWORD_NOT_MATCH);
                            }
                        } else {
                            Comman.topSnakBar(Student_SignUp_Activity.this, v, Constant.PLEASE_FILL_ALL_FIELD);
                        }
                    } else {
                        if (pwd.getText().toString().equals(cnfpwd.getText().toString())) {
                            progressDialog = new ProgressDialog(Student_SignUp_Activity.this);
                            progressDialog.setMessage("Loading...");
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                            Api_Calling.postMethodCall(Student_SignUp_Activity.this, getWindow().getDecorView().getRootView(), onResult, URLS.SIGNUP, setStudentJson(), "SignUp");
                            break;
                        } else {
                            Comman.topSnakBar(Student_SignUp_Activity.this, v, Constant.PASSWORD_NOT_MATCH);
                        }
                    }
                } else {
                    Comman.topSnakBar(Student_SignUp_Activity.this, v, Constant.PLEASE_FILL_ALL_FIELD);
                }
        }
    }

    public void showPopup(ArrayList<String> items, String title, final Segow_UI_Font segow_ui_font) {
        spinnerDialog = new SpinnerDialog(Student_SignUp_Activity.this, items, title, "");// With No Animation
//        spinnerDialog=new SpinnerDialog(Student_SignUp_Activity.this,items,title,R.style.DialogAnimations_SmileWindow,"");// With 	Animation
        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                segow_ui_font.setText(item);
                segow_ui_font.setTextColor(Color.WHITE);
                segow_ui_font.setTag(position);
            }
        });
    }

    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        progressDialog.dismiss();
        if (jsonObject != null && status) {
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
                videoView.release();
                Intent i = new Intent(Student_SignUp_Activity.this, Login_Activity.class);
                i.putExtra("username", "" + Comman.getValueFromJsonObject(jsonObject1, "userName"));
                startActivity(i);
                finish();

            }
        }
    }

    public void setMultiChoice() {
        define.setVisibility(View.GONE);
        define.setText("");
        interest.setText("");
        doctorIdArray = new JSONArray();
        final ArrayList<String> value = new ArrayList<>();
        final String[] str = new String[Api_Calling.StudentIntrestList.size()];
        boolean[] barray = new boolean[Api_Calling.StudentIntrestList.size()];
        for (int i = 0; i < Api_Calling.StudentIntrestList.size(); i++) {
            str[i] = Api_Calling.StudentIntrestList.get(i);
            barray[i] = false;
        }
        CFAlertDialog.Builder builder = new CFAlertDialog.Builder(this, R.style.MyCheckBox);
        builder.setDialogStyle(CFAlertDialog.CFAlertStyle.ALERT);
        builder.setMessage("Select Interest").setTextColor(Color.WHITE);
        builder.setMultiChoiceItems(str, barray, new DialogInterface.OnMultiChoiceClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int index, boolean b) {
                if ((index) == Api_Calling.StudentIntrestList.size() - 1) {
                    dialogInterface.dismiss();
                    define.setVisibility(View.VISIBLE);
                    interest.setText(str[index]);
                    interest.setTextColor(Color.WHITE);
                } else {
                    Comman.log("Selected " + index, "" + str[index] + " Status" + Api_Calling.StudentIntrestList.size());
                    if (b) {
                        value.add(str[index]);
                        Comman.log("interesr", "" + Api_Calling.StudentIntrestHash.get(str[index]));
                        doctorIdArray.put(Integer.valueOf(Api_Calling.StudentIntrestHash.get(str[index])));
                    } else {
                        doctorIdArray.remove(index);
                        value.remove(str[index]);
                    }
                }
            }
        });
        builder.addButton("  Ok   ", Color.parseColor("#ffffff"), Color.parseColor("#0e68a7"), CFAlertDialog.CFAlertActionStyle.POSITIVE, CFAlertDialog.CFAlertActionAlignment.END, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {
                dialogInterface.dismiss();
                Comman.log("Intersest Final Arrya", "" + doctorIdArray.toString());
                interest.setText(value.toString().replace("[", "").replace("]", "").trim());
                interest.setTextColor(Color.WHITE);
            }
        });
        builder.show();
    }

    public JSONObject interestJSon() {
        JSONObject jsonObject = new JSONObject();
        try {
            jsonObject.put("id", "1");
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Interst json", "" + jsonObject);
        return jsonObject;
    }


    public void setBatch(final TextView textView) {
        final MaterialAlertDialogBuilder alertDialogBuilder = new MaterialAlertDialogBuilder(Student_SignUp_Activity.this);
        final androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        final View view = LayoutInflater.from(Student_SignUp_Activity.this).inflate(R.layout.batch_layout, null, false);
        final NumberPicker np1 = view.findViewById(R.id.number1);
        NumberPicker np2 = view.findViewById(R.id.number2);
        final Button ok;
        ok = view.findViewById(R.id.yes);
        alertDialog.setView(view);
        alertDialog.show();
        s1 = "2015";
        s2 = "2016";
        np1.setMinValue(2000);
        np2.setMinValue(2000);
        np1.setMaxValue(2050);
        np2.setMaxValue(2050);
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                s1 = String.valueOf(newVal);
            }
        });
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                s2 = String.valueOf(newVal);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(s1 + "-" + s2);
                textView.setTextColor(Color.WHITE);
                alertDialog.dismiss();
            }
        });
    }

    @Override
    public void onVideoResult(final JSONObject jsonObject, Boolean status) {
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
                Comman.setRectangleImage(Student_SignUp_Activity.this,image,Comman.getValueFromJsonObject(jsonObject1,"imagePath"));
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
            jsonObject.put("userTypeId","7");
            Comman.log("Video",""+jsonObject);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
}
