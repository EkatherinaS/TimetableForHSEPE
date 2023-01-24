package org.hse.timetableforhsepe.view_model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.model.GroupEntity;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.model.TimeTableWithGroupEntity;
import org.hse.timetableforhsepe.model.TimeTableWithTeacherEntity;
import org.hse.timetableforhsepe.view.BaseActivity;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.ZoneOffset;
import java.time.temporal.TemporalAdjusters;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class HseRepository {

    private DatabaseManager databaseManager;
    private HseDao dao;
    private Date dayStart;
    private Date dayEnd;

    private void getDates(BaseActivity.ScheduleType type) {
        if (type == BaseActivity.ScheduleType.DAY) {
            dayStart = Date.from(LocalDate.now()
                                          .atStartOfDay(ZoneOffset.systemDefault())
                                          .toInstant());
            dayEnd = Date.from(LocalDate.now()
                                        .plusDays(1)
                                        .atStartOfDay(ZoneOffset.systemDefault())
                                        .toInstant());
        }
        if (type == BaseActivity.ScheduleType.WEEK) {
            if (Calendar.getInstance().get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
                dayStart = Date.from(LocalDate.now()
                                              .atStartOfDay(ZoneOffset.systemDefault())
                                              .toInstant());
            }
            else {
                dayStart = Date.from(LocalDate.now()
                                              .with(TemporalAdjusters.previous(DayOfWeek.MONDAY))
                                              .atStartOfDay(ZoneOffset.systemDefault())
                                              .toInstant());
            }
            dayEnd = Date.from(LocalDate.now()
                         .with(TemporalAdjusters.next(DayOfWeek.MONDAY))
                         .atStartOfDay(ZoneOffset.systemDefault())
                         .toInstant());
        }
    }

    public HseRepository(Context context) {
        databaseManager = DatabaseManager.getInstance(context);
        dao = databaseManager.getHseDao();
    }

    public LiveData<List<FullTimeTableEntity>> getTimetableByGroupId(Integer groupId, BaseActivity.ScheduleType type) {
        getDates(type);
        return dao.getTimeTableByGroupId(groupId, dayStart, dayEnd);
    }

    public LiveData<List<FullTimeTableEntity>> getTimetableByTeacherId(Integer teacherId, BaseActivity.ScheduleType type) {
        getDates(type);
        return dao.getTimeTableByTeacherId(teacherId, dayStart, dayEnd);
    }

    public LiveData<List<FullTimeTableEntity>> getTimetable() {
        return dao.getAllTimeTable();
    }

    public LiveData<List<GroupEntity>> getGroups() {
        return dao.getAllGroup();
    }

    public LiveData<List<TeacherEntity>> getTeachers() {
        return dao.getAllTeacher();
    }

    public LiveData<List<TimeTableWithTeacherEntity>> getTimeTableWithTeacherByDate(Date date) {
        return dao.getTimeTableTeacher();
    }

    public LiveData<List<TimeTableWithGroupEntity>> getTimeTableWithGroupByDate(Date date) {
        return dao.getTimeTableGroup();
    }
}
