package com.sodatracker.apptastic.sodatracker.utilities;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;

import com.sodatracker.apptastic.sodatracker.R;

/**
 * Created by mgarner on 10/2/2015.
 */
public class Utils {

    public static Intent rateApp(Context context) {
        if (context != null) {
            final Uri marketUri = Uri.parse("market://details?id=" + context.getPackageName());
            final Uri webUri = Uri.parse("https://play.google.com/store/apps/details?id=" + context.getPackageName());
            final Intent marketRateIntent = new Intent(Intent.ACTION_VIEW, marketUri);
            final Intent webRateIntent = new Intent(Intent.ACTION_VIEW, webUri);

            if (context.getPackageManager().queryIntentActivities(marketRateIntent, 0).size() > 0) {
                return marketRateIntent;
            } else {
                return webRateIntent;
            }
        }
        return null;
    }

    public static Intent shareApp(Context context) {
        Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
        sharingIntent.setType("text/plain");
        sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, context.getResources().getString(R.string.share_app_subject));
        sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, context.getResources().getString(R.string.share_app_message));
        return Intent.createChooser(sharingIntent, "Share via");
    }
}
