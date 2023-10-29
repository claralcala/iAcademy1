package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;

import es.iescarrillo.iacademy1.daos.ClassroomDAO;
import es.iescarrillo.iacademy1.daos.LessonDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.models.Lesson;

public class LessonService implements LessonDAO {


    private LessonDAO lessonDAO;

    public LessonService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        lessonDAO= db.lessonDAO();
    }
    @Override
    public long insertLesson(Lesson lesson) {
        return lessonDAO.insertLesson(lesson);

    }

    @Override
    public void updateLesson(Lesson lesson) {
        lessonDAO.updateLesson(lesson);
    }

    @Override
    public void deleteLesson(Lesson lesson) {
        lessonDAO.deleteLesson(lesson);
    }

    @Override
    public List<Lesson> getAll() {
        return lessonDAO.getAll();
    }
}
