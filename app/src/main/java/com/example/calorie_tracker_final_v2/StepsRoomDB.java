package com.example.calorie_tracker_final_v2;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

@Database(entities = {StepsRoom.class}, version = 2, exportSchema = false)
public abstract class StepsRoomDB extends RoomDatabase {
    public abstract StepsRoomDao stepsRoomDBDao();
    private static volatile StepsRoomDB INSTANCE;
    static StepsRoomDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (StepsRoomDB.class) {
                if (INSTANCE == null) {
                    INSTANCE =
                            Room.databaseBuilder(context.getApplicationContext(),
                                    StepsRoomDB.class, "steps_room_db")
                                    .build();
                }
            }
        }
        return INSTANCE;
    }
}

