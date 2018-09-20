package com.vemuru.transformers.model;

import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

/**
 * Created by Manoj Vemuru on 2018-09-19.
 */
@Dao
public interface BotDao {

    @Query("SELECT * FROM botmodel")
    List<BotModel> getAll();

    @Insert
    void insertBot(BotModel botModel);

    @Insert
    void insertAllBot(List<BotModel> botModelList);

    @Delete
    void deleteBot(BotModel botModel);

    @Update
    void updateBot(BotModel botModel);
}
