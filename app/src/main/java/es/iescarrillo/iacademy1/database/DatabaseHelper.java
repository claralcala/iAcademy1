package es.iescarrillo.iacademy1.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.iescarrillo.iacademy1.daos.AcademyDAO;
import es.iescarrillo.iacademy1.daos.CourseDAO;
import es.iescarrillo.iacademy1.daos.ManagerDAO;
import es.iescarrillo.iacademy1.daos.PersonDAO;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.models.Person;
import es.iescarrillo.iacademy1.repositories.AcademyRepository;
import es.iescarrillo.iacademy1.repositories.ManagerRepository;



    /* Dentro de los corchetes de entities habrá que añadir las clases que queremos convertir en tablas
en nuestra base de datos.
   La versión tendremos que ir incementándola cada vez que hagamos un cambio dentro de nuestra BBDD
* */
    @Database(entities = {Manager.class, Academy.class, Course.class, Person.class}, version = 1)
    public abstract class DatabaseHelper extends RoomDatabase {

        // Añadir los DAO
        public abstract ManagerDAO managerDAO();
        public abstract AcademyDAO academyDAO();

        public abstract CourseDAO courseDAO();

        public abstract PersonDAO personDAO();
        // Instancia estática de la clase, para oder usarla en toda la aplicación
        private static DatabaseHelper instance;

        // Método de Android Room para crear la base de datos
        public static synchronized DatabaseHelper getInstance(Context context) {
            if (instance == null) {
                instance = Room.databaseBuilder(context.getApplicationContext(), DatabaseHelper.class, "iAcademy")
                        .fallbackToDestructiveMigration() // Si se cambia la versión elimina y reconstruye
                        .build();
            }
            return instance;
        }

    }

