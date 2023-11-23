package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;

import es.iescarrillo.iacademy1.R;

public class DetailsEnrolled extends AppCompatActivity {

    Button btnBackDetailsLesson;
    @SuppressLint("MissingInflatedId")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details_enrolled);

        SharedPreferences sharedPreferences = getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username = sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", false);
        Long id = sharedPreferences.getLong("id", 0);


        btnBackDetailsLesson =  findViewById(R.id.btnBackDetailsLesson);

        btnBackDetailsLesson.setOnClickListener( v -> {

            Intent intent = new Intent(this, CoursesRegistration.class);

            startActivity(intent);
        });


    }
}