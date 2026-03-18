package com.example.legacy.adapters;

import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.example.legacy.R;
import com.example.legacy.models.Person;
import java.util.ArrayList;
import java.util.List;

public class PersonAdapter extends RecyclerView.Adapter<PersonAdapter.ViewHolder> {

    private static final String[] COLORS = {
        "#E53935", "#8E24AA", "#1E88E5", "#43A047",
        "#FB8C00", "#00ACC1", "#6D4C41", "#3949AB"
    };

    private final List<Person> people = new ArrayList<>();

    public void addPerson(Person person) {
        people.add(person);
        notifyItemInserted(people.size() - 1);
    }

    public List<Person> getPeople() {
        return people;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_person, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Person person = people.get(position);

        // Set initials
        String initial = person.getName().substring(0, 1).toUpperCase();
        holder.textInitials.setText(initial);

        // Circular avatar background
        GradientDrawable circle = new GradientDrawable();
        circle.setShape(GradientDrawable.OVAL);
        circle.setColor(Color.parseColor(COLORS[position % COLORS.length]));
        holder.textInitials.setBackground(circle);

        holder.textName.setText(person.getName());
        holder.textRole.setText(person.getRole());

        holder.btnDelete.setOnClickListener(v -> {
            int pos = holder.getAdapterPosition();
            if (pos != RecyclerView.NO_ID) {
                people.remove(pos);
                notifyItemRemoved(pos);
            }
        });
    }

    @Override
    public int getItemCount() {
        return people.size();
    }

    static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textInitials;
        TextView textName;
        TextView textRole;
        TextView btnDelete;

        ViewHolder(@NonNull View itemView) {
            super(itemView);
            textInitials = itemView.findViewById(R.id.text_initials);
            textName = itemView.findViewById(R.id.text_name);
            textRole = itemView.findViewById(R.id.text_role);
            btnDelete = itemView.findViewById(R.id.btn_delete);
        }
    }
}
