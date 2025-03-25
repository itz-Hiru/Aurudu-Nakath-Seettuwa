package com.hiru.aurudunakathseettuwa;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import android.view.View;
import android.widget.TextView;

import com.hiru.aurudunakathseettuwa.alarm.AlarmReceiver;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class HomeSinhalaActivity extends AppCompatActivity {

    private TextView daysLeftText, hoursLeftText, minutesLeftText, secondsLeftText;
    private TextView nextNakathaText, nakathaDescriptionText;
    private CountDownTimer countDownTimer;
    private int currentEventIndex = 0;

    private final String[] eventDates = {
            "2025.03.25 16:18",
            "2025.04.13 00:00",
            "2025.04.13 20:57",
            "2025.04.14 03:21",
            "2025.04.14 04:04",
            "2025.04.14 06:44",
            "2025.04.16 09:04",
            "2025.04.17 09:03",
            "2025.05.01 00:00"
    };

    private final int[] nakathaNames = {
            R.string.nakatha01_header,
            R.string.nakatha02_header,
            R.string.nakatha04_header,
            R.string.nakatha03_header,
            R.string.nakatha05_header,
            R.string.nakatha06_header,
            R.string.nakatha07_header,
            R.string.nakatha08_header,
            R.string.nakatha01_header
    };

    private final int[] nakathaDescriptions = {
            R.string.nakatha01_description,
            R.string.nakatha02_description,
            R.string.nakatha04_description,
            R.string.nakatha03_description,
            R.string.nakatha05_description,
            R.string.nakatha06_description,
            R.string.nakatha07_description,
            R.string.nakatha08_description,
            R.string.nakatha01_description
    };

    private final int[] nakathaImages = {
            R.drawable.nakatha_01_img,
            R.drawable.nakatha_02_img,
            R.drawable.nakatha_04_img,
            R.drawable.nakatha_03_img,
            R.drawable.nakatha_05_img,
            R.drawable.nakatha_06_img,
            R.drawable.nakatha_07_img,
            R.drawable.nakatha_08_img,
            R.drawable.nakatha_01_img
    };

    private final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy.MM.dd HH:mm", Locale.getDefault());

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.home_sinhala);

        View sinhalaLayout = findViewById(R.id.sinhala_layout);
        if (sinhalaLayout != null) {
            ViewCompat.setOnApplyWindowInsetsListener(sinhalaLayout, (v, insets) -> {
                Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
                v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
                return insets;
            });
        }

        daysLeftText = findViewById(R.id.days_left);
        hoursLeftText = findViewById(R.id.hours_left);
        minutesLeftText = findViewById(R.id.minutes_left);
        secondsLeftText = findViewById(R.id.seconds_left);
        nextNakathaText = findViewById(R.id.find_next_nakatha);
        nakathaDescriptionText = findViewById(R.id.nakatha_description);

        startNextCountdown();
    }

    private void startNextCountdown() {
        long currentTime = System.currentTimeMillis();

        while (currentEventIndex < eventDates.length) {
            try {
                Date eventDate = dateFormat.parse(eventDates[currentEventIndex]);
                if (eventDate != null && eventDate.getTime() > currentTime) {
                    updateNakathaInfo(currentEventIndex);
                    startCountdown(eventDate.getTime());
                    return;
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
            currentEventIndex++;
        }

        updateCountdownUI(0, 0, 0, 0);
        nextNakathaText.setText(getString(R.string.no_more_nakathas));
        nakathaDescriptionText.setText(getString(R.string.default_nakatha_description));
    }

    private void startCountdown(long targetTime) {
        long timeDifference = targetTime - System.currentTimeMillis();

        countDownTimer = new CountDownTimer(timeDifference, 1000) {
            @Override
            public void onTick(long millisUntilFinished) {
                long days = millisUntilFinished / (24 * 60 * 60 * 1000);
                long hours = (millisUntilFinished % (24 * 60 * 60 * 1000)) / (60 * 60 * 1000);
                long minutes = (millisUntilFinished % (60 * 60 * 1000)) / (60 * 1000);
                long seconds = (millisUntilFinished % (60 * 1000)) / 1000;

                updateCountdownUI(days, hours, minutes, seconds);
            }

            @Override
            public void onFinish() {
                updateCountdownUI(0, 0, 0, 0);

                String eventName = getString(nakathaNames[currentEventIndex]);
                String eventDescription = getString(nakathaDescriptions[currentEventIndex]);

                scheduleAlarm(eventDates[currentEventIndex], eventName, eventDescription, nakathaImages[currentEventIndex]);

                currentEventIndex++;
                startNextCountdown();
            }
        }.start();
    }

    private void updateNakathaInfo(int index) {
        nextNakathaText.setText(getString(nakathaNames[index]));
        nakathaDescriptionText.setText(getString(nakathaDescriptions[index]));
    }

    private void updateCountdownUI(long days, long hours, long minutes, long seconds) {
        daysLeftText.setText(String.valueOf(days));
        hoursLeftText.setText(String.format("%02d", hours));
        minutesLeftText.setText(String.format("%02d", minutes));
        secondsLeftText.setText(String.format("%02d", seconds));
    }

    private void scheduleAlarm(String eventDate, String eventName, String eventDescription, int eventImage) {
        AlarmManager alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        if (alarmManager == null) return;

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
            if (!alarmManager.canScheduleExactAlarms()) {
                Intent intent = new Intent(android.provider.Settings.ACTION_REQUEST_SCHEDULE_EXACT_ALARM);
                startActivity(intent);
                return;
            }
        }

        Intent intent = new Intent(this, AlarmReceiver.class);
        intent.putExtra("EVENT_NAME", eventName);
        intent.putExtra("EVENT_DESCRIPTION", eventDescription);
        intent.putExtra("EVENT_IMAGE", eventImage);

        PendingIntent pendingIntent = PendingIntent.getBroadcast(
                this, currentEventIndex, intent, PendingIntent.FLAG_UPDATE_CURRENT | PendingIntent.FLAG_IMMUTABLE
        );

        try {
            Date date = dateFormat.parse(eventDate);
            if (date != null) {
                long alarmTime = date.getTime();
                alarmManager.setExact(AlarmManager.RTC_WAKEUP, alarmTime, pendingIntent);

                new android.os.Handler().postDelayed(() -> {
                    alarmManager.cancel(pendingIntent);
                }, 60000);
            }
        } catch (ParseException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (countDownTimer != null) {
            countDownTimer.cancel();
        }
    }
}
