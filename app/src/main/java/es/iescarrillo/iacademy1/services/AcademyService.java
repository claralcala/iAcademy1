package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;

import es.iescarrillo.iacademy1.daos.AcademyDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Academy;

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
}
