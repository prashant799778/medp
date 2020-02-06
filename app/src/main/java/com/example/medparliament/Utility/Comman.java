package com.example.medparliament.Utility;

import android.Manifest;
import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Color;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AlertDialog;
import androidx.core.app.ActivityCompat;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import com.example.medparliament.R;

import com.example.medparliament.Widget.Segow_UI_Semi_Font;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.sun.easysnackbar.EasySnackBar;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import de.hdodenhof.circleimageview.CircleImageView;

public class Comman {
    public  static boolean Check_Login(Context context)
    {
        com.example.medparliament.Utility.MySharedPrefrence mySharedPrefrence= com.example.medparliament.Utility.MySharedPrefrence.instanceOf(context);
        if(mySharedPrefrence.isLoggedIn())
        {
            return true;
        }
        return false;
    }

    public static boolean isConnectedToInternet(Context context) {
        ConnectivityManager connectivity = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (connectivity != null) {
            @SuppressLint("MissingPermission") NetworkInfo[] info = connectivity.getAllNetworkInfo();
            if (info != null)
                for (int i = 0; i < info.length; i++)
                    if (info[i].getState() == NetworkInfo.State.CONNECTED) {
                        return true;
                    }

        }
        return false;
    }
    public static void toastLong(Context context, String text) {
        Toast.makeText(context, text, Toast.LENGTH_LONG).show();
    }
    public static void log(String tag, String text) {
        if(text!=null)
        Log.d(tag, text);
    }
    public static JSONObject deviceInformation(Context context)
    {
        JSONObject jsonObject=new JSONObject();
        try {
            jsonObject.put("IMEI",""+getIMEI(context)).put("DeviceName",""+android.os.Build.MODEL);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return jsonObject;
    }
    public static String getIMEI(Context context) {
        try {
            TelephonyManager telephonyManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_PHONE_STATE) != PackageManager.PERMISSION_GRANTED) {
                // TODO: Consider calling
                //    ActivityCompat#requestPermissions
                // here to request the missing permissions, and then overriding
                //   public void onRequestPermissionsResult(int requestCode, String[] permissions,
                //                                          int[] grantResults)
                // to handle the case where the user grants the permission. See the documentation
                // for ActivityCompat#requestPermissions for more details.
                return "";
            }
            @SuppressLint("MissingPermission") String imei = telephonyManager.getDeviceId();
            Log.e("IMEI_NUMBER", "=" + imei);
            if (imei != null && !imei.isEmpty()) {
                return imei;
            } else {
                return android.os.Build.SERIAL;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "not_found";
    }
    public static Boolean isFilled(EditText editText)
    {
        if(editText.length()==0){
            editText.setFocusable(true);
            editText.setError(com.example.medparliament.Utility.Constant.PLEASE_FILL_THIS_FIELD);
            return false;
        }

        return true;
    }


  @SuppressLint("WrongConstant")
  public static void topSnakBar(Context context,View mView,String msg)
  {
      // Must create custom view in this way,so it can display normally
      if(mView!=null){
      View contentView = EasySnackBar.convertToContentView(mView, R.layout.top_snakbar);
      Segow_UI_Semi_Font segow_ui_semi_font=contentView.findViewById(R.id.msg);
      if(msg!=null)
      segow_ui_semi_font.setText(msg);
      // true represent show at top,false at bottom
      EasySnackBar.make(mView, contentView, EasySnackBar.LENGTH_LONG, true).show();}
  }

    public static String getValueFromJsonObject(JSONObject jsonObject, String key) {
        String s = "";
        if (jsonObject.has(key)) {
            try {
                s = jsonObject.getString(key);
                if (s.equalsIgnoreCase(null)) {
                    s = "";
                }
            } catch (JSONException e) {
                e.printStackTrace();
            } finally {
                return s;
            }
        }
        return s;
    }
    public static String uniqueId(Context context)
    {
        String uniqueId="";
        uniqueId = Settings.Secure.getString(context.getContentResolver(),
                Settings.Secure.ANDROID_ID);
        Comman.log("UniqueId",""+uniqueId);
        return uniqueId;
    }



    public static  void setMandatoryTextView(TextView text, String title)
    {

        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(title);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setHint(builder);
    }
    public static  void setMandatory(EditText text, String title)
    {

        String colored = "*";
        SpannableStringBuilder builder = new SpannableStringBuilder();

        builder.append(title);
        int start = builder.length();
        builder.append(colored);
        int end = builder.length();

        builder.setSpan(new ForegroundColorSpan(Color.RED), start, end,
                Spannable.SPAN_EXCLUSIVE_EXCLUSIVE);
        text.setHint(builder);

    }
    public static boolean isVisible(final View view) {
        if (view.isShown()) {
            return true;
        }
        return false;
    }
    public static long timeInms(String date)
    {
        Date d2 = null;
        try {
            d2 = new SimpleDateFormat("yyyy-M-dd HH:mm:ss").parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        long diff = d2.getTime();
//        diff=diff+(3600000*6);
        return diff;
    }
    public static void setRoundedImage(Context context, CircleImageView circleImageView, String path)
    {
      Glide.with(context).load(path).
                apply(RequestOptions.skipMemoryCacheOf(true)).apply(RequestOptions.encodeQualityOf(100))
                .apply(RequestOptions.diskCacheStrategyOf(DiskCacheStrategy.NONE)).into(circleImageView);
    }
    public static void ChangeFocus(final EditText edittext) {
        edittext.setOnFocusChangeListener(
                new View.OnFocusChangeListener() {
                    @Override
                    public void onFocusChange(View view, boolean b) {

                        if (b) {
                            edittext.setBackgroundResource(R.drawable.focused);

                        } else {
                            edittext.setBackgroundResource(R.drawable.edit_text_boarder);
                        }
                    }
                });
    }

}
