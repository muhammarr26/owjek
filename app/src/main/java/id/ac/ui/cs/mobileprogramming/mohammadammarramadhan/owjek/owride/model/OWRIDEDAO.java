package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface OWRIDEDAO {

    // allowing the insert of the same word multiple times by passing a
    // conflict resolution strategy
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    void insert(OWRIDEModel word);

    @Update
    void update(OWRIDEModel history);

    @Query("SELECT * FROM owride_history ORDER BY owridehistory_id DESC")
    LiveData<List<OWRIDEModel>> getAllHistories();

    @Query("SELECT * FROM owride_history WHERE owridehistory_id == :id")
    LiveData<OWRIDEModel> getHistory(int id);

    @Query("SELECT * FROM owride_history ORDER BY owridehistory_id DESC LIMIT 1")
    LiveData<OWRIDEModel> getLatestHistory();

    @Query("DELETE FROM owride_history")
    void deleteAll();

    @Query("SELECT COUNT(*) FROM owride_history")
    int getSize();

    @Query("UPDATE owride_history SET status = :status WHERE owridehistory_id == (SELECT COUNT(*) FROM owride_history)")
    int updateStatusOnLatestHistory(int status);
}
