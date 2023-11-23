package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.services.CourseService;

public class Teacher_Details_Course extends AppCompatActivity {

    Button btnEdit, btnDelete, btnViewlesson, btnViewstudent, btnCancel;
    TextView tvTittle, tvDescription, tvLevel, tvCapacity, tvCourseEnd, tvCourseStart, tvActivated;

    private CourseService courseService;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details_course);

        //Recuperamos el intent
        Intent intent = getIntent();

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);
        Long academyID = sharedPreferences.getLong("academyID",0);

        //Inicializamos componentes
         btnEdit = findViewById(R.id.btnEdit);
         btnDelete = findViewById(R.id.btnDelete);
         btnViewlesson=findViewById(R.id.btnViewlesson);
         btnViewstudent = findViewById(R.id.btnViewstudent);
        btnCancel=findViewById(R.id.btnCancel);

        tvTittle = findViewById(R.id.tvTittle);
        tvDescription = findViewById(R.id.tvDescription);
        tvLevel = findViewById(R.id.tvLevel);
        tvCapacity = findViewById(R.id.tvCapacity);
        tvCourseEnd = findViewById(R.id.tvCourseEnd);
        tvCourseStart = findViewById(R.id.tvCourseStart);
        tvActivated = findViewById(R.id.tvActivated);

        courseService = new CourseService(getApplication());

        //Ponemos los datos que traemos del intent en los campos de texto
        tvTittle.setText(intent.getStringExtra("title"));
        tvDescription.setText(intent.getStringExtra("description"));
        tvLevel.setText(intent.getStringExtra("level"));
        tvCapacity.setText(intent.getStringExtra("capacity"));
        tvCourseStart.setText(intent.getStringExtra("startDate"));
        tvCourseEnd.setText(intent.getStringExtra("endDate"));
        tvActivated.setText(intent.getStringExtra("activated"));

        //Long courseID2 = Long.parseLong(intent.getStringExtra("id"));
        String courseID = intent.getStringExtra("id");

        btnCancel.setOnClickListener(v -> {
            Intent back = new Intent(this, Teacher_View_Courses.class);
            startActivity(back);
        });

        btnEdit.setOnClickListener(v -> {
            Intent edit = new Intent (this, Teacher_Edit_Course.class);

            edit.putExtra("title", tvTittle.getText().toString());
            edit.putExtra("description", tvDescription.getText().toString());
            edit.putExtra("level", tvLevel.getText().toString());
            edit.putExtra("capacity", tvCapacity.getText().toString());
            edit.putExtra("start", tvCourseStart.getText().toString());
            edit.putExtra("end", tvCourseEnd.getText().toString());
            edit.putExtra("activated", tvActivated.getText().toString());
            edit.putExtra("id", intent.getStringExtra("id"));


            startActivity(edit);
        });

        btnViewstudent.setOnClickListener(v -> {
            Intent viewStudent = new Intent(this, Teacher_View_Student.class);
            //Log.i("id",courseID);
            viewStudent.putExtra("courseID", courseID);


            startActivity(viewStudent);
        });

        btnViewlesson.setOnClickListener(v -> {
            Intent viewLesson = new Intent(this, Teacher_View_Lesson.class);
            viewLesson.putExtra("courseID", courseID);
            startActivity(viewLesson);
        });

        btnDelete.setOnClickListener(v -> {
            Intent delete = new Intent(this, Teacher_View_Courses.class);

            Thread thread = new Thread(()->{

                courseService.deleteCourseById(Long.parseLong(courseID),academyID);

            });
            thread.start();
            try{
                thread.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }
            startActivity(delete);
        });

    }
}