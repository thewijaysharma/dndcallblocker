package codeview.apps.dndcallblocker.model;

import android.app.Application;
import android.arch.lifecycle.LiveData;
import android.os.AsyncTask;
import android.provider.Contacts;

import java.util.List;

import codeview.apps.dndcallblocker.database.BlacklistDatabase;
import codeview.apps.dndcallblocker.database.DaoBlacklist;

public class BlacklistRepository {

    private DaoBlacklist daoBlacklist;
    private LiveData<List<BlacklistModel>> allBlacklists;

    public BlacklistRepository(Application application) {
        BlacklistDatabase database = BlacklistDatabase.getAppDatabase(application);
        daoBlacklist = database.daoBlacklist();
        allBlacklists = daoBlacklist.getAllBlacklist();
    }

    public void insert(BlacklistModel blacklistModel) {
        new InsertBlacklistAsynctask(daoBlacklist).execute(blacklistModel);
    }

    public void delete(BlacklistModel blacklistModel) {
        new DeleteBlacklistAsynctask(daoBlacklist).execute(blacklistModel);
    }

    public void deleteAllBlacklist() {
        new DeleteAllAsynctask(daoBlacklist).execute();
    }

    public LiveData<List<BlacklistModel>> getAllBlacklists() {
        return allBlacklists;
    }

    private static class InsertBlacklistAsynctask extends AsyncTask<BlacklistModel, Void, Void> {
        private DaoBlacklist daoBlacklist;

        private InsertBlacklistAsynctask(DaoBlacklist daoBlacklist) {
            this.daoBlacklist = daoBlacklist;
        }

        @Override
        protected Void doInBackground(BlacklistModel... blacklistModels) {
            daoBlacklist.insertBlacklist(blacklistModels[0]);
            return null;
        }
    }

    private static class DeleteBlacklistAsynctask extends AsyncTask<BlacklistModel, Void, Void> {
        private DaoBlacklist daoBlacklist;

        private DeleteBlacklistAsynctask(DaoBlacklist daoBlacklist) {
            this.daoBlacklist = daoBlacklist;
        }

        @Override
        protected Void doInBackground(BlacklistModel... blacklistModels) {
            daoBlacklist.deleteItem(blacklistModels[0]);
            return null;
        }
    }

    private static class DeleteAllAsynctask extends AsyncTask<BlacklistModel, Void, Void> {
        private DaoBlacklist daoBlacklist;

        private DeleteAllAsynctask(DaoBlacklist daoBlacklist) {
            this.daoBlacklist = daoBlacklist;
        }

        @Override
        protected Void doInBackground(BlacklistModel... blacklistModels) {
            daoBlacklist.deleteAll();
            return null;
        }
    }

}
