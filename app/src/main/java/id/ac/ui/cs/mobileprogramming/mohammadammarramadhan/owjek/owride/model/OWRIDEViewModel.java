package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class OWRIDEViewModel extends AndroidViewModel {
    private OWRIDERepository mRepository;

    public OWRIDEViewModel (Application application) {
        super(application);
        mRepository = new OWRIDERepository(application);
    }

    public void insert(OWRIDEModel history) {
        mRepository.insert(history);
        mRepository.logCurrentSize();
    }

    public void update(OWRIDEModel history) { mRepository.update(history); }

    public LiveData<OWRIDEModel> getHistory(int id) { return mRepository.getHistory(id); }

    public LiveData<OWRIDEModel> getLatestHistory() { return mRepository.getLatestHistory(); }

}
