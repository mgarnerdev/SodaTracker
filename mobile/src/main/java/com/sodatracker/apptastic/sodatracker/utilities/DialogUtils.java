package com.sodatracker.apptastic.sodatracker.utilities;

import android.app.Activity;
import android.view.View;
import android.widget.TextView;

import com.afollestad.materialdialogs.MaterialDialog;
import com.sodatracker.apptastic.sodatracker.BuildConfig;
import com.sodatracker.apptastic.sodatracker.R;

/**
 * Created by mgarner on 10/2/2015.
 */
public class DialogUtils {

    public static void showErrorMsgDialog(final Activity activity, String message) {
        if (activity != null && !activity.isFinishing()) {
            new MaterialDialog.Builder(activity)
                    .content(message)
                    .positiveText(android.R.string.ok)
                    .show();
        }
    }

    public static void showMsgDialog(final Activity activity, String title, String message) {
        if (activity != null && !activity.isFinishing()) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                    .title(title)
                    .content(message)
                    .positiveText(android.R.string.ok);
            builder.show();
        }
    }

    public static void showMsgConfirmationDialog(final Activity activity, String title, String message, String confirmation) {
        if (activity != null && !activity.isFinishing()) {
            MaterialDialog.Builder builder = new MaterialDialog.Builder(activity)
                    .title(title)
                    .content(message)
                    .positiveText(confirmation);
            builder.show();
        }
    }

    public static void showAboutAppDialog(final Activity activity) {
        MaterialDialog.Builder builder = new MaterialDialog.Builder(activity);
        builder.customView(R.layout.dialog_about_app, false);
        builder.title("About the App");
        builder.positiveText("AWESOME!");
        MaterialDialog dialog = builder.build();
        View dialogView = dialog.getCustomView();
        if (dialogView != null) {
            TextView tvVersion = (TextView) dialogView.findViewById(R.id.tv_version_name);
            TextView tvCompany = (TextView) dialogView.findViewById(R.id.tv_company_name);
            tvVersion.setText("Version " + BuildConfig.VERSION_NAME);
            tvCompany.setText("Created by AppTastic");
        }
        if (!activity.isFinishing()) {
            dialog.show();
        }
    }
}
