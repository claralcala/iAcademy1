package es.iescarrillo.iacademy1.daos;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

import es.iescarrillo.iacademy1.models.Lesson;

@Dao
public interface LessonDAO {
    @Insert
    long insertLesson(Lesson lesson);

    @Update
    void updateLesson(Lesson lesson);

    @Delete
    void deleteLesson(Lesson lesson);

    @Query("SELECT * FROM lesson")
    List<Lesson> getAll();

    @Query("SELECT * FROM lesson WHERE course_id = course_id = :courseID ORDER BY lessonDate ASC, lessonHour ASC")
    List<Lesson> getLessonWithCouseById(long courseID);
}
