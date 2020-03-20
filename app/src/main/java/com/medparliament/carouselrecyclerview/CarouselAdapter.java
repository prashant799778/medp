package com.medparliament.carouselrecyclerview;


import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.medparliament.Activity.Up_Skill_Details_Activity;
import com.medparliament.Internet.NewModel.up_skill_model;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.PrettyTimeClass;
import com.medparliament.Widget.Open_Sans_Regular_Font;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;


import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;

public class CarouselAdapter extends RecyclerView.Adapter<CarouselAdapter.CarouselViewHolder> {

    ArrayList<up_skill_model>arrayList = new ArrayList<>();
    Context context;

   public CarouselAdapter(Context context) {
       this.context=context;
        //mDrawable.addAll(drawableList);
    }
    public void addAll( ArrayList<up_skill_model>arrayList) {
       this.arrayList.clear();
        this.arrayList.addAll(arrayList);
        Log.d("new_size....",arrayList.size()+"");
        Log.d("new_size....",arrayList.get(0).getNewsDesc());
        notifyDataSetChanged();
    }
    @NonNull
    @Override
    public CarouselViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.skill_up_layout, parent, false);
        return new CarouselViewHolder(view);
    }
    @Override
    public void onBindViewHolder(@NonNull CarouselViewHolder holder, int position) {
       final up_skill_model r=arrayList.get(position);
       Comman.log("rrrr",""+r.getNewsDesc());
       if(r.getNewsDesc()!=null)
       holder.msg.setText(Html.fromHtml(r.getNewsDesc()));
       if(!r.getImagePath().equalsIgnoreCase(""))
       {
       Comman.setRectangleImage(context,holder.mainImge,r.getImagePath());
       holder.vodio.setVisibility(View.GONE);
       }else {
           holder.mainImge.setVisibility(View.GONE);
           holder.vodio.getPlayerUiController().showYouTubeButton(false);
           holder.vodio.addYouTubePlayerListener(new YouTubePlayerListener() {
               @Override
               public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                   youTubePlayer.cueVideo(r.getVideoId(),0);
               }

               @Override
               public void onStateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerState playerState) {

               }

               @Override
               public void onPlaybackQualityChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackQuality playbackQuality) {

               }

               @Override
               public void onPlaybackRateChange(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlaybackRate playbackRate) {

               }

               @Override
               public void onError(@NotNull YouTubePlayer youTubePlayer, @NotNull PlayerConstants.PlayerError playerError) {

               }

               @Override
               public void onCurrentSecond(@NotNull YouTubePlayer youTubePlayer, float v) {

               }

               @Override
               public void onVideoDuration(@NotNull YouTubePlayer youTubePlayer, float v) {

               }

               @Override
               public void onVideoLoadedFraction(@NotNull YouTubePlayer youTubePlayer, float v) {

               }

               @Override
               public void onVideoId(@NotNull YouTubePlayer youTubePlayer, @NotNull String s) {

               }

               @Override
               public void onApiChange(@NotNull YouTubePlayer youTubePlayer) {

               }
           });
       }
       if(r.getDateCreate()!=null)
       holder.time.setText(PrettyTimeClass.PrettyTime(Comman.timeInms(r.getDateCreate())));
       holder.heading.setText(r.getNewsTitle());
       holder.course.setText(r.getNewsTitle());
       holder.itemView.setOnClickListener(new View.OnClickListener() {
           @Override
           public void onClick(View v) {
               Intent intent=new Intent(context, Up_Skill_Details_Activity.class);
               intent.putExtra("data",r);
               intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
               context.startActivity(intent);
           }
       });
    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    static class CarouselViewHolder extends RecyclerView.ViewHolder {
        ImageView mainImge;
        Open_Sans_Regular_Font course;
        YouTubePlayerView vodio;
        Segow_UI_Font msg;
        Segow_UI_Bold_Font time,heading;

//        @BindView(R.id.tvPhotoTitle) TextView mPhotoTitleView;

        public CarouselViewHolder(View itemView) {
            super(itemView);
            mainImge=itemView.findViewById(R.id.mainimg);
            msg=itemView.findViewById(R.id.msg);
            vodio=itemView.findViewById(R.id.video);
            time=itemView.findViewById(R.id.time);
            course=itemView.findViewById(R.id.streme);
            heading=itemView.findViewById(R.id.heading);
           // ButterKnife.bind(this, itemView);
        }

        void bind(up_skill_model drawable) {
//
////            Glide.with(itemView)
////                    .load(drawable.getImagePath())
////                    .into(mainImge);
//
////            Glide.with(itemView)
////                    .load(drawable.getImagePath())
////                    .into(mainImge);
////            msg.setText(drawable.getNewsDesc());
//            Comman.log("GGGGG",""+drawable.getNewsDesc());

//            mPhotoTitleView.setText(photoItem.getNewsDesc());
        }
    }
}
