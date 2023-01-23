package org.hse.timetableforhsepe.model;

import androidx.room.Embedded;
import androidx.room.Relation;

public class TimeTableWithGroupEntity {

    @Embedded
    public TimeTableEntity timeTableEntity;

    @Relation(
            parentColumn = "group_id",
            entityColumn = "id"
    )
    public GroupEntity groupEntity;
}
