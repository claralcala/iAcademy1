package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;

import es.iescarrillo.iacademy1.daos.CourseDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Course;

public class CourseService implements CourseDAO {

    private CourseDAO courseDAO;

    public CourseService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        courseDAO = db.courseDAO();
    }

    @Override
    public long insertCourse(Course course) {
        return courseDAO.insertCourse(course);
    }

    @Override
    public void updateCourse(Course course) {
        courseDAO.updateCourse(course);
    }

    @Override
    public void deleteCourse(Course course) {
        courseDAO.deleteCourse(course);
    }

    @Override
    public List<Course> getAll() {
        return courseDAO.getAll();
    }
}
