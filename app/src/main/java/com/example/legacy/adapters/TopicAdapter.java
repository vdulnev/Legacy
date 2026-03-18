package com.example.legacy.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.GradientDrawable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import com.example.legacy.R;
import com.example.legacy.models.Topic;
import java.util.List;

public class TopicAdapter extends ArrayAdapter<Topic> {

    private final LayoutInflater inflater;

    public TopicAdapter(@NonNull Context context, @NonNull List<Topic> topics) {
        super(context, 0, topics);
        inflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView,
            @NonNull ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = inflater.inflate(R.layout.item_topic, parent, false);
            holder = new ViewHolder();
            holder.textIcon = convertView.findViewById(R.id.text_icon);
            holder.textTitle = convertView.findViewById(R.id.text_title);
            holder.textDescription = convertView.findViewById(R.id.text_description);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        Topic topic = getItem(position);
        if (topic != null) {
            // Set first letter as icon
            String firstLetter = topic.getTitle().substring(0, 1).toUpperCase();
            holder.textIcon.setText(firstLetter);
            holder.textIcon.setTextColor(Color.WHITE);

            // Make circular icon with topic color
            GradientDrawable circle = new GradientDrawable();
            circle.setShape(GradientDrawable.OVAL);
            circle.setColor(Color.parseColor(topic.getColorHex()));
            holder.textIcon.setBackground(circle);

            holder.textTitle.setText(topic.getTitle());
            holder.textDescription.setText(topic.getDescription());
        }

        return convertView;
    }

    static class ViewHolder {
        TextView textIcon;
        TextView textTitle;
        TextView textDescription;
    }
}
