package com.example.legacy.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ScrollView;
import android.widget.TextView;
import com.example.legacy.R;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class LifecycleDemoActivity extends BaseActivity {

    private TextView textState;
    private TextView textLog;
    private ScrollView scrollLog;
    private final StringBuilder log = new StringBuilder();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lifecycle_demo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Activity Lifecycle");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
        textState = findViewById(R.id.text_state);
        textLog = findViewById(R.id.text_log);
        scrollLog = findViewById(R.id.scroll_log);

        Button btnClear = findViewById(R.id.btn_clear);
        btnClear.setOnClickListener(v -> {
            log.setLength(0);
            textLog.setText("");
        });

        appendLog("onCreate", "Activity is being created. Set up UI here.");
        updateState("Created");
    }

    @Override
    protected void onStart() {
        super.onStart();
        appendLog("onStart", "Activity is now visible but not interactive.");
        updateState("Started");
    }

    @Override
    protected void onResume() {
        super.onResume();
        appendLog("onResume", "Activity is in the foreground and interactive.");
        updateState("Resumed (Active)");
    }

    @Override
    protected void onPause() {
        super.onPause();
        appendLog("onPause", "Activity is partially visible. Save lightweight state.");
        updateState("Paused");
    }

    @Override
    protected void onStop() {
        super.onStop();
        appendLog("onStop", "Activity is no longer visible.");
        updateState("Stopped");
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        appendLog("onRestart", "Activity is restarting after being stopped.");
        updateState("Restarting");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        appendLog("onDestroy", "Activity is being destroyed. Release all resources.");
    }

    private void appendLog(String method, String description) {
        String time = new SimpleDateFormat("HH:mm:ss", Locale.getDefault())
                .format(new Date());
        log.insert(0, time + "  " + method + "()\n    → " + description + "\n\n");
        if (textLog != null) {
            textLog.setText(log.toString());
            scrollLog.post(() -> scrollLog.fullScroll(ScrollView.FOCUS_UP));
        }
    }

    private void updateState(String state) {
        if (textState != null) {
            textState.setText("Current State: " + state);
        }
    }

}
