package com.medparliament.Adapter;


import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.viewpager.widget.PagerAdapter;

import com.medparliament.Activity.Login_Signup_Activity;
import com.medparliament.Activity.NewsDetails_Activity;
import com.medparliament.Internet.Models.Dashboard_News_Model;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;

import java.util.ArrayList;

public class Dashboard_news_adapter_new  extends PagerAdapter {

    private Context context;
    ArrayList<Dashboard_News_Model> itemList;
    public Dashboard_news_adapter_new(Context context, ArrayList<Dashboard_News_Model> itemList,boolean flag) {
        this.context = context;
        this.itemList=itemList;
    }

    @Override
    public Object instantiateItem(ViewGroup collection, int position) {

        Log.d("aaa","aaaaa");
        final Dashboard_News_Model pm = itemList.get(position);

        LayoutInflater inflater = LayoutInflater.from(context);
        ViewGroup convertView = (ViewGroup) inflater.inflate(R.layout.dashboard_news_layout, collection, false);




        Comman.setImageWithCondition(context, (ImageView) convertView.findViewById(R.id.mainimg), pm.getImagePath());
        ((Segow_UI_Font) convertView.findViewById(R.id.title)).setText(pm.getNewsTitle());
        ((Open_Sans_Regular_Font) convertView.findViewById(R.id.msg)).setText(Html.fromHtml(pm.getNewsDesc()));
        (( Segow_UI_Font) convertView.findViewById(R.id.date)).setText(PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));


        convertView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Comman.Check_Login(context)){
                Intent intent = new Intent(context, NewsDetails_Activity.class);
                intent.putExtra("newses", pm);

                context.startActivity(intent);} else {

                    Intent intent = new Intent(context, Login_Signup_Activity.class);


                    context.startActivity(intent);
                }
            }
        });
        collection.addView(convertView);
        return convertView;
    }

    @Override
    public void destroyItem(ViewGroup collection, int position, Object view) {
        collection.removeView((View) view);
    }

    @Override
    public int getCount() {
        return itemList.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        final Dashboard_News_Model pm = itemList.get(position);
        return "";
    }


    public void updateList(ArrayList<Dashboard_News_Model> itemList){


        this.itemList=itemList;
        super.notifyDataSetChanged();
    }

}