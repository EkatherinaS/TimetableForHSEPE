package org.hse.timetableforhsepe.view_model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import org.hse.timetableforhsepe.model.DatabaseHelper;
import org.hse.timetableforhsepe.model.GroupEntity;
import org.hse.timetableforhsepe.model.TeacherEntity;
import org.hse.timetableforhsepe.model.TimeTableEntity;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.Executors;

public class DatabaseManager {

    private final DatabaseHelper db;

    private static DatabaseManager instance;

    public static DatabaseManager getInstance(Context context) {
        if (instance == null) {
            instance = new DatabaseManager(context.getApplicationContext());
        }
        return instance;
    }

    private DatabaseManager(Context context) {
        db = Room.databaseBuilder(context,
                DatabaseHelper.class,
                DatabaseHelper.DATABASE_NAME)
                .addCallback(new RoomDatabase.Callback() {
                    @Override
                    public void onCreate(@NonNull SupportSQLiteDatabase db) {
                        Executors.newSingleThreadScheduledExecutor().execute(new Runnable() {
                            @Override
                            public void run() {
                                initData(context);
                            }
                        });
                    }
                })
                .build();
    }

    public HseDao getHseDao() {
        return db.hseDao();
    }

    private void initData(Context context) {

        List<GroupEntity> groups = new ArrayList<>();
        GroupEntity group = new GroupEntity();
        group.id = 1;
        group.name = "Группа-18-1";
        groups.add(group);
        group = new GroupEntity();
        group.id = 2;
        group.name = "Группа-18-2";
        groups.add(group);
        DatabaseManager.getInstance(context).getHseDao().insertGroup(groups);

        List<TeacherEntity> teachers = new ArrayList<>();
        TeacherEntity teacher = new TeacherEntity();
        teacher.id = 1;
        teacher.fio = "Петров Петр Петрович";
        teachers.add(teacher);
        teacher = new TeacherEntity();
        teacher.id = 2;
        teacher.fio = "Петров2 Петр2 Петрович2";
        teachers.add(teacher);
        DatabaseManager.getInstance(context).getHseDao().insertTeacher(teachers);

        List<TimeTableEntity> timeTables = new ArrayList<>();
        TimeTableEntity timeTable = new TimeTableEntity();
        timeTable.id = 1;
        timeTable.cabinet = "Кабинет 1";
        timeTable.subGroup = "ПИ";
        timeTable.subName = "Философия";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2023-02-17 15:00");
        timeTable.timeEnd = dateFromString("2023-02-17 23:59");
        timeTable.groupId = 1;
        timeTable.teacherId = 1;
        timeTables.add(timeTable);
        timeTable = new TimeTableEntity();
        timeTable.id = 2;
        timeTable.cabinet = "Кабинет 2";
        timeTable.subGroup = "ПИ";
        timeTable.subName = "Мобильная разработка";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2023-02-17 13:00");
        timeTable.timeEnd = dateFromString("2023-02-17 18:00");
        timeTable.groupId = 2;
        timeTable.teacherId = 2;
        timeTables.add(timeTable);
        timeTable = new TimeTableEntity();
        timeTable.id = 3;
        timeTable.cabinet = "Кабинет 2";
        timeTable.subGroup = "ПИ";
        timeTable.subName = "Мобильная разработка";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2023-02-18 20:00");
        timeTable.timeEnd = dateFromString("2023-02-18 21:20");
        timeTable.groupId = 2;
        timeTable.teacherId = 2;
        timeTables.add(timeTable);
        timeTable = new TimeTableEntity();
        timeTable.id = 4;
        timeTable.cabinet = "Кабинет 2";
        timeTable.subGroup = "ПИ";
        timeTable.subName = "Мобильная разработка";
        timeTable.corp = "К1";
        timeTable.type = 0;
        timeTable.timeStart = dateFromString("2023-02-18 15:00");
        timeTable.timeEnd = dateFromString("2023-02-18 18:00");
        timeTable.groupId = 2;
        timeTable.teacherId = 2;
        timeTables.add(timeTable);
        DatabaseManager.getInstance(context).getHseDao().insertTimeTable(timeTables);
    }

    private Date dateFromString(String val) {
        SimpleDateFormat simpleDateFormat = new SimpleDateFormat(
                "yyyy-MM-dd HH:mm",
                Locale.getDefault()
        );
        try {
            return simpleDateFormat.parse((val));
        }
        catch (ParseException e) {
            //
        }
        return null;
    }

}
