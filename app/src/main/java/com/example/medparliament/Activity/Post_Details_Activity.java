package  com.example.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Utility.PrettyTimeClass;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;

import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

public class Post_Details_Activity extends AppCompatActivity implements onResult {
  onResult onResult;
  String postId="";
  MySharedPrefrence m;
  Segow_UI_Semi_Font date,created,msg;
  ImageButton bck;
  Segow_UI_Bold_Font title;
  ProgressDialog progressDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__details_);
        Animatoo.animateSwipeLeft(Post_Details_Activity.this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        this.onResult=this;
        title=findViewById(R.id.title);
        date=findViewById(R.id.date);
        msg=findViewById(R.id.msg);
        created=findViewById(R.id.created);
        bck=findViewById(R.id.bck);
        progressDialog = new ProgressDialog(Post_Details_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        m=MySharedPrefrence.instanceOf(Post_Details_Activity.this);
        Intent i=getIntent();
        if(i!=null)
            postId=i.getStringExtra("postId");
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
         progressDialog.dismiss();
        if(jsonObject!=null && status){
            try {
                JSONObject jo=jsonObject.getJSONArray("result").getJSONObject(0);
                m.setLoggedIn(true);
                title.setText(Comman.getValueFromJsonObject(jo,"postTitle"));
                created.setText("By :-"+Comman.getValueFromJsonObject(jo,"userName"));
                date.setText(""+ PrettyTimeClass.PrettyTime(Comman.timeInms(Comman.getValueFromJsonObject(jo,"DateCreate"))));
                msg.setText(""+Comman.getValueFromJsonObject(jo,"postDescription"));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCall(Post_Details_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.ALL_POSTS,setjson(),"PostDetails");
    }
    public JSONObject setjson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("postId",""+postId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("postDetailsJson",""+jsonObject);
        return jsonObject;
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Post_Details_Activity.this);
    }
}
