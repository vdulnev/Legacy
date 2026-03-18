package com.example.legacy.activities;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;
import com.example.legacy.R;
import com.example.legacy.database.NoteDbHelper;
import com.example.legacy.models.Note;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class SqliteDemoActivity extends BaseActivity {

    private NoteDbHelper dbHelper;
    private ArrayAdapter<String> listAdapter;
    private List<Note> notes;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sqlite_demo);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("SQLite Database");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        dbHelper = new NoteDbHelper(this);

        EditText editTitle = findViewById(R.id.edit_title);
        EditText editContent = findViewById(R.id.edit_content);
        Button btnSave = findViewById(R.id.btn_save);
        ListView listNotes = findViewById(R.id.list_notes);

        listAdapter = new ArrayAdapter<>(this, android.R.layout.simple_list_item_1);
        listNotes.setAdapter(listAdapter);

        refreshNotes();

        btnSave.setOnClickListener(v -> {
            String title = editTitle.getText().toString().trim();
            String content = editContent.getText().toString().trim();
            if (!title.isEmpty()) {
                dbHelper.insertNote(title, content);
                editTitle.setText("");
                editContent.setText("");
                refreshNotes();
                Toast.makeText(this, "Note saved!", Toast.LENGTH_SHORT).show();
            } else {
                Toast.makeText(this, "Enter a title", Toast.LENGTH_SHORT).show();
            }
        });

        listNotes.setOnItemLongClickListener((parent, view, position, id) -> {
            Note note = notes.get(position);
            dbHelper.deleteNote(note.getId());
            refreshNotes();
            Toast.makeText(this, "Note deleted", Toast.LENGTH_SHORT).show();
            return true;
        });
    }

    private void refreshNotes() {
        notes = dbHelper.getAllNotes();
        listAdapter.clear();
        SimpleDateFormat sdf = new SimpleDateFormat("MMM d, HH:mm", Locale.getDefault());
        for (Note note : notes) {
            listAdapter.add(note.getTitle() + " — "
                    + sdf.format(new Date(note.getTimestamp())));
        }
        listAdapter.notifyDataSetChanged();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        dbHelper.close();
    }

}
