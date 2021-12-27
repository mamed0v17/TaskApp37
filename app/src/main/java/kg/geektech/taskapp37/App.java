package kg.geektech.taskapp37;

import android.app.Application;

import androidx.room.Room;

import kg.geektech.taskapp37.room.AppDatabase;

public class App extends Application {
    private AppDatabase database;

    private static App instance;

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        database = Room.databaseBuilder
                (this, AppDatabase.class, "database.db")
                .allowMainThreadQueries().build();
    }

    public AppDatabase getDatabase() {
        return database;
    }

    public static App getInstance() {
        return instance;
    }
}
