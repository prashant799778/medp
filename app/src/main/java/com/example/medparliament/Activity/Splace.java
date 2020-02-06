 package  com.example.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;
import android.view.WindowManager;

import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.MySharedPrefrence;

 public class Splace extends Base_Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                if(Comman.Check_Login(Splace.this)){
                startActivity(new Intent(Splace.this,DashBoard_Activity.class));}else {
                    startActivity(new Intent(Splace.this,Login_Signup_Activity.class));
                }
            }
        },3000);
    }
 }
