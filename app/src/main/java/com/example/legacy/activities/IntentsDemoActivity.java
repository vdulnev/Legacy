package com.example.legacy.activities;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import com.example.legacy.R;

public class IntentsDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_intents_demo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Intents");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Explicit intent → Lifecycle demo
        Button btnExplicit = findViewById(R.id.btn_explicit);
        btnExplicit.setOnClickListener(v -> {
            Intent intent = new Intent(this, LifecycleDemoActivity.class);
            startActivity(intent);
        });

        // Implicit: open browser
        Button btnBrowser = findViewById(R.id.btn_browser);
        btnBrowser.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_VIEW,
                    Uri.parse("https://developer.android.com"));
            startActivity(intent);
        });

        // Implicit: share text
        Button btnShare = findViewById(R.id.btn_share);
        btnShare.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_SEND);
            intent.setType("text/plain");
            intent.putExtra(Intent.EXTRA_TEXT, "Learning Legacy Android Development!");
            startActivity(Intent.createChooser(intent, "Share via"));
        });

        // Implicit: dial
        Button btnDial = findViewById(R.id.btn_dial);
        btnDial.setOnClickListener(v -> {
            Intent intent = new Intent(Intent.ACTION_DIAL,
                    Uri.parse("tel:+15555555555"));
            startActivity(intent);
        });

        // Send data to self (show received label)
        EditText editName = findViewById(R.id.edit_name);
        Button btnSend = findViewById(R.id.btn_send_data);
        TextView textReceived = findViewById(R.id.text_received);

        // Check if we received data from a previous call
        String received = getIntent().getStringExtra("received_name");
        if (received != null) {
            textReceived.setText("Received: " + received);
        }

        btnSend.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            if (!name.isEmpty()) {
                // Re-launch self with extra data to demonstrate data passing
                Intent intent = new Intent(this, IntentsDemoActivity.class);
                intent.putExtra("received_name", name);
                startActivity(intent);
            } else {
                Toast.makeText(this, "Enter a name first", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
