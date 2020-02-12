package  com.example.medparliament.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.R;
import com.google.android.material.card.MaterialCardView;

public class SignUp_type_Activity extends Base_Activity implements View.OnClickListener {
    MaterialCardView student,enterpre,policyMaker,doctor,profes,decision;
    ImageButton bck;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up_type_);
        Animatoo.animateSwipeLeft(SignUp_type_Activity.this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
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
}
