package com.medparliament.Adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.medparliament.Activity.NewsDetails_Activity;
import com.medparliament.Internet.Models.Dashbooard_eventModel;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Font;

import java.util.ArrayList;
import java.util.Date;

public class Event_Adapter extends RecyclerView.Adapter<Event_Adapter.NotificationHolder> {
    ArrayList<Dashbooard_eventModel>list;
    Context context;
    int a;
    String userName;
    public Event_Adapter(Context context, ArrayList<Dashbooard_eventModel>arrayList)
    {
        this.context=context;
        this.list=arrayList;
        this.a=a;
    }
    @NonNull
    @Override
    public Event_Adapter.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.dashboard_event_layout,parent,false);
        return new Event_Adapter.NotificationHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        final Dashbooard_eventModel pm=list.get(position);
        Comman.setRectangleImage(context,holder.mainimg,pm.getImagePath());
        holder.msg.setText(pm.getEventSummary());
        holder.title.setText(pm.getEventTitle());
        holder.place.setText(pm.getEventLocation());
        holder.date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, NewsDetails_Activity.class);
                intent.putExtra("event",pm);
                context.startActivity(intent);
            }
        });
    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        ImageView mainimg;
        Segow_UI_Font date,title,place;
        Open_Sans_Regular_Font msg;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            title=itemView.findViewById(R.id.title);
            date=itemView.findViewById(R.id.date);
            msg=itemView.findViewById(R.id.msg);
            place=itemView.findViewById(R.id.place);
            mainimg=itemView.findViewById(R.id.mainimg);


        }
    }
    @RequiresApi(api = Build.VERSION_CODES.N)
    public String setDate(String date)
    {
        SimpleDateFormat input = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
        SimpleDateFormat output = new SimpleDateFormat("dd/MM/yyyy");

        Date d = new Date();
        try {
            d = input.parse(date);
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }
        String formatted="";
        if(output.format(d)!=null)
            formatted= output.format(d);
        return formatted;
    }
}
