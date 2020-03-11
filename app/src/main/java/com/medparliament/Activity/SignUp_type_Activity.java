package  com.medparliament.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.Dashbooard_eventModel;
import com.medparliament.Internet.NewModel.SignUpTypeModel;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Widget.ObservableScrollView;
import com.medparliament.Widget.ScrollViewListener;
import com.google.android.material.card.MaterialCardView;
import com.medparliament.Widget.Segow_UI_Font;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class SignUp_type_Activity extends Base_Activity implements View.OnClickListener, ScrollViewListener, onResult {
    MaterialCardView student,enterpre,policyMaker,doctor,profes,decision;
    ImageButton bck;
    Segow_UI_Font studentText,EntreText,PolicyText,DoctorText,ProfessionText,DecisionMakerText;
    ImageView studentImage,EnterImage,PolicyImage,DoctorImage,PRofessionImage,DecisionMakerImage;
    ObservableScrollView  scrollView;
     RelativeLayout relativeLayout;
      onResult onResult;
     ImageView bIcon,uIcon;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_type_);
        Animatoo.animateSwipeLeft(SignUp_type_Activity.this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);

        scrollView=findViewById(R.id.scroll1);
        this.onResult=this;

         relativeLayout=findViewById(R.id.relative);

         bIcon=findViewById(R.id.bIcon);
         studentText=findViewById(R.id.studentText);
         EntreText=findViewById(R.id.EText);
         PolicyText=findViewById(R.id.PText);
         DoctorText=findViewById(R.id.DText);
         ProfessionText=findViewById(R.id.ProText);
         DecisionMakerText=findViewById(R.id.DMText);
         studentImage=findViewById(R.id.studentImage);
         EnterImage=findViewById(R.id.EImage);
        PolicyImage=findViewById(R.id.PImage);
        DoctorImage=findViewById(R.id.DImage);
        PRofessionImage=findViewById(R.id.Pro_Image);
        DecisionMakerImage=findViewById(R.id.DImage);

//        uIcon=findViewById(R.id.uIcon);

    scrollView.setScrollViewListener(this);

        student=findViewById(R.id.student);
        enterpre=findViewById(R.id.enterPr);
        policyMaker=findViewById(R.id.policyMaker);
        doctor=findViewById(R.id.doctor);
        profes=findViewById(R.id.profess);
        doctor.setOnClickListener(this);
        profes.setOnClickListener(this);
        student.setOnClickListener(this);
        enterpre.setOnClickListener(this);
        policyMaker.setOnClickListener(this);
        decision=findViewById(R.id.decision);
        decision.setOnClickListener(this);
        bck=findViewById(R.id.bck);
        bck.setOnClickListener(this);
        Api_Calling.postMethodCall_NO_MSG(SignUp_type_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.getuserContent,json(),"SignUpType");

//        Api_Calling.getQualificationListData(SignUp_type_Activity.this,getWindow().getDecorView().getRootView(), URLS.ALL_QUALIFICATION);
//        Api_Calling.getUniversityListData(SignUp_type_Activity.this,getWindow().getDecorView().getRootView(), URLS.ALL_UNIVERSITY);


