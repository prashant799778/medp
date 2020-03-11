package com.medparliament.Activity;



import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.appcompat.app.AppCompatActivity;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.Models.Dashboard_News_Model;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.Constant;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_EditText;

import org.json.JSONException;
import org.json.JSONObject;

public class Market_Insight_Detail_Activity extends AppCompatActivity implements onResult {
    Segow_UI_Bold_Font title;
    Open_Sans_Regular_Font date,msg;
    ImageView pic_img,like;
    MySharedPrefrence m;
    Segow_UI_EditText editTextcmnt;
    Segow_UI_Bold_Font post;
    LinearLayout cmnt_Layout;
    ImageButton bck;
    onResult onResult;
    Segow_UI_Bold_Font counter;
    Result result;
    Dashboard_News_Model model;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_market__insight__detail_);
        Animatoo.animateSwipeLeft(Market_Insight_Detail_Activity.this);
        title=findViewById(R.id.title);
        date=findViewById(R.id.date);
        like=findViewById(R.id.like);
        cmnt_Layout=findViewById(R.id.l);
        msg=findViewById(R.id.msg);
        counter=findViewById(R.id.counter);
        pic_img=findViewById(R.id.pic_img);
        bck=findViewById(R.id.bck);
        post=findViewById(R.id.post);
        this.onResult=this;
        editTextcmnt=findViewById(R.id.add_view);
        m=MySharedPrefrence.instanceOf(Market_Insight_Detail_Activity.this);
        Intent intent=getIntent();
        if(intent!=null)
        {
         result=(Result) intent.getSerializableExtra("date");
         if(result!=null) {
             if (result.getImagePath() != null)
                 Comman.setImageWithCondition(Market_Insight_Detail_Activity.this, pic_img, result.getImagePath());
             title.setText(result.getNewsTitle());
             msg.setText(Html.fromHtml(result.getNewsDesc()));
             if (result.getDateCreate1() != null)
                 date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(result.getDateCreate1())));
                 counter.setText(result.getLikeCount()+"  Endorse");
         }
        }
        if(m.getUserTypeId().equalsIgnoreCase("7"))
        {
            cmnt_Layout.setVisibility(View.VISIBLE);
        }else {
            cmnt_Layout.setVisibility(View.GONE);
        }
        if(result.getMakedone().equalsIgnoreCase("1")) {
            like.setEnabled(false);
            like.setImageResource(R.drawable.ic_like_after);
        }
        like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            Api_Calling.likePostMarket(Market_Insight_Detail_Activity.this,likejson(),like,counter,result.getLikeCount().toString());
            }
        });
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        post.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Comman.log("DDDDEd","dddssdd");
                if(!editTextcmnt.getText().toString().isEmpty())
                {
                    Api_Calling.postMethodCall(Market_Insight_Detail_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.commentsMarketingInsight,conmtJson(),"");

                }else { Comman.topSnakBar(Market_Insight_Detail_Activity.this,v, Constant.MSG);

                }
            }
        });
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(jsonObject!=null && status)
        {editTextcmnt.setText("");
            onBackPressed();
        }

    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Market_Insight_Detail_Activity.this);
    }


    public JSONObject conmtJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("marketingInsightId",""+result.getId()).put("commentDescription",""+editTextcmnt.getText().toString());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("CommentJson",""+jsonObject);
        return jsonObject;
    }
    public JSONObject likejson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+m.getUserId()).put("userTypeId",""+m.getUserTypeId()).put("marketingInsightId",""+result.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("likejson",""+jsonObject);
        return jsonObject;
    }
}
