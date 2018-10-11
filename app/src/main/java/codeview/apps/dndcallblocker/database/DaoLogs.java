package codeview.apps.dndcallblocker.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import codeview.apps.dndcallblocker.model.LogModel;

@Dao
public interface DaoLogs {

    @Insert
    void insertMultipleLogs(List<LogModel> logs);

    @Insert
    void insertLog(LogModel log);

    @Query("SELECT * FROM logs_table")
    LiveData<List<LogModel>> getAllLogs();

    @Delete
    void deleteLog(LogModel log);

    @Query("DELETE from logs_table")
    void deleteAllLogs();
}
