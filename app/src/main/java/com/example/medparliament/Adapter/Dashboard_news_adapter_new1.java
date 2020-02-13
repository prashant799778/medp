package com.example.medparliament.Adapter;


import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.example.medparliament.Activity.NewsDetails_Activity;
import com.example.medparliament.Internet.Models.Dashboard_News_Model;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.PrettyTimeClass;
import com.example.medparliament.Widget.Open_Sans_Regular_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;

import java.util.ArrayList;

public class Dashboard_news_adapter_new1 extends LoopingPagerAdapter<Dashboard_News_Model> {

    public Dashboard_news_adapter_new1(Context context, ArrayList<Dashboard_News_Model> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.dashboard_news_layout, container, false);
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
if(convertView!=null) {
    final Dashboard_News_Model pm = itemList.get(listPosition);
    Comman.setImageWithCondition(context, (ImageView) convertView.findViewById(R.id.mainimg), pm.getImagePath());
    ((Segow_UI_Semi_Font) convertView.findViewById(R.id.title)).setText(pm.getNewsTitle());
    ((Open_Sans_Regular_Font) convertView.findViewById(R.id.msg)).setText(Html.fromHtml(pm.getNewsDesc()));
    ((Segow_UI_Semi_Font) convertView.findViewById(R.id.date)).setText(PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));


    convertView.setOnClickListener(new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            Intent intent = new Intent(context, NewsDetails_Activity.class);
            intent.putExtra("newses", pm);

            context.startActivity(intent);
        }
    });
}
    }
}