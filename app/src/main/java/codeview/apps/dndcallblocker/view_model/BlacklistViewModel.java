package codeview.apps.dndcallblocker.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codeview.apps.dndcallblocker.model.BlacklistModel;
import codeview.apps.dndcallblocker.model.Repository;

public class BlacklistViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<BlacklistModel>> allBlacklists;

    public BlacklistViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allBlacklists = repository.getAllBlacklists();
    }

    public LiveData<List<BlacklistModel>> getAllBlacklists() {
        return allBlacklists;
    }

    public void insertBlacklist(BlacklistModel blacklistModel) {
        repository.insertBlacklist(blacklistModel);
    }

    public void insertMultipleBlacklist(List<BlacklistModel> blacklistModels) {
        repository.insertMultipleBlacklist(blacklistModels);
    }

    public void deleteBlacklist(BlacklistModel blacklistModel) {
        repository.deleteBlacklist(blacklistModel);
    }

    public void deleteAllBlacklist() {
        repository.deleteAllBlacklist();
    }

}
