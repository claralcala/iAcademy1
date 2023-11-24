package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.ArrayAdapter;

import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.adapters.CourseAdapter;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.services.CourseService;

public class ViewCourses extends AppCompatActivity {

    //Declaramos las variables de servicio, adaptadores, botones , ListView.
private CourseService courseService;
private CourseAdapter adapter;
List<Course> courses;
ListView lvListCourses;

Button btnbackCourses;

    /**
     * @author Manu Rguez
     * Pantalla para visualizar cursos
     *
     */
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_courses);

        //Declaramos las variables de session

        SharedPreferences sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_ = sharedPreferences.getLong("id", 0);


        @SuppressLint("WrongViewCast") ListView lvListCourses = findViewById(R.id.lvLisCourses);


        //Declaramos el servicio y creamos un hilo
        courseService = new CourseService(getApplication());
        Thread thread = new Thread(()->{

            //Llamamos a la consulta que se encuentra en CourseService y nos traemos lo relacionado con los cursos
           courses = courseService.getAll();
        });
        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }

        //Introducimos el adaptador y a la listview le guardamos el adaptador que es donde esta toda la informacion
        adapter = new CourseAdapter((Context)this, courses);
        lvListCourses.setAdapter(adapter);

        //Declaramos el boton de volver y le damos funcion
        btnbackCourses = findViewById(R.id.btnbackCourses);

        btnbackCourses.setOnClickListener( v ->  {

            Intent intent = new Intent(this, StudentActivity.class);

            startActivity(intent);

        });

        //Declaramos funcion a la listView que al hacer clic en un item de la listview muestren los detalles del curso creado
        //Por eso nos traemos los datos con el intent para recuperarlos en la pantalla de details
        lvListCourses.setOnItemClickListener((parent, view, position, id) -> {
            Course c = courses.get(position);

            Intent intent = new Intent(this, DetailsCourses.class);

            intent.putExtra("title", c.getTitle());
            intent.putExtra("description", c.getDescription());
            intent.putExtra("level", c.getLevel());
            intent.putExtra("capacity",c.getCapacity());
            intent.putExtra("start", c.getStartDate());
            intent.putExtra("end", c.getEndDate());
            intent.putExtra("activated", c.isActivated());
            intent.putExtra("id",c.getId());

            startActivity(intent);



        });
    }
}