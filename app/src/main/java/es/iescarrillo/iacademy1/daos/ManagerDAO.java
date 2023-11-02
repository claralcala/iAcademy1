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


@Dao
public interface ManagerDAO {

    @Insert
    long insertManager(Manager manager);

    @Update
    void updateManager(Manager manager);

    @Delete
    void deleteManager(Manager manager);

    @Query("SELECT * FROM manager")
    List<Manager> getAll();

    @Query("SELECT * FROM manager m JOIN academy a on m.id=a.manager_id")
    Map<Manager,Academy> getManagerWithAcademyMap();

    @Query("SELECT * FROM manager m JOIN academy a on m.id=a.manager_id JOIN course c on a.id=c.academy_id")
    Map<Manager, List<Course>> getManagerWithCoursesMap();

    
}
