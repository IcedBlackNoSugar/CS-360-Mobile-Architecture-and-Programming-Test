package com.zybooks.projecttwo;

import android.widget.Toast;
import androidx.appcompat.app.AlertDialog;


public class AD_SMSNotifications {

    public static AlertDialog smsButton(final Inventory context){
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle("SMS Permission Needed")
                .setCancelable(false)
                .setMessage(R.string.ad_sms_permission)
                .setPositiveButton("Enable", (dialog, arg1) ->{
                    Toast.makeText(context, "SMS Alerts Enabled", Toast.LENGTH_LONG).show();
                    Inventory.AllowSendSMS();
                    dialog.cancel();
                })
                .setNegativeButton("Disable",(dialog, arg1) ->{
                    Toast.makeText(context, "SMS Alerts Disabled", Toast.LENGTH_LONG).show();
                    Inventory.DenySendSMS();
                    dialog.cancel();
                });

        return builder.create();
    }
}
