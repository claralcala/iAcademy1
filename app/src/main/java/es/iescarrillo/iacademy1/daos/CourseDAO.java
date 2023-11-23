package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
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

    @Query("DELETE FROM course WHERE id = :course_id AND academy_id= :ac_id")
    void deleteCourseById(long course_id, long ac_id);

    @Query("UPDATE course SET title=:title_, description=:description_, level = :level_, capacity= :capacity_, startDate= :startDate_, endDate= :endDate_, activated= :activated_  WHERE academy_id = :ac_id AND id = :id ")
    void updateCoursebyId(String title_, String description_, String level_, int capacity_, LocalDate startDate_, LocalDate endDate_, boolean activated_, long ac_id, long id);

    @Query("SELECT * FROM course WHERE teacher_id = :tID")
    List<Course> getCoursebyTeacherID(Long tID);
    @Query("SELECT * FROM course WHERE academy_id = :acID")
    List<Course> getCoursebyAcademyID(Long acID);

    @Query("SELECT * FROM Course WHERE id IN (SELECT course_id FROM Inscription WHERE student_id = :userId)")
    List<Course> getEnrolledCoursesStudent(long userId);


}



