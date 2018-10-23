package codeview.apps.dndcallblocker.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import java.util.List;
import codeview.apps.dndcallblocker.database.BlacklistDatabase;
import codeview.apps.dndcallblocker.database.DaoBlacklist;
import codeview.apps.dndcallblocker.database.DaoLogs;

public class Repository {

    private DaoBlacklist daoBlacklist;
    private DaoLogs daoLogs;
    private LiveData<List<BlacklistModel>> allBlacklists;
    private LiveData<List<LogModel>> allLogs;

    public Repository(Application application) {
        BlacklistDatabase database = BlacklistDatabase.getAppDatabase(application);
        daoBlacklist = database.daoBlacklist();
        daoLogs=database.daoLogs();
        allBlacklists = daoBlacklist.getLiveBlacklist();
        allLogs=daoLogs.getAllLogs();
    }

    public void insertBlacklist(BlacklistModel blacklistModel) {
        daoBlacklist.insertBlacklist(blacklistModel);
    }

    public void insertMultipleBlacklist(List<BlacklistModel> blacklistModels){
        daoBlacklist.insertMultipleBlacklist(blacklistModels);
    }

    public void insertLog(LogModel logModel) {
        daoLogs.insertLog(logModel);
    }

    public void insertMultipleLogs(List<LogModel> logModels){
        daoLogs.insertMultipleLogs(logModels);
    }

    public void deleteBlacklist(BlacklistModel blacklistModel) {
        daoBlacklist.deleteItem(blacklistModel);
    }

    public void deleteLog(LogModel logModel) {
        daoLogs.deleteLog(logModel);
    }

    public void deleteAllBlacklist() {
        new DeleteAllBlacklistTask(daoBlacklist).execute();
    }

    public void deleteAllLogs() {
        daoLogs.deleteAllLogs();
    }

    public LiveData<List<BlacklistModel>> getAllBlacklists() {
        return allBlacklists;
    }

    public LiveData<List<LogModel>> getAllLogs() {
        return allLogs;
    }

    private static class DeleteAllBlacklistTask extends AsyncTask<Void, Void, Void> {
        private DaoBlacklist daoBlacklist;

        private DeleteAllBlacklistTask(DaoBlacklist daoBlacklist) {
            this.daoBlacklist = daoBlacklist;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            daoBlacklist.deleteAll();
            return null;        }
    }

    private static class DeleteAllLogsTask extends AsyncTask<Void, Void, Void> {
        private DaoLogs daoLogs;

        private DeleteAllLogsTask(DaoLogs daoLogs) {
            this.daoLogs = daoLogs;
        }

        @Override
        protected Void doInBackground(Void... voids) {
            daoLogs.deleteAllLogs();
            return null;
        }
    }

}
