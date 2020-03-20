package com.medparliament.Adapter;

import android.content.Context;
import android.content.Intent;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.medparliament.Activity.Login_Signup_Activity;
import com.medparliament.Activity.News_Activity_1;
import com.medparliament.Internet.NewModel.ListDataModel;
import com.medparliament.Internet.NewModel.Result;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;

import java.util.ArrayList;
import java.util.Date;

public class CommentListAdapter extends RecyclerView.Adapter<CommentListAdapter.NotificationHolder> {
    ArrayList<ListDataModel>list;
    Context context;
    int a;
    String userName;
    public CommentListAdapter(Context context, ArrayList<ListDataModel>arrayList, String username, int a)
    {
        this.context=context;
        this.list=arrayList;
        this.userName=username;
        this.a=a;
    }
    @NonNull
    @Override
    public CommentListAdapter.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.commentlayout,parent,false);
        return new CommentListAdapter.NotificationHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull NotificationHolder holder, int position) {
        final ListDataModel result=list.get(position);
        holder.name.setText(result.getUserName());
        holder.date.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(result.getDateCreate())));
        holder.msg.setText(result.getCommentDescription());
    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public class NotificationHolder extends RecyclerView.ViewHolder {

        Segow_UI_Font name,date,msg;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            name=itemView.findViewById(R.id.name);
            msg=itemView.findViewById(R.id.msg);
            date=itemView.findViewById(R.id.time);
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
