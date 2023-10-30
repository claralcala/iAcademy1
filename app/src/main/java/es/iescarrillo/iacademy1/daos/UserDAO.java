package es.iescarrillo.iacademy1.daos;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.iescarrillo.iacademy1.models.User;

@Dao
public interface UserDAO {

    @Insert

    long insertUser(User user);

    @Update

    void uptadeUser(User user);

    @Delete

    void deleteUser(User user);

    @Query("SELECT * FROM user")
    List<User> getAll();

}
