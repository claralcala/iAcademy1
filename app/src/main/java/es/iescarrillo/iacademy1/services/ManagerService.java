package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;
import java.util.Map;

import es.iescarrillo.iacademy1.daos.ManagerDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Manager;


public class ManagerService implements ManagerDAO {

    private ManagerDAO managerDAO;

    public ManagerService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        managerDAO = db.managerDAO();
    }
    @Override
    public long insertManager(Manager manager) {
        return managerDAO.insertManager(manager);
    }

    @Override
    public void updateManager(Manager manager) {
        managerDAO.updateManager(manager);
    }

    @Override
    public void deleteManager(Manager manager) {
        managerDAO.deleteManager(manager);
    }

    @Override
    public List<Manager> getAll() {
        return managerDAO.getAll();
    }

    @Override
    public Map<Manager, Academy> getManagerWithAcademyMap() {
        return managerDAO.getManagerWithAcademyMap();
    }


    @Override
    public Map<Manager, List<Course>> getManagerWithCoursesMap() {
        return managerDAO.getManagerWithCoursesMap();
    }

    @Override
    public Manager getManagerByUsername(String username) {
        return managerDAO.getManagerByUsername(username);
    }
}
