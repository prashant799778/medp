package com.medparliament.Activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.NewModel.up_skill_model;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.Segow_UI_Bold_Font;
import com.medparliament.Widget.Segow_UI_Semi_Font;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.PlayerConstants;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.YouTubePlayer;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.listeners.YouTubePlayerListener;
import com.pierfrancescosoffritti.androidyoutubeplayer.core.player.views.YouTubePlayerView;
import com.razorpay.Checkout;
import com.razorpay.CheckoutActivity;
import com.razorpay.PaymentResultListener;

import org.jetbrains.annotations.NotNull;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import static com.medparliament.Utility.Comman.REQUEST_ACCEPT;

public class Up_Skill_Details_Activity extends AppCompatActivity implements onResult, PaymentResultListener {
    Segow_UI_Semi_Font  msg;
    ImageButton bck;
    Button enroll,enroll1;
    Segow_UI_Bold_Font title;
    YouTubePlayerView youTubePlayerView;
    ProgressDialog progressDialog;
    MySharedPrefrence m;
    onResult onResult;
    Segow_UI_Semi_Font noti_counter;
    LinearLayout circle;
    ImageView share;
    ImageView bell;
    Handler ha;
    ImageView image;
    up_skill_model skill_model;
    String id="";
    Checkout checkout;
    RelativeLayout nodata;
    Segow_UI_Semi_Font lenght,effort,price,institute,subject,level,language,videotext;
    BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_up__skill__details_);
        noti_counter = findViewById(R.id.noti_count);
        m=MySharedPrefrence.instanceOf(Up_Skill_Details_Activity.this);
//        apiCall(URLS.Notification, myPostJson2());
        noti_counter.setText(m.getCounterValue());

        Animatoo.animateSwipeLeft(Up_Skill_Details_Activity.this);
        bell = findViewById(R.id.bell);
        if (Comman.Check_Login(Up_Skill_Details_Activity.this))
            bell.setVisibility(View.VISIBLE);


        circle=findViewById(R.id.circle);
        share=findViewById(R.id.share_tool);

        receiver = new BroadcastReceiver() {
            @Override
            public void onReceive(Context context, Intent intent) {
                try {
                    String   value= intent.getStringExtra("count_value");
                    if(value!=null && !value.isEmpty()){
                        noti_counter.setText(value);

                        circle.setVisibility(View.VISIBLE);

                    }else{
                        circle.setVisibility(View.GONE);
                    }


                } catch (Exception e) {
                    e.printStackTrace();
                }

            }
        };


        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(Up_Skill_Details_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(Up_Skill_Details_Activity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(Up_Skill_Details_Activity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(Up_Skill_Details_Activity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });

        share.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent emailIntent = new Intent(Intent.ACTION_SEND);
                emailIntent.putExtra(Intent.EXTRA_SUBJECT, "MedParliament App");
                emailIntent.putExtra(Intent.EXTRA_TEXT, getResources().getString(R.string.text) + "https://play.google.com/store/apps/details?id=com.medparliament");
                emailIntent.setType("text/plain");
                startActivity(Intent.createChooser(emailIntent, "Share via"));
            }
        });




        msg=findViewById(R.id.msg);
        bck=findViewById(R.id.bck);
        enroll=findViewById(R.id.enroll);
        enroll1=findViewById(R.id.enroll_1);
        progressDialog = new ProgressDialog(Up_Skill_Details_Activity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        nodata=findViewById(R.id.nodata);
        this.onResult=this;
        lenght=findViewById(R.id.lenght);
        youTubePlayerView=findViewById(R.id.video);
        effort=findViewById(R.id.effort);
        price=findViewById(R.id.price);
        institute=findViewById(R.id.institute);
        subject=findViewById(R.id.subject);
        level=findViewById(R.id.level);
        image=findViewById(R.id.cover);
        language=findViewById(R.id.language);
        videotext=findViewById(R.id.videotext);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
             onBackPressed();
            }
        });

        Checkout.preload(getApplicationContext());


        Intent intent=getIntent();
        if(intent!=null)
        {
            skill_model=(up_skill_model) intent.getSerializableExtra("data");
        }

        Comman.log("Status",""+skill_model.getMakedone());
        if(skill_model.getMakedone().equalsIgnoreCase("1"))
        {
            Comman.log("Statusttt",""+skill_model.getMakedone());
            enroll1.setEnabled(false);
            enroll.setEnabled(false);
            enroll.setText("Already Done");
            enroll1.setText("Already Done");
            enroll.setBackgroundColor(Color.GRAY);
            enroll1.setBackgroundColor(Color.GRAY);
        }
        enroll.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Up_Skill_Details_Activity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
