package org.hse.timetableforhsepe.view;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.lifecycle.Observer;

import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.model.GroupEntity;
import org.hse.timetableforhsepe.R;
import org.hse.timetableforhsepe.model.TimeTableEntity;
import org.hse.timetableforhsepe.model.TimeTableWithTeacherEntity;
import org.hse.timetableforhsepe.view_model.Item;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class StudentActivity extends BaseActivity {

    private TextView status;
    private TextView subject;
    private TextView cabinet;
    private TextView corp;
    private TextView teacher;
    private Spinner spinner;
    ArrayAdapter<Item> adapter;
    public static final String TAG = "StudentActivity";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student);

        spinner = findViewById(R.id.groupItemList);
        List<Item> items = new ArrayList<>();
        initGroupList(items);

        adapter = new ArrayAdapter<>(this, android.R.layout.simple_spinner_item, items);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

        spinner.setAdapter(adapter);

        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            public void onItemSelected(AdapterView<?> parent, View itemSelected, int selectedItemPosition, long selectedId) {
                Object item = adapter.getItem(selectedItemPosition);
                initTime();
                Log.d(TAG, "selectedItem: " + item);
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
        teacher = findViewById(R.id.teacher);

        View scheduleDay = findViewById(R.id.schedule_day);
        scheduleDay.setOnClickListener(v -> showSchedule(ScheduleType.DAY));
        View scheduleWeek = findViewById(R.id.schedule_week);
        scheduleWeek.setOnClickListener(v -> showSchedule(ScheduleType.WEEK));

        initData();
    }

    private void initData() {
        initDataFromTimeTable(null);
    }

    private void initGroupList(final List<Item> items) {
        Observer<? super List<GroupEntity>> observer = (Observer<List<GroupEntity>>) list -> {
            List<Item> groupsResult = new ArrayList<>();
            for (GroupEntity listEntity : list) {
                groupsResult.add(new Item(listEntity.id, listEntity.name));
            }
            adapter.clear();
            adapter.addAll(groupsResult);
        };
        mainViewModel.getGroups().observe(this, observer);
    }

    @Override
    protected void showTime(Date dateTime) {
        super.showTime(dateTime);
        if (getSelectedItem() != null) {
            Observer<? super List<FullTimeTableEntity>> observer = (Observer<List<FullTimeTableEntity>>) list -> {
                if (!list.isEmpty()) {
                    Log.d(TAG, list.get(0).timeTableEntity.subName + " " + list.get(0).teacherEntity.fio);
                    initDataFromTimeTable(list.get(0));
                }
            };
            mainViewModel.getLessonByGroupId(getSelectedItem().getId(), currentTime).observe(this, observer);
        }
    }

    private Item getSelectedItem() {
        return (Item) spinner.getSelectedItem();
    }

    private void initDataFromTimeTable(FullTimeTableEntity timeTableWithTeacherEntity) {
        if (timeTableWithTeacherEntity == null) {
            status.setText(R.string.stubStatus);

            subject.setText(R.string.stubSubject);
            cabinet.setText(R.string.stubCabinet);
            corp.setText(R.string.stubCorp);
            teacher.setText(R.string.stubProfessor);
            return;
        }
        status.setText(R.string.stubStatusRunning);
        TimeTableEntity timeTableEntity = timeTableWithTeacherEntity.timeTableEntity;

        subject.setText(timeTableEntity.subName);
        cabinet.setText(timeTableEntity.cabinet);
        corp.setText(timeTableEntity.corp);
        teacher.setText(timeTableWithTeacherEntity.teacherEntity.fio);

    }

    private void showSchedule(ScheduleType type) {
        Object selectedItem = spinner.getSelectedItem();
        if (!(selectedItem instanceof Item)) {
            return;
        }
        showScheduleImpl(ScheduleMode.STUDENT, type, (Item) selectedItem);
    }
}
