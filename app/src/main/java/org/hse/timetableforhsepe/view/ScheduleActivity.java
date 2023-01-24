package org.hse.timetableforhsepe.view;

import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import org.hse.timetableforhsepe.model.Converters;
import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.model.TimeTableEntity;
import org.hse.timetableforhsepe.model.TimeTableWithTeacherEntity;
import org.hse.timetableforhsepe.view_model.HseRepository;
import org.hse.timetableforhsepe.view_model.ItemAdapter;
import org.hse.timetableforhsepe.R;
import org.hse.timetableforhsepe.view_model.MainViewModel;
import org.hse.timetableforhsepe.view_model.ScheduleItem;
import org.hse.timetableforhsepe.view_model.ScheduleItemHeader;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.ConcurrentModificationException;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.Objects;


public class ScheduleActivity extends BaseActivity {

    public static String ARG_ID = "ARG_ID";
    public static String ARG_TYPE = "ARG_TYPE";
    public static String ARG_MODE = "ARG_MODE";
    public static String ARG_NAME = "ARG_NAME";
    private static final int DEFAULT_ID = 0;

    private ItemAdapter adapter;
    private BaseActivity.ScheduleType type;
    private BaseActivity.ScheduleMode mode;
    private int id;
    private String name;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_schedule);

        type = (BaseActivity.ScheduleType) getIntent().getSerializableExtra(ARG_TYPE);
        mode = (BaseActivity.ScheduleMode) getIntent().getSerializableExtra(ARG_MODE);
        id = getIntent().getIntExtra(ARG_ID, DEFAULT_ID);
        name = getIntent().getStringExtra(ARG_NAME);

        TextView title = findViewById(R.id.title);
        title.setText(name);

        RecyclerView recyclerView = findViewById(R.id.listView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.addItemDecoration(new DividerItemDecoration(this, LinearLayoutManager.VERTICAL));
        adapter = new ItemAdapter(this::onScheduleItemClick);
        recyclerView.setAdapter(adapter);

        filterItem();
    }

    private void onScheduleItemClick(ScheduleItem scheduleItem) {
        ;
    }

    private void filterItem() {

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
            mainViewModel.getTimetableByGroupId(id, type).observe(this, observer);
        }
        if (mode == ScheduleMode.PROFESSOR) {
            mainViewModel.getTimetableByTeacherId(id, type).observe(this, observer);
        }

    }

    protected void initData(List<ScheduleItem> items) {
        this.adapter.setDataList(items);
    }
}
