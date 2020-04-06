package  com.medparliament.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.TextView;
import android.widget.Toast;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.bumptech.glide.Glide;
import com.crowdfire.cfalertdialog.CFAlertDialog;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.Segow_UI_EditText;
import com.medparliament.Widget.Segow_UI_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.fxn.pix.Options;
import com.fxn.pix.Pix;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;
import in.galaxyofandroid.spinerdialog.OnSpinerItemClick;
import in.galaxyofandroid.spinerdialog.SpinnerDialog;

public class Edit_Profile_Activity extends Base_Activity implements onResult {
    ImageView bck;
    JSONArray doctorIdArray;
    CircleImageView profile_image;
    LinearLayout l1,l2,l3,l4,l5,l6,l7,l8,l9,l10,lll;
    Segow_UI_Semi_Font save;
    Segow_UI_Font l_name,l_email,l_institute_name,l_university_name,l_mobile,l_university_address,l_qulafication,l_batch,l_address,l_iterest,l_new;
    ProgressDialog progressDialog;
    Segow_UI_EditText name,email,institute_name,university_name,mobile,university_address,qualfication,batch,address,interest,newE;
    public ArrayList<String>array1;
    onResult onResult;
    SpinnerDialog spinnerDialog;
    String s1="";
    String s2="";
    MySharedPrefrence m;
    ArrayList<String> genderList=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit__profile_);
        array1=new ArrayList<>();
        this.onResult=this;
        Animatoo.animateSwipeLeft(Edit_Profile_Activity.this);
        Window window = getWindow();
        m=MySharedPrefrence.instanceOf(Edit_Profile_Activity.this);
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        bck=findViewById(R.id.bckii);

        genderList.add("Male");
        genderList.add("Female");
        genderList.add("Other");
        save=findViewById(R.id.save);
        l1=findViewById(R.id.l1);
        l2=findViewById(R.id.l2);
        l3=findViewById(R.id.l3);
        l4=findViewById(R.id.l4);
        l5=findViewById(R.id.l5);
        l6=findViewById(R.id.l6);
        l7=findViewById(R.id.l7);
        l8=findViewById(R.id.l8);
        l9=findViewById(R.id.l9);
        l10=findViewById(R.id.l10);
        lll=findViewById(R.id.kkkk);
        newE=findViewById(R.id.new_e);
        l_new=findViewById(R.id.l_new);
        l_name=findViewById(R.id.l_name);
        name=findViewById(R.id.name);
        l_email=findViewById(R.id.l_email);
        email=findViewById(R.id.email);
        l_institute_name=findViewById(R.id.l_institute_name);
        institute_name=findViewById(R.id.institute_name);
        l_university_name=findViewById(R.id.l_unversity_name);
        university_name=findViewById(R.id.university_name);
//        university_name.setFocusable(false);
//        university_name.setFocusableInTouchMode(false);
        l_mobile=findViewById(R.id.l_mobile);
        mobile=findViewById(R.id.mobile);
        l_university_address=findViewById(R.id.l_University_address);
        university_address=findViewById(R.id.university_address);
        l_qulafication=findViewById(R.id.l_qualification);
        qualfication=findViewById(R.id.qualificatin);
