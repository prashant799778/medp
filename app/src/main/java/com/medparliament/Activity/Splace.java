 package  com.medparliament.Activity;

import android.content.Intent;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.medparliament.R;
import com.medparliament.Temp.CameraActivity;
import com.medparliament.Temp.FaceOverlayView;
import com.medparliament.Utility.Comman;

import com.medparliament.Widget.Segow_UI_Semi_Font;

 public class Splace extends Base_Activity {
     VideoView videoView;
     Segow_UI_Semi_Font skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


           startActivity(new Intent(Splace.this, FaceDetectionActivity.class));
        Window window = getWindow();
        videoView=findViewById(R.id.scalableVideoView);
        setDimension();
        skip=findViewById(R.id.skip);
        window.addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        playVideo();
        skip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(Comman.Check_Login(Splace.this)){
                    startActivity(new Intent(Splace.this,DashBoard_Activity.class));
                    finish();

                }else {
                    startActivity(new Intent(Splace.this,DashBoard_Activity.class));
                    finish();
                }
            }
        });
    }
     public void playVideo() {
         String path = "android.resource://" + getPackageName() + "/" + R.raw.video;
         Uri u = Uri.parse(path);
         videoView.setVideoURI(u);
         videoView.start();

         videoView.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
             @Override
             public void onCompletion(MediaPlayer mp) {
                 startActivity(new Intent(Splace.this,DashBoard_Activity.class));
                 finish();
             }
         });

     }

     private void setDimension() {
         // Adjust the size of the video
         // so it fits on the screen
         float videoProportion = getVideoProportion();
         int screenWidth = getResources().getDisplayMetrics().widthPixels;
         int screenHeight = getResources().getDisplayMetrics().heightPixels;
         float screenProportion = (float) screenHeight / (float) screenWidth;
         android.view.ViewGroup.LayoutParams lp = videoView.getLayoutParams();

         if (videoProportion < screenProportion) {
             lp.height= screenHeight;
             lp.width = (int) ((float) screenHeight / videoProportion);
         } else {
             lp.width = screenWidth;
             lp.height = (int) ((float) screenWidth * videoProportion);
         }
         videoView.setLayoutParams(lp);
     }

     // This method gets the proportion of the video that you want to display.
// I already know this ratio since my video is hardcoded, you can get the
// height and width of your video and appropriately generate  the proportion
//    as :height/width
     private float getVideoProportion(){
         return 1.5f;
     }
 }
