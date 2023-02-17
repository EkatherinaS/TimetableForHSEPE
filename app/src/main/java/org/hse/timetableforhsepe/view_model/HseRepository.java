package org.hse.timetableforhsepe.view_model;

import android.content.Context;

import androidx.lifecycle.LiveData;
import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.model.GroupEntity;
import org.hse.timetableforhsepe.model.RequestBuilder;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.view.BaseActivity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneId;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import okhttp3.Callback;

public class HseRepository {

    private DatabaseManager databaseManager;
    private RequestBuilder requestBuilder;
    private final HseDao dao;
    private Date dayStart;
    private Date dayEnd;

    private void getDates(BaseActivity.ScheduleType type, Date currentTime) {
        if (currentTime != null) {
            LocalDate localDate = currentTime.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
            if (type == BaseActivity.ScheduleType.DAY) {
                dayStart = Date.from(localDate
                        .atStartOfDay(ZoneOffset.systemDefault())
                        .toInstant());
                dayEnd = Date.from(localDate
                        .plusDays(1)
                        .atStartOfDay(ZoneOffset.systemDefault())
                        .toInstant());
            }
            if (type == BaseActivity.ScheduleType.WEEK) {
                if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                    dayStart = Date.from(localDate
                            .atStartOfDay(ZoneOffset.systemDefault())
                            .toInstant());
                } else {
                    dayStart = Date.from(localDate
                            .with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
                            .atStartOfDay(ZoneOffset.systemDefault())
                            .toInstant());
                }
                dayEnd = Date.from(localDate
                        .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                        .atStartOfDay(ZoneOffset.systemDefault())
                        .toInstant());
            }
        }
    }

    public HseRepository(Context context) {
        databaseManager = DatabaseManager.getInstance(context);
        requestBuilder = RequestBuilder.getInstance();
        dao = databaseManager.getHseDao();
    }

    public LiveData<List<FullTimeTableEntity>> getTimetableByGroupId(Integer groupId, BaseActivity.ScheduleType type, Date date) {
        getDates(type, date);
        return dao.getTimeTableByGroupId(groupId, dayStart, dayEnd);
    }

    public LiveData<List<FullTimeTableEntity>> getTimetableByTeacherId(Integer teacherId, BaseActivity.ScheduleType type, Date date) {
        getDates(type, date);
        return dao.getTimeTableByTeacherId(teacherId, dayStart, dayEnd);
    }

    public LiveData<List<FullTimeTableEntity>> getLessonByGroupId(Integer groupId, Date currentTime) {
        return dao.getLessonByGroupId(groupId, currentTime);
    }

    public LiveData<List<FullTimeTableEntity>> getLessonByTeacherId(Integer teacherId, Date currentTime) {
        return dao.getLessonByTeacherId(teacherId, currentTime);
    }

    public LiveData<List<GroupEntity>> getGroups() {
        return dao.getAllGroup();
    }

    public LiveData<List<TeacherEntity>> getTeachers() {
        return dao.getAllTeacher();
    }

    public void getDateTime(Callback callback) {
        requestBuilder.getDateTime(callback);
    }
}
