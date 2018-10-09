package codeview.apps.dndcallblocker.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codeview.apps.dndcallblocker.model.BlacklistModel;
import codeview.apps.dndcallblocker.model.BlacklistRepository;

public class BlacklistViewModel extends AndroidViewModel {

    private BlacklistRepository repository;
    private LiveData<List<BlacklistModel>> allBlacklists;

    public BlacklistViewModel(@NonNull Application application) {
        super(application);
        repository=new BlacklistRepository(application);
        allBlacklists=repository.getAllBlacklists();
    }

    public void insert(BlacklistModel blacklistModel){
        repository.insert(blacklistModel);
    }
    public void delete(BlacklistModel blacklistModel){
        repository.delete(blacklistModel);
    }
    public void deleteAll(){
        repository.deleteAllBlacklist();
    }

    public LiveData<List<BlacklistModel>> getAllBlacklists(){
        return allBlacklists;
    }
}
