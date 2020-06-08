package com.medparliament.Activity;
import androidx.appcompat.app.AppCompatActivity;
import androidx.localbroadcastmanager.content.LocalBroadcastManager;

import android.app.ProgressDialog;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.os.Handler;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.blogspot.atifsoftwares.animatoolib.Animatoo;
import com.medparliament.Internet.Api_Calling;
import com.medparliament.Internet.URLS;
import com.medparliament.Internet.onResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.MySharedPrefrence;
import com.medparliament.Widget.Segow_UI_Semi_Font;

import org.json.JSONException;
import org.json.JSONObject;

import static com.medparliament.Utility.Comman.REQUEST_ACCEPT;

public class AboutUsActivity extends AppCompatActivity implements onResult {
    Button btn;
    Segow_UI_Semi_Font msg;
    onResult onResult;
    ImageView bck;
    ProgressDialog progressDialog;
    Segow_UI_Semi_Font noti_counter;
    LinearLayout circle;
    ImageView share;
    ImageView bell;
    Handler ha;
    MySharedPrefrence m;
    WebView webView;

    BroadcastReceiver receiver;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);


        m=MySharedPrefrence.instanceOf(AboutUsActivity.this);
        noti_counter = findViewById(R.id.noti_count);
//        apiCall(URLS.Notification, myPostJson2());
        noti_counter.setText(m.getCounterValue());



        this.onResult=this;
        bell = findViewById(R.id.bell);
        if (Comman.Check_Login(AboutUsActivity.this))
            bell.setVisibility(View.VISIBLE);


        circle=findViewById(R.id.circle);
        share=findViewById(R.id.share_tool);
         webView =findViewById(R.id.webViews);
        webView.clearCache(true);
        webView.clearHistory();
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.loadUrl("https://www.medachievers.com/");
         webView .setWebViewClient(new WebViewClient() {
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
                view.loadUrl(request.getUrl().toString());
                return false;
            }
        });
        webView.getSettings().setLoadWithOverviewMode(true);
         webView.getSettings().setUseWideViewPort(true);
        WebView.setWebContentsDebuggingEnabled(false);
        bell.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(AboutUsActivity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(AboutUsActivity.this, NotificationActivity.class));
                }else {
                    circle.setVisibility(View.GONE);

                }


            }
        });
        circle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                //Log.d(" belliconTAG", "onClick: bell icon ");
                if (Comman.Check_Login(AboutUsActivity.this)){
                    circle.setVisibility(View.GONE);
                    startActivity(new Intent(AboutUsActivity.this, NotificationActivity.class));
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


        Animatoo.animateSwipeLeft(AboutUsActivity.this);
        progressDialog = new ProgressDialog(AboutUsActivity.this);
        progressDialog.setMessage("Loading...");
        progressDialog.setCancelable(true);
        progressDialog.show();
        bck=findViewById(R.id.bckii);
        bck.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
        msg=findViewById(R.id.msg);
        btn=findViewById(R.id.enter);
        Api_Calling.postMethodCall(AboutUsActivity.this,getWindow().getDecorView().getRootView(),onResult, URLS.aboutUs,null,"d");
        if(Comman.Check_Login(AboutUsActivity.this))
        {
         btn.setVisibility(View.GONE);
        }
        btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(AboutUsActivity.this,Login_Signup_Activity.class));
            }
        });
    }
    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Animatoo.animateSwipeRight(AboutUsActivity.this);
    }
    @Override
    public void onResult(JSONObject jsonObject, Boolean status) {
        if(progressDialog!=null &&  progressDialog.isShowing())
            progressDialog.dismiss();
        if(jsonObject!=null && status)
        {
            try {
                JSONObject jsonObject1=jsonObject.getJSONArray("result").getJSONObject(0);
                msg.setText(Html.fromHtml(Comman.getValueFromJsonObject(jsonObject1,"description")));
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }

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
//                        noti_counter.setText(Comman.getValueFromJsonObject(response,"totalcount"));
                        m.setCounterValue(Comman.getValueFromJsonObject(response,"totalcount"));
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
        RequestQueue requestQueue= Volley.newRequestQueue(AboutUsActivity.this);
        requestQueue.add(jsonObjectRequest);

    }
    private JSONObject myPostJson2() {
        JSONObject jsonObject = new JSONObject();
        try {
            if (Comman.Check_Login(AboutUsActivity.this)) {
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
    protected void onStart() {
        super.onStart();
        LocalBroadcastManager.getInstance(AboutUsActivity.this).registerReceiver((receiver),
                new IntentFilter(REQUEST_ACCEPT)
        );
    }

    @Override
    protected void onStop() {
        super.onStop();
        LocalBroadcastManager.getInstance(AboutUsActivity.this).unregisterReceiver(receiver);
    }
}
