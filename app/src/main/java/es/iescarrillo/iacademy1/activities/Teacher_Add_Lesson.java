package es.iescarrillo.iacademy1.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import es.iescarrillo.iacademy1.R;

public class Teacher_Add_Lesson extends AppCompatActivity {

    Button btnAdd, btnCancel;

    EditText etLessonHour, etLessonDate;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_add_lesson);

        //Declaramos los botones
        btnAdd = findViewById(R.id.btnAdd);
        btnCancel = findViewById(R.id.btnCancel);

        //Declaramos los editText
        etLessonHour=findViewById(R.id.etLessonHour);
        etLessonDate=findViewById(R.id.etLessonDate);

        //Variables de sesión
        SharedPreferences sharedPreferences= getSharedPreferences("PreferencesAcademy", Context.MODE_PRIVATE);
        String username= sharedPreferences.getString("user", "");
        String role = sharedPreferences.getString("role", "");
        Boolean login = sharedPreferences.getBoolean("login", true);
        Long id = sharedPreferences.getLong("id", 0);

        //Boton para
        btnAdd.setOnClickListener(v -> {

            Intent add = new Intent (this, Teacher_View_Lesson.class);
            startActivity(add);
        });

    }
}