package codeview.apps.dndcallblocker.database;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;

import java.util.List;

import codeview.apps.dndcallblocker.model.BlacklistModel;

@Dao
public interface DaoBlacklist {

    @Insert
    void insertMultipleBlacklist(List<BlacklistModel> blacklistList);

    @Insert
    void insertBlacklist(BlacklistModel blacklistModel);

    @Query("SELECT * FROM blacklist")
    LiveData<List<BlacklistModel>> getAllBlacklist();

    @Delete
    void deleteItem(BlacklistModel blacklistModel);

    @Query("DELETE from blacklist")
    void deleteAll();
}
