package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.daos.LessonDAO;
import es.iescarrillo.iacademy1.daos.TeacherDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Teacher;

public class TeacherService implements TeacherDAO {

    private TeacherDAO teacherDAO;

    public TeacherService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        teacherDAO= db.teacherDAO();
    }
    @Override
    public long insertTeacher(Teacher teacher) {
        return teacherDAO.insertTeacher(teacher);
    }

    @Override
    public void updateTeacher(Teacher teacher) {
        teacherDAO.updateTeacher(teacher);
    }

    @Override
    public void deleteTeacher(Teacher teacher) {
        teacherDAO.deleteTeacher(teacher);
    }

    @Override
    public List<Teacher> getAll() {
        return teacherDAO.getAll();
    }

    @Override
    public Map<Teacher, List<Course>> getTeacherWithCoursesMap() {
        return teacherDAO.getTeacherWithCoursesMap();
    }

    @Override
    public Teacher getTeacherByUsername(String username) {
        return teacherDAO.getTeacherByUsername(username);
    }
}
