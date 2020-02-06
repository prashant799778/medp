package com.example.medparliament.Internet;

import android.nfc.tech.NfcA;

public class URLS {
    public static String BASE_URL="http://134.209.153.34:5031/";

    ////////////////////////GET_METHODS/////////////////////////
    public static String LOGIN=BASE_URL+"Login";
    public static String ALL_UNIVERSITY=BASE_URL+"allUniversities";
    public static String ALL_QUALIFICATION=BASE_URL+"allQualifications";
    public static String INTEREST=BASE_URL+"allinterests";
    public static String ALL_CATEGORY=BASE_URL+"allCategories";
    public static String ALL_COUNTRY=BASE_URL+"allCountries";
    ///////////////////////POST_METHODS/////////////////////////
    public static String UpdateUser=BASE_URL+"UpdateUser1";
    public static String userDashBoardPost=BASE_URL+"userPostDashboard";
    public static String SIGNUP=BASE_URL+"SignUp";
    public static String changeProfilePic=BASE_URL+"changeProfilePic";
    public static String USER_PROFILE=BASE_URL+"userProfile";
    public static String USER_POST=BASE_URL+"userPost";
    public static String MY_POST=BASE_URL+"myPosts";
    public static String ALL_POSTS=BASE_URL+"allPosts";
    public static String ADD_USER=BASE_URL+"addUser";
}
