package com.example.legacy;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import com.example.legacy.activities.LayoutsDemoActivity;
import com.example.legacy.activities.MvvmDemoActivity;
import com.example.legacy.activities.LifecycleDemoActivity;
import com.example.legacy.activities.FragmentsDemoActivity;
import com.example.legacy.activities.IntentsDemoActivity;
import com.example.legacy.activities.RecyclerDemoActivity;
import com.example.legacy.activities.SqliteDemoActivity;
import com.example.legacy.activities.ViewsDemoActivity;
import com.example.legacy.adapters.TopicAdapter;
import com.example.legacy.models.Topic;
import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Legacy Android Learning");
        }

        ListView listView = findViewById(R.id.list_topics);
        List<Topic> topics = buildTopics();
        TopicAdapter adapter = new TopicAdapter(this, topics);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, view, position, id) -> {
            Topic topic = topics.get(position);
            Intent intent = new Intent(this, topic.getActivityClass());
            startActivity(intent);
        });
    }

    private List<Topic> buildTopics() {
        List<Topic> list = new ArrayList<>();
        list.add(new Topic("XML Layouts",
                "LinearLayout · RelativeLayout · ConstraintLayout",
                "#E53935", LayoutsDemoActivity.class));
        list.add(new Topic("Common Views",
                "Button · EditText · CheckBox · Spinner · SeekBar",
                "#8E24AA", ViewsDemoActivity.class));
        list.add(new Topic("Intents",
                "Explicit & Implicit · Data passing · startActivityForResult",
                "#1E88E5", IntentsDemoActivity.class));
        list.add(new Topic("Activity Lifecycle",
                "onCreate · onStart · onResume · onPause · onStop · onDestroy",
                "#43A047", LifecycleDemoActivity.class));
        list.add(new Topic("RecyclerView",
                "Adapter · ViewHolder pattern · LayoutManager · Click handling",
                "#FB8C00", RecyclerDemoActivity.class));
        list.add(new Topic("Fragments",
                "Fragment transactions · BackStack · Fragment communication",
                "#00ACC1", FragmentsDemoActivity.class));
        list.add(new Topic("SQLite Database",
                "SQLiteOpenHelper · CRUD · Cursor · Contract class",
                "#6D4C41", SqliteDemoActivity.class));
        list.add(new Topic("MVVM Architecture",
                "ViewModel · LiveData · Repository · DB API layers",
                "#37474F", MvvmDemoActivity.class));
        return list;
    }
}
