package  com.example.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.AppCompatButton;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.medparliament.R;

public class Login_Signup_Activity extends Base_Activity implements View.OnClickListener {
    AppCompatButton login,signup;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login__signup_);
        Animatoo.animateSwipeLeft(Login_Signup_Activity.this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        login=findViewById(R.id.login);
        signup=findViewById(R.id.signup);
        signup.setOnClickListener(this);
        login.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.login:
                startActivity(new Intent(Login_Signup_Activity.this,Login_Activity.class));
                break;
            case R.id.signup:
                startActivity(new Intent(Login_Signup_Activity.this,SignUp_type_Activity.class));
                break;
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Login_Signup_Activity.this);
    }
}
