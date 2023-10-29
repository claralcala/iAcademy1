package es.iescarrillo.iacademy1.daos;

import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.iescarrillo.iacademy1.models.Admin;
import es.iescarrillo.iacademy1.models.Lesson;

public interface LessonDAO {
    @Insert
    long insertLesson(Lesson lesson);

    @Update
    void updateLesson(Lesson lesson);

    @Delete
    void deleteLesson(Lesson lesson);

    @Query("SELECT * FROM lesson")
    List<Lesson> getAll();
}
