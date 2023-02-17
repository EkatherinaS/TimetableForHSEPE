package org.hse.timetableforhsepe.view_model;

import android.app.Application;
import org.hse.timetableforhsepe.model.Converters;
import org.hse.timetableforhsepe.model.FullTimeTableEntity;
import org.hse.timetableforhsepe.view.BaseActivity;

public class ScheduleItem extends Application {

    private String start;
    private String end;
    private String name;
    private String place;
    private String addInfo;
    private int type;

    public ScheduleItem() { ; }

    public ScheduleItem(FullTimeTableEntity entity, BaseActivity.ScheduleMode mode) {
        this.start = Converters.dateToTimeFormat(entity.timeTableEntity.timeStart);
        this.end = Converters.dateToTimeFormat(entity.timeTableEntity.timeEnd);
        this.type = entity.timeTableEntity.type;
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

    public int getType() { return this.type; }

    public String getName() { return this.name; }

    public String getPlace() { return this.place; }

    public String getAddInfo() { return this.addInfo; }


    public void setStart ( String start ) { this.start = start; }

    public void setEnd ( String end ) { this.end = end; }

    public void setType ( int type ) { this.type = type; }

    public void setName ( String name ) { this.name = name; }

    public void setPlace ( String place ) { this.place = place; }

    public void setAddInfo ( String addInfo ) { this.addInfo = addInfo; }
}
