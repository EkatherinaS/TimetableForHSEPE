package org.hse.timetableforhsepe.view;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import org.hse.timetableforhsepe.model.Converters;
import org.hse.timetableforhsepe.view_model.Item;
import org.hse.timetableforhsepe.view_model.MainViewModel;
import java.util.Date;

public abstract class BaseActivity extends ActionBarActivity {

    protected TextView time;
    protected MainViewModel mainViewModel;
    private String TAG = "BaseActivity";
    protected Date dateTime;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mainViewModel = new ViewModelProvider(this).get(MainViewModel.class);
    }

    protected void initTime(){
        Observer<Date> observer = new Observer<Date>(){
            @Override
            public void onChanged(Date date) {
                dateTime = date;
                showTime(date);
                Log.i(TAG, "initTime: " + date);
            }
        };
        mainViewModel.getDateTime().observe(this, observer);
    }

    protected void showTime(Date dateTime) {
        if (dateTime == null) {
            return;
        }
        time.setText(Converters.dateToSimpleFormat(dateTime));
    }


    public enum ScheduleType {
        DAY,
        WEEK
    }

    public enum ScheduleMode {
        STUDENT,
        PROFESSOR
    }

    protected void showScheduleImpl(ScheduleMode mode, ScheduleType type, Item item) {
        Intent intent = new Intent(this, ScheduleActivity.class);
        intent.putExtra(ScheduleActivity.ARG_ID, item.getId());
        intent.putExtra(ScheduleActivity.ARG_TYPE, type);
        intent.putExtra(ScheduleActivity.ARG_MODE, mode);
        intent.putExtra(ScheduleActivity.ARG_NAME, item.getName());
        startActivity(intent);
    }
}
