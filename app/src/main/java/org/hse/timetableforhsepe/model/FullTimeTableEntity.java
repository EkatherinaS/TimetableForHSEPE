package org.hse.timetableforhsepe.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class FullTimeTableEntity {

    @Embedded
    public TimeTableEntity timeTableEntity;

    @Relation(
            parentColumn = "group_id",
            entityColumn = "id"
    )
    public GroupEntity groupEntity;

    @Relation(
            parentColumn = "teacher_id",
            entityColumn = "id"
    )
    public TeacherEntity teacherEntity;
}
