package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Manager;

@Dao
public interface CourseDAO {

    @Insert
    long insertCourse(Course course);

    @Update
    void updateCourse(Course course);

    @Delete
    void deleteCourse(Course course);

    @Query("SELECT * FROM course")
    List<Course> getAll();
}
