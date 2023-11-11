package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import es.iescarrillo.iacademy1.R;
import es.iescarrillo.iacademy1.models.Academy;
import es.iescarrillo.iacademy1.services.AcademyService;
import es.iescarrillo.iacademy1.services.CourseService;

public class EditCourseActivity extends AppCompatActivity {

    EditText etTitle, etDescription, etLevel, etCapacity, etStart, etEnd, etActivated;

    Button btnSave;

    private AcademyService academyService;
    private CourseService courseService;

    Academy a;

    long idAcademy;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_course);


        Intent intent = getIntent();

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);

        btnSave =findViewById(R.id.btnSave);
        etTitle= findViewById(R.id.etCourseTitle);
        etDescription= findViewById(R.id.etCourseDescription);
        etLevel=findViewById(R.id.etCourseLevel);
        etStart=findViewById(R.id.etCourseStart);
        etEnd =findViewById(R.id.etCourseEnd);
        etActivated=findViewById(R.id.etCourseActivated);
        etCapacity=findViewById(R.id.etCourseCapacity);

        etTitle.setText(intent.getStringExtra("title"));
        etDescription.setText(intent.getStringExtra("description"));
        etCapacity.setText(intent.getStringExtra("capacity"));
        etLevel.setText(intent.getStringExtra("level"));
        etStart.setText(intent.getStringExtra("start"));
        etEnd.setText(intent.getStringExtra("end"));
        etActivated.setText(intent.getStringExtra("activated"));


        academyService= new AcademyService(getApplication());
        courseService = new CourseService(getApplication());


        Thread thread = new Thread(()->{


               a = academyService.getAcademyByManagerid(id);

               idAcademy = a.getId();


        });


        thread.start();
        try{
            thread.join();
        }catch(Exception e ){
            Log.i("error", e.getMessage());
        }




        btnSave.setOnClickListener(v -> {

            Thread thread2 = new Thread(()->{

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
                LocalDate startDate= LocalDate.parse(etStart.getText().toString(), formatter);
                LocalDate endDate = LocalDate.parse(etEnd.getText().toString(), formatter);
                Boolean active = Boolean.parseBoolean(etActivated.getText().toString());

                courseService.updateCoursebyId(etTitle.getText().toString(), etDescription.getText().toString(), etLevel.getText().toString(), Integer.parseInt(etCapacity.getText().toString()), startDate, endDate, active, idAcademy, Long.parseLong(intent.getStringExtra("id")));


            });


            thread2.start();
            try{
                thread2.join();
            }catch(Exception e ){
                Log.i("error", e.getMessage());
            }

            Intent back = new Intent(this, ManagerViewCourses.class);

            startActivity(back);

        });


    }
}