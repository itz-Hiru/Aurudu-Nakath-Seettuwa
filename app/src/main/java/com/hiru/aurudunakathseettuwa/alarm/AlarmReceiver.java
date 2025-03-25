package com.hiru.aurudunakathseettuwa.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String eventName = intent.getStringExtra("EVENT_NAME");
        String eventDescription = intent.getStringExtra("EVENT_DESCRIPTION");

        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.putExtra("EVENT_NAME", eventName);
        alarmIntent.putExtra("EVENT_DESCRIPTION", eventDescription);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(alarmIntent);
    }
}