//        qualfication.setFocusable(false);
//       qualfication.setFocusableInTouchMode(false);
        l_batch=findViewById(R.id.l_bach_of_qualification);
        batch=findViewById(R.id.bach);
       batch.setFocusable(false);
       batch.setFocusableInTouchMode(false);
        university_name.setFocusable(false);
        university_name.setFocusableInTouchMode(false);
        l_name=findViewById(R.id.l_name);
        name=findViewById(R.id.name);
        l_address=findViewById(R.id.l_personal);
        address=findViewById(R.id.address);
        address.setFocusable(false);
        address.setFocusableInTouchMode(false);
        l_iterest=findViewById(R.id.l_interest);
        interest=findViewById(R.id.interest);
        profile_image=findViewById(R.id.profile_image);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        profile_image.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Pix.start(Edit_Profile_Activity.this, Options.init().setRequestCode(500).setCount(1));
            }
        });

        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Edit_Profile_Activity.this);
                progressDialog.setMessage("Updating...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                if(m.getUserTypeId().equalsIgnoreCase("7")){
                Api_Calling.multiPartCall1(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.UpdateUser,setStudentJson(),onResult,"Student",array1);
                }
                else if(m.getUserTypeId().equalsIgnoreCase("6"))
                {
                    Api_Calling.multiPartCall1(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.UpdateUser,setEnterPreuner(),onResult,"Student",array1);

                }else if(m.getUserTypeId().equalsIgnoreCase("5"))
                {
                    Api_Calling.multiPartCall1(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.UpdateUser,setPolicyMakerJson(),onResult,"Student",array1);

                }else if(m.getUserTypeId().equalsIgnoreCase("8"))
                {
                    Api_Calling.multiPartCall1(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.UpdateUser,setDoctor(),onResult,"Student",array1);

                }else if(m.getUserTypeId().equalsIgnoreCase("9"))
                {
                    Api_Calling.multiPartCall1(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.UpdateUser,setProfessional(),onResult,"Student",array1);

                }else if(m.getUserTypeId().equalsIgnoreCase("13"))
                {
                    Api_Calling.multiPartCall1(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.UpdateUser,setDecisionMaker(),onResult,"Student",array1);

                }

            }
        });
