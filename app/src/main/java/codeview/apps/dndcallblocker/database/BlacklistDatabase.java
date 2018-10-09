package codeview.apps.dndcallblocker.database;
import android.arch.persistence.room.Database;
import android.arch.persistence.room.Room;
import android.arch.persistence.room.RoomDatabase;
import android.content.Context;

import codeview.apps.dndcallblocker.model.BlacklistModel;
import codeview.apps.dndcallblocker.utils.AppConstants;

@Database(entities = {BlacklistModel.class}, version = 1, exportSchema = false)
public abstract class BlacklistDatabase extends RoomDatabase {
    private static BlacklistDatabase INSTANCE;

    public abstract DaoBlacklist daoBlacklist();

    public static BlacklistDatabase getAppDatabase(Context context){
        if(INSTANCE==null){
            INSTANCE=Room.databaseBuilder(context,BlacklistDatabase.class,AppConstants.DATABASE_NAME)
                    .allowMainThreadQueries()
                    .build();
        }
        return INSTANCE;
    }

    public static void destroyInstance(){
        INSTANCE=null;
    }
}
