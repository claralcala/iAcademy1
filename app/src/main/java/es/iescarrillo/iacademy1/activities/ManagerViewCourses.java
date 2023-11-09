package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

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
import es.iescarrillo.iacademy1.adapters.TeacherAdapter;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.models.Course;
import es.iescarrillo.iacademy1.models.Teacher;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.CourseService;
import es.iescarrillo.iacademy1.services.TeacherService;

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

        btnAddCourse=findViewById(R.id.btnAddCourse);
        btnBack=findViewById(R.id.btnBack);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id_ = sharedPreferences.getLong("id", 0);

        ListView lvCourses = findViewById(R.id.lvCourses);


        courseService = new CourseService(getApplication());

        academyService = new AcademyService(getApplication());

        Thread thread = new Thread(()->{

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



        lvCourses.setOnItemClickListener((parent, view, position, id)->{

            Course c = (Course) parent.getItemAtPosition(position);

            Intent intent = new Intent(this, ManagerCourseDetails.class);


            startActivity(intent);
        });

        btnAddCourse.setOnClickListener(v->{
            Intent addCourse = new Intent(this, RegisterCourseActivity.class);
            startActivity(addCourse);


        });


        btnBack.setOnClickListener(v->{
            Intent back = new Intent(this, ManagerMainActivity.class);
            startActivity(back);
        });

    }
}