//        qualfication.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                if(m.getUserTypeId().equalsIgnoreCase("7"))
//                {
//                    showPopup(Api_Calling.QualificationList,"Select Qualification",qualfication);
//                    spinnerDialog.showSpinerDialog();
//
//                } if(m.getUserTypeId().equalsIgnoreCase("8"))
//                {
//                    showPopup(Api_Calling.QualificationList,"Select Qualification",qualfication);
//                    spinnerDialog.showSpinerDialog();
//
//                }else if(m.getUserTypeId().equalsIgnoreCase("6"))
//                {
//
//                }else if(m.getUserTypeId().equalsIgnoreCase("5"))
//                {
//
//                }
//            }
//        });


        address.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m.getUserTypeId().equalsIgnoreCase("13"))
                {
                    showPopup(Api_Calling.CountrytList,"Select Country",address);
                    spinnerDialog.showSpinerDialog();
                }
            }
        });

        university_name.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m.getUserTypeId().equalsIgnoreCase("7"))
                {
//                    showPopup(Api_Calling.UniversityList,"Select University Name",university_name);
//                    spinnerDialog.showSpinerDialog();

                }else if(m.getUserTypeId().equalsIgnoreCase("6"))
                {
                    showPopup(Api_Calling.ProfileList,"Select Profile Category",university_name);
                    spinnerDialog.showSpinerDialog();
                }else if(m.getUserTypeId().equalsIgnoreCase("5"))
                {
                    showPopup(Api_Calling.CountrytList,"Select Country",university_name);
                    spinnerDialog.showSpinerDialog();
                }
            }
        });
        batch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m.getUserTypeId().equalsIgnoreCase("7"))
                {                    setStudentBatch(batch);
                }else if(m.getUserTypeId().equalsIgnoreCase("6"))
                {

                }else if(m.getUserTypeId().equalsIgnoreCase("13"))
                {
                    showPopup(genderList,"Select Gender",batch);
                    spinnerDialog.showSpinerDialog();
                }
            }
        });
        interest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(m.getUserTypeId().equalsIgnoreCase("7"))
                {
                    setMultiChoice();

                }else if(m.getUserTypeId().equalsIgnoreCase("6"))
                {
                    setMultiChoice();
                }else if(m.getUserTypeId().equalsIgnoreCase("8"))
                {
                    setMultiChoice();
                }else if(m.getUserTypeId().equalsIgnoreCase("9"))
                {
                    setMultiChoice();
                }else if(m.getUserTypeId().equalsIgnoreCase("5"))
                {

                }
            }
        });
        checkUserType(m.getUserTypeId());
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 500) {
            array1 = data.getStringArrayListExtra(Pix.IMAGE_RESULTS);
//            if(!isFinishing()) {
                if(array1.size()>0){
                Comman.setRoundedImage(Edit_Profile_Activity.this,profile_image,array1.get(0).trim());
//                    Toast.makeText(this, "ImagePath"+array1.get(0), Toast.LENGTH_SHORT).show();
                }else {
//                    Toast.makeText(this, "ArraySize"+array1.size(), Toast.LENGTH_SHORT).show();
                }
//            }
        }
        else {
//            Toast.makeText(this, "Request Code Not Match", Toast.LENGTH_SHORT).show();
        }
    }
    public JSONObject setJson()
    {
     JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("ProfileUpdate",""+jsonObject);
        return  jsonObject;
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Edit_Profile_Activity.this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Comman.log("ProfilePicPath","----"+m.getUserProfile());
//        Glide.with(Edit_Profile_Activity.this).load(m.getUserProfile()).into(profile_image);
        Comman.setRoundedImage(Edit_Profile_Activity.this,profile_image,m.getUserProfile());
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(progressDialog!=null &&  progressDialog.isShowing())
        progressDialog.dismiss();
        if(jsonObject!=null && status)
        {
            Comman.log("abcdef","cc"+jsonObject.toString());
            if(!isFinishing()) {
                if(array1!=null && array1.size()>0)
                m.setUserProfile(array1.get(0));
                onBackPressed();
            }
        }
    }
    public void checkUserType(String userId)
    {
        switch (userId)
        {
            case "5":
               policyMaker(userId);
                break;
            case "6":
                entrepreneur(userId);
                break;
            case "7":
                student(userId);
                break;
            case "8":
                doctor(userId);
                break;
            case "9":
                professional(userId);
                break;
            case "13":
                decisionMaker(userId);
                break;
        }
    }

    public void student(String key)
    {
        l9.setVisibility(View.GONE);
        lll.setVisibility(View.GONE);
//        if(Api_Calling.QualificationList.size()==0)
//            Api_Calling.getQualificationListData(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(), URLS.ALL_QUALIFICATION);
//        if(Api_Calling.UniversityList.size()==0)
//            Api_Calling.getUniversityListData(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(), URLS.ALL_UNIVERSITY);
        Api_Calling.getStudentIntrestList(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.INTEREST,interestJSon("1"));
        l_name.setText(getResources().getString(R.string.Name));
        l_email.setText(getResources().getString(R.string.email));
        l_institute_name.setText(getResources().getString(R.string.Institute_Name));
        l_university_name.setText(getResources().getString(R.string.University_name));
        l_mobile.setText(getResources().getString(R.string.Mobile_No));
        l_university_address.setText(getResources().getString(R.string.University_Address));
        l_qulafication.setText(getResources().getString(R.string.Qualification));
        l_batch.setText(getResources().getString(R.string.Batch_of_Qualification));
        l_address.setText(getResources().getString(R.string.Address));
        l_iterest.setText(getResources().getString(R.string.Interests));
        name.setText(m.getUserName());
        email.setText(m.getUserEmail());
        institute_name.setText(m.getInstituteName());
        university_name.setText(m.getUniversityName());
        university_name.setClickable(false);
        mobile.setText(m.getMobile());
        university_address.setText(m.getUniversityAddress());
        qualfication.setText(m.getQualification());
        batch.setText(m.getQualificationBatch());
        interest.setText(m.getInterest());
    }
    public void policyMaker(String key)
    {
        Api_Calling.getALLCountry(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_COUNTRY);
        l7.setVisibility(View.GONE);
        l8.setVisibility(View.GONE);
        l10.setVisibility(View.GONE);
        l9.setVisibility(View.GONE);
        l_university_name.setText(getResources().getString(R.string.Country));
        l_name.setText(getResources().getString(R.string.Name));
        l_email.setText(getResources().getString(R.string.email));
        l_institute_name.setText(getResources().getString(R.string.Designation));
        l_mobile.setText(getResources().getString(R.string.Mobile_No));
        l_university_address.setText(getResources().getString(R.string.Organisation));
        l_new.setText(getResources().getString(R.string.About_Your_Profile));
        name.setText(m.getUserName());
        email.setText(m.getUserEmail());
        institute_name.setText(m.getDasignation());
        university_name.setText(m.getCountry());
        mobile.setText(m.getMobile());
        university_address.setText(m.getOrgatination());
        newE.setText(m.getDescription());

    }
    public void entrepreneur(String key)
    {
        Api_Calling.getStudentIntrestList(Edit_Profile_Activity.this, getWindow().getDecorView().getRootView(),URLS.INTEREST,interestJSon("2"));
        Api_Calling.getALLCATEGORY(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_CATEGORY);
        l6.setVisibility(View.GONE);
        l7.setVisibility(View.GONE);
        l8.setVisibility(View.GONE);
        l9.setVisibility(View.GONE);
        l_name.setText(getResources().getString(R.string.Name));
        l_email.setText(getResources().getString(R.string.email));
        l_institute_name.setText(getResources().getString(R.string.Area_of_Activity));
        l_mobile.setText(getResources().getString(R.string.Mobile_No));
        l_university_name.setText(getResources().getString(R.string.Profile_Category));
        l_iterest.setText(getResources().getString(R.string.Interests));
        l_new.setText(getResources().getString(R.string.Designation));
        name.setText(m.getUserName());
        email.setText(m.getUserEmail());
        institute_name.setText(m.getDescription());
        university_name.setClickable(true);
        university_name.setFocusable(true);
        university_name.setText(m.getProfileCategory());
        mobile.setText(m.getMobile());
        interest.setText(m.getInterest());
        newE.setText(m.getDasignation());
    }
    public void  doctor(String key)
    {
        Api_Calling.getStudentIntrestList(Edit_Profile_Activity.this, getWindow().getDecorView().getRootView(),URLS.INTEREST,interestJSon("2"));
//        Api_Calling.getALLCATEGORY(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_CATEGORY);
        l4.setVisibility(View.VISIBLE);
        l3.setVisibility(View.GONE);
        l6.setVisibility(View.VISIBLE);
        l7.setVisibility(View.VISIBLE);
        l8.setVisibility(View.VISIBLE);
        l9.setVisibility(View.VISIBLE);
        lll.setVisibility(View.GONE);
        l_name.setText(getResources().getString(R.string.Name));
        l_email.setText(getResources().getString(R.string.email));
        name.setText(m.getUserName());
        email.setText(m.getUserEmail());
        l_mobile.setText(getResources().getString(R.string.Mobile_No));
        mobile.setText(m.getMobile());
        l_university_name.setText(getResources().getString(R.string.institute));
        university_name.setText(m.getCompanyNAame());
        university_name.setFocusable(true);
       university_name.setFocusableInTouchMode(true);
       university_name.setClickable(true);
        l_university_address.setText(getResources().getString(R.string.hos_add));
        university_address.setText(m.getUniversityAddress());

        l_qulafication.setText(getResources().getString(R.string.Qualification));
        qualfication.setText(m.getQualification());
        l_batch.setText(getResources().getString(R.string.Designation));
        batch.setText(m.getDasignation());

        batch.setFocusable(true);
        batch.setFocusableInTouchMode(true);
        batch.setClickable(true);

        l_address.setText(getResources().getString(R.string.AreaofExperites));
        address.setText(m.getUserPersonalAddress());
        address.setFocusable(true);
        address.setFocusableInTouchMode(true);
        address.setClickable(true);

        l_iterest.setText(getResources().getString(R.string.Interests));
        interest.setText(m.getInterest());
    }
    public void professional(String key)
    {
        Api_Calling.getStudentIntrestList(Edit_Profile_Activity.this, getWindow().getDecorView().getRootView(),URLS.INTEREST,interestJSon("2"));
//        Api_Calling.getALLCATEGORY(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_CATEGORY);
        l4.setVisibility(View.VISIBLE);
        l3.setVisibility(View.GONE);
        l6.setVisibility(View.VISIBLE);
        l7.setVisibility(View.VISIBLE);
        l8.setVisibility(View.VISIBLE);
        l9.setVisibility(View.VISIBLE);
        lll.setVisibility(View.GONE);
        l_name.setText(getResources().getString(R.string.Name));
        l_email.setText(getResources().getString(R.string.email));
        name.setText(m.getUserName());
        email.setText(m.getUserEmail());
        l_mobile.setText(getResources().getString(R.string.Mobile_No));
        mobile.setText(m.getMobile());
        l_university_name.setText(getResources().getString(R.string.Comapny_Name));
        university_name.setText(m.getCompanyNAame());
       university_name.setFocusable(true);
       university_name.setFocusableInTouchMode(true);
       university_name.setClickable(true);
        l_university_address.setText(getResources().getString(R.string.CompanyAddress));
        university_address.setText(m.getUniversityAddress());

        l_qulafication.setText(getResources().getString(R.string.Occupation));
        qualfication.setText(m.getQualification());
       qualfication.setFocusable(true);
        qualfication.setFocusableInTouchMode(true);
        qualfication.setClickable(true);
        l_batch.setText(getResources().getString(R.string.Designation));
        batch.setText(m.getDasignation());
        batch.setFocusable(true);
        batch.setFocusableInTouchMode(true);
        batch.setClickable(true);
        l_address.setText(getResources().getString(R.string.Address));
        address.setText(m.getUserPersonalAddress());
        address.setFocusable(true);
        address.setFocusableInTouchMode(true);
        address.setClickable(true);
        l_iterest.setText(getResources().getString(R.string.Interests));
        interest.setText(m.getInterest());


    }
    public void decisionMaker(String key)
    {
        Api_Calling.getALLCountry(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_COUNTRY);
        Comman.log("DecisionMaker","Name : "+m.getUserName());
        Comman.log("DecisionMaker","Mobile : "+m.getMobile2());
        Comman.log("DecisionMaker","Email"+m.getUserEmail2());
        Api_Calling.getStudentIntrestList(Edit_Profile_Activity.this, getWindow().getDecorView().getRootView(),URLS.INTEREST,interestJSon("2"));
//        Api_Calling.getALLCATEGORY(Edit_Profile_Activity.this,getWindow().getDecorView().getRootView(),URLS.ALL_CATEGORY);
        l4.setVisibility(View.GONE);
        l3.setVisibility(View.GONE);
        l6.setVisibility(View.GONE);
        l7.setVisibility(View.GONE);
        l5.setVisibility(View.VISIBLE);
        l8.setVisibility(View.VISIBLE);
        l9.setVisibility(View.VISIBLE);
        l10.setVisibility(View.GONE);
        lll.setVisibility(View.GONE);
        l_name.setText(getResources().getString(R.string.Name));
        name.setText(m.getUserName());
        l_email.setText(getResources().getString(R.string.email));
        email.setText(m.getUserEmail());
        l_mobile.setText(getResources().getString(R.string.Mobile_No));
        mobile.setText(m.getMobile2());
        l_batch.setText(getResources().getString(R.string.Gender));
        batch.setText(m.getGender());
        setGender(batch,m.getGender());
        batch.setFocusable(true);
        batch.setClickable(true);
        l_address.setText(getResources().getString(R.string.Country));
        address.setText(m.getCountry());
        address.setFocusable(true);
        address.setClickable(true);
    }
    public JSONObject interestJSon(String id)
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",""+id);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Interst json",""+jsonObject);
        return jsonObject;
    }
    public void setStudentBatch(final EditText textView)
    {
        final MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(Edit_Profile_Activity.this);
        final androidx.appcompat.app.AlertDialog alertDialog = alertDialogBuilder.create();
        final View view = LayoutInflater.from(Edit_Profile_Activity.this).inflate(R.layout.batch_layout, null, false);
        final NumberPicker np1=view.findViewById(R.id.number1);
        NumberPicker np2=view.findViewById(R.id.number2);
        final Button ok;
        ok=view.findViewById(R.id.yes);
        alertDialog.setView(view);
        alertDialog.show();
        s1="2015";
        s2="2016";
        np1.setMinValue(2000);
        np2.setMinValue(2000);
        np1.setMaxValue(2050);
        np2.setMaxValue(2050);
        np1.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                s1=String.valueOf(newVal);
            }
        });
        np2.setOnValueChangedListener(new NumberPicker.OnValueChangeListener() {
            @Override
            public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
                s2=String.valueOf(newVal);
            }
        });
        ok.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                textView.setText(s1+"-"+s2);
                textView.setTextColor(Color.BLACK);
                alertDialog.dismiss();
            }
        });
    }
    public void showPopup(ArrayList<String>items, String title, final EditText segow_ui_font)
    {
        spinnerDialog=new SpinnerDialog(Edit_Profile_Activity.this,items,title,"");// With No Animation
//        spinnerDialog=new SpinnerDialog(Student_SignUp_Activity.this,items,title,R.style.DialogAnimations_SmileWindow,"");// With 	Animation
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
    public void setMultiChoice()
    {
        interest.setText("");
        doctorIdArray=new JSONArray();
        final ArrayList<String>value=new ArrayList<>();
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
//                if ((index) == Api_Calling.StudentIntrestList.size() - 1) {
//                    dialogInterface.dismiss();
//                    define.setVisibility(View.VISIBLE);
//                    interest.setText(str[index]);
//                    interest.setTextColor(Color.BLACK);
//                } else {
                    Comman.log("Selected " + index, "" + str[index] + " Status" + Api_Calling.StudentIntrestList.size());
                    if (b) {
                        value.add(str[index]);
                        Comman.log("interesr", "" + Api_Calling.StudentIntrestHash.get(str[index]));
                        doctorIdArray.put(Integer.valueOf(Api_Calling.StudentIntrestHash.get(str[index])));
                    } else {
                        doctorIdArray.remove(index);
                        value.remove(str[index]);
                    }
//                }
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
    {
        JSONObject jsonObject=new JSONObject();
        try {
            if(doctorIdArray!=null && doctorIdArray.length()>0){
            jsonObject.put("userName",""+name.getText().toString())
                    .put("mobileNo",""+mobile.getText().toString())
                    .put("email",""+email.getText().toString()).
                    put("userTypeId","7")
                    .put("userId",""+m.getUserId())
                    .put("address",""+address.getText().toString())
                    .put("qualification",""+qualfication.getText().toString())
                    .put("batchofQualification",""+batch.getText().toString()).put("institutionName",""+institute_name.getText().toString())
                    .put("universityName",""+university_name.getText().toString()).put("universityAddress",""+university_address.getText().toString())
                    .put("interestId",doctorIdArray);}else {
                jsonObject.put("userName",""+name.getText().toString())
                        .put("mobileNo",""+mobile.getText().toString())
                        .put("email",""+email.getText().toString()).
                        put("userTypeId","7")
                        .put("userId",""+m.getUserId())
                        .put("address",""+address.getText().toString())
                        .put("qualification",""+ qualfication.getText().toString())
                        .put("batchofQualification",""+batch.getText().toString()).put("institutionName",""+institute_name.getText().toString())
                        .put("universityName",""+university_name.getText().toString()).put("universityAddress",""+university_address.getText().toString())
                        .put("interestId","");
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignUpJSon",""+jsonObject);
        return jsonObject;
    }


    public JSONObject setPolicyMakerJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userName",""+name.getText().toString())
                    .put("mobileNo",""+mobile.getText().toString())
                    .put("email",""+email.getText().toString()).
                    put("userTypeId","5").put("userId",""+m.getUserId()).
                    put("country",""+Api_Calling.CountryHash.get(university_name.getText().toString()))
                   .put("designation",""+institute_name.getText().toString()).put("aboutProfile",""+newE.getText().toString()).put("organization",""+university_address.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignUpJSon",""+jsonObject);
        return jsonObject;
    }



    public JSONObject setEnterPreuner()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            if(doctorIdArray!=null && doctorIdArray.length()!=0){
            jsonObject.put("userName",""+name.getText().toString())
                    .put("mobileNo",""+mobile.getText().toString())
                    .put("email",""+email.getText().toString()).
                    put("userTypeId","6").put("userId",""+m.getUserId())
                    .put("interestId",doctorIdArray).put("profileCategoryId",""+Api_Calling.ProfileHash.get(university_name.getText().toString()))
                    .put("areaofActivity",""+institute_name.getText().toString()).put("designation",""+newE.getText().toString());}else {
                jsonObject.put("userName",""+name.getText().toString())
                        .put("mobileNo",""+mobile.getText().toString())
                        .put("email",""+email.getText().toString()).
                        put("userTypeId","6").put("userId",""+m.getUserId())
                        .put("interestId","").put("profileCategoryId",""+Api_Calling.ProfileHash.get(university_name.getText().toString()))
                        .put("areaofActivity",""+institute_name.getText().toString()).put("designation",""+newE.getText().toString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignIpJSon",""+jsonObject);
        return jsonObject;
    }


    public JSONObject setDoctor()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            if(doctorIdArray!=null && doctorIdArray.length()!=0){
                jsonObject.put("userName",""+name.getText().toString())
                        .put("mobileNo",""+mobile.getText().toString())
                        .put("email",""+email.getText().toString()).put("hospital",""+university_name.getText().toString())
                        .put("hospitalAddress",""+university_address.getText().toString()). put("userTypeId","8").put("userId",""+m.getUserId())
                        .put("interestId",doctorIdArray) .put("qualification",qualfication.getText().toString())
                        .put("areaOfExpertise",""+address.getText().toString()).put("designation",""+batch.getText().toString());}else {
                jsonObject.put("userName",""+name.getText().toString())
                        .put("mobileNo",""+mobile.getText().toString())
                        .put("email",""+email.getText().toString()).put("hospital",""+university_name.getText().toString())
                        .put("hospitalAddress",""+university_address.getText().toString()). put("userTypeId","8").put("userId",""+m.getUserId())
                        .put("interestId","") .put("qualification",qualfication.getText().toString())
                        .put("areaOfExpertise",""+address.getText().toString()).put("designation",""+batch.getText().toString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignIpJSonDoctor",""+jsonObject);
        return jsonObject;
    }

    public JSONObject setProfessional()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            if(doctorIdArray!=null && doctorIdArray.length()!=0){
                jsonObject.put("userName",""+name.getText().toString())
                        .put("mobileNo",""+mobile.getText().toString())
                        .put("email",""+email.getText().toString()).put("companyName",""+university_name.getText().toString())
                        .put("companyAddress",""+university_address.getText().toString()). put("userTypeId","9").put("userId",""+m.getUserId())
                        .put("interestId",doctorIdArray) .put("occupation", qualfication.getText().toString())
                        .put("address",""+address.getText().toString()).put("designation",""+batch.getText().toString());
            }else {
                jsonObject.put("userName",""+name.getText().toString())
                        .put("mobileNo",""+mobile.getText().toString())
                        .put("email",""+email.getText().toString()).put("companyName",""+university_name.getText().toString())
                        .put("companyAddress",""+university_address.getText().toString()). put("userTypeId","9").put("userId",""+m.getUserId())
                        .put("interestId","") .put("occupation", qualfication.getText().toString())
                        .put("address",""+address.getText().toString()).put("designation",""+batch.getText().toString());

            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignIpJSon",""+jsonObject);
        return jsonObject;
    }

    public JSONObject setDecisionMaker()
    {
        JSONObject jsonObject=new JSONObject();
        try {
//            if(doctorIdArray!=null && doctorIdArray.length()!=0){
                jsonObject.put("userName",""+name.getText().toString())
                        .put("mobileNo",""+mobile.getText().toString())
                        .put("email",""+email.getText().toString()).put("companyName","")
                        .put("companyAddress",""). put("userTypeId","13").put("userId",""+m.getUserId())
                        .put("gender",""+batch.getTag().toString()) .put("country", ""+Api_Calling.CountryHash.get(address.getText().toString()))
                        .put("address",""+address.getText().toString()).put("designation",""+batch.getText().toString());
//            }
//            else {
//                jsonObject.put("userName",""+name.getText().toString())
//                        .put("mobileNo",""+mobile.getText().toString())
//                        .put("email",""+email.getText().toString()).put("companyName",""+university_name.getText().toString())
//                        .put("companyAddress",""+university_address.getText().toString()). put("userTypeId","9").put("userId",""+m.getUserId())
//                        .put("interestId","") .put("occupation", qualfication.getText().toString())
//                        .put("address",""+address.getText().toString()).put("designation",""+batch.getText().toString());
//
//            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("SignIpJSon777777777777",""+jsonObject);
        return jsonObject;
    }

    private void  setGender(Segow_UI_EditText textView, String value)
    {
        Comman.log("GenderValue","----"+value);
        switch (value)
        {
            case "Male":
                textView.setTag("0");
                break;
            case "Female":
                textView.setTag("1");
                break;
            case "Other":
                textView.setTag("2");
                break;
        }

    }


    public  Drawable LoadImageFromWebOperations(String url) {
        try {
            Comman.log("Path",""+url);
            InputStream is = (InputStream) new URL(url).getContent();
            Drawable d = Drawable.createFromStream(is, "src name");
            return d;
        } catch (Exception e) {
            return null;
        }
    }

}
