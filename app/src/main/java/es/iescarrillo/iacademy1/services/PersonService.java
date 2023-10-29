package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;


import es.iescarrillo.iacademy1.daos.PersonDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.Person;

public class PersonService implements PersonDAO {


    private PersonDAO personDAO;

    public PersonService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        personDAO = db.personDAO();
    }
    @Override
    public long insertPerson(Person person) {
        return personDAO.insertPerson(person);

    }

    @Override
    public void updatePerson(Person person) {
        personDAO.updatePerson(person);
    }

    @Override
    public void deletePerson(Person person) {
        personDAO.deletePerson(person);
    }

    @Override
    public List<Person> getAll() {
        return personDAO.getAll();
    }
}
