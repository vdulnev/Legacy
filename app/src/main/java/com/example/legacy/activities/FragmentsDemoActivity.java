package com.example.legacy.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import com.example.legacy.R;
import com.example.legacy.fragments.DetailFragment;
import com.example.legacy.fragments.ListFragment;

public class FragmentsDemoActivity extends BaseActivity
        implements ListFragment.OnItemSelectedListener {

    private TextView textBackstack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fragments_demo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Fragments");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        textBackstack = findViewById(R.id.text_backstack);

        Button btnShowList = findViewById(R.id.btn_show_list);
        Button btnShowDetail = findViewById(R.id.btn_show_detail);

        btnShowList.setOnClickListener(v -> showListFragment());
        btnShowDetail.setOnClickListener(v -> showDetailFragment("Sample Item",
                "This is the detail view. Navigate back to see the list fragment."));

        // Show list fragment by default
        if (savedInstanceState == null) {
            showListFragment();
        }

        updateBackstackCount();
    }

    private void showListFragment() {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, new ListFragment())
                .commit();
        updateBackstackCount();
    }

    public void showDetailFragment(String title, String body) {
        DetailFragment fragment = DetailFragment.newInstance(title, body);
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.fragment_container, fragment)
                .addToBackStack("detail")
                .commit();
        updateBackstackCount();
    }

    private void updateBackstackCount() {
        getSupportFragmentManager().addOnBackStackChangedListener(() -> {
            int count = getSupportFragmentManager().getBackStackEntryCount();
            textBackstack.setText("Backstack entries: " + count);
        });
        textBackstack.setText("Backstack entries: "
                + getSupportFragmentManager().getBackStackEntryCount());
    }

    @Override
    public void onItemSelected(String title, String body) {
        showDetailFragment(title, body);
    }

}
