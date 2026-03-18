package com.example.legacy.activities;

import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import com.example.legacy.R;
import com.example.legacy.adapters.PersonAdapter;
import com.example.legacy.models.Person;

public class RecyclerDemoActivity extends AppCompatActivity {

    private PersonAdapter adapter;
    private int personCounter = 1;

    private static final String[] ROLES =
            {"Developer", "Designer", "Manager", "Tester", "Architect"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recycler_demo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("RecyclerView");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        adapter = new PersonAdapter();
        // Add initial sample data
        adapter.addPerson(new Person("Alice Johnson", "Developer"));
        adapter.addPerson(new Person("Bob Smith", "Designer"));
        adapter.addPerson(new Person("Carol White", "Manager"));

        recyclerView.setAdapter(adapter);

        EditText editName = findViewById(R.id.edit_name);
        Button btnAdd = findViewById(R.id.btn_add);

        btnAdd.setOnClickListener(v -> {
            String name = editName.getText().toString().trim();
            if (!name.isEmpty()) {
                String role = ROLES[personCounter % ROLES.length];
                adapter.addPerson(new Person(name, role));
                personCounter++;
                editName.setText("");
                recyclerView.smoothScrollToPosition(adapter.getItemCount() - 1);
            } else {
                Toast.makeText(this, "Enter a name", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }
}
