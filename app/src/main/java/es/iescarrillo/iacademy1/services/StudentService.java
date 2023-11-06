package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.daos.StudentDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Inscription;
import es.iescarrillo.iacademy1.models.Student;

public class StudentService implements StudentDAO {

    private StudentDAO studentDAO;
    public StudentService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        studentDAO = db.studentDAO();
    }

    @Override
    public long inserStudent(Student student) {
        return studentDAO.inserStudent(student);
    }

    @Override
    public void updateStudent(Student student) {
        studentDAO.updateStudent(student);
    }

    @Override
    public void deleteStudent(Student student) {
        studentDAO.deleteStudent(student);
    }

    @Override
    public List<Student> getAll() {
        return studentDAO.getAll();
    }

    @Override
    public Map<Student, List<Inscription>> getStudentWithInscriptionsMap() {
        return studentDAO.getStudentWithInscriptionsMap();
    }

    @Override
    public Student getStudentByUsername(String username) {
        return studentDAO.getStudentByUsername(username);
    }
}
