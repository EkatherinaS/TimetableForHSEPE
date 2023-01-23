package org.hse.timetableforhsepe.view_model;

import org.hse.timetableforhsepe.model.Converters;
import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.view.BaseActivity;

public class ScheduleItem {

    private String start;
    private String end;
    private String type;
    private String name;
    private String place;
    private String addInfo;

    public ScheduleItem() { ; }

    public ScheduleItem(FullTimeTableEntity entity, BaseActivity.ScheduleMode mode) {
        this.start = Converters.dateToTimeFormat(entity.timeTableEntity.timeStart);
        this.end = Converters.dateToTimeFormat(entity.timeTableEntity.timeEnd);
        switch (entity.timeTableEntity.type) {
            case 0:
                this.type = "Практическое занятие";
                break;
            case 1:
                this.type = "Лекция";
                break;
        }
        this.name = entity.timeTableEntity.subName;
        this.place = entity.timeTableEntity.cabinet + " [" + entity.timeTableEntity.corp + "]";
        if(mode == BaseActivity.ScheduleMode.PROFESSOR) {
            this.addInfo = entity.groupEntity.name;
        }
        if(mode == BaseActivity.ScheduleMode.STUDENT) {
            this.addInfo = entity.teacherEntity.fio;
        }
    }


    public String getStart() { return this.start; }

    public String getEnd() { return this.end; }

    public String getType() { return this.type; }

    public String getName() { return this.name; }

    public String getPlace() { return this.place; }

    public String getAddInfo() { return this.addInfo; }


    public void setStart ( String start ) { this.start = start; }

    public void setEnd ( String end ) { this.end = end; }

    public void setType ( String type ) { this.type = type; }

    public void setName ( String name ) { this.name = name; }

    public void setPlace ( String place ) { this.place = place; }

    public void setAddInfo ( String addInfo ) { this.addInfo = addInfo; }
}
