package com.example.legacy.activities;

// Layer 4 — View (Activity)
// The Activity only knows about the ViewModel. It:
//   1. Observes LiveData and re-renders whenever data changes.
//   2. Forwards user actions (add / toggle / delete) to the ViewModel.
//   3. Never touches the Repository or DB directly.

import android.os.Bundle;
import android.widget.*;
import androidx.lifecycle.ViewModelProvider;
import com.example.legacy.R;
import com.example.legacy.adapters.TaskAdapter;
import com.example.legacy.models.Task;
import com.example.legacy.viewmodel.TaskViewModel;
import java.util.ArrayList;

public class MvvmDemoActivity extends BaseActivity implements TaskAdapter.Listener {

    private TaskViewModel viewModel;
    private TaskAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mvvm_demo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("MVVM Architecture");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        // ViewModel survives rotation — ViewModelProvider returns the same
        // instance if it already exists for this Activity.
        viewModel = new ViewModelProvider(this).get(TaskViewModel.class);

        ListView listView   = findViewById(R.id.list_tasks);
        EditText editTitle  = findViewById(R.id.edit_task_title);
        Button   btnAdd     = findViewById(R.id.btn_add_task);

        adapter = new TaskAdapter(this, new ArrayList<>(), this);
        listView.setAdapter(adapter);

        // Observe: re-render the list whenever the ViewModel pushes a new snapshot
        viewModel.getTasks().observe(this, tasks -> {
            adapter.clear();
            adapter.addAll(tasks);
            adapter.notifyDataSetChanged();
        });

        btnAdd.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            if (!title.isEmpty()) {
                viewModel.addTask(title);   // ViewModel → Repository → DB
                editTitle.setText("");
            } else {
                Toast.makeText(this, "Enter a task title", Toast.LENGTH_SHORT).show();
            }
        });
    }

    // TaskAdapter.Listener callbacks — forward to ViewModel, never touch DB here

    @Override
    public void onToggle(long id, boolean done) {
        viewModel.toggleDone(id, done);
    }

    @Override
    public void onDelete(long id) {
        viewModel.deleteTask(id);
    }
}