package com.medparliament.Internet;

import org.json.JSONObject;

public interface VideoListener {
    public void onVideoResult(JSONObject jsonObject,Boolean status);
}
