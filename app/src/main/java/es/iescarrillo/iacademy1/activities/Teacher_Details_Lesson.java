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
import es.iescarrillo.iacademy1.models.Classroom;
import es.iescarrillo.iacademy1.services.ClassRoomService;
import es.iescarrillo.iacademy1.services.LessonService;

public class Teacher_Details_Lesson extends AppCompatActivity {

    TextView tvLessonDate,tvlessonHour, tvClassroomName;
    Button btnEdit,btnBack;
    private ClassRoomService classRoomService;

    Classroom c;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_details_lesson);

        classRoomService = new ClassRoomService(getApplication());

        //Recuperamos el intent
        //Intent intent = getIntent();

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);
        Long academyID = sharedPreferences.getLong("academyID",0);



        long classroomID = getIntent().getLongExtra("classroomID",0);
        //Log.i("id", classroomID);
        Thread thread = new Thread(()->{
            c = classRoomService.getClassroomByCourseID(classroomID);
        });
        thread.start();
        try {
            thread.join();
        }catch (Exception e ){
            Log.i("error", e.getMessage());
        }


        //Inicializamos componentes
        tvClassroomName=findViewById(R.id.tvClassroomName);
        tvLessonDate=findViewById(R.id.tvLessonDate);
        tvlessonHour=findViewById(R.id.tvlessonHour);
        btnBack=findViewById(R.id.btnBack);
        btnEdit=findViewById(R.id.btnEdit);



        tvlessonHour.setText(getIntent().getStringExtra("time"));
        tvLessonDate.setText(getIntent().getStringExtra("date"));
        tvClassroomName.setText(c.getName());

        btnBack.setOnClickListener(v -> {
            onBackPressed();
        });

        btnEdit.setOnClickListener(v -> {
            Intent edit = new Intent(this, Teacher_Edit_Lesson.class);
            edit.putExtra("time", tvlessonHour.getText().toString());
            edit.putExtra("date", tvLessonDate.getText().toString());
            edit.putExtra("id", getIntent().getLongExtra("id", 0));

            startActivity(edit);

        });

    }
}