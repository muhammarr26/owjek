package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.Executor;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.OWJEK;

public class OWRIDERepository {
    private OWRIDEDAO mOWRIDEDAO;

    public OWRIDERepository(Application application) {
        OWRIDEDatabase db = OWRIDEDatabase.getDatabase(application);
        mOWRIDEDAO = db.owrideHistoryDAO();
    }

    int getSize(){
        return mOWRIDEDAO.getSize();
    }

    void insert(final OWRIDEModel history) {
        OWRIDEDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mOWRIDEDAO.insert(history);
            }
        });
    }

    void update(final OWRIDEModel history) {
        OWRIDEDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mOWRIDEDAO.update(history);
            }
        });
    }

    LiveData<OWRIDEModel> getHistory(int id){
        return mOWRIDEDAO.getHistory(id);
    }

    LiveData<OWRIDEModel> getLatestHistory(){
        return mOWRIDEDAO.getLatestHistory();
    }

    void logCurrentSize(){
        OWJEK.asyncLog.execute(new Runnable() {
            @Override
            public void run() {
                Log.i("Database", String.format("Row Size: %d", mOWRIDEDAO.getSize()));
            }
        });
    }

    public void updateStatusOnLatestHistory(final int status){
        OWRIDEDatabase.databaseWriteExecutor.execute(new Runnable() {
            @Override
            public void run() {
                mOWRIDEDAO.updateStatusOnLatestHistory(status);
            }
        });
    }
}
