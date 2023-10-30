package es.iescarrillo.iacademy1.services;

import android.app.Application;

import java.util.List;

import es.iescarrillo.iacademy1.daos.UserDAO;
import es.iescarrillo.iacademy1.database.DatabaseHelper;
import es.iescarrillo.iacademy1.models.User;

public class UserService implements UserDAO {

    private UserDAO userDao;

    public UserService(Application application){
        DatabaseHelper db = DatabaseHelper.getInstance(application);
        userDao = db.userDAO();
    }

    @Override
    public long insertUser(User user) {
        return userDao.insertUser(user);
    }

    @Override
    public void uptadeUser(User user) {

        userDao.uptadeUser(user);
    }

    @Override
    public void deleteUser(User user) {

        userDao.deleteUser(user);
    }

    @Override
    public List<User> getAll() {

        return userDao.getAll();
    }
}
