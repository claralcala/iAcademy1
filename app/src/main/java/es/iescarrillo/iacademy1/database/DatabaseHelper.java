package es.iescarrillo.iacademy1.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import es.iescarrillo.iacademy1.daos.AdminDAO;
import es.iescarrillo.iacademy1.daos.ClassroomDAO;
import es.iescarrillo.iacademy1.daos.LessonDAO;
import es.iescarrillo.iacademy1.daos.PersonDAO;
import es.iescarrillo.iacademy1.daos.TeacherDAO;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Admin;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.models.Lesson;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.models.Person;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.repositories.AcademyRepository;
import es.iescarrillo.iacademy1.repositories.ManagerRepository;



    /* Dentro de los corchetes de entities habrá que añadir las clases que queremos convertir en tablas
en nuestra base de datos.
   La versión tendremos que ir incementándola cada vez que hagamos un cambio dentro de nuestra BBDD
* */
    @Database(entities = {Manager.class, Academy.class, Person.class, Admin.class, Classroom.class, Lesson.class, Teacher.class}, version = 1)
    public abstract class DatabaseHelper extends RoomDatabase {

        // Añadir los DAO
        public abstract ManagerDAO managerDAO();
        public abstract AcademyDAO academyDAO();
        public abstract PersonDAO personDAO();

        public abstract AdminDAO adminDAO();
        public abstract ClassroomDAO classroomDAO();
        public abstract LessonDAO lessonDAO();
        public abstract TeacherDAO teacherDAO();



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

