
package com.medparliament.Adapter;


import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.asksira.loopingviewpager.LoopingPagerAdapter;
import com.medparliament.Activity.NewsDetails_Activity;
import com.medparliament.Internet.Models.Dashbooard_eventModel;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Font;

import java.util.ArrayList;

public class New_Event_Adapter extends LoopingPagerAdapter<Dashbooard_eventModel> {

    public New_Event_Adapter(Context context, ArrayList<Dashbooard_eventModel> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.new_event_layout, container, false);
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    @Override
    protected void bindView(View convertView, int listPosition, int viewType) {
        if(convertView!=null) {
            ImageView mainimg;
            Segow_UI_Bold_Font title;
            Open_Sans_Regular_Font place;

            title=convertView.findViewById(R.id.title);
            place=convertView.findViewById(R.id.location);
            mainimg=convertView.findViewById(R.id.img);

            final Dashbooard_eventModel pm=itemList.get(listPosition);
            Comman.setRectangleImage(context,mainimg,pm.getImagePath());
            title.setText(pm.getEventTitle());
           place.setText(pm.getEventLocation());
            convertView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(context, NewsDetails_Activity.class);
                    intent.putExtra("event",pm);
                    context.startActivity(intent);
                }
            });



        }
    }
}
