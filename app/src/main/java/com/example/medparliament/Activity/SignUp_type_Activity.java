package  com.example.medparliament.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.R;
import com.example.medparliament.Widget.ObservableScrollView;
import com.example.medparliament.Widget.ScrollViewListener;
import com.google.android.material.card.MaterialCardView;

public class SignUp_type_Activity extends Base_Activity implements View.OnClickListener, ScrollViewListener {
    MaterialCardView student,enterpre,policyMaker,doctor,profes,decision;
    ImageButton bck;
    ObservableScrollView  scrollView;
     RelativeLayout relativeLayout;

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

         relativeLayout=findViewById(R.id.relative);

         bIcon=findViewById(R.id.bIcon);
        uIcon=findViewById(R.id.uIcon);

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
            uIcon.setVisibility(View.VISIBLE);
        }else if(y>3) {
           // Log.d("scroll....","need");
            bIcon.setVisibility(View.VISIBLE);
            uIcon.setVisibility(View.VISIBLE);

        }else{

            bIcon.setVisibility(View.VISIBLE);
            uIcon.setVisibility(View.GONE);
        }
    }
}
