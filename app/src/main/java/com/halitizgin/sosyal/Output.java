package com.halitizgin.sosyal;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;

import javax.security.auth.callback.Callback;

public class Output {

    public static ProgressDialog showLoading(Context context, String title, String description, Boolean isCancelable)
    {
        ProgressDialog progress = new ProgressDialog(context);
        progress.setTitle(title);
        progress.setMessage(description);
        progress.setCancelable(isCancelable); // disable dismiss by tapping outside of the dialog
        return progress;
    }

    public static AlertDialog.Builder showAlert(Context context, String title, String message, String buttonTitle)
    {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title);
        builder.setMessage(message);
        builder.setNegativeButton(buttonTitle, new DialogInterface.OnClickListener(){
            public void onClick(DialogInterface dialog, int id) {

            }
        });

        return builder;
    }
}