//        Log.d("scroll....",relativeLayout.getBottom()+"-"+relativeLayout.getMeasuredHeight()+"-"+relativeLayout.getBottom()+"-"+relativeLayout.getChildAt(0).getVisibility());
//        Log.d("scroll....",scrollView.getBottom()+"-"+scrollView.getMeasuredHeight()+"-"+scrollView.getBottom()+"-"+scrollView.getChildAt(scrollView.getChildCount()-1).getVisibility());
    }

    @Override
    public void onWindowFocusChanged (boolean hasFocus) {
        // the height will be set at this point

          if((scrollView.getMeasuredHeight()-relativeLayout.getMeasuredHeight())>0){

          }else {

              bIcon.setVisibility(View.VISIBLE);

          }

    }
    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.student:
                Intent i=new Intent(SignUp_type_Activity.this, Student_SignUp_Activity.class);
                i.putExtra("userType","7");
                startActivity(i);
                break;
            case R.id.enterPr:
                Intent i2=new Intent(SignUp_type_Activity.this, Entrepreneur_SignUp_Activity.class);
                i2.putExtra("userType","6");
                startActivity(i2);
                break;
            case R.id.policyMaker:
                Intent i3=new Intent(SignUp_type_Activity.this, Policy_Maker_SignUp_Activity.class);
                i3.putExtra("userType","5");
                startActivity(i3);
                break;
            case R.id.bck:
                onBackPressed();
                break;
            case R.id.doctor:
                Intent ir3=new Intent(SignUp_type_Activity.this, DoctorSignupActivity.class);
                ir3.putExtra("userType","8");
                startActivity(ir3);
                break;
            case R.id.profess:
                Intent irr3=new Intent(SignUp_type_Activity.this, Professonal_signup_Activity.class);
                irr3.putExtra("userType","9");
                startActivity(irr3);
                break;
            case R.id.decision:
                Intent irre3=new Intent(SignUp_type_Activity.this, DecisionMaker.class);
                irre3.putExtra("userType","13");
                startActivity(irre3);
                break;
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(SignUp_type_Activity.this);
    }

    @Override
    public void onScrollChanged(ObservableScrollView scrollView, int x, int y, int oldx, int oldy) {
        //Log.d("scroll....",y+"");
        if((relativeLayout.getMeasuredHeight()-scrollView.getMeasuredHeight())==y){

            bIcon.setVisibility(View.GONE);
//            uIcon.setVisibility(View.VISIBLE);
        }else if(y>3) {
           // Log.d("scroll....","need");
            bIcon.setVisibility(View.VISIBLE);
//            uIcon.setVisibility(View.VISIBLE);

        }else{

            bIcon.setVisibility(View.VISIBLE);
//            uIcon.setVisibility(View.GONE);
        }
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {

        if(jsonObject!=null && status)
        {
            Gson gson = new GsonBuilder().create();
            try {
                ArrayList<SignUpTypeModel> dash_event_list = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<SignUpTypeModel>>() {}.getType());
                if(dash_event_list.size()>0 && dash_event_list!=null) {
                    for (int i = 0; i < dash_event_list.size(); i++) {
                        if (dash_event_list.get(i).getUserTypeId().equalsIgnoreCase("7")) {
                            studentText.setText(dash_event_list.get(i).getContent());
                            Comman.setRectangleImage(SignUp_type_Activity.this, studentImage, dash_event_list.get(i).getImagePath());
                        }
                        if (dash_event_list.get(i).getUserTypeId().equalsIgnoreCase("6")) {
                            EntreText.setText(dash_event_list.get(i).getContent());
                            Comman.setRectangleImage(SignUp_type_Activity.this, EnterImage, dash_event_list.get(i).getImagePath());
                        }
                        if (dash_event_list.get(i).getUserTypeId().equalsIgnoreCase("5")) {

                            PolicyText.setText(dash_event_list.get(i).getContent());
                            Comman.setRectangleImage(SignUp_type_Activity.this, PolicyImage, dash_event_list.get(i).getImagePath());
                        }
                        if (dash_event_list.get(i).getUserTypeId().equalsIgnoreCase("8")) {
                            DoctorText.setText(dash_event_list.get(i).getContent());
                            Comman.setRectangleImage(SignUp_type_Activity.this, DoctorImage, dash_event_list.get(i).getImagePath());

                        }
                        if (dash_event_list.get(i).getUserTypeId().equalsIgnoreCase("9")) {
                            ProfessionText.setText(dash_event_list.get(i).getContent());
                            Comman.setRectangleImage(SignUp_type_Activity.this, PRofessionImage, dash_event_list.get(i).getImagePath());

                        }
                        if (dash_event_list.get(i).getUserTypeId().equalsIgnoreCase("13")) {
                            DecisionMakerText.setText(dash_event_list.get(i).getContent());
                            Comman.setRectangleImage(SignUp_type_Activity.this, DecisionMakerImage, dash_event_list.get(i).getImagePath());

                        }
                    }
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            Api_Calling.postMethodCall_NO_MSG(SignUp_type_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.getuserContent,json(),"SignUpType");
        }


    }
    public JSONObject json()
    {
        JSONObject r=new JSONObject();
        return r;
    }
}
