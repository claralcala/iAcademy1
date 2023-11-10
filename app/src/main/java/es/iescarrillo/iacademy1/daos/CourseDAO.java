package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Inscription;
import es.iescarrillo.iacademy1.models.Lesson;
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


    @Query("SELECT * FROM course c JOIN lesson s on c.id=s.course_id")
    Map<Course, List<Lesson>> getCourseWithLessonsMap();

    @Query("SELECT * FROM course c JOIN inscription i ON c.id=i.course_id")
    Map<Course, List<Inscription>> getCoursesWithInscriptionMap();

    @Query("SELECT * FROM course WHERE academy_id = :acID")
    List<Course> getCoursebyAcademyID(Long acID);

    @Query("DELETE FROM course WHERE id = :course_id")
    void deleteCourseById(long course_id);

}
