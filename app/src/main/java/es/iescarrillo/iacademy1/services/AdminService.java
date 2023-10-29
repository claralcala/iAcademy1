package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;

import es.iescarrillo.iacademy1.daos.AdminDAO;

import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Admin;
import es.iescarrillo.iacademy1.models.Person;

public class AdminService implements AdminDAO {


    private AdminDAO adminDAO;

    public AdminService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        adminDAO = db.adminDAO();
    }
    @Override
    public long insertAdmin(Admin admin) {
        return adminDAO.insertAdmin(admin);

    }

    @Override
    public void updateAdmin(Admin admin) {
        adminDAO.updateAdmin(admin);
    }

    @Override
    public void deleteAdmin(Admin admin) {
        adminDAO.deleteAdmin(admin);
    }

    @Override
    public List<Admin> getAll() {
        return adminDAO.getAll();
    }
}
