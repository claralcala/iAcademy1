package es.iescarrillo.iacademy1.database;

import android.content.Context;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import es.iescarrillo.iacademy1.daos.AdminDAO;
import es.iescarrillo.iacademy1.daos.ClassroomDAO;
import es.iescarrillo.iacademy1.daos.LessonDAO;
import es.iescarrillo.iacademy1.daos.TeacherDAO;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Manager;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.daos.InscriptionDAO;
import es.iescarrillo.iacademy1.daos.StudentDAO;
import es.iescarrillo.iacademy1.models.Inscription;
import es.iescarrillo.iacademy1.daos.AcademyDAO;
import es.iescarrillo.iacademy1.daos.CourseDAO;
import es.iescarrillo.iacademy1.daos.ManagerDAO;
import es.iescarrillo.iacademy1.models.Admin;
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.models.Lesson;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.models.Student;
import es.iescarrillo.iacademy1.models.User;



    /* Dentro de los corchetes de entities habrá que añadir las clases que queremos convertir en tablas
en nuestra base de datos.
   La versión tendremos que ir incementándola cada vez que hagamos un cambio dentro de nuestra BBDD
* */
    @Database(entities = {Manager.class, Academy.class, Course.class, Student.class, Inscription.class,Admin.class, Classroom.class, Lesson.class, Teacher.class}, version = 8)
    @TypeConverters({LocalDateConverter.class, LocalTimeConverter.class, LocalDateTimeConverter.class})
    public abstract class DatabaseHelper extends RoomDatabase {




        public abstract StudentDAO studentDAO();

        public abstract InscriptionDAO inscriptionDAO();

        // Añadir los DAO
        public abstract ManagerDAO managerDAO();
        public abstract AcademyDAO academyDAO();

        public abstract CourseDAO courseDAO();

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

