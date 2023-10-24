package es.iescarrillo.iacademy1.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.repositories.AcademyRepository;
import es.iescarrillo.iacademy1.repositories.ManagerRepository;

public class DatabaseHelper {

    /* Dentro de los corchetes de entities habrá que añadir las clases que queremos convertir en tablas
en nuestra base de datos.
   La versión tendremos que ir incementándola cada vez que hagamos un cambio dentro de nuestra BBDD
* */
    @Database(entities = {Manager.class, Academy.class}, version = 1)
    public abstract class DatabaseHelper extends RoomDatabase {

        // Añadir los DAO
        public abstract ManagerDAO managerDAO();
        public abstract AcademyDAO academyDAO();

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
}
