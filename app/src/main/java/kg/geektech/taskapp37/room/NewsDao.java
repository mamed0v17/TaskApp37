package kg.geektech.taskapp37.room;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import java.util.List;

import kg.geektech.taskapp37.models.News;

@Dao
public interface NewsDao {
    @Query("SELECT *FROM news order by createAt DESC")
    List<News> getAll();

    @Query("SELECT *FROM news order by title ASC")
    List<News> getAllSortedByTitle();

    @Insert
    void insert(News news);

    @Delete
    void delete(News news);
}
