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
import es.iescarrillo.iacademy1.adapters.StudentAdapter;
import es.iescarrillo.iacademy1.models.Student;
import es.iescarrillo.iacademy1.services.StudentService;

public class Teacher_View_Student extends AppCompatActivity {
    ListView lvStudent;
    Button btnBack;
    private StudentService studentService;
    private StudentAdapter adapter;
    List<Student> students;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_view_student);

        //Recuperamos el intent
        Intent intent = getIntent();

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

        //Inicializamos las variables
        lvStudent = findViewById(R.id.lvStudent);
        btnBack = findViewById(R.id.btnBack);

        studentService = new StudentService(getApplication());

        Thread thread = new Thread(()->{

            String courseID = intent.getStringExtra("courseID");
            Long courseID2 = Long.parseLong(courseID);
            Log.i("id", String.valueOf(courseID2));
            students = studentService.getAllStudentInACourse(courseID2);



        });


        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }


        adapter = new StudentAdapter((Context)this, students);



        lvStudent.setAdapter(adapter);

        btnBack.setOnClickListener(v -> {

           // Intent back = new Intent(this, Teacher_Details_Course.class);
           // startActivity(back);
            onBackPressed();
        });
    }
}