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

public class Teacher_View_Courses extends AppCompatActivity {

    Button btnAdd;
    Button btnLogout;

    private CourseService courseService;
    private CourseAdapter adapter;
    List<Course> courses;
    ListView lvCourses;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_courses);

        //Declaramos los botones
        btnAdd=findViewById(R.id.btnAdd);
        btnLogout=findViewById(R.id.btnLogout);

        lvCourses=findViewById(R.id.lvCourses);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id_ = sharedPreferences.getLong("id", 0);

        //¿Comprobar si el rol no es el de manager y echarlo?
        if(!role.equals("TEACHER")){


            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);

        }

        courseService = new CourseService(getApplication());

        Thread thread = new Thread(()->{

            courses = courseService.getCoursebyTeacherID(id_);



        });


        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        adapter = new CourseAdapter((Context)this, courses);



        lvCourses.setAdapter(adapter);

        //Cuando clicamos en un item de la lista, nos llevamos en el intent sus datos

        lvCourses.setOnItemClickListener((parent, view, position, id)->{

            Course c = (Course) parent.getItemAtPosition(position);


            Intent intent = new Intent(this, Teacher_Details_Course.class);

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
        //Boton para ir a la pagina de añadir curso
        btnAdd.setOnClickListener(v -> {
            Intent add = new Intent(this, Teacher_Add_Course.class);
            startActivity(add);
        });

        btnLogout.setOnClickListener(v -> {

            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);
        });
    }
    //Este método para formatear las fechas con el formatter lo vi en stack overflow y decidí incluirlo aquí
    private String format(LocalDate localdate){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

        return formatter.format(localdate);
    }
}