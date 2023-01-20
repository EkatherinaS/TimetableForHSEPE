package org.hse.timetableforhsepe;

import android.os.Bundle;

import androidx.annotation.Nullable;

public class ScheduleItem {
    private String start;
    private String end;
    private String type;
    private String name;
    private String place;
    private String professor;

    public String getStart() { return this.start; }

    public String getEnd() { return this.end; }

    public String getType() { return this.type; }

    public String getName() { return this.name; }

    public String getPlace() { return this.place; }

    public String getProfessor() { return this.professor; }


    public void setStart ( String start ) { this.start = start; }

    public void setEnd ( String end ) { this.end = end; }

    public void setType ( String type ) { this.type = type; }

    public void setName ( String name ) { this.name = name; }

    public void setPlace ( String place ) { this.place = place; }

    public void setProfessor ( String professor ) { this.professor = professor; }


}
