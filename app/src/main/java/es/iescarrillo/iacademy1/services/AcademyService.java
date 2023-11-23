package es.iescarrillo.iacademy1.services;

import android.app.Application;
import android.content.Context;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.daos.AcademyDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Teacher;

public class AcademyService implements AcademyDAO {

    private AcademyDAO academyDAO;

    public AcademyService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        academyDAO = db.academyDAO();
    }


    @Override
    public long insertAcademy(Academy academy) {
        return academyDAO.insertAcademy(academy);
    }

    @Override
    public void updateAcademy(Academy academy) {
        academyDAO.updateAcademy(academy);
    }



    @Override
    public void deleteAcademy(Academy academy) {
        academyDAO.deleteAcademy(academy);
    }

    @Override
    public List<Academy> getAll() {
        return academyDAO.getAll();
    }

    @Override
    public Map<Academy, List<Teacher>> getAcademyWithTeachersMap() {
        return academyDAO.getAcademyWithTeachersMap();
    }

    @Override
    public Map<Academy, List<Course>> getAcademyWithCoursesMap() {
        return academyDAO.getAcademyWithCoursesMap();
    }

    @Override
    public Academy getAcademyByManagerid(Long managerId) {
        return academyDAO.getAcademyByManagerid(managerId);
    }

    @Override
    public void updatebyId(String name_, String description_, String city_, String country_, String state_, String address_, String phone_, String web_, String email_, long id) {
        academyDAO.updatebyId(name_, description_, city_, country_, state_, address_, phone_, web_, email_, id);
    }


}
