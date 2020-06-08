package com.medparliament.Service;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.AudioAttributes;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.app.NotificationCompat;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bumptech.glide.Glide;
import com.google.firebase.iid.FirebaseInstanceId;
import com.google.firebase.iid.InstanceIdResult;
import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.medparliament.Activity.DashBoard_Activity;
import com.medparliament.Activity.Login_Activity;
import com.medparliament.Activity.Tabs_DashBoard_Activity;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.URLS;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.concurrent.ExecutionException;

import static com.medparliament.Utility.Comman.REQUEST_ACCEPT;

public class FcmService extends FirebaseMessagingService {

    MySharedPrefrence m;
    @Override
    public void onCreate() {
        super.onCreate();
        m=MySharedPrefrence.instanceOf(FcmService.this);
        Log.d("notificationnnnnn", "on create"+" ");
    }

    @Override
    public void onNewToken(@NonNull String s) {
        super.onNewToken(s);
        m=MySharedPrefrence.instanceOf(FcmService.this);
        m.setFCMToken(s);
        Comman.log("GGG...............in ", m.getFCMToken());
        JSONObject jsonObject=new JSONObject();
         if(m.getUserTypeId()!=""){


             try {
                 jsonObject.put("userId",m.getUserId());
                 jsonObject.put("MobileToken",s);
             } catch (JSONException e) {
                 e.printStackTrace();
             }


         }

        Api_Calling.postMethodCall_NO_Result(getApplicationContext(), URLS.updateToken,jsonObject);

    }

    @Override
    public void onMessageReceived(@NonNull RemoteMessage remoteMessage) {


         if(remoteMessage!=null && remoteMessage.getData() !=null && !remoteMessage.getData().isEmpty()){


             generateNotification(remoteMessage.getData().get("title"),remoteMessage.getData().get("image"), remoteMessage.getData().get("description")
              ,remoteMessage.getData().get("summary"));


         }else  if(remoteMessage.getNotification() != null){

             generateNotification(remoteMessage.getNotification().getTitle(),"", remoteMessage.getNotification().getBody()
                     , remoteMessage.getNotification().getBody());
             apiCall(URLS.Notification, myPostJson2());

          }

        super.onMessageReceived(remoteMessage);
    }



    void  generateNotification(  String title,String image,String desc, String summary ){
         if(title==null){
             title="";
         }
        NotificationCompat.Builder mBuilder =
                new NotificationCompat.Builder(getApplicationContext(),m.getFCMToken());
        Intent ii = new Intent(getApplicationContext(), DashBoard_Activity.class);
        PendingIntent pendingIntent = PendingIntent.getActivity(getApplicationContext() , 0, ii, 0);

        NotificationCompat.BigTextStyle bigText = new NotificationCompat.BigTextStyle();

            bigText.bigText(desc);


        bigText.setBigContentTitle(title);
        bigText.setSummaryText(summary);

        mBuilder.setContentIntent(pendingIntent);
        mBuilder.setSmallIcon(R.mipmap.ic_launcher_round);
        mBuilder.setContentTitle(title);
        mBuilder.setContentText(desc);
        mBuilder.setPriority(Notification.PRIORITY_MAX);

        if(image!=null && !image.isEmpty()){

            try {
                Bitmap chefBitmap = Glide.with(FcmService.this)
                        .asBitmap()
                        .load(image)
                        .submit()
                        .get();
                mBuilder .setLargeIcon(chefBitmap );
                mBuilder. setStyle(new NotificationCompat.BigPictureStyle()
                        .bigPicture(chefBitmap).setSummaryText(summary) );



            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }



        }else
            {

                mBuilder.setStyle(bigText);
            }


        NotificationManager mNotificationManager;
        mNotificationManager =
                (NotificationManager)  getSystemService(Context.NOTIFICATION_SERVICE);
        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);
// === Removed some obsoletes
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O)
        {
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            String channelId = m.getFCMToken();
            NotificationChannel channel = new NotificationChannel(
                    channelId,
                    title,
                    NotificationManager.IMPORTANCE_HIGH);
            // Configure the notification channel.
            channel.setDescription(desc);
            channel.enableLights(true);
            channel.enableVibration(true);
            channel.setSound(alarmSound, attributes); // This is IMPORTANT
            mNotificationManager.createNotificationChannel(channel);
            mBuilder.setChannelId(channelId);

        }



        mBuilder.setSound(alarmSound);
        mBuilder.setVibrate(new long[]{500, 500});

        mNotificationManager.notify(0, mBuilder.build());



        Log.d("notificationnnnnn","custom............." );
    }


    private JSONObject myPostJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (Comman.Check_Login(FcmService.this)) {
                jsonObject.put("userTypeId", m.getUserTypeId());
                jsonObject.put("userId", m.getUserId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return jsonObject;
    }
    private void apiCall(String URL,JSONObject jsonObject)
    {

        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                //  Comman.log("NotificationApi","Response"+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("true") && (!Comman.getValueFromJsonObject(response,"totalcount").equalsIgnoreCase("0")) )
                    {

                        m.setCounterValue(Comman.getValueFromJsonObject(response,"totalcount"));
                      LocalBroadcastManager    broadcaster = LocalBroadcastManager.getInstance(getBaseContext());

                        Intent intent = new Intent(REQUEST_ACCEPT);
                        intent.putExtra("count_value", m.getCounterValue() );

                        broadcaster.sendBroadcast(intent);
//                        noti_counter.setText(Comman.getValueFromJsonObject(response,"totalcount"));
//
//                        circle.setVisibility(View.VISIBLE);
                    }else {
                        LocalBroadcastManager    broadcaster = LocalBroadcastManager.getInstance(getBaseContext());
                        Intent intent = new Intent(REQUEST_ACCEPT);
                        intent.putExtra("count_value", "" );

                        broadcaster.sendBroadcast(intent);
//                   circle.setVisibility(View.GONE);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(FcmService.this);
        requestQueue.add(jsonObjectRequest);

    }


}
