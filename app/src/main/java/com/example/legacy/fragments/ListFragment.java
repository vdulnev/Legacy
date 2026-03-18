package com.example.legacy.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.example.legacy.R;

public class ListFragment extends Fragment {

    public interface OnItemSelectedListener {
        void onItemSelected(String title, String body);
    }

    private OnItemSelectedListener listener;

    private static final String[][] ITEMS = {
        {
            "LinearLayout",
            "Arranges children in a single row or column using orientation attribute."
        },
        {
            "RelativeLayout",
            "Positions children relative to each other or to the parent container."
        },
        {
            "ConstraintLayout",
            "Flexible layout that uses constraints to position views. Replaces RelativeLayout."
        },
        {
            "FrameLayout",
            "Designed to display a single child. Useful as a container for Fragments."
        },
        {
            "ScrollView",
            "A layout container for a view hierarchy that can be scrolled vertically."
        },
    };

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach(context);
        if (context instanceof OnItemSelectedListener) {
            listener = (OnItemSelectedListener) context;
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
            @Nullable ViewGroup container,
            @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_list, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ListView listView = view.findViewById(R.id.list_items);
        String[] titles = new String[ITEMS.length];
        for (int i = 0; i < ITEMS.length; i++) {
            titles[i] = ITEMS[i][0];
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireContext(),
                android.R.layout.simple_list_item_1, titles);
        listView.setAdapter(adapter);

        listView.setOnItemClickListener((parent, v, position, id) -> {
            if (listener != null) {
                listener.onItemSelected(ITEMS[position][0], ITEMS[position][1]);
            }
        });
    }

    @Override
    public void onDetach() {
        super.onDetach();
        listener = null;
    }
}
