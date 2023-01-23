package org.hse.timetableforhsepe.view_model;

import android.content.Context;

import androidx.lifecycle.LiveData;

import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.model.GroupEntity;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.model.TimeTableEntity;
import org.hse.timetableforhsepe.model.TimeTableWithGroupEntity;
import org.hse.timetableforhsepe.model.TimeTableWithTeacherEntity;

import java.util.Date;
import java.util.List;

public class HseRepository {

    private DatabaseManager databaseManager;
    private HseDao dao;

    public HseRepository(Context context) {
        databaseManager = DatabaseManager.getInstance(context);
        dao = databaseManager.getHseDao();
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
