package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

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
}
