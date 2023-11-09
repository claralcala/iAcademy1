package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import es.iescarrillo.iacademy1.R;


public class ManagerMainActivity extends AppCompatActivity {
    Button btnViewTeachers, btnViewCourses, btnViewAcademy, btnViewClassrooms, btnLogout, btnAddAcademy;
    SharedPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manager_main);

        btnViewTeachers = findViewById(R.id.btnViewTeachers);
        btnViewAcademy = findViewById(R.id.btnViewAcademy);
        btnViewCourses = findViewById(R.id.btnViewCourses);
        btnViewClassrooms = findViewById(R.id.btnViewClassrooms);
        btnLogout = findViewById(R.id.btnLogout);
        btnAddAcademy=findViewById(R.id.btnAddAcademy);

        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);




        btnViewTeachers.setOnClickListener(v -> {
            Intent intentViewTeachers = new Intent(this, ViewTeachersActivity.class);
            startActivity(intentViewTeachers);

        });

        btnViewAcademy.setOnClickListener(v -> {
            Intent intentViewAcademy = new Intent(this, AcademyDetailsActivity.class);
            startActivity(intentViewAcademy);
        });

        btnViewCourses.setOnClickListener(v -> {
            Intent intentViewCourses = new Intent(this, ManagerViewCourses.class);
            startActivity(intentViewCourses);
        });

        btnViewClassrooms.setOnClickListener(v -> {
            Intent intentViewClassrooms = new Intent(this, ManagerViewClassroomsActivity.class);
            startActivity(intentViewClassrooms);
        });

        btnAddAcademy.setOnClickListener(v->{
            Intent add = new Intent(this, ManagerRegisterAcademyActivity.class);
            startActivity(add);
        });

        btnLogout.setOnClickListener(v -> {

            sharedPreferences.edit().clear().apply();
            Intent backMain = new Intent(this, MainActivity.class);
            startActivity(backMain);
        });
    }
}