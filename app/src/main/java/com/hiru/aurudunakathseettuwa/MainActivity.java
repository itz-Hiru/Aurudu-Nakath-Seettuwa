package com.hiru.aurudunakathseettuwa;

import android.content.Intent;
import android.os.Bundle;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.welcome);

        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main), (v, insets) -> {
            Insets systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars());
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom);
            return insets;
        });

        RelativeLayout sinhalaBtn = findViewById(R.id.sinhala_btn);
        RelativeLayout tamilBtn = findViewById(R.id.tamil_btn);
        LinearLayout aboutBtn = findViewById(R.id.about);

        sinhalaBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HomeSinhalaActivity.class);
            startActivity(intent);
        });

        tamilBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, HomeTamilActivity.class);
            startActivity(intent);
        });

        aboutBtn.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, AboutActivity.class);
            startActivity(intent);
        });
    }
}
