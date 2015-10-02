package com.sodatracker.apptastic.sodatracker;

import android.app.Activity;
import android.app.Application;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;

import java.util.ArrayList;

/**
 * Created by mgarner on 10/1/2015.
 */
public class SodaTrackerApp extends Application {
    public static final String CLASS_NAME = SodaTrackerApp.class.getName();
    public static final boolean DEBUG_CLASS = false;
    public static final boolean DEBUG_METHOD_CALLS = false;

    public static final String SP_SODA_ARRAY_KEY = "sodaArrayListKey";

    @Override
    public void onCreate() {
        super.onCreate();

    }

    public static void saveSodaList(Context context, ArrayList<Soda> sodaList) {
        if (context != null && sodaList != null) {
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            SharedPreferences.Editor editor = sharedPreferences.edit();
            Gson gson = new Gson();
            StringBuilder jsonStringBuilder = new StringBuilder();
            for (int i = 0; i < sodaList.size(); i++) {
                String jsonStringWithSeperator = gson.toJson(sodaList.get(i)) + ((i + 1 != sodaList.size()) ? "~~" : "");
                jsonStringBuilder.append(jsonStringWithSeperator);
            }
            editor.putString(SP_SODA_ARRAY_KEY, jsonStringBuilder.toString());
            editor.apply();
        }
    }

    public static ArrayList<Soda> getSodaList(Context context) {
        if (context != null) {
            ArrayList<Soda> sodaList = null;
            SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(context.getApplicationContext());
            String jsonString = sharedPreferences.getString(SP_SODA_ARRAY_KEY, "");
            if (jsonString.length() > 0) {
                String[] jsonStringArray = jsonString.split("~~");
                if (jsonStringArray.length > 0) {
                    Gson gson = new Gson();
                    sodaList = new ArrayList<>();
                    for (int i = 0; i < jsonStringArray.length; i++) {
                        Soda soda = gson.fromJson(jsonStringArray[i], Soda.class);
                        sodaList.add(soda);
                    }
                }
            }
            return sodaList;
        } else {
            return null;
        }
    }

    public static void addMoreSoda(Activity activity) {
        Intent getMoreSodaIntent = new Intent(activity, AddSodaActivity.class);
        activity.startActivityForResult(getMoreSodaIntent, MainActivity.REQUEST_CODE_FOR_SODA);
    }
}
