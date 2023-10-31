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

    @Query("SELECT * FROM teacher t JOIN course c ON t.id=c.teacher_id")
    Map<Teacher, List<Course>> getTeacherWithCoursesMap();



}
