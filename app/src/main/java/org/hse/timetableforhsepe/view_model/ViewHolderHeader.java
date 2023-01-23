package org.hse.timetableforhsepe.view_model;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.hse.timetableforhsepe.R;

public class ViewHolderHeader extends RecyclerView.ViewHolder {
    private Context context;
    private OnItemClick onItemClick;
    private TextView title;

    public ViewHolderHeader(View itemView, Context context, OnItemClick onItemClick) {
        super(itemView);
        this.context = context;
        this.onItemClick = onItemClick;
        title = itemView.findViewById(R.id.header_title);
    }

    public void bind(final ScheduleItemHeader data) {
        title.setText(data.getTitle());
    }
}
