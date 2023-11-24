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
    Long idCourseRegistration;

    Button btnBackCourseRegistration;

    private CourseService courseService;
    private CourseAdapter courseAdapter;

    private List<Course> listCourse;
    ListView lvCoursesRegistration;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_courses_registration);

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

        btnBackCourseRegistration = findViewById(R.id.btnbackCoursesRegistration);
        lvCoursesRegistration = findViewById(R.id.lvCoursesRegistration);

        Intent intent = getIntent();
        idCourseRegistration = intent.getLongExtra("id", 0);

        courseService = new CourseService(getApplication());

        Thread thread = new Thread(() -> {
            listCourse = courseService.getEnrolledCoursesStudent(id_);
        });

        thread.start();
        try {
            thread.join();
        } catch (Exception e) {
            Log.e("error", e.getMessage());
        }
        courseAdapter = new CourseAdapter((Context) this,listCourse);

        lvCoursesRegistration.setAdapter(courseAdapter);

        lvCoursesRegistration.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Course c = (Course) parent.getItemAtPosition(position);
                Intent intentLesson  = new Intent(CoursesRegistration.this, DetailsEnrolled.class);
                intentLesson.putExtra("id", c.getId());
                startActivity(intentLesson);
            }
        });


        btnBackCourseRegistration.setOnClickListener(v -> {
            Intent intentBack = new Intent(CoursesRegistration.this, StudentActivity.class);
            startActivity(intentBack);
        });

    }


}
