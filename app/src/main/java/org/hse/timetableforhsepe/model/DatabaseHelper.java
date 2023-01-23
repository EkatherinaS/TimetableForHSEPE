package org.hse.timetableforhsepe.model;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import org.hse.timetableforhsepe.model.Converters;
import org.hse.timetableforhsepe.model.GroupEntity;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.model.TimeTableEntity;
import org.hse.timetableforhsepe.view_model.HseDao;

@Database(entities = {GroupEntity.class, TeacherEntity.class, TimeTableEntity.class},
          version = 1,
          exportSchema = false)
@TypeConverters({Converters.class})
public abstract class DatabaseHelper extends RoomDatabase {

    public static final String DATABASE_NAME = "hse_timetable";

    public abstract HseDao hseDao();
}
