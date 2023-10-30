package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

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

}
