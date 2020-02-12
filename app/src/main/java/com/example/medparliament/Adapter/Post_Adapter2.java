package com.example.medparliament.Adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.icu.text.SimpleDateFormat;
import android.os.Build;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.recyclerview.widget.RecyclerView;

import com.example.medparliament.Activity.All_Post_Details;
import com.example.medparliament.Activity.Post_Details_Activity;
import com.example.medparliament.Internet.Api_Calling;
import com.example.medparliament.Internet.Models.Post_Modle;
import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Utility.PrettyTimeClass;
import com.example.medparliament.Widget.Segow_UI_Bold_Font;
import com.example.medparliament.Widget.Segow_UI_Font;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;

public class Post_Adapter2  extends RecyclerView.Adapter<Post_Adapter2.NotificationHolder>  {
    ArrayList<Post_Modle> list;
    Context context;
    int a;
    MySharedPrefrence mySharedPrefrence;
    String userName;
    public Post_Adapter2(Context context, ArrayList<Post_Modle>arrayList,String username,int a)
    {
        this.context=context;
        this.list=arrayList;
        this.userName=username;
        this.a=a;
        mySharedPrefrence=MySharedPrefrence.instanceOf(context);
    }
    @NonNull
    @Override
    public Post_Adapter2.NotificationHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater=LayoutInflater.from(context);
        View view=layoutInflater.inflate(R.layout.all_post,parent,false);
        return new Post_Adapter2.NotificationHolder(view);
    }

    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public void onBindViewHolder(@NonNull final Post_Adapter2.NotificationHolder holder, int position) {
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
                Intent i=new Intent(context, All_Post_Details.class);
                i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                i.putExtra("postn",pm);
                context.startActivity(i);
            }
        });
        holder.likleCount.setText("Endorse "+pm.getLike()+"  (Students)");
//        setRed(holder.likleCount,pm.getLike().toString()," Indorse");
        if(pm.getLikeStatus()!=null) {
            if (Integer.parseInt(pm.getLikeStatus())==1){
                holder.like.setEnabled(false);
            holder.like.setImageResource(R.drawable.ic_after_like);
            }
        }
        Comman.log("DDD","ddddd"+pm.getLikeStatus());
        holder.like.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Api_Calling.likePost(context,likeJson(pm.getPostId()),holder.like,holder.likleCount,pm.getLike());
            }
        });
        holder.setIsRecyclable(false);


    }
    @Override
    public int getItemCount() {
        return list.size();
    }

    public class NotificationHolder extends RecyclerView.ViewHolder {
        Segow_UI_Font creaedby;
        Segow_UI_Bold_Font userName;
        Segow_UI_Semi_Font date,msg;
        Segow_UI_Font likleCount;
        LinearLayout layout;
        ImageView like;
        public NotificationHolder(@NonNull View itemView) {
            super(itemView);
            userName=itemView.findViewById(R.id.name);
            date=itemView.findViewById(R.id.date);
            msg=itemView.findViewById(R.id.msg);
            creaedby=itemView.findViewById(R.id.createdby);
            like=itemView.findViewById(R.id.like);
            likleCount=itemView.findViewById(R.id.likecount);
            layout=itemView.findViewById(R.id.layout);

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
    public JSONObject likeJson(String postId)
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userId",""+mySharedPrefrence.getUserId()).put("userTypeId",""+mySharedPrefrence.getUserTypeId()).put("postId",""+postId);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("LikeJson",""+jsonObject);
        return jsonObject;
    }
    public static  void setRed(TextView text,String counter, String title)
    {

        String colored = counter;
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(title);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setHint(builder);
    }
}
