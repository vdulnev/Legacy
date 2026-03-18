package com.example.legacy.activities;

import android.os.Bundle;
import com.example.legacy.R;

public class LayoutsDemoActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_layouts_demo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("XML Layouts");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }
    }

}
