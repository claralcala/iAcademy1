package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.models.Admin;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Lesson;

@Dao
public interface ClassroomDAO {
    @Insert
    long insertClassroom(Classroom classroom);

    @Update
    void updateClassroom(Classroom classroom);

    @Delete
    void deleteClassroom(Classroom classroom);

    @Query("SELECT * FROM classroom")
    List<Classroom> getAll();

    @Query("SELECT * FROM classroom class JOIN lesson s on class.id=s.classroom_id")
    Map<Classroom, List<Lesson>> getClassroomWithLessonsMap();

    @Query ("SELECT * FROM classroom WHERE academy_id= :academyid")
    List<Classroom> getClassroomsByAcademy(long academyid);

    @Query("DELETE FROM classroom WHERE id = :class_id AND academy_id= :ac_id")
    void deleteClassById(long class_id, long ac_id);

    @Query("UPDATE classroom SET name=:name_, capacity= :capacity_ WHERE academy_id = :ac_id AND id = :id ")
    void updateClassbyId(String name_, int capacity_, long ac_id, long id);

    @Query("SELECT * FROM classroom WHERE id= :classroomID")
    Classroom getClassroomByCourseID(long classroomID);
}
