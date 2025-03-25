package com.hiru.aurudunakathseettuwa.alarm;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.widget.ImageView;
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
        ImageView eventImageView = findViewById(R.id.event_image);
        TextView stopAlarmButton = findViewById(R.id.stop_alarm);

        String eventName = getIntent().getStringExtra("EVENT_NAME");
        String eventDesc = getIntent().getStringExtra("EVENT_DESCRIPTION");
        int eventImage = getIntent().getIntExtra("EVENT_IMAGE", R.drawable.nakatha_01_img);

        eventTitle.setText(eventName);
        eventDescription.setText(eventDesc);
        eventImageView.setImageResource(eventImage);

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
