package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Inscription;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.models.Student;

@Dao
public interface StudentDAO {

    @Insert
    long inserStudent(Student student);

    @Update

    void updateStudent (Student student);

    @Delete

    void deleteStudent(Student student);

    @Query("SELECT * FROM student")
    List<Student> getAll();


    @Query("SELECT * FROM student s JOIN inscription i ON s.id=i.course_id")
    Map<Student, List<Inscription>> getStudentWithInscriptionsMap();


    @Query("SELECT * FROM student WHERE username = :username")
    Student getStudentByUsername(String username);
}
