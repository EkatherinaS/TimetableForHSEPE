package org.hse.timetableforhsepe.view;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import org.hse.timetableforhsepe.R;
import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.model.TimeTableEntity;
import org.hse.timetableforhsepe.view_model.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TeacherActivity extends BaseActivity {

    private TextView status;
    private TextView subject;
    private TextView cabinet;
    private TextView corp;
    private TextView group;
    private Spinner spinner;
    ArrayAdapter<Item> adapter;
    public static final String TAG = "TeacherActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher);

        spinner = findViewById(R.id.teacherItemList);
        List<Item> items = new ArrayList<>();
        initItemList(items);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                Log.d(TAG, "selectedItem: " + adapter.getItem(selectedItemPosition));
                showTime(dateTime);
            }

            public void onNothingSelected(AdapterView<?> parent) {
                //
            }
        });

        time = findViewById(R.id.time);
        initTime();

        status = findViewById(R.id.status);
        subject = findViewById(R.id.subject);
        cabinet = findViewById(R.id.cabinet);
        corp = findViewById(R.id.corp);
        group = findViewById(R.id.group);

        View scheduleDay = findViewById(R.id.schedule_day);
        scheduleDay.setOnClickListener(v -> showSchedule(ScheduleType.DAY));
        View scheduleWeek = findViewById(R.id.schedule_week);
        scheduleWeek.setOnClickListener(v -> showSchedule(ScheduleType.WEEK));

        initData();
    }

    private void initData() {
        initDataFromTimeTable(null);
    }

    private void initItemList(final List<Item> items) {
        Observer<? super List<TeacherEntity>> observer = (Observer<List<TeacherEntity>>) list -> {
            List<Item> itemsResult = new ArrayList<>();
            for (TeacherEntity listEntity : list) {
                itemsResult.add(new Item(listEntity.id, listEntity.fio));
            }
            adapter.clear();
            adapter.addAll(itemsResult);
        };
        mainViewModel.getTeachers().observe(this, observer);
    }

    @Override
    protected void showTime(Date dateTime) {
        super.showTime(dateTime);
        if (getSelectedItem() != null) {
            Observer<? super List<FullTimeTableEntity>> observer = (Observer<List<FullTimeTableEntity>>) list -> {
                if (!list.isEmpty()) {
                    Log.d(TAG, list.get(0).timeTableEntity.subName + " " + list.get(0).groupEntity.name);
                    initDataFromTimeTable(list.get(0));
                }
                else {
                    initDataFromTimeTable(null);
                }
            };
            mainViewModel.getLessonByTeacherId(getSelectedItem().getId(), dateTime).observe(this, observer);
        }
    }

    private Item getSelectedItem() {
        return (Item) spinner.getSelectedItem();
    }

    private void initDataFromTimeTable(FullTimeTableEntity timeTableWithGroupEntity) {
        if (timeTableWithGroupEntity == null) {
            status.setText(R.string.stubStatus);

            subject.setText(R.string.stubSubject);
            cabinet.setText(R.string.stubCabinet);
            corp.setText(R.string.stubCorp);
            group.setText(R.string.stubGroup);
            return;
        }
        status.setText(R.string.stubStatusRunning);
        TimeTableEntity timeTableEntity = timeTableWithGroupEntity.timeTableEntity;

        subject.setText(timeTableEntity.subName);
        cabinet.setText(timeTableEntity.cabinet);
        corp.setText(timeTableEntity.corp);
        group.setText(timeTableWithGroupEntity.groupEntity.name);

    }

    private void showSchedule(ScheduleType type) {
        Object selectedItem = spinner.getSelectedItem();
        if (!(selectedItem instanceof Item)) {
            return;
        }
        showScheduleImpl(ScheduleMode.PROFESSOR, type, (Item) selectedItem);
    }
}
