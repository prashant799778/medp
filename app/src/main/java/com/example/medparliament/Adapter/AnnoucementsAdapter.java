package com.example.medparliament.Adapter;

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

import com.example.medparliament.Activity.NewsDetails_Activity;
import com.example.medparliament.Activity.Post_Details_Activity;
import com.example.medparliament.Internet.Models.DashboardAnnouncedModel;
import com.example.medparliament.Internet.Models.Post_Modle;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.PrettyTimeClass;
import com.example.medparliament.Widget.Open_Sans_Regular_Font;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;

import java.util.ArrayList;
import java.util.Date;

public class AnnoucementsAdapter extends RecyclerView.Adapter<AnnoucementsAdapter.NotificationHolder> {
    ArrayList<DashboardAnnouncedModel>list;
    Context context;
    int a;
    String userName;
    public AnnoucementsAdapter(Context context, ArrayList<DashboardAnnouncedModel>arrayList)
    {
        this.context=context;
        this.list=arrayList;
        this.a=a;
    }
    @NonNull
    @Override
    public AnnoucementsAdapter.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.announced_layout,parent,false);
        return new AnnoucementsAdapter.NotificationHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        final DashboardAnnouncedModel pm=list.get(position);
         holder.msg.setText(pm.getSummary());
         holder.title.setText(pm.getTitle());
         Comman.log("ImagePath",""+pm.getImagePath());
         Comman.setRectangleImage(context,holder.mainimg,pm.getImagePath());

//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(context, NewsDetails_Activity.class);
//                intent.putExtra("ann",pm);
//                context.startActivity(intent);
//            }
//        });
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
