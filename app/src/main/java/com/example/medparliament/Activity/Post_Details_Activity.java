package  com.example.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.example.medparliament.Adapter.Post_Adapter;
import com.example.medparliament.Adapter.ThreadAdapter;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.Models.Post_Modle;
import com.example.medparliament.Internet.Models.Reply_Model;
import com.example.medparliament.Internet.URLS;
import com.example.medparliament.Internet.onResult;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Utility.PrettyTimeClass;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.ocpsoft.prettytime.PrettyTime;

import java.util.ArrayList;

public class Post_Details_Activity extends AppCompatActivity implements onResult {
  onResult onResult;
  String postId="";
  MySharedPrefrence m;
  Segow_UI_Semi_Font date,created,msg;
  ImageButton bck;
  Segow_UI_Bold_Font title;
    FloatingActionButton  cmnts;
  ProgressDialog progressDialog;
    MySharedPrefrence mySharedPrefrence;
    ArrayList<Reply_Model> arrayList;
    RecyclerView recyclerView;
    ThreadAdapter threadAdapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post__details_);
        m=MySharedPrefrence.instanceOf(getApplicationContext());
        Animatoo.animateSwipeLeft(Post_Details_Activity.this);
//        Window window = getWindow();
//        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
//        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        arrayList=new ArrayList<>();
        this.onResult=this;

        cmnts=findViewById(R.id.cmnt);
        cmnts.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent= new Intent(Post_Details_Activity.this,ReplyActivity.class);
                intent.putExtra("postId",postId);
                startActivity(intent);
            }
        });
        title=findViewById(R.id.title);
        date=findViewById(R.id.date);
        msg=findViewById(R.id.msg);
        created=findViewById(R.id.created);
        recyclerView=findViewById(R.id.recycle);
        bck=findViewById(R.id.bck);
        LinearLayoutManager    manager=new LinearLayoutManager(getApplicationContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setNestedScrollingEnabled(false);
        threadAdapter=new ThreadAdapter(getApplicationContext(),arrayList,m.getUserName(),1);
        recyclerView.setAdapter(threadAdapter);
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
                JSONObject jo=jsonObject.getJSONArray("result").getJSONArray(1).getJSONObject(0);
                m.setLoggedIn(true);
                title.setText(Comman.getValueFromJsonObject(jo,"postTitle"));
                created.setText("By :-"+Comman.getValueFromJsonObject(jo,"userName"));
                date.setText(""+ PrettyTimeClass.PrettyTime(Comman.timeInms(Comman.getValueFromJsonObject(jo,"DateCreate"))));
                msg.setText(""+Comman.getValueFromJsonObject(jo,"postDescription"));

                JSONArray jo1=jsonObject.getJSONArray("result").getJSONArray(0);

                Gson gson=new GsonBuilder().create();
                try {
                    ArrayList<Reply_Model> rm = gson.fromJson(jo1.toString(), new TypeToken<ArrayList<Reply_Model>>(){}.getType());
                    arrayList.clear();
                    arrayList.addAll(rm);
                    threadAdapter.notifyDataSetChanged();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }


    }
    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCall(Post_Details_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.ALL_POSTS_THREAD,setjson(),"PostDetails");
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
