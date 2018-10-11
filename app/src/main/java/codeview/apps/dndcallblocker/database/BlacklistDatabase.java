package codeview.apps.dndcallblocker.database;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import codeview.apps.dndcallblocker.model.BlacklistModel;
import codeview.apps.dndcallblocker.model.LogModel;
import codeview.apps.dndcallblocker.utils.AppConstants;

@Database(entities = {BlacklistModel.class,LogModel.class}, version = 2, exportSchema = false)
public abstract class BlacklistDatabase extends RoomDatabase {
    private static BlacklistDatabase INSTANCE;

    public abstract DaoLogs daoLogs();
    public abstract DaoBlacklist daoBlacklist();

    public static synchronized BlacklistDatabase getAppDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE=Room.databaseBuilder(context.getApplicationContext(),BlacklistDatabase.class,AppConstants.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .fallbackToDestructiveMigration()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE=null;
    }
}