//                startPayment();
                genetareOrderId(progressDialog);
            }
        });
        enroll1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                progressDialog = new ProgressDialog(Up_Skill_Details_Activity.this);
                progressDialog.setMessage("Loading...");
                progressDialog.setCancelable(true);
                progressDialog.show();
                genetareOrderId(progressDialog);
//                startPayment();
//                Api_Calling.postMethodCall_1(Up_Skill_Details_Activity.this,getWindow().getDecorView().getRootView(),URLS.enrollUpSkills,myPostJson(),"",enroll,enroll1,progressDialog);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        Api_Calling.postMethodCall(Up_Skill_Details_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.getUpSkillsOpportunity,setJson(),"");

        LocalBroadcastManager.getInstance(Up_Skill_Details_Activity.this).registerReceiver((receiver),
                new IntentFilter(REQUEST_ACCEPT)
        );


    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        JSONObject jo = new JSONObject();
        Comman.log("ONResultMewthod", "" + jsonObject);
        if (progressDialog != null && progressDialog.isShowing())
            progressDialog.dismiss();
        nodata.setVisibility(View.VISIBLE);
        if (jsonObject != null && status) {
            nodata.setVisibility(View.GONE);
            Gson gson = new GsonBuilder().create();
            try {
                ArrayList<up_skill_model> rm = gson.fromJson(jsonObject.getString("result"), new TypeToken<ArrayList<up_skill_model>>(){}.getType());
                final up_skill_model r=rm.get(0);
                Segow_UI_Bold_Font t1=    findViewById(R.id.t1);
                 t1.setText(r.getNewsTitle());
                lenght.setText(r.getLength());
                effort.setText(r.getEffort());
                price.setText("USD "+r.getPrice());
                institute.setText(r.getInstitutions());
                subject.setText(r.getSummary());
                level.setText(r.getLevel());
                language.setText(r.getLanguage());
                videotext.setText(r.getVideoTranscript());
                msg.setText(Html.fromHtml(r.getNewsDesc()));
                if(!r.getImagePath().equalsIgnoreCase("")){
                Comman.setImageWithCondition(Up_Skill_Details_Activity.this,image,r.getImagePath());
                youTubePlayerView.setVisibility(View.GONE);}else {
                    image.setVisibility(View.GONE);
                    youTubePlayerView.getPlayerUiController().showYouTubeButton(false);
                    youTubePlayerView.addYouTubePlayerListener(new YouTubePlayerListener() {
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
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }else {
            Api_Calling.postMethodCall(Up_Skill_Details_Activity.this,getWindow().getDecorView().getRootView(),onResult, URLS.getUpSkillsOpportunity,setJson(),"");
        }

    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(Up_Skill_Details_Activity.this);
    }
    public JSONObject setJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("id",""+skill_model.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public JSONObject myPostJson()
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("userTypeId",""+m.getUserTypeId()).put("userId",""+m.getUserId()).put("upSkillsId",""+skill_model.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("EnrollJson",""+jsonObject);
        return jsonObject;
    }


    @Override
    public void onPaymentSuccess(String s) {
        Comman.log("PaymentInformation","OnSuccess "+s);
        showDialogeBox(Up_Skill_Details_Activity.this,"Information","Your Payment Successfully Done!");
        enroll1.setEnabled(false);
        enroll.setEnabled(false);
        enroll.setText("Already Done");
        enroll1.setText("Already Done");
        enroll.setBackgroundColor(Color.GRAY);
        enroll1.setBackgroundColor(Color.GRAY);
        Api_Calling.postMethodCall_1(Up_Skill_Details_Activity.this,getWindow().getDecorView().getRootView(),URLS.enrollUpSkills,myPostJson(),"",enroll,enroll1,progressDialog);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Comman.log("PaymentInformation","OnError "+s);
        showDialogeBox(Up_Skill_Details_Activity.this,"Information","Sorry! Unsuccessful payment. Please try again or contact MedParliament for assistance.");
    }

    public void startPayment(String orderId) {
        Comman.log("PaymentInformation","generatedOrderId "+orderId);

        /**
         * Instantiate Checkout
         */
        checkout = new Checkout();
        checkout.setKeyID(getResources().getString(R.string.RazorKey));

        /**
         * Set your logo here
         */

        /**
         * Reference to current activity
         */
        final Activity activity = this;

        /**
         * Pass your payment options to the Razorpay Checkout as a JSONObject
         */
        try {
            JSONObject options = new JSONObject();

            /**
             * Merchant Name
             * eg: ACME Corp || HasGeek etc.
             */

            /**
             * Description can be anything
             * eg: Reference No. #123123 - This order number is passed by you for your internal reference. This is not the `razorpay_order_id`.
             *     Invoice Payment
             *     etc.
             */

            options.put("order_id", orderId);
            options.put("currency", "USD");
             //Log.d("test.........","order_iiiii");

            /**
             * Amount is always passed in currency subunits
             * Eg: "500" = INR 5.00
             */
            if(skill_model.getPrice()!=null){
            try {
                options.put("amount", ((Integer.parseInt(skill_model.getPrice()))*100));
            }catch (NumberFormatException e)
            {

            }
            }
            Comman.log("PaymentInformation","Json"+options.toString());
            checkout.open(activity, options);
        } catch(Exception e) {
            Comman.log("PaymentInformation","OnException "+e.getMessage());
        }
    }
    public  void genetareOrderId(final ProgressDialog progressDialog)
    {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, "http://54.169.46.109:5031/generateOrder", orderIdJson(), new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {

                Comman.log("PaymentInformation","OnResponseOrderID "+response);
                try {
                    if(Boolean.parseBoolean(response.getString("status")))
                    {
                        if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                        JSONObject jsonObject=response.getJSONObject("result");
                        startPayment(Comman.getValueFromJsonObject(jsonObject,"id"));
                    }else {
                        if (progressDialog != null && progressDialog.isShowing())
                        progressDialog.dismiss();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Comman.log("PaymentInformation","OnExceptionVolley "+error.getMessage());
            }
        });
        RequestQueue requestQueue= Volley.newRequestQueue(Up_Skill_Details_Activity.this);
        requestQueue.add(jsonObjectRequest);
    }

    public JSONObject orderIdJson()
    {
        try {
            JSONObject orderRequest = new JSONObject();
            orderRequest.put("userId",""+m.getUserId());
            orderRequest.put("course_id",""+skill_model.getId());
            orderRequest.put("currency", "USD");
            if(skill_model.getPrice()!=null){
                try {
                orderRequest.put("order_amount", ((Integer.parseInt(skill_model.getPrice()))*100)); // amount in the smallest currency unit
                     }catch (NumberFormatException e)
                {

                }
            }
            Comman.log("PaymentInformation","JsonVolley "+orderRequest);
            return  orderRequest;
        } catch (JSONException e) {
            System.out.println(e.getMessage());
        }
        return null;

    }

    public void showDialogeBox(Context context, String title, String msg)
    {
        final MaterialAlertDialogBuilder alertDialogBuilder=new MaterialAlertDialogBuilder(context,R.style.custom_dialog);
        final AlertDialog alertDialog = alertDialogBuilder.create();
        final View dialogView = LayoutInflater.from(context).inflate(R.layout.custom_layout, null, false);
        Button yes=dialogView.findViewById(R.id.yes);
        Button no=dialogView.findViewById(R.id.no);
        ImageView mainimg=dialogView.findViewById(R.id.mainimg);
        no.setVisibility(View.GONE);
        Segow_UI_Bold_Font title1=dialogView.findViewById(R.id.title);
        Segow_UI_Semi_Font msg1=dialogView.findViewById(R.id.msg);
        title1.setText(title);
        msg1.setText(msg);
//        WindowManager.LayoutParams lp = alertDialog.getWindow().getAttributes();
//        lp.dimAmount=0.7f;
//        alertDialog.getWindow().setAttributes(lp);
//        alertDialog.getWindow().addFlags(WindowManager.LayoutParams.FLAG_BLUR_BEHIND);
        alertDialog.setView(dialogView);
        alertDialog.show();
        yes.setText("Ok");
        yes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
//                m.clearData();
//                startActivity(new Intent(Up_Skill_Details_Activity.this,DashBoard_Activity.class));
//                finish();
            }
        });
        no.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alertDialog.dismiss();
            }
        });

    }

    private void apiCall(String URL,JSONObject jsonObject)
    {
        Comman.log("NotificationApi","Callling");
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Comman.log("NotificationApi","Response"+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("true") && (!Comman.getValueFromJsonObject(response,"totalcount").equalsIgnoreCase("0")) )
                    {
                        noti_counter.setText(Comman.getValueFromJsonObject(response,"totalcount"));
                        circle.setVisibility(View.VISIBLE);
                    }else {
                        circle.setVisibility(View.GONE);
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
        RequestQueue requestQueue= Volley.newRequestQueue(Up_Skill_Details_Activity.this);
        requestQueue.add(jsonObjectRequest);

    }
    private JSONObject myPostJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (Comman.Check_Login(Up_Skill_Details_Activity.this)) {
                jsonObject.put("userTypeId", m.getUserTypeId());
                jsonObject.put("userId", m.getUserId());
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }
        Comman.log("Notificationscsdcsdcdsvdsfvfdv", "" + jsonObject);
        return jsonObject;
    }


    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(Up_Skill_Details_Activity .this).unregisterReceiver(receiver);
    }
}
