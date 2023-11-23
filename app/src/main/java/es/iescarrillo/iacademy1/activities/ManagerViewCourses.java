package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.adapters.CourseAdapter;
import es.iescarrillo.iacademy1.adapters.TeacherAdapter;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.CourseService;
import es.iescarrillo.iacademy1.services.TeacherService;

/**
 *
 * @author clara
 * Pantalla para que el manager visualice los cursos de su academia
 */
public class ManagerViewCourses extends AppCompatActivity {

    CourseAdapter adapter;
    Button btnAddCourse, btnBack;
    CourseService courseService;

    List<Course> courses;

    private AcademyService academyService;
    long idAcademy;

    Academy a;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_view_courses);

        //Inicializamos los botones
        btnAddCourse=findViewById(R.id.btnAddCourse);
        btnBack=findViewById(R.id.btnBack);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_ = sharedPreferences.getLong("id", 0);

        //Listview
        ListView lvCourses = findViewById(R.id.lvCourses);


        //Inicializamos servicios
        courseService = new CourseService(getApplication());

        academyService = new AcademyService(getApplication());


        Thread thread = new Thread(()->{
            //Buscamos la academia por el id del manager
            a =academyService.getAcademyByManagerid(id_);
            idAcademy=a.getId();



        });

        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        Thread thread2 = new Thread(()->{
            //Nos traemos una lista de los cursos por el id de la academia que hemos obtenido previamente

            courses= courseService.getCoursebyAcademyID(idAcademy);



        });


        thread2.start();
        try{
            thread2.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }

        adapter = new CourseAdapter((Context)this, courses);



        lvCourses.setAdapter(adapter);


        //Cuando clicamos en un item de la lista, nos llevamos en el intent sus datos

        lvCourses.setOnItemClickListener((parent, view, position, id)->{

            Course c = (Course) parent.getItemAtPosition(position);


            Intent intent = new Intent(this, ManagerCourseDetails.class);

            String startDate = format(c.getStartDate());
            String endDate = format(c.getEndDate());
            intent.putExtra("title", c.getTitle());
            intent.putExtra("description", c.getDescription());
            intent.putExtra("level", c.getLevel());
            intent.putExtra("capacity", c.getCapacity().toString());
            intent.putExtra("startDate", startDate);
            intent.putExtra("endDate", endDate);
            intent.putExtra("activated", String.valueOf(c.isActivated()));
            intent.putExtra("id", Long.toString(c.getId()));



            startActivity(intent);
        });

        //Acción del botón de añadir
        btnAddCourse.setOnClickListener(v->{
            Intent addCourse = new Intent(this, RegisterCourseActivity.class);
            startActivity(addCourse);


        });


        //Acción del botón de volver
        btnBack.setOnClickListener(v->{
            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });

    }


    //Este método para formatear las fechas con el formatter lo vi en stack overflow y decidí incluirlo aquí
    private String format(LocalDate localdate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return formatter.format(localdate);
    }
}