

package com.medparliament.Adapter;


        import android.content.Context;
        import android.content.Intent;
        import android.util.Log;
        import android.view.LayoutInflater;
        import android.view.View;
        import android.view.ViewGroup;
        import android.widget.ImageView;

        import com.asksira.loopingviewpager.LoopingPagerAdapter;
        import com.medparliament.Activity.DashBoard_Activity;
        import com.medparliament.Activity.Login_Signup_Activity;
        import com.medparliament.Internet.Models.Video_Model;
        import com.medparliament.R;
        import com.medparliament.Utility.Comman;
        import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
        import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
        import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
        import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;

        import org.jetbrains.annotations.NotNull;

        import java.util.ArrayList;

public class Dashboard_video_adapter_new1 extends LoopingPagerAdapter<Video_Model> {

    public Dashboard_video_adapter_new1(Context context, ArrayList<Video_Model> itemList, boolean isInfinite) {
        super(context, itemList, isInfinite);
    }

    //This method will be triggered if the item View has not been inflated before.
    @Override
    protected View inflateView(int viewType, ViewGroup container, int listPosition) {
        return LayoutInflater.from(context).inflate(R.layout.video_item_layout, container, false);
    }

    //Bind your data with your item View here.
    //Below is just an example in the demo app.
    //You can assume convertView will not be null here.
    //You may also consider using a ViewHolder pattern.
    @Override
    protected void bindView(View convertView, final int listPosition, int viewType) {
        if(convertView!=null) {
            final Video_Model pm = itemList.get(listPosition);

            YouTubePlayerView youTubePlayerView= convertView.findViewById(R.id.video);

            ImageView imageView=convertView.findViewById(R.id.image);
             final View over_lay = convertView.findViewById(R.id.over_lay);
             over_lay.setVisibility(View.GONE);

            if(pm.getVideoId()!=null && pm.getVideoId()!=""){
                //Log.d("vie" ,pm.getVideoId());
                if(listPosition>=1 && !Comman.Check_Login(context)) {

                    over_lay.setVisibility(View.VISIBLE);
                    over_lay.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {

                            over_lay.setVisibility(View.VISIBLE);
                            context.startActivity(new Intent(context, Login_Signup_Activity.class));
                            ((DashBoard_Activity)context).finish();

                        }
                    });
                }
                youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
                    @Override
                    public void onReady(@NotNull YouTubePlayer youTubePlayer) {
                        youTubePlayer.cueVideo(pm.getVideoId(),0);
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
            else{

                  imageView.setVisibility(View.VISIBLE);

                Comman.setRectangleImage(context,imageView,pm.getImagePath());

            }



        }
    }
}
