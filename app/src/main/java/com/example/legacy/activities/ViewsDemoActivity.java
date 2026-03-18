package com.example.legacy.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.SeekBar;
import android.widget.Spinner;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;
import com.example.legacy.R;

public class ViewsDemoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_views_demo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Common Views");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // Set up Spinner
        Spinner spinner = findViewById(R.id.spinner_demo);
        String[] items = {
            "Select a language...", "Java", "Kotlin", "Python", "Swift", "JavaScript"
        };
        ArrayAdapter<String> spinnerAdapter = new ArrayAdapter<>(
                this, android.R.layout.simple_spinner_item, items);
        spinnerAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        spinner.setAdapter(spinnerAdapter);

        // Set up SeekBar
        SeekBar seekBar = findViewById(R.id.seekbar_demo);
        TextView seekValue = findViewById(R.id.text_seekbar_value);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar s, int progress, boolean fromUser) {
                seekValue.setText("Value: " + progress);
            }

            @Override
            public void onStartTrackingTouch(SeekBar s) {
            }

            @Override
            public void onStopTrackingTouch(SeekBar s) {
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
