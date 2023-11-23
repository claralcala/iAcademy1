package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Admin;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.models.Teacher;

@Dao
public interface TeacherDAO {
    @Insert
    long insertTeacher(Teacher teacher);

    @Update
    void updateTeacher(Teacher teacher);

    @Delete
    void deleteTeacher(Teacher teacher);

    @Query("SELECT * FROM teacher")
    List<Teacher> getAll();



    @Query("SELECT * FROM teacher WHERE username = :username")
    Teacher getTeacherByUsername(String username);


    @Query("SELECT * FROM teacher WHERE academy_id= :id")
    List<Teacher> getTeachersByAcademy(long id);

}
