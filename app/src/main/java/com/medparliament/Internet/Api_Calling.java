package com.medparliament.Internet;

import android.app.ProgressDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.medparliament.Internet.NewModel.onNotificationResult;
import com.medparliament.R;
import com.medparliament.Utility.Comman;
import com.medparliament.Utility.Constant;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Api_Calling {

    public static ArrayList<String>QualificationList=new ArrayList<>();
    public static HashMap<String ,String>QualifiactionHashMap=new HashMap<>();

    public static ArrayList<String>StudentIntrestList=new ArrayList<>();
    public static HashMap<String ,String>StudentIntrestHash=new HashMap<>();
    public static ArrayList<String> DPIntrestList=new ArrayList<>();
    public static HashMap<String ,String> DPIntrestHash=new HashMap<>();

    public static ArrayList<String>ProfileList=new ArrayList<>();
    public static HashMap<String ,String>ProfileHash=new HashMap<>();



    public static ArrayList<String>CountrytList=new ArrayList<>();
    public static HashMap<String ,String>CountryHash=new HashMap<>();

    public static ArrayList<String>UniversityList=new ArrayList<>();
    public static HashMap<String ,String>UniversityHashMap=new HashMap<>();


    public static void getMethodCall(final Context context, String URL, final View view, final onResult onResult, final String name)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context,view, Constant.NO_INTERNET);
            onResult.onResult(null,false);
        }else {
            final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                 Comman.log(name,""+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                        onResult.onResult(response,true);}else {
                            onResult.onResult(response,false);
                            Comman.topSnakBar(context,view, response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            final RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    requestQueue.getCache().clear();
                }
            });
        }

    }




    public static void getQualificationListData(final Context context, final View view, String URL)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context, view,Constant.NO_INTERNET);
        }else {
            final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log("ListResponse",""+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            QualificationList.clear();
                            JSONArray array=response.getJSONArray("result");
                            for (int i=0;i<array.length();i++)
                            {
                                QualificationList.add(Comman.getValueFromJsonObject(array.getJSONObject(i),"qualificationName"));
                                QualifiactionHashMap.put(Comman.getValueFromJsonObject(array.getJSONObject(i),"qualificationName"),Comman.getValueFromJsonObject(array.getJSONObject(i),"id"));
                            }
                        }else {
//                            Comman.toastLong(context, response.getString("result"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            final RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    requestQueue.getCache().clear();
                }
            });
        }

    }
    public static void getUniversityListData(final Context context, final View view, String URL)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context,view, Constant.NO_INTERNET);
        }else {
            final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log("ListResponse",""+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            UniversityList.clear();
                            JSONArray array=response.getJSONArray("result");
                            for (int i=0;i<array.length();i++)
                            {
                                UniversityList.add(Comman.getValueFromJsonObject(array.getJSONObject(i),"universityName"));
                                UniversityHashMap.put(Comman.getValueFromJsonObject(array.getJSONObject(i),"universityName"),Comman.getValueFromJsonObject(array.getJSONObject(i),"id"));
                            }
                        }else {
//                            Comman.toastLong(context, response.getString("result"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            final RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    requestQueue.getCache().clear();
                }
            });
        }

    }


    public static void getStudentIntrestList(final Context context, final View view ,String URL, JSONObject jsonObject)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context, view,Constant.NO_INTERNET);
        }else {
            final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log("InterestListResponse",""+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            StudentIntrestList.clear();
                            JSONArray array=response.getJSONArray("result");
                            for (int i=0;i<array.length();i++)
                            {
                                StudentIntrestList.add(Comman.getValueFromJsonObject(array.getJSONObject(i),"name"));
                                StudentIntrestHash.put(Comman.getValueFromJsonObject(array.getJSONObject(i),"name"),Comman.getValueFromJsonObject(array.getJSONObject(i),"id"));
                            }
                        }else {
//                            Comman.toastLong(context, response.getString("result"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            final RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    requestQueue.getCache().clear();
                }
            });
        }

    }
    public static void getDPIntrestList(final Context context, final View view ,String URL, JSONObject jsonObject)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context, view,Constant.NO_INTERNET);
        }else {
            final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log("DPInterestListResponse",""+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            DPIntrestList.clear();
                            JSONArray array=response.getJSONArray("result");
                            for (int i=0;i<array.length();i++)
                            {
                                DPIntrestList.add(Comman.getValueFromJsonObject(array.getJSONObject(i),"name"));
                                DPIntrestHash.put(Comman.getValueFromJsonObject(array.getJSONObject(i),"name"),Comman.getValueFromJsonObject(array.getJSONObject(i),"id"));
                            }
                        }else {
//                            Comman.toastLong(context, response.getString("result"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            final RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    requestQueue.getCache().clear();
                }
            });
        }

    }
    public static void getALLCountry(final Context context, final View view, String URL)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context,view, Constant.NO_INTERNET);
        }else {
            final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log("CountryListResponse",""+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            CountrytList.clear();
                            JSONArray array=response.getJSONArray("result");
                            for (int i=0;i<array.length();i++)
                            {
                                CountrytList.add(Comman.getValueFromJsonObject(array.getJSONObject(i),"countryName"));
                                CountryHash.put(Comman.getValueFromJsonObject(array.getJSONObject(i),"countryName"),Comman.getValueFromJsonObject(array.getJSONObject(i),"id"));
                            }
                        }else {
//                            Comman.toastLong(context, response.getString("result"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            final RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    requestQueue.getCache().clear();
                }
            });
        }

    }

    public static void getALLCATEGORY(final Context context, final View view, String URL)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context,view, Constant.NO_INTERNET);
        }else {
            final JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.GET, URL, null, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log("CountryListResponse",""+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            ProfileList.clear();
                            JSONArray array=response.getJSONArray("result");
                            for (int i=0;i<array.length();i++)
                            {
                                ProfileList.add(Comman.getValueFromJsonObject(array.getJSONObject(i),"name"));
                                ProfileHash.put(Comman.getValueFromJsonObject(array.getJSONObject(i),"name"),Comman.getValueFromJsonObject(array.getJSONObject(i),"id"));
                            }
                        }else {
//                            Comman.toastLong(context, response.getString("result"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context, view,Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            final RequestQueue requestQueue= Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
            requestQueue.addRequestFinishedListener(new RequestQueue.RequestFinishedListener<Object>() {
                @Override
                public void onRequestFinished(Request<Object> request) {
                    requestQueue.getCache().clear();
                }
            });
        }

    }




    public static void postMethodCall(final Context context, final View view, final onResult onResult, String URL, JSONObject jsonObject, final String name)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context,view, Constant.NO_INTERNET);
            onResult.onResult(null,false);
        }else {
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log(name,"yyyyy"+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            onResult.onResult(response,true);}else {
                            onResult.onResult(null,false);
                            Comman.topSnakBar(context,view, response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue=Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);

        }

    }

    public static void postMethodCall_NO_MSG2(final Context context, final View view, final onNotificationResult onNotificationResult, String URL, JSONObject jsonObject, final String name)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context,view, Constant.NO_INTERNET);
            onNotificationResult.onNotificationResult(null,false);
        }else {
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log(name,"yyyyy"+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            onNotificationResult.onNotificationResult(response,true);}else {
                            onNotificationResult.onNotificationResult(null,false);
                            Comman.topSnakBar(context,view, response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue=Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);

        }

    }



    public static void postMethodCallForListData(final Context context, final View view, final OnListData onResult, String URL, JSONObject jsonObject, final String name)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context,view, Constant.NO_INTERNET);
            onResult.onListData(null,false);
        }else {
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log(name,"yyyyy"+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            onResult.onListData(response,true);}else {
                            onResult.onListData(null,false);
                            Comman.topSnakBar(context,view, response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue=Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);

        }

    }




    public static void postMethodCall_1(final Context context, final View view, String URL, JSONObject jsonObject, final String name, final Button button1, final Button button2, final ProgressDialog progressDialog)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context,view, Constant.NO_INTERNET);
        }else {
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log(name,"yyyyy"+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            button1.setEnabled(false);
                            button2.setEnabled(false);
                            button1.setText(" Done");
                            button2.setText(" Done");
                            button1.setBackgroundColor(Color.GRAY);
                            button2.setBackgroundColor(Color.GRAY);
                            progressDialog.dismiss();
                        }
                        progressDialog.dismiss();
                    } catch (JSONException e) {
                        e.printStackTrace();
                        progressDialog.dismiss();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
            RequestQueue requestQueue=Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);

        }

    }






    public static void likePost(Context context, JSONObject jsonObject, final ImageView imageView, final TextView textView, String counter)
    {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URLS.LIKEPOST, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Comman.log("Result",""+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("true")) {
                        textView.setText("("+ response.getString("result")+")");
                        imageView.setEnabled(false);
                        imageView.setImageResource(R.drawable.after);
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
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }


    public static void likePostMarket(Context context, JSONObject jsonObject, final ImageView imageView, final TextView textView, String counter)
    {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URLS.likeMarketingInsight, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Comman.log("Result",""+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("true")) {
                        textView.setText(response.getJSONArray("result").getJSONObject(0).getString("count")+"  Endorse");
                        imageView.setEnabled(false);
                        imageView.setImageResource(R.drawable.ic_like_after);
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
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }




    public static void likePostEvent(Context context, JSONObject jsonObject, final ImageView imageView, final TextView textView, String counter)
    {
        JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URLS.eventInterest1, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject response) {
                Comman.log("Result",""+response);
                try {
                    if(response.getString("status").equalsIgnoreCase("true")) {
                        textView.setText(response.getJSONArray("result").getJSONObject(0).getString("count")+"  Endorse");
                        imageView.setEnabled(false);
                        imageView.setImageResource(R.drawable.ic_like_after);
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
        RequestQueue requestQueue=Volley.newRequestQueue(context);
        requestQueue.add(jsonObjectRequest);
    }



    public static void postMethodCall_NO_MSG(final Context context, final View view, final onResult onResult, String URL, JSONObject jsonObject, final String name)
    {
        if(!Comman.isConnectedToInternet(context))
        {
            Comman.topSnakBar(context,view, Constant.NO_INTERNET);
            onResult.onResult(null,false);
        }else {
            Comman.log(name,"yyyyy");
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log(name,"yyyyy"+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            onResult.onResult(response,true);}else {
                            onResult.onResult(null,false);
//                            Comman.topSnakBar(context,view, response.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue=Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);

        }

    }


    public static void multiPartCall(final Context context, final View view, String URL, final JSONObject jsonObject, final onResult onResult, final String name) {
        if (!Comman.isConnectedToInternet(context)) {
            Comman.topSnakBar(context, view, Constant.NO_INTERNET);
        } else {
            SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Comman.log(name,""+response);
                    try {
                        JSONObject jsonObject1=new JSONObject(response);
                        if(Boolean.parseBoolean(jsonObject1.getString("status"))){
                            onResult.onResult(jsonObject1,true);}else {
                            onResult.onResult(null,false);
                            Comman.topSnakBar(context,view, jsonObject1.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
             simpleMultiPartRequest.addStringParam("data",""+jsonObject.toString());
             simpleMultiPartRequest.addStringParam("postImage","");
            try {
                Comman.log("FinalData",""+simpleMultiPartRequest.getBody());
            } catch (AuthFailureError authFailureError) {
                authFailureError.printStackTrace();
            }
            RequestQueue requestQueue=Volley.newRequestQueue(context);
            requestQueue.add(simpleMultiPartRequest);
        }
    }
    public static void multiPartCall1(final Context context, final View view, String URL, final JSONObject jsonObject, final onResult onResult, final String name,ArrayList<String>arrayList) {
        if (!Comman.isConnectedToInternet(context)) {
            Comman.topSnakBar(context, view, Constant.NO_INTERNET);
        } else {
            Comman.log("Inside","Inside1");
            SimpleMultiPartRequest simpleMultiPartRequest = new SimpleMultiPartRequest(Request.Method.POST, URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    Comman.log(name,""+response);
                    try {
                        Comman.log("Inside","Inside2");
                        JSONObject jsonObject1=new JSONObject(response);
                        if(Boolean.parseBoolean(jsonObject1.getString("status"))){
                            onResult.onResult(jsonObject1,true);}else {
                            onResult.onResult(null,false);
                            Comman.topSnakBar(context,view, jsonObject1.getString("message"));
                        }
                    } catch (JSONException e) {
                        e.printStackTrace();
                        Comman.topSnakBar(context,view, Constant.SOMETHING_WENT_WRONG);
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Comman.log("Inside","Inside3"+error.getMessage());
                }
            });
            simpleMultiPartRequest.addStringParam("data",""+jsonObject.toString());
            if(arrayList.size()>0)
            simpleMultiPartRequest.addFile("postImage",""+new File(arrayList.get(0).trim()));
            Comman.log("Inside","Inside1");
            RequestQueue requestQueue=Volley.newRequestQueue(context);
            requestQueue.add(simpleMultiPartRequest);
        }
    }


    public static void videoApiCalling(Context context, View v, String URL, JSONObject object, final VideoListener videoListener)
    {
        if (!Comman.isConnectedToInternet(context)) {
            Comman.topSnakBar(context, v, Constant.NO_INTERNET);
        } else {
            JsonObjectRequest jsonObjectRequest=new JsonObjectRequest(Request.Method.POST, URL, object, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject response) {
                    Comman.log("Response",""+response);
                    try {
                        if(Boolean.parseBoolean(response.getString("status"))){
                            videoListener.onVideoResult(response,true);}else {
                            videoListener.onVideoResult(null,false);}
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                }
            });
            RequestQueue requestQueue=Volley.newRequestQueue(context);
            requestQueue.add(jsonObjectRequest);
        }
    }

}
