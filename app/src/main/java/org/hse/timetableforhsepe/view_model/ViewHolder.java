package org.hse.timetableforhsepe.view_model;
import android.content.Context;
import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import org.hse.timetableforhsepe.R;

public class ViewHolder extends RecyclerView.ViewHolder {

    private Context context;
    private OnItemClick onItemClick;
    private TextView start;
    private TextView end;
    private TextView type;
    private TextView name;
    private TextView place;
    private TextView addInfo;

    public ViewHolder(View itemView, Context context, OnItemClick onItemClick) {
        super(itemView);
        this.context = context;
        this.onItemClick = onItemClick;
        start = itemView.findViewById(R.id.start);
        end = itemView.findViewById(R.id.end);
        type = itemView.findViewById(R.id.type);
        name = itemView.findViewById(R.id.name);
        place = itemView.findViewById(R.id.place);
        addInfo = itemView.findViewById(R.id.additional_info);
    }

    public void bind(final ScheduleItem data) {
        start.setText(data.getStart());
        end.setText(data.getEnd());
        type.setText(data.getType());
        name.setText(data.getName());
        place.setText(data.getPlace());
        addInfo.setText(data.getAddInfo());
    }
}
