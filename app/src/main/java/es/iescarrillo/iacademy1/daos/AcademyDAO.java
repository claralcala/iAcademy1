package es.iescarrillo.iacademy1.daos;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.iescarrillo.iacademy1.models.Academy;

@Dao
public interface AcademyDAO {
    @Insert
    long insertAcademy(Academy academy);

    @Update
    void updateAcademy(Academy academy);

    @Delete
    void deleteAcademy(Academy academy);

    @Query("SELECT * FROM academy")
    List<Academy> getAll();

}
