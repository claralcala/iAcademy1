package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.daos.CourseDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Inscription;
import es.iescarrillo.iacademy1.models.Lesson;

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

    @Override
    public Map<Course, List<Lesson>> getCourseWithLessonsMap() {
        return courseDAO.getCourseWithLessonsMap();
    }

    @Override
    public Map<Course, List<Inscription>> getCoursesWithInscriptionMap() {
        return courseDAO.getCoursesWithInscriptionMap();
    }

    @Override
    public List<Course> getCoursebyAcademyID(Long acID) {
        return courseDAO.getCoursebyAcademyID(acID);
    }

    @Override
    public List<Course> getEnrolledCoursesStudent(long userId) {
        return courseDAO.getEnrolledCoursesStudent(userId);
    }

}
