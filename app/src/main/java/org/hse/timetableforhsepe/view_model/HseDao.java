package org.hse.timetableforhsepe.view_model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.model.GroupEntity;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.model.TimeTableEntity;
import org.hse.timetableforhsepe.model.TimeTableWithGroupEntity;
import org.hse.timetableforhsepe.model.TimeTableWithTeacherEntity;

import java.util.List;

@Dao
public interface HseDao {

    @Query("SELECT * FROM 'group'")
    LiveData<List<GroupEntity>> getAllGroup();

    @Insert
    void insertGroup(List<GroupEntity> data);

    @Delete
    void delete(GroupEntity data);


    @Query("SELECT * FROM 'teacher'")
    LiveData<List<TeacherEntity>> getAllTeacher();

    @Insert
    void insertTeacher(List<TeacherEntity> data);

    @Delete
    void delete(TeacherEntity data);


    @Query("SELECT * FROM time_table")
    LiveData<List<FullTimeTableEntity>> getAllTimeTable();

    @Transaction
    @Query("SELECT * FROM time_table")
    LiveData<List<TimeTableWithTeacherEntity>> getTimeTableTeacher();

    @Transaction
    @Query("SELECT * FROM time_table")
    LiveData<List<TimeTableWithGroupEntity>> getTimeTableGroup();

    @Insert
    void insertTimeTable(List<TimeTableEntity> data);

}
