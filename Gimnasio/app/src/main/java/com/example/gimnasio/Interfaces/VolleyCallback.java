package com.example.gimnasio.Interfaces;

import org.json.JSONObject;


    public interface VolleyCallback {
        void onSuccess(JSONObject result);
        void onError(String error);
    }


