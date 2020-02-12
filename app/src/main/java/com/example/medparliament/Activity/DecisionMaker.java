package com.example.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

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
import com.example.medparliament.Widget.Segow_UI_EditText;
import com.example.medparliament.Widget.Segow_UI_Font;

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
    com.example.medparliament.Internet.onResult onResult;
    RelativeLayout googleLogin;
    private ProgressDialog progressDialog;
    JSONArray doctorIdArray;
    SpinnerDialog spinnerDialog;
    ImageButton bck;
    MySharedPrefrence m;
    ArrayList<String> genderList=new ArrayList<>();
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
        Api_Calling.getALLCountry(DecisionMaker.this,getWindow().getDecorView().getRootView(), URLS.ALL_COUNTRY);
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
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(DecisionMaker.this);
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
        builder.setMessage("Select Interest").setTextColor(Color.WHITE);
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
                Intent i = new Intent(DecisionMaker.this, Login_Activity.class);
                i.putExtra("username", "" + Comman.getValueFromJsonObject(jsonObject1, "userName"));
                startActivity(i);
                finish();
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
                segow_ui_font.setTextColor(Color.WHITE);
                segow_ui_font.setTag(position);
            }
        });
    }
    public JSONObject setStudentJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            String id="";
            if((gender!=null && gender.getTag()!=null)){
                id=gender.getTag().toString();}else {
                id="";
            }
            jsonObject.put("userName",""+name.getText().toString())
                    .put("mobileNo",""+mobile.getText().toString())
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

    }
}
