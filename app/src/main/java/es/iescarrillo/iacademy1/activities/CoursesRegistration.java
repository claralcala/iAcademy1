package es.iescarrillo.iacademy1.activities;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import androidx.appcompat.app.AppCompatActivity;

import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.adapters.AcademyAdapter;
import es.iescarrillo.iacademy1.adapters.CourseAdapter;
import es.iescarrillo.iacademy1.models.Course;

import es.iescarrillo.iacademy1.services.CourseService;

public class CoursesRegistration extends AppCompatActivity {

    //Declaramos una variable para pillar la id del curso registrado, bototnes servicios, adaptadores, list y una listView
    Long idCourseRegistration;

    Button btnBackCourseRegistration;

    private CourseService courseService;
    private CourseAdapter courseAdapter;

    private List<Course> listCourse;
    ListView lvCoursesRegistration;

    /**
     * @author Manu Rguez
     * Pantalla para visualizar todos los cursos en los que un studen se ha matriculado
     *
     */

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_registration);


        //Declaramos las variables de session
        SharedPreferences sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_ = sharedPreferences.getLong("id", 0);

        //declamos el boton y la lv que hemos declarado en java con sus correspondientes en el xml
        btnBackCourseRegistration = findViewById(R.id.btnbackCoursesRegistration);
        lvCoursesRegistration = findViewById(R.id.lvCoursesRegistration);


        //Recuperamos la variable desde el intent
        Intent intent = getIntent();
        idCourseRegistration = intent.getLongExtra("id", 0);

        //Declaramos el servicio
        courseService = new CourseService(getApplication());


        //Creamos un hilo
        Thread thread = new Thread(() -> {
            listCourse = courseService.getEnrolledCoursesStudent(id_);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }

        //Introducimos el adaptador y a la listview le guardamos el adaptador que es donde esta toda la informacion
        courseAdapter = new CourseAdapter((Context) this,listCourse);
        lvCoursesRegistration.setAdapter(courseAdapter);


        //Le damos funcion que al hacer clic en un curso matriculado nos diriga a la lista de las lecciones y le damos el id del course que vamos a recoger alli
        lvCoursesRegistration.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course c = (Course) parent.getItemAtPosition(position);
                Intent intentLesson  = new Intent(CoursesRegistration.this, DetailsEnrolled.class);
                intentLesson.putExtra("id", c.getId());
                startActivity(intentLesson);
            }
        });

        //Le damos funcion al boton de volver

        btnBackCourseRegistration.setOnClickListener(v -> {
            Intent intentBack = new Intent(CoursesRegistration.this, StudentActivity.class);
            startActivity(intentBack);
        });

    }


}
