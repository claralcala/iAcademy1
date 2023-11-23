package es.iescarrillo.iacademy1.daos;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.iescarrillo.iacademy1.models.Inscription;

@Dao
public interface InscriptionDAO {

    @Insert

    long insertInscription (Inscription inscription);

    @Update

    void updateInscription (Inscription inscription);


    @Delete

    void deleteInscription (Inscription inscription);


    @Query("SELECT * FROM inscription")
    List<Inscription> getAll();

    @Query("select count (*) from Inscription where course_id = :courseID")
    int countInscriptionInACourse (Long courseID);

    @Query("SELECT * FROM inscription WHERE student_id = :studentId AND course_id = :courseId LIMIT 1")
    Inscription getInscriptionByStudentAndCourse(Long studentId, Long courseId);
}
