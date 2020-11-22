package id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.owride.model;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverter;
import androidx.sqlite.db.SupportSQLiteDatabase;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import id.ac.ui.cs.mobileprogramming.mohammadammarramadhan.owjek.util.Converters;

@Database(entities = {OWRIDEModel.class}, version = 1, exportSchema = false)
public abstract class OWRIDEDatabase extends RoomDatabase {

    public abstract OWRIDEDAO owrideHistoryDAO();

    private static volatile OWRIDEDatabase INSTANCE;
    private static final int NUMBER_OF_THREADS = 4;
    static final ExecutorService databaseWriteExecutor =
            Executors.newFixedThreadPool(NUMBER_OF_THREADS);

    static OWRIDEDatabase getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (OWRIDEDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                            OWRIDEDatabase.class, "owride_database")
                            .build();
                }
            }
        }
        return INSTANCE;
    }

    private static RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback() {
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);

            // If you want to keep data through app restarts,
            // comment out the following block
            databaseWriteExecutor.execute(new Runnable() {
                @Override
                public void run() {
                    // Populate the database in the background.
                    // If you want to start with more words, just add them.
                    OWRIDEDAO dao = INSTANCE.owrideHistoryDAO();
                    dao.deleteAll();

                    dao.insert(new OWRIDEModel("Margo", "UI"));
                    dao.insert(new OWRIDEModel("FASILKOM", "Stasiun Pocin"));
                    dao.insert(new OWRIDEModel("Stasiun UI", "FASILKOM"));
                }
            });
        }
    };
}
