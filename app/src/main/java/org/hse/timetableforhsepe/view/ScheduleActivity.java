package org.hse.timetableforhsepe.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.hse.timetableforhsepe.model.Converters;
import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.view_model.ItemAdapter;
import org.hse.timetableforhsepe.R;
import org.hse.timetableforhsepe.view_model.ScheduleItem;
import org.hse.timetableforhsepe.view_model.ScheduleItemHeader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;


public class ScheduleActivity extends BaseActivity {

    public static String ARG_ID = "ARG_ID";
    public static String ARG_TYPE = "ARG_TYPE";
    public static String ARG_MODE = "ARG_MODE";
    public static String ARG_NAME = "ARG_NAME";
    private static final int DEFAULT_ID = 0;
    private static String TAG = "ScheduleActivity";

    private ItemAdapter adapter;
    private ScheduleType type;
    private ScheduleMode mode;
    private int id;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        type = (ScheduleType) getIntent().getSerializableExtra(ARG_TYPE);
        mode = (ScheduleMode) getIntent().getSerializableExtra(ARG_MODE);
        id = getIntent().getIntExtra(ARG_ID, DEFAULT_ID);
        String name = getIntent().getStringExtra(ARG_NAME);

        TextView title = findViewById(R.id.title);
        title.setText(name);

        RecyclerView recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ItemAdapter(this::onScheduleItemClick);
        recyclerView.setAdapter(adapter);

        initTimetableObserver();
    }

    private void onScheduleItemClick(ScheduleItem scheduleItem) { }

    protected void initTimetableObserver(){
        Observer<Date> observer = new Observer<Date>(){
            @Override
            public void onChanged(Date date) {
                filterItem(date);
            }
        };
        mainViewModel.getDateTime().observe(this, observer);
    }

    private void filterItem(Date dateTime) {

        Observer<? super List<FullTimeTableEntity>> observer = (Observer<List<FullTimeTableEntity>>) list -> {

            if (type == BaseActivity.ScheduleType.DAY) {
                List<ScheduleItem> dayItems = new ArrayList<>();
                ScheduleItemHeader header = new ScheduleItemHeader();
                header.setTitle(Converters.dateToWeekdayDateFormat(new Date()));
                dayItems.add(header);
                for (FullTimeTableEntity listEntity : list) {
                    dayItems.add(new ScheduleItem(listEntity, mode));
                }
                initData(dayItems);
            }
            else {
                List<ScheduleItem> weekItems = new ArrayList<>();
                ScheduleItemHeader header = new ScheduleItemHeader();
                header.setTitle("");
                for (FullTimeTableEntity listEntity : list) {
                    if (!header.getTitle().equals(
                            Converters.dateToWeekdayDateFormat(listEntity.timeTableEntity.timeStart))) {
                        header = new ScheduleItemHeader();
                        header.setTitle(Converters.dateToWeekdayDateFormat(listEntity.timeTableEntity.timeStart));
                        weekItems.add(header);
                    }
                    weekItems.add(new ScheduleItem(listEntity, mode));
                }
                initData(weekItems);
            }
        };

        if (mode == ScheduleMode.STUDENT) {
            mainViewModel.getTimetableByGroupId(id, type, dateTime).observe(this, observer);
        }
        if (mode == ScheduleMode.PROFESSOR) {
            mainViewModel.getTimetableByTeacherId(id, type, dateTime).observe(this, observer);
        }
    }

    protected void initData(List<ScheduleItem> items) {
        this.adapter.setDataList(items);
    }
}
