package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.time.LocalDate;
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
    public void deleteCourseById(long course_id, long ac_id) {
        courseDAO.deleteCourseById(course_id, ac_id);
    }

    @Override
    public void updateCoursebyId(String title_, String description_, String level_, int capacity_, LocalDate startDate_, LocalDate endDate_, boolean activated_, long ac_id, long id) {
        courseDAO.updateCoursebyId(title_, description_, level_, capacity_, startDate_, endDate_, activated_, ac_id, id);
    }

    @Override
    public List<Course> getCoursebyTeacherID(Long tID) {
        return courseDAO.getCoursebyTeacherID(tID);
    }
}
