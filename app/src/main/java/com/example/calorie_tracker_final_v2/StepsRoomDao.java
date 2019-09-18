package com.example.calorie_tracker_final_v2;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import static android.arch.persistence.room.OnConflictStrategy.REPLACE;

@Dao
public interface StepsRoomDao {
    @Query("SELECT * FROM stepsroom")
    List<StepsRoom> getAll();
    @Query("SELECT * FROM StepsRoom WHERE time LIKE :time")
    StepsRoom findStepsRoom(String time);
    @Insert
    void insertAll(StepsRoom... StepsRooms);
    @Insert
    long insert(StepsRoom StepsRoom);
    @Delete
    void delete(StepsRoom StepsRoom);
    @Update(onConflict = REPLACE)
    public void updateUsers(StepsRoom... StepsRooms);
    @Query("DELETE FROM StepsRoom")
    void deleteAll();
    @Query("SELECT SUM(Steps) FROM StepsRoom")
    int getSummisionofSteps();
}

