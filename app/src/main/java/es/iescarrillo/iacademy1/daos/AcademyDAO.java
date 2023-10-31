package es.iescarrillo.iacademy1.daos;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.models.Teacher;

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

    @Query("SELECT * FROM academy a JOIN teacher t on a.id=t.academy_id")
    Map<Academy, List<Teacher>> getAcademyWithTeachersMap();

    @Query("SELECT * FROM academy a JOIN course c on a.id=c.academy_id")
    Map<Academy, List<Course>> getAcademyWithCoursesMap();


}
