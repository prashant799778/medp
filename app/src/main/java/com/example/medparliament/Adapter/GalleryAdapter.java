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

import com.example.medparliament.Activity.Post_Details_Activity;
import com.example.medparliament.Internet.Models.DashboardGalleryModel;
import com.example.medparliament.Internet.Models.Post_Modle;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.PrettyTimeClass;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;

import java.util.ArrayList;
import java.util.Date;

public class GalleryAdapter extends RecyclerView.Adapter<GalleryAdapter.NotificationHolder> {
    ArrayList<DashboardGalleryModel>list;
    Context context;
    int a;
    String userName;
    public GalleryAdapter(Context context, ArrayList<DashboardGalleryModel>arrayList)
    {
        this.context=context;
        this.list=arrayList;
        this.a=a;
    }
    @NonNull
    @Override
    public GalleryAdapter.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.gallery_layout,parent,false);
        return new GalleryAdapter.NotificationHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        final DashboardGalleryModel pm=list.get(position);
        Comman.setRectangleImage(context,holder.imageView,pm.getImagePath());


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        ImageView imageView;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            imageView=itemView.findViewById(R.id.main_img);
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
