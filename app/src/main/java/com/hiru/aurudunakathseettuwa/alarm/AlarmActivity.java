package com.hiru.aurudunakathseettuwa.alarm;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.Button;
import android.widget.TextView;

import com.hiru.aurudunakathseettuwa.R;

public class AlarmActivity extends Activity {
    private MediaPlayer mediaPlayer;
    private final Handler handler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_alarm);

        TextView eventTitle = findViewById(R.id.event_title);
        TextView eventDescription = findViewById(R.id.event_description);
        Button stopAlarmButton = findViewById(R.id.stop_alarm);

        String eventName = getIntent().getStringExtra("EVENT_NAME");
        String eventDesc = getIntent().getStringExtra("EVENT_DESCRIPTION");

        eventTitle.setText(eventName);
        eventDescription.setText(eventDesc);

        mediaPlayer = MediaPlayer.create(this, R.raw.alarm_sound);
        if (mediaPlayer != null) {
            mediaPlayer.setLooping(true);
            mediaPlayer.start();
        }

        handler.postDelayed(this::stopAlarm, 60000);

        stopAlarmButton.setOnClickListener(v -> stopAlarm());
    }

    private void stopAlarm() {
        if (mediaPlayer != null) {
            if (mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
            }
            mediaPlayer.release();
            mediaPlayer = null;
        }
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        stopAlarm();
    }
}
