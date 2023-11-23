package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;

import es.iescarrillo.iacademy1.daos.InscriptionDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Inscription;

public class InscriptionService implements InscriptionDAO {

    private InscriptionDAO inscriptionDAO;

    public InscriptionService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        inscriptionDAO=db.inscriptionDAO();
    }
    @Override
    public long insertInscription(Inscription inscription) {
        return inscriptionDAO.insertInscription(inscription);
    }

    @Override
    public void updateInscription(Inscription inscription) {

        inscriptionDAO.updateInscription(inscription);

    }

    @Override
    public void deleteInscription(Inscription inscription) {
        inscriptionDAO.deleteInscription(inscription);
    }

    @Override
    public List<Inscription> getAll() {
        return inscriptionDAO.getAll();
    }

    @Override
    public int countInscriptionInACourse(Long courseID) {
        return inscriptionDAO.countInscriptionInACourse(courseID);
    }
}
