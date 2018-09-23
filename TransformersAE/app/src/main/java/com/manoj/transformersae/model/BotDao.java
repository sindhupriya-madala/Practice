package com.manoj.transformersae.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;
import android.support.annotation.VisibleForTesting;

import java.util.List;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
@Dao
public interface BotDao {

    @Query("SELECT * FROM botmodel")
    List<BotModel> getAll();

    @Query("SELECT * FROM botmodel where id = :id")
    BotModel getBotById(String id);

    @Insert
    void insertBot(BotModel botModel);

    @Insert
    void insertAllBot(List<BotModel> botModelList);

    @Delete
    void deleteBot(BotModel botModel);

    @Update
    void updateBot(BotModel botModel);

    @VisibleForTesting
    @Query("DELETE FROM botmodel")
    void deleteAll();
}
