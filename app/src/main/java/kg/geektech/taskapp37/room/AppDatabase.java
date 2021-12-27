package kg.geektech.taskapp37.room;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import kg.geektech.taskapp37.models.News;

@Database(entities = {News.class}, version = 1)
public abstract class AppDatabase  extends RoomDatabase {
    public abstract NewsDao newsDao();
}
