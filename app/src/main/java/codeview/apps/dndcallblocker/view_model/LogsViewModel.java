package codeview.apps.dndcallblocker.view_model;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.support.annotation.NonNull;

import java.util.List;

import codeview.apps.dndcallblocker.model.LogModel;
import codeview.apps.dndcallblocker.model.Repository;

public class LogsViewModel extends AndroidViewModel {

    private Repository repository;
    private LiveData<List<LogModel>> allLogs;

    public LogsViewModel(@NonNull Application application) {
        super(application);
        repository = new Repository(application);
        allLogs = repository.getAllLogs();
    }

    public void insertLog(LogModel logModel) {
        repository.insertLog(logModel);
    }

    public void deleteLog(LogModel logModel) {
        repository.deleteLog(logModel);
    }

    public void deleteAllLogs() {
        repository.deleteAllLogs();
    }

    public LiveData<List<LogModel>> getAllLogs() {
        return allLogs;
    }
}
