package com.example.calorie_tracker_final_v2;

import android.arch.persistence.room.ColumnInfo;
import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;
import android.support.annotation.NonNull;

@Entity
public class StepsRoom {

    @PrimaryKey(autoGenerate = false)
    @NonNull
    public String time;
    @ColumnInfo(name = "Steps")
    public Integer steps;


    public StepsRoom(String time, Integer steps) {
        this.time = time;
        this.steps = steps;

    }

    public String getTime() {
        return time;
    }

    public Integer getSteps() {
        return steps;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public void setSteps(Integer steps) {
        this.steps = steps;
    }
}

