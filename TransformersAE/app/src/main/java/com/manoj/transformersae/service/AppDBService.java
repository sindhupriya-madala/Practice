package com.manoj.transformersae.service;

import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import com.manoj.transformersae.model.BotDao;
import com.manoj.transformersae.model.BotModel;


/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
@Database(entities = {BotModel.class}, version = 1, exportSchema = false)
public abstract class AppDBService extends RoomDatabase {
    private static final String DATABASE_NAME = "bot_db";
    private static AppDBService INSTANCE;

    public static AppDBService getAppDatabase(Context context) {
        if (INSTANCE == null) {
            INSTANCE =
                    Room.databaseBuilder(context.getApplicationContext(), AppDBService.class, DATABASE_NAME)
                            .fallbackToDestructiveMigration().build();
        }
        return INSTANCE;
    }



    public abstract BotDao botDao();
}
