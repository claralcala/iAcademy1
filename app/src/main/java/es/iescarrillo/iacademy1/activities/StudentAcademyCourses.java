package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.ListView;

import java.util.List;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.adapters.CourseAdapter;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.services.CourseService;



public class StudentAcademyCourses extends AppCompatActivity {
//Declaramso lso servicios, adaptadores, lista de courses y una listView y botones
    private CourseService courseService;
    private CourseAdapter adapter;
    List<Course> courses;
    ListView lvListCourses;

    Button btnbackCourses;

    /**
     * @author Manu Rguez
     * Pantalla para visualizar los cursos de una academia
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

        //¿Comprobar si el rol no es el de student y echarlo?
        if(!role.equals("STUDENT")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        //Recuperamos los datos del intent  de la clase CourseRegistration

        Intent intent2 = getIntent();

        //declaramos en la variable idCourseRegistration la id del course qeu se ha registado

        Long idCourseRegistration = intent2.getLongExtra("id", 0);

        //Asignamos el listcourses que hemos creado en java se lo asignamos al que hemos creado en el xml
        @SuppressLint("WrongViewCast") ListView lvListCourses = findViewById(R.id.lvLisCourses);

        //Declaramos el servicio y creamos un hilo
        courseService = new CourseService(getApplication());
        Thread thread = new Thread(()->{

            // introducimos en course el course que hemos buscado por el id de la academia y le introducimos
            //por parametro el id del course que hemos recuperado en el intent
            courses = courseService.getCoursebyAcademyID(idCourseRegistration);
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

        //Declaramos boton de volver y le damos funcion que nos lleva a la StudenActivity

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