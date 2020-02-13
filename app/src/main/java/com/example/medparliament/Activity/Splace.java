 package  com.example.medparliament.Activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.media.MediaPlayer;
import android.media.session.MediaController;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.VideoView;

import com.example.medparliament.R;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.MySharedPrefrence;
import com.example.medparliament.Widget.ScalableVideoView;
import com.example.medparliament.Widget.Segow_UI_Semi_Font;

 public class Splace extends Base_Activity {
      ScalableVideoView videoView;
     Segow_UI_Semi_Font skip;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Window window = getWindow();
        videoView=findViewById(R.id.scalableVideoView);
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
 }
