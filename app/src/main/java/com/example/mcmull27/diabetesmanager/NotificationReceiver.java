package com.example.mcmull27.diabetesmanager;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

public class NotificationReceiver extends BroadcastReceiver {

    String TAG = "NotificationReceiver";

    @Override
    public void onReceive(Context context, Intent intent) {

        Scheduler.showNotification(context, MainActivity.class,
                "Regiment Activity", "You have an upcoming regiment activity");

    }
}
