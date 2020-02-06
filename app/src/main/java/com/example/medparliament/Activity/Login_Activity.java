package  com.example.medparliament.Activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.RelativeLayout;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.Constant;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_EditText;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.Task;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Login_Activity extends Base_Activity implements View.OnClickListener, onResult {
    Button login;
    Segow_UI_EditText email,pswd;
    RelativeLayout top_view;
    onResult onResult;
    ImageButton bck;
    RelativeLayout googleLogin;
    GoogleSignInClient googleSignInClient;
    private ProgressDialog progressDialog;
    MySharedPrefrence m;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login_);
        Animatoo.animateSwipeLeft(Login_Activity.this);
        this.onResult=this;
        bck=findViewById(R.id.bck);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        m=MySharedPrefrence.instanceOf(Login_Activity.this);
        login=findViewById(R.id.login);
        login.setOnClickListener(this);
        email=findViewById(R.id.email);
        pswd=findViewById(R.id.pswd);
        googleLogin=findViewById(R.id.googleLogin);
        googleLogin.setOnClickListener(this);
        top_view=findViewById(R.id.top_view);
        bck.setOnClickListener(this);
        //google Login Listener
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
        Comman.ChangeFocus(email);
        Comman.ChangeFocus(pswd);
    }
    @Override
    public void onClick(View v) {
        if(v.getId()==R.id.login) {
            if (!email.getText().toString().isEmpty() && !pswd.getText().toString().isEmpty()) {
                progressDialog = new ProgressDialog(Login_Activity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                Api_Calling.getMethodCall(Login_Activity.this, URLS.LOGIN + "?email=" + email.getText().toString() + "&&password=" + pswd.getText().toString(), v, onResult, "Login");
            }else {
                Comman.topSnakBar(Login_Activity.this,v, Constant.PLEASE_FILL_ALL_FIELD);
            }
        }
        else {
            onBackPressed();
        }
    }
    @Override
    public void onResult(JSONObject jsonObject,Boolean status) {
        progressDialog.dismiss();
        if(jsonObject!=null && status){
            try {
                JSONObject jo=jsonObject.getJSONArray("result").getJSONObject(0);
                if(Comman.getValueFromJsonObject(jo,"userTypeId").equalsIgnoreCase("5") || Comman.getValueFromJsonObject(jo,"userTypeId").equalsIgnoreCase("6") || Comman.getValueFromJsonObject(jo,"userTypeId").equalsIgnoreCase("7")) {
                    m.setLoggedIn(true);
                    m.setUserName(Comman.getValueFromJsonObject(jo,"userName"));
                    m.setUserId(Comman.getValueFromJsonObject(jo,"userId"));
                    m.setUserTypeId(Comman.getValueFromJsonObject(jo,"userTypeId"));
                    m.setUserProfile(Comman.getValueFromJsonObject(jo,"profilePic"));
                    Intent i = new Intent(Login_Activity.this, DashBoard_Activity.class);
                    i.putExtra("username", "" + Comman.getValueFromJsonObject(jo, "userName"));
                    startActivity(i);
                }else {
                    showDialogeBox(Login_Activity.this,"Information","You are not authorised for login on this application");
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Login_Activity.this);
//        if(Comman.Check_Login(Login_Activity.this))
//        finishAffinity();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 101:
                try {
                    // The Task returned from this call is always completed, no need to attach
                    // a listener.
                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
                    GoogleSignInAccount account = task.getResult(ApiException.class);
//                    Utility.log("Google_Repsonse",""+task.getResult().toString());
                    onLoggedIn(account);
                } catch (ApiException e) {
                    // The ApiException status code indicates the detailed failure reason.
                    //Utility.toastShort(this, "signInResult:failed code=" + e.getStatusCode());
                    Comman.log("google", "signInResult:failed code=" + e.getStatusCode());
                }
                break;
        }


    }
    private void onLoggedIn(GoogleSignInAccount googleSignInAccount) {
        Comman.log("Goolge_Response","Display_Name...."+googleSignInAccount.getDisplayName()+"...Email...."+googleSignInAccount.getEmail()+"....3rd...."+googleSignInAccount.getId()+"...Image....."+googleSignInAccount.getPhotoUrl()+"....id....."+googleSignInAccount.getId());
    }


    public void showDialogeBox(Context context, String title, String msg)
    {
        final MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context,R.style.custom_dialog);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_layout, null, false);
        Button yes=dialogView.findViewById(R.id.yes);
        Button no=dialogView.findViewById(R.id.no);
        no.setVisibility(View.GONE);
        Segow_UI_Bold_Font title1=dialogView.findViewById(R.id.title);
        Segow_UI_Semi_Font msg1=dialogView.findViewById(R.id.msg);
        title1.setText(title);
        msg1.setText(msg);
//        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
//        lp.dimAmount=0.7f;
//        alertDialog.getWindow().setAttributes(lp);
//        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        alertDialog.setView(dialogView);
        alertDialog.show();
        yes.setText("Ok");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
                m.clearData();
                 alertDialog.dismiss();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });
    }
}
