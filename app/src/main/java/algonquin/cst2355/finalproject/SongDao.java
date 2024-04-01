package algonquin.cst2355.finalproject;

// SongDao.java

import androidx.room.Dao;
import androidx.room.Insert;

@Dao
public interface SongDao {
    @Insert
    void insert(Song song);
}
