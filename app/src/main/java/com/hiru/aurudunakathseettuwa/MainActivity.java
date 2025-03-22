package com.hiru.aurudunakathseettuwa;

import android.os.Bundle;
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

        sinhalaBtn.setOnClickListener(v -> setContentView(R.layout.home_sinhala));
        tamilBtn.setOnClickListener(v -> setContentView(R.layout.home_tamil));
    }
}
