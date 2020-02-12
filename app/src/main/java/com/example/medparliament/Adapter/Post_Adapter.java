package com.example.medparliament.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medparliament.Internet.Models.Post_Modle;
import com.example.medparliament.Activity.Post_Details_Activity;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.PrettyTimeClass;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;
import java.util.ArrayList;
import java.util.Date;

public class Post_Adapter extends RecyclerView.Adapter<Post_Adapter.NotificationHolder> {
    ArrayList<Post_Modle>list;
    Context context;
    int a;
    String userName;
    public Post_Adapter(Context context, ArrayList<Post_Modle>arrayList,String username,int a)
    {
        this.context=context;
        this.list=arrayList;
        this.userName=username;
        this.a=a;
    }
    @NonNull
    @Override
    public Post_Adapter.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.post_layout,parent,false);
        return new Post_Adapter.NotificationHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        final Post_Modle pm=list.get(position);
        if(a==1){
        holder.userName.setText(pm.getPostTitle());
        holder.creaedby.setText("By :"+userName);
        holder.msg.setText(""+pm.getPostDescription());
        Comman.log("My",""+pm.getPostDescription());
            if(pm.getDateCreate()!=null)
        holder.date.setText(""+ PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));}else {
            holder.userName.setText(pm.getPostTitle());
            holder.creaedby.setText("By :"+pm.getUserName());
            holder.msg.setText(pm.getPostDescription());
            if(pm.getDateCreate()!=null)
            holder.date.setText(""+ PrettyTimeClass.PrettyTime(Comman.timeInms(pm.getDateCreate())));}
        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent i=new Intent(context, Post_Details_Activity.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("postId",""+pm.getPostId());

                context.startActivity(i);
            }
        });

    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        Segow_UI_Font creaedby;
        Segow_UI_Bold_Font userName;
        Segow_UI_Semi_Font date,msg;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.name);
            date=itemView.findViewById(R.id.date);
            msg=itemView.findViewById(R.id.msg);
            creaedby=itemView.findViewById(R.id.createdby);

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
