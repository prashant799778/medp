package com.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;

import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;

public class News_Activity_1 extends AppCompatActivity {

    MySharedPrefrence m;
    Segow_UI_Semi_Font date, created, msg,loc,header;
    ImageButton bck;
    Segow_UI_Bold_Font title;
    ImageView img;
    Result result;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news__1);
        Animatoo.animateSwipeLeft(News_Activity_1.this);
        Intent i=getIntent();
        title = findViewById(R.id.title);
        date = findViewById(R.id.date);
        loc = findViewById(R.id.loc);
        msg = findViewById(R.id.msg);
        header = findViewById(R.id.header);
        created = findViewById(R.id.created);
        bck = findViewById(R.id.bck);
        img= findViewById(R.id.cover);
        if(i!=null)
        {
            result=(Result) i.getSerializableExtra("data");
            msg.setText(Html.fromHtml(result.getNewsDesc()));
            title.setText(Html.fromHtml(result.getNewsTitle()));
            Comman.setImageWithCondition(News_Activity_1.this,img,result.getImagePath());
            if(result.getDateCreate1()!=null)
                created.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(result.getDateCreate1())));

        }
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        m = MySharedPrefrence.instanceOf(News_Activity_1.this);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(News_Activity_1.this);
    }
}
