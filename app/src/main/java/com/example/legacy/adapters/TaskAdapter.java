package com.example.legacy.adapters;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.legacy.R;
import com.example.legacy.models.Task;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task> {

    public interface Listener {
        void onToggle(long id, boolean done);
        void onDelete(long id);
    }

    private final Listener listener;

    public TaskAdapter(@NonNull Context context, @NonNull List<Task> tasks, Listener listener) {
        super(context, 0, tasks);
        this.listener = listener;
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(getContext())
                    .inflate(R.layout.item_task, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Task task = getItem(position);
        if (task == null) return convertView;

        // Prevent the CheckBox listener firing during recycling
        holder.checkDone.setOnCheckedChangeListener(null);
        holder.checkDone.setChecked(task.isDone());
        holder.textTitle.setText(task.getTitle());

        // Strikethrough when done
        int flags = holder.textTitle.getPaintFlags();
        if (task.isDone()) {
            holder.textTitle.setPaintFlags(flags | Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textTitle.setAlpha(0.45f);
        } else {
            holder.textTitle.setPaintFlags(flags & ~Paint.STRIKE_THRU_TEXT_FLAG);
            holder.textTitle.setAlpha(1f);
        }

        holder.checkDone.setOnCheckedChangeListener(
                (btn, isChecked) -> listener.onToggle(task.getId(), isChecked));
        holder.btnDelete.setOnClickListener(v -> listener.onDelete(task.getId()));

        return convertView;
    }

    static class ViewHolder {
        final CheckBox checkDone;
        final TextView textTitle;
        final TextView btnDelete;

        ViewHolder(View v) {
            checkDone  = v.findViewById(R.id.checkbox_done);
            textTitle  = v.findViewById(R.id.text_task_title);
            btnDelete  = v.findViewById(R.id.btn_task_delete);
        }
    }
}