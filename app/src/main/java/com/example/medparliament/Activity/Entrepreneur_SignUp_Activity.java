package  com.example.medparliament.Activity;

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

public class Entrepreneur_SignUp_Activity extends AppCompatActivity implements View.OnClickListener, onResult {
    Segow_UI_EditText name,designation,mobile,email,pwd,cnf_pwd,area_of_activity,define,companyName;
    Button signUp;
    ArrayList<String>genderList=new ArrayList<>();
    Segow_UI_Font interest,gender,profile_category,country;
    onResult onResult;
    RelativeLayout googleLogin;
    private ProgressDialog progressDialog;
    JSONArray doctorIdArray;
    SpinnerDialog spinnerDialog;
    ImageButton bck;
    MySharedPrefrence m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_entrepreneur__sign_up_);
        Animatoo.animateSwipeLeft(Entrepreneur_SignUp_Activity.this);
        doctorIdArray=new JSONArray();
        m=MySharedPrefrence.instanceOf(Entrepreneur_SignUp_Activity.this);
        this.onResult=this;
        define=findViewById(R.id.define);
        companyName=findViewById(R.id.companyname);
        name=findViewById(R.id.name);
        signUp=findViewById(R.id.signup);
        googleLogin=findViewById(R.id.login);
        designation=findViewById(R.id.desgination);
        mobile=findViewById(R.id.mobile);
        email=findViewById(R.id.email);
        pwd=findViewById(R.id.pswd);
        profile_category=findViewById(R.id.profilecatogery);
        area_of_activity=findViewById(R.id.area_activity);
        cnf_pwd=findViewById(R.id.cnfm_pswd);
        gender=findViewById(R.id.gender);
        interest=findViewById(R.id.interest);
        interest.setOnClickListener(this);
        country=findViewById(R.id.country);
        country.setOnClickListener(this);
        signUp.setOnClickListener(this);
        googleLogin.setOnClickListener(this);
        gender.setOnClickListener(this);
        profile_category.setOnClickListener(this);
        bck=findViewById(R.id.bck);
        bck.setOnClickListener(this);
        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        Comman.setMandatory(define,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Define_in_200_words));
        Comman.setMandatory(name,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Name));
        Comman.setMandatory(designation,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Designation));
        Comman.setMandatory(mobile,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Mobile_No));
        Comman.setMandatory(email,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.email));
        Comman.setMandatory(pwd,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Password));
        Comman.setMandatory(cnf_pwd,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Confirm_Password));
        Comman.setMandatory(area_of_activity,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Area_of_Activity));
        Comman.setMandatory(companyName,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Comapny_Name));
        Comman.ChangeFocus(define);
        Comman.ChangeFocus(name);
        Comman.ChangeFocus(designation);
        Comman.ChangeFocus(mobile);
        Comman.ChangeFocus(email);
        Comman.ChangeFocus(pwd);
        Comman.ChangeFocus(cnf_pwd);
        Comman.ChangeFocus(companyName);
        Comman.ChangeFocus(area_of_activity);
        Comman.setMandatoryTextView(gender,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.gender));
        Comman.setMandatoryTextView(interest,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Interests));
        Comman.setMandatoryTextView(profile_category,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Profile_Category));
        Comman.setMandatoryTextView(country,Entrepreneur_SignUp_Activity.this.getResources().getString(R.string.Country));
        Api_Calling.getStudentIntrestList(Entrepreneur_SignUp_Activity.this, getWindow().getDecorView().getRootView(),URLS.INTEREST,interestJSon());
        Api_Calling.getALLCATEGORY(Entrepreneur_SignUp_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_CATEGORY);
        Api_Calling.getALLCountry(Entrepreneur_SignUp_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_COUNTRY);

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.interest:
                setMultiChoice();
                break;
            case R.id.country:
                showPopup(Api_Calling.CountrytList,"Select Country",country);
                spinnerDialog.showSpinerDialog();
                break;
            case R.id.bck:
                onBackPressed();
                break;
            case R.id.profilecatogery:
                showPopup(Api_Calling.ProfileList,"Select Profile Category",profile_category);
                spinnerDialog.showSpinerDialog();
                break;
            case R.id.signup:
                if(!name.getText().toString().isEmpty() && !companyName.getText().toString().isEmpty() && !designation.getText().toString().isEmpty() && !email.getText().toString().isEmpty() && !mobile.getText().toString().isEmpty()
                && !gender.getText().toString().isEmpty() && !pwd.getText().toString().isEmpty() && !cnf_pwd.getText().toString().isEmpty() &&
                !area_of_activity.getText().toString().isEmpty() && !interest.getText().toString().isEmpty() && !profile_category.getText().toString().isEmpty()) {
                    if(Comman.isVisible(define)){
                        if(!define.getText().toString().isEmpty())
                        {
                            if (pwd.getText().toString().equals(cnf_pwd.getText().toString())) {
                                progressDialog = new ProgressDialog(Entrepreneur_SignUp_Activity.this);
                                progressDialog.setMessage("Loading...");
                                progressDialog.setCancelable(true);
                                progressDialog.show();
                                Api_Calling.postMethodCall(Entrepreneur_SignUp_Activity.this, getWindow().getDecorView().getRootView(), onResult, URLS.SIGNUP, setStudentJson(), "SignUp");
                                break;
                            } else {
                                Comman.topSnakBar(Entrepreneur_SignUp_Activity.this, v, Constant.PASSWORD_NOT_MATCH);
                            }

                        }else {
                            Comman.topSnakBar(Entrepreneur_SignUp_Activity.this, v, Constant.PLEASE_FILL_ALL_FIELD);
                        }
                    }else {
                        if (pwd.getText().toString().equals(cnf_pwd.getText().toString())) {
                            progressDialog = new ProgressDialog(Entrepreneur_SignUp_Activity.this);
                            progressDialog.setMessage("Loading...");
                            progressDialog.setCancelable(true);
                            progressDialog.show();
                            Api_Calling.postMethodCall(Entrepreneur_SignUp_Activity.this, getWindow().getDecorView().getRootView(), onResult, URLS.SIGNUP, setStudentJson(), "SignUp");
                            break;
                        } else {
                            Comman.topSnakBar(Entrepreneur_SignUp_Activity.this, v, Constant.PASSWORD_NOT_MATCH);
                        }
                    }
                }else {
                    Comman.topSnakBar(Entrepreneur_SignUp_Activity.this, v, Constant.PLEASE_FILL_ALL_FIELD);
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
                interest.setTextColor(Color.WHITE);
            }
        });
        builder.show();
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
                    put("userTypeId","6")
                    .put("gender",""+id).put("companyName",""+companyName.getText().toString())
                    .put("password",""+pwd.getText().toString()).put("deviceType","").
                    put("os","").put("ipAddress","").put("country",""+Api_Calling.CountryHash.get(country.getText().toString()))
                    .put("city","").put("deviceid",""+Comman.uniqueId(Entrepreneur_SignUp_Activity.this))
                    .put("ImeiNo",""+Comman.getIMEI(Entrepreneur_SignUp_Activity.this))
                    .put("interestId",doctorIdArray).put("profileCategoryId",""+Api_Calling.ProfileHash.get(profile_category.getText().toString()))
                    .put("aboutProfile",""+define.getText().toString()).put("designation",""+designation.getText().toString()).put("areaofActivity",""+area_of_activity.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignIpJSon",""+jsonObject);
        return jsonObject;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Entrepreneur_SignUp_Activity.this);
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
                Intent i = new Intent(Entrepreneur_SignUp_Activity.this, Login_Activity.class);
                i.putExtra("username", "" + Comman.getValueFromJsonObject(jsonObject1, "userName"));
                startActivity(i);
                finish();

            }
        }
    }
    public void showPopup(ArrayList<String>items, String title, final Segow_UI_Font segow_ui_font)
    {
        spinnerDialog=new SpinnerDialog(Entrepreneur_SignUp_Activity.this,items,title,"");// With No Animation
//        spinnerDialog=new SpinnerDialog(Entrepreneur_SignUp_Activity.this,items,title,R.style.DialogAnimations_SmileWindow,"");// With 	Animation
        spinnerDialog.setCancellable(true); // for cancellable
        spinnerDialog.setShowKeyboard(false);// for open keyboard by default
        spinnerDialog.bindOnSpinerListener(new OnSpinerItemClick() {
            @Override
            public void onClick(String item, int position) {
                segow_ui_font.setText(item);
                segow_ui_font.setTextColor(Color.WHITE);
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

}
