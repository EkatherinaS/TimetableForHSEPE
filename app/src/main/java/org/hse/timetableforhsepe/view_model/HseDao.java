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

import java.util.Date;
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

    @Query("SELECT * FROM time_table WHERE teacher_id = :id AND time_start >= :start_day AND time_start <= :end_day")
    LiveData<List<FullTimeTableEntity>> getTimeTableByTeacherId(Integer id, Date start_day, Date end_day);

    @Query("SELECT * FROM time_table WHERE group_id = :id AND time_start >= :start_day AND time_start <= :end_day")
    LiveData<List<FullTimeTableEntity>> getTimeTableByGroupId(Integer id, Date start_day, Date end_day);

    @Transaction
    @Query("SELECT * FROM time_table")
    LiveData<List<TimeTableWithTeacherEntity>> getTimeTableTeacher();

    @Transaction
    @Query("SELECT * FROM time_table")
    LiveData<List<TimeTableWithGroupEntity>> getTimeTableGroup();

    @Insert
    void insertTimeTable(List<TimeTableEntity> data);

}
