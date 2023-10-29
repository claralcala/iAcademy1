package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.iescarrillo.iacademy1.models.Admin;
import es.iescarrillo.iacademy1.models.Person;
@Dao
public interface AdminDAO {
    @Insert
    long insertAdmin(Admin admin);

    @Update
    void updateAdmin(Admin admin);

    @Delete
    void deleteAdmin(Admin admin);

    @Query("SELECT * FROM admin")
    List<Admin> getAll();
}
