package com.example.medparliament.Internet;

import android.content.Context;
import android.view.View;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.error.AuthFailureError;
import com.android.volley.error.VolleyError;
import com.android.volley.request.JsonObjectRequest;
import com.android.volley.request.SimpleMultiPartRequest;
import com.android.volley.toolbox.Volley;
import com.example.medparliament.Utility.Comman;
import com.example.medparliament.Utility.Constant;
import com.example.medparliament.Internet.onResult;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Comment;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

public class Api_Calling {

    public static ArrayList<String>QualificationList=new ArrayList<>();
    public static HashMap<String ,String>QualifiactionHashMap=new HashMap<>();

    public static ArrayList<String>StudentIntrestList=new ArrayList<>();
    public static HashMap<String ,String>StudentIntrestHash=new HashMap<>();

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



    public static void postMethodCall_NO_MSG(final Context context, final View view, final onResult onResult, String URL, JSONObject jsonObject, final String name)
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

}
