package com.hiru.aurudunakathseettuwa.alarm;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.hiru.aurudunakathseettuwa.R;

public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String eventName = intent.getStringExtra("EVENT_NAME");
        String eventDescription = intent.getStringExtra("EVENT_DESCRIPTION");
        int eventImage = intent.getIntExtra("EVENT_IMAGE", R.drawable.nakatha_01_img);

        Intent alarmIntent = new Intent(context, AlarmActivity.class);
        alarmIntent.putExtra("EVENT_NAME", eventName);
        alarmIntent.putExtra("EVENT_DESCRIPTION", eventDescription);
        alarmIntent.putExtra("EVENT_IMAGE", eventImage);
        alarmIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);

        context.startActivity(alarmIntent);
    }
}